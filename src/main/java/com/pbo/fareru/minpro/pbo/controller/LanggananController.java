package com.pbo.fareru.minpro.pbo.controller;

import com.pbo.fareru.minpro.pbo.model.DapatDiskon;
import com.pbo.fareru.minpro.pbo.model.Langganan;
import com.pbo.fareru.minpro.pbo.model.LanggananProduktivitas;
import com.pbo.fareru.minpro.pbo.model.LanggananStreaming;
import com.pbo.fareru.minpro.pbo.model.Layanan;
import com.pbo.fareru.minpro.pbo.model.MetodePembayaran;
import com.pbo.fareru.minpro.pbo.view.LanggananView;

import java.util.ArrayList;
import java.util.Scanner;

public class LanggananController {
    private ArrayList<Langganan> listLangganan;
    private ArrayList<Layanan> listLayanan;
    private ArrayList<MetodePembayaran> listMetode;
    private LanggananView view;
    private Scanner scanner;

    public LanggananController(LanggananView view) {
        this.view = view;
        this.scanner = new Scanner(System.in);
        this.listLangganan = new ArrayList<>();
        this.listLayanan = new ArrayList<>();
        this.listMetode = new ArrayList<>();
        inisialisasiDummyData();
    }

    private void inisialisasiDummyData() {
        Layanan netflix = new Layanan("LYN01", "Netflix", "Hiburan Film & Serial");
        Layanan spotify = new Layanan("LYN02", "Spotify", "Streaming Musik & Audio");
        Layanan googleOne = new Layanan("LYN03", "Google One", "Cloud Storage & Backup");
        Layanan ms365 = new Layanan("LYN04", "Microsoft 365", "Office & Produktivitas");

        listLayanan.add(netflix);
        listLayanan.add(spotify);
        listLayanan.add(googleOne);
        listLayanan.add(ms365);

        MetodePembayaran gopay = new MetodePembayaran("PAY01", "GoPay", "E-Wallet");
        MetodePembayaran mandiriCC = new MetodePembayaran("PAY02", "Kartu Kredit Mandiri", "Credit Card");
        MetodePembayaran bcaVA = new MetodePembayaran("PAY03", "BCA Virtual Account", "Bank Transfer");

        listMetode.add(gopay);
        listMetode.add(mandiriCC);
        listMetode.add(bcaVA);

        listLangganan.add(new LanggananStreaming(
                "SUB01", netflix, 186000.0, mandiriCC, 15, "4K UHD + HDR", 4));
        listLangganan.add(new LanggananStreaming(
                "SUB02", spotify, 54990.0, gopay, 20, "Very High Quality (320 kbps)", 1));
        listLangganan.add(new LanggananProduktivitas(
                "SUB03", googleOne, 43000.0, gopay, 5, "2 TB Google Drive & Photos", 5));
        listLangganan.add(new LanggananProduktivitas(
                "SUB04", ms365, 95990.0, bcaVA, 28, "1 TB OneDrive + Office Apps", 1));
    }

    public void jalankanAplikasi() {
        boolean berjalan = true;
        while (berjalan) {
            view.tampilkanMenuUtama();
            int pilihan = InputValidator.bacaInt(scanner, "Pilih Menu (1-7): ", 1, 7);

            switch (pilihan) {
                case 1:
                    menuTampilkanLangganan();
                    break;
                case 2:
                    menuTambahLangganan();
                    break;
                case 3:
                    menuEditLangganan();
                    break;
                case 4:
                    menuHapusLangganan();
                    break;
                case 5:
                    menuRingkasanPengeluaran();
                    break;
                case 6:
                    menuSimulasiEstimasi();
                    break;
                case 7:
                    berjalan = false;
                    view.tampilkanPesan("\nTerima kasih telah menggunakan Personal Subscription Tracker. Sampai jumpa!");
                    break;
                default:
                    view.tampilkanPesanError("Pilihan tidak valid!");
            }
        }
        scanner.close();
    }

    private void menuTampilkanLangganan() {
        if (listLangganan.isEmpty()) {
            view.tampilkanPesan("Belum ada data langganan yang tercatat.");
            InputValidator.tekanEnter(scanner);
            return;
        }

        view.tampilkanHeader("FORMAT PENAMPILAN DATA");
        System.out.println("1. Tampilkan Detail Lengkap (Kartu)");
        System.out.println("2. Tampilkan Format Ringkas (Tabel)");
        int format = InputValidator.bacaInt(scanner, "Pilih format tampilan (1-2): ", 1, 2);

        if (format == 1) {
            view.tampilkanDaftarLangganan(listLangganan);
        } else {
            view.tampilkanDaftarRingkas(listLangganan);
        }

        InputValidator.tekanEnter(scanner);
    }

