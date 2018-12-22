package linear.algebra.solver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {

    Scanner scanner;
    Matrix matrix;

    public Menu() {
        scanner = new Scanner(System.in);
        matrix = new Matrix(0, 0);
    }

    public int showMainMenu() {
        System.out.println("---------------------------------------------------");
        System.out.println("Selamat Datang di Program Matrix dan Interpolasi.");
        System.out.println("---------------------------------------------------");
        System.out.println("Apa yang anda ingin lakukan?");
        System.out.println("1. Persoalan Gauss dan Gauss Jordan.");
        System.out.println("2. Persoalan Intepolasi.");
        System.out.println("3. Keluar");
        System.out.print("Pilihan : ");
        int options = scanner.nextInt();
        System.out.println();
        return options;
    }

    private void writeFileAnswer(String output) {
        try {
            PrintWriter f = new PrintWriter("../test/jawabanA.txt");
            f.print(output);
            f.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Can't Write File. Not Found.");
        }
    }

    private void printResult(String path) {
        matrix.printMatrix();
        double[] ans;
        ans = matrix.sulihMundur();
        printSolution(ans, matrix.getNKolEff() - 1);
        printSolutionToFile(ans, matrix.getNKolEff() - 1, path);
    }

    private void processGauss(int option) {
        switch (option) {
            case 1:
                matrix.matrixEselon();
                matrix.printMatrix();
                System.out.println("Solusinya adalah :");
                if (matrix.isNoSolution()) {
                    System.out.println("Tidak ada solusi.");
                    writeFileAnswer("Tidak ada solusi");
                } else if (matrix.isManySolution()) {
                    System.out.println("Solusi banyak.");
                    System.out.println("Solusi dalam bentuk parametriknya adalah :");
                    matrix.printParametrik();
                    writeFileAnswer("Solusi Banyak.");
                } else {
                    printResult("../test/jawabanA.txt");
                }
                break;
            case 2:
                matrix.matrixEselon();
                matrix.gaussJordan();

                System.out.println("Solusinya adalah :");
                if (matrix.isNoSolution()) {
                    System.out.println("Tidak ada solusi.");
                    writeFileAnswer("Tidak ada solusi");
                } else if (matrix.isManySolution()) {
                    System.out.println("Solusi banyak.");
                    matrix.printMatrix();
                    System.out.println("Solusi dalam bentuk parametriknya adalah :");
                    matrix.printParametrik();
                    writeFileAnswer("Solusi Banyak.");
                } else {
                    printResult("../test/jawabanA.txt");
                }
                break;
            default:
                System.out.println("Pilihan salah!");
                break;
        }

    }

    private void showSelectionMethodGauss() {
        System.out.println();
        System.out.println("1. Pakai metode Gauss");
        System.out.println("2. Pakai metode Gauss-Jordan");
        System.out.print("Pilihan : ");
        int option = scanner.nextInt();
        System.out.println();
        processGauss(option);
    }

    private void processSelectionMatrix(int option) {
        switch (option) {
            case 1:
                matrix.readMatrix();
                showSelectionMethodGauss();
                break;
            case 2:
                InputStream resource = ClassLoader.getSystemClassLoader().getResourceAsStream("soalA.txt");
                matrix.readMatrixFromFile(resource);
                showSelectionMethodGauss();
                break;
            default:
                System.out.println("Pilihan salah!");
                break;
        }
    }

    private void showOptionInputMatrix() {
        System.out.println("1. Masukan dari papan ketik.");
        System.out.println("2. Masukan dari file text.");
        System.out.print("Pilihan : ");
        int option = scanner.nextInt();
        processSelectionMatrix(option);
    }

    private void showGaussMenuByOptions(int option) {
        switch (option) {
            case 1:
                showOptionInputMatrix();
                break;
            case 2:
                matrix.readMatrixFromFile("../test/soal1.txt");
                matrix.matrixEselon();
                matrix.gaussJordan();
                matrix.printMatrix();
                matrix.printMatrixFromFile("../test/jawaban1.txt");
                System.out.println("Solusinya adalah :");
                printResult("../test/jawaban1.txt");
                break;
            case 3:
                matrix.readMatrixFromFile("../test/soal2.txt");
                matrix.matrixEselon();
                matrix.gaussJordan();
                matrix.printMatrix();
                matrix.printMatrixFromFile("../test/jawaban2.txt");
                System.out.println("Solusinya adalah :");
                printResult("../test/jawaban2.txt");
                break;
            default:
                System.out.println("Pilihan salah");
                break;
        }
        System.out.println();
    }

    private void showGaussProblemMenu() {
        System.out.println("Berikut ini beberapa persoalan sain yang diselesaikan dengan matriks.");
        System.out.println("Apa yang anda inginkan?");
        System.out.println("1. Persoalan bebas.");
        System.out.println("2. Solusi soal 1.");
        System.out.println("3. Solusi soal 2.");
        System.out.print("Pilihan anda : ");
        int option = scanner.nextInt();
        System.out.println();
        showGaussMenuByOptions(option);
    }

    private void showInterpolationMenu() {
        System.out.println("Berikut ini beberapa persoalan sain yang diselesaikan dengan .");
        System.out.println("Apa yang anda inginkan?");
        System.out.println("1. Solusi soal 3.");
        System.out.println("2. Solusi soal 4a.");
        System.out.println("3. Solusi soal 4b.");
        System.out.println("4. Solusi soal 5.");
        int pilihan2 = scanner.nextInt();
        System.out.println();

        String file_in = "", file_out = "";
        if (pilihan2 == 1) {
            file_in = "../test/soal3.txt";
            file_out = "../test/jawaban3.txt";
        } else if (pilihan2 == 2) {
            file_in = "../test/soal4a.txt";
            file_out = "../test/jawaban4a.txt";

        } else if (pilihan2 == 3) {
            file_in = "../test/soal4b.txt";
            file_out = "../test/jawaban4b.txt";

        } else if (pilihan2 == 4) {
            file_in = "../test/soal5.txt";
            file_out = "../test/jawaban5.txt";
        } else {
            System.out.println("Pilihan salah.");
            System.out.println();
        }

        if (pilihan2 == 1 || pilihan2 == 2 || pilihan2 == 3 || pilihan2 == 4) {
            try {
                final int NMAX = 100;
                Point[] TabPoint = new Point[NMAX + 1];
                double[] a = new double[NMAX + 1];

                Scanner inFile = new Scanner(new FileReader(file_in));
                PrintWriter f = new PrintWriter(file_out);

                int N;
                N = inFile.nextInt();
                for (int i = 1; i <= N; i++) {
                    double x, y;
                    x = inFile.nextDouble();
                    y = inFile.nextDouble();
                    TabPoint[i] = new Point(x, y);
                }

                Matrix MatInterpolasi = new Matrix(N, N + 1);
                for (int i = 1; i <= N; i++) {
                    double now = 1;
                    for (int j = 1; j <= N; j++) {
                        MatInterpolasi.setElement(i, j, now);
                        now = now * TabPoint[i].getX();
                    }
                    MatInterpolasi.setElement(i, N + 1, TabPoint[i].getY());
                }

                MatInterpolasi.matrixEselon();
                MatInterpolasi.gaussJordan();
                //MatInterpolasi.printMatrix("aaa.txt");

                for (int i = N; i >= 1; i--) {
                    a[i - 1] = MatInterpolasi.getElement(i, N + 1);
                }
					/*
					for(int i=0; i<N; i++)
					{
						f.print(a[i]);
						f.println();
					}
					*/
                System.out.print("Masukan jumlah query : ");
                int Q;
                Q = scanner.nextInt();
                for (int i = 1; i <= Q; i++) {
                    double x;
                    System.out.print("x = ");
                    x = scanner.nextDouble();
                    System.out.print("f(x) = ");
                    System.out.println(fx(a, N, x));
                    f.print("x = ");
                    f.print(x);
                    f.print(" -> f(x) = ");
                    f.println(fx(a, N, x));
                }
                System.out.println();
                System.out.println(fXtoStr(a, N));
                f.println(fXtoStr(a, N));
                f.close();
            } catch (FileNotFoundException exception) {
                System.out.println("Can't read file");
            }
        }
    }

    public boolean showSubmenu(int option) {
        switch (option) {
            case 1:
                showGaussProblemMenu();
                return false;
            case 2:
                showInterpolationMenu();
                return false;
            case 3:
                System.out.println("Terima kasih telah menggunakan program ini.");
                System.out.println();
                return true;
            default:
                System.out.println("Perintah salah. Ulangi!");
                System.out.println();
                return false;
        }
    }

    private void printSolution(double[] val_X, int N) {
        for (int i = 1; i <= N; i++) {
            System.out.println("X" + i + " = " + val_X[i]);
        }
    }

    private void printSolutionToFile(double[] val_X, int N, String file_out) {
        try {
            PrintWriter f = new PrintWriter(file_out);
            for (int i = 1; i <= N; i++) {
                f.print("X");
                f.print(i);
                f.print(" = ");
                f.println(val_X[i]);
            }
            f.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Can't write into file");
        }
    }

    private double fx(double[] a, int n, double x) {
        double res = 0.0;
        double pow = 1.0;
        for (int i = 0; i < n; i++) {
            res = res + pow * a[i];
            pow = pow * x;
        }
        return res;
    }

    private String fXtoStr(double[] a, int n) {
        StringBuilder answer = new StringBuilder();
        answer.append("f(x) =");
        for (int i = 0; i < n; i++) {
            if (a[i] != 0.0) {
                if (i == 0) answer.append(String.format(" %f", a[i]));
                else answer.append(String.format(" + %fx^%d", a[i], i));
            }
        }
        return answer.toString();
    }
}
