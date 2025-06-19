import java.util.ArrayList;
import java.util.List;

public class KeranjangBelanja {
    private List<ItemKeranjang> items;

    public KeranjangBelanja() {
        this.items = new ArrayList<>();
    }

    public void tambahProduk(Produk produk, int jumlah) {
        for (ItemKeranjang item : items) {
            if (item.getProduk().getNama().equals(produk.getNama())) {
                item = new ItemKeranjang(produk, item.getJumlah() + jumlah);
                System.out.println("Jumlah " + produk.getNama() + " di keranjang diupdate.");
                return;
            }
        }
        this.items.add(new ItemKeranjang(produk, jumlah));
        System.out.println(produk.getNama() + " berhasil ditambahkan ke keranjang.");
    }

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