    private void menuTambahLangganan() {
        view.tampilkanHeader("TAMBAH LANGGANAN BARU");

        String idBaru = generateIdOtomatis();
        System.out.println("Info: ID otomatis untuk data baru adalah " + idBaru);

        view.tampilkanHeader("PILIH TIPE LANGGANAN");
        System.out.println("1. Langganan Streaming (Film / Musik / Hiburan)");
        System.out.println("2. Langganan Produktivitas & Cloud SaaS");
        int tipe = InputValidator.bacaInt(scanner, "Pilih Tipe (1-2): ", 1, 2);

        Layanan layananTerpilih = tentukanLayanan();
        MetodePembayaran metodeTerpilih = tentukanMetodePembayaran();

        double harga = InputValidator.bacaDouble(scanner, "Masukkan Harga Bulanan (Rp): ", 0.0);
        int tglTagihan = InputValidator.bacaInt(scanner, "Masukkan Tanggal Tagihan Bulanan (1-31): ", 1, 31);

        Langganan langgananBaru;
        if (tipe == 1) {
            String resolusi = InputValidator.bacaString(scanner, "Masukkan Kualitas Streaming/Audio (contoh: 4K UHD / Hi-Fi): ");
            int batasLayar = InputValidator.bacaInt(scanner, "Masukkan Batas Layar/Perangkat Simultan: ", 1, 100);
            langgananBaru = new LanggananStreaming(idBaru, layananTerpilih, harga, metodeTerpilih, tglTagihan, resolusi, batasLayar);
        } else {
            String storage = InputValidator.bacaString(scanner, "Masukkan Kapasitas Storage/Cloud (contoh: 1 TB / Unlimited): ");
            int lisensi = InputValidator.bacaInt(scanner, "Masukkan Jumlah Lisensi User: ", 1, 1000);
            langgananBaru = new LanggananProduktivitas(idBaru, layananTerpilih, harga, metodeTerpilih, tglTagihan, storage, lisensi);
        }

        listLangganan.add(langgananBaru);
        view.tampilkanPesanSukses("Data langganan baru dengan ID '" + idBaru + "' berhasil disimpan!");
        InputValidator.tekanEnter(scanner);
    }

    private Layanan tentukanLayanan() {
        view.tampilkanHeader("PILIH LAYANAN");
        for (int i = 0; i < listLayanan.size(); i++) {
            Layanan l = listLayanan.get(i);
            System.out.printf("%d. %s (%s)%n", (i + 1), l.getNamaLayanan(), l.getKategori());
        }
        System.out.printf("%d. [Daftarkan Layanan Baru]%n", (listLayanan.size() + 1));

        int pilihan = InputValidator.bacaInt(scanner, "Pilihan Layanan (1-" + (listLayanan.size() + 1) + "): ", 1, listLayanan.size() + 1);

        if (pilihan <= listLayanan.size()) {
            return listLayanan.get(pilihan - 1);
        }

        String idLayanan;
        while (true) {
            idLayanan = InputValidator.bacaString(scanner, "Masukkan ID Layanan Baru: (Contoh: LYN04");
            boolean duplikat = false;
            for (Layanan lyn : listLayanan) {
                if (lyn.getIdLayanan().equalsIgnoreCase(idLayanan)) {
                    duplikat = true;
                    break;
                }
            }
            if (!duplikat) {
                break;
            }
            view.tampilkanPesanError("ID Layanan sudah ada! Masukkan ID lain.");
        }

        String namaLayanan = InputValidator.bacaString(scanner, "Masukkan Nama Layanan Baru: ");
        String kategori = InputValidator.bacaString(scanner, "Masukkan Kategori Layanan: ");

        Layanan layananBaru = new Layanan(idLayanan, namaLayanan, kategori);
        listLayanan.add(layananBaru);
        view.tampilkanPesanSukses("Layanan '" + namaLayanan + "' berhasil ditambahkan!");
        return layananBaru;
    }

