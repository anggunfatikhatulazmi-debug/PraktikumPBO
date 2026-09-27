/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.service;
import library.model.Book;
import library.model.Member;
import library.exception.BookNotFoundException;
import library.exception.BorrowLimitExceededException;
import library.exception.BookAlreadyBorrowedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author anggu
 */
public class LibraryService {

    // ==== Reference type: koleksi data ====
    private List<Book> daftarBuku;
    private Map<String, Member> daftarAnggota; // key = id anggota

    // ==== Primitive type: statistik global ====
    private int totalTransaksiPinjam;

    public LibraryService() {
        this.daftarBuku = new ArrayList<>();
        this.daftarAnggota = new HashMap<>();
        this.totalTransaksiPinjam = 0;
    }

    // ======================================================
    // 1. MANAJEMEN DATA BUKU
    // ======================================================
    public void tambahBuku(Book buku) {
        daftarBuku.add(buku);
    }

    public List<Book> getDaftarBuku() {
        return daftarBuku;
    }

    public void tambahAnggota(Member anggota) {
        daftarAnggota.put(anggota.getId(), anggota);
    }

    public Member getAnggota(String id) {
        return daftarAnggota.get(id);
    }

    public Map<String, Member> getDaftarAnggota() {
        return daftarAnggota;
    }

