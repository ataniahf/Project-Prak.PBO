package sewa_kendaraan;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class Mobil extends JFrame{//OOP INHERRITANCE
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/sewa_mobil";
    static final String USER = "root";
    static final String PASS = "";
    Connection koneksi;
    Statement statement;
    JLabel judul = new JLabel("DATA MOBIL"); 
    JLabel lNopol= new JLabel("Nopol");
    JTextField tfNopol = new JTextField();
    JLabel lMerk = new JLabel("Tipe Mobil");
    JTextField tfMerk = new JTextField();
    JLabel lTipe = new JLabel("Merk Mobil");
    JTextField tfTipe = new JTextField();
    JLabel lTahun = new JLabel("Tahun Mobil");
    JTextField tfTahun = new JTextField();
    JLabel lHarga = new JLabel("Harga Mobil");
    JTextField tfHarga = new JTextField();
    JButton btnRefershPanel = new JButton("Refesh");
    JButton btnCreatePanel = new JButton("Create");
    JButton btnDeletePanel = new JButton("Delete");
    JButton btnExitPanel = new JButton("Exit");
    JTable tabel;
    DefaultTableModel tabelModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"Nopol","Tipe","Merk","Tahun","Status"};
   //CONTROLLER(METHOD MOBIL)
   public Mobil(){
        try{
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
    //    
    //VIEW
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setSize(850,580);
        setLocation(225,75);
        
        add(judul);
        judul.setBounds(350, 10, 200, 20);
        judul.setFont(new Font("",Font.CENTER_BASELINE, 15));
        add(lNopol);
        lNopol.setBounds(10,300,90,20);
        add(tfNopol);
        tfNopol.setBounds(120,300,120,20);
        add(lMerk);
        lMerk.setBounds(10,320,90,20);
        add(tfMerk);
        tfMerk.setBounds(120,320,120,20);
        add(lTipe);
        lTipe.setBounds(10,340,90,20);
        add(tfTipe);
        tfTipe.setBounds(120,340,120,20);
        add(lTahun);
        lTahun.setBounds(10,360,90,20);
        add(tfTahun);
        tfTahun.setBounds(120,360,120,20);
        add(btnRefershPanel);
        btnRefershPanel.setBounds(400,300,100,50);
        add(btnCreatePanel);
        btnCreatePanel.setBounds(400,370,100,50);
        add(btnDeletePanel);
        btnDeletePanel.setBounds(600,300,100,50);
        add(btnExitPanel);
        btnExitPanel.setBounds(600,370,100,50);
        add(scrollPane);
        scrollPane.setBounds(110,50,600,200);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

if (this.getBanyakData() != 0) {//mengambil data tabel pada database
            String dataMobil[][] = this.readMobil();
            tabel.setModel((new JTable(dataMobil, namaKolom)).getModel());
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
btnCreatePanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk create(event ActionListener untuk button, enter di text,milih menu)
            if (tfNopol.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String Nopol = tfNopol.getText();//encaptulation
                String Merk = tfMerk.getText();
                String Tipe = tfTipe.getText();
                String Tahun = tfTahun.getText();
                String Harga = tfHarga.getText(); 
                this.insertMobil(Nopol, Merk, Tipe, Tahun, Harga);
                String dataMobil[][] = readMobil();
                tabel.setModel(new JTable(dataMobil,namaKolom).getModel());
            }
        });

tabel.addMouseListener(new MouseAdapter() {//registrasi mouse untuk exit (event MouseListener untuk menekan button mouse saat di atas komponen)
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = tabel.getSelectedRow();//encaptulation
               int kolom = tabel.getSelectedColumn(); 
               String dataterpilih = tabel.getValueAt(baris, 0).toString();
btnDeletePanel.addActionListener((ActionEvent f) -> {//registrasi listener untuk delete(event ActionListener untuk button, enter di text,milih menu) 
                  deleteMobil(dataterpilih);
                  String dataMobil[][] = readMobil();
                tabel.setModel(new JTable(dataMobil,namaKolom).getModel());
                }); 
           }
        });

btnRefershPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk refresh(event ActionListener untuk button, enter di text,milih menu)
          tfNopol.setText("");//encapsulation
          tfMerk.setText("");
          tfTipe.setText("");
          tfTahun.setText("");
          tfHarga.setText(""); 
        });


btnExitPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk exit (event ActionListener untuk button, enter di text,milih menu)
          Menu g = new Menu();//modifier object menu
          dispose();//perintah keluar
        });
}
//CONTROLLER
  //mengambil banyak data mobil
int getBanyakData() {//ENCAPTULATION
        int jmlData = 0;
        //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            statement = koneksi.createStatement();
            String query = "SELECT * from `mobil";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){//kalo salah
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }
//menampilkan data mobil
String[][] readMobil() {
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][8];
            String query = "Select * from `mobil`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Nopol");//encaptulation
                data[jmlData][1] = resultSet.getString("Merk");
                data[jmlData][2] = resultSet.getString("Tipe");
                data[jmlData][3] = resultSet.getString("Tahun");
                data[jmlData][4] = resultSet.getString("Status");       
                jmlData++;
            }
            return data;
        }catch(SQLException e){//kalo salah
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }
//(METHOD INSERT)
public void insertMobil(String Nopol, String Merk, String Tipe, String Tahun, String Harga) {
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            String query = "INSERT INTO `mobil`(`Nopol`,`Merk`,`Tipe`,`Tahun`,`Status`) VALUES ('"+Nopol+"','"+Merk+"','"+Tipe+"','"+Tahun+"','"+"Tersedia"+"')";        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        JOptionPane.showMessageDialog(null,"Data berhasil ditambahkan");
        }catch(Exception sql){//kalo salah
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }   
    }
//(MRTHOD DELETE NOPOL)
void deleteMobil(String Nopol) {
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            String query = "DELETE FROM `mobil` WHERE `Nopol` = '"+Nopol+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "berhasil dihapus" );
        }catch(SQLException sql){//kalo salah
            System.out.println(sql.getMessage());
        }
    }
}