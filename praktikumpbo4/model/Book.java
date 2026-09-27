/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;

/**
 *
 * @author anggu
 */
public class Book {

    // ==== Reference type fields ====
    private String judul;
    private String penulis;
    private String kategori;

    // ==== Primitive type fields ====
    private int tahunTerbit;
    private boolean statusKetersediaan; // true = tersedia, false = sedang dipinjam
    private int jumlahDipinjam;         // dipakai untuk analisis buku terpopuler

    // Constructor
    public Book(String judul, String penulis, int tahunTerbit, String kategori) {
        // --- Manipulasi String & Character (1): rapikan judul jadi Title Case ---
        this.judul = capitalizeEachWord(judul);
        this.penulis = penulis;
        this.tahunTerbit = tahunTerbit;
        this.kategori = kategori.trim();
        this.statusKetersediaan = true; // buku baru otomatis tersedia
        this.jumlahDipinjam = 0;
    }

    /**
     * Contoh manipulasi Character: mengubah huruf pertama tiap kata menjadi kapital
     * menggunakan Character.isLetter() dan Character.toUpperCase()/toLowerCase().
     */
    private String capitalizeEachWord(String teks) {
        StringBuilder hasil = new StringBuilder();
        boolean awalKata = true;

        for (int i = 0; i < teks.length(); i++) {
            char c = teks.charAt(i);
            if (Character.isWhitespace(c)) {
                awalKata = true;
                hasil.append(c);
            } else if (Character.isLetter(c)) {
                if (awalKata) {
                    hasil.append(Character.toUpperCase(c));
                    awalKata = false;
                } else {
                    hasil.append(Character.toLowerCase(c));
                }
            } else {
                hasil.append(c);
            }
        }
        return hasil.toString();
    }

    // ==== Getter & Setter ====
    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public int getTahunTerbit() {
        return tahunTerbit;
    }

    public String getKategori() {
        return kategori;
    }

    public boolean isStatusKetersediaan() {
        return statusKetersediaan;
    }

    public void setStatusKetersediaan(boolean statusKetersediaan) {
        this.statusKetersediaan = statusKetersediaan;
    }

    public int getJumlahDipinjam() {
        return jumlahDipinjam;
    }

    public void tambahHitunganDipinjam() {
        this.jumlahDipinjam++;
    }

    @Override
    public String toString() {
        String status = statusKetersediaan ? "Tersedia" : "Dipinjam";
        return String.format("\"%s\" - %s (%d) | Kategori: %s | Status: %s | Dipinjam: %dx",
                judul, penulis, tahunTerbit, kategori, status, jumlahDipinjam);
    }
}

