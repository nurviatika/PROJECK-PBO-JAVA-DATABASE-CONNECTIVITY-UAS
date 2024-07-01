
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {
    private final Connection conn;

    public TicketDAO(Connection conn) {
        this.conn = conn;
    }

    public void addTicket(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO ticket (film_id, showtime, seatNumber, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticket.getFilmId());
            stmt.setTimestamp(2, ticket.getShowtime());
            stmt.setString(3, ticket.getSeatNumber());
            stmt.setBigDecimal(4, ticket.getPrice());
            stmt.executeUpdate();
        }
    }

    public List<Ticket> getAllTickets() throws SQLException {
        String sql = "SELECT * FROM ticket";
        List<Ticket> tickets = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tickets.add(new Ticket(
                        rs.getInt("ticket_id"),
                        rs.getInt("film_id"),
                        rs.getTimestamp("showtime"),
                        rs.getString("seatNumber"),
                        rs.getBigDecimal("price")
                ));
            }
        }
        return tickets;
    }

    public BigDecimal getTicketPriceById(int ticketId) throws SQLException {
        String sql = "SELECT price FROM ticket WHERE ticket_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("price");
                } else {
                    return null;
                }
            }
        }
    }
}