    private MetodePembayaran tentukanMetodePembayaran() {
        view.tampilkanHeader("PILIH METODE PEMBAYARAN");
        for (int i = 0; i < listMetode.size(); i++) {
            MetodePembayaran m = listMetode.get(i);
            System.out.printf("%d. %s [%s]%n", (i + 1), m.getNamaMetode(), m.getJenis());
        }
        System.out.printf("%d. [Daftarkan Metode Pembayaran Baru]%n", (listMetode.size() + 1));

        int pilihan = InputValidator.bacaInt(scanner, "Pilihan Metode (1-" + (listMetode.size() + 1) + "): ", 1, listMetode.size() + 1);

        if (pilihan <= listMetode.size()) {
            return listMetode.get(pilihan - 1);
        }

        String idMetode;
        while (true) {
            idMetode = InputValidator.bacaString(scanner, "Masukkan ID Metode Baru: ");
            boolean duplikat = false;
            for (MetodePembayaran mp : listMetode) {
                if (mp.getIdMetode().equalsIgnoreCase(idMetode)) {
                    duplikat = true;
                    break;
                }
            }
            if (!duplikat) {
                break;
            }
            view.tampilkanPesanError("ID Metode sudah ada! Masukkan ID lain.");
        }

        String namaMetode = InputValidator.bacaString(scanner, "Masukkan Nama Metode Pembayaran: ");
        String jenis = InputValidator.bacaString(scanner, "Masukkan Jenis Metode (E-Wallet/Credit Card/Bank Transfer/dsb): ");

        MetodePembayaran metodeBaru = new MetodePembayaran(idMetode, namaMetode, jenis);
        listMetode.add(metodeBaru);
        view.tampilkanPesanSukses("Metode '" + namaMetode + "' berhasil didaftarkan!");
        return metodeBaru;
    }

    private void menuEditLangganan() {
        if (listLangganan.isEmpty()) {
            view.tampilkanPesan("Belum ada data langganan yang tercatat.");
            InputValidator.tekanEnter(scanner);
            return;
        }

        view.tampilkanHeader("EDIT DATA LANGGANAN");
        String idCari = InputValidator.bacaString(scanner, "Masukkan ID Subscription yang ingin diedit: ");
        Langganan langganan = cariLanggananById(idCari);

        if (langganan == null) {
            view.tampilkanPesanError("Data langganan dengan ID '" + idCari + "' tidak ditemukan!");
            InputValidator.tekanEnter(scanner);
            return;
        }

        view.tampilkanHeader("DATA SAAT INI");
        langganan.tampilkanDetail();
        view.tampilkanGaris();

        System.out.println("Pilihan Edit:");
        System.out.println("1. Ubah Harga Bulanan");
        System.out.println("2. Ubah Tanggal Tagihan");
        System.out.println("3. Ubah Status (Aktif / Nonaktif)");
        System.out.println("4. Ubah Metode Pembayaran");
        System.out.println("5. Ubah Atribut Spesifik Kategori");
        System.out.println("6. Batal");

        int pilihan = InputValidator.bacaInt(scanner, "Pilih Aksi (1-6): ", 1, 6);

        switch (pilihan) {
            case 1:
                double hargaBaru = InputValidator.bacaDouble(scanner, "Masukkan Harga Bulanan Baru (Rp): ", 0.0);
                langganan.setHargaBulanan(hargaBaru);
                view.tampilkanPesanSukses("Harga bulanan berhasil diperbarui!");
                break;

            case 2:
                int tglBaru = InputValidator.bacaInt(scanner, "Masukkan Tanggal Tagihan Baru (1-31): ", 1, 31);
                langganan.setTanggalTagihan(tglBaru);
                view.tampilkanPesanSukses("Tanggal tagihan berhasil diperbarui!");
                break;

            case 3:
                System.out.println("1. Aktif");
                System.out.println("2. Nonaktif");
                int statusPil = InputValidator.bacaInt(scanner, "Pilih status baru (1-2): ", 1, 2);
                langganan.setStatus(statusPil == 1 ? "Aktif" : "Nonaktif");
                view.tampilkanPesanSukses("Status berhasil diubah menjadi " + langganan.getStatus() + "!");
                break;

            case 4:
                view.tampilkanHeader("PILIH METODE PEMBAYARAN BARU");
                MetodePembayaran metodeBaru = tentukanMetodePembayaran();
                langganan.setMetode(metodeBaru);
                view.tampilkanPesanSukses("Metode pembayaran berhasil diubah menjadi " + metodeBaru.getNamaMetode() + "!");
                break;

            case 5:
                if (langganan instanceof LanggananStreaming) {
                    LanggananStreaming streaming = (LanggananStreaming) langganan;
                    System.out.println("1. Ubah Kualitas Streaming (" + streaming.getKualitasResolusi() + ")");
                    System.out.println("2. Ubah Batas Layar (" + streaming.getBatasLayar() + ")");
                    int subPil = InputValidator.bacaInt(scanner, "Pilih (1-2): ", 1, 2);
                    if (subPil == 1) {
                        String res = InputValidator.bacaString(scanner, "Masukkan Kualitas Streaming Baru: ");
                        streaming.setKualitasResolusi(res);
                        view.tampilkanPesanSukses("Kualitas streaming berhasil diperbarui!");
                    } else {
                        int layar = InputValidator.bacaInt(scanner, "Masukkan Batas Layar Baru: ", 1, 100);
                        streaming.setBatasLayar(layar);
                        view.tampilkanPesanSukses("Batas layar berhasil diperbarui!");
                    }
                } else if (langganan instanceof LanggananProduktivitas) {
                    LanggananProduktivitas prod = (LanggananProduktivitas) langganan;
                    System.out.println("1. Ubah Kapasitas Storage (" + prod.getKapasitasStorage() + ")");
                    System.out.println("2. Ubah Lisensi User (" + prod.getLisensiUser() + ")");
                    int subPil = InputValidator.bacaInt(scanner, "Pilih (1-2): ", 1, 2);
                    if (subPil == 1) {
                        String storage = InputValidator.bacaString(scanner, "Masukkan Kapasitas Storage Baru: ");
                        prod.setKapasitasStorage(storage);
                        view.tampilkanPesanSukses("Kapasitas storage berhasil diperbarui!");
                    } else {
                        int lisensi = InputValidator.bacaInt(scanner, "Masukkan Jumlah Lisensi Baru: ", 1, 1000);
                        prod.setLisensiUser(lisensi);
                        view.tampilkanPesanSukses("Jumlah lisensi user berhasil diperbarui!");
                    }
                } else {
                    view.tampilkanPesan("Tidak ada atribut spesifik tambahan untuk tipe langganan umum.");
                }
                break;

            case 6:
                view.tampilkanPesan("Perubahan dibatalkan.");
                break;
        }

        InputValidator.tekanEnter(scanner);
    }

