/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labsessionpbo3;

/**
 *
 * @author anggu
 */
public class Mobil extends Kendaraan implements DapatDisewa {
    
    // Constructor
    public Mobil(String merk, String nama, double tarifHarian) {
        super(merk, nama, tarifHarian);
    }

    // Method Overriding
    @Override
    public double hitungBiaya(int hari) {
        return getTarifHarian() * hari;
    }

    // Method Overriding
    @Override
    public void tampilkanInfo() {
        System.out.println("Jenis Kendaraan : Mobil");
        System.out.println("Merk            : " + getMerk());
        System.out.println("Nama            : " + getNama());
        System.out.println("Tarif per Hari  : Rp" + getTarifHarian());
        System.out.println("Status          : " +
                (isTersedia() ? "Tersedia" : "Sedang Disewa"));
    }

    // Implementasi interface
    @Override
    public void sewa(int hari) {
        super.sewa(hari);
    }

    @Override
    public void kembalikan() {
        super.kembalikan();
    }
}
