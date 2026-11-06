/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labsessionpbo3;

/**
 *
 * @author anggu
 */
    
public abstract class Kendaraan {

    // Atribut private (Encapsulation)
    private String merk;
    private String nama;
    private double tarifHarian;
    private boolean tersedia;

    // Constructor
    public Kendaraan(String merk, String nama, double tarifHarian) {
        this.merk = merk;
        this.nama = nama;
        this.tarifHarian = tarifHarian;
        this.tersedia = true;
    }

    // Getter dan Setter
    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getTarifHarian() {
        return tarifHarian;
    }

    public void setTarifHarian(double tarifHarian) {
        this.tarifHarian = tarifHarian;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    // Abstract method
    public abstract double hitungBiaya(int hari);

    public abstract void tampilkanInfo();

    // Method penyewaan
    public void sewa(int hari) {
        prosesSewa(hari, 0);
    }

    // Method Overloading
    public void sewa(int hari, double diskon) {
        if (diskon < 0 || diskon > 100) {
            System.out.println("Persentase diskon tidak valid!");
            return;
        }

        prosesSewa(hari, diskon);
    }

    // Proses penyewaan
    private void prosesSewa(int hari, double diskon) {

        if (hari <= 0) {
            System.out.println("Jumlah hari harus lebih dari 0!");
            return;
        }

        if (!tersedia) {
            System.out.println(nama + " sedang disewa!");
            return;
        }

        double biaya = hitungBiaya(hari);
        double potongan = biaya * diskon / 100;
        double total = biaya - potongan;

        tersedia = false;

        System.out.println("Kendaraan : " + nama);
        System.out.println("Lama sewa : " + hari + " hari");
        System.out.println("Biaya     : Rp" + biaya);
        System.out.println("Diskon    : " + diskon + "%");
        System.out.println("Total     : Rp" + total);
        System.out.println("Status    : Berhasil disewa");
    }

    // Method pengembalian
    public void kembalikan() {
        if (!tersedia) {
            tersedia = true;
            System.out.println(nama + " berhasil dikembalikan.");
        } else {
            System.out.println(nama + " tidak sedang disewa.");
        }
    }
}