    private void menuHapusLangganan() {
        if (listLangganan.isEmpty()) {
            view.tampilkanPesan("Belum ada data langganan yang tercatat.");
            InputValidator.tekanEnter(scanner);
            return;
        }

        view.tampilkanHeader("HAPUS / BATALKAN LANGGANAN");
        String idHapus = InputValidator.bacaString(scanner, "Masukkan ID Subscription yang ingin dihapus: ");
        Langganan langganan = cariLanggananById(idHapus);

        if (langganan == null) {
            view.tampilkanPesanError("Data langganan dengan ID '" + idHapus + "' tidak ditemukan!");
            InputValidator.tekanEnter(scanner);
            return;
        }

        view.tampilkanHeader("DETAIL DATA YANG AKAN DIHAPUS");
        langganan.tampilkanDetail();
        view.tampilkanGaris();

        boolean konfirmasi = InputValidator.bacaKonfirmasi(scanner, "Apakah Anda yakin ingin menghapus langganan ini?");
        if (konfirmasi) {
            listLangganan.remove(langganan);
            view.tampilkanPesanSukses("Langganan dengan ID '" + idHapus + "' berhasil dihapus dari sistem!");
        } else {
            view.tampilkanPesan("Penghapusan langganan dibatalkan.");
        }

        InputValidator.tekanEnter(scanner);
    }

    private void menuRingkasanPengeluaran() {
        if (listLangganan.isEmpty()) {
            view.tampilkanPesan("Belum ada data langganan yang tercatat.");
            InputValidator.tekanEnter(scanner);
            return;
        }

        view.tampilkanHeader("RINGKASAN & TOTAL PENGELUARAN BULANAN");

        double totalSemua = 0;
        double totalStreaming = 0;
        double totalProduktivitas = 0;
        int jumlahAktif = 0;
        int jumlahStreaming = 0;
        int jumlahProduktivitas = 0;

        for (Langganan sub : listLangganan) {
            if (sub.getStatus().equalsIgnoreCase("Aktif")) {
                totalSemua += sub.getHargaBulanan();
                jumlahAktif++;

                if (sub instanceof LanggananStreaming) {
                    totalStreaming += sub.getHargaBulanan();
                    jumlahStreaming++;
                } else if (sub instanceof LanggananProduktivitas) {
                    totalProduktivitas += sub.getHargaBulanan();
                    jumlahProduktivitas++;
                }
            }
        }

        System.out.println("Jumlah Langganan Aktif : " + jumlahAktif + " layanan");
        System.out.println("- Kategori Streaming   : " + jumlahStreaming + " layanan (" + view.formatRupiah(totalStreaming) + ")");
        System.out.println("- Kategori Produktif   : " + jumlahProduktivitas + " layanan (" + view.formatRupiah(totalProduktivitas) + ")");
        view.tampilkanGaris();
        System.out.println("TOTAL PENGELUARAN/BULAN: " + view.formatRupiah(totalSemua));
        view.tampilkanGaris();

        if (totalSemua > 500000) {
            view.tampilkanPesan("PERHATIAN: Total pengeluaran langganan Anda melebihi Rp 500.000! Evaluasi layanan yang jarang Anda pakai.");
        } else {
            view.tampilkanPesan("STATUS AMAN: Pengeluaran langganan Anda masih dalam batas hemat.");
        }

        InputValidator.tekanEnter(scanner);
    }

