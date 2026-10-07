package com.pbo.fareru.minpro.pbo.view;

import com.pbo.fareru.minpro.pbo.model.Langganan;
import java.util.ArrayList;

/**

 * @author Muhammad Fahriel (2509116050)
 */
public class LanggananView {

    public void tampilkanMenuUtama() {
        System.out.println("\n================================================");
        System.out.println("         PERSONAL SUBSCRIPTION TRACKER          ");
        System.out.println("================================================");
        System.out.println("1. Tampilkan Daftar Langganan");
        System.out.println("2. Tambah Langganan Baru");
        System.out.println("3. Edit Data Langganan");
        System.out.println("4. Batalkan / Hapus Langganan");
        System.out.println("5. Ringkasan & Total Pengeluaran Bulanan");
        System.out.println("6. Simulasi Estimasi Biaya Langganan");
        System.out.println("7. Keluar");
        System.out.println("------------------------------------------------");
    }

    public void tampilkanHeader(String judul) {
        System.out.println("\n=== " + judul + " ===");
    }

    public void tampilkanGaris() {
        System.out.println("------------------------------------------------");
    }

    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }

    public void tampilkanPesanSukses(String pesan) {
        System.out.println("[SUKSES] " + pesan);
    }

    public void tampilkanPesanError(String pesan) {
        System.out.println("[ERROR] " + pesan);
    }

    public void tampilkanDaftarLangganan(ArrayList<Langganan> list) {
        if (list == null || list.isEmpty()) {
            tampilkanPesan("Belum ada data langganan yang tercatat.");
            return;
        }

        tampilkanHeader("DAFTAR SELURUH LANGGANAN");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("Data #" + (i + 1));
            list.get(i).tampilkanDetail();
            tampilkanGaris();
        }
    }

    public void tampilkanDaftarRingkas(ArrayList<Langganan> list) {
        if (list == null || list.isEmpty()) {
            tampilkanPesan("Belum ada data langganan yang tercatat.");
            return;
        }

        tampilkanHeader("RINGKASAN CEPAT LANGGANAN");
        System.out.printf("%-8s | %-15s | %-25s | %-16s | %-8s | %s%n",
                "ID", "Layanan", "Tipe Langganan", "Harga Bulanan", "Tanggal", "Status");
        tampilkanGaris();
        for (Langganan sub : list) {
            sub.tampilkanDetail(true);
        }
        tampilkanGaris();
    }

    public String formatRupiah(double nominal) {
        return "Rp " + String.format("%,.2f", nominal);
    }
}
