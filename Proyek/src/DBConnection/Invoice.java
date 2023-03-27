package DBConnection;

public class Invoice {
    Integer idInv;
    Integer idSup;
    String tglJatuhTempo;
    String tglTransaksi;
    Integer jumlah;
    Double total;
    Double ppn;
    Double grandTotal;

    public Invoice(Integer idInv, Integer idSup, String tglJatuhTempo, String tglTransaksi, Integer jumlah, Double total, Double ppn, Double grandTotal) {
        this.idInv = idInv;
        this.idSup = idSup;
        this.tglJatuhTempo = tglJatuhTempo;
        this.tglTransaksi = tglTransaksi;
        this.jumlah = jumlah;
        this.total = total;
        this.ppn = ppn;
        this.grandTotal = grandTotal;
    }

    public Integer getIdInv() {
        return idInv;
    }

    public void setIdInv(Integer idInv) {
        this.idInv = idInv;
    }

    public Integer getIdSup() {
        return idSup;
    }

    public void setIdSup(Integer idSup) {
        this.idSup = idSup;
    }

    public String getTglJatuhTempo() {
        return tglJatuhTempo;
    }

    public void setTglJatuhTempo(String tglJatuhTempo) {
        this.tglJatuhTempo = tglJatuhTempo;
    }

    public String getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(String tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPpn() {
        return ppn;
    }

    public void setPpn(Double ppn) {
        this.ppn = ppn;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }
}

