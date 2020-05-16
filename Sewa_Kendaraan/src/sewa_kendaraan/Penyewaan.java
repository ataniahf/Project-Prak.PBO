package sewa_kendaraan;
import java.awt.Font;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class Penyewaan extends JFrame{//inherritance
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/sewa_mobil";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;
    
    JLabel judul = new JLabel("DATA MOBIL"); 
    JLabel lKTP= new JLabel("No KTP");
    JTextField tfKTP = new JTextField();
    JLabel lNama = new JLabel("Nama Penyewa");
    JTextField tfNama = new JTextField();
    JLabel lNoTelp = new JLabel("No Telepon");
    JTextField tfNoTelp = new JTextField();
    JLabel lNopol = new JLabel("Plat Mobil");
    JTextField tfNopol = new JTextField();
    JLabel lMerk = new JLabel("Merk Mobil");
    JLabel tfMerk = new JLabel("-");
    JLabel ltgl_masuk = new JLabel("Tanggal masuk");
    JTextField tftgl_masuk = new JTextField();
    JLabel ljangka = new JLabel("Lama penyewaan");
    JTextField tfjangka = new JTextField();
    JButton btnSearch = new JButton("Search");
    JButton btnRefershPanel = new JButton("Refesh");
    JButton btnCreatePanel = new JButton("Create");
    JButton btnDeletePanel = new JButton("Delete");
    JButton btnExitPanel = new JButton("Exit");
    JTable tabel;
    DefaultTableModel tabelModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"Nopol","Merk","Tipe","Tahun"};
    
   public Penyewaan(){//method penyewaan
       //exception (menangani error agar program tetap berjalan)
       try {//kalo bener
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }  
        tabelModel = new DefaultTableModel (namaKolom,0);
        tabel = new JTable(tabelModel);
        scrollPane = new JScrollPane(tabel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setSize(1000,580);
        setLocation(225,75);
        
        add(judul);
        judul.setBounds(350, 10, 200, 20);
        judul.setFont(new Font("",Font.CENTER_BASELINE, 15));
        add(lKTP);
        lKTP.setBounds(10,300,90,20);
        add(tfKTP);
        tfKTP.setBounds(120,300,120,20);
        add(lNama);
        lNama.setBounds(10,320,90,20);
        add(tfNama);
        tfNama.setBounds(120,320,120,20);
        add(lNoTelp);
        lNoTelp.setBounds(10,340,90,20);
        add(tfNoTelp);
	tfNoTelp.setBounds(120,340,120,20);
        add(lNopol);
        lNopol.setBounds(10,360,90,20);
        add(tfNopol);
	tfNopol.setBounds(120,360,120,20);
        add(lMerk);
        lMerk.setBounds(10,380,90,20);
        add(tfMerk);
	tfMerk.setBounds(120,380,120,20);
        add(ltgl_masuk);
        ltgl_masuk.setBounds(10,400,90,20);
        add(tftgl_masuk);
	tftgl_masuk.setBounds(120,400,120,20);
        add(ljangka);
        ljangka.setBounds(10,420,90,20);
        add(tfjangka);
	tfjangka.setBounds(120,420,120,20);
        add(btnRefershPanel);
        btnRefershPanel.setBounds(400,300,100,50);
        add(btnCreatePanel);
        btnCreatePanel.setBounds(400,370,100,50);
        add(btnExitPanel);
        btnExitPanel.setBounds(600,370,100,50);
        add(scrollPane);
        scrollPane.setBounds(110,50,600,200);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
      if (this.getBanyakData() != 0) { //memanggil getbanyak data
            String dataPenyewaan[][] = this.readPenyewaan(); //this memanggil kelas sendiri dataPenyewaan
            tabel.setModel((new JTable(dataPenyewaan, namaKolom)).getModel());
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
btnCreatePanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk create(event ActionListener untuk button, enter di text,milih menu)
            if (tfKTP.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String KTP = tfKTP.getText();
                String Nama = tfNama.getText();
                String No_Telp = tfNoTelp.getText();
                String Nopol = tfNopol.getText();
                String Merk = tfMerk.getText();
                String tgl_masuk = tftgl_masuk.getText();
                String jangka = tfjangka.getText();
                
                this.insertPenyewaan(KTP, Nama, No_Telp, Nopol, Merk, tgl_masuk, jangka);
                this.updateMobil(Nopol);
  
                String dataPenyewaan[][] = this.readPenyewaan();
                tabel.setModel(new JTable(dataPenyewaan,namaKolom).getModel());
            }
        });
tabel.addMouseListener(new MouseAdapter() {//registrasi mouse untuk exit (event MouseListener untuk menekan button mouse saat di atas komponen)
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = tabel.getSelectedRow();
               int kolom = tabel.getSelectedColumn(); 
               String dataterpilih = tabel.getValueAt(baris, 0).toString();
               btnDeletePanel.addActionListener((ActionEvent f) -> {
                  deletePenyewaan(dataterpilih);
                  String dataPenyewaan[][] = readPenyewaan();
                tabel.setModel(new JTable(dataPenyewaan,namaKolom).getModel());
                });         
           }
        });
btnRefershPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk refresh(event ActionListener untuk button, enter di text,milih menu)
          tfKTP.setText("");//encaptulation set
          tfNama.setText("");
          tfNoTelp.setText("");
          tfNopol.setText("");
          tfMerk.setText("");
          tftgl_masuk.setText("");
          tfjangka.setText("");
        });
btnExitPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk exit (event ActionListener untuk button, enter di text,milih menu)
        Menu g = new Menu();//modifier object menu
        dispose();//keluar
        });
tfNopol.addActionListener((ActionEvent e) -> {//registrasi listener untuk menampilkan data sesuai nopol(event ActionListener untuk button, enter di text,milih menu)
    String Nopol = tfNopol.getText();//encaptulation(get)
    //exception (menangani error agar program tetap berjalan)
    try {//kalo bener
        String query = "SELECT * FROM `mobil` WHERE `Nopol` =  '" + Nopol + "'";
        statement = koneksi.createStatement();
        ResultSet resultSet = statement.executeQuery(query); 
        while (resultSet.next()) { 
            tfMerk.setText(resultSet.getString("Merk"));   
        }
    } catch (SQLException sql) {//kalo salah
        System.out.println(sql.getMessage());
    }
       });
}
int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `mobil`";
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
String[][] readPenyewaan() {//menampilkan data riwayat dari tabel riwayat di database
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][4];
            String query = "Select `Nopol`,`Merk`,`Tipe`,`Tahun` from `mobil` where status='Tersedia'  ";
         
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Nopol");
                data[jmlData][1] = resultSet.getString("Merk");
                data[jmlData][2] = resultSet.getString("Tipe");
                data[jmlData][3] = resultSet.getString("Tahun");
                
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }
//method insert data penyewan tabel penyewaan di database
public void insertPenyewaan(String KTP, String Nama, String No_Telp, String Nopol, String Merk, String tgl_masuk, String jangka) {
        //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            String query = "INSERT INTO `penyewaan`(`KTP`,`Nama`,`No_Telp`,`Nopol`,`Merk`,`tgl_masuk`,`jangka`) VALUES ('"+KTP+"','"+Nama+"','"+No_Telp+"','"+Nopol+"','"+Merk+"','"+tgl_masuk+"','"+jangka+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        JOptionPane.showMessageDialog(null,"data berhasil ditambahkan");
        }catch(Exception sql){//kalo salah
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }     
    }
void deletePenyewaan(String KTP) {//method delete data penyewaan tabel penyewaan di database karena
      //exception (menangani error agar program tetap berjalan)
        try{
            String query = "DELETE FROM `penyewaan` WHERE `KTP` = '"+KTP+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "berhasil dihapus" );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }
void updateMobil(String Nopol) {//method update data mobil tabel mobil di database
      //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            String query = "UPDATE mobil SET Status = 'Keluar' where `Nopol` = '"+Nopol+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
        }catch(SQLException sql){//kalo salah
            System.out.println(sql.getMessage());
        }
    }
}