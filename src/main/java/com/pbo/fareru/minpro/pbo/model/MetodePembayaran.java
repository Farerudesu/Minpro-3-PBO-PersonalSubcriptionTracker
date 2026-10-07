package com.pbo.fareru.minpro.pbo.model;

/**

 * @author Muhammad Fahriel (2509116050)
 */
public class MetodePembayaran {
    private String idMetode;
    private String namaMetode;
    private String jenis;

    public MetodePembayaran(String idMetode, String namaMetode, String jenis) {
        setIdMetode(idMetode);
        setNamaMetode(namaMetode);
        setJenis(jenis);
    }

    public String getIdMetode() {
        return idMetode;
    }

    public void setIdMetode(String idMetode) {
        if (idMetode != null && !idMetode.trim().isEmpty()) {
            this.idMetode = idMetode;
        }
    }

    public String getNamaMetode() {
        return namaMetode;
    }

    public void setNamaMetode(String namaMetode) {
        if (namaMetode != null && !namaMetode.trim().isEmpty()) {
            this.namaMetode = namaMetode;
        }
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        if (jenis != null && !jenis.trim().isEmpty()) {
            this.jenis = jenis;
        }
    }
}
