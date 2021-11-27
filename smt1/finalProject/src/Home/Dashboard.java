/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import javax.swing.JOptionPane;
import Controller.Koneksi;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


/**
 *
 * @author Raihan
 */

public final class Dashboard extends javax.swing.JFrame {
	Connection conn;
	ResultSet rs = null;
	PreparedStatement pst = null;
	String id;
	String idBuah;
	String idPelanggan;
	int hargaBuah;
	int totalHarga;

    /**
     * Creates new form Dashboard
	 * @throws java.sql.SQLException
	 * @throws java.lang.ClassNotFoundException
     */
    public Dashboard() throws SQLException, ClassNotFoundException {
	this.conn = Koneksi.getKoneksi();
        initComponents();
    }
    public Dashboard(String id) throws SQLException, ClassNotFoundException {
	this.conn = Koneksi.getKoneksi();
	this.id = id;
        initComponents();
	initThings();
	tabelKaryawan();
	tabelPelanggan();
	tabelPenjualan();
	tabelBuah();
	listId();
	listBuah();
	listPelanggan();
    }

    public void initThings(){
	    try{
			jTextField7.disable();
		    // transaksi hari ini
	    String sql = "select count(*) from tb_transaksi where tanggal = curdate()";
	    pst = conn.prepareStatement(sql);
	    rs = pst.executeQuery();
	    while(rs.next()){
		    jLabel8.setText(rs.getString(1));
	    }
		    // total transaksi
	    sql = "select count(*) from tb_transaksi";
	    pst = conn.prepareStatement(sql);
	    rs = pst.executeQuery();
	    while(rs.next()){
		    jLabel18.setText(rs.getString(1));
	    }
		    // stok buah
	    sql = "select sum(stok) from tb_buah";
	    pst = conn.prepareStatement(sql);
	    rs = pst.executeQuery();
	    while(rs.next()){
		    jLabel34.setText(rs.getString(1));
	    }
	    // list id
	sql = "select nama from tb_user where id = " + id;
	System.out.println(sql);
	pst=conn.prepareStatement(sql);
	rs=pst.executeQuery();
	if(rs.next()){
	jLabel24.setText("<html><center>Hi, " + rs.getString(1) + "</center></html>");
	jLabel11.setText(rs.getString(1));
	// init qty
	jTextField2.setText("0");
	}else{
	System.out.println("ur mom gay");
}
	    }catch(SQLException ex){
		    System.out.println("gagal gan");
	    }
    }

