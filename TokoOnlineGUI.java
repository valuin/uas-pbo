import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TokoOnlineGUI extends JFrame {

    private List<Produk> daftarProduk;
    private KeranjangBelanja keranjang;
    private JTable tabelProduk;
    private DefaultTableModel modelTabel;
    private JTextArea areaKeranjang;
    private JLabel labelTotal;
    private JButton tombolTambah, tombolCheckout;
    private JSpinner spinnerJumlah;

    public TokoOnlineGUI() {
        this.keranjang = new KeranjangBelanja();
        initProduk(); 

        setTitle("Aplikasi Kasir Toko Online Sederhana");
        setSize(850, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); 

        setupKomponen();
        
        setupActionListeners();
        
        updateTampilanKeranjang(); 
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void setupKomponen() {
        JPanel panelProduk = new JPanel(new BorderLayout(0, 5));
        panelProduk.setBorder(BorderFactory.createTitledBorder("Daftar Produk"));

        String[] kolom = {"Nama", "Kategori", "Harga (Rp)"};
        modelTabel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelProduk = new JTable(modelTabel);
        tabelProduk.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelProduk.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        for (Produk p : daftarProduk) {
            Object[] baris = {p.getNama(), p.getKategori(), String.format("%,.0f", p.getHarga())};
            modelTabel.addRow(baris);
        }
        
        panelProduk.add(new JScrollPane(tabelProduk), BorderLayout.CENTER);

        JPanel panelKontrolTambah = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelKontrolTambah.add(new JLabel("Jumlah:"));
        spinnerJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); 
        panelKontrolTambah.add(spinnerJumlah);
        tombolTambah = new JButton("Tambah ke Keranjang");
        panelKontrolTambah.add(tombolTambah);
        panelProduk.add(panelKontrolTambah, BorderLayout.SOUTH);

        JPanel panelKeranjang = new JPanel(new BorderLayout(0, 5));
        panelKeranjang.setBorder(BorderFactory.createTitledBorder("Keranjang Belanja"));
        
        areaKeranjang = new JTextArea();
        areaKeranjang.setEditable(false);
        areaKeranjang.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        panelKeranjang.add(new JScrollPane(areaKeranjang), BorderLayout.CENTER);

        JPanel panelInfoKeranjang = new JPanel(new BorderLayout());
        labelTotal = new JLabel("Total: Rp0");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfoKeranjang.add(labelTotal, BorderLayout.WEST);
        
        tombolCheckout = new JButton("Checkout");
        tombolCheckout.setFont(new Font("Arial", Font.BOLD, 14));
        panelInfoKeranjang.add(tombolCheckout, BorderLayout.EAST);
        
        panelKeranjang.add(panelInfoKeranjang, BorderLayout.SOUTH);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelProduk, panelKeranjang);
        splitPane.setResizeWeight(0.6); 

        add(splitPane, BorderLayout.CENTER);
    }

    private void setupActionListeners() {
        tombolTambah.addActionListener(e -> {
            int barisTerpilih = tabelProduk.getSelectedRow();
            if (barisTerpilih == -1) {
                JOptionPane.showMessageDialog(this, "Silakan pilih produk terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Produk produkDipilih = daftarProduk.get(barisTerpilih);
            int jumlah = (int) spinnerJumlah.getValue();
            
            keranjang.tambahProduk(produkDipilih, jumlah);
            
            updateTampilanKeranjang();
            JOptionPane.showMessageDialog(this, produkDipilih.getNama() + " berhasil ditambahkan.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        tombolCheckout.addActionListener(e -> prosesCheckout());
    }
    
    private void prosesCheckout() {
        if (keranjang.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang belanja masih kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double total = keranjang.hitungTotal();
        
        Object[] options = {"Debit", "GoPay (+Rp1,000)", "Tunai", "Batal"};
        int pilihan = JOptionPane.showOptionDialog(this,
                "Total belanja Anda: Rp" + String.format("%,.0f", total) + "\nSilakan pilih metode pembayaran:",
                "Proses Checkout",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        MetodePembayaran metodePembayaran = null;
        switch (pilihan) {
            case 0: metodePembayaran = new PembayaranDebit(); break;
            case 1: metodePembayaran = new PembayaranGopay(); break;
            case 2: metodePembayaran = new PembayaranTunai(); break;
            case 3: 
                JOptionPane.showMessageDialog(this, "Checkout dibatalkan.", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            default: 
                return;
        }

        String hasilPembayaran = metodePembayaran.prosesPembayaran(total);

        JOptionPane.showMessageDialog(this, hasilPembayaran, "Transaksi Berhasil", JOptionPane.INFORMATION_MESSAGE);

        keranjang = new KeranjangBelanja();
        updateTampilanKeranjang();
    }
    
    private void updateTampilanKeranjang() {
        areaKeranjang.setText("");
        
        if (keranjang.getItems().isEmpty()) {
            areaKeranjang.setText("Keranjang belanja kosong.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-25s %-5s %-17s %-17s\n", "Produk", "Jml", "Harga Satuan", "Subtotal"));
            sb.append("--------------------------------------------------------------------\n");
            
            for (ItemKeranjang item : keranjang.getItems()) {
                sb.append(String.format("%-25s %-5d Rp%-15s Rp%-15s\n",
                        item.getProduk().getNama(),
                        item.getJumlah(),
                        String.format("%,.0f", item.getProduk().getHarga()),
                        String.format("%,.0f", item.getSubtotal())));
            }
            areaKeranjang.setText(sb.toString());
        }
        
        labelTotal.setText("Total: Rp" + String.format("%,.0f", keranjang.hitungTotal()));
    }

    private void initProduk() {
        daftarProduk = new ArrayList<>();
        daftarProduk.add(new ProdukMakanan("Nasi Goreng Spesial", 25000));
        daftarProduk.add(new ProdukMakanan("Ayam Bakar Madu", 35000));
        daftarProduk.add(new ProdukMakanan("Sate Ayam (10 tusuk)", 30000));
        daftarProduk.add(new ProdukMakanan("Indomie Goreng Jumbo", 10000));
        daftarProduk.add(new ProdukMakanan("Roti Bakar Coklat Keju", 18000));

        daftarProduk.add(new ProdukMinuman("Es Teh Manis", 5000));
        daftarProduk.add(new ProdukMinuman("Jus Alpukat", 15000));
        daftarProduk.add(new ProdukMinuman("Kopi Susu Gula Aren", 22000));
        daftarProduk.add(new ProdukMinuman("Air Mineral 600ml", 4000));
        daftarProduk.add(new ProdukMinuman("Soda Gembira", 12000));

        daftarProduk.add(new ProdukPeralatanDapur("Spatula Silikon", 45000));
        daftarProduk.add(new ProdukPeralatanDapur("Panci Anti Lengket 24cm", 150000));
        daftarProduk.add(new ProdukPeralatanDapur("Pisau Dapur Set", 250000));
        daftarProduk.add(new ProdukPeralatanDapur("Talenan Kayu", 75000));
        
        daftarProduk.add(new ProdukKeperluanMandi("Sabun Batang Lifebuoy", 5000));
        daftarProduk.add(new ProdukKeperluanMandi("Shampoo Pantene 135ml", 22000));
        daftarProduk.add(new ProdukKeperluanMandi("Sikat Gigi Oral-B", 12000));
        daftarProduk.add(new ProdukKeperluanMandi("Pasta Gigi Pepsodent", 15000));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TokoOnlineGUI());
    }
}