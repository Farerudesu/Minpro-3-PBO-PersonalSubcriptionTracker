package com.pbo.fareru.minpro.pbo.model;

public class LanggananProduktivitas extends Langganan implements DapatDiDdskon{
    @Override
    public double hitungDiskon(int bulan) {
        if (bulan >= BATAS_BULAN_DISKON) {
            return getHargaBulanan()* BATAS_BULAN_DISKON * 0.10;
        }
        return 0;
    }
    private String kapasitasStorage;
    private int lisensiUser;

    public LanggananProduktivitas(String idSubscription, Layanan layanan, double hargaBulanan,
                                  MetodePembayaran metode, int tanggalTagihan,
                                  String kapasitasStorage, int lisensiUser) {
        super(idSubscription, layanan, hargaBulanan, metode, tanggalTagihan);
        setKapasitasStorage(kapasitasStorage);
        setLisensiUser(lisensiUser);
    }

    public String getKapasitasStorage() {
        return kapasitasStorage;
    }

    public void setKapasitasStorage(String kapasitasStorage) {
        if (kapasitasStorage != null && !kapasitasStorage.trim().isEmpty()) {
            this.kapasitasStorage = kapasitasStorage;
        }
    }

    public int getLisensiUser() {
        return lisensiUser;
    }

    public void setLisensiUser(int lisensiUser) {
        if (lisensiUser >= 1) {
            this.lisensiUser = lisensiUser;
        } else {
            this.lisensiUser = 1;
        }
    }

    @Override
    public String getTipeLangganan() {
        return "Produktivitas & Cloud SaaS";
    }

    @Override
    public void tampilkanDetail() {
        super.tampilkanDetail();
        System.out.println("Storage      : " + kapasitasStorage);
        System.out.println("Lisensi User : " + lisensiUser + " Akun Pengguna");
    }

    @Override
    public double hitungEstimasiBiaya(int bulan) {
        if (bulan <= 0) {
            return 0;
        }
        double total = getHargaBulanan() * bulan;
        if (bulan >= 12) {
            return total * 0.90;
        }
        return total;
    }
    @Override
    public double hitungBiayaTahunan(){
        return getHargaBulanan() * BATAS_BULAN_DISKON * 0.90;
    }
}
