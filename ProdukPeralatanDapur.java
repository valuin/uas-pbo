public class ProdukPeralatanDapur extends Produk {
    public ProdukPeralatanDapur(String nama, double harga) {
        super(nama, harga, "Peralatan Dapur");
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Info Peralatan Dapur: " + getNama() + " - Rp" + getHarga());
    }
}