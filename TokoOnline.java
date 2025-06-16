import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TokoOnline {
    // Menyimpan daftar semua produk yang tersedia di toko
    private static List<Produk> daftarProduk = new ArrayList<>();
    // Keranjang belanja milik pengguna
    private static KeranjangBelanja keranjang = new KeranjangBelanja();
    // Scanner untuk menerima input dari pengguna
    private static Scanner scanner = new Scanner(System.in);

    // Method untuk mengisi data produk ke dalam daftarProduk
    public static void initProduk() {
        // Kategori: Makanan
        daftarProduk.add(new ProdukMakanan("Nasi Goreng Spesial", 25000));
        daftarProduk.add(new ProdukMakanan("Ayam Bakar Madu", 35000));
        daftarProduk.add(new ProdukMakanan("Sate Ayam (10 tusuk)", 30000));
        daftarProduk.add(new ProdukMakanan("Indomie Goreng Jumbo", 10000));
        daftarProduk.add(new ProdukMakanan("Roti Bakar Coklat Keju", 18000));

        // Kategori: Minuman
        daftarProduk.add(new ProdukMinuman("Es Teh Manis", 5000));
        daftarProduk.add(new ProdukMinuman("Jus Alpukat", 15000));
        daftarProduk.add(new ProdukMinuman("Kopi Susu Gula Aren", 22000));
        daftarProduk.add(new ProdukMinuman("Air Mineral 600ml", 4000));
        daftarProduk.add(new ProdukMinuman("Soda Gembira", 12000));

        // Kategori: Peralatan Dapur
        daftarProduk.add(new ProdukPeralatanDapur("Spatula Silikon", 45000));
        daftarProduk.add(new ProdukPeralatanDapur("Panci Anti Lengket 24cm", 150000));
        daftarProduk.add(new ProdukPeralatanDapur("Pisau Dapur Set", 250000));
        daftarProduk.add(new ProdukPeralatanDapur("Talenan Kayu", 75000));
        
        // Kategori: Keperluan Mandi
        daftarProduk.add(new ProdukKeperluanMandi("Sabun Batang Lifebuoy", 5000));
        daftarProduk.add(new ProdukKeperluanMandi("Shampoo Pantene 135ml", 22000));
        daftarProduk.add(new ProdukKeperluanMandi("Sikat Gigi Oral-B", 12000));
        daftarProduk.add(new ProdukKeperluanMandi("Pasta Gigi Pepsodent", 15000));
    }

    // Method untuk menampilkan semua produk yang tersedia
    public static void tampilkanDaftarProduk() {
        System.out.println("\n--- Selamat Datang di Toko Online ---");
        System.out.println("Daftar Produk yang Tersedia:");
        for (int i = 0; i < daftarProduk.size(); i++) {
            Produk p = daftarProduk.get(i);
            System.out.printf("%d. [%s] %s - Rp%,.0f\n", (i + 1), p.getKategori(), p.getNama(), p.getHarga());
        }
    }

    // Method untuk mengelola proses belanja
    public static void prosesBelanja() {
        int pilihan = -1;
        while (pilihan != 0) {
            tampilkanDaftarProduk();
            System.out.println("\nKetik nomor produk untuk menambah ke keranjang.");
            System.out.println("Ketik '99' untuk melihat keranjang & checkout.");
            System.out.println("Ketik '0' untuk keluar.");
            System.out.print("Pilihan Anda: ");
            
            try {
                pilihan = scanner.nextInt();
                if (pilihan > 0 && pilihan <= daftarProduk.size()) {
                    // Pengguna memilih produk
                    Produk produkDipilih = daftarProduk.get(pilihan - 1);
                    System.out.print("Masukkan jumlah: ");
                    int jumlah = scanner.nextInt();
                    if (jumlah > 0) {
                        keranjang.tambahProduk(produkDipilih, jumlah);
                    } else {
                        System.out.println("Jumlah harus lebih dari 0.");
                    }
                } else if (pilihan == 99) {
                    // Jika prosesCheckout mengembalikan false, itu artinya transaksi berhasil dan program harus berhenti.
                    if (!prosesCheckout()) {
                        pilihan = 0; // Set pilihan ke 0 untuk keluar dari while loop
                    }
                } else if (pilihan != 0) {
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                }
            } catch (InputMismatchException e) {
                // Konsep: Exception Handling (Praktikum 12)
                System.out.println("Input tidak valid! Harap masukkan angka.");
                scanner.next(); // Membersihkan buffer scanner
            }
        }
    }

    /**
     * Method untuk mengelola proses checkout dan pembayaran.
     * @return boolean: 'true' jika pengguna membatalkan checkout (kembali ke menu), 
     * 'false' jika transaksi berhasil (sinyal untuk keluar program).
     */
    public static boolean prosesCheckout() {
        keranjang.tampilkanIsiKeranjang();
        double total = keranjang.hitungTotal();
        
        if (total == 0) {
            return true; // Jika keranjang kosong, kembali ke menu utama.
        }

        System.out.printf("Total Belanja Anda: Rp%,.0f\n", total);
        System.out.println("\nPilih Metode Pembayaran:");
        System.out.println("1. Debit");
        System.out.println("2. GoPay (+ Rp1,000 biaya admin)");
        System.out.println("3. Tunai (Cash)");
        System.out.println("0. Batal Checkout");
        System.out.print("Pilihan Anda: ");

        try {
            int pilihanBayar = scanner.nextInt();
            MetodePembayaran metodePembayaran = null;

            switch (pilihanBayar) {
                case 1: metodePembayaran = new PembayaranDebit(); break;
                case 2: metodePembayaran = new PembayaranGopay(); break;
                case 3: metodePembayaran = new PembayaranTunai(); break;
                case 0:
                    System.out.println("Checkout dibatalkan.");
                    return true; // Kembali ke menu utama.
                default:
                    System.out.println("Pilihan pembayaran tidak valid.");
                    return true; // Kembali ke menu utama.
            }

            // Bagian ini hanya akan dieksekusi jika metode pembayaran valid (1, 2, atau 3)
            metodePembayaran.prosesPembayaran(total);
            System.out.println("\n==============================================");
            System.out.println("           Transaksi Berhasil!");
            System.out.println("==============================================");

            keranjang = new KeranjangBelanja(); // Kosongkan keranjang setelah berhasil bayar.
            return false; // Transaksi berhasil, kirim sinyal untuk keluar dari program.

        } catch (InputMismatchException e) {
            System.out.println("Input tidak valid! Harap masukkan angka.");
            scanner.next(); // Membersihkan buffer
            return true; // Jika terjadi error, kembali ke menu utama
        }
    }

    // Method utama (entry point program)
    public static void main(String[] args) {
        // 1. Inisialisasi data produk
        initProduk();
        // 2. Mulai proses belanja
        prosesBelanja();
        // 3. Program selesai, tampilkan pesan penutup
        System.out.println("\nTerima kasih telah berbelanja. Sampai jumpa lagi!");
        scanner.close();
    }
}
