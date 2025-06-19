public class PembayaranGopay implements MetodePembayaran {
    private static final double BIAYA_ADMIN = 1000;

    @Override
    public String prosesPembayaran(double total) {
        double totalDenganBiaya = total + BIAYA_ADMIN;
        return String.format(
            "Pembayaran dengan GoPay\n" +
            "Total Belanja: Rp%,.0f\n" +
            "Biaya Admin GoPay: Rp%,.0f\n" +
            "Total Tagihan: Rp%,.0f\n\n" +
            "Silakan scan QR code untuk membayar.",
            total, BIAYA_ADMIN, totalDenganBiaya
        );
    }
}