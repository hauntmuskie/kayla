/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.placeholder.PlaceHolder;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Elza Kayla
 */
public class Utility extends javax.swing.JFrame {

    private Connection conn = new DatabaseConnection().connect();
    private DefaultTableModel tabmode;
    public DataSiswa dk = null;
    PlaceHolder pl;
    public String idsiswa, nama, akademik, prestasi, kehadiran, sikap, partisipasi;

    /**
     * Creates new form utility
     */
    public Utility() {
        initComponents();
        aktif();
        kosong();
        Locale locale = new Locale("id", "ID");
        Locale.setDefault(locale);
        bhapus.setEnabled(false);

        // non editable textfield
        txtid.setEditable(false);
        txtnm.setEditable(false);
        txttepat.setEditable(false);
        txtakurasi.setEditable(false);
        txtjml.setEditable(false);
        txtint.setEditable(false);
        txtpenangan.setEditable(false);
        datatable();
    }

    private void datatable() {
        Object[] Baris = {"ID Siswa", "Nama Siswa", "Utility Akademik", "Utility Prestasi",
            "Utility Kehadiran",
            "Utility Sikap", "Utility Partisipasi"};
        tabmode = new DefaultTableModel(null, Baris);
        try {
            String sql = "SELECT * FROM utility ORDER by id_siswa";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[]{
                    hasil.getString("id_siswa"),
                    hasil.getString("nama_siswa"),
                    hasil.getString("utility_akademik"),
                    hasil.getString("utility_prestasi"),
                    hasil.getString("utility_kehadiran"),
                    hasil.getString("utility_sikap"),
                    hasil.getString("utility_partisipasi"),});
            }
            tabelpenilaian.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void cari() {
        Object[] Baris = {"ID Siswa", "Nama Siswa", "Utility Akademik", "Utility Prestasi",
            "Utility Kehadiran",
            "Utility Sikap", "Utility Partisipasi"};
        tabmode = new DefaultTableModel(null, Baris);
        String cariitem = txtcari.getText();
        try {
            String sql = "SELECT * FROM utility WHERE id_siswa='" + cariitem + "' OR nama_siswa='"
                    + cariitem
                    + "' OR utility_akademik='" + cariitem + "' OR utility_prestasi='" + cariitem
                    + "' OR utility_kehadiran='" + cariitem + "' OR utility_sikap='" + cariitem
                    + "' OR utility_partisipasi='" + cariitem + "'";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                tabmode.addRow(new Object[]{
                    hasil.getString("id_siswa"),
                    hasil.getString("nama_siswa"),
                    hasil.getString("utility_akademik"),
                    hasil.getString("utility_prestasi"),
                    hasil.getString("utility_kehadiran"),
                    hasil.getString("utility_sikap"),
                    hasil.getString("utility_partisipasi"),});
            }
            tabelpenilaian.setModel(tabmode);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "data gagal dipanggil" + e);
        }
    }

    private void kosong() {
        txtid.setText("");
        txtnm.setText("");
        txttepat.setText("");
        txtakurasi.setText("");
        txtjml.setText("");
        txtint.setText("");
        txtpenangan.setText("");
        txtk1.setText("");
        txtk2.setText("");
        txtk3.setText("");
        txtk4.setText("");
        txtk5.setText("");
        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
    }

    private void aktif() {
        txttepat.requestFocus();
    }

    private void tambah() {
        try {
            // Validasi input
            if (txtk1.getText().trim().isEmpty() || txtk2.getText().trim().isEmpty()
                    || txtk3.getText().trim().isEmpty() || txtk4.getText().trim().isEmpty()
                    || txtk5.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Harap klik 'Menghitung Nilai Utility' terlebih dahulu untuk menghitung utility!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idSiswa = txtid.getText();
            String namaSiswa = txtnm.getText();

            // Parse utility values with better error handling
            double utility1, utility2, utility3, utility4, utility5;
            try {
                // Handle potential locale issues by replacing comma with dot if needed
                String k1Val = txtk1.getText().trim().replace(",", ".");
                String k2Val = txtk2.getText().trim().replace(",", ".");
                String k3Val = txtk3.getText().trim().replace(",", ".");
                String k4Val = txtk4.getText().trim().replace(",", ".");
                String k5Val = txtk5.getText().trim().replace(",", ".");

                utility1 = Double.parseDouble(k1Val);
                utility2 = Double.parseDouble(k2Val);
                utility3 = Double.parseDouble(k3Val);
                utility4 = Double.parseDouble(k4Val);
                utility5 = Double.parseDouble(k5Val);

                // Debug: print the values being parsed
                System.out.printf(
                        "DEBUG - Parsing utility values: K1='%s'->%.3f, K2='%s'->%.3f, K3='%s'->%.3f, K4='%s'->%.3f, K5='%s'->%.3f%n",
                        k1Val, utility1, k2Val, utility2, k3Val, utility3, k4Val, utility4,
                        k5Val, utility5);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Nilai utility tidak valid! Harap klik 'Menghitung Nilai Utility' terlebih dahulu.\n\nNilai saat ini:\nK1: '"
                        + txtk1.getText() + "'\nK2: '" + txtk2.getText()
                        + "'\nK3: '" + txtk3.getText()
                        + "'\nK4: '" + txtk4.getText() + "'\nK5: '"
                        + txtk5.getText() + "'\n\nError: "
                        + e.getMessage(),
                        "Error Format", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Cek apakah data sudah ada di tabel utility
            String checkSql = "SELECT COUNT(*) FROM utility WHERE id_siswa = ?";
            PreparedStatement checkStat = conn.prepareStatement(checkSql);
            checkStat.setString(1, idSiswa);
            ResultSet checkResult = checkStat.executeQuery();
            checkResult.next();
            boolean exists = checkResult.getInt(1) > 0;

            if (exists) {
                // Update data yang sudah ada
                String updateSql = "UPDATE utility SET nama_siswa = ?, utility_akademik = ?, utility_prestasi = ?, "
                        + "utility_kehadiran = ?, utility_sikap = ?, utility_partisipasi = ? WHERE id_siswa = ?";
                PreparedStatement updateStat = conn.prepareStatement(updateSql);
                updateStat.setString(1, namaSiswa);
                updateStat.setDouble(2, utility1); // utility_akademik
                updateStat.setDouble(3, utility2); // utility_prestasi
                updateStat.setDouble(4, utility3); // utility_kehadiran
                updateStat.setDouble(5, utility4); // utility_sikap
                updateStat.setDouble(6, utility5); // utility_partisipasi
                updateStat.setString(7, idSiswa);
                updateStat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data utility berhasil diperbarui", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Insert data baru
                String insertSql = "INSERT INTO utility (id_siswa, nama_siswa, utility_akademik, utility_prestasi, utility_kehadiran, utility_sikap, utility_partisipasi) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement insertStat = conn.prepareStatement(insertSql);
                insertStat.setString(1, idSiswa);
                insertStat.setString(2, namaSiswa);
                insertStat.setDouble(3, utility1); // utility_akademik
                insertStat.setDouble(4, utility2); // utility_prestasi
                insertStat.setDouble(5, utility3); // utility_kehadiran
                insertStat.setDouble(6, utility4); // utility_sikap
                insertStat.setDouble(7, utility5); // utility_partisipasi
                insertStat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data utility berhasil disimpan", "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // Refresh the table to show updated data
            datatable();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan: " + e.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public void itemTerpilih() {
        PopupPenilaian PN = new PopupPenilaian();
        PN.du = this;
        txtid.setText(idsiswa);
        txtnm.setText(nama);
        txttepat.setText(akademik);
        txtakurasi.setText(prestasi);
        txtjml.setText(kehadiran);
        txtint.setText(sikap);
        txtpenangan.setText(partisipasi);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtid = new javax.swing.JTextField();
        txtnm = new javax.swing.JTextField();
        bdata = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txttepat = new javax.swing.JTextField();
        txtakurasi = new javax.swing.JTextField();
        txtjml = new javax.swing.JTextField();
        txtint = new javax.swing.JTextField();
        txtpenangan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpenilaian = new javax.swing.JTable();
        bsimpan = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        bbersih = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtk1 = new javax.swing.JTextField();
        txtk2 = new javax.swing.JTextField();
        txtk3 = new javax.swing.JTextField();
        txtk4 = new javax.swing.JTextField();
        txtk5 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        brefresh = new javax.swing.JButton();
        bcek = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        bcari = new javax.swing.JButton();
        cetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(231, 229, 179));
        jPanel2.setForeground(new java.awt.Color(32, 33, 35));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(138, 120, 78));
        jLabel1.setText("Hitung Nilai Utility");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(138, 120, 78));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/back_arrow_14447.png"))); // NOI18N
        jLabel6.setText("Kembali");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(177, 171, 134));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Kurir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 3, 13))); // NOI18N

        txtid.setBackground(new java.awt.Color(234, 228, 213));
        txtid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidActionPerformed(evt);
            }
        });

        txtnm.setBackground(new java.awt.Color(234, 228, 213));

        bdata.setBackground(new java.awt.Color(138, 120, 78));
        bdata.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bdata.setForeground(new java.awt.Color(255, 255, 255));
        bdata.setText("Pilih Data Penilaian");
        bdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bdataActionPerformed(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(0, 0, 0));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setText("Nama Siswa");

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setText("ID Siswa");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(207, 207, 207)
                .addComponent(bdata, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel25))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(bdata))
        );

        jPanel4.setBackground(new java.awt.Color(177, 171, 134));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nilai Bobot", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Historic", 3, 13))); // NOI18N

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("K1");

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("K2");

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("K3");

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("K4");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("K5");

        txttepat.setBackground(new java.awt.Color(234, 228, 213));
        txttepat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttepatActionPerformed(evt);
            }
        });

        txtakurasi.setBackground(new java.awt.Color(234, 228, 213));
        txtakurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtakurasiActionPerformed(evt);
            }
        });

        txtjml.setBackground(new java.awt.Color(234, 228, 213));
        txtjml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtjmlActionPerformed(evt);
            }
        });

        txtint.setBackground(new java.awt.Color(234, 228, 213));
        txtint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtintActionPerformed(evt);
            }
        });

        txtpenangan.setBackground(new java.awt.Color(234, 228, 213));
        txtpenangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpenanganActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txttepat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtakurasi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtjml, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtint, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtpenangan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txttepat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtakurasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtjml, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtpenangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        tabelpenilaian.setBackground(new java.awt.Color(234, 228, 213));
        tabelpenilaian.setForeground(new java.awt.Color(255, 255, 255));
        tabelpenilaian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabelpenilaian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpenilaianMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpenilaian);

        bsimpan.setBackground(new java.awt.Color(138, 120, 78));
        bsimpan.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bsimpan.setForeground(new java.awt.Color(255, 255, 255));
        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bhapus.setBackground(new java.awt.Color(255, 0, 0));
        bhapus.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bhapus.setForeground(new java.awt.Color(255, 255, 255));
        bhapus.setText("Hapus");
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        bbersih.setBackground(new java.awt.Color(138, 120, 78));
        bbersih.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bbersih.setForeground(new java.awt.Color(255, 255, 255));
        bbersih.setText("Bersihkan");
        bbersih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bbersihActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(177, 171, 134));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nilai Utility", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI Historic", 3, 13))); // NOI18N

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("K1");

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("K2");

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("K3");

        jLabel18.setBackground(new java.awt.Color(0, 0, 0));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("K4");

        jLabel19.setBackground(new java.awt.Color(0, 0, 0));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("K5");

        txtk1.setBackground(new java.awt.Color(234, 228, 213));
        txtk1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk1ActionPerformed(evt);
            }
        });

        txtk2.setBackground(new java.awt.Color(234, 228, 213));
        txtk2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk2ActionPerformed(evt);
            }
        });

        txtk3.setBackground(new java.awt.Color(234, 228, 213));
        txtk3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk3ActionPerformed(evt);
            }
        });

        txtk4.setBackground(new java.awt.Color(234, 228, 213));
        txtk4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk4ActionPerformed(evt);
            }
        });

        txtk5.setBackground(new java.awt.Color(234, 228, 213));
        txtk5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtk5ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(51, 51, 51)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtk1)
                    .addComponent(txtk2)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(95, 95, 95)
                        .addComponent(jLabel26)
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addComponent(txtk3)
                    .addComponent(txtk4)
                    .addComponent(txtk5))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txtk1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtk2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtk3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtk4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtk5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        brefresh.setBackground(new java.awt.Color(138, 120, 78));
        brefresh.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        brefresh.setForeground(new java.awt.Color(255, 255, 255));
        brefresh.setText("Refresh");
        brefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshActionPerformed(evt);
            }
        });

        bcek.setBackground(new java.awt.Color(138, 120, 78));
        bcek.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bcek.setForeground(new java.awt.Color(255, 255, 255));
        bcek.setText("Mengitung Nilai Utility");
        bcek.setToolTipText("");
        bcek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcekActionPerformed(evt);
            }
        });

        txtcari.setBackground(new java.awt.Color(234, 228, 213));

        bcari.setBackground(new java.awt.Color(138, 120, 78));
        bcari.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        bcari.setForeground(new java.awt.Color(255, 255, 255));
        bcari.setText("Cari");
        bcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcariActionPerformed(evt);
            }
        });

        cetak.setBackground(new java.awt.Color(138, 120, 78));
        cetak.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        cetak.setForeground(new java.awt.Color(255, 255, 255));
        cetak.setText("Cetak");
        cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(548, 548, 548)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(bcek)
                                        .addGap(19, 19, 19)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(41, 41, 41)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(brefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(bcari, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(bsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bbersih)
                                .addGap(18, 18, 18)
                                .addComponent(cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(bhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(bcari)
                                .addGap(338, 338, 338))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(brefresh))
                                .addGap(337, 337, 337))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(85, 85, 85)
                                .addComponent(bcek, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan)
                    .addComponent(bbersih)
                    .addComponent(bhapus)
                    .addComponent(cetak))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cetakActionPerformed
        // TODO add your handling code here:
        try {
            String report = "./src/reports/laporan_utility_siswa.jasper";
            HashMap<String, Object> param = new HashMap<>();
            // param.put("parameter1", cari.getText());
            JasperPrint print = JasperFillManager.fillReport(report, param, conn);
            JasperViewer.viewReport(print, false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex);
        }
    }//GEN-LAST:event_cetakActionPerformed

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLabel6MouseClicked
        new MetodeSmart().setVisible(true);
        this.dispose(); // TODO add your handling code here:
    }// GEN-LAST:event_jLabel6MouseClicked

    private void txtidActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtidActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtidActionPerformed

    private void bdataActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bdataActionPerformed
        PopupPenilaian PN = new PopupPenilaian();
        PN.du = this;
        PN.setVisible(true);
        PN.setResizable(false);
        kosong();
    }// GEN-LAST:event_bdataActionPerformed

    private void txttepatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txttepatActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txttepatActionPerformed

    private void txtakurasiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtakurasiActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtakurasiActionPerformed

    private void txtjmlActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtjmlActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtjmlActionPerformed

    private void txtintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtintActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtintActionPerformed

    private void txtpenanganActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtpenanganActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtpenanganActionPerformed

    private void tabelpenilaianMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tabelpenilaianMouseClicked
        bsimpan.setEnabled(false);
        bhapus.setEnabled(true);
        int bar = tabelpenilaian.getSelectedRow();
        String a = tabmode.getValueAt(bar, 0).toString();
        String b = tabmode.getValueAt(bar, 1).toString();
        String c = tabmode.getValueAt(bar, 2).toString();
        String d = tabmode.getValueAt(bar, 3).toString();
        String e = tabmode.getValueAt(bar, 4).toString();
        String f = tabmode.getValueAt(bar, 5).toString();
        String g = tabmode.getValueAt(bar, 6).toString();
        txtid.setText(a);
        txtnm.setText(b);
        txtk1.setText(c);
        txtk2.setText(d);
        txtk3.setText(e);
        txtk4.setText(f);
        txtk5.setText(g);
    }// GEN-LAST:event_tabelpenilaianMouseClicked

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bsimpanActionPerformed
        String textId, textNama, textTepat, textAkurasi, textJml, textInt, textPenangan;

        textId = txtid.getText();
        textNama = txtnm.getText();
        textTepat = txtk1.getText();
        textAkurasi = txtk2.getText();
        textJml = txtk3.getText();
        textInt = txtk4.getText();
        textPenangan = txtk5.getText();
        if ((textId.equals("") | (textNama.equals("") | textTepat.equals("") | textAkurasi.equals("")
                | textJml.equals("") | textInt.equals("") | textPenangan.equals("")))) {
            JOptionPane.showMessageDialog(null, "Pengisian Data Tidak Boleh Kosong");
            txtid.requestFocus();
        } else {
            tambah();
        }
    }// GEN-LAST:event_bsimpanActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bhapusActionPerformed
        int ok = JOptionPane.showConfirmDialog(null, "Yakin ingin menghapus utility ini?", "Konfirmasi dialog!",
                JOptionPane.YES_NO_OPTION);
        if (ok == 0) {
            int row = tabelpenilaian.getSelectedRow();
            String cell = tabelpenilaian.getModel().getValueAt(row, 0).toString();
            String sql = "delete from utility where id_siswa = '" + cell + "'";
            try {
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data gagal dihapus" + e);
            }
            datatable();
            kosong();
            bsimpan.setEnabled(true);
            bhapus.setEnabled(false);
        }
    }// GEN-LAST:event_bhapusActionPerformed

    private void bbersihActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bbersihActionPerformed
        kosong();
    }// GEN-LAST:event_bbersihActionPerformed

    private void txtk1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk1ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk1ActionPerformed

    private void txtk2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk2ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk2ActionPerformed

    private void txtk3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk3ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk3ActionPerformed

    private void txtk4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk4ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk4ActionPerformed

    private void txtk5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtk5ActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtk5ActionPerformed

    private void brefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_brefreshActionPerformed
        datatable();
        txtcari.setText("");
        pl = new PlaceHolder(txtcari, "Pencarian data...");
        bsimpan.setEnabled(true);

        bhapus.setEnabled(false);
    }// GEN-LAST:event_brefreshActionPerformed

    private void bcekActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcekActionPerformed
        try {
            // Validasi input tidak kosong
            if (txtid.getText().trim().isEmpty() || txtnm.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "ID Siswa dan Nama Siswa harus diisi terlebih dahulu!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1. Ambil input dari field (yang sudah diisi dari tabel penilaian)
            // CATATAN: Field ini berisi nilai yang sudah dikonversi (dari tabel penilaian),
            // bukan nilai mentah dari tabel alternatif
            String akademikStr = txttepat.getText().trim();
            String prestasiStr = txtakurasi.getText().trim();
            String kehadiranStr = txtjml.getText().trim();
            String sikapStr = txtint.getText().trim();
            String partisipasiStr = txtpenangan.getText().trim();

            // Validasi field tidak kosong
            if (akademikStr.isEmpty() || prestasiStr.isEmpty() || kehadiranStr.isEmpty()
                    || sikapStr.isEmpty() || partisipasiStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field kriteria harus diisi!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int skorK1 = 0, skorK2 = 0, skorK3 = 0, skorK4 = 0, skorK5 = 0;

            // 2. Konversi input ke skor numerik (karena field sudah berisi nilai
            // terkonversi dari tabel penilaian)
            try {
                skorK1 = Integer.parseInt(akademikStr);
                skorK2 = Integer.parseInt(prestasiStr);
                skorK3 = Integer.parseInt(kehadiranStr);
                skorK4 = Integer.parseInt(sikapStr);
                skorK5 = Integer.parseInt(partisipasiStr);

                // Debug: tampilkan skor yang digunakan
                System.out.printf(
                        "DEBUG - Skor dari tabel penilaian: K1=%d, K2=%d, K3=%d, K4=%d, K5=%d%n",
                        skorK1, skorK2, skorK3, skorK4, skorK5);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null,
                        "Semua field kriteria harus berupa angka (skor yang sudah dikonversi)!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 3. Hitung Utility sesuai dengan formula paper (Table 4.20)
            // Ui(ai) = (Cij - CjMin) / (CjMax - CjMin)
            // Min/Max values berdasarkan Table 4.9 dari paper
            int[] minValues = {90, 60, 60, 85, 75}; // Min values dari semua siswa di Table 4.9
            int[] maxValues = {100, 90, 100, 100, 100}; // Max values dari semua siswa di Table 4.9
            int[] skorArr = {skorK1, skorK2, skorK3, skorK4, skorK5};
            double[] utilities = new double[5];

            // Debug info - tampilkan skor yang digunakan
            String debugInfo = String.format("DEBUG - Skor Konversi:\nK1=%d, K2=%d, K3=%d, K4=%d, K5=%d\n",
                    skorK1, skorK2, skorK3, skorK4, skorK5);
            System.out.println(debugInfo);

            for (int i = 0; i < 5; i++) {
                if (maxValues[i] == minValues[i]) {
                    utilities[i] = 0.0;
                } else {
                    utilities[i] = (double) (skorArr[i] - minValues[i])
                            / (double) (maxValues[i] - minValues[i]);
                }
                // Pastikan dalam range 0-1
                utilities[i] = Math.max(0.0, Math.min(1.0, utilities[i]));

                // Debug utility calculation
                System.out.printf("K%d: (%d-%d)/(%d-%d) = %.3f\n",
                        i + 1, skorArr[i], minValues[i], maxValues[i], minValues[i],
                        utilities[i]);
            }

            // 4. Tampilkan hasil utility pada field K1-K5 (format 3 decimal places)
            txtk1.setText(String.format("%.3f", utilities[0]));
            txtk2.setText(String.format("%.3f", utilities[1]));
            txtk3.setText(String.format("%.3f", utilities[2]));
            txtk4.setText(String.format("%.3f", utilities[3]));
            txtk5.setText(String.format("%.3f", utilities[4]));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }// GEN-LAST:event_bcekActionPerformed

    private void bcariActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bcariActionPerformed
        cari();
    }// GEN-LAST:event_bcariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                    .getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Utility.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Utility.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Utility.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Utility.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Utility().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bbersih;
    private javax.swing.JButton bcari;
    private javax.swing.JButton bcek;
    private javax.swing.JButton bdata;
    private javax.swing.JButton bhapus;
    private javax.swing.JButton brefresh;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton cetak;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelpenilaian;
    private javax.swing.JTextField txtakurasi;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtint;
    private javax.swing.JTextField txtjml;
    private javax.swing.JTextField txtk1;
    private javax.swing.JTextField txtk2;
    private javax.swing.JTextField txtk3;
    private javax.swing.JTextField txtk4;
    private javax.swing.JTextField txtk5;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtpenangan;
    private javax.swing.JTextField txttepat;
    // End of variables declaration//GEN-END:variables
}
