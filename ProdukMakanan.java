public class ProdukMakanan extends Produk {
    public ProdukMakanan(String nama, double harga) {
        super(nama, harga, "Makanan"); 
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Info Makanan: " + getNama() + " - Rp" + getHarga());
    }
}