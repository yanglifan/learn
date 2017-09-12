package yanglifan.learn.algorithm.foobar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1. Find Matrix Q [done]
 * 2. Find Matrix R [done]
 * 3. Calculate I - Q
 * 4. Calculate (I - Q) ^ -1 (F)
 * 5. Calculate FR
 * 6. Simplify the final Matrix
 *
 * @author Yang Lifan
 */
public class Answer4 {
    public static int[] answer(int[][] m) {
        int width = m[0].length;
        int[] nonAbsorbingMatrixIndices = findSubMatrixIndices(m);

        adjustMatrix(m, nonAbsorbingMatrixIndices);

        Fraction[][] fractionMatrix = toFractionMatrix(m);
        Fraction[][] matrixR = findSubMatrix(
                fractionMatrix,
                nonAbsorbingMatrixIndices,
                0,
                width - nonAbsorbingMatrixIndices.length - 1
        );

        Fraction[][] matrixQ = findSubMatrix(
                fractionMatrix,
                nonAbsorbingMatrixIndices,
                width - nonAbsorbingMatrixIndices.length,
                width - 1
        );

        new Matrix(matrixQ).simplify();

        Fraction[][] identityMatrix = createIdentityMatrix(matrixQ.length, matrixQ[0].length);
        Matrix iSubQ = new Matrix(identityMatrix).sub(new Matrix(matrixQ));
        Fraction[][] matrixF = MatrixUtils.getInverseMatrix(iSubQ.value);

        Matrix mF = new Matrix(matrixF);
        mF.simplify();

        Matrix result = mF.multiply(new Matrix(matrixR));
        result.simplify();

        Fraction[] finalFractionArray = result.value[0];
        int denominator = 0;
        for (Fraction f : finalFractionArray) {
            if (denominator < f.denominator) {
                denominator = f.denominator;
            }
        }

        int[] finalResult = new int[finalFractionArray.length + 1];
        int i = 0;
        for (Fraction f : finalFractionArray) {
            if (f.denominator != denominator) {
                f.numerator = f.numerator * (denominator / f.denominator);
                f.denominator = denominator;
            }
            finalResult[i++] = f.numerator;

        }

        finalResult[finalResult.length - 1] = denominator;

        return finalResult;
    }

    private static void adjustMatrix(int[][] m, int[] nonAbsorbingMatrixIndices) {
        for (int index : nonAbsorbingMatrixIndices) {
            int[] newArray = new int[m[index].length];
            for (int i = 0; i < m[index].length; i++) {
                if (m[index][i] == 0) {
                    newArray[i] = m[index][i];
                    continue;
                }

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

    static Fraction[][] createIdentityMatrix(int length, int width) {
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
        return identityMatrix;
    }

    static Fraction[][] toFractionMatrix(int[][] matrix) {
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

    static Fraction[][] findSubMatrix(Fraction[][] matrix, int[] nonAbsorbingMatrixIndices, int start, int end) {
        System.out.println("[findSubMatrix] start: " + start + ", end: " + end);
        Fraction[][] subMatrix = new Fraction[nonAbsorbingMatrixIndices.length][end - start];
        int i = 0;
        for (int index : nonAbsorbingMatrixIndices) {
            subMatrix[i++] = Arrays.copyOfRange(matrix[index], start, end + 1);
        }
        return subMatrix;
    }
}

class Matrix {
    Fraction[][] value;

    public Matrix(Fraction[][] value) {
        this.value = value;
    }

    Matrix sub(Matrix m) {
        Fraction[][] resultValue = new Fraction[m.value.length][m.value[0].length];
        Matrix result = new Matrix(resultValue);
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                result.value[i][j] = value[i][j].sub(m.value[i][j]);
            }
        }
        return result;
    }

    Matrix multiply(Matrix other) {
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

        return new Matrix(newOne);
    }

    Fraction[] findRow(int row) {
        return value[row];
    }

    Fraction[] findCol(int col) {
        Fraction[] column = new Fraction[value.length];
        int i = 0;
        for (Fraction[] row : value) {
            column[i++] = row[col];
        }

        return column;
    }

    void simplify() {
        for (int i = 0; i < value.length; i++) {
            for (int j = 0; j < value[i].length; j++) {
                value[i][j].simplify();
            }
        }
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

    public void simplify() {
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
    public int hashCode() {
        int result = numerator;
        result = 31 * result + denominator;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraction fraction = (Fraction) o;

        if (numerator != fraction.numerator) return false;
        return denominator == fraction.denominator;
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
