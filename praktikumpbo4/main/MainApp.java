/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.main;

import library.model.Book;
import library.model.Member;
import library.service.LibraryService;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.exception.BookAlreadyBorrowedException;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author anggu
 */
public class MainApp {


    private static Scanner scanner = new Scanner(System.in);
    private static LibraryService service = new LibraryService();

    public static void main(String[] args) {
        seedDataAwal(); // beberapa data contoh agar aplikasi langsung bisa dicoba

        boolean berjalan = true; // primitive boolean untuk kontrol looping menu

        while (berjalan) {
            tampilkanMenu();
            String pilihanInput = scanner.nextLine().trim();
            int pilihan;

            try {
                pilihan = Integer.parseInt(pilihanInput);
            } catch (NumberFormatException e) {
                System.out.println(">> Input tidak valid, masukkan angka menu.\n");
                continue;
            }

            switch (pilihan) {
                case 1:
                    tambahBuku();
                    break;
                case 2:
                    daftarBuku();
                    break;
                case 3:
                    cariBuku();
                    break;
                case 4:
                    pinjamBuku();
                    break;
                case 5:
                    kembalikanBuku();
                    break;
                case 6:
                    System.out.println(service.cetakLaporanPerpustakaan());
                    break;
                case 7:
                    berjalan = false;
                    System.out.println("Terima kasih, sampai jumpa!");
                    break;
                default:
                    System.out.println(">> Menu tidak dikenali, silakan pilih 1-7.\n");
            }
        }

        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("=========================================");
        System.out.println("   SISTEM MANAJEMEN PERPUSTAKAAN MINI");
        System.out.println("=========================================");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Daftar Buku");
        System.out.println("3. Cari Buku");
        System.out.println("4. Pinjam Buku");
        System.out.println("5. Kembalikan Buku");
        System.out.println("6. Laporan Perpustakaan");
        System.out.println("7. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static void tambahBuku() {
        System.out.print("Judul buku       : ");
        String judul = scanner.nextLine();
        System.out.print("Penulis          : ");
        String penulis = scanner.nextLine();

        int tahun = 0;
        boolean tahunValid = false;
        while (!tahunValid) {
            System.out.print("Tahun terbit     : ");
            String inputTahun = scanner.nextLine();
            try {
                tahun = Integer.parseInt(inputTahun);
                tahunValid = true;
            } catch (NumberFormatException e) {
                System.out.println(">> Tahun harus berupa angka, coba lagi.");
            }
        }

        System.out.print("Kategori         : ");
        String kategori = scanner.nextLine();

        Book bukuBaru = new Book(judul, penulis, tahun, kategori);
        service.tambahBuku(bukuBaru);
        System.out.println(">> Buku berhasil ditambahkan: " + bukuBaru + "\n");
    }

    private static void daftarBuku() {
        List<Book> semuaBuku = service.getDaftarBuku();
        if (semuaBuku.isEmpty()) {
            System.out.println(">> Belum ada buku dalam koleksi.\n");
            return;
        }

        System.out.println("---- Daftar Buku (" + semuaBuku.size() + ") ----");
        int nomor = 1;
        for (Book b : semuaBuku) {
            System.out.println(nomor + ". " + b);
            nomor++;
        }
        System.out.println();
    }

    private static void cariBuku() {
        System.out.println("Cari berdasarkan: 1) Judul  2) Kategori");
        System.out.print("Pilih: ");
        String pilihan = scanner.nextLine().trim();

        System.out.print("Kata kunci: ");
        String kataKunci = scanner.nextLine();

        List<Book> hasil;
        if (pilihan.equals("2")) {
            hasil = service.cariBukuByKategori(kataKunci);
        } else {
            hasil = service.cariBukuByJudul(kataKunci);
        }

        if (hasil.isEmpty()) {
            System.out.println(">> Tidak ada buku yang cocok dengan '" + kataKunci + "'.\n");
        } else {
            System.out.println("---- Hasil Pencarian (" + hasil.size() + ") ----");
            for (Book b : hasil) {
                System.out.println("- " + b);
            }
            System.out.println();
        }
    }

    private static void pinjamBuku() {
        System.out.print("ID Anggota   : ");
        String idAnggota = scanner.nextLine().trim();
        System.out.print("Judul Buku   : ");
        String judul = scanner.nextLine().trim();

        try {
            service.pinjamBuku(idAnggota, judul);
            System.out.println(">> Peminjaman berhasil!\n");
        } catch (BookNotFoundException e) {
            System.out.println(">> Gagal meminjam: " + e.getMessage() + "\n");
        } catch (BookAlreadyBorrowedException e) {
            System.out.println(">> Gagal meminjam: " + e.getMessage () + "\n");
        } catch (BorrowLimitExceededException e) {
            System.out.println(">> Gagal meminjam: " + e.getMessage() + "\n");
        } catch (AssertionError e) {
            System.out.println(">> Data anggota tidak valid: " + e.getMessage() + "\n");
        }
    }

    private static void kembalikanBuku() {
        System.out.print("ID Anggota   : ");
        String idAnggota = scanner.nextLine().trim();
        System.out.print("Judul Buku   : ");
        String judul = scanner.nextLine().trim();

        try {
            service.kembalikanBuku(idAnggota, judul);
            System.out.println(">> Pengembalian berhasil!\n");
        } catch (BookNotFoundException e) {
            System.out.println(">> Gagal mengembalikan: " + e.getMessage() + "\n");
        } catch (AssertionError e) {
            System.out.println(">> Data anggota tidak valid: " + e.getMessage() + "\n");
        }
    }

    
    private static void seedDataAwal() {
        service.tambahBuku(new Book("laskar pelangi", "Andrea Hirata", 2005, "Novel"));
        service.tambahBuku(new Book("bumi manusia", "Pramoedya Ananta Toer", 1980, "Novel"));
        service.tambahBuku(new Book("clean code", "Robert C. Martin", 2008, "Teknologi"));
        service.tambahBuku(new Book("filosofi teras", "Henry Manampiring", 2018, "Filsafat"));
        service.tambahBuku(new Book("atomic habits", "James Clear", 2018, "Pengembangan Diri"));

        service.tambahAnggota(new Member("A001", "Budi Santoso"));
        service.tambahAnggota(new Member("A002", "Siti Aminah"));
    }
}

