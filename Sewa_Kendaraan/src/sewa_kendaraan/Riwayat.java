package sewa_kendaraan;
/**
 *
 * @author ATANIA
 */
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

public class Riwayat extends JFrame{//inherritance
    //koneksi database
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/sewa_mobil";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;
    Statement statement;
    //layout /view /form
    JLabel judul = new JLabel("DATA RIWAYAT");
    JButton btnExitPanel = new JButton("Exit");    
    JTable tabel;
    DefaultTableModel tabelModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"KTP","Nopol","Tgl Pinjam","Tgl Kembali","Lama Peminjaman","Denda","Total","NIK Karyawan"};
     
    public Riwayat(){//method riwayat
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
       
        setDefaultCloseOperation(EXIT_ON_CLOSE);//set keluar
        setLayout(null);
        setVisible(true);
        setSize(850,580);
        setLocation(225,75);       
        add(judul);
        judul.setBounds(350, 10, 200, 20);
        judul.setFont(new Font("",Font.CENTER_BASELINE, 15));
        add(btnExitPanel);
        btnExitPanel.setBounds(720,480,100,50);
        add(scrollPane);
        scrollPane.setBounds(50,50,720,400);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        if (this.getBanyakData() != 0) { //this kelas sendiri manggil banyak data
            String dataRiwayat[][] = this.readRiwayat(); //this kelas sendiri manggil read data
            tabel.setModel((new JTable(dataRiwayat, namaKolom)).getModel());//masukin data ke tabel
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");//kalo data gaada
        }        
btnExitPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk exit (event ActionListener untuk button, enter di text,milih menu)
        Menu g = new Menu();//modifier object menu
        dispose();//keluar
        });        
}    
//mengambil data di tabel riwayat
int getBanyakData() {
        int jmlData = 0;
        //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            statement = koneksi.createStatement();
            String query = "SELECT * from `riwayat`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){//perulangan while
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){//kalo salah
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }    
String[][] readRiwayat() {//menampilkan data riwayat dari tabel riwayat di database
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener 
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][8];
            String query = "SELECT * from `Riwayat";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("KTP");//encaptulation(set)
                data[jmlData][1] = resultSet.getString("Nopol");
                data[jmlData][2] = resultSet.getString("Pinjam");
                data[jmlData][3] = resultSet.getString("Kembali");
                data[jmlData][4] = resultSet.getString("Lama");
                data[jmlData][5] = resultSet.getString("Denda");
                data[jmlData][6] = resultSet.getString("Total");
                data[jmlData][7] = resultSet.getString("NIK");
                jmlData++;
            }
            return data;
        }catch(SQLException e){//kalo salah tetep jaln
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }
}