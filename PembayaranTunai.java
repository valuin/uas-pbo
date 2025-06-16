// Nama File: PembayaranTunai.java
public class PembayaranTunai implements MetodePembayaran {
    @Override
    public void prosesPembayaran(double total) {
        System.out.println("\nPembayaran sebesar Rp" + String.format("%,.0f", total) + " dengan Tunai berhasil.");
        System.out.println("Silakan siapkan uang pas.");
    }
}