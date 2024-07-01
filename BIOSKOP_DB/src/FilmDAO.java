
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    private final Connection conn;

    public FilmDAO(Connection conn) {
        this.conn = conn;
    }

    public void addFilm(Film film) throws SQLException {
        String sql = "INSERT INTO film (title, genre, duration) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre());
            stmt.setInt(3, film.getDuration());
            stmt.executeUpdate();
        }
    }

    public String getGenreById(int filmId) throws SQLException {
        String sql = "SELECT genre FROM film WHERE film_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("genre");
                } else {
                    return null;
                }
            }
        }
    }

    public List<Film> getAllFilms() throws SQLException {
        List<Film> films = new ArrayList<>();
        String sql = "SELECT * FROM film";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Film film = new Film(
                        rs.getInt("film_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("duration")
                );
                films.add(film);
            }
        }
        return films;
    }

    public Film getFilmById(int filmId) throws SQLException {
        String sql = "SELECT * FROM film WHERE film_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Film(
                            rs.getInt("film_id"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getInt("duration")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public void updateFilm(Film film) throws SQLException {
        String sql = "UPDATE film SET title = ?, genre = ?, duration = ? WHERE film_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, film.getTitle());
            stmt.setString(2, film.getGenre());
            stmt.setInt(3, film.getDuration());
            stmt.setInt(4, film.getFilmId());
            stmt.executeUpdate();
        }
    }

    public void deleteFilm(int filmId) throws SQLException {
        String sql = "DELETE FROM film WHERE film_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, filmId);
            stmt.executeUpdate();
        }
    }
}
