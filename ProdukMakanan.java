// Nama File: ProdukMakanan.java
public class ProdukMakanan extends Produk {
    public ProdukMakanan(String nama, double harga) {
        super(nama, harga, "Makanan"); // Memanggil constructor kelas induk
    }

    @Override
    public void tampilkanInfo() {
        // Implementasi spesifik untuk makanan
        System.out.println("Info Makanan: " + getNama() + " - Rp" + getHarga());
    }
}