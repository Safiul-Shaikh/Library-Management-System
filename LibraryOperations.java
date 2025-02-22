package operations;

import java.sql.*;
import database.DBConnection;

public class LibraryOperations {

    // Add a new book
    public static void addBook(int bookId, String title, String author) {
        try (Connection con = DBConnection.getConnection()) {
            // First, check if the book already exists
            PreparedStatement checkStmt = con.prepareStatement("SELECT COUNT(*) FROM books WHERE book_id = ?");
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("❌ Book with ID " + bookId + " already exists!");
                return;
            }
    
            // If not exists, insert the new book
            PreparedStatement stmt = con.prepareStatement("INSERT INTO books VALUES (?, ?, ?, 1)");
            stmt.setInt(1, bookId);
            stmt.setString(2, title);
            stmt.setString(3, author);
            stmt.executeUpdate();
            System.out.println("✅ Book added successfully!");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    // View all books
    public static void viewBooks() {
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            System.out.println("\n📚 Available Books:");
            while (rs.next()) {
                System.out.println(rs.getInt("book_id") + " | " +
                                   rs.getString("title") + " | " +
                                   rs.getString("author") + " | " +
                                   (rs.getInt("available") == 1 ? "Available" : "Borrowed"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Search for a book by ID
    public static void searchBook(int bookId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM books WHERE book_id = ?")) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("\n🔍 Book Found:");
                System.out.println(rs.getInt("book_id") + " | " +
                                   rs.getString("title") + " | " +
                                   rs.getString("author") + " | " +
                                   (rs.getInt("available") == 1 ? "Available" : "Borrowed"));
            } else {
                System.out.println("❌ No book found with ID: " + bookId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Issue a book
    public static void issueBook(int bookId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement("UPDATE books SET available = 0 WHERE book_id = ? AND available = 1")) {

            stmt.setInt(1, bookId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("📖 Book issued successfully!");
            } else {
                System.out.println("❌ Book is either not available or does not exist.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Return a book
    public static void returnBook(int bookId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement("UPDATE books SET available = 1 WHERE book_id = ?")) {

            stmt.setInt(1, bookId);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Book returned successfully!");
            } else {
                System.out.println("❌ Invalid book ID or book was not issued.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