    // ======================================================
    // 2. PENCARIAN & ANALISIS BUKU
    // ======================================================

    
    public List<Book> cariBukuByJudul(String keyword) {
        List<Book> hasil = new ArrayList<>();
        String keywordLower = keyword.toLowerCase();

        for (Book b : daftarBuku) {
            if (b.getJudul().toLowerCase().contains(keywordLower)) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    
    public List<Book> cariBukuByKategori(String keyword) {
        List<Book> hasil = new ArrayList<>();
        String keywordLower = keyword.toLowerCase();

        for (Book b : daftarBuku) {
            if (b.getKategori().toLowerCase().contains(keywordLower)) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    
    public Map<String, Integer> hitungJumlahBukuPerKategori() {
        Map<String, Integer> rekap = new HashMap<>();

        for (Book b : daftarBuku) {
            String kategori = b.getKategori();
            if (rekap.containsKey(kategori)) {
                int jumlahSaatIni = rekap.get(kategori);
                rekap.put(kategori, jumlahSaatIni + 1);
            } else {
                rekap.put(kategori, 1);
            }
        }
        return rekap;
    }

    private Book cariBukuPersisByJudul(String judul) {
        for (Book b : daftarBuku) {
            if (b.getJudul().equalsIgnoreCase(judul.trim())) {
                return b;
            }
        }
        return null;
    }

    // ======================================================
    // 3 & 4. PEMINJAMAN & PENGEMBALIAN
    // ======================================================

    public void pinjamBuku(String memberId, String judulBuku)
            throws BookNotFoundException, BookAlreadyBorrowedException, BorrowLimitExceededException {

        Member anggota = daftarAnggota.get(memberId);

        // --- Assertion: memastikan data anggota valid sebelum transaksi ---
        // (Catatan: assertion hanya aktif jika dijalankan dengan flag -ea)
        assert anggota != null : "Anggota dengan ID " + memberId + " tidak valid/tidak terdaftar";
        assert anggota.getId() != null && !anggota.getId().isEmpty() : "ID anggota tidak boleh kosong";

        // Validasi manual sebagai jaring pengaman jika assertion tidak aktif
        if (anggota == null) {
            throw new BookNotFoundException("Anggota dengan ID '" + memberId + "' tidak ditemukan.");
        }

        Book buku = cariBukuPersisByJudul(judulBuku);
        if (buku == null) {
            throw new BookNotFoundException("Buku dengan judul '" + judulBuku + "' tidak ditemukan.");
        }

        if (!buku.isStatusKetersediaan()) {
            throw new BookAlreadyBorrowedException("Buku \"" + buku.getJudul() + "\" sedang dipinjam anggota lain.");
        }

        if (anggota.jumlahPinjamanAktif() >= Member.BATAS_MAKSIMAL_PINJAM) {
            throw new BorrowLimitExceededException(
                    "Anggota " + anggota.getNama() + " sudah meminjam " + Member.BATAS_MAKSIMAL_PINJAM
                            + " buku (batas maksimal). Kembalikan buku terlebih dahulu.");
        }

        // Semua validasi lolos -> proses peminjaman
        buku.setStatusKetersediaan(false);
        buku.tambahHitunganDipinjam();
        anggota.tambahPinjaman(buku);
        totalTransaksiPinjam++;
    }

    public void kembalikanBuku(String memberId, String judulBuku) throws BookNotFoundException {
        Member anggota = daftarAnggota.get(memberId);
        assert anggota != null : "Anggota dengan ID " + memberId + " tidak valid";

        if (anggota == null) {
            throw new BookNotFoundException("Anggota dengan ID '" + memberId + "' tidak ditemukan.");
        }

        Book bukuDipinjam = null;
        for (Book b : anggota.getDaftarPinjaman()) {
            if (b.getJudul().equalsIgnoreCase(judulBuku.trim())) {
                bukuDipinjam = b;
                break;
            }
        }

        if (bukuDipinjam == null) {
            throw new BookNotFoundException(
                    "Anggota " + anggota.getNama() + " tidak sedang meminjam buku '" + judulBuku + "'.");
        }

        bukuDipinjam.setStatusKetersediaan(true);
        anggota.hapusPinjaman(bukuDipinjam);
    }

    // ======================================================
    // 5. ANALISIS AKTIVITAS & LAPORAN
    // ======================================================

    public Book bukuTerpopuler() {
        if (daftarBuku.isEmpty()) {
            return null;
        }

        Book terpopuler = daftarBuku.get(0);
        for (Book b : daftarBuku) {
            if (b.getJumlahDipinjam() > terpopuler.getJumlahDipinjam()) {
                terpopuler = b;
            }
        }
        return terpopuler.getJumlahDipinjam() > 0 ? terpopuler : null;
    }

    public Member anggotaPalingAktif() {
        Member palingAktif = null;
        int rekorTertinggi = 0;

        for (Member m : daftarAnggota.values()) {
            if (m.getTotalPinjamSepanjangWaktu() > rekorTertinggi) {
                rekorTertinggi = m.getTotalPinjamSepanjangWaktu();
                palingAktif = m;
            }
        }
        return palingAktif;
    }

    public String kategoriPalingPopuler() {
        Map<String, Integer> hitungPinjamPerKategori = new HashMap<>();

        for (Book b : daftarBuku) {
            int totalDipinjamBukuIni = b.getJumlahDipinjam();
            if (totalDipinjamBukuIni > 0) {
                String kategori = b.getKategori();
                int akumulasi = hitungPinjamPerKategori.containsKey(kategori)
                        ? hitungPinjamPerKategori.get(kategori)
                        : 0;
                hitungPinjamPerKategori.put(kategori, akumulasi + totalDipinjamBukuIni);
            }
        }

        String kategoriTerpopuler = null;
        int nilaiTertinggi = 0;
        for (Map.Entry<String, Integer> entry : hitungPinjamPerKategori.entrySet()) {
            if (entry.getValue() > nilaiTertinggi) {
                nilaiTertinggi = entry.getValue();
                kategoriTerpopuler = entry.getKey();
            }
        }
        return kategoriTerpopuler == null ? "Belum ada data peminjaman" : kategoriTerpopuler;
    }

    public String cetakLaporanPerpustakaan() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== LAPORAN PERPUSTAKAAN =====\n");
        sb.append("Total transaksi peminjaman : ").append(totalTransaksiPinjam).append("\n");

        Book terpopuler = bukuTerpopuler();
        sb.append("Buku paling sering dipinjam: ")
          .append(terpopuler != null ? terpopuler.getJudul() + " (" + terpopuler.getJumlahDipinjam() + "x)" : "-")
          .append("\n");

        Member aktif = anggotaPalingAktif();
        sb.append("Anggota paling aktif       : ")
          .append(aktif != null ? aktif.getNama() + " (" + aktif.getTotalPinjamSepanjangWaktu() + "x pinjam)" : "-")
          .append("\n");

        sb.append("Kategori paling populer     : ").append(kategoriPalingPopuler()).append("\n");

        sb.append("\n-- Jumlah buku per kategori --\n");
        Map<String, Integer> rekapKategori = hitungJumlahBukuPerKategori();
        for (Map.Entry<String, Integer> entry : rekapKategori.entrySet()) {
            sb.append("  ").append(entry.getKey()).append(" : ").append(entry.getValue()).append(" buku\n");
        }

        return sb.toString();
    }

    public int getTotalTransaksiPinjam() {
        return totalTransaksiPinjam;
    }
}

