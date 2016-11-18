import java.util.Scanner;

public class Matrix {
    private int amountStrings;
    private int amountColumns;
    public double[][] matrix;


    public Matrix() {
        amountStrings = 5;
        amountColumns = 5;
        matrix = new double[amountStrings][amountColumns];

    }

    public Matrix(int amountStrings, int amountColumns) {
        this.amountStrings = amountStrings;
        this.amountColumns = amountColumns;
        matrix = new double[amountStrings][amountColumns];
    }

    public Matrix(Matrix other) {
        this(other.amountStrings, other.amountColumns);
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountColumns; j++) {
                this.matrix[i][j] = other.matrix[i][j];
            }
        }
    }

    static public double[][] transpose(double[][] a) {
        double[][] transposeMatrix = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                transposeMatrix[j][i] = a[i][j];
            }
        }
        return transposeMatrix;
    }

    public static double[][] multiplyMatrix(double[][] a, double[][] b) {
        double[][] temp = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < a.length; k++) {
                    temp[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return temp;
    }

    public void input() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Your A matrix: ");
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountColumns; j++) {
                matrix[i][j] = sc.nextDouble();
            }
        }
        System.out.println();
    }

    public double[][] toSymmetric() {
        Matrix tempMatrix = new Matrix(this);
        tempMatrix.matrix = transpose(tempMatrix.matrix);
        matrix = multiplyMatrix(tempMatrix.matrix, matrix);
        return tempMatrix.matrix;
    }

    public void print() {
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountColumns; j++) {
                System.out.print(this.matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}