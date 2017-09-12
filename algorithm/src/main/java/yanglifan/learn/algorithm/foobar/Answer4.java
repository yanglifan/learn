package yanglifan.learn.algorithm.foobar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Answer4 {
    static int[] answer(int[][] m) {
        int width = m[0].length;
        int[] nonAbsorbIds = findSubMatrixIndices(m);

        adjust(m, nonAbsorbIds);

        F[][] fm = toFractionMatrix(m);
        M r = subMatrix(fm, nonAbsorbIds, 0, width - nonAbsorbIds.length - 1);
        M q = subMatrix(fm, nonAbsorbIds, width - nonAbsorbIds.length, width - 1);

        M i = createIdentityMatrix(q.height(), q.width());
        M iSubQ = i.sub(q);
        M f = iSubQ.inverse();

        M result = f.multiply(r);
        result.simplify();

        F[] ffa = result.v[0];
        int d = 0;
        for (F fr : ffa) {
            if (d < fr.d) {
                d = fr.d;
            }
        }

        int[] finalResult = new int[ffa.length + 1];
        int k = 0;
        for (F fr : ffa) {
            if (fr.d != d) {
                fr.n = fr.n * (d / fr.d);
                fr.d = d;
            }
            finalResult[k++] = fr.n;

        }

        finalResult[finalResult.length - 1] = d;

        return finalResult;
    }

    private static void adjust(int[][] m, int[] nonAbsorbIds) {
        for (int index : nonAbsorbIds) {
            int[] newArray = new int[m[index].length];
            for (int i = 0; i < m[index].length; i++) {
                if (contains(i, nonAbsorbIds)) {
                    newArray[i + m[index].length - nonAbsorbIds.length] = m[index][i];
                } else {
                    int newIndex = i - nonAbsorbIds.length > 0 ? i - nonAbsorbIds.length : 0;
                    newArray[newIndex] = m[index][i];
                }
            }

            m[index] = newArray;
        }
    }

    private static boolean contains(int v, int[] array) {
        for (int i : array) {
            if (i == v) {
                return true;
            }
        }

        return false;
    }

    private static M createIdentityMatrix(int l, int w) {
        F[][] im = new F[l][w];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                if (i == j) {
                    im[i][j] = new F(1, 1);
                } else {
                    im[i][j] = new F(0, 1);
                }
            }
        }
        return new M(im);
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
        List<Integer> notAbsorbingIndices = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            boolean nonAbsorbingRow = false;
            for (int num : matrix[i]) {
                if (num != 0) {
                    nonAbsorbingRow = true;
                    break;
                }
            }
            if (nonAbsorbingRow) {
                notAbsorbingIndices.add(i);
            }
        }

        return toIntArray(notAbsorbingIndices);
    }

    private static int[] toIntArray(List<Integer> integerArray) {
        int[] array = new int[integerArray.size()];
        for (int i = 0; i < integerArray.size(); i++) {
            array[i] = integerArray.get(i);
        }
        return array;
    }

    private static M subMatrix(F[][] matrix, int[] nonAbsorbingMatrixIndices, int start, int end) {
        System.out.println("[subMatrix] start: " + start + ", end: " + end);
        F[][] subMatrix = new F[nonAbsorbingMatrixIndices.length][end - start];
        int i = 0;
        for (int index : nonAbsorbingMatrixIndices) {
            subMatrix[i++] = Arrays.copyOfRange(matrix[index], start, end + 1);
        }
        return new M(subMatrix).simplify();
    }
}

class M {
    F[][] v;

    M(F[][] value) {
        this.v = value;
    }

    private static F[][] confactor(F[][] data, int h, int v) {
        int height = data.length;
        int length = data[0].length;
        F[][] newData = new F[height - 1][length - 1];
        for (int i = 0; i < newData.length; i++) {
            if (i < h - 1) {
                for (int j = 0; j < newData[i].length; j++) {
                    if (j < v - 1) {
                        newData[i][j] = data[i][j];
                    } else {
                        newData[i][j] = data[i][j + 1];
                    }
                }
            } else {
                for (int j = 0; j < newData[i].length; j++) {
                    if (j < v - 1) {
                        newData[i][j] = data[i + 1][j];
                    } else {
                        newData[i][j] = data[i + 1][j + 1];
                    }
                }
            }
        }
        return newData;
    }

