

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    private Connection conn;

    public CustomerDAO(Connection conn) {
        this.conn = conn;
    }

    public void addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (name, email, phone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.executeUpdate();
        }
    }

    public Customer findCustomerById(int customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
            } else {
                return null;
            }
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM customer";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                );
                customers.add(customer);
            }
            return customers;
        }
    }
}
