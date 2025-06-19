public class PembayaranTunai implements MetodePembayaran {
    @Override
    public String prosesPembayaran(double total) {
        String pesan = "Pembayaran sebesar Rp" + String.format("%,.0f", total) + " dengan Tunai berhasil.\n";
        pesan += "Silakan siapkan uang pas.";
        return pesan;
    }
}