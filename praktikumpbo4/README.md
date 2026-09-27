## 📚 Sistem Manajemen Perpustakaan Mini
Aplikasi Java sederhana untuk mengelola koleksi buku dan peminjaman anggota perpustakaan, dilengkapi fitur pencarian, analisis aktivitas, dan laporan statistik.

## 🗂️ Struktur Project
library/
├── model/
│ ├── Book.java # Data buku (judul, penulis, tahun, kategori, status)
│ └── Member.java # Data anggota & daftar pinjaman
├── service/
│ └── LibraryService.java # Logika utama (CRUD, pinjam, kembalikan, laporan)
├── exception/
│ ├── BookNotFoundException.java
│ ├── BookAlreadyBorrowedException.java
│ └── BorrowLimitExceededException.java
└── main/
└── MainApp.java # Menu interaktif (entry point)


## 🔄 Alur Program
1. **Inisialisasi** — saat dijalankan, `MainApp` mengisi beberapa data contoh (5 buku & 2 anggota) lewat `seedDataAwal()` agar aplikasi langsung bisa dicoba.
2. **Menu utama** ditampilkan dalam perulangan (`while`) sampai pengguna memilih **Keluar**:
Menu Utama:
 1. Tambah Buku
 2. Daftar Buku
 3. Cari Buku
 4. Pinjam Buku
 5. Kembalikan Buku
 6. Laporan Perpustakaan
 7. Keluar   
3. **Tambah Buku** — input judul, penulis, tahun, kategori → disimpan ke `ArrayList<Book>`. Judul otomatis dirapikan jadi *Title Case*.
4. **Cari Buku** — mencari berdasarkan judul atau kategori memakai `toLowerCase()` + `contains()`, jadi tidak case-sensitive dan bisa pencarian sebagian kata.
5. **Pinjam Buku** — sebelum transaksi berhasil, sistem mengecek berurutan:
   - Anggota valid (`assert`)
   - Buku ada di koleksi → jika tidak, `BookNotFoundException`
   - Buku belum dipinjam orang lain → jika sudah, `BookAlreadyBorrowedException`
   - Anggota belum meminjam 3 buku → jika sudah, `BorrowLimitExceededException`
6. **Kembalikan Buku** — status buku dikembalikan jadi "Tersedia" dan dihapus dari daftar pinjaman anggota.
7. **Laporan Perpustakaan** — menghitung dan menampilkan:
   - Total transaksi peminjaman
   - Buku paling sering dipinjam
   - Anggota paling aktif
   - Kategori paling populer
   - Jumlah buku per kategori
     

## ▶️ Cara Menjalankan
```bash
javac -d out $(find library -name "*.java")
java -ea -cp out library.main.MainApp
```
> Flag `-ea` mengaktifkan **assertion** untuk validasi data anggota.

## 🖥️ Contoh Output
================================
SISTEM MANAJEMEN PERPUSTAKAAN MINI
1. Tambah Buku
2. Daftar Buku
3. Cari Buku
4. Pinjam Buku
5. Kembalikan Buku
6. Laporan Perpustakaan
7. Keluar

Pilih menu: 4
ID Anggota : A001
Judul Buku : Laskar Pelangi

Peminjaman berhasil!

Pilih menu: 4
ID Anggota : A001
Judul Buku : Laskar Pelangi

Gagal meminjam: Buku "Laskar Pelangi" sedang dipinjam anggota lain.

Pilih menu: 6
===== LAPORAN PERPUSTAKAAN =====
Total transaksi peminjaman : 1
Buku paling sering dipinjam: Laskar Pelangi (1x)
Anggota paling aktif : Budi Santoso (1x pinjam)
Kategori paling populer : Novel

-- Jumlah buku per kategori --
Novel : 2 buku
Teknologi : 1 buku
Filsafat : 1 buku
Pengembangan Diri : 1 buku


## ✨ Fitur Java yang Digunakan
| Konsep | Contoh Penerapan |
|---|---|
| OOP | `Class`, `Object`, `Constructor`, `Package` (model, service, exception, main) |
| Tipe Data | Primitive (`int`, `boolean`) & Reference (`String`, `ArrayList`, `HashMap`) |
| Exception | 3 custom exception (`BookNotFoundException`, dll.) |
| Assertion | Validasi anggota sebelum transaksi (`assert`) |
| String & Character | `toLowerCase()`, `contains()`, `Character.isLetter()`, `Character.toUpperCase()` |
| Kontrol Alur | `if-else`, `switch`, `for`, `while` |
