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


public class Karyawan extends JFrame{//inherritance
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";//method
    static final String DB_URL = "jdbc:mysql://localhost/sewa_mobil";
    static final String USER = "root";
    static final String PASS = "";
    
    Connection koneksi;//untuk membangun koneksi
    Statement statement;//intuk membangun statemen
    //layout
    JLabel judul = new JLabel("DATA KARYAWAN"); //membuat 
    JLabel lNIK= new JLabel("NIK");
    JTextField tfNIK = new JTextField();
    JLabel lNama = new JLabel("Nama Karyawan");
    JTextField tfNama = new JTextField();
    JLabel lJk = new JLabel("Jenis Kelamin");
    JRadioButton rbPria = new JRadioButton(" Laki-Laki ");
    JRadioButton rbWanita = new JRadioButton(" Perempuan ");

   
    JButton btnRefershPanel = new JButton("Refesh");
    JButton btnCreatePanel = new JButton("Create");
    JButton btnDeletePanel = new JButton("Delete");
    JButton btnExitPanel = new JButton("Exit");

    JTable tabel;
    DefaultTableModel tabelModel;
    JScrollPane scrollPane;
    Object namaKolom[] = {"NIK","Nama Karyawan","Jenis Kelamin"};
   
   public Karyawan(){//method karyawan
       //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {//kalo error
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
        add(lNIK);
        lNIK.setBounds(10,300,90,20);
        add(tfNIK);
        tfNIK.setBounds(120,300,120,20);
        add(lNama);
        lNama.setBounds(10,320,90,20);
        add(tfNama);
        tfNama.setBounds(120,320,120,20);
        add(lJk);
        lJk.setBounds(10,340,120,20);
        add(rbPria);
	rbPria.setBounds(130,340,100,20);
        add(rbWanita);
	rbWanita.setBounds(230,340,100,20);

        
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
   
if (this.getBanyakData() != 0) {//memanggil getbanyak data
            String dataKaryawan[][] = this.readKaryawan();//this =kelas sendiri
            tabel.setModel((new JTable(dataKaryawan, namaKolom)).getModel());
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
btnCreatePanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk create(event ActionListener untuk button, enter di text,milih menu)
            if (tfNIK.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String nik = tfNIK.getText();
                String namak = tfNama.getText();
                String jk = null;
                if (rbWanita.isSelected()) {
                    jk = "perempuan";
                } else if (rbPria.isSelected() ) {
                    jk = "Laki laki";
                }
   
                this.insertKaryawan(nik, namak, jk);//thi=kelas sendiri
                String dataKaryawan[][] = readKaryawan();
                tabel.setModel(new JTable(dataKaryawan,namaKolom).getModel());
            }
        });


tabel.addMouseListener(new MouseAdapter() {//registrasi mouse untuk exit (event MouseListener untuk menekan button mouse saat di atas komponen)
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = tabel.getSelectedRow();
               int kolom = tabel.getSelectedColumn(); 
               String dataterpilih = tabel.getValueAt(baris, 0).toString();
btnDeletePanel.addActionListener((ActionEvent f) -> {//registrasi listener untuk delete(event ActionListener untuk button, enter di text,milih menu) 
                deleteKaryawan(dataterpilih);
                String dataKaryawan[][] = readKaryawan();
                tabel.setModel(new JTable(dataKaryawan,namaKolom).getModel());
                });
               
           }
        });


btnRefershPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk refresh(event ActionListener untuk button, enter di text,milih menu)
          tfNIK.setText("");//encaptulation
          tfNama.setText("");
          
        });


btnExitPanel.addActionListener((ActionEvent e) -> {//registrasi listener untuk exit (event ActionListener untuk button, enter di text,milih menu)
          Menu g = new Menu();//modifier object menu
          dispose();//perintah keluar
        });

}

int getBanyakData() {//mengambil data karyawan
        int jmlData = 0;
        //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            statement = koneksi.createStatement();
            String query = "SELECT * from `karyawan`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){//melakukan pengecekan data menggunakan perulangan while
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){//kalo error
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }

String[][] readKaryawan() {//menampilkan data karyawan dari tabel karyawan di database
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][7];//encaptulation
            String query = "Select * from `karyawan`";//menampilkan data karyawan
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){//menampilan data menggunakan perulangan while
                data[jmlData][0] = resultSet.getString("NIK");//encaptulation
                data[jmlData][1] = resultSet.getString("NamaK");
                data[jmlData][2] = resultSet.getString("Jk");

                
                jmlData++;
            }
            return data;
        }catch(SQLException e){//kalo salah
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

public void insertKaryawan(String nik, String namak, String jk) {//method input data karyawan ke tabel karyawan di database
    //exception (menangani error agar program tetap berjalan)
        try{//kalo bener
        String query = "INSERT INTO `karyawan`(`NIK`,`NamaK`,`Jk`) VALUES ('"+nik+"','"+namak+"','"+jk+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        JOptionPane.showMessageDialog(null,"Data berhasil ditambahkan");
        }catch(Exception sql){//kalo error
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
        
    }

void deleteKaryawan(String nik) {//method hapus data karyawan tabel karyawan di database
    //exception (menangani error agar program tetap berjalan)
        try{//benar
            String query = "DELETE FROM `karyawan` WHERE `NIK` = '"+nik+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "berhasil dihapus" );
        }catch(SQLException sql){//kalo error
            System.out.println(sql.getMessage());
        }
    }

}
