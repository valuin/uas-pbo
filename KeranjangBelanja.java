// Nama File: KeranjangBelanja.java
import java.util.ArrayList;
import java.util.List;

/**
 * Konsep yang diterapkan:
 * 1. Java Collection (Praktikum 14): Menggunakan ArrayList untuk menyimpan item.
 * 2. Method (Praktikum 6): Berisi method untuk mengelola keranjang.
 */
public class KeranjangBelanja {
    private List<ItemKeranjang> items;

    public KeranjangBelanja() {
        // Menggunakan ArrayList dari Java Collection Framework
        this.items = new ArrayList<>();
    }

    // Method untuk menambah produk ke keranjang
    public void tambahProduk(Produk produk, int jumlah) {
        // Cek apakah produk sudah ada di keranjang, jika ya, update jumlahnya
        for (ItemKeranjang item : items) {
            if (item.getProduk().getNama().equals(produk.getNama())) {
                item = new ItemKeranjang(produk, item.getJumlah() + jumlah);
                System.out.println("Jumlah " + produk.getNama() + " di keranjang diupdate.");
                return;
            }
        }
        // Jika belum ada, tambahkan sebagai item baru
        this.items.add(new ItemKeranjang(produk, jumlah));
        System.out.println(produk.getNama() + " berhasil ditambahkan ke keranjang.");
    }

    // Method untuk menampilkan isi keranjang
    public void tampilkanIsiKeranjang() {
        if (items.isEmpty()) {
            System.out.println("Keranjang belanja masih kosong.");
            return;
        }
        System.out.println("\n--- Isi Keranjang Belanja ---");
        int nomor = 1;
        for (ItemKeranjang item : items) {
            System.out.printf("%d. %s (%d x Rp%,.0f) = Rp%,.0f\n",
                nomor++,
                item.getProduk().getNama(),
                item.getJumlah(),
                item.getProduk().getHarga(),
                item.getSubtotal());
        }
        System.out.println("-----------------------------");
    }
    
    // Method untuk menghitung total harga
    public double hitungTotal() {
        double total = 0;
        for (ItemKeranjang item : items) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    public List<ItemKeranjang> getItems() {
        return items;
    }
}
