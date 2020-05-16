-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 16 Bulan Mei 2020 pada 05.50
-- Versi server: 10.4.6-MariaDB
-- Versi PHP: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sewa_mobil`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `karyawan`
--

CREATE TABLE `karyawan` (
  `NIK` int(20) NOT NULL,
  `NamaK` varchar(20) NOT NULL,
  `Jk` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `karyawan`
--

INSERT INTO `karyawan` (`NIK`, `NamaK`, `Jk`) VALUES
(123180092, 'Atania Harfiani', 'perempuan'),
(123180115, 'M Afiq Alvadeano', 'Laki laki');

-- --------------------------------------------------------

--
-- Struktur dari tabel `mobil`
--

CREATE TABLE `mobil` (
  `Nopol` varchar(15) NOT NULL,
  `Merk` varchar(20) NOT NULL,
  `Tipe` varchar(20) NOT NULL,
  `Tahun` varchar(5) NOT NULL,
  `Status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `mobil`
--

INSERT INTO `mobil` (`Nopol`, `Merk`, `Tipe`, `Tahun`, `Status`) VALUES
('A234A', 'Agya', 'Toyota', '2017', 'tersedia'),
('A345A', 'Kijang Innova', 'Toyota', '2015', 'Tersedia'),
('AB123A', 'Mobilio', 'Honda', '2013', 'Tersedia'),
('AB124B', 'Ayla', 'Daihatsu', '2017', 'Tersedia'),
('B1234A', 'Avanza', 'Toyota', '2012', 'tersedia'),
('B1235B', 'Xenia', 'Daihatsu', '2010', 'Keluar');

-- --------------------------------------------------------

--
-- Struktur dari tabel `penyewaan`
--

CREATE TABLE `penyewaan` (
  `KTP` int(20) NOT NULL,
  `Nama` varchar(20) NOT NULL,
  `No_Telp` int(15) NOT NULL,
  `Nopol` varchar(15) NOT NULL,
  `Merk` varchar(20) NOT NULL,
  `tgl_masuk` date NOT NULL,
  `jangka` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `penyewaan`
--

INSERT INTO `penyewaan` (`KTP`, `Nama`, `No_Telp`, `Nopol`, `Merk`, `tgl_masuk`, `jangka`) VALUES
(12345, 'Atania', 876234, 'B1235B', 'Xenia', '2020-05-15', '5');

-- --------------------------------------------------------

--
-- Struktur dari tabel `riwayat`
--

CREATE TABLE `riwayat` (
  `KTP` int(20) NOT NULL,
  `Nopol` varchar(15) NOT NULL,
  `Pinjam` date NOT NULL,
  `Kembali` date NOT NULL,
  `Lama` bigint(255) NOT NULL,
  `Denda` bigint(255) NOT NULL,
  `Total` bigint(255) NOT NULL,
  `NIK` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `riwayat`
--

INSERT INTO `riwayat` (`KTP`, `Nopol`, `Pinjam`, `Kembali`, `Lama`, `Denda`, `Total`, `NIK`) VALUES
(12312, '123456', '2020-01-23', '2020-02-01', 9, 2750000, 4750000, 123),
(12345, '123', '2020-12-18', '2020-12-20', 2, 0, 1500000, 123),
(12321, 'A234A', '2020-05-14', '2020-05-18', 4, 400000, 1600000, 123180092),
(123232, 'B1234A', '2020-12-02', '2020-12-03', 1, 0, 800000, 123180092),
(35091, 'A234A', '2020-04-15', '2020-04-18', 3, 0, 1200000, 123180092),
(123, 'B1234A', '2020-06-15', '2020-06-20', 5, 800000, 2000000, 123180092);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`NIK`);

--
-- Indeks untuk tabel `mobil`
--
ALTER TABLE `mobil`
  ADD PRIMARY KEY (`Nopol`);

--
-- Indeks untuk tabel `penyewaan`
--
ALTER TABLE `penyewaan`
  ADD PRIMARY KEY (`KTP`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