    private static F[][] trans(F[][] matrix) {
        F[][] newMatrix = new F[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[j][i] = matrix[i][j];
            }
        }
        return newMatrix;
    }

    M inverse() {
        if (height() == 2) {
            F p = v[0][0].multiply(v[1][1]).sub(v[0][1].multiply(v[1][0]));
            p = new F(p.d, p.n);
            F[][] inv = new F[2][2];
            inv[0][0] = v[1][1].multiply(p);
            inv[0][1] = v[0][1].multiply(p).multiply(new F(-1, 1));
            inv[1][0] = v[1][0].multiply(p).multiply(new F(-1, 1));
            inv[1][1] = v[0][0].multiply(p);
            return new M(inv).simplify();
        }

        F[][] inv = new F[height()][width()];
        F a = getMatrixResult(v);
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                if ((i + j) % 2 == 0) {
                    inv[i][j] = getMatrixResult(confactor(v, i + 1, j + 1)).multiply(new F(a.d, a.n));
                } else {
                    inv[i][j] = getMatrixResult(confactor(v, i + 1, j + 1)).multiply(new F(-a.d, a.n));
                }
            }
        }

        inv = trans(inv);

        return new M(inv);
    }

    private F getMatrixResult(F[][] data) {
        if (data.length == 2) {
            return data[0][0].multiply(data[1][1]).sub(data[0][1].multiply(data[1][0]));
        }
        F result = new F(0, 1);
        int num = data.length;
        F[] nums = new F[num];
        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                F newValue = getMatrixResult(confactor(data, 1, i + 1));
                nums[i] = data[0][i].multiply(newValue);
            } else {
                nums[i] = new F(-data[0][i].n, data[0][i].d)
                        .multiply(getMatrixResult(confactor(data, 1, i + 1)));
            }
        }
        for (int i = 0; i < data.length; i++) {
            result = result.plus(nums[i]);
        }
        return result;
    }

    M sub(M m) {
        F[][] resultValue = new F[m.v.length][m.v[0].length];
        M result = new M(resultValue);
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                result.v[i][j] = v[i][j].sub(m.v[i][j]);
            }
        }
        return result;
    }

    int height() {
        return v.length;
    }

    int width() {
        return v[0].length;
    }

    M multiply(M other) {
        F[][] newOne = new F[other.v.length][other.v[0].length];
        for (int i = 0; i < other.v.length; i++) {
            for (int j = 0; j < other.v[i].length; j++) {
                F[] row = findRow(i);
                F[] col = other.findCol(j);

                if (row.length != col.length) {
                    throw new RuntimeException("error");
                }

                F r = new F(0, 1);
                for (int k = 0; k < row.length; k++) {
                    r = r.plus(row[k].multiply(col[k]));
                }
                newOne[i][j] = r;
            }
        }

        return new M(newOne);
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

    M simplify() {
        for (F[] i : v) {
            for (F j : i) {
                j.simplify();
            }
        }

        return this;
    }
}

class F {
    int n;
    int d;

    F(int n, int d) {
        this.n = n;
        this.d = d;
    }

    void simplify() {
        int common = 1;
        for (int k = d; k > 0; k--) {
            if (n % k == 0 && d % k == 0) {
                common = k;
                break;
            }
        }

        if (common != 1) {
            n = n / common;
            d = d / common;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        F fraction = (F) o;

        return n == fraction.n && d == fraction.d;
    }

    @Override
    public String toString() {
        return n + "/" + d;
    }

    F multiply(F other) {
        return new F(this.n * other.n, this.d * other.d);
    }

    F plus(F other) {
        int newThisNumerator = this.n * other.d;
        int newOtherNumerator = other.n * this.d;
        return new F(newThisNumerator + newOtherNumerator, this.d * other.d);
    }

    F sub(F other) {
        int newThisNumerator = this.n * other.d;
        int newOtherNumerator = other.n * this.d;
        return new F(newThisNumerator - newOtherNumerator, this.d * other.d);
    }
}