    public void tabelPenjualan(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    model.addColumn("No Faktur");
	    model.addColumn("Buah");
	    model.addColumn("QTY");
	    model.addColumn("Pelanggan");
	    model.addColumn("Kasir");
	    model.addColumn("Tanggal");
	    try{
		    String sql = "select dt.id, t.no_faktur, b.nama, dt.qty, p.nama, u.nama, t.tanggal from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_buah as b on dt.id_buah = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id order by t.tanggal desc";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)});
		    }
		    jTable3.setModel(model);
		    jTable3.setEnabled(false);
	    }catch(SQLException e){
	    }
    }

    public void tabelPenjualan(String id){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("Buah");
	    model.addColumn("QTY");
	    model.addColumn("Harga");
	    model.addColumn("Kasir");
	    try{
		    String sql = "select b.nama, dt.qty, sum(dt.qty * b.harga), u.nama from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_buah as b on dt.id_buah = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id where t.no_faktur = " + id + " group by dt.id order by t.tanggal desc";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4)});
		    }
		    jTable5.setModel(model);
		    jTable5.setEnabled(false);
		    JTableHeader tableHeader = jTable5.getTableHeader();
		    tableHeader.setOpaque(false);
		    tableHeader.setBackground(Color.black);
		    tableHeader.setForeground(Color.black);
		    sql = "select sum(dt.qty * b.harga) from tb_detail_transaksi as dt join tb_transaksi as t on dt.id_transaksi = t.no_faktur join tb_buah as b on dt.id_buah = b.id join tb_pelanggan as p on t.id_pelanggan = p.id join tb_user as u on dt.id_user = u.id where t.no_faktur = " + id;
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    if(rs.getString(1) == null){
				    jLabel46.setText("Rp.0,00");
			    }else{
				    Locale locale = new Locale("id", "ID");
				    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
				    jLabel46.setText(currencyFormatter.format(Integer.parseInt(rs.getString(1))));
			    }
		    }
	    }catch(SQLException e){
	    }
    }

    public void tabelKaryawan(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("No");
	    model.addColumn("Nama");
	    model.addColumn("Bagian");
	    try{
		    String sql = "select k.id, k.nama, b.nama from tb_karyawan as k join tb_bagianrole as b on k.id = b.id";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3)});
		    }
		    jTable1.setModel(model);
	    }catch(SQLException e){
	    }
    }

    public void tabelPelanggan(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("No");
	    model.addColumn("Nama");
	    model.addColumn("Alamat");
	    model.addColumn("No HP");
	    try{
		    String sql = "select * from tb_pelanggan";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
		    }
		    jTable2.setModel(model);
	    }catch(SQLException e){
	    }
    }

    public void tabelBuah(){
	    DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("ID");
	    model.addColumn("Nama");
	    model.addColumn("Stok");
	    model.addColumn("Harga");
	    try{
		    String sql = "select * from tb_buah";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			    model.addRow(new Object[] {rs.getString(1),rs.getString(2), rs.getString(3), rs.getString(4)});
		    }
		    jTable4.setModel(model);
	    }catch(SQLException e){
	    }
    }

    public void listId(){
	jComboBox3.removeAllItems();
	    try{
		    String sql = "select no_faktur from tb_transaksi";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			jComboBox3.addItem(rs.getString(1));
		    }
			sql = "select t.tanggal, p.nama from tb_transaksi as t join tb_pelanggan as p on t.id_pelanggan = p.id where t.no_faktur = " + jComboBox3.getSelectedItem();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				jLabel9.setText(rs.getString(1));
				jLabel47.setText(rs.getString(2));
				tabelPenjualan((String) jComboBox3.getSelectedItem());
			}
	    }catch(SQLException e){
	    }
    }

    public void listBuah(){
	jComboBox4.removeAllItems();
	    try{
		    String sql = "select nama from tb_buah";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			jComboBox4.addItem(rs.getString(1));
		    }
	    }catch(SQLException e){
	    }
    }

    public void listPelanggan(){
	jComboBox5.removeAllItems();
	    try{
		    String sql = "select nama from tb_pelanggan";
		    pst=conn.prepareStatement(sql);
		    rs=pst.executeQuery();
		    while(rs.next()){
			jComboBox5.addItem(rs.getString(1));
		    }
	    }catch(SQLException e){
	    }
    }

	public void clear(){
		jTextField3.setText("");
		jTextField4.setText("");
		jTextField5.setText("");
		jTextField3.enable();
		tabelBuah();
	}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jDialog1 = new javax.swing.JDialog();
                jLabel37 = new javax.swing.JLabel();
                jLabel40 = new javax.swing.JLabel();
                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jLabel6 = new javax.swing.JLabel();
                jPanel11 = new javax.swing.JPanel();
                jLabel12 = new javax.swing.JLabel();
                jPanel9 = new javax.swing.JPanel();
                jLabel21 = new javax.swing.JLabel();
                jPanel6 = new javax.swing.JPanel();
                home = new javax.swing.JPanel();
                jLabel13 = new javax.swing.JLabel();
                jPanel12 = new javax.swing.JPanel();
                jLabel8 = new javax.swing.JLabel();
                jLabel23 = new javax.swing.JLabel();
                jPanel10 = new javax.swing.JPanel();
                jLabel10 = new javax.swing.JLabel();
                jPanel13 = new javax.swing.JPanel();
                jLabel18 = new javax.swing.JLabel();
                jLabel25 = new javax.swing.JLabel();
                jPanel23 = new javax.swing.JPanel();
                jLabel26 = new javax.swing.JLabel();
                jPanel14 = new javax.swing.JPanel();
                jLabel34 = new javax.swing.JLabel();
                jLabel28 = new javax.swing.JLabel();
                jPanel24 = new javax.swing.JPanel();
                jLabel35 = new javax.swing.JLabel();
                jPanel20 = new javax.swing.JPanel();
                jPanel4 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jPanel5 = new javax.swing.JPanel();
                jLabel3 = new javax.swing.JLabel();
                jPanel7 = new javax.swing.JPanel();
                jLabel4 = new javax.swing.JLabel();
                jPanel8 = new javax.swing.JPanel();
                jLabel5 = new javax.swing.JLabel();
                jPanel15 = new javax.swing.JPanel();
                jLabel31 = new javax.swing.JLabel();
                cekStokBuah = new javax.swing.JPanel();
                jScrollPane4 = new javax.swing.JScrollPane();
                jTable4 = new javax.swing.JTable();
                jButton1 = new javax.swing.JButton();
                jSeparator2 = new javax.swing.JSeparator();
                jTextField1 = new javax.swing.JTextField();
                jLabel38 = new javax.swing.JLabel();
                jLabel51 = new javax.swing.JLabel();
                jPanel26 = new javax.swing.JPanel();
                jLabel15 = new javax.swing.JLabel();
                jLabel52 = new javax.swing.JLabel();
                jLabel53 = new javax.swing.JLabel();
                jPanel27 = new javax.swing.JPanel();
                jLabel54 = new javax.swing.JLabel();
                jLabel58 = new javax.swing.JLabel();
                jTextField3 = new javax.swing.JTextField();
                jTextField4 = new javax.swing.JTextField();
                jTextField5 = new javax.swing.JTextField();
                jPanel28 = new javax.swing.JPanel();
                jLabel55 = new javax.swing.JLabel();
                jPanel29 = new javax.swing.JPanel();
                jLabel56 = new javax.swing.JLabel();
                jPanel30 = new javax.swing.JPanel();
                jLabel57 = new javax.swing.JLabel();
                jTextField7 = new javax.swing.JTextField();
                buatTransaksi = new javax.swing.JPanel();
                jPanel17 = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jLabel39 = new javax.swing.JLabel();
                jLabel41 = new javax.swing.JLabel();
                jLabel42 = new javax.swing.JLabel();
                jComboBox3 = new javax.swing.JComboBox<>();
                jLabel9 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel47 = new javax.swing.JLabel();
                jPanel18 = new javax.swing.JPanel();
                jLabel43 = new javax.swing.JLabel();
                jLabel44 = new javax.swing.JLabel();
                jPanel22 = new javax.swing.JPanel();
                jLabel7 = new javax.swing.JLabel();
                jComboBox4 = new javax.swing.JComboBox<>();
                jTextField2 = new javax.swing.JTextField();
                jScrollPane5 = new javax.swing.JScrollPane();
                jTable5 = new javax.swing.JTable();
                jPanel16 = new javax.swing.JPanel();
                jPanel3 = new javax.swing.JPanel();
                jLabel45 = new javax.swing.JLabel();
                jLabel46 = new javax.swing.JLabel();
                jPanel19 = new javax.swing.JPanel();
                jLabel49 = new javax.swing.JLabel();
                jPanel21 = new javax.swing.JPanel();
                jLabel48 = new javax.swing.JLabel();
                jPanel25 = new javax.swing.JPanel();
                jLabel50 = new javax.swing.JLabel();
                jComboBox5 = new javax.swing.JComboBox<>();
                dataPenjualan = new javax.swing.JPanel();
                jLabel32 = new javax.swing.JLabel();
                jScrollPane3 = new javax.swing.JScrollPane();
                jTable3 = new javax.swing.JTable();
                dataPelanggan = new javax.swing.JPanel();
                jLabel27 = new javax.swing.JLabel();
                jScrollPane2 = new javax.swing.JScrollPane();
                jTable2 = new javax.swing.JTable();
                dataKaryawan = new javax.swing.JPanel();
                jLabel33 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                jTable1 = new javax.swing.JTable();
                about = new javax.swing.JPanel();
                jLabel36 = new javax.swing.JLabel();
                jLabel30 = new javax.swing.JLabel();
                jLabel24 = new javax.swing.JLabel();
                jLabel29 = new javax.swing.JLabel();

                javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
                jDialog1.getContentPane().setLayout(jDialog1Layout);
                jDialog1Layout.setHorizontalGroup(
                        jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
                );
                jDialog1Layout.setVerticalGroup(
                        jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
                );

                jLabel37.setText("jLabel37");

                jLabel40.setText("jLabel40");

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setBackground(new java.awt.Color(30, 81, 40));
                jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jPanel2.setBackground(new java.awt.Color(78, 159, 61));

                jLabel6.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel6.setForeground(new java.awt.Color(255, 255, 255));
                jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-logout-20.png"))); // NOI18N
                jLabel6.setText(" Logout");
                jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel6MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel6)
                                .addContainerGap(71, Short.MAX_VALUE))
                );
                jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                );

                jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 370, 190, 50));

                jPanel11.setBackground(new java.awt.Color(78, 159, 61));

                jLabel12.setBackground(new java.awt.Color(54, 33, 89));
                jLabel12.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel12.setForeground(new java.awt.Color(255, 255, 255));
                jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-graph-20.png"))); // NOI18N
                jLabel12.setText(" Dashboard");
                jLabel12.setToolTipText("");
                jLabel12.setMaximumSize(new java.awt.Dimension(120, 20));
                jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel12MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
                jPanel11.setLayout(jPanel11Layout);
                jPanel11Layout.setHorizontalGroup(
                        jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(47, Short.MAX_VALUE))
                );
                jPanel11Layout.setVerticalGroup(
                        jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                );

                jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 190, 50));

                jPanel9.setBackground(new java.awt.Color(78, 159, 61));

                jLabel21.setBackground(new java.awt.Color(255, 255, 255));
                jLabel21.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel21.setForeground(new java.awt.Color(255, 255, 255));
                jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-about-20.png"))); // NOI18N
                jLabel21.setText(" About");
                jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel21MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
                jPanel9.setLayout(jPanel9Layout);
                jPanel9Layout.setHorizontalGroup(
                        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(38, Short.MAX_VALUE))
                );
                jPanel9Layout.setVerticalGroup(
                        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                );

                jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 190, 50));

                jPanel6.setBackground(new java.awt.Color(186, 79, 84));
                jPanel6.setLayout(new java.awt.CardLayout());

                home.setBackground(new java.awt.Color(216, 233, 168));
                home.setForeground(new java.awt.Color(25, 26, 25));
                home.addContainerListener(new java.awt.event.ContainerAdapter() {
                        public void componentAdded(java.awt.event.ContainerEvent evt) {
                                homeComponentAdded(evt);
                        }
                });
                home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel13.setBackground(new java.awt.Color(25, 26, 25));
                jLabel13.setFont(new java.awt.Font("Cascadia Mono", 1, 24)); // NOI18N
                jLabel13.setForeground(new java.awt.Color(25, 26, 25));
                jLabel13.setText("Toko Buah-Buahan");
                home.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 260, 60));

                jPanel12.setBackground(new java.awt.Color(78, 159, 61));

                jLabel8.setFont(new java.awt.Font("Cascadia Mono", 0, 24)); // NOI18N
                jLabel8.setForeground(new java.awt.Color(255, 255, 255));
                jLabel8.setText("bruh");

                jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-transaction-48.png"))); // NOI18N

                jPanel10.setBackground(new java.awt.Color(30, 81, 40));

                jLabel10.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel10.setForeground(new java.awt.Color(255, 255, 255));
                jLabel10.setText("Transaksi Hari Ini");

                javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
                jPanel10.setLayout(jPanel10Layout);
                jPanel10Layout.setHorizontalGroup(
                        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(32, Short.MAX_VALUE))
                );
                jPanel10Layout.setVerticalGroup(
                        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
                jPanel12.setLayout(jPanel12Layout);
                jPanel12Layout.setHorizontalGroup(
                        jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                jPanel12Layout.setVerticalGroup(
                        jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                home.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 210, 120));

                jPanel13.setBackground(new java.awt.Color(78, 159, 61));

                jLabel18.setFont(new java.awt.Font("Cascadia Mono", 0, 24)); // NOI18N
                jLabel18.setForeground(new java.awt.Color(255, 255, 255));
                jLabel18.setText("bruh");

                jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-bill-48.png"))); // NOI18N

                jPanel23.setBackground(new java.awt.Color(30, 81, 40));

                jLabel26.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel26.setForeground(new java.awt.Color(255, 255, 255));
                jLabel26.setText("Total Transaksi");

                javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
                jPanel23.setLayout(jPanel23Layout);
                jPanel23Layout.setHorizontalGroup(
                        jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                                .addContainerGap(37, Short.MAX_VALUE)
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))
                );
                jPanel23Layout.setVerticalGroup(
                        jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
                jPanel13.setLayout(jPanel13Layout);
                jPanel13Layout.setHorizontalGroup(
                        jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel13Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel13Layout.setVerticalGroup(
                        jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                home.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 210, 120));

                jPanel14.setBackground(new java.awt.Color(78, 159, 61));

                jLabel34.setFont(new java.awt.Font("Cascadia Mono", 0, 24)); // NOI18N
                jLabel34.setForeground(new java.awt.Color(255, 255, 255));
                jLabel34.setText("bruh");

                jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-fruits-48.png"))); // NOI18N

                jPanel24.setBackground(new java.awt.Color(30, 81, 40));

                jLabel35.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel35.setForeground(new java.awt.Color(255, 255, 255));
                jLabel35.setText("Stok Buah");

                javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
                jPanel24.setLayout(jPanel24Layout);
                jPanel24Layout.setHorizontalGroup(
                        jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel24Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel35)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel24Layout.setVerticalGroup(
                        jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
                jPanel14.setLayout(jPanel14Layout);
                jPanel14Layout.setHorizontalGroup(
                        jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel28)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(45, Short.MAX_VALUE))
                        .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                jPanel14Layout.setVerticalGroup(
                        jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(16, 16, 16)
                                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                home.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, 210, 120));

                jPanel20.setBackground(new java.awt.Color(220, 237, 171));

                jPanel4.setBackground(new java.awt.Color(220, 237, 171));
                jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));
                jPanel4.setToolTipText("");
                jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jPanel4MouseClicked(evt);
                        }
                });

                jLabel1.setBackground(new java.awt.Color(25, 26, 25));
                jLabel1.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(25, 26, 25));
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-apple-30.png"))); // NOI18N
                jLabel1.setText("<html>Stock Buah</html>");
                jLabel1.setMaximumSize(new java.awt.Dimension(50, 50));
                jLabel1.setMinimumSize(new java.awt.Dimension(50, 50));
                jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel1MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(40, Short.MAX_VALUE))
                );
                jPanel4Layout.setVerticalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                );

                jPanel5.setBackground(new java.awt.Color(220, 237, 171));
                jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));

                jLabel3.setBackground(new java.awt.Color(25, 26, 25));
                jLabel3.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(25, 26, 25));
                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-detail-30.png"))); // NOI18N
                jLabel3.setText("<html>Data Transaksi</html>");
                jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel3MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                );
                jPanel5Layout.setVerticalGroup(
                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                );

                jPanel7.setBackground(new java.awt.Color(220, 237, 171));
                jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));

                jLabel4.setBackground(new java.awt.Color(25, 26, 25));
                jLabel4.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel4.setForeground(new java.awt.Color(25, 26, 25));
                jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-person-30.png"))); // NOI18N
                jLabel4.setText("<html>Data Pelanggan</html>");
                jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel4MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
                jPanel7.setLayout(jPanel7Layout);
                jPanel7Layout.setHorizontalGroup(
                        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(14, Short.MAX_VALUE))
                );
                jPanel7Layout.setVerticalGroup(
                        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                );

                jPanel8.setBackground(new java.awt.Color(220, 237, 171));
                jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));

                jLabel5.setBackground(new java.awt.Color(25, 26, 25));
                jLabel5.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel5.setForeground(new java.awt.Color(25, 26, 25));
                jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-worker-30.png"))); // NOI18N
                jLabel5.setText("<html>Data Karyawan</html>");
                jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel5MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
                jPanel8.setLayout(jPanel8Layout);
                jPanel8Layout.setHorizontalGroup(
                        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                .addGap(17, 17, 17))
                );
                jPanel8Layout.setVerticalGroup(
                        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                );

                jPanel15.setBackground(new java.awt.Color(220, 237, 171));
                jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));
                jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jPanel15MouseClicked(evt);
                        }
                });

                jLabel31.setBackground(new java.awt.Color(25, 26, 25));
                jLabel31.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel31.setForeground(new java.awt.Color(25, 26, 25));
                jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-buy-30.png"))); // NOI18N
                jLabel31.setText("<html>Buat Transaksi</html>");
                jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel31MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
                jPanel15.setLayout(jPanel15Layout);
                jPanel15Layout.setHorizontalGroup(
                        jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(18, Short.MAX_VALUE))
                );
                jPanel15Layout.setVerticalGroup(
                        jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
                jPanel20.setLayout(jPanel20Layout);
                jPanel20Layout.setHorizontalGroup(
                        jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel20Layout.createSequentialGroup()
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel20Layout.createSequentialGroup()
                                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(145, Short.MAX_VALUE))
                );
                jPanel20Layout.setVerticalGroup(
                        jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                );

                home.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 690, 250));

                jPanel6.add(home, "card4");

                cekStokBuah.setBackground(new java.awt.Color(216, 233, 168));
                cekStokBuah.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jTable4.setBackground(new java.awt.Color(223, 232, 197));
                jTable4.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jTable4.setForeground(new java.awt.Color(25, 26, 25));
                jTable4.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4"
                        }
                ));
                jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jTable4MouseClicked(evt);
                        }
                });
                jScrollPane4.setViewportView(jTable4);

                cekStokBuah.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 360, 380));

                jButton1.setBackground(new java.awt.Color(78, 159, 61));
                jButton1.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jButton1.setForeground(new java.awt.Color(255, 255, 255));
                jButton1.setText("Cari");
                jButton1.setToolTipText("");
                jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jButton1MouseClicked(evt);
                        }
                });
                cekStokBuah.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 70, 100, 30));

                jSeparator2.setBackground(new java.awt.Color(30, 81, 40));
                jSeparator2.setForeground(new java.awt.Color(30, 81, 40));
                jSeparator2.setAlignmentX(1.0F);
                jSeparator2.setAlignmentY(1.0F);
                jSeparator2.setAutoscrolls(true);
                cekStokBuah.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 210, 20));

                jTextField1.setBackground(new java.awt.Color(216, 233, 168));
                jTextField1.setBorder(null);
                jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyPressed(java.awt.event.KeyEvent evt) {
                                jTextField1KeyPressed(evt);
                        }
                });
                cekStokBuah.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 210, 30));

                jLabel38.setForeground(new java.awt.Color(216, 233, 168));
                jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-30.png"))); // NOI18N
                jLabel38.setText("jLabel38");
                cekStokBuah.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 30, 30));

                jLabel51.setBackground(new java.awt.Color(25, 26, 25));
                jLabel51.setFont(new java.awt.Font("Cascadia Mono", 1, 24)); // NOI18N
                jLabel51.setForeground(new java.awt.Color(25, 26, 25));
                jLabel51.setText("Stok Buah");
                cekStokBuah.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, 260, 60));

                jPanel26.setBackground(new java.awt.Color(223, 232, 197));

                jLabel15.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel15.setForeground(new java.awt.Color(25, 26, 25));
                jLabel15.setText("Nama Buah");

                jLabel52.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel52.setForeground(new java.awt.Color(25, 26, 25));
                jLabel52.setText("Stok");

                jLabel53.setFont(new java.awt.Font("Cascadia Mono", 1, 14)); // NOI18N
                jLabel53.setForeground(new java.awt.Color(25, 26, 25));
                jLabel53.setText("Harga");

                jPanel27.setBackground(new java.awt.Color(78, 159, 61));

                jLabel54.setBackground(new java.awt.Color(25, 26, 25));
                jLabel54.setFont(new java.awt.Font("Cascadia Mono", 1, 20)); // NOI18N
                jLabel54.setForeground(new java.awt.Color(255, 255, 255));
                jLabel54.setText("<html><center>Edit/Update Stok Buah-Buahan</center></html>");

                jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-refresh-20.png"))); // NOI18N
                jLabel58.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel58MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
                jPanel27.setLayout(jPanel27Layout);
                jPanel27Layout.setHorizontalGroup(
                        jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel27Layout.createSequentialGroup()
                                .addContainerGap(28, Short.MAX_VALUE)
                                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                                                .addComponent(jLabel58)
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(23, 23, 23))))
                );
                jPanel27Layout.setVerticalGroup(
                        jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel27Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel58)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                );

                jTextField3.setBackground(new java.awt.Color(223, 232, 197));
                jTextField3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jTextField3ActionPerformed(evt);
                        }
                });

                jTextField4.setBackground(new java.awt.Color(223, 232, 197));
                jTextField4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jTextField4ActionPerformed(evt);
                        }
                });

                jTextField5.setBackground(new java.awt.Color(223, 232, 197));
                jTextField5.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jTextField5ActionPerformed(evt);
                        }
                });

                jPanel28.setBackground(new java.awt.Color(78, 159, 61));

                jLabel55.setFont(new java.awt.Font("Cascadia Mono", 1, 18)); // NOI18N
                jLabel55.setForeground(new java.awt.Color(255, 255, 255));
                jLabel55.setText("<html><center>Tambah</center></html>");
                jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel55MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
                jPanel28.setLayout(jPanel28Layout);
                jPanel28Layout.setHorizontalGroup(
                        jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel28Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel28Layout.setVerticalGroup(
                        jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                );

                jPanel29.setBackground(new java.awt.Color(253, 253, 150));

                jLabel56.setBackground(new java.awt.Color(25, 26, 25));
                jLabel56.setFont(new java.awt.Font("Cascadia Mono", 1, 18)); // NOI18N
                jLabel56.setForeground(new java.awt.Color(25, 26, 25));
                jLabel56.setText("<html><center>Update</center></html>");
                jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel56MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
                jPanel29.setLayout(jPanel29Layout);
                jPanel29Layout.setHorizontalGroup(
                        jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel29Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel29Layout.setVerticalGroup(
                        jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                );

                jPanel30.setBackground(new java.awt.Color(255, 105, 97));

                jLabel57.setFont(new java.awt.Font("Cascadia Mono", 1, 18)); // NOI18N
                jLabel57.setForeground(new java.awt.Color(255, 255, 255));
                jLabel57.setText("<html><center>Delete</center></html>");
                jLabel57.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel57MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
                jPanel30.setLayout(jPanel30Layout);
                jPanel30Layout.setHorizontalGroup(
                        jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel30Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel30Layout.setVerticalGroup(
                        jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
                jPanel26.setLayout(jPanel26Layout);
                jPanel26Layout.setHorizontalGroup(
                        jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel26Layout.createSequentialGroup()
                                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel15)
                                                        .addComponent(jLabel52)
                                                        .addComponent(jLabel53))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                                        .addComponent(jTextField4)
                                                        .addComponent(jTextField3))))
                                .addGap(22, 22, 22))
                );
                jPanel26Layout.setVerticalGroup(
                        jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel26Layout.createSequentialGroup()
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel15)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel52)
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel53)
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(15, 15, 15))
                );

                cekStokBuah.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 250, 380));

                jTextField7.setBackground(new java.awt.Color(216, 233, 168));
                jTextField7.setForeground(new java.awt.Color(216, 233, 168));
                jTextField7.setBorder(null);
                jTextField7.setCaretColor(new java.awt.Color(216, 233, 168));
                jTextField7.setDisabledTextColor(new java.awt.Color(216, 233, 168));
                jTextField7.setSelectedTextColor(new java.awt.Color(216, 233, 168));
                jTextField7.setSelectionColor(new java.awt.Color(216, 233, 168));
                jTextField7.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jTextField7ActionPerformed(evt);
                        }
                });
                cekStokBuah.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

                jPanel6.add(cekStokBuah, "card2");

                buatTransaksi.setBackground(new java.awt.Color(216, 233, 168));
                buatTransaksi.setForeground(new java.awt.Color(255, 255, 255));

                jPanel17.setBackground(new java.awt.Color(223, 232, 197));

                jLabel2.setBackground(new java.awt.Color(25, 26, 25));
                jLabel2.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel2.setForeground(new java.awt.Color(25, 26, 25));
                jLabel2.setText("No Faktur");

                jLabel39.setBackground(new java.awt.Color(25, 26, 25));
                jLabel39.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel39.setForeground(new java.awt.Color(25, 26, 25));
                jLabel39.setText("Tanggal");

                jLabel41.setBackground(new java.awt.Color(25, 26, 25));
                jLabel41.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel41.setForeground(new java.awt.Color(25, 26, 25));
                jLabel41.setText("Kasir");

                jLabel42.setBackground(new java.awt.Color(25, 26, 25));
                jLabel42.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel42.setForeground(new java.awt.Color(25, 26, 25));
                jLabel42.setText("Pelanggan");

                jComboBox3.setBackground(new java.awt.Color(223, 232, 197));
                jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                jComboBox3.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
                        public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                        }
                        public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                                jComboBox3PopupMenuWillBecomeInvisible(evt);
                        }
                        public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                        }
                });

                jLabel9.setText("jLabel9");

                jLabel11.setText("jLabel11");

                jLabel47.setText("jLabel47");

                javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
                jPanel17.setLayout(jPanel17Layout);
                jPanel17Layout.setHorizontalGroup(
                        jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel39)
                                        .addComponent(jLabel41)
                                        .addComponent(jLabel42)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                                .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel17Layout.setVerticalGroup(
                        jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel39)
                                        .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel41)
                                        .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel42)
                                        .addComponent(jLabel47))
                                .addContainerGap(17, Short.MAX_VALUE))
                );

                jPanel18.setBackground(new java.awt.Color(223, 232, 197));

                jLabel43.setBackground(new java.awt.Color(25, 26, 25));
                jLabel43.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel43.setForeground(new java.awt.Color(25, 26, 25));
                jLabel43.setText("Buah");

                jLabel44.setBackground(new java.awt.Color(25, 26, 25));
                jLabel44.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel44.setForeground(new java.awt.Color(25, 26, 25));
                jLabel44.setText("Quantitas");

                jPanel22.setBackground(new java.awt.Color(78, 159, 61));
                jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));

                jLabel7.setBackground(new java.awt.Color(255, 255, 255));
                jLabel7.setFont(new java.awt.Font("Cascadia Mono", 0, 16)); // NOI18N
                jLabel7.setForeground(new java.awt.Color(255, 255, 255));
                jLabel7.setText("        Tambah");
                jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel7MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
                jPanel22.setLayout(jPanel22Layout);
                jPanel22Layout.setHorizontalGroup(
                        jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                );
                jPanel22Layout.setVerticalGroup(
                        jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                );

                jComboBox4.setBackground(new java.awt.Color(223, 232, 197));
                jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                jTextField2.setBackground(new java.awt.Color(223, 232, 197));
                jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                jTextField2MouseExited(evt);
                        }
                });
                jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                jTextField2KeyReleased(evt);
                        }
                });

                javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
                jPanel18.setLayout(jPanel18Layout);
                jPanel18Layout.setHorizontalGroup(
                        jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel18Layout.createSequentialGroup()
                                                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(15, 15, 15))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                                                .addComponent(jLabel44)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel18Layout.createSequentialGroup()
                                                                .addComponent(jLabel43)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(14, 14, 14))))
                );
                jPanel18Layout.setVerticalGroup(
                        jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                                .addContainerGap(15, Short.MAX_VALUE)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel43)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel44)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))
                );

                jTable5.setBackground(new java.awt.Color(223, 232, 197));
                jTable5.setFont(new java.awt.Font("Cascadia Mono", 1, 12)); // NOI18N
                jTable5.setForeground(new java.awt.Color(25, 26, 25));
                jTable5.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null}
                        },
                        new String [] {
                                "Buah", "QTY", "Harga", "Kasir"
                        }
                ));
                jTable5.setGridColor(new java.awt.Color(78, 159, 61));
                jScrollPane5.setViewportView(jTable5);

                jPanel16.setBackground(new java.awt.Color(219, 232, 181));
                jPanel16.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));

                jPanel3.setBackground(new java.awt.Color(78, 159, 61));

                jLabel45.setFont(new java.awt.Font("Cascadia Mono", 0, 24)); // NOI18N
                jLabel45.setForeground(new java.awt.Color(255, 255, 255));
                jLabel45.setText("TOTAL");

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel45)
                                .addContainerGap(15, Short.MAX_VALUE))
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                );

                jLabel46.setBackground(new java.awt.Color(223, 232, 197));
                jLabel46.setFont(new java.awt.Font("Cascadia Mono", 0, 18)); // NOI18N
                jLabel46.setForeground(new java.awt.Color(25, 26, 25));
                jLabel46.setText("Rp. Harga Dirimu");

                javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
                jPanel16.setLayout(jPanel16Layout);
                jPanel16Layout.setHorizontalGroup(
                        jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel16Layout.setVerticalGroup(
                        jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                jPanel19.setBackground(new java.awt.Color(78, 159, 61));
                jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));

                jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-checkout-40.png"))); // NOI18N

                javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
                jPanel19.setLayout(jPanel19Layout);
                jPanel19Layout.setHorizontalGroup(
                        jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel49)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel19Layout.setVerticalGroup(
                        jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                );

                jPanel21.setBackground(new java.awt.Color(223, 232, 197));

                jLabel48.setBackground(new java.awt.Color(25, 26, 25));
                jLabel48.setFont(new java.awt.Font("Cascadia Mono", 0, 14)); // NOI18N
                jLabel48.setForeground(new java.awt.Color(25, 26, 25));
                jLabel48.setText("Transaksi Baru");

                jPanel25.setBackground(new java.awt.Color(78, 159, 61));
                jPanel25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(78, 159, 61), 2, true));

                jLabel50.setBackground(new java.awt.Color(255, 255, 255));
                jLabel50.setFont(new java.awt.Font("Cascadia Mono", 0, 16)); // NOI18N
                jLabel50.setForeground(new java.awt.Color(255, 255, 255));
                jLabel50.setText("         Buat");
                jLabel50.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                jLabel50MouseClicked(evt);
                        }
                });

                javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
                jPanel25.setLayout(jPanel25Layout);
                jPanel25Layout.setHorizontalGroup(
                        jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                );
                jPanel25Layout.setVerticalGroup(
                        jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                );

                jComboBox5.setBackground(new java.awt.Color(223, 232, 197));
                jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

                javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
                jPanel21.setLayout(jPanel21Layout);
                jPanel21Layout.setHorizontalGroup(
                        jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel21Layout.createSequentialGroup()
                                                .addComponent(jLabel48)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(15, 15, 15))
                );
                jPanel21Layout.setVerticalGroup(
                        jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel21Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel48)
                                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                );

                javax.swing.GroupLayout buatTransaksiLayout = new javax.swing.GroupLayout(buatTransaksi);
                buatTransaksi.setLayout(buatTransaksiLayout);
                buatTransaksiLayout.setHorizontalGroup(
                        buatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buatTransaksiLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(buatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(25, 25, 25)
                                .addGroup(buatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(buatTransaksiLayout.createSequentialGroup()
                                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(35, 35, 35))
                );
                buatTransaksiLayout.setVerticalGroup(
                        buatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(buatTransaksiLayout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(buatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(buatTransaksiLayout.createSequentialGroup()
                                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(buatTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(buatTransaksiLayout.createSequentialGroup()
                                                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(15, 15, 15)
                                                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(52, Short.MAX_VALUE))
                );

                jPanel6.add(buatTransaksi, "card5");

                dataPenjualan.setBackground(new java.awt.Color(216, 233, 168));
                dataPenjualan.setForeground(new java.awt.Color(255, 255, 255));

                jLabel32.setBackground(new java.awt.Color(255, 255, 255));
                jLabel32.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
                jLabel32.setForeground(new java.awt.Color(25, 26, 25));
                jLabel32.setText("Data Penjualan");

                jTable3.setBackground(new java.awt.Color(216, 233, 168));
                jTable3.setForeground(new java.awt.Color(25, 26, 25));
                jTable3.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4"
                        }
                ));
                jScrollPane3.setViewportView(jTable3);

                javax.swing.GroupLayout dataPenjualanLayout = new javax.swing.GroupLayout(dataPenjualan);
                dataPenjualan.setLayout(dataPenjualanLayout);
                dataPenjualanLayout.setHorizontalGroup(
                        dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataPenjualanLayout.createSequentialGroup()
                                .addGap(308, 308, 308)
                                .addComponent(jLabel32)
                                .addContainerGap(370, Short.MAX_VALUE))
                        .addGroup(dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dataPenjualanLayout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(98, Short.MAX_VALUE)))
                );
                dataPenjualanLayout.setVerticalGroup(
                        dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataPenjualanLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel32)
                                .addContainerGap(507, Short.MAX_VALUE))
                        .addGroup(dataPenjualanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dataPenjualanLayout.createSequentialGroup()
                                        .addGap(74, 74, 74)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(113, Short.MAX_VALUE)))
                );

                jPanel6.add(dataPenjualan, "card5");

                dataPelanggan.setBackground(new java.awt.Color(216, 233, 168));
                dataPelanggan.setForeground(new java.awt.Color(255, 255, 255));

                jLabel27.setBackground(new java.awt.Color(255, 255, 255));
                jLabel27.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
                jLabel27.setForeground(new java.awt.Color(25, 26, 25));
                jLabel27.setText("Data Pelanggan");

                jTable2.setBackground(new java.awt.Color(216, 233, 168));
                jTable2.setForeground(new java.awt.Color(25, 26, 25));
                jTable2.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4"
                        }
                ));
                jScrollPane2.setViewportView(jTable2);

                javax.swing.GroupLayout dataPelangganLayout = new javax.swing.GroupLayout(dataPelanggan);
                dataPelanggan.setLayout(dataPelangganLayout);
                dataPelangganLayout.setHorizontalGroup(
                        dataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataPelangganLayout.createSequentialGroup()
                                .addGap(301, 301, 301)
                                .addComponent(jLabel27)
                                .addContainerGap(370, Short.MAX_VALUE))
                        .addGroup(dataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dataPelangganLayout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(99, Short.MAX_VALUE)))
                );
                dataPelangganLayout.setVerticalGroup(
                        dataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataPelangganLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel27)
                                .addContainerGap(508, Short.MAX_VALUE))
                        .addGroup(dataPelangganLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dataPelangganLayout.createSequentialGroup()
                                        .addGap(76, 76, 76)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(132, Short.MAX_VALUE)))
                );

                jPanel6.add(dataPelanggan, "card5");

                dataKaryawan.setBackground(new java.awt.Color(216, 233, 168));
                dataKaryawan.setForeground(new java.awt.Color(255, 255, 255));

                jLabel33.setBackground(new java.awt.Color(255, 255, 255));
                jLabel33.setFont(new java.awt.Font("sansserif", 1, 13)); // NOI18N
                jLabel33.setForeground(new java.awt.Color(25, 26, 25));
                jLabel33.setText("Data Karyawan");

                jTable1.setBackground(new java.awt.Color(216, 233, 168));
                jTable1.setForeground(new java.awt.Color(25, 26, 25));
                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][] {
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null},
                                {null, null, null, null}
                        },
                        new String [] {
                                "Title 1", "Title 2", "Title 3", "Title 4"
                        }
                ));
                jScrollPane1.setViewportView(jTable1);

                javax.swing.GroupLayout dataKaryawanLayout = new javax.swing.GroupLayout(dataKaryawan);
                dataKaryawan.setLayout(dataKaryawanLayout);
                dataKaryawanLayout.setHorizontalGroup(
                        dataKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataKaryawanLayout.createSequentialGroup()
                                .addGroup(dataKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(dataKaryawanLayout.createSequentialGroup()
                                                .addGap(309, 309, 309)
                                                .addComponent(jLabel33))
                                        .addGroup(dataKaryawanLayout.createSequentialGroup()
                                                .addGap(42, 42, 42)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(29, Short.MAX_VALUE))
                );
                dataKaryawanLayout.setVerticalGroup(
                        dataKaryawanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataKaryawanLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel33)
                                .addGap(32, 32, 32)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jPanel6.add(dataKaryawan, "card5");

                about.setBackground(new java.awt.Color(216, 233, 168));
                about.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

                jLabel36.setBackground(new java.awt.Color(255, 255, 255));
                jLabel36.setFont(new java.awt.Font("Cascadia Mono", 0, 24)); // NOI18N
                jLabel36.setForeground(new java.awt.Color(25, 26, 25));
                jLabel36.setText("About Us");
                about.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 120, 120, 40));

                jLabel30.setFont(new java.awt.Font("Cascadia Mono", 0, 18)); // NOI18N
                jLabel30.setForeground(new java.awt.Color(25, 26, 25));
                jLabel30.setText("<html>Kami kelompok 1 dari golongan A, yaudah gitu aja mau ngomong apa lagi? aku gatau mau nulis apa jadi ya gitu deh jadinya oh iya kalo mau kasih word wrap di label tinggal ngasih tag html aja trus teksnya dimasukin ke dalem tag itu. ok tq</html>");
                about.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, 650, 150));

                jPanel6.add(about, "card7");

                jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 790, 550));

                jLabel24.setBackground(new java.awt.Color(25, 26, 25));
                jLabel24.setFont(new java.awt.Font("Cascadia Mono", 1, 18)); // NOI18N
                jLabel24.setForeground(new java.awt.Color(255, 255, 255));
                jLabel24.setText("<html><center>Welcome</center></html>");
                jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 150, 60));

                jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-user-96.png"))); // NOI18N
                jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, 100, 120));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                pack();
        }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
       int dialogbtn = JOptionPane.YES_NO_OPTION;
       int dialogresult = JOptionPane.showConfirmDialog(this, "Yakin kh?", "Warning", dialogbtn);
       
       if (dialogresult == 0){
           this.setVisible(false);
	       try {
		       new Login_Form().setVisible(true);
	       } catch (SQLException | ClassNotFoundException ex) {
		       Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
	       }
       }
       else {
           
       }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
      jPanel6.removeAll();
      jPanel6.repaint();
      jPanel6.revalidate();
      
      jPanel6.add(cekStokBuah);
      jPanel6.repaint();
      jPanel6.revalidate();
        
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
      jPanel6.removeAll();
      jPanel6.repaint();
      jPanel6.revalidate();
      
      jPanel6.add(home);
      jPanel6.repaint();
      jPanel6.revalidate();
      initThings();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
	jPanel6.removeAll();
	jPanel6.repaint();
	jPanel6.revalidate();

	jPanel6.add(about);
	jPanel6.repaint();
	jPanel6.revalidate();
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
	jPanel6.removeAll();
	jPanel6.repaint();
	jPanel6.revalidate();

	jPanel6.add(dataPenjualan);
	jPanel6.repaint();
	jPanel6.revalidate();
	tabelPenjualan();
    }//GEN-LAST:event_jLabel3MouseClicked

        private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
	jPanel6.removeAll();
	jPanel6.repaint();
	jPanel6.revalidate();

	jPanel6.add(dataPelanggan);
	jPanel6.repaint();
	jPanel6.revalidate();

	tabelPelanggan();
        }//GEN-LAST:event_jLabel4MouseClicked

        private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
	jPanel6.removeAll();
	jPanel6.repaint();
	jPanel6.revalidate();

	jPanel6.add(dataKaryawan);
	jPanel6.repaint();
	jPanel6.revalidate();

	tabelKaryawan();
        }//GEN-LAST:event_jLabel5MouseClicked

        private void homeComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_homeComponentAdded

        }//GEN-LAST:event_homeComponentAdded

        private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
                // TODO add your handling code here:
		    DefaultTableModel model = new DefaultTableModel();
		    model.addColumn("ID");
		    model.addColumn("Nama");
		    model.addColumn("Stok");
		    model.addColumn("Harga");
		    try{
			    String sql = "select * from tb_buah where nama like '%" + jTextField1.getText() + "%'";
			    pst=conn.prepareStatement(sql);
			    rs=pst.executeQuery();
			    while(rs.next()){
				    model.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
			    }
			    jTable4.setModel(model);
		    }catch(SQLException e){
		    }
        }//GEN-LAST:event_jButton1MouseClicked

        private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
                // TODO add your handling code here:
		tabelBuah();
        }//GEN-LAST:event_jPanel4MouseClicked

        private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
                // TODO add your handling code here:
        }//GEN-LAST:event_jTextField1KeyPressed

        private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
                // TODO add your handling code here:
			jPanel6.removeAll();
			jPanel6.repaint();
			jPanel6.revalidate();

			jPanel6.add(buatTransaksi);
			jPanel6.repaint();
			jPanel6.revalidate();
        }//GEN-LAST:event_jLabel31MouseClicked

        private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
                // TODO add your handling code here:
        }//GEN-LAST:event_jPanel15MouseClicked

        private void jComboBox3PopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jComboBox3PopupMenuWillBecomeInvisible
                // TODO add your handling code here:
		try{
			String sql = "select t.tanggal, p.nama from tb_transaksi as t join tb_pelanggan as p on t.id_pelanggan = p.id where t.no_faktur = " + jComboBox3.getSelectedItem();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				jLabel9.setText(rs.getString(1));
				jLabel47.setText(rs.getString(2));
				tabelPenjualan((String) jComboBox3.getSelectedItem());
			}
		}catch(SQLException ex){
			System.out.println("bruh");
		}

        }//GEN-LAST:event_jComboBox3PopupMenuWillBecomeInvisible

        private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
                // TODO add your handling code here:
		try {
			Integer.parseInt(jTextField2.getText());
		} catch (NumberFormatException e) {
			jTextField2.setText(null);
		}
        }//GEN-LAST:event_jTextField2KeyReleased

        private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
                // TODO add your handling code here:
		try{
			int um = Integer.valueOf(jTextField2.getText());
			System.out.println(um);
			if( um > 0 ){
				String sql = "select id from tb_buah where nama = '" + jComboBox4.getSelectedItem() + "'";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				while(rs.next()){
					idBuah = rs.getString(1);
				}
				sql = "insert into tb_detail_transaksi values(null, '" + jComboBox3.getSelectedItem() + "','" + idBuah + "','" + jTextField2.getText() + "','" + id + "')";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				tabelPenjualan((String) jComboBox3.getSelectedItem());
			}
		}catch(SQLException ex){
			System.out.println("gabisa gan");
		}
        }//GEN-LAST:event_jLabel7MouseClicked

        private void jTextField2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MouseExited
                // TODO add your handling code here:
		if(jTextField2.getText().equals("")){
			jTextField2.setText("0");
		}
        }//GEN-LAST:event_jTextField2MouseExited

        private void jLabel50MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel50MouseClicked
		// TODO add your handling code here:
		String sql = "";
        }//GEN-LAST:event_jLabel50MouseClicked

        private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_jTextField4ActionPerformed

        private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_jTextField5ActionPerformed

        private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
                // TODO add your handling code here:
		int row = jTable4.rowAtPoint(evt.getPoint());
		String idBuah = jTable4.getValueAt(row,0).toString();
		String nama = jTable4.getValueAt(row,1).toString();
		String stok = jTable4.getValueAt(row,2).toString();
		String harga = jTable4.getValueAt(row,3).toString();
		if(jTable4.getValueAt(row, 0) == null){
			jTextField7.setText("");
		}else{
			jTextField7.setText(idBuah);
		}
		if(jTable4.getValueAt(row, 1) == null){
			jTextField3.setText("");
		}else{
			jTextField3.setText(nama);
			jTextField3.disable();
		}
		if(jTable4.getValueAt(row, 2) == null){
			jTextField4.setText("");
		}else{
			jTextField4.setText(stok);
		}
		if(jTable4.getValueAt(row, 3) == null){
			jTextField5.setText("");
		}else{
			jTextField5.setText(harga);
		}
        }//GEN-LAST:event_jTable4MouseClicked

        private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_jTextField3ActionPerformed

        private void jLabel55MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseClicked
                // TODO add your handling code here:
		String sql = "insert into tb_buah values(null, '" + jTextField3.getText() + "','"+ jTextField4.getText() + "','" + jTextField5.getText() + "')";
		System.out.println(sql);
		try{
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
           JOptionPane.showMessageDialog(null,"Data telah dimasukkan");
		}catch(SQLException ex){
           JOptionPane.showMessageDialog(null,"sike!!! gabisa");
		}
		tabelBuah();
		
        }//GEN-LAST:event_jLabel55MouseClicked

        private void jLabel58MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel58MouseClicked
                // TODO add your handling code here:
		clear();
        }//GEN-LAST:event_jLabel58MouseClicked

        private void jLabel56MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseClicked
                // TODO add your handling code here:
		String sql = "update tb_buah set nama = '" + jTextField3.getText() + "', stok = '"+ jTextField4.getText() + "', harga = '" + jTextField5.getText() + "' where id = '" + jTextField7.getText() + "'";
		System.out.println(sql);
		try{
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
           JOptionPane.showMessageDialog(null,"Data telah diupdate");
		}catch(SQLException ex){
           JOptionPane.showMessageDialog(null,"Gagal atau Masih ada data yang terrelasi");
		}
		tabelBuah();
        }//GEN-LAST:event_jLabel56MouseClicked

        private void jLabel57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel57MouseClicked
                // TODO add your handling code here:
		String sql = "delete from tb_buah where id = '" + jTextField7.getText() + "'";
		System.out.println(sql);
		try{
		pst = conn.prepareStatement(sql);
		rs = pst.executeQuery();
           JOptionPane.showMessageDialog(null,"Data telah dihapus");
		}catch(SQLException ex){
           JOptionPane.showMessageDialog(null,"Gagal atau Masih ada data yang terrelasi");
		}
		tabelBuah();
		tabelBuah();
        }//GEN-LAST:event_jLabel57MouseClicked

        private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
                // TODO add your handling code here:
        }//GEN-LAST:event_jTextField7ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /* Create and display the form */
	System.setProperty("awt.useSystemAAFontSettings", "on");
        java.awt.EventQueue.invokeLater(() -> {
		try {
			new Dashboard().setVisible(true);
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
		}
	});
    }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel about;
        private javax.swing.JPanel buatTransaksi;
        private javax.swing.JPanel cekStokBuah;
        private javax.swing.JPanel dataKaryawan;
        private javax.swing.JPanel dataPelanggan;
        private javax.swing.JPanel dataPenjualan;
        private javax.swing.JPanel home;
        private javax.swing.JButton jButton1;
        private javax.swing.JComboBox<String> jComboBox3;
        private javax.swing.JComboBox<String> jComboBox4;
        private javax.swing.JComboBox<String> jComboBox5;
        private javax.swing.JDialog jDialog1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel18;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel21;
        private javax.swing.JLabel jLabel23;
        private javax.swing.JLabel jLabel24;
        private javax.swing.JLabel jLabel25;
        private javax.swing.JLabel jLabel26;
        private javax.swing.JLabel jLabel27;
        private javax.swing.JLabel jLabel28;
        private javax.swing.JLabel jLabel29;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel30;
        private javax.swing.JLabel jLabel31;
        private javax.swing.JLabel jLabel32;
        private javax.swing.JLabel jLabel33;
        private javax.swing.JLabel jLabel34;
        private javax.swing.JLabel jLabel35;
        private javax.swing.JLabel jLabel36;
        private javax.swing.JLabel jLabel37;
        private javax.swing.JLabel jLabel38;
        private javax.swing.JLabel jLabel39;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel40;
        private javax.swing.JLabel jLabel41;
        private javax.swing.JLabel jLabel42;
        private javax.swing.JLabel jLabel43;
        private javax.swing.JLabel jLabel44;
        private javax.swing.JLabel jLabel45;
        private javax.swing.JLabel jLabel46;
        private javax.swing.JLabel jLabel47;
        private javax.swing.JLabel jLabel48;
        private javax.swing.JLabel jLabel49;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel50;
        private javax.swing.JLabel jLabel51;
        private javax.swing.JLabel jLabel52;
        private javax.swing.JLabel jLabel53;
        private javax.swing.JLabel jLabel54;
        private javax.swing.JLabel jLabel55;
        private javax.swing.JLabel jLabel56;
        private javax.swing.JLabel jLabel57;
        private javax.swing.JLabel jLabel58;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel10;
        private javax.swing.JPanel jPanel11;
        private javax.swing.JPanel jPanel12;
        private javax.swing.JPanel jPanel13;
        private javax.swing.JPanel jPanel14;
        private javax.swing.JPanel jPanel15;
        private javax.swing.JPanel jPanel16;
        private javax.swing.JPanel jPanel17;
        private javax.swing.JPanel jPanel18;
        private javax.swing.JPanel jPanel19;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel20;
        private javax.swing.JPanel jPanel21;
        private javax.swing.JPanel jPanel22;
        private javax.swing.JPanel jPanel23;
        private javax.swing.JPanel jPanel24;
        private javax.swing.JPanel jPanel25;
        private javax.swing.JPanel jPanel26;
        private javax.swing.JPanel jPanel27;
        private javax.swing.JPanel jPanel28;
        private javax.swing.JPanel jPanel29;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel30;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
        private javax.swing.JPanel jPanel9;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JScrollPane jScrollPane3;
        private javax.swing.JScrollPane jScrollPane4;
        private javax.swing.JScrollPane jScrollPane5;
        private javax.swing.JSeparator jSeparator2;
        private javax.swing.JTable jTable1;
        private javax.swing.JTable jTable2;
        private javax.swing.JTable jTable3;
        private javax.swing.JTable jTable4;
        private javax.swing.JTable jTable5;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTextField jTextField3;
        private javax.swing.JTextField jTextField4;
        private javax.swing.JTextField jTextField5;
        private javax.swing.JTextField jTextField7;
        // End of variables declaration//GEN-END:variables
}
