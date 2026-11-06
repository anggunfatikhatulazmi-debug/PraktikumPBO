/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labsessionpbo3;

/**
 *
 * @author anggu
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("    SISTEM PENYEWAAN KENDARAAN");
        System.out.println("======================================");

        // Membuat 4 objek kendaraan
        Mobil mobil1 = new Mobil("Toyota", "Avanza", 350000);
        Mobil mobil2 = new Mobil("Honda", "Brio", 300000);

        Motor motor1 = new Motor("Honda", "Vario", 100000);
        Motor motor2 = new Motor("Yamaha", "NMAX", 150000);

        // Runtime Polymorphism
        Kendaraan[] daftarKendaraan = {
            mobil1, mobil2, motor1, motor2
        };

        // Menampilkan daftar kendaraan
        System.out.println("\n--- DAFTAR KENDARAAN ---");

        for (Kendaraan kendaraan : daftarKendaraan) {
            kendaraan.tampilkanInfo();
            System.out.println("--------------------------------");
        }

        // Proses penyewaan
        System.out.println("\n--- TRANSAKSI PENYEWAAN ---");

        System.out.println("\n1. Penyewaan Mobil:");
        mobil1.sewa(2);

        System.out.println("\n2. Penyewaan Mobil dengan Diskon:");
        mobil2.sewa(1, 10);

        System.out.println("\n3. Penyewaan Motor:");
        motor1.sewa(3);

        System.out.println("\n4. Penyewaan Motor dengan Diskon:");
        motor2.sewa(2, 15);

        // Pengembalian kendaraan
        System.out.println("\n--- PENGEMBALIAN KENDARAAN ---");

        mobil1.kembalikan();
        motor1.kembalikan();

        // Menampilkan status akhir
        System.out.println("\n--- STATUS AKHIR KENDARAAN ---");

        for (Kendaraan kendaraan : daftarKendaraan) {
            System.out.println(
                kendaraan.getNama() + " : " +
                (kendaraan.isTersedia() ? "Tersedia" : "Sedang Disewa")
            );
        }

        System.out.println("\n======================================");
        System.out.println("       PROGRAM SELESAI");
        System.out.println("======================================");
    }
}
