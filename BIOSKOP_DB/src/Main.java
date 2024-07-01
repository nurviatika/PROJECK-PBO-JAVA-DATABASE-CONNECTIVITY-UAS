import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/bioskop";
        String user = "root";
        String password = "your_password";
        return DriverManager.getConnection(url, user, password);
    }

    private static void showMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah Customer");
        System.out.println("2. Tambah Film");
        System.out.println("3. Tambah Ticket");
        System.out.println("4. Tambah Transaksi");
        System.out.println("5. Lihat Semua Customer");
        System.out.println("6. Lihat Semua Film");
        System.out.println("7. Lihat Semua Ticket");
        System.out.println("8. Lihat Semua Transaksi");
        System.out.println("9. Lihat Total Harga Transaksi");
        System.out.println("10. Keluar");
        System.out.print("Pilih menu: ");
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            CustomerDAO customerDAO = new CustomerDAO(conn);
            FilmDAO filmDAO = new FilmDAO(conn);
            TicketDAO ticketDAO = new TicketDAO(conn);
            TransaksiDAO transaksiDAO = new TransaksiDAO(conn);

            Scanner scanner = new Scanner(System.in);
            while (true) {
                showMenu();
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Nama: ");
                        scanner.nextLine(); // consume newline
                        String name = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Phone: ");
                        String phone = scanner.nextLine();
                        Customer customer = new Customer(0, name, email, phone);
                        customerDAO.addCustomer(customer);
                        System.out.println("Customer ditambahkan.");
                        break;

                    case 2:
                        System.out.print("Title: ");
                        scanner.nextLine(); // consume newline
                        String title = scanner.nextLine();
                        System.out.print("Genre: ");
                        String genre = scanner.nextLine();
                        System.out.print("Duration (minutes): ");
                        int duration = scanner.nextInt();
                        Film film = new Film(0, title, genre, duration);
                        filmDAO.addFilm(film);
                        System.out.println("Film ditambahkan.");
                        break;

                    case 3:
                        System.out.print("ID Film: ");
                        int ticketFilmId = scanner.nextInt();
                        System.out.print("Showtime (yyyy-mm-dd hh:mm:ss): ");
                        scanner.nextLine(); // consume newline
                        String showtimeStr = scanner.nextLine();
                        Timestamp showtime = Timestamp.valueOf(showtimeStr);
                        System.out.print("Nomor Kursi: ");
                        String seatNumber = scanner.nextLine();

                        // Tentukan harga tiket berdasarkan genre
                        String filmGenre = filmDAO.getGenreById(ticketFilmId);
                        BigDecimal ticketPrice;
                        if (filmGenre != null) {
                            switch (filmGenre.toLowerCase()) {
                                case "action":
                                    ticketPrice = new BigDecimal("50.00");
                                    break;
                                case "fantasy":
                                    ticketPrice = new BigDecimal("40.00");
                                    break;
                                case "romance":
                                    ticketPrice = new BigDecimal("30.00");
                                    break;
                                default:
                                    ticketPrice = new BigDecimal("35.00");
                                    break;
                            }
                        } else {
                            System.out.println("Film genre tidak ditemukan.");
                            break;
                        }

                        Ticket ticket = new Ticket(0, ticketFilmId, showtime, seatNumber, ticketPrice);
                        ticketDAO.addTicket(ticket);
                        System.out.println("Ticket ditambahkan.");
                        break;

                    case 4:
                        System.out.print("ID Ticket: ");
                        int transaksiTicketId = scanner.nextInt();
                        System.out.print("ID Customer: ");
                        int transaksiCustomerId = scanner.nextInt();
                        System.out.print("Jumlah Tiket: ");
                        int jumlahTiket = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        // Periksa apakah ticket dan customer ada
                        if (!transaksiDAO.isTicketExists(transaksiTicketId)) {
                            System.out.println("Ticket dengan ID tersebut tidak ditemukan.");
                            break;
                        }
                        if (!transaksiDAO.isCustomerExists(transaksiCustomerId)) {
                            System.out.println("Customer dengan ID tersebut tidak ditemukan.");
                            break;
                        }

                        // Hitung jumlah harga
                        BigDecimal hargaPerTicket = ticketDAO.getTicketPriceById(transaksiTicketId);
                        if (hargaPerTicket == null) {
                            System.out.println("Harga tiket tidak ditemukan.");
                            break;
                        }
                        BigDecimal jumlahHarga = hargaPerTicket.multiply(BigDecimal.valueOf(jumlahTiket));

                        Transaksi transaksi = new Transaksi(0, transaksiTicketId, transaksiCustomerId, new Timestamp(new Date().getTime()), jumlahTiket, jumlahHarga);
                        transaksiDAO.addTransaksi(transaksi);
                        System.out.println("Transaksi ditambahkan.");
                        break;

                    case 5:
                        List<Customer> customers = customerDAO.getAllCustomers();
                        for (Customer c : customers) {
                            System.out.println(c.getCustomerId() + ": " + c.getName() + ", " + c.getEmail() + ", " + c.getPhone());
                        }
                        break;

                    case 6:
                        List<Film> films = filmDAO.getAllFilms();
                        for (Film f : films) {
                            System.out.println(f.getFilmId() + ": " + f.getTitle() + ", " + f.getGenre() + ", " + f.getDuration() + " minutes");
                        }
                        break;

                    case 7:
                        List<Ticket> tickets = ticketDAO.getAllTickets();
                        for (Ticket t : tickets) {
                            System.out.println(t.getTicketId() + ": " + t.getFilmId() + ", " + t.getShowtime() + ", " + t.getSeatNumber() + ", " + t.getPrice());
                        }
                        break;

                    case 8:
                        List<Transaksi> transaksiList = transaksiDAO.getAllTransaksi();
                        for (Transaksi t : transaksiList) {
                            System.out.println(t.getTransaksiId() + ": " + t.getTicketId() + ", " + t.getCustomerId() + ", " + t.getTanggalTransaksi() + ", " + t.getJumlahTiket() + ", " + t.getJumlahHarga());
                        }
                        break;

                    case 9:
                        BigDecimal totalHarga = transaksiDAO.getTotalHargaTransaksi();
                        System.out.println("Total Harga Transaksi: " + totalHarga);
                        break;

                    case 10:
                        System.out.println("Keluar dari program.");
                        return;

                    default:
                        System.out.println("Pilihan tidak valid.");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
