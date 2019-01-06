package casher;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Blob;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class HomeForm extends javax.swing.JFrame {

    Connection con;
    Statement stat;
    ResultSet rs;
    
    String idname;
    double pengeluaran = 0;
    double pemasukan = 0;
    String pengeluaranFinal;
    String pemasukanFinal;
    String aktivitas;
    String tanggal;
    DefaultTableModel tableModel;
    DefaultTableModel tableModelPemasukan;
    Object[] tableObject = new Object[3];
    Object[] tableObjectPemasukan = new Object[3];
    String jmlPengeluaran;
    double jumlahPengeluaran = 0;
    String jmlPemasukan;
    double jumlahPemasukan = 0;
    String totaluang;
    boolean PengeluaranBtn = false;
    boolean PemasukanBtn = false;
    
    String pengeluaranHAPUS;
    String aktivitasHAPUS;
    String tanggalHAPUS;
    
    String pengeluaranEDIT;
    String aktivitasEDIT;
    String tanggalEDIT;
    
    String pemasukanHAPUS;
    String aktivitasPemasukanHAPUS;
    String tanggalPemasukanHAPUS;
    
    String pemasukanEDIT;
    String aktivitasPemasukanEDIT;
    String tanggalPemasukanEDIT;
    String fotoPathEdit;
    
    String password;
    String nama;
    String jeniskelamin;
    
    String namahomeEdit;
    String idhomeEdit;
    String passhomeEdit;
    String jkhomeEdit;
    
    public HomeForm() {
        initComponents();
        connect();
        
        this.getContentPane().setBackground(new Color(236,221,15));
        tableModel = (DefaultTableModel) tabelPengeluaran.getModel();
        tableModelPemasukan = (DefaultTableModel) tabelPemasukan.getModel();

        fotoHome.setVisible(true);
        nameHome.setVisible(true);
        totaluangHome.setVisible(true);
        totalUangHome.setVisible(true);
        
        namaEdit.setVisible(false);
        namaidEdit.setVisible(false);
        passEdit.setVisible(false);
        jeniskelaminEdit.setVisible(false);
        fotoEdit.setVisible(false);
        choosefotoEdit.setVisible(false);
        
        namaeditTxt.setVisible(false);
        ideditTxt.setVisible(false);
        passeditTxt.setVisible(false);
        lakiEdit.setVisible(false);
        perempuanEdit.setVisible(false);
        fotoeditTxt.setVisible(false);
        
        pengeluaranInfo.setEnabled(false);
        aktivitasInfo.setEnabled(false);
        tanggalInfo.setEnabled(false);
        simpanEdit.setVisible(false);
        batalEdit.setVisible(false);
        
        pemasukanInfo.setEnabled(false);
        aktivitasInfoPemasukan.setEnabled(false);
        tanggalInfoPemasukan.setEnabled(false);
        simpanEditPemasukan.setVisible(false);
        batalEditPemasukan.setVisible(false);
    }
    
    public void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myney", "root", "");
            stat = con.createStatement();
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, "Koneksi Gagal");
        }
    }
    
    public void masuk(String id)
    {
        idname = id;
        getData();
        showProfile();
        tampilkanPengeluaran();
        tampilkanPemasukan();
        tampilkanTotalUang();
    }
    
    public void getData()
    {
        try
        {
            rs = stat.executeQuery("SELECT * FROM user WHERE id = '" + idname + "'");
            
            if(rs.next())
            {
                nama = rs.getString("nama");
                password = rs.getString("password");
                jeniskelamin = rs.getString("jeniskelamin");
            }
        } catch(Exception ex)
        {
            
        }
    }
    
    public void showProfile()
    {
        try
        {    
            rs = stat.executeQuery("SELECT * FROM user WHERE id = '" + idname + "'");
            
            if(rs.next())
            {
                nameHome.setText(rs.getString("nama"));
                Blob foto = rs.getBlob("foto");
                byte[] fotoByte = foto.getBytes(1, (int)foto.length());
                ImageIcon imageIcon = new ImageIcon(fotoByte);
                Image image = imageIcon.getImage();
                Image im = image.getScaledInstance(100,100,100);
                ImageIcon imIcon = new ImageIcon(im);
                fotoHome.setText("");
                fotoHome.setIcon(imIcon);
            }
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public void tampilkanPengeluaran()
    {
        try
        {
            rs = stat.executeQuery("SELECT * FROM aktivitas_user WHERE id = '" + idname + "'");
            
            while(rs.next())
            {
                tableObject[0] = rs.getString("pengeluaran");
                tableObject[1] = rs.getString("aktivitas");
                tableObject[2] = rs.getString("tanggal");
                tableModel.addRow(tableObject);
                
                jmlPengeluaran = rs.getString("pengeluaran");
                jumlahPengeluaran += Double.parseDouble(jmlPengeluaran.replace(".", ""));
            }
            
            NumberFormat nf = NumberFormat.getInstance();
            totalPengeluaranInfo.setText("Rp " + nf.format(jumlahPengeluaran).replaceAll(",", "."));
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    public void tampilkanPemasukan()
    {
        try
        {
            rs = stat.executeQuery("SELECT * FROM aktivitas_user_pemasukan WHERE id = '" + idname + "'");
            
            while(rs.next())
            {
                tableObjectPemasukan[0] = rs.getString("pemasukan");
                tableObjectPemasukan[1] = rs.getString("aktivitas");
                tableObjectPemasukan[2] = rs.getString("tanggal");
                tableModelPemasukan.addRow(tableObjectPemasukan);
                
                jmlPemasukan = rs.getString("pemasukan");
                jumlahPemasukan += Double.parseDouble(jmlPemasukan.replace(".", ""));
            }
            
            NumberFormat nf = NumberFormat.getInstance();
            totalPemasukanInfo.setText("Rp " + nf.format(jumlahPemasukan).replaceAll(",", "."));
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    
    public void tampilkanTotalUang()
    {
        NumberFormat nf = NumberFormat.getInstance();
        totaluang = nf.format(jumlahPemasukan - jumlahPengeluaran).replaceAll(",", ".");
        totalUangHome.setText("Rp " + totaluang);
    }
    
    public void refreshPengeluaran()
    {
        tableModel.setRowCount(0);
        jumlahPengeluaran = 0;
        pengeluaranInfo.setEnabled(false);
        aktivitasInfo.setEnabled(false);
        tanggalInfo.setEnabled(false);
        pengeluaranInfo.setText("");
        aktivitasInfo.setText("");
        tanggalInfo.setDate(null);
        simpanEdit.setVisible(false);
        batalEdit.setVisible(false);
        tampilkanPengeluaran();
    }
    
    public void refreshPemasukan()
    {
        tableModelPemasukan.setRowCount(0);
        jumlahPemasukan = 0;
        pemasukanInfo.setEnabled(false);
        aktivitasInfoPemasukan.setEnabled(false);
        tanggalInfoPemasukan.setEnabled(false);
        pemasukanInfo.setText("");
        aktivitasInfoPemasukan.setText("");
        tanggalInfoPemasukan.setDate(null);
        simpanEditPemasukan.setVisible(false);
        batalEditPemasukan.setVisible(false);
        tampilkanPemasukan();
    }
    
    public void refreshAktivitas()
    {
        totalUangHome.setText("");
        NumberFormat nf = NumberFormat.getInstance();
        totaluang = nf.format(jumlahPemasukan - jumlahPengeluaran).replaceAll(",", ".");
        totalUangHome.setText("Rp " + totaluang);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jkeditGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        Aktivitas = new javax.swing.JPanel();
        inputuangTxt = new javax.swing.JTextField();
        jenisTxt = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        aktivitasTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tanggalChooser = new com.toedter.calendar.JDateChooser();
        simpanBtn = new javax.swing.JButton();
        hapusBtn = new javax.swing.JButton();
        pemasukanBtn = new javax.swing.JButton();
        pengeluaranBtn = new javax.swing.JButton();
        Profil = new javax.swing.JPanel();
        keluarBtn = new javax.swing.JButton();
        editprofilBtn = new javax.swing.JButton();
        nameHome = new javax.swing.JLabel();
        fotoHome = new javax.swing.JLabel();
        totaluangHome = new javax.swing.JLabel();
        totalUangHome = new javax.swing.JLabel();
        namaEdit = new javax.swing.JLabel();
        namaidEdit = new javax.swing.JLabel();
        passEdit = new javax.swing.JLabel();
        jeniskelaminEdit = new javax.swing.JLabel();
        fotoEdit = new javax.swing.JLabel();
        namaeditTxt = new javax.swing.JTextField();
        ideditTxt = new javax.swing.JTextField();
        passeditTxt = new javax.swing.JPasswordField();
        lakiEdit = new javax.swing.JRadioButton();
        perempuanEdit = new javax.swing.JRadioButton();
        choosefotoEdit = new javax.swing.JButton();
        fotoeditTxt = new javax.swing.JTextField();
        Pengeluaran = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPengeluaran = new javax.swing.JTable();
        refreshBtn = new javax.swing.JButton();
        infoPengeluaranPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pengeluaranInfo = new javax.swing.JTextField();
        aktivitasInfo = new javax.swing.JTextField();
        tanggalInfo = new com.toedter.calendar.JDateChooser();
        simpanEdit = new javax.swing.JButton();
        batalEdit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        totalPengeluaranInfo = new javax.swing.JLabel();
        deleteBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        Pemasukan = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelPemasukan = new javax.swing.JTable();
        refreshBtnPemasukan = new javax.swing.JButton();
        editBtnPemasukan = new javax.swing.JButton();
        deleteBtnPemasukan = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        totalPemasukanInfo = new javax.swing.JLabel();
        infoPemasukanPanel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pemasukanInfo = new javax.swing.JTextField();
        aktivitasInfoPemasukan = new javax.swing.JTextField();
        tanggalInfoPemasukan = new com.toedter.calendar.JDateChooser();
        simpanEditPemasukan = new javax.swing.JButton();
        batalEditPemasukan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N

        Aktivitas.setBackground(new java.awt.Color(255, 255, 255));
        Aktivitas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputuangTxt.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        Aktivitas.add(inputuangTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 190, -1));

        jenisTxt.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jenisTxt.setText("Pengeluaran (Rp)");
        Aktivitas.add(jenisTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 123, -1, -1));

        jLabel2.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel2.setText("Aktivitas");
        Aktivitas.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 183, -1, -1));

        aktivitasTxt.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        Aktivitas.add(aktivitasTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 190, -1));

        jLabel3.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        jLabel3.setText("Tanggal");
        Aktivitas.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, 29));

        tanggalChooser.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        Aktivitas.add(tanggalChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 240, 190, 29));

        simpanBtn.setBackground(new java.awt.Color(57, 159, 221));
        simpanBtn.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        simpanBtn.setForeground(new java.awt.Color(255, 255, 255));
        simpanBtn.setText("Simpan");
        simpanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanBtnActionPerformed(evt);
            }
        });
        Aktivitas.add(simpanBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 300, -1, -1));

        hapusBtn.setBackground(new java.awt.Color(255, 45, 24));
        hapusBtn.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        hapusBtn.setForeground(new java.awt.Color(255, 255, 255));
        hapusBtn.setText("Hapus");
        hapusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBtnActionPerformed(evt);
            }
        });
        Aktivitas.add(hapusBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, -1, -1));

        pemasukanBtn.setBackground(new java.awt.Color(57, 159, 221));
        pemasukanBtn.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        pemasukanBtn.setForeground(new java.awt.Color(255, 255, 255));
        pemasukanBtn.setText("Pemasukan");
        pemasukanBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pemasukanBtnMouseClicked(evt);
            }
        });
        pemasukanBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pemasukanBtnActionPerformed(evt);
            }
        });
        Aktivitas.add(pemasukanBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 28, 120, 40));

        pengeluaranBtn.setBackground(new java.awt.Color(57, 159, 221));
        pengeluaranBtn.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        pengeluaranBtn.setForeground(new java.awt.Color(255, 255, 255));
        pengeluaranBtn.setText("Pengeluaran");
        pengeluaranBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pengeluaranBtnMouseClicked(evt);
            }
        });
        pengeluaranBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pengeluaranBtnActionPerformed(evt);
            }
        });
        Aktivitas.add(pengeluaranBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 28, -1, 40));

        Profil.setBackground(new java.awt.Color(255, 255, 255));
        Profil.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profil", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Sans Unicode", 0, 12))); // NOI18N

        keluarBtn.setBackground(new java.awt.Color(255, 45, 24));
        keluarBtn.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        keluarBtn.setForeground(new java.awt.Color(255, 255, 255));
        keluarBtn.setText("Keluar");
        keluarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keluarBtnActionPerformed(evt);
            }
        });

        editprofilBtn.setBackground(new java.awt.Color(57, 159, 221));
        editprofilBtn.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 14)); // NOI18N
        editprofilBtn.setForeground(new java.awt.Color(255, 255, 255));
        editprofilBtn.setText("Edit Profil");
        editprofilBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editprofilBtnActionPerformed(evt);
            }
        });

        nameHome.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N
        nameHome.setText("name");

        fotoHome.setText("Foto");

        totaluangHome.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        totaluangHome.setText("Total Uang");

        totalUangHome.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 12)); // NOI18N
        totalUangHome.setText("jLabel12");

        namaEdit.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        namaEdit.setText("Nama");

        namaidEdit.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        namaidEdit.setText("Nama ID");

        passEdit.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        passEdit.setText("Password");

        jeniskelaminEdit.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        jeniskelaminEdit.setText("Jenis Kelamin");

        fotoEdit.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        fotoEdit.setText("Foto");

        passeditTxt.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N

        lakiEdit.setBackground(new java.awt.Color(255, 255, 255));
        jkeditGroup.add(lakiEdit);
        lakiEdit.setText("Laki-laki");

        perempuanEdit.setBackground(new java.awt.Color(255, 255, 255));
        jkeditGroup.add(perempuanEdit);
        perempuanEdit.setText("Perempuan");

        choosefotoEdit.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        choosefotoEdit.setText("...");
        choosefotoEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choosefotoEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ProfilLayout = new javax.swing.GroupLayout(Profil);
        Profil.setLayout(ProfilLayout);
        ProfilLayout.setHorizontalGroup(
            ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProfilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ProfilLayout.createSequentialGroup()
                        .addComponent(namaEdit)
                        .addGap(57, 57, 57)
                        .addComponent(namaeditTxt))
                    .addGroup(ProfilLayout.createSequentialGroup()
                        .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namaidEdit)
                            .addComponent(passEdit)
                            .addComponent(fotoEdit))
                        .addGap(36, 36, 36)
                        .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ProfilLayout.createSequentialGroup()
                                .addComponent(fotoeditTxt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(choosefotoEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ideditTxt)
                            .addComponent(passeditTxt)))
                    .addGroup(ProfilLayout.createSequentialGroup()
                        .addComponent(keluarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(editprofilBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(ProfilLayout.createSequentialGroup()
                        .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(ProfilLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(ProfilLayout.createSequentialGroup()
                                        .addComponent(totaluangHome)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(totalUangHome))
                                    .addGroup(ProfilLayout.createSequentialGroup()
                                        .addGap(31, 31, 31)
                                        .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(fotoHome)
                                            .addComponent(nameHome)))))
                            .addGroup(ProfilLayout.createSequentialGroup()
                                .addComponent(jeniskelaminEdit)
                                .addGap(18, 18, 18)
                                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(perempuanEdit)
                                    .addComponent(lakiEdit))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        ProfilLayout.setVerticalGroup(
            ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProfilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaEdit)
                    .addComponent(namaeditTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namaidEdit)
                    .addComponent(ideditTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passEdit)
                    .addComponent(passeditTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jeniskelaminEdit)
                    .addGroup(ProfilLayout.createSequentialGroup()
                        .addComponent(lakiEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(perempuanEdit)))
                .addGap(15, 15, 15)
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fotoEdit)
                    .addComponent(choosefotoEdit)
                    .addComponent(fotoeditTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(fotoHome)
                .addGap(18, 18, 18)
                .addComponent(nameHome)
                .addGap(18, 18, 18)
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totaluangHome)
                    .addComponent(totalUangHome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(ProfilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keluarBtn)
                    .addComponent(editprofilBtn))
                .addContainerGap())
        );

        Aktivitas.add(Profil, new org.netbeans.lib.awtextra.AbsoluteConstraints(503, 11, -1, 420));

        jTabbedPane1.addTab("Aktivitas", Aktivitas);

        Pengeluaran.setBackground(new java.awt.Color(255, 255, 255));

        tabelPengeluaran.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N
        tabelPengeluaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pengeluaran", "Aktivitas", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPengeluaran.setGridColor(new java.awt.Color(255, 255, 255));
        tabelPengeluaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPengeluaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelPengeluaran);
        if (tabelPengeluaran.getColumnModel().getColumnCount() > 0) {
            tabelPengeluaran.getColumnModel().getColumn(0).setResizable(false);
            tabelPengeluaran.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabelPengeluaran.getColumnModel().getColumn(1).setResizable(false);
            tabelPengeluaran.getColumnModel().getColumn(2).setResizable(false);
            tabelPengeluaran.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        refreshBtn.setBackground(new java.awt.Color(236, 221, 15));
        refreshBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casher/image/refreshbtn.png"))); // NOI18N
        refreshBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnActionPerformed(evt);
            }
        });

        infoPengeluaranPanel.setBackground(new java.awt.Color(255, 255, 255));
        infoPengeluaranPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Sans Unicode", 0, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        jLabel4.setText("Pengeluaran (Rp)");

        jLabel6.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        jLabel6.setText("Aktivitas");

        jLabel8.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        jLabel8.setText("Tanggal");

        pengeluaranInfo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N

        aktivitasInfo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N

        tanggalInfo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N

        simpanEdit.setBackground(new java.awt.Color(57, 159, 221));
        simpanEdit.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        simpanEdit.setForeground(new java.awt.Color(255, 255, 255));
        simpanEdit.setText("Simpan");
        simpanEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanEditActionPerformed(evt);
            }
        });

        batalEdit.setBackground(new java.awt.Color(255, 45, 24));
        batalEdit.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        batalEdit.setForeground(new java.awt.Color(255, 255, 255));
        batalEdit.setText("Batal");
        batalEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout infoPengeluaranPanelLayout = new javax.swing.GroupLayout(infoPengeluaranPanel);
        infoPengeluaranPanel.setLayout(infoPengeluaranPanelLayout);
        infoPengeluaranPanelLayout.setHorizontalGroup(
            infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPengeluaranPanelLayout.createSequentialGroup()
                .addGroup(infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pengeluaranInfo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aktivitasInfo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalInfo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addGroup(infoPengeluaranPanelLayout.createSequentialGroup()
                        .addComponent(batalEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(simpanEdit)))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        infoPengeluaranPanelLayout.setVerticalGroup(
            infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPengeluaranPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pengeluaranInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(aktivitasInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(tanggalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPengeluaranPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanEdit)
                    .addComponent(batalEdit))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N
        jLabel5.setText("Total Pengeluaran");

        totalPengeluaranInfo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N
        totalPengeluaranInfo.setText("0");

        deleteBtn.setBackground(new java.awt.Color(236, 221, 15));
        deleteBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casher/image/deletebtn.png"))); // NOI18N
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        editBtn.setBackground(new java.awt.Color(236, 221, 15));
        editBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casher/image/editBtn.png"))); // NOI18N
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PengeluaranLayout = new javax.swing.GroupLayout(Pengeluaran);
        Pengeluaran.setLayout(PengeluaranLayout);
        PengeluaranLayout.setHorizontalGroup(
            PengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PengeluaranLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoPengeluaranPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PengeluaranLayout.createSequentialGroup()
                        .addGroup(PengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PengeluaranLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(totalPengeluaranInfo))
                            .addGroup(PengeluaranLayout.createSequentialGroup()
                                .addComponent(refreshBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(editBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PengeluaranLayout.setVerticalGroup(
            PengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PengeluaranLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PengeluaranLayout.createSequentialGroup()
                        .addGroup(PengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(refreshBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PengeluaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(totalPengeluaranInfo))
                        .addGap(18, 18, 18)
                        .addComponent(infoPengeluaranPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Pengeluaran", Pengeluaran);

        Pemasukan.setBackground(new java.awt.Color(255, 255, 255));

        tabelPemasukan.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N
        tabelPemasukan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pemasukan", "Aktivitas", "Tanggal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelPemasukan.setGridColor(new java.awt.Color(255, 255, 255));
        tabelPemasukan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelPemasukanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelPemasukan);
        if (tabelPemasukan.getColumnModel().getColumnCount() > 0) {
            tabelPemasukan.getColumnModel().getColumn(0).setResizable(false);
            tabelPemasukan.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabelPemasukan.getColumnModel().getColumn(1).setResizable(false);
            tabelPemasukan.getColumnModel().getColumn(2).setResizable(false);
            tabelPemasukan.getColumnModel().getColumn(2).setPreferredWidth(30);
        }

        refreshBtnPemasukan.setBackground(new java.awt.Color(236, 221, 15));
        refreshBtnPemasukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casher/image/refreshbtn.png"))); // NOI18N
        refreshBtnPemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshBtnPemasukanActionPerformed(evt);
            }
        });

        editBtnPemasukan.setBackground(new java.awt.Color(236, 221, 15));
        editBtnPemasukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casher/image/editBtn.png"))); // NOI18N
        editBtnPemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnPemasukanActionPerformed(evt);
            }
        });

        deleteBtnPemasukan.setBackground(new java.awt.Color(236, 221, 15));
        deleteBtnPemasukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casher/image/deletebtn.png"))); // NOI18N
        deleteBtnPemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnPemasukanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N
        jLabel7.setText("Total Pemasukan");

        totalPemasukanInfo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 13)); // NOI18N
        totalPemasukanInfo.setText("0");

        infoPemasukanPanel.setBackground(new java.awt.Color(255, 255, 255));
        infoPemasukanPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Sans Unicode", 0, 12))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        jLabel9.setText("Pemasukan (Rp)");

        jLabel10.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        jLabel10.setText("Aktivitas");

        jLabel11.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        jLabel11.setText("Tanggal");

        pemasukanInfo.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N

        aktivitasInfoPemasukan.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N

        tanggalInfoPemasukan.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N

        simpanEditPemasukan.setBackground(new java.awt.Color(57, 159, 221));
        simpanEditPemasukan.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        simpanEditPemasukan.setForeground(new java.awt.Color(255, 255, 255));
        simpanEditPemasukan.setText("Simpan");
        simpanEditPemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanEditPemasukanActionPerformed(evt);
            }
        });

        batalEditPemasukan.setBackground(new java.awt.Color(255, 45, 24));
        batalEditPemasukan.setFont(new java.awt.Font("Lucida Sans Unicode", 0, 11)); // NOI18N
        batalEditPemasukan.setForeground(new java.awt.Color(255, 255, 255));
        batalEditPemasukan.setText("Batal");
        batalEditPemasukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batalEditPemasukanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout infoPemasukanPanelLayout = new javax.swing.GroupLayout(infoPemasukanPanel);
        infoPemasukanPanel.setLayout(infoPemasukanPanelLayout);
        infoPemasukanPanelLayout.setHorizontalGroup(
            infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPemasukanPanelLayout.createSequentialGroup()
                .addGroup(infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pemasukanInfo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aktivitasInfoPemasukan, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tanggalInfoPemasukan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addGroup(infoPemasukanPanelLayout.createSequentialGroup()
                        .addComponent(batalEditPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(simpanEditPemasukan)))
                .addGap(0, 17, Short.MAX_VALUE))
        );
        infoPemasukanPanelLayout.setVerticalGroup(
            infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPemasukanPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(pemasukanInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(aktivitasInfoPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(tanggalInfoPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(infoPemasukanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpanEditPemasukan)
                    .addComponent(batalEditPemasukan))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PemasukanLayout = new javax.swing.GroupLayout(Pemasukan);
        Pemasukan.setLayout(PemasukanLayout);
        PemasukanLayout.setHorizontalGroup(
            PemasukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PemasukanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PemasukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoPemasukanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PemasukanLayout.createSequentialGroup()
                        .addGroup(PemasukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PemasukanLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(totalPemasukanInfo))
                            .addGroup(PemasukanLayout.createSequentialGroup()
                                .addComponent(refreshBtnPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(editBtnPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(deleteBtnPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PemasukanLayout.setVerticalGroup(
            PemasukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PemasukanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PemasukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PemasukanLayout.createSequentialGroup()
                        .addGroup(PemasukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(editBtnPemasukan, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(refreshBtnPemasukan, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                            .addComponent(deleteBtnPemasukan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PemasukanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(totalPemasukanInfo))
                        .addGap(18, 18, 18)
                        .addComponent(infoPemasukanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Pemasukan", Pemasukan);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hapusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBtnActionPerformed
        pengeluaran = 0;
        pengeluaranFinal = "";
        inputuangTxt.setText("");
        aktivitas = "";
        aktivitasTxt.setText("");
        tanggalChooser.setDate(null);
    }//GEN-LAST:event_hapusBtnActionPerformed

    private void simpanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanBtnActionPerformed
        try
        { 
            pengeluaran = Double.parseDouble(inputuangTxt.getText());
            NumberFormat nf = NumberFormat.getInstance();
            pengeluaranFinal = nf.format(pengeluaran).replaceAll(",", ".");
            aktivitas = aktivitasTxt.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            tanggal = String.valueOf(sdf.format(tanggalChooser.getDate()));

            if(idname.isEmpty() || pengeluaranFinal.isEmpty() || aktivitas.isEmpty() || tanggal.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Lengkapi Aktivitas Anda", "PERINGATAN", JOptionPane.WARNING_MESSAGE);
            } else
            {
                if(PengeluaranBtn)
                {
                    stat.executeUpdate("INSERT INTO aktivitas_user values('" + idname + "', '" + pengeluaranFinal + "', '" + aktivitas + "', '" + tanggal + "')");
                    JOptionPane.showMessageDialog(this, "Berhasil di simpan");
                } else if(PemasukanBtn)
                {
                    stat.executeUpdate("INSERT INTO aktivitas_user_pemasukan values('" + idname + "', '" + pengeluaranFinal + "', '" + aktivitas + "', '" + tanggal + "')");
                    JOptionPane.showMessageDialog(this, "Berhasil di simpan");
                } else
                {
                    JOptionPane.showMessageDialog(this, "Pilih jenis aktivitas keuangan Anda");
                }
            }
            
            pengeluaran = 0;
            pengeluaranFinal = "";
            inputuangTxt.setText("");
            aktivitas = "";
            aktivitasTxt.setText("");
            tanggalChooser.setDate(null);
            
            refreshPengeluaran();
            refreshPemasukan();
            refreshAktivitas();
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_simpanBtnActionPerformed

    private void batalEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalEditActionPerformed
        pengeluaranInfo.setEnabled(false);
        aktivitasInfo.setEnabled(false);
        tanggalInfo.setEnabled(false);
        simpanEdit.setVisible(false);
        batalEdit.setVisible(false);
    }//GEN-LAST:event_batalEditActionPerformed

    private void simpanEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanEditActionPerformed
        try
        {
            int row = tabelPengeluaran.getSelectedRow();
            String PengeluaranEDIT = tabelPengeluaran.getValueAt(row, 0).toString();
            String AktivitasEDIT = tabelPengeluaran.getValueAt(row, 1).toString();
            String TanggalEDIT = tabelPengeluaran.getValueAt(row, 2).toString();
            pengeluaranEDIT = pengeluaranInfo.getText();
            aktivitasEDIT = aktivitasInfo.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            tanggalEDIT = String.valueOf(sdf.format(tanggalInfo.getDate()));
            stat.executeUpdate("UPDATE aktivitas_user SET pengeluaran = '" + pengeluaranEDIT + "', aktivitas = '" + aktivitasEDIT + "', tanggal = '" + tanggalEDIT + "' WHERE id = '" + idname + "' AND pengeluaran = '" + PengeluaranEDIT + "' AND aktivitas = '" + AktivitasEDIT + "' AND tanggal = '" + TanggalEDIT + "'");
            JOptionPane.showMessageDialog(this, "Data berhasil disunting");
            pengeluaranInfo.setEnabled(false);
            aktivitasInfo.setEnabled(false);
            tanggalInfo.setEnabled(false);
            simpanEdit.setVisible(false);
            batalEdit.setVisible(false);
            jumlahPengeluaran = 0;
            tableModel.setRowCount(0);
            tampilkanPengeluaran();
            refreshAktivitas();
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_simpanEditActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        try
        {
            int row = tabelPengeluaran.getSelectedRow();
            pengeluaranHAPUS = tabelPengeluaran.getValueAt(row, 0).toString();
            aktivitasHAPUS = tabelPengeluaran.getValueAt(row, 1).toString();
            tanggalHAPUS = tabelPengeluaran.getValueAt(row, 2).toString();
            JOptionPane.showMessageDialog(this, "Hapus pengeluaran ini?");
            stat.executeUpdate("DELETE FROM aktivitas_user WHERE id = '" + idname + "' AND pengeluaran = '" + pengeluaranHAPUS + "' AND aktivitas = '" + aktivitasHAPUS + "' AND tanggal = '" + tanggalHAPUS + "'");
            tableModel.removeRow(row);
            refreshPengeluaran();
            refreshAktivitas();
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        pengeluaranInfo.setEnabled(true);
        aktivitasInfo.setEnabled(true);
        tanggalInfo.setEnabled(true);
        simpanEdit.setVisible(true);
        batalEdit.setVisible(true);
    }//GEN-LAST:event_editBtnActionPerformed

    private void refreshBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnActionPerformed
        refreshPengeluaran();
    }//GEN-LAST:event_refreshBtnActionPerformed

    private void tabelPengeluaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPengeluaranMouseClicked
        try
        {
            int row = tabelPengeluaran.getSelectedRow();
            pengeluaranInfo.setText(tabelPengeluaran.getValueAt(row, 0).toString());
            aktivitasInfo.setText(tabelPengeluaran.getValueAt(row, 1).toString());
            String tglInfo = tabelPengeluaran.getValueAt(row, 2).toString();
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(tglInfo);
            tanggalInfo.setDate(date);
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_tabelPengeluaranMouseClicked

    private void keluarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarBtnActionPerformed
        if(keluarBtn.getText() == "Keluar")
        {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
            this.dispose();
        } else if(keluarBtn.getText() == "Batal")
        {
            namaEdit.setVisible(false);
            namaidEdit.setVisible(false);
            passEdit.setVisible(false);
            jeniskelaminEdit.setVisible(false);
            fotoEdit.setVisible(false);
            choosefotoEdit.setVisible(false);

            namaeditTxt.setVisible(false);
            ideditTxt.setVisible(false);
            passeditTxt.setVisible(false);
            lakiEdit.setVisible(false);
            perempuanEdit.setVisible(false);
            fotoeditTxt.setVisible(false);
            
            fotoHome.setVisible(true);
            nameHome.setVisible(true);
            totaluangHome.setVisible(true);
            totalUangHome.setVisible(true);
            
            keluarBtn.setText("Keluar");
            keluarBtn.setSize(100, 31);
            
            editprofilBtn.setText("Edit Profil");
            editprofilBtn.setSize(100, 31);
        }
    }//GEN-LAST:event_keluarBtnActionPerformed

    private void tabelPemasukanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelPemasukanMouseClicked
        try
        {
            int row = tabelPemasukan.getSelectedRow();
            pemasukanInfo.setText(tabelPemasukan.getValueAt(row, 0).toString());
            aktivitasInfoPemasukan.setText(tabelPemasukan.getValueAt(row, 1).toString());
            String tglInfoPemasukan = tabelPemasukan.getValueAt(row, 2).toString();
            Date datePemasukan = new SimpleDateFormat("dd-MM-yyyy").parse(tglInfoPemasukan);
            tanggalInfoPemasukan.setDate(datePemasukan);
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_tabelPemasukanMouseClicked

    private void refreshBtnPemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshBtnPemasukanActionPerformed
        refreshPemasukan();
    }//GEN-LAST:event_refreshBtnPemasukanActionPerformed

    private void editBtnPemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnPemasukanActionPerformed
        pemasukanInfo.setEnabled(true);
        aktivitasInfoPemasukan.setEnabled(true);
        tanggalInfoPemasukan.setEnabled(true);
        simpanEditPemasukan.setVisible(true);
        batalEditPemasukan.setVisible(true);
    }//GEN-LAST:event_editBtnPemasukanActionPerformed

    private void deleteBtnPemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnPemasukanActionPerformed
        try
        {
            int row = tabelPemasukan.getSelectedRow();
            pemasukanHAPUS = tabelPemasukan.getValueAt(row, 0).toString();
            aktivitasPemasukanHAPUS = tabelPemasukan.getValueAt(row, 1).toString();
            tanggalPemasukanHAPUS = tabelPemasukan.getValueAt(row, 2).toString();
            JOptionPane.showMessageDialog(this, "Hapus pemasukan ini?");
            stat.executeUpdate("DELETE FROM aktivitas_user_pemasukan WHERE id = '" + idname + "' AND pemasukan = '" + pemasukanHAPUS + "' AND aktivitas = '" + aktivitasPemasukanHAPUS + "' AND tanggal = '" + tanggalPemasukanHAPUS + "'");
            tableModelPemasukan.removeRow(row);
            refreshPemasukan();
            refreshAktivitas();
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_deleteBtnPemasukanActionPerformed

    private void simpanEditPemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanEditPemasukanActionPerformed
        try
        {
            int row = tabelPemasukan.getSelectedRow();
            String PemasukanEDIT = tabelPemasukan.getValueAt(row, 0).toString();
            String AktivitasEDIT = tabelPemasukan.getValueAt(row, 1).toString();
            String TanggalEDIT = tabelPemasukan.getValueAt(row, 2).toString();
            pemasukanEDIT = pemasukanInfo.getText();
            aktivitasPemasukanEDIT = aktivitasInfoPemasukan.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            tanggalPemasukanEDIT = sdf.format(tanggalInfoPemasukan.getDate());
            stat.executeUpdate("UPDATE aktivitas_user_pemasukan SET pemasukan = '" + pemasukanEDIT + "', aktivitas = '" + aktivitasPemasukanEDIT + "', tanggal = '" + tanggalPemasukanEDIT + "' WHERE id = '" + idname + "' AND pemasukan = '" + PemasukanEDIT + "' AND aktivitas = '" + AktivitasEDIT + "' AND tanggal = '" + TanggalEDIT + "'");
            JOptionPane.showMessageDialog(this, "Data berhasil disunting");
            pemasukanInfo.setEnabled(false);
            aktivitasInfoPemasukan.setEnabled(false);
            tanggalInfoPemasukan.setEnabled(false);
            simpanEditPemasukan.setVisible(false);
            batalEditPemasukan.setVisible(false);
            jumlahPemasukan = 0;
            tableModelPemasukan.setRowCount(0);
            tampilkanPemasukan();
            refreshAktivitas();
        } catch(Exception ex)
        {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_simpanEditPemasukanActionPerformed

    private void batalEditPemasukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batalEditPemasukanActionPerformed
        pemasukanInfo.setEnabled(false);
        aktivitasInfoPemasukan.setEnabled(false);
        tanggalInfoPemasukan.setEnabled(false);
        simpanEditPemasukan.setVisible(false);
        batalEditPemasukan.setVisible(false);
    }//GEN-LAST:event_batalEditPemasukanActionPerformed

    private void pengeluaranBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pengeluaranBtnActionPerformed
        PengeluaranBtn = true;
        PemasukanBtn = false;
        jenisTxt.setText("Pengeluaran (Rp)");
    }//GEN-LAST:event_pengeluaranBtnActionPerformed

    private void pemasukanBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pemasukanBtnActionPerformed
        PemasukanBtn = true;
        PengeluaranBtn = false;
        jenisTxt.setText("Pemasukan (Rp)");
    }//GEN-LAST:event_pemasukanBtnActionPerformed

    private void pengeluaranBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pengeluaranBtnMouseClicked
        pengeluaranBtn.setBackground(new Color(236,221,15));
        pemasukanBtn.setBackground(new Color(57,159,221));
    }//GEN-LAST:event_pengeluaranBtnMouseClicked

    private void pemasukanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pemasukanBtnMouseClicked
        pemasukanBtn.setBackground(new Color(236,221,15));
        pengeluaranBtn.setBackground(new Color(57,159,221));
    }//GEN-LAST:event_pemasukanBtnMouseClicked

    private void editprofilBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editprofilBtnActionPerformed
        if(editprofilBtn.getText() == "Edit Profil")
        {    
            namaEdit.setVisible(true);
            namaidEdit.setVisible(true);
            passEdit.setVisible(true);
            jeniskelaminEdit.setVisible(true);
            fotoEdit.setVisible(true);
            choosefotoEdit.setVisible(true);

            namaeditTxt.setVisible(true);
            ideditTxt.setVisible(true);
            passeditTxt.setVisible(true);
            lakiEdit.setVisible(true);
            perempuanEdit.setVisible(true);
            fotoeditTxt.setVisible(true);

            fotoHome.setVisible(false);
            nameHome.setVisible(false);
            totaluangHome.setVisible(false);
            totalUangHome.setVisible(false);

            keluarBtn.setText("Batal");
            keluarBtn.setSize(100, 31);
            
            editprofilBtn.setText("Simpan");
            editprofilBtn.setSize(100, 31);
            
            lakiEdit.setActionCommand("Laki-laki");
            perempuanEdit.setActionCommand("Perempuan");
            
            try
            {
                rs = stat.executeQuery("SELECT * FROM user WHERE id = '" + idname + "'");
                
                if(rs.next())
                {
                    namaeditTxt.setText(rs.getString("nama"));
                    ideditTxt.setText(rs.getString("id"));
                    passeditTxt.setText(rs.getString("password"));
                    
                    String jkEdit = rs.getString("jeniskelamin");
                    
                    if(jkEdit.equals("Laki-laki"))
                    {
                        lakiEdit.setSelected(true);
                    } else if(jkEdit.equals("Perempuan"))
                    {
                        perempuanEdit.setSelected(true);
                    }
                }
            } catch(Exception ex)
            {
                JOptionPane.showMessageDialog(this, ex);
            }
        } else if(editprofilBtn.getText() == "Simpan")
        {
            try
            {
                namahomeEdit = namaeditTxt.getText();
                idhomeEdit = ideditTxt.getText();
                passhomeEdit = passeditTxt.getText();
                jkhomeEdit = jkeditGroup.getSelection().getActionCommand();
                
                if(namahomeEdit.isEmpty() || idhomeEdit.isEmpty() || passhomeEdit.isEmpty() || jkhomeEdit.isEmpty())
                {
                    JOptionPane.showMessageDialog(this, "Isi data dengan lengkap");
                } else
                {
                    stat.executeUpdate("UPDATE user SET id = '" + idhomeEdit + "', password = '" + passhomeEdit + "', nama = '" + namahomeEdit + "', jeniskelamin = '" + jkhomeEdit + "' WHERE id = '" + idname + "'");
                    JOptionPane.showMessageDialog(this, "Profil berhasil disunting");
                    getData();
                    
                    namaEdit.setVisible(false);
                    namaidEdit.setVisible(false);
                    passEdit.setVisible(false);
                    jeniskelaminEdit.setVisible(false);
                    fotoEdit.setVisible(false);
                    choosefotoEdit.setVisible(false);

                    namaeditTxt.setVisible(false);
                    ideditTxt.setVisible(false);
                    passeditTxt.setVisible(false);
                    lakiEdit.setVisible(false);
                    perempuanEdit.setVisible(false);
                    fotoeditTxt.setVisible(false);

                    fotoHome.setVisible(true);
                    nameHome.setVisible(true);
                    nameHome.setText(nama);
                    totaluangHome.setVisible(true);
                    totalUangHome.setVisible(true);

                    keluarBtn.setText("Keluar");
                    keluarBtn.setSize(100, 31);

                    editprofilBtn.setText("Edit Profil");
                    editprofilBtn.setSize(100, 31);
                }
            } catch(Exception ex)
            {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }//GEN-LAST:event_editprofilBtnActionPerformed

    private void choosefotoEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choosefotoEditActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("JPG, PNG, and GIF", "jpg", "png", "gif");
        fileChooser.setFileFilter(fileFilter);
        fileChooser.showOpenDialog(null);
        File foto = fileChooser.getSelectedFile();
        fotoPathEdit = foto.getAbsolutePath();
        fotoeditTxt.setText(fotoPathEdit);
    }//GEN-LAST:event_choosefotoEditActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomeForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomeForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Aktivitas;
    private javax.swing.JPanel Pemasukan;
    private javax.swing.JPanel Pengeluaran;
    private javax.swing.JPanel Profil;
    private javax.swing.JTextField aktivitasInfo;
    private javax.swing.JTextField aktivitasInfoPemasukan;
    private javax.swing.JTextField aktivitasTxt;
    private javax.swing.JButton batalEdit;
    private javax.swing.JButton batalEditPemasukan;
    private javax.swing.JButton choosefotoEdit;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton deleteBtnPemasukan;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton editBtnPemasukan;
    private javax.swing.JButton editprofilBtn;
    private javax.swing.JLabel fotoEdit;
    private javax.swing.JLabel fotoHome;
    private javax.swing.JTextField fotoeditTxt;
    private javax.swing.JButton hapusBtn;
    private javax.swing.JTextField ideditTxt;
    private javax.swing.JPanel infoPemasukanPanel;
    private javax.swing.JPanel infoPengeluaranPanel;
    private javax.swing.JTextField inputuangTxt;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel jenisTxt;
    private javax.swing.JLabel jeniskelaminEdit;
    private javax.swing.ButtonGroup jkeditGroup;
    private javax.swing.JButton keluarBtn;
    private javax.swing.JRadioButton lakiEdit;
    private javax.swing.JLabel namaEdit;
    private javax.swing.JTextField namaeditTxt;
    private javax.swing.JLabel namaidEdit;
    private javax.swing.JLabel nameHome;
    private javax.swing.JLabel passEdit;
    private javax.swing.JPasswordField passeditTxt;
    private javax.swing.JButton pemasukanBtn;
    private javax.swing.JTextField pemasukanInfo;
    private javax.swing.JButton pengeluaranBtn;
    private javax.swing.JTextField pengeluaranInfo;
    private javax.swing.JRadioButton perempuanEdit;
    private javax.swing.JButton refreshBtn;
    private javax.swing.JButton refreshBtnPemasukan;
    private javax.swing.JButton simpanBtn;
    private javax.swing.JButton simpanEdit;
    private javax.swing.JButton simpanEditPemasukan;
    private javax.swing.JTable tabelPemasukan;
    private javax.swing.JTable tabelPengeluaran;
    private com.toedter.calendar.JDateChooser tanggalChooser;
    private com.toedter.calendar.JDateChooser tanggalInfo;
    private com.toedter.calendar.JDateChooser tanggalInfoPemasukan;
    private javax.swing.JLabel totalPemasukanInfo;
    private javax.swing.JLabel totalPengeluaranInfo;
    private javax.swing.JLabel totalUangHome;
    private javax.swing.JLabel totaluangHome;
    // End of variables declaration//GEN-END:variables
}
