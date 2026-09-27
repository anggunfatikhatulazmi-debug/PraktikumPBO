/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author anggu
 */
public class Member {

    // ==== Reference type fields ====
    private String id;
    private String nama;
    private List<Book> daftarPinjaman;

    // ==== Primitive type field ====
    private int totalPinjamSepanjangWaktu; // untuk laporan "anggota paling aktif"

    public static final int BATAS_MAKSIMAL_PINJAM = 3;

    public Member(String id, String nama) {
        this.id = id;
        this.nama = nama;
        this.daftarPinjaman = new ArrayList<>();
        this.totalPinjamSepanjangWaktu = 0;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public List<Book> getDaftarPinjaman() {
        return daftarPinjaman;
    }

    public int getTotalPinjamSepanjangWaktu() {
        return totalPinjamSepanjangWaktu;
    }

    public int jumlahPinjamanAktif() {
        return daftarPinjaman.size();
    }

    public void tambahPinjaman(Book buku) {
        daftarPinjaman.add(buku);
        totalPinjamSepanjangWaktu++;
    }

    public void hapusPinjaman(Book buku) {
        daftarPinjaman.remove(buku);
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Sedang dipinjam: %d buku | Total riwayat pinjam: %d",
                id, nama, jumlahPinjamanAktif(), totalPinjamSepanjangWaktu);
    }
}

