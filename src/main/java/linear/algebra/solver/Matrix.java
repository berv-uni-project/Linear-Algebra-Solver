package linear.algebra.solver;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Matrix Class
 * Data structure matrix
 */
public class Matrix {
    private final int N_MAX = 100;
    private final int StartPoint = 1;
    private int NBrsEff;
    private int NKolEff;
    private double[][] Mem = new double[N_MAX + 1][N_MAX + 1];
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor alternative
     *
     * @param row    Row
     * @param column Column
     */
    Matrix(int row, int column) {
        this.NBrsEff = row;
        this.NKolEff = column;
        for (int i = StartPoint; i <= row; i++) {
            for (int j = StartPoint; j <= column; j++) {
                this.Mem[i][j] = 0;
            }
        }
    }

    /**
     * Getting row number
     * @return row effective
     */
    public int getNBrsEff() {
        return this.NBrsEff;
    }

    public int getNKolEff() {
        return this.NKolEff;
    }

    public double getElement(int i, int j) {
        return this.Mem[i][j];
    }

    public double getElementDiagonal(int i) {
        return this.Mem[i][i];
    }

    public void setElement(int i, int j, double newVal) {
        this.Mem[i][j] = newVal;
    }

    private void readColumn(boolean isFromFile, Scanner input) {
        int N;
        if (!isFromFile) {
            do {
                System.out.println("Masukan jumlah baris :");
                N = input.nextInt();
            } while (N > 100 && N < 1);
        } else {
            N = input.nextInt();
        }
        this.NKolEff = N;
    }

    private void readRow(boolean isFromFile, Scanner input) {
        int M;
        if (!isFromFile) {
            do {
                System.out.println("Masukan jumlah kolom :");
                M = input.nextInt();
            } while (M > 100 && M < 1);
        } else {
            M = input.nextInt();
        }
        this.NBrsEff = M;
    }

    private void readMatrixByInput(Scanner input) {
        for (int i = StartPoint; i <= this.getNBrsEff(); i++) {
            for (int j = StartPoint; j <= this.getNKolEff(); j++) {
                this.Mem[i][j] = input.nextDouble();
            }
        }
    }

    // Prosedur I/O
    /**
     * I.S. sembarang. F.S. Matrix terisi sesuai dengan input user
     */
    public void readMatrix() {
        readColumn(true, this.scanner);
        readRow(true, this.scanner);
        readMatrixByInput(this.scanner);
    }

    /**
     * I.S. sembarang. F.S. nilai matriks tercetak pada layar
     */
    public void printMatrix() {
        for (int i = StartPoint; i <= this.getNBrsEff(); i++) {
            for (int j = StartPoint; j <= this.getNKolEff(); j++) {
                System.out.print(this.getElement(i, j));
                if (j == this.getNKolEff()) {
                    System.out.println();
                } else {
                    System.out.print(" ");
                }
            }
        }
    }

    /**
     * I.S. direktori yang tersimpan dalam value file_in berisi sebuah matriks. F.S.
     * Matrix terisi sesuai dengan isi direktori file_in
     */
    public void readMatrixFromFile(InputStream inputStream) {
        Scanner in = new Scanner(inputStream);
        readRow(true, in);
        readColumn(true, in);
        readMatrixByInput(in);
    }

    /**
     * I.S. direktori yang tersimpan dalam value file_in berisi sebuah matriks. F.S.
     * Matrix terisi sesuai dengan isi direktori file_in
     */
    public void readMatrixFromFile(String file_in) {
        try {
            File file = new File(file_in);
            Scanner in = new Scanner(new FileReader(file));
            readRow(true, in);
            readColumn(true, in);
            readMatrixByInput(in);
        } catch (FileNotFoundException exception) {
            System.out.println("Failed Read Data");
            System.out.println(exception.toString());
        }
    }

    /**
     * I.S. direktori yang tersimpan dalam value file_out kosong. F.S. direktori
     * file_out terisi dengan matriks
     */
    public void printMatrixFromFile(String file_out) {
        try {
            PrintWriter f = new PrintWriter(file_out);
            for (int i = StartPoint; i <= this.getNBrsEff(); i++) {
                for (int j = StartPoint; j <= this.getNKolEff(); j++) {
                    f.print(this.getElement(i, j));
                    if (j == this.getNKolEff()) {
                        f.println();
                    } else {
                        f.print(" ");
                    }
                }
            }
            f.close();
        } catch (FileNotFoundException exception) {
            System.out.println("File Not Found");
        }

    }

    /**
     * prekondisi : Matrix terdefinisi j masuk ke dalam NKolEff Mengembalikan indeks
     * baris nilai maksimum dari kolom ke-j yang dimulai dari baris ke i
     */
    public int maxAbsColumn(int j, int i) {

        int idxMax = i;
        for (int k = i; k <= this.getNBrsEff(); k++) {
            if (Math.abs(this.getElement(idxMax, j)) < Math.abs(this.getElement(k, j))) {
                idxMax = k;
            }
        }
        return idxMax;
    }

