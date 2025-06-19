public class ProdukKeperluanMandi extends Produk {
    public ProdukKeperluanMandi(String nama, double harga) {
        super(nama, harga, "Keperluan Mandi");
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Info Keperluan Mandi: " + getNama() + " - Rp" + getHarga());
    }
}