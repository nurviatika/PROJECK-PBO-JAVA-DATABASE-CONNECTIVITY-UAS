
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiDAO {
    private final Connection conn;

    public TransaksiDAO(Connection conn) {
        this.conn = conn;
    }

    public void addTransaksi(Transaksi transaksi) throws SQLException {
        String sql = "INSERT INTO transaksi (ticket_id, customer_id, tanggalTransaksi, jumlahTiket, jumlahHarga) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaksi.getTicketId());
            stmt.setInt(2, transaksi.getCustomerId());
            stmt.setTimestamp(3, transaksi.getTanggalTransaksi());
            stmt.setInt(4, transaksi.getJumlahTiket());
            stmt.setBigDecimal(5, transaksi.getJumlahHarga());
            stmt.executeUpdate();
        }
    }

    public List<Transaksi> getAllTransaksi() throws SQLException {
        String sql = "SELECT * FROM transaksi";
        List<Transaksi> transaksiList = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                transaksiList.add(new Transaksi(
                        rs.getInt("transaksi_id"),
                        rs.getInt("ticket_id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("tanggalTransaksi"),
                        rs.getInt("jumlahTiket"),
                        rs.getBigDecimal("jumlahHarga")
                ));
            }
        }
        return transaksiList;
    }

    public boolean isTicketExists(int ticketId) throws SQLException {
        String sql = "SELECT 1 FROM ticket WHERE ticket_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean isCustomerExists(int customerId) throws SQLException {
        String sql = "SELECT 1 FROM customer WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public BigDecimal getTotalHargaTransaksi() throws SQLException {
        String sql = "SELECT SUM(jumlahHarga) AS totalHarga FROM transaksi";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getBigDecimal("totalHarga");
            } else {
                return BigDecimal.ZERO;
            }
        }
    }
}
