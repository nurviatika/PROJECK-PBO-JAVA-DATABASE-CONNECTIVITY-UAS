
public class Film {
    private int filmId;
    private String title;
    private String genre;
    private int duration;

    public Film(int filmId, String title, String genre, int duration) {
        this.filmId = filmId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
    }

    // Getters and Setters
    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
