// Nama File: PembayaranGopay.java
public class PembayaranGopay implements MetodePembayaran {
    // Konsep: Final Static Variable (Praktikum 11)
    // Biaya admin yang tidak bisa diubah (final) dan milik kelas (static)
    private static final double BIAYA_ADMIN = 1000;

    @Override
    public void prosesPembayaran(double total) {
        double totalDenganBiaya = total + BIAYA_ADMIN;
        System.out.println("\nPembayaran dengan GoPay");
        System.out.println("Total Belanja: Rp" + String.format("%,.0f", total));
        System.out.println("Biaya Admin GoPay: Rp" + String.format("%,.0f", BIAYA_ADMIN));
        System.out.println("Total Tagihan: Rp" + String.format("%,.0f", totalDenganBiaya));
        System.out.println("Silakan scan QR code untuk membayar.");
    }
}