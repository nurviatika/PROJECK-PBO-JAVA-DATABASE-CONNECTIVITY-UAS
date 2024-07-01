

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaksi {
    private int transaksiId;
    private int ticketId;
    private int customerId;
    private Timestamp tanggalTransaksi;
    private int jumlahTiket;
    private BigDecimal jumlahHarga;

    public Transaksi(int transaksiId, int ticketId, int customerId, Timestamp tanggalTransaksi, int jumlahTiket, BigDecimal jumlahHarga) {
        this.transaksiId = transaksiId;
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.tanggalTransaksi = tanggalTransaksi;
        this.jumlahTiket = jumlahTiket;
        this.jumlahHarga = jumlahHarga;
    }

    // Getters and Setters
    public int getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(int transaksiId) {
        this.transaksiId = transaksiId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(Timestamp tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public BigDecimal getJumlahHarga() {
        return jumlahHarga;
    }

    public void setJumlahHarga(BigDecimal jumlahHarga) {
        this.jumlahHarga = jumlahHarga;
    }
}
