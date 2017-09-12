package yanglifan.learn.algorithm.foobar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1. Find M Q [done]
 * 2. Find M R [done]
 * 3. Calculate I - Q
 * 4. Calculate (I - Q) ^ -1 (F)
 * 5. Calculate FR
 * 6. Simplify the final M
 *
 * @author Yang Lifan
 */
class Answer4 {
    static int[] answer(int[][] m) {
        int width = m[0].length;
        int[] nonAbsorbingMatrixIndices = findSubMatrixIndices(m);

        adjustMatrix(m, nonAbsorbingMatrixIndices);

        Fraction[][] fractionMatrix = toFractionMatrix(m);
        M r = findSubMatrix(
                fractionMatrix,
                nonAbsorbingMatrixIndices,
                0,
                width - nonAbsorbingMatrixIndices.length - 1
        );

        M q = findSubMatrix(
                fractionMatrix,
                nonAbsorbingMatrixIndices,
                width - nonAbsorbingMatrixIndices.length,
                width - 1
        );


        M i = createIdentityMatrix(q.height(), q.width());
        M iSubQ = i.sub(q);
        Fraction[][] matrixF = MatrixUtils.getInverseMatrix(iSubQ.value);

        M f = new M(matrixF);
        f.simplify();

        M result = f.multiply(r);
        result.simplify();

        Fraction[] finalFractionArray = result.value[0];
        int denominator = 0;
        for (Fraction fr : finalFractionArray) {
            if (denominator < fr.denominator) {
                denominator = fr.denominator;
            }
        }

        int[] finalResult = new int[finalFractionArray.length + 1];
        int k = 0;
        for (Fraction fr : finalFractionArray) {
            if (fr.denominator != denominator) {
                fr.numerator = fr.numerator * (denominator / fr.denominator);
                fr.denominator = denominator;
            }
            finalResult[k++] = fr.numerator;

        }

        finalResult[finalResult.length - 1] = denominator;

        return finalResult;
    }

