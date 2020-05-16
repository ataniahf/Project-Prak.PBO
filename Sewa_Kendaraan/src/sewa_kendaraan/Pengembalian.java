package sewa_kendaraan;
/**
 *
 * @author ATANIA
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class Pengembalian extends JFrame{//inherritance

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/sewa_mobil";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;
    //layout
    int banyak = 0;
    JLabel judul = new JLabel("DATA RIWAYAT");
    JLabel lKTP= new JLabel("KTP");
    JLabel tfKTP = new JLabel("-");
    JLabel lNama = new JLabel("Nama Penyewa");
    JLabel tfNama = new JLabel("-");
    JLabel lNo_Telp = new JLabel("No Telp");
    JLabel tfNoTelp = new JLabel("-");
    JLabel lNopol = new JLabel("Plat Mobil");
    JLabel tfNopol = new JLabel("-");
    JLabel lMerk = new JLabel("Merk Mobil");
    JLabel tfMerk = new JLabel("-");
    JLabel ljangka = new JLabel("Jangka Waktu");
    JLabel tfjangka = new JLabel("-");
    JLabel lPinjam = new JLabel("Tgl Pinjam");
    JLabel tfPinjam = new JLabel("-");
    JLabel lKembali = new JLabel("Tgl Kembali");
    JTextField tfKembali = new JTextField();
    JLabel lLama = new JLabel("Lama Pinjam");
    JLabel tfLama = new JLabel("-");
    JLabel lDenda = new JLabel("Denda");
    JLabel tfDenda = new JLabel("-");
    JLabel ltotal = new JLabel("TOTAL BAYAR");
    JLabel tftotal = new JLabel("-");
    JLabel lNIK= new JLabel("NIK");
    JTextField tfNIK = new JTextField();
    JLabel lKaryawan = new JLabel("Karyawan");
    JLabel tfKaryawan = new JLabel("-");
    JButton btnPrint = new JButton("Print");
    JButton btnSearch = new JButton("Search");
    JButton btnRefershPanel = new JButton("Refesh");
    JButton btnCreatePanel = new JButton("Ambil + Bayar");
    JButton btnExitPanel = new JButton("Exit");
    JTable tabel;
    DefaultTableModel tabelModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"No KTP","Nama Penyewa","No Telp","Nopol","Tipe","jangka Waktu"};   
   public Pengembalian(){//method pengembalian
       //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {//kalo salah
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }
        tabelModel = new DefaultTableModel (namaKolom,0);
        tabel = new JTable(tabelModel);
        scrollPane = new JScrollPane(tabel);
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setSize(850,580);
        setLocation(225,75);       
        add(judul);
        judul.setBounds(350, 10, 200, 20);
        judul.setFont(new Font("",Font.CENTER_BASELINE, 15));
        add(lKTP);
        lKTP.setBounds(10,280,90,20);
        add(tfKTP);
        tfKTP.setBounds(120,280,120,20);
        add(lNama);
        lNama.setBounds(10,300,120,20);
        add(tfNama);
	tfNama.setBounds(120,300,100,20);
        add(lNo_Telp);
        lNo_Telp.setBounds(10,320,90,20);
        add(tfNoTelp);
        tfNoTelp.setBounds(120,320,120,20);
        add(lNopol);
        lNopol.setBounds(10,340,90,20);
        add(tfNopol);
        tfNopol.setBounds(120,340,120,20);
        add(ljangka);
        ljangka.setBounds(10,360,90,20);
        add(tfjangka);
        tfjangka.setBounds(120,360,120,20);
        add(lPinjam);
        lPinjam.setBounds(10,380,90,20);
        add(tfPinjam);
        tfPinjam.setBounds(120,380,120,20);
        add(lKembali);
        lKembali.setBounds(10,400,90,20);
        add(tfKembali);
        tfKembali.setBounds(120,400,120,20);
        add(lLama);
        lLama.setBounds(10,420,90,20);
        add(tfLama);
        tfLama.setBounds(120,420,120,20);
        add(lDenda);
        lDenda.setBounds(10,440,90,20);
        add(tfDenda);
        tfDenda.setBounds(120,440,120,20);
        add(ltotal);
        ltotal.setBounds(10,460,90,20);
        add(tftotal);
        tftotal.setBounds(120,460,120,20);
        add(lNIK);
        lNIK.setBounds(10,480,90,20);
        add(tfNIK);
        tfNIK.setBounds(120,480,120,20);
        add(lKaryawan);
        lKaryawan.setBounds(10,500,90,20);
        add(tfKaryawan);
        tfKaryawan.setBounds(120,500,120,20);
        add(btnRefershPanel);
        btnRefershPanel.setBounds(400,300,100,50);
        add(btnCreatePanel);
        btnCreatePanel.setBounds(400,370,300,50);
        add(btnExitPanel);
        btnExitPanel.setBounds(600,300,100,50);
        add(scrollPane);
        scrollPane.setBounds(110,50,600,200);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);       
if (this.getBanyakData() != 0) { //memanggil getbanyak data
            String dataRiwayat[][] = this.readRiwayat(); //this=kelas sendiri
            tabel.setModel((new JTable(dataRiwayat, namaKolom)).getModel());
          
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
btnCreatePanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk create(event ActionListener untuk button, enter di text,milih menu)
            if (tfKembali.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String KTP = tfKTP.getText();
                String Nopol = tfNopol.getText();
                String tgl_masuk = tfPinjam.getText();
                String tgl_ambil = tfKembali.getText();
                long Lama = Long.parseLong(tfLama.getText());
                long Denda = Long.parseLong(tfDenda.getText());
                long Total = Long.parseLong(tftotal.getText());
                String NIK = tfNIK.getText();
                this.deleteRiwayat(KTP);
                this.insertRiwayat(KTP, Nopol, tgl_masuk, tgl_ambil,Lama,Denda,Total, NIK);
                this.updateMobil(Nopol);                
                String dataRiwayat[][] = this.readRiwayat();
                tabel.setModel(new JTable(dataRiwayat,namaKolom).getModel());            
            }
        });
tabel.addMouseListener(new MouseAdapter() {//registrasi mouse untuk exit (event MouseListener untuk menekan button mouse saat di atas komponen)
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = tabel.getSelectedRow();
               int kolom = tabel.getSelectedColumn(); 
               String dataterpilih = tabel.getValueAt(baris, 0).toString();
               try {
        String query = "SELECT * FROM `penyewaan` WHERE `KTP` = '" + dataterpilih + "'";
        statement = koneksi.createStatement();
        ResultSet resultSet = statement.executeQuery(query); 
        while (resultSet.next()) { 
            tfKTP.setText(resultSet.getString("KTP"));
            tfNama.setText(resultSet.getString("Nama"));
            tfPinjam.setText(resultSet.getString("tgl_masuk"));
            tfNoTelp.setText(resultSet.getString("No_Telp"));
            tfMerk.setText(resultSet.getString("Merk"));
            tfjangka.setText(resultSet.getString("jangka"));
            tfNopol.setText(resultSet.getString("Nopol"));
        }
    } catch (SQLException sql) {
        System.out.println(sql.getMessage());
    }               
           }
        });
tfKembali.addActionListener((ActionEvent e) -> {//registrasi listener untuk menampilkan data sesuai input(event ActionListener untuk button, enter di text,milih menu)
    String kembali = tfKembali.getText();
    String b = tfPinjam.getText();
    try {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date masuk = Date.valueOf(b);
        Date ambil = Date.valueOf(kembali);        
        long Hari1 = masuk.getTime();
        long Hari2 = ambil.getTime();
        long diff = Hari2 - Hari1;
        long Lama = diff / (24 * 60 * 60 * 1000);
        long jangka = Long.parseLong(tfjangka.getText())*400000;//biaya sewa perhari
        long denda=0;
        if (Lama-Long.parseLong(tfjangka.getText())>0) {
            denda = (Lama-Long.parseLong(tfjangka.getText()))*400000;//biaya denda perhari
        }        
        long biaya = jangka+denda;       
        tfLama.setText(Long.toString(jangka));
        tfDenda.setText(Long.toString(denda));
        tftotal.setText(Long.toString(biaya));
        tfLama.setText(Long.toString(Lama));        
    } catch (Exception a) {
        System.out.println(a.getMessage());
    }   
       });
tfNIK.addActionListener((ActionEvent e) -> {//registrasi listener untuk menampilkan data sesuai nik(event ActionListener untuk button, enter di text,milih menu)
    String nik = tfNIK.getText();
    try {
        String query = "SELECT * FROM `karyawan` WHERE `NIK` = '" + nik + "'";
        statement = koneksi.createStatement();
        ResultSet resultSet = statement.executeQuery(query); 
        while (resultSet.next()) { 
            tfKaryawan.setText(resultSet.getString("NamaK"));   
        }
    } catch (SQLException sql) {
        System.out.println(sql.getMessage());
    }
       });
btnRefershPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk refresh(event ActionListener untuk button, enter di text,milih menu)
          tfKTP.setText("");//encaptulation (set)
          tfNama.setText("");
          tfNoTelp.setText("");
          tfNopol.setText("");
          tfjangka.setText("");
          tfLama.setText("");
          tfPinjam.setText("");
          tfDenda.setText("");
          tftotal.setText("");
          tfKembali.setText("");
          tfNIK.setText("");
          tfKaryawan.setText("");
        });
btnExitPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk exit (event ActionListener untuk button, enter di text,milih menu)
          Menu g = new Menu();//modifier object menu
           dispose();//keluar
        });
}
   //mengambil data di tabel penyewaan
int getBanyakData() {
        int jmlData = 0;
        //exception (menangani error agar program tetap berjalan)
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `penyewaan`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }
String[][] readRiwayat() {//menampilkan data riwayat dari tabel riwayat di database
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][7];
            String query = "Select `KTP`,`Nama`,`No_Telp`,`Nopol`,`Merk`,`tgl_masuk`,`jangka` from `penyewaan`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("KTP");//encaptulation (set)
                data[jmlData][1] = resultSet.getString("Nama");
                data[jmlData][2] = resultSet.getString("No_Telp");
                data[jmlData][3] = resultSet.getString("Nopol");
                data[jmlData][4] = resultSet.getString("Merk");
                data[jmlData][5] = resultSet.getString("tgl_masuk");
                data[jmlData][6] = resultSet.getString("jangka");
                jmlData++;
            }
            return data;
        }catch(SQLException e){//kalo error
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }
//method insert data mobil tabel mobil di database
public void insertRiwayat(String KTP, String Nopol, String Pinjam, String Kembali, long Lama, long Denda,long Total, String NIK) {
      //exception (menangani error agar program tetap berjalan)
    try{//kalo bener
        //memasukkan data ke tabel riwayat
        String query = "INSERT INTO `riwayat`(`KTP`,`Nopol`,`Pinjam`,`Kembali`,`Lama`,`Denda`,`Total`,`NIK`) VALUES ('"+KTP+"','"+Nopol+"', '"+Pinjam+"','"+Kembali+"','"+Lama+"','"+Denda+"','"+Total+"','"+NIK+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        System.out.println("Berhasil Ditambahkan");
        JOptionPane.showMessageDialog(null,"TRANSAKSI BERHASIL");
        }catch(Exception sql){//kalo salah
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }   
    }
void deleteRiwayat(String KTP) {///method delete data mobil tabel penyewaan di database
      //exception (menangani error agar program tetap berjalan)
        try{
            String query = "DELETE FROM `penyewaan` WHERE `KTP` = '"+KTP+"'";//mengapus data penyewaan sesuai no KTP
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
           
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }
void updateMobil(String Nopol) {//method update data mobil tabel mobil di database
      //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            String query = "UPDATE mobil SET Status = 'tersedia' where `Nopol` = '"+Nopol+"'";//ini untuk memberi value status apabila mobil (tersedia)
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
        
        }catch(SQLException sql){//kalo salah
            System.out.println(sql.getMessage());
        }
    }
}