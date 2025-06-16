// Nama File: ProdukMinuman.java
public class ProdukMinuman extends Produk {
    public ProdukMinuman(String nama, double harga) {
        super(nama, harga, "Minuman");
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Info Minuman: " + getNama() + " - Rp" + getHarga());
    }
}