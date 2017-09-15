package yanglifan.learn.algorithm.foobar;

import java.util.Arrays;

class Answer4 {
    public static int[] answer(int[][] m) {
        if (m.length == 1) {
            if (m[0][0] == 0) {
                return new int[]{1, 1};
            } else {
                return new int[]{0};
            }
        }

        int width = m[0].length;
        int[] nonAbsorbIds = findSubMatrixIndices(m);

        int[] absorbIds = new int[m.length - nonAbsorbIds.length];
        int j = 0;
        for (int i = 0; i < m.length; i++) {
            if (locate(i, nonAbsorbIds) == -1) {
                absorbIds[j++] = i;
            }
        }

        adjust(m, nonAbsorbIds, absorbIds);

        F[][] fm = toFractionMatrix(m);
        int w = width - nonAbsorbIds.length - 1 < 0 ? 0 : width - nonAbsorbIds.length - 1;
        M r = subMatrix(fm, nonAbsorbIds, 0, w);
        M q = subMatrix(fm, nonAbsorbIds, width - nonAbsorbIds.length, width - 1);

        M iSubQ = iSubQ(q);
        M f = iSubQ.inv();

        M f2 = new M(new F[][]{f.v[0]});
        M result = f2.multiply(r);

        F[] ffa = result.v[0];
        long d = ffa[0].d;
        for (F fr : ffa) {
            fr.simplify();
            if (d % fr.d != 0) {
                d = d * fr.d / gcd(fr.d, d);
            }
        }

        int[] finalResult = new int[ffa.length + 1];
        int k = 0;
        for (F fr : ffa) {
            if (fr.d != d) {
                fr.n = fr.n * (d / fr.d);
                fr.d = d;
            }
            finalResult[k++] = Long.valueOf(fr.n).intValue();
        }

        finalResult[finalResult.length - 1] = Long.valueOf(d).intValue();

        return finalResult;
    }

    private static void adjust(int[][] m, int[] nonAbsorbIds, int[] absorbIds) {
        for (int index : nonAbsorbIds) {
            int[] newArray = new int[m[index].length];
            for (int i = 0; i < m[index].length; i++) {
                int newIndex;
                int nonAbsorbPos = locate(i, nonAbsorbIds);
                if (nonAbsorbPos != -1) {
                    newIndex = absorbIds.length + nonAbsorbPos;
                } else {
                    int absorbPos = locate(i, absorbIds);
                    newIndex = m.length - (nonAbsorbIds.length + absorbIds.length - absorbPos);
                }
                newArray[newIndex] = m[index][i];
            }

            m[index] = newArray;
        }
    }

    private static int locate(int v, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (v == array[i]) {
                return i;
            }
        }

        return -1;
    }

    private static M iSubQ(M q) {
        for (int i = 0; i < q.width(); i++) {
            for (int j = 0; j < q.width(); j++) {
                F f = q.v[i][j];
                if (i == j) {
                    f.n = f.d - f.n;
                } else {
                    f.n = -f.n;
                }
            }
        }
        return q;
    }

    private static F[][] toFractionMatrix(int[][] matrix) {
        F[][] fractionMatrix = new F[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int denominator = 0;
            for (int num : matrix[i]) {
                denominator += num;
            }

            for (int j = 0; j < matrix[i].length; j++) {
                fractionMatrix[i][j] = new F(matrix[i][j], denominator);
            }
        }
        return fractionMatrix;
    }

    static int[] findSubMatrixIndices(int[][] matrix) {
        int[] notAbsorbingIndices = new int[matrix.length];
        int j = 0;
        for (int i = 0; i < matrix.length; i++) {
            boolean nonAbsorbingRow = false;
            for (int num : matrix[i]) {
                if (num != 0) {
                    nonAbsorbingRow = true;
                    break;
                }
            }
            if (nonAbsorbingRow) {
                notAbsorbingIndices[j++] = i;
            }
        }

        return Arrays.copyOf(notAbsorbingIndices, j);
    }

    private static M subMatrix(F[][] matrix, int[] nonAbsorbingMatrixIndices, int start, int end) {
        F[][] subMatrix = new F[nonAbsorbingMatrixIndices.length][end - start];
        int i = 0;
        for (int index : nonAbsorbingMatrixIndices) {
            subMatrix[i++] = Arrays.copyOfRange(matrix[index], start, end + 1);
        }
        return new M(subMatrix).simplify();
    }

    private static long gcd(long n, long d) {
        if (n == 0) {
            return 1;
        }

        long t;
        while (d % n != 0) {
            t = n;
            n = d % n;
            d = t;
        }
        return n;
    }

    static class M {
        F[][] v;

        M(F[][] value) {
            this.v = value;
        }

        M inv() {
            int n = v.length;
            F[][] t = new F[n][n * 2];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    t[i][j] = v[i][j];
                    t[i][j + n] = new F(0, 1);
                }
                t[i][i + n] = new F(1, 1);
            }

            for (int i = 0; i < n; i++) {
                F s = t[i][i];
                for (int j = 0; j < n * 2; j++) {
                    if (t[i][j] == null) {
                        t[i][j] = new F(0, 1);
                    } else {
                        t[i][j] = t[i][j].multiply(new F(s.d, s.n));
                    }
                }

                for (int j = 0; j < n; j++) {
                    if (j != i) {
                        s = t[j][i];
                        for (int k = 0; k < n * 2; k++) {
                            F m = s.multiply(t[i][k]);
                            t[j][k] = t[j][k].sub(m);
                        }
                    }
                }
            }

            F[][] r = new F[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    r[i][j] = t[i][j + n];
                }
            }
            return new M(r).simplify();
        }

        int width() {
            return v[0].length;
        }

        M multiply(M other) {
            F[][] newOne = new F[other.v.length][other.v[0].length];
            for (int i = 0; i < this.v.length; i++) {
                F[] row = findRow(i);
                for (int j = 0; j < other.v[i].length; j++) {
                    F[] col = other.findCol(j);

                    if (row.length != col.length) {
                        throw new RuntimeException("error");
                    }

                    F r = new F(0, 1);
                    for (int k = 0; k < row.length; k++) {
                        r = r.plus(row[k].multiply(col[k]).simplify()).simplify();
                    }
                    newOne[i][j] = r;
                }
            }

            return new M(newOne);
        }

        M simplify() {
            for (F[] i : v) {
                for (F j : i) {
                    j.simplify();
                }
            }

            return this;
        }

        private F[] findRow(int row) {
            return v[row];
        }

        private F[] findCol(int col) {
            F[] column = new F[v.length];
            int i = 0;
            for (F[] row : v) {
                column[i++] = row[col];
            }

            return column;
        }
    }

    static class F {
        long n;
        long d;

        F(long n, long d) {
            this.n = n;
            this.d = d;
        }

        F simplify() {
            if (n == 0) {
                d = 1;
                return this;
            }

            if (n == d) {
                d = 1;
                n = 1;
                return this;
            }

            long common = gcd(n, d);

            if (common != 1) {
                n = n / common;
                d = d / common;
            }
            return this;
        }

        F multiply(F other) {
            this.simplify();
            other.simplify();
            return new F(this.n * other.n, this.d * other.d).simplify();
        }

        F plus(F other) {
            long newThisNumerator = this.n * other.d;
            long newOtherNumerator = other.n * this.d;
            return new F(newThisNumerator + newOtherNumerator, this.d * other.d).simplify();
        }

        F sub(F other) {
            long newThisNumerator = this.n * other.d;
            long newOtherNumerator = other.n * this.d;
            return new F(newThisNumerator - newOtherNumerator, this.d * other.d).simplify();
        }
    }
}
