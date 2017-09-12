package yanglifan.learn.algorithm.foobar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1. Find Matrix R
 * 2. Find Matrix Q
 * 3. Calculate I - Q
 * 4. Calculate (I - Q) ^ -1 (F)
 * 5. Calculate FR
 * 6. Simplify the final Matrix
 *
 * @author Yang Lifan
 */
public class Answer4 {
    public static int[] answer(int[][] m) {
        int[] nonAbsorbingMatrixIndices = findSubMatrixIndices(m);
        return null;
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

    static int[][] findSubMatrix(int[][] matrix, int[] nonAbsorbingMatrixIndices, int start, int end) {
        int[][] subMatrix = new int[nonAbsorbingMatrixIndices.length][end - start];
        int i = 0;
        for (int index : nonAbsorbingMatrixIndices) {
            subMatrix[i++] = Arrays.copyOfRange(matrix[index], start, end + 1);
        }
        return subMatrix;
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
