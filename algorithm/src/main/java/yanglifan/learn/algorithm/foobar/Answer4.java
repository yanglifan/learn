package yanglifan.learn.algorithm.foobar;

/**
 * @author Yang Lifan
 */
public class Answer4 {
    public static void main(String[] args) {
        Fraction[][] inversed = MatrixUtils.getInverseMatrix(new Fraction[][]{
                new Fraction[]{new Fraction(1, 1), new Fraction(-1, 2)},
                new Fraction[]{new Fraction(-4, 9), new Fraction(1, 1)}
        });


        for (Fraction[] row : inversed) {
            for (Fraction f : row) {
                System.out.print(f + "\t");
            }
            System.out.println();
        }
    }
}

class MatrixUtils {
    static Fraction[][] getInverseMatrix(Fraction[][] matrix) {
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
