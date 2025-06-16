// Nama File: PembayaranDebit.java
public class PembayaranDebit implements MetodePembayaran {
    @Override
    public void prosesPembayaran(double total) {
        System.out.println("\nPembayaran sebesar Rp" + String.format("%,.0f", total) + " dengan Debit berhasil.");
        System.out.println("Silakan gesek kartu Anda.");
    }
}