    /**
     * Menukar baris ke a dan baris ke b
     * @param a
     * @param b
     */
    public void switchRow(int a, int b) {
        // Kamus Lokal
        double tmp;

        // Algoritma
        for (int j = StartPoint; j <= this.getNKolEff(); j++) {
            tmp = this.getElement(a, j);
            this.setElement(a, j, this.getElement(b, j));
            this.setElement(b, j, tmp);
        }
    }

    /**
     * prekondisi matriks M terdefinisi, i dan j masuk dalam range NEFF
     * mengembalikan nilai true jika seluruh kolom j mulai dari baris i sampai akhir
     * sama dengan Nol dan false untuk sebaliknya
     */
    public boolean cekKolNol(int i, int j) {
        // Kamus Lokal
        boolean isKolNol;
        int k;

        // Algoritma
        isKolNol = true;
        k = i;
        while (k <= this.getNBrsEff() && isKolNol) {
            if (this.getElement(k, j) != 0.0) {
                isKolNol = false;
            } else {
                k++;
            }
        }
        return isKolNol;
    }

    /**
     * I.S. Matrix sembarang F.S. Matrix tereduksi menjadi matriks eselon dengan
     * eliminasi Gauss
     */
    public void matrixEselon() {

        // Kamus Lokal
        int iMax, idxBrs, idxKol;
        double currentElmt;

        // Algoritma
        for (int j = 1; j <= this.getNKolEff(); j++) {
            idxBrs = Math.min(j, this.getNBrsEff());

            if (!this.cekKolNol(idxBrs, j)) {
                // KASUS NORMAL
                for (int i = j; i <= this.getNBrsEff(); i++) {
                    if (i == j) {
                        iMax = this.maxAbsColumn(j, j);
                        this.switchRow(i, iMax);
                        if (this.getElementDiagonal(i) != 0.0) {
                            currentElmt = this.getElementDiagonal(i);
                            for (int k = 1; k <= this.getNKolEff(); k++) {
                                if (this.getElement(i, k) != 0.0) {
                                    this.setElement(i, k, (this.getElement(i, k)) / currentElmt);
                                }
                            }
                        }
                    } else if (this.getElementDiagonal(j) != 0.0) {
                        currentElmt = this.getElement(i, j);
                        for (int k = j; k <= this.getNKolEff(); k++) {

                            this.setElement(i, k, (this.getElement(i, k) - (currentElmt * this.getElement(j, k))));
                        }
                    }
                }
            } else {
                // KASUS TIDAK NORMAL
                idxKol = j;
                if (idxKol < this.getNKolEff()) {
                    idxKol++;
                }
                for (int i = 1; i <= this.getNBrsEff(); i++) {
                    idxBrs = Math.min(j, this.getNBrsEff());

                    iMax = this.maxAbsColumn(idxKol, idxBrs);
                    this.switchRow(idxBrs, iMax);

                    if (this.getElement(idxBrs, idxKol) != 0.0) {
                        currentElmt = this.getElement(idxBrs, idxKol);
                        for (int k = 1; k <= this.getNKolEff(); k++) {
                            if (this.getElement(idxBrs, k) != 0.0) {
                                this.setElement(idxBrs, k, (this.getElement(idxBrs, k) / currentElmt));
                            }
                        }
                    }
                    if (i > j && this.getElement(j, idxKol) != 0.0) {
                        currentElmt = this.getElement(i, idxKol);
                        for (int k = j; k <= this.getNKolEff(); k++) {

                            this.setElement(i, k, (this.getElement(i, k) - (currentElmt * this.getElement(j, k))));
                        }
                    }
                    // TulisMATRIKS(*M);
                    // printf("\n");
                }
            }
        }
    }

    /** Prosedur Gauss-Jordan
      * I.S. : Matrix berupa matriks eselon dan memiliki solusi
      * F.S. : Matrix berupa matriks eselon tereduksi
      */
    public void gaussJordan() {

        // Kamus Lokal
        int idxSatuUtama, j;
        boolean found;
        double multiplier;

        // Algoritma
        for (int i = this.getNBrsEff(); i > 1; i--) {
            found = false;
            j = 1;
            while (j < this.getNKolEff() && !found) {
                if (this.getElement(i, j) == 1.0) {
                    found = true;
                } else {
                    j++;
                }
            }

            if (found) {
                idxSatuUtama = j;
                for (int k = 1; k < i; k++) {
                    multiplier = this.getElement(k, idxSatuUtama);
                    for (int l = 1; l <= this.getNKolEff(); l++) {
                        this.setElement(k, l, this.getElement(k, l) - (multiplier * this.getElement(i, l)));
                    }
                }
            }  // Baris 000000 DO NOTHING!!

        }
    }

