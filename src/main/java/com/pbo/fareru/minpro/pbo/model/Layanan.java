package com.pbo.fareru.minpro.pbo.model;

/**

 * @author Muhammad Fahriel (2509116050)
 */
public class Layanan {
    private String idLayanan;
    private String namaLayanan;
    private String kategori;

    public Layanan(String idLayanan, String namaLayanan, String kategori) {
        setIdLayanan(idLayanan);
        setNamaLayanan(namaLayanan);
        setKategori(kategori);
    }

    public String getIdLayanan() {
        return idLayanan;
    }

    public void setIdLayanan(String idLayanan) {
        if (idLayanan != null && !idLayanan.trim().isEmpty()) {
            this.idLayanan = idLayanan;
        }
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        if (namaLayanan != null && !namaLayanan.trim().isEmpty()) {
            this.namaLayanan = namaLayanan;
        }
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        if (kategori != null && !kategori.trim().isEmpty()) {
            this.kategori = kategori;
        }
    }
}