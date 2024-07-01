

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Ticket {
    private int ticketId;
    private int filmId;
    private Timestamp showtime;
    private String seatNumber;
    private BigDecimal price;

    public Ticket(int ticketId, int filmId, Timestamp showtime, String seatNumber, BigDecimal price) {
        this.ticketId = ticketId;
        this.filmId = filmId;
        this.showtime = showtime;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    // Getters and Setters
    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public Timestamp getShowtime() {
        return showtime;
    }

    public void setShowtime(Timestamp showtime) {
        this.showtime = showtime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
