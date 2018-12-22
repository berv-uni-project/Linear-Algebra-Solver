package linear.algebra.solver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Interface {
    public static void main(String args[]) throws FileNotFoundException {
        boolean finish;
        finish = false;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("---------------------------------------------------");
            System.out.println("Selamat Datang di Program Matriks dan Interpolasi.");
            System.out.println("---------------------------------------------------");
            System.out.println("Apa yang anda ingin lakukan?");
            System.out.println("1. Persoalan Gauss dan Gauss Jordan.");
            System.out.println("2. Persoalan Intepolasi.");
            System.out.println("3. Keluar");
            System.out.print("Pilihan : ");
            int pilihan = in.nextInt();
            System.out.println();
            if (pilihan == 1) {
                Matriks Mat = new Matriks(0, 0);
                System.out.println("Berikut ini beberapa persoalan sain yang diselesaikan dengan matriks.");
                System.out.println("Apa yang anda inginkan?");
                System.out.println("1. Persoalan bebas.");
                System.out.println("2. Solusi soal 1.");
                System.out.println("3. Solusi soal 2.");
                System.out.print("Pilihan anda : ");
                int pilihan1;
                pilihan1 = in.nextInt();
                System.out.println();

                if (pilihan1 == 1) {
                    System.out.println("1. Masukan dari papan ketik.");
                    System.out.println("2. Masukan dari file text.");
                    System.out.print("Pilihan : ");
                    int pilihanmat = in.nextInt();

                    if (pilihanmat == 1) {
                        Mat.BacaMATRIKS();
                    } else if (pilihanmat == 2) {
                        Mat.BacaMATRIKSfromFile("../test/soalA.txt");
                    } else {
                        System.out.println("Pilihan salah!");
                    }

                    if (pilihanmat == 1 || pilihanmat == 2) {
                        System.out.println();
                        System.out.println("1. Pakai metode Gauss");
                        System.out.println("2. Pakai metode Gauss-Jordan");
                        System.out.print("Pilihan : ");
                        int pilihanGauss = in.nextInt();
                        System.out.println();

                        if (pilihanGauss == 1) {
                            Mat.MatriksEselon();
                            Mat.PrintMATRIKS();
                            System.out.println("Solusinya adalah :");
                            if (Mat.IsNoSolution()) {
                                System.out.println("Tidak ada solusi.");

                                PrintWriter f = new PrintWriter("../test/jawabanA.txt");
                                f.print("Tidak ada solusi.");
                                f.close();
                            } else if (Mat.IsManySolution()) {
                                System.out.println("Solusi banyak.");
                                System.out.println("Solusi dalam bentuk parametriknya adalah :");
                                Mat.PrintParametrik();

                                PrintWriter f = new PrintWriter("../test/jawabanA.txt");
                                f.print("Solusi banyak.");
                                f.close();
                            } else {
                                Mat.PrintMATRIKS();

                                double[] ans = new double[101];
                                ans = Mat.Sulih_Mundur();
                                PrintSolution(ans, Mat.GetNKolEff() - 1);

                                if (pilihanmat == 2)
                                    PrintSolutionToFile(ans, Mat.GetNKolEff() - 1, "../test/jawabanA.txt");
                            }
                        } else if (pilihanGauss == 2) {
                            Mat.MatriksEselon();
                            Mat.Gauss_Jordan();

                            System.out.println("Solusinya adalah :");
                            if (Mat.IsNoSolution()) {
                                System.out.println("Tidak ada solusi.");

                                PrintWriter f = new PrintWriter("../test/jawabanA.txt");
                                f.print("Tidak ada solusi.");
                                f.close();
                            } else if (Mat.IsManySolution()) {
                                System.out.println("Solusi banyak.");
                                Mat.PrintMATRIKS();

                                System.out.println("Solusi dalam bentuk parametriknya adalah :");
                                Mat.PrintParametrik();

                                PrintWriter f = new PrintWriter("../test/jawabanA.txt");
                                f.print("Solusi banyak.");
                                f.close();
                            } else {
                                Mat.PrintMATRIKS();

                                double[] ans = new double[101];
                                ans = Mat.Sulih_Mundur();
                                PrintSolution(ans, Mat.GetNKolEff() - 1);

                                if (pilihanmat == 2)
                                    PrintSolutionToFile(ans, Mat.GetNKolEff() - 1, "../test/jawabanA.txt");
                            }
                        } else {
                            System.out.println("Pilihan salah!");
                        }
                    }

                } else if (pilihan1 == 2) {
                    Mat.BacaMATRIKSfromFile("../test/soal1.txt");
                    Mat.MatriksEselon();
                    Mat.Gauss_Jordan();
                    Mat.PrintMATRIKS();
                    Mat.PrintMATRIKStoFile("../test/jawaban1.txt");

                    System.out.println("Solusinya adalah :");
                    double[] ans = new double[101];
                    ans = Mat.Sulih_Mundur();
                    PrintSolution(ans, Mat.GetNKolEff() - 1);
                    PrintSolutionToFile(ans, Mat.GetNKolEff() - 1, "../test/jawaban1.txt");
                } else if (pilihan1 == 3) {
                    Mat.BacaMATRIKSfromFile("../test/soal2.txt");
                    Mat.MatriksEselon();
                    Mat.Gauss_Jordan();
                    Mat.PrintMATRIKS();
                    Mat.PrintMATRIKStoFile("../test/jawaban2.txt");

                    System.out.println("Solusinya adalah :");
                    double[] ans = new double[101];
                    ans = Mat.Sulih_Mundur();
                    PrintSolution(ans, Mat.GetNKolEff() - 1);
                    PrintSolutionToFile(ans, Mat.GetNKolEff() - 1, "../test/jawaban2.txt");
                } else {
                    System.out.println("Pilihan salah");
                }
                System.out.println();
            } else if (pilihan == 2) {
                System.out.println("Berikut ini beberapa persoalan sain yang diselesaikan dengan .");
                System.out.println("Apa yang anda inginkan?");
                System.out.println("1. Solusi soal 3.");
                System.out.println("2. Solusi soal 4a.");
                System.out.println("3. Solusi soal 4b.");
                System.out.println("4. Solusi soal 5.");
                int pilihan2 = in.nextInt();
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

                    Matriks MatInterpolasi = new Matriks(N, N + 1);
                    for (int i = 1; i <= N; i++) {
                        double now = 1;
                        for (int j = 1; j <= N; j++) {
                            MatInterpolasi.SetElmt(i, j, now);
                            now = now * TabPoint[i].GetX();
                        }
                        MatInterpolasi.SetElmt(i, N + 1, TabPoint[i].GetY());
                    }

                    MatInterpolasi.MatriksEselon();
                    MatInterpolasi.Gauss_Jordan();
                    //MatInterpolasi.PrintMATRIKS("aaa.txt");

                    for (int i = N; i >= 1; i--) {
                        a[i - 1] = MatInterpolasi.GetElmt(i, N + 1);
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
                    Q = in.nextInt();
                    for (int i = 1; i <= Q; i++) {
                        double x;
                        System.out.print("x = ");
                        x = in.nextDouble();
                        System.out.print("f(x) = ");
                        System.out.println(fx(a, N, x));
                        f.print("x = ");
                        f.print(x);
                        f.print(" -> f(x) = ");
                        f.println(fx(a, N, x));
                    }
                    System.out.println();
                    System.out.println(FXtoStr(a, N));
                    f.println(FXtoStr(a, N));
                    f.close();
                }

            } else if (pilihan == 3) {
                //Jika telah memilih angka 3, program selesai
                finish = true;
                System.out.println("Terima kasih telah menggunakan program ini.");
                System.out.println();
            } else {
                //Jika input selain 1-3
                System.out.println("Perintah salah. Ulangi!");
                System.out.println();
            }
        } while (!finish);
    }

    public static void PrintSolution(double[] val_X, int N) {
        for (int i = 1; i <= N; i++) {
            System.out.println("X" + i + " = " + val_X[i]);
        }
    }

    public static void PrintSolutionToFile(double[] val_X, int N, String file_out) throws FileNotFoundException {
        PrintWriter f = new PrintWriter(file_out);
        for (int i = 1; i <= N; i++) {
            f.print("X");
            f.print(i);
            f.print(" = ");
            f.println(val_X[i]);
        }
        f.close();
    }

    public static double fx(double[] a, int n, double x) {
        double res = 0.0;
        double pow = 1.0;
        for (int i = 0; i < n; i++) {
            res = res + pow * a[i];
            pow = pow * x;
        }
        return res;
    }

    public static String FXtoStr(double[] a, int n) {
        String ans = "f(x) =";
        for (int i = 0; i < n; i++) {
            if (a[i] != 0.0) {
                if (i == 0) ans = ans + " " + Double.toString(a[i]);
                else ans = ans + " + " + Double.toString(a[i]) + "x^" + String.valueOf(i);
            }
        }
        return ans;
    }
}
