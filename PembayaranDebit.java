public class PembayaranDebit implements MetodePembayaran {
    @Override
    public String prosesPembayaran(double total) {
        String pesan = "Pembayaran sebesar Rp" + String.format("%,.0f", total) + " dengan Debit berhasil.\n";
        pesan += "Silakan gesek kartu Anda.";
        return pesan;
    }
}