    private static void adjustMatrix(int[][] m, int[] nonAbsorbingMatrixIndices) {
        for (int index : nonAbsorbingMatrixIndices) {
            int[] newArray = new int[m[index].length];
            for (int i = 0; i < m[index].length; i++) {
                if (contains(i, nonAbsorbingMatrixIndices)) {
                    newArray[i + m[index].length - nonAbsorbingMatrixIndices.length] = m[index][i];
                } else {
                    int newIndex = i - nonAbsorbingMatrixIndices.length > 0 ? i - nonAbsorbingMatrixIndices.length : 0;
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

    private static M createIdentityMatrix(int length, int width) {
        Fraction[][] identityMatrix = new Fraction[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (i == j) {
                    identityMatrix[i][j] = new Fraction(1, 1);
                } else {
                    identityMatrix[i][j] = new Fraction(0, 1);
                }
            }
        }
        return new M(identityMatrix);
    }

    private static Fraction[][] toFractionMatrix(int[][] matrix) {
        Fraction[][] fractionMatrix = new Fraction[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            int denominator = 0;
            for (int num : matrix[i]) {
                denominator += num;
            }

            for (int j = 0; j < matrix[i].length; j++) {
                fractionMatrix[i][j] = new Fraction(matrix[i][j], denominator);
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

    private static M findSubMatrix(Fraction[][] matrix, int[] nonAbsorbingMatrixIndices, int start, int end) {
        System.out.println("[findSubMatrix] start: " + start + ", end: " + end);
        Fraction[][] subMatrix = new Fraction[nonAbsorbingMatrixIndices.length][end - start];
        int i = 0;
        for (int index : nonAbsorbingMatrixIndices) {
            subMatrix[i++] = Arrays.copyOfRange(matrix[index], start, end + 1);
        }
        return new M(subMatrix).simplify();
    }
}

class M {
    Fraction[][] value;

    M(Fraction[][] value) {
        this.value = value;
    }

    M sub(M m) {
        Fraction[][] resultValue = new Fraction[m.value.length][m.value[0].length];
        M result = new M(resultValue);
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                result.value[i][j] = value[i][j].sub(m.value[i][j]);
            }
        }
        return result;
    }

    int height() {
        return value.length;
    }

    int width() {
        return value[0].length;
    }

    M multiply(M other) {
        Fraction[][] newOne = new Fraction[other.value.length][other.value[0].length];
        for (int i = 0; i < other.value.length; i++) {
            for (int j = 0; j < other.value[i].length; j++) {
                Fraction[] row = findRow(i);
                Fraction[] col = other.findCol(j);

                if (row.length != col.length) {
                    throw new RuntimeException("error");
                }

                Fraction r = new Fraction(0, 1);
                for (int k = 0; k < row.length; k++) {
                    r = r.plus(row[k].multiply(col[k]));
                }
                newOne[i][j] = r;
            }
        }

        return new M(newOne);
    }

    private Fraction[] findRow(int row) {
        return value[row];
    }

    private Fraction[] findCol(int col) {
        Fraction[] column = new Fraction[value.length];
        int i = 0;
        for (Fraction[] row : value) {
            column[i++] = row[col];
        }

        return column;
    }

    M simplify() {
        for (Fraction[] i : value) {
            for (Fraction j : i) {
                j.simplify();
            }
        }

        return this;
    }
}

class MatrixUtils {
    static Fraction[][] getInverseMatrix(Fraction[][] matrix) {
        if (matrix.length == 2) {
            Fraction part1 = matrix[0][0].multiply(matrix[1][1]).sub(matrix[0][1].multiply(matrix[1][0]));
            part1 = new Fraction(part1.denominator, part1.numerator);
            Fraction[][] inverseMatrix = new Fraction[2][2];
            inverseMatrix[0][0] = matrix[1][1].multiply(part1);
            inverseMatrix[0][1] = matrix[0][1].multiply(part1).multiply(new Fraction(-1, 1));
            inverseMatrix[1][0] = matrix[1][0].multiply(part1).multiply(new Fraction(-1, 1));
            inverseMatrix[1][1] = matrix[0][0].multiply(part1);
            return inverseMatrix;
        }

        Fraction[][] inverseMatrix = new Fraction[matrix.length][matrix[0].length];
        Fraction a = getMatrixResult(matrix);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if ((i + j) % 2 == 0) {
                    inverseMatrix[i][j] = getMatrixResult(getConfactor(matrix, i + 1, j + 1)).multiply(new Fraction(a.denominator, a.numerator));
                } else {
                    inverseMatrix[i][j] = getMatrixResult(getConfactor(matrix, i + 1, j + 1)).multiply(new Fraction(-a.denominator, a.numerator));
                }
            }
        }

        inverseMatrix = trans(inverseMatrix);

        return inverseMatrix;
    }

    private static Fraction getMatrixResult(Fraction[][] data) {
        if (data.length == 2) {
            return data[0][0].multiply(data[1][1]).sub(data[0][1].multiply(data[1][0]));
        }
        Fraction result = new Fraction(0, 1);
        int num = data.length;
        Fraction[] nums = new Fraction[num];
        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                Fraction newValue = getMatrixResult(getConfactor(data, 1, i + 1));
                nums[i] = data[0][i].multiply(newValue);
            } else {
                nums[i] = new Fraction(-data[0][i].numerator, data[0][i].denominator)
                        .multiply(getMatrixResult(getConfactor(data, 1, i + 1)));
            }
        }
        for (int i = 0; i < data.length; i++) {
            result = result.plus(nums[i]);
        }
        return result;
    }

    private static Fraction[][] getConfactor(Fraction[][] data, int h, int v) {
        int height = data.length;
        int length = data[0].length;
        Fraction[][] newData = new Fraction[height - 1][length - 1];
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

    private static Fraction[][] trans(Fraction[][] matrix) {
        Fraction[][] newMatrix = new Fraction[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                newMatrix[j][i] = matrix[i][j];
            }
        }
        return newMatrix;
    }
}

class Fraction {
    int numerator;
    int denominator;

    Fraction(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    void simplify() {
        int common = 1;
        for (int k = denominator; k > 0; k--) {
            if (numerator % k == 0 && denominator % k == 0) {
                common = k;
                break;
            }
        }

        if (common != 1) {
            numerator = numerator / common;
            denominator = denominator / common;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        return numerator == fraction.numerator && denominator == fraction.denominator;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    Fraction multiply(Fraction other) {
        return new Fraction(this.numerator * other.numerator, this.denominator * other.denominator);
    }

    Fraction plus(Fraction other) {
        int newThisNumerator = this.numerator * other.denominator;
        int newOtherNumerator = other.numerator * this.denominator;
        return new Fraction(newThisNumerator + newOtherNumerator, this.denominator * other.denominator);
    }

    Fraction sub(Fraction other) {
        int newThisNumerator = this.numerator * other.denominator;
        int newOtherNumerator = other.numerator * this.denominator;
        return new Fraction(newThisNumerator - newOtherNumerator, this.denominator * other.denominator);
    }
}
