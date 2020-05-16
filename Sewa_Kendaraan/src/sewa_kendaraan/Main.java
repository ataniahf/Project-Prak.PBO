package sewa_kendaraan;


import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

class Main {
    public static void main(String[] args) {//method menu 
        Menu g = new Menu();//membuat object menu
    } 
}
//LAYOUT
class Menu extends JFrame {//inherritance
    JLabel menu = new JLabel("MENU UTAMA");
    JButton tombolPenyewa = new JButton("Penyewa");
    JButton tombolKaryawan = new JButton("Karyawan");
    JButton tombolMobil = new JButton("Mobil");
    JButton tombolPenyewaan = new JButton("Penyewaan");
    JButton tombolPengembalian = new JButton("Pengembalian");
    JButton tombolRiwayat = new JButton("Riwayat");
    
    public Menu() {//method
    
        setTitle("MENU");//mengatur judul output
        setDefaultCloseOperation(3);//mengatur operasi default dalam container
        setSize(350,250);//mengatur ukuran output
        setLocation(500,275);//mengatur lokasi output
        setLayout(null);//mengatur tampilan
        
        add(menu);
        
        add(tombolKaryawan);
        add(tombolMobil);
        add(tombolPenyewaan);
        add(tombolPengembalian);
        add(tombolRiwayat);
        menu.setBounds(110,10,120,20);
        menu.setFont(new Font("",Font.CENTER_BASELINE, 15));
        
        tombolKaryawan.setBounds(20,70,120,40);
        tombolMobil.setBounds(20,120,120,40);
        tombolPenyewaan.setBounds(180,50,120,40);
        tombolPengembalian.setBounds(180,100,120,40);
        tombolRiwayat.setBounds(180,150,120,40);
        
        tombolKaryawan.addActionListener((ActionEvent e) -> {//registrasi listener untuk button karyawan (event ActionListener untuk button, enter di text,milih menu)
            Karyawan b = new Karyawan();//membuat object karyawan
            dispose();//perintah keluar
        });
        tombolMobil.addActionListener((ActionEvent e) -> {
            Mobil c = new Mobil();
            dispose();
        });
        tombolPenyewaan.addActionListener((ActionEvent e) -> {
            Penyewaan d = new Penyewaan();
            dispose();
        });
        tombolPengembalian.addActionListener((ActionEvent e) -> {
            Pengembalian f = new Pengembalian();
            dispose();
        });
        
        tombolRiwayat.addActionListener((ActionEvent e) -> {
            Riwayat g = new Riwayat();
            dispose();
        });
        
        
        setVisible(true);//berfungsi untuk mengatur output agar d tampilkan
    }
    
}