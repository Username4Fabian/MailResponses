package htlle.mailresponse.Database;

import htlle.mailresponse.Mail.Email;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmailDatabase {

    private static final String DB_URL = "jdbc:h2:tcp://localhost/~/h2_db/MailResponse;AUTO_SERVER=TRUE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    private final Connection connection;

    public static void main(String[] args) {
        try {
            // Initialize the database
            // test comment
            EmailDatabase db = new EmailDatabase();

            // Insert a few emails
            db.insertEmail("receiver@example.com", "sender@example.com", "Hello, Schasch 69 noice!", "This is the content of the first email.");
            db.insertEmail("anotherReceiver@example.com", "anotherSender@example.com", "Another Email", "This is another email's content.");

            // Retrieve and print all emails
            List<Email> emails = db.getAllEmails();
            for (Email email : emails) {
                System.out.println("ID: " + email.getId());
                System.out.println("Receiver: " + email.getReceiver());
                System.out.println("Sender: " + email.getSender());
                System.out.println("Subject: " + email.getSubject());
                System.out.println("Content: " + email.getContent());
                System.out.println("Timestamp: " + email.getTimestamp());
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public EmailDatabase() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        initDatabase();
    }

    private void initDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS emails (id INT AUTO_INCREMENT PRIMARY KEY, receiver VARCHAR(255), sender VARCHAR(255), subject VARCHAR(255), content TEXT, timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
        }
    }

    public void insertResponse(String receiver, String sender, String subject, String content) throws SQLException {
        insertEmail(receiver, sender, subject, content);
    }

    public void insertEmail(String receiver, String sender, String subject, String content) throws SQLException {
        String sql = "INSERT INTO emails (receiver, sender, subject, content) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, receiver);
            pstmt.setString(2, sender);
            pstmt.setString(3, subject);
            pstmt.setString(4, content);
            pstmt.executeUpdate();
        }
    }

    public List<Email> getAllEmails() throws SQLException {
        List<Email> emails = new ArrayList<>();
        String sql = "SELECT * FROM emails";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Email email = new Email(
                        rs.getLong("id"),
                        rs.getString("receiver"),
                        rs.getString("sender"),
                        rs.getString("subject"),
                        rs.getString("content"),
                        rs.getTimestamp("timestamp")
                );
                emails.add(email);
            }
        }
        return emails;
    }

}