    private void menuSimulasiEstimasi() {
        if (listLangganan.isEmpty()) {
            view.tampilkanPesan("Belum ada data langganan yang tercatat.");
            InputValidator.tekanEnter(scanner);
            return;
        }

        view.tampilkanHeader("SIMULASI ESTIMASI BIAYA (POLYMORPHISM)");
        int durasiBulan = InputValidator.bacaInt(scanner, "Masukkan proyeksi durasi langganan (jumlah bulan): ", 1, 60);

        boolean pakaiDiskon = InputValidator.bacaKonfirmasi(scanner, "Apakah ingin menerapkan diskon voucher tambahan (overloading)?");
        double diskon = 0.0;
        if (pakaiDiskon) {
            diskon = InputValidator.bacaDouble(scanner, "Masukkan persentase diskon tambahan (0-100%): ", 0.0);
            if (diskon > 100.0) {
                diskon = 100.0;
            }
        }

        view.tampilkanHeader("HASIL PROYEKSI BIAYA UNTUK " + durasiBulan + " BULAN");
        System.out.printf("%-8s | %-15s | %-25s | %-18s | %s%n",
                "ID", "Layanan", "Tipe Langganan", "Tarif/Bulan", "Estimasi Total");
        view.tampilkanGaris();

        double grandTotal = 0;
        for (Langganan sub : listLangganan) {
            if (!sub.getStatus().equalsIgnoreCase("Aktif")) {
                continue;
            }
            double estimasi;
            if (pakaiDiskon) {
                estimasi = sub.hitungEstimasiBiaya(durasiBulan, diskon);
            } else {
                estimasi = sub.hitungEstimasiBiaya(durasiBulan);
            }
            grandTotal += estimasi;

            System.out.printf("%-8s | %-15s | %-25s | %-18s | %s%n",
                    sub.getIdSubscription(),
                    sub.getLayanan().getNamaLayanan(),
                    sub.getTipeLangganan(),
                    view.formatRupiah(sub.getHargaBulanan()),
                    view.formatRupiah(estimasi));
        }

        view.tampilkanGaris();
        System.out.println("TOTAL ESTIMASI BIAYA AKUMULATIF: " + view.formatRupiah(grandTotal));
        view.tampilkanHeader("PROYEKSI TAHUNAN (ABSTRACT METHOD + INTERFACE)");
        double totalTahunan = 0, totalDiskonTahunan = 0;
        for (Langganan sub : listLangganan) {
        if (!sub.getStatus().equalsIgnoreCase("Aktif")) continue;
        double tahunan = sub.hitungBiayaTahunan(); // abstract method: polimorfik
        double diskon = 0;
        if (sub instanceof DapatDidiskon) {
            diskon = ((DapatDidiskon) sub).hitungDiskon(12); // interface
        }
            totalTahunan += tahunan;
            totalDiskonTahunan += diskon;
            System.out.printf("%-8s | %-15s | Tahunan: %-18s | Diskon: %s%n",
            sub.getIdSubscription(), sub.getLayanan().getNamaLayanan(),
            view.formatRupiah(tahunan), view.formatRupiah(diskon));
        }
            view.tampilkanGaris();
            System.out.println("TOTAL TAHUNAN : " + view.formatRupiah(totalTahunan));
            System.out.println("TOTAL DISKON  : " + view.formatRupiah(totalDiskonTahunan));
        }
        InputValidator.tekanEnter(scanner);
 
    }

    private String generateIdOtomatis() {
    int max = 0;
    for (Langganan sub : listLangganan) {
        String id = sub.getIdSubscription();
        if (id != null && id.startsWith("SUB")) {
            try {
                int num = Integer.parseInt(id.substring(3));
                if (num > max) max = num;
            } catch (NumberFormatException e) {}
        }
    }
    return String.format("SUB%02d", max + 1);
}
    private Langganan cariLanggananById(String id) {
        if (id == null) {
            return null;
        }
        for (Langganan sub : listLangganan) {
            if (sub.getIdSubscription().equalsIgnoreCase(id.trim())) {
                return sub;
            }
        }
        return null;
    }
}
