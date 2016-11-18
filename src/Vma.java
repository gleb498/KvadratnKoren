import java.util.Scanner;

public class Vma {
    private Matrix eq;
    private int amountStrings;
    double[] b;
    double[] x;
    double[] y;
    double[][] s;
    double[][] sTranspose;

    public Vma() {
        amountStrings = 5;
        eq = new Matrix(amountStrings, amountStrings);
        b = new double[amountStrings];
        x = new double[amountStrings];
        y = new double[amountStrings];
        s = new double[amountStrings][amountStrings];
        sTranspose = new double[amountStrings][amountStrings];
    }

    private void fillB(double[][] mtr) {
        Scanner sc = new Scanner(System.in);
        double[] temp = new double[amountStrings];
        System.out.println("b: ");
        for (int i = 0; i < amountStrings; i++) {
            temp[i] = sc.nextDouble();
        }
        for (int i = 0; i < mtr.length; i++) {
            for (int k = 0; k < mtr.length; k++) {
                b[i] += mtr[i][k] * temp[k];
            }
        }
    }

    private void buildSMatrix() {
        s[0][0] = Math.sqrt(eq.matrix[0][0]);
        for (int j = 1; j < amountStrings; j++) {
            s[0][j] = eq.matrix[0][j] / s[0][0];
        }
        for (int i = 1; i < amountStrings; i++) {
            double sum = 0;
            for (int k = 0; k < i; k++) {
                sum += s[k][i] * s[k][i];
            }
            s[i][i] = Math.sqrt(eq.matrix[i][i] - sum);
            for (int j = i + 1; j < amountStrings; j++) {
                double temp = 0;
                for (int k = 0; k < i; k++) {
                    temp += s[k][i] * s[k][j];
                }
                s[i][j] = (eq.matrix[i][j] - temp) / s[i][i];
            }
        }
    }

    private void makeSTranspose() {
        sTranspose = Matrix.transpose(s);
    }

    private void findY() {
        for (int i = 0; i < amountStrings; i++) {
            y[i] = b[i];
        }
        y[0] /= sTranspose[0][0];
        for (int i = 1; i < amountStrings; i++) {
            for (int j = i - 1; j >= 0; j--) {
                y[i] -= y[j] * sTranspose[i][j];
            }
            y[i] /= sTranspose[i][i];
        }

    }

    private void findX() {
        for (int i = 0; i < amountStrings; i++) {
            x[i] = y[i];
        }
        x[amountStrings - 1] /= s[amountStrings - 1][amountStrings - 1];
        for (int i = amountStrings - 2; i >= 0; i--) {
            for (int j = i + 1; j < amountStrings; j++) {
                x[i] -= x[j] * s[i][j];
            }
            x[i] /= s[i][i];
        }
    }

    private void checkEqualization() {
        double[] r = new double[amountStrings];
        for (int i = 0; i < amountStrings; i++) {
            for (int j = 0; j < amountStrings; j++) {
                r[i] += eq.matrix[i][j] * x[j];
            }
            r[i] -= b[i];
        }
        System.out.println("r = A * x - b: ");
        for (double item : r) {
            System.out.printf("%E", item);
            System.out.println();
        }
        double max = r[0];
        for (int i = 1; i < amountStrings; i++) {
            if (Math.abs(max) < Math.abs(r[i])) {
                max = r[i];
            }
        }
        System.out.println("||r|| = " + max);
    }

    private void printMatrix(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                System.out.printf("%-15f", a[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private void printArray(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%-15f", a[i]);
        }
        System.out.println();
        System.out.println();
    }

    private void determinant() {
        double det = 1;
        for (int i = 0; i < amountStrings; i++) {
            det *= s[i][i] * s[i][i];
        }
        System.out.print("Symmetric matrix determinant:  " + det);
    }

    public void solve() {
        eq.input();
        fillB(eq.toSymmetric());
        System.out.println("Matrix a: ");
        printMatrix(eq.matrix);
        System.out.println("Array b: ");
        printArray(b);
        buildSMatrix();
        makeSTranspose();
        printMatrix(s);
        printMatrix(sTranspose);
        findY();
        findX();
        System.out.println("X:");
        printArray(x);
        checkEqualization();
        determinant();
    }
}