    /**
     * I.S : Matrix terdefinisi dan matriks adalah matriks eselon
     * F.S : Nilai dari matriks dimasukkan ke dalam array double
     */
    public double[] sulihMundur() {
        // Kamus Lokal
        int j;
        boolean found;
        double sum;
        double[] val_X = new double[N_MAX + 1];

        // Algoritma
        for (int i = this.getNBrsEff(); i >= 1; i--) {
            sum = 0;
            found = false;
            j = 1;
            while (j < this.getNKolEff() && !found) {
                if (this.getElement(i, j) == 1.0) {
                    found = true;
                } else {
                    j++;
                }
            }

            if (found) {
                sum = this.getElement(i, this.getNKolEff());
                for (int k = j + 1; k < this.getNKolEff(); k++) {
                    sum -= this.getElement(i, k) * val_X[k];
                }
                val_X[j] = sum;
            }  // Baris 000000 DO NOTHING!!

        }
        return (val_X);
    }

    /**
     * Predikat untuk determinasi jenis SPL
     * Mengembalikan nilai true apabila baris ke-i pada matriks merupakan baris nol
     */
    public boolean isBarisNol(int i) {
        int j = 1;
        while (j < this.getNKolEff() && this.getElement(i, j) == 0.0) {
            j++;
        } // i == this.getNKolEff || i!=0.0
        return this.getElement(i, j) == 0.0;
    }

    /**
     * Menghitung kemunculan baris nol pada matriks
     */
    public int countBarisNol() {
        int cnt = 0;
        for (int i = 1; i <= this.getNBrsEff(); i++) {
            if (this.isBarisNol(i)) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Menghasilkan true apabila SPL memiliki solusi banyak, yaitu jika
     * jumlahVariabel &lt; jumlahPersamaan
     */
    public boolean isManySolution() {

        int jumlahBarisNol = this.countBarisNol();
        int jumlahVariabel = this.getNKolEff() - 1;
        return this.getNBrsEff() - jumlahBarisNol < jumlahVariabel;
    }

    /**
     * Menghasilkan true apabila baris ke-i merupakan baris "siluman", yaitu baris
     * yang 000000X
     */
    public boolean isBarisSiluman(int i) {
        int j = 1;
        while (j < this.getNKolEff() && this.getElement(i, j) == 0.0) {
            j++;
        } // i == this.getNKolEff || i!=0.0
        return j == this.getNKolEff() && this.getElement(i, j) != 0.0;
    }

    /**
     * Menghasilkan nilai true apabila SPL tidak memiliki solusi, yaitu ketika SPL
     * memiliki baris "siluman"
     */
    public boolean isNoSolution() {
        int i = 1;
        while (i < this.getNBrsEff() && !(this.isBarisSiluman(i))) {
            i++;
        } // i == NBrsEff || isBarisSiluman
        return this.isBarisSiluman(i);
    }

    /**
     * Menghasilkan nilai-nilai variabel-variabel dalam bentuk parametrik
     */
    public void printParametrik() {
        // Kamus Lokal
        boolean[] stat = new boolean[N_MAX + 1];
        String[] ans = new String[N_MAX + 1];
        // Algoritma
        Arrays.fill(stat, false);
        for (char c = 'a'; c <= 'z'; c++) {
            ans[c - 'a' + 1] = Character.toString(c);
        }
        int startingI = this.getNBrsEff();
        while (startingI > 1 && isBarisNol(startingI)) {
            startingI--;
        } // startingI == 1 || !isBarisNol(startingI)
        StringBuilder tmp = new StringBuilder();
        for (int i = startingI; i >= 1; i--) {
            // Cari indeks untuk set nilai
            int idxSet = 1;
            while ((this.getElement(i, idxSet) == 0 || stat[idxSet]) && idxSet < this.getNKolEff() - 1) {
                idxSet++;
            } // getElement(idxSet)!=0 && stat[idxSet]==false
            stat[idxSet] = true;

            // Hitung nilai string yg akan diset
            tmp.append(this.getElement(i, this.getNKolEff()));
            for (int j = this.getNKolEff() - 1; j >= 1; j--) {
                if (j != idxSet) {
                    char operator;
                    double multiplier;
                    String nilai = ans[j];

                    multiplier = this.getElement(i, j);
                    if (nilai.length() != 1) {
                        nilai = "(" + nilai + ")";
                    }

                    if (multiplier == 0) {
                        continue;
                    } else if (multiplier < 0.0) {
                        operator = '+';
                    } else {
                        operator = '-';
                    }

                    stat[j] = true;

                    if (Math.abs(multiplier) == 1.0) {
                        tmp.append(String.format(" %c  %s", operator, nilai));
                    } else {
                        tmp.append(String.format(" %c %f * %s", operator, multiplier, nilai));
                    }
                }
            }

            if (this.getElement(i, idxSet) != 1.0) {
                tmp.append(String.format("(%s)/%f", tmp.toString(), getElement(i, idxSet)));
            }
            ans[idxSet] = tmp.toString();
            tmp.setLength(0);
        }
        for (int j = 1; j < this.getNKolEff(); j++) {
            System.out.println(String.format("X %d = %s",j, ans[j]));
        }
    }
}
