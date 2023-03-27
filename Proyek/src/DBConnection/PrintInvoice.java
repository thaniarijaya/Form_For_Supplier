package DBConnection;

public class PrintInvoice {
    Integer idInv;
    Integer idSup;
    Integer idProd;
    String namaProd;
    Integer jumlah;
    String tglTransaksi;
    String tglJatuhTempo;
    Double total;
    Double ppn;
    Double grandTotal;

    public PrintInvoice(Integer idInv, Integer idSup, Integer idProd, String namaProd, Integer jumlah, String tglTransaksi, String tglJatuhTempo, Double total, Double ppn, Double grandTotal) {
        this.idInv = idInv;
        this.idSup = idSup;
        this.idProd = idProd;
        this.namaProd = namaProd;
        this.jumlah = jumlah;
        this.tglTransaksi = tglTransaksi;
        this.tglJatuhTempo = tglJatuhTempo;
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

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public String getNamaProd() {
        return namaProd;
    }

    public void setNamaProd(String namaProd) {
        this.namaProd = namaProd;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public String getTglTransaksi() {
        return tglTransaksi;
    }

    public void setTglTransaksi(String tglTransaksi) {
        this.tglTransaksi = tglTransaksi;
    }

    public String getTglJatuhTempo() {
        return tglJatuhTempo;
    }

    public void setTglJatuhTempo(String tglJatuhTempo) {
        this.tglJatuhTempo = tglJatuhTempo;
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

