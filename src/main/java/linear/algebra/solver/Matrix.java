package linear.algebra.solver;
/*
	ADT Matrix
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Matrix {
    private final int N_MAX = 100;
    private final int StartPoint = 1;
    private int NBrsEff;
    private int NKolEff;
    private double[][] Mem = new double[N_MAX + 1][N_MAX + 1];

    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructor Default
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

    // Selektor
    public int GetNBrsEff() {
        return this.NBrsEff;
    }

    public int GetNKolEff() {
        return this.NKolEff;
    }

    public double GetElmt(int i, int j) {
        return this.Mem[i][j];
    }

    public double GetElmtDiagonal(int i) {
        return this.Mem[i][i];
    }

    // Setter
    public void SetNBrsEff(int newN) {
        this.NBrsEff = newN;
    }

    public void SetNKolEff(int newM) {
        this.NKolEff = newM;
    }

    public void SetElement(int i, int j, double newVal) {
        this.Mem[i][j] = newVal;
    }

    private void ReadColumn(boolean isFromFile, Scanner input) {
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

    private void ReadRow(boolean isFromFile, Scanner input) {
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

    private void ReadMatrixByInput(Scanner input) {
        for (int i = StartPoint; i <= this.GetNBrsEff(); i++) {
            for (int j = StartPoint; j <= this.GetNKolEff(); j++) {
                this.Mem[i][j] = input.nextDouble();
            }
        }
    }

    // Prosedur I/O
    public void ReadMatrix() {
		/* I.S. sembarang
		   F.S. Matrix terisi sesuai dengan input user */
        ReadColumn(true, this.scanner);
        ReadRow(true, this.scanner);
        ReadMatrixByInput(this.scanner);
    }

    public void PrintMatrix() {
		/* I.S. sembarang
		   F.S. nilai matriks tercetak pada layar */
        for (int i = StartPoint; i <= this.GetNBrsEff(); i++) {
            for (int j = StartPoint; j <= this.GetNKolEff(); j++) {
                System.out.print(this.GetElmt(i, j));
                if (j == this.GetNKolEff()) System.out.println();
                else System.out.print(" ");
            }
        }
    }

    public void ReadMatrixFromFile(String file_in) {
		/* I.S. direktori yang tersimpan dalam value file_in berisi sebuah matriks 
		   F.S. Matrix terisi sesuai dengan isi direktori file_in */
        try {
            File file = new File(file_in);
            Scanner in = new Scanner(new FileReader(file));
            ReadRow(false, in);
            ReadColumn(false, in);
            ReadMatrixByInput(in);
        } catch (FileNotFoundException exception) {
            System.out.println("Failed Read Data");
        }

    }

    public void PrintMatrixFromFile(String file_out) {
		/* I.S. direktori yang tersimpan dalam value file_out kosong 
		   F.S. direktori file_out terisi dengan matriks */
        try {
            PrintWriter f = new PrintWriter(file_out);
            for (int i = StartPoint; i <= this.GetNBrsEff(); i++) {
                for (int j = StartPoint; j <= this.GetNKolEff(); j++) {
                    f.print(this.GetElmt(i, j));
                    if (j == this.GetNKolEff()) f.println();
                    else f.print(" ");
                }
            }
            f.close();
        } catch (FileNotFoundException exception) {
            System.out.println("File Not Found");
        }

    }

    // Prosedur Operasi Baris Elementer
    public int MaxAbsColumn(int j, int i) {
        /* prekondisi : Matrix terdefinisi j masuk ke dalam NKolEff
         * Mengembalikan indeks baris nilai maksimum dari kolom ke-j yang dimulai
         * dari baris ke i*/

        int idxMax = i;
        for (int k = i; k <= this.GetNBrsEff(); k++) {
            if (Math.abs(this.GetElmt(idxMax, j)) < Math.abs(this.GetElmt(k, j))) {
                idxMax = k;
            }
        }
        return idxMax;
    }

    public void SwitchRow(int a, int b) {
        // Menukar baris ke a dan baris ke b
        // Kamus Lokal
        double tmp;

        // Algoritma
        for (int j = StartPoint; j <= this.GetNKolEff(); j++) {
            tmp = this.GetElmt(a, j);
            this.SetElement(a, j, this.GetElmt(b, j));
            this.SetElement(b, j, tmp);
        }
    }

    public boolean CekKolNol(int i, int j) {
        /* prekondisi matriks M terdefinisi, i dan j masuk dalam range NEFF
         * mengembalikan nilai true jika seluruh kolom j mulai dari baris i sampai
         * akhir sama dengan Nol dan false untuk sebaliknya*/
        // Kamus Lokal
        boolean isKolNol;
        int k;

        // Algoritma
        isKolNol = true;
        k = i;
        while (k <= this.GetNBrsEff() && isKolNol) {
            if (this.GetElmt(k, j) != 0.0) {
                isKolNol = false;
            } else {
                k++;
            }
        }
        return isKolNol;
    }

    public void MatriksEselon() {
		/* I.S. Matrix sembarang
		   F.S. Matrix tereduksi menjadi matriks eselon dengan eliminasi Gauss */

        // Kamus Lokal
        int iMax, idxBrs, idxKol;
        double currentElmt;

        // Algoritma
        for (int j = 1; j <= this.GetNKolEff(); j++) {
            if (j <= this.GetNBrsEff()) {
                idxBrs = j;
            } else {
                idxBrs = this.GetNBrsEff();
            }

            if (!this.CekKolNol(idxBrs, j)) {
                // KASUS NORMAL
                for (int i = j; i <= this.GetNBrsEff(); i++) {
                    if (i == j) {
                        iMax = this.MaxAbsColumn(j, j);
                        this.SwitchRow(i, iMax);
                        if (this.GetElmtDiagonal(i) != 0.0) {
                            currentElmt = this.GetElmtDiagonal(i);
                            for (int k = 1; k <= this.GetNKolEff(); k++) {
                                if (this.GetElmt(i, k) != 0.0) {
                                    this.SetElement(i, k, (this.GetElmt(i, k)) / currentElmt);
                                }
                            }
                        }
                    } else if (this.GetElmtDiagonal(j) != 0.0) {
                        currentElmt = this.GetElmt(i, j);
                        for (int k = j; k <= this.GetNKolEff(); k++) {

                            this.SetElement(i, k, (this.GetElmt(i, k) - (currentElmt * this.GetElmt(j, k))));
                        }
                    }
                }
            } else {
                // KASUS TIDAK NORMAL
                idxKol = j;
                if (idxKol < this.GetNKolEff()) {
                    idxKol++;
                }
                for (int i = 1; i <= this.GetNBrsEff(); i++) {
                    if (j > this.GetNBrsEff()) {
                        idxBrs = this.GetNBrsEff();
                    } else {
                        idxBrs = j;
                    }

                    iMax = this.MaxAbsColumn(idxKol, idxBrs);
                    this.SwitchRow(idxBrs, iMax);

                    if (this.GetElmt(idxBrs, idxKol) != 0.0) {
                        currentElmt = this.GetElmt(idxBrs, idxKol);
                        for (int k = 1; k <= this.GetNKolEff(); k++) {
                            if (this.GetElmt(idxBrs, k) != 0.0) {
                                this.SetElement(idxBrs, k, (this.GetElmt(idxBrs, k) / currentElmt));
                            }
                        }
                    }
                    if (i > j && this.GetElmt(j, idxKol) != 0.0) {
                        currentElmt = this.GetElmt(i, idxKol);
                        for (int k = j; k <= this.GetNKolEff(); k++) {

                            this.SetElement(i, k, (this.GetElmt(i, k) - (currentElmt * this.GetElmt(j, k))));
                        }
                    }
                    //TulisMATRIKS(*M);
                    //printf("\n");
                }
            }
        }
    }

    // Prosedur Gauss-Jordan
    public void Gauss_Jordan() {
        /* I.S. : Matrix berupa matriks eselon dan memiliki solusi */
        /* F.S. : Matrix berupa matriks eselon tereduksi */

        // Kamus Lokal
        int idxSatuUtama, j;
        boolean found;
        double multiplier;

        // Algoritma
        for (int i = this.GetNBrsEff(); i > 1; i--) {
            found = false;
            j = 1;
            while (j < this.GetNKolEff() && !found) {
                if (this.GetElmt(i, j) == 1.0) {
                    found = true;
                } else {
                    j++;
                }
            }

            if (found) {
                idxSatuUtama = j;
                for (int k = 1; k < i; k++) {
                    multiplier = this.GetElmt(k, idxSatuUtama);
                    for (int l = 1; l <= this.GetNKolEff(); l++) {
                        this.SetElement(k, l, this.GetElmt(k, l) - (multiplier * this.GetElmt(i, l)));
                    }
                }
            } else {
                // Baris 000000 DO NOTHING!!
            }
        }
    }

    public double[] SulihMundur() {
	    /*  I.S : Matrix terdefinisi dan matriks adalah matriks eselon
            F.S : Nilai dari matriks dimasukkan ke dalam array double */

        // Kamus Lokal
        int j;
        boolean found;
        double sum;
        double[] val_X = new double[N_MAX + 1];

        // Algoritma
        for (int i = this.GetNBrsEff(); i >= 1; i--) {
            sum = 0;
            found = false;
            j = 1;
            while (j < this.GetNKolEff() && !found) {
                if (this.GetElmt(i, j) == 1.0) {
                    found = true;
                } else {
                    j++;
                }
            }

            if (found) {
                sum = this.GetElmt(i, this.GetNKolEff());
                for (int k = j + 1; k < this.GetNKolEff(); k++) {
                    sum -= this.GetElmt(i, k) * val_X[k];
                }
                val_X[j] = sum;
            } else {
                // Baris 000000 DO NOTHING!!
            }
        }
        return (val_X);
    }

    // Predikat untuk determinasi jenis SPL
    public boolean IsBarisNol(int i) {
        /* Mengembalikan nilai true apabila baris ke-i pada matriks merupakan baris nol */
        int j = 1;
        while (j < this.GetNKolEff() && this.GetElmt(i, j) == 0.0) {
            j++;
        }// i == this.GetNKolEff || i!=0.0
        if (this.GetElmt(i, j) == 0.0) {
            return true;
        } else {
            return false;
        }
    }

    public int CountBarisNol() {
        /* Menghitung kemunculan baris nol pada matriks */
        int cnt = 0;
        for (int i = 1; i <= this.GetNBrsEff(); i++) {
            if (this.IsBarisNol(i)) cnt++;
        }
        return cnt;
    }

    public boolean IsManySolution() {
        /* Menghasilkan true apabila SPL memiliki solusi banyak, yaitu jika jumlahVariabel < jumlahPersamaan */
        int jumlahBarisNol = this.CountBarisNol();
        int jumlahVariabel = this.GetNKolEff() - 1;
        if (this.GetNBrsEff() - jumlahBarisNol < jumlahVariabel) {
            return true;
        } else {
            return false;
        }
    }

    public boolean IsBarisSiluman(int i) {
        /* Menghasilkan true apabila baris ke-i merupakan baris "siluman", yaitu baris yang 000000X */
        int j = 1;
        while (j < this.GetNKolEff() && this.GetElmt(i, j) == 0.0) {
            j++;
        }// i == this.GetNKolEff || i!=0.0
        if (j == this.GetNKolEff() && this.GetElmt(i, j) != 0.0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean IsNoSolution() {
        /* Menghasilkan nilai true apabila SPL tidak memiliki solusi, yaitu ketika SPL memiliki baris "siluman" */
        int i = 1;
        while (i < this.GetNBrsEff() && !(this.IsBarisSiluman(i))) {
            i++;
        }// i == NBrsEff || IsBarisSiluman
        if (this.IsBarisSiluman(i)) {
            return true;
        } else {
            return false;
        }
    }

    public void PrintParametrik() {
        /* Menghasilkan nilai-nilai variabel-variabel dalam bentuk parametrik */
        // Kamus Lokal
        boolean[] stat = new boolean[N_MAX + 1];
        String[] ans = new String[N_MAX + 1];
        // Algoritma
        Arrays.fill(stat, false);
        for (char c = 'a'; c <= 'z'; c++) {
            ans[c - 'a' + 1] = Character.toString(c);
        }
        int startingI = this.GetNBrsEff();
        while (startingI > 1 && IsBarisNol(startingI)) {
            startingI--;
        } // startingI == 1 || !IsBarisNol(startingI)
        for (int i = startingI; i >= 1; i--) {
            // Cari indeks untuk set nilai
            int idxSet = 1;
            while ((this.GetElmt(i, idxSet) == 0 || stat[idxSet]) && idxSet < this.GetNKolEff() - 1) {
                idxSet++;
            }// GetElmt(idxSet)!=0 && stat[idxSet]==false
            stat[idxSet] = true;

            // Hitung nilai string yg akan diset
            String tmp;
            tmp = String.valueOf(this.GetElmt(i, this.GetNKolEff()));
            for (int j = this.GetNKolEff() - 1; j >= 1; j--) {
                if (j == idxSet) continue;
                else {
                    char operator;
                    double multiplier;
                    String nilai;

                    multiplier = this.GetElmt(i, j);
                    nilai = ans[j];
                    if (nilai.length() != 1)
                        nilai = "(" + nilai + ")";

                    if (multiplier == 0)
                        continue;
                    else if (multiplier < 0.0)
                        operator = '+';
                    else
                        operator = '-';

                    stat[j] = true;

                    if (Math.abs(multiplier) == 1.0) {
                        tmp = tmp + " " + Character.toString(operator) + " " + nilai;
                    } else {
                        tmp = tmp + " " + Character.toString(operator) + " " + String.valueOf(multiplier) + "*" + nilai;
                    }
                }
            }

            if (this.GetElmt(i, idxSet) != 1.0)
                tmp = "(" + tmp + ")" + "/" + String.valueOf(GetElmt(i, idxSet));

            ans[idxSet] = tmp;

        }
        for (int j = 1; j < this.GetNKolEff(); j++) {
            System.out.println("X" + String.valueOf(j) + " = " + ans[j]);
        }

    }

}