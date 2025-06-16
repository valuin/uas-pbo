// Nama File: Produk.java

/**
 * Konsep yang diterapkan:
 * 1. Abstract Class (Praktikum 13): Menjadi blueprint untuk kelas produk lainnya.
 * 2. Encapsulation (Praktikum 7): Variabel dibuat private dan diakses melalui getter.
 */
public abstract class Produk {
    // Variabel instance di-encapsulate (private)
    private String nama;
    private double harga;
    private String kategori;

    // Constructor untuk inisialisasi properti produk
    public Produk(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getter untuk mengakses properti (Encapsulation)
    public String getNama() {
        return nama;
    }

    public double getHarga() {
        return harga;
    }

    public String getKategori() {
        return kategori;
    }

    // Method abstract yang harus diimplementasikan oleh kelas turunan
    // Ini adalah contoh sederhana untuk menunjukkan polymorphism nantinya
    public abstract void tampilkanInfo();
}