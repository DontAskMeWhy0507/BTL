package org.example.demo6.Classes;

import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBUltis implements Database{

    // đăng ký
    public boolean signUp(String id, String username, String password, String email) {
        try (Connection conn = ConnectionPool.getConnection()) {
            // check if the username already exists
            if (userExistsByUsername(conn, username)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username already exists");
                alert.show();
                return false;
            }
            // check if the email already exists
            if (userExistsByEmail(conn, email)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Email already exists");
                alert.show();
                return false;
            }
            // check if the id already exists
            if (userExistsById(conn, id)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("ID already exists");
                alert.show();
                return false;
            }

            String sql = "INSERT INTO Users(id, username, password, email, date_of_birth, avatar, role, last_access, streak, longest_streak) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, email);
            pstmt.setString(5, LocalDate.now().toString());
            pstmt.setString(6, "/Image/Avatar/Gekko.png");
            pstmt.setString(7, "User");
            pstmt.setString(8, LocalDate.now().toString());
            pstmt.setInt(9, 0);
            pstmt.setInt(10, 0);

            pstmt.executeUpdate();
            System.out.println("User signed up successfully.");

        } catch (SQLException e) {
            System.out.println("Error signing up: " + e.getMessage());
        }
        return true;

    }

    // Helper method to check if user exists by username
    private boolean userExistsByUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Helper method to check if user exists by email
    private boolean userExistsByEmail(Connection conn, String email) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Helper method to check if user exists by id
    private boolean userExistsById(Connection conn, String id) throws SQLException {
        String sql = "SELECT 1 FROM Users WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // đăng nhập
    public User logIn(String username, String password) {
        Connection connection = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet rs = null;
        User loggedInUser = null;

        try {
            connection = ConnectionPool.getConnection();

            psCheckUserExist = connection.prepareStatement("SELECT * FROM USERS WHERE username = ? AND password = ?");
            psCheckUserExist.setString(1, username);
            psCheckUserExist.setString(2, password);
            rs = psCheckUserExist.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User does not exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Your username or password is incorrect");
                alert.show();
            } else {
                while (rs.next()) {
                    String retrievedPassword = rs.getString("password");
                    String role = rs.getString("role");
                    if (retrievedPassword.equals(password)) {
                        // Check the role of the user
                        if (role.equals("Admin")) {
                            loggedInUser = new Admin(rs.getInt("ID"),
                                    rs.getString("USERNAME"),
                                    rs.getString("PASSWORD"),
                                    rs.getString("EMAIL"),
                                    LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                                    rs.getString("AVATAR"),
                                    rs.getString("ROLE"),
                                    new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), rs.getInt("STREAK"), rs.getInt("LONGEST_STREAK")));
                        } else if (role.equals("User")) {
                            loggedInUser = new User(rs.getInt("ID"),
                                    rs.getString("USERNAME"),
                                    rs.getString("PASSWORD"),
                                    rs.getString("EMAIL"),
                                    LocalDate.parse(rs.getString("DATE_OF_BIRTH")),
                                    rs.getString("AVATAR"),
                                    rs.getString("ROLE"),
                                    new Streak(LocalDate.parse(rs.getString("LAST_ACCESS")), rs.getInt("STREAK"),rs.getInt("LONGEST_STREAK")));
                        }
                    } else {
                        System.out.println("Password is incorrect");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Your username or password is incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExist != null) {
                try {
                    psCheckUserExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return loggedInUser;
    }

    // lưu sách vào database
    public void saveBookToDatabase(Book book) {
        // Câu lệnh SQL không bao gồm book_id
        String sql = "INSERT INTO Books(isbn, title, author, publisher, published_date, language, category, description, cover_image_path, audio_path,quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());              // Mã ISBN
            pstmt.setString(2, book.getTitle());             // Tên sách
            pstmt.setString(3, book.getAuthor());            // Tác giả
            pstmt.setString(4, book.getPublisher());         // Nhà xuất bản
            pstmt.setString(5, book.getPublishedDate().toString());
            pstmt.setString(6, book.getLanguage());          // Ngôn ngữ
            pstmt.setString(7, book.getCategory());          // Thể loại
            pstmt.setString(8, book.getDescription());       // Mô tả
            pstmt.setString(9, book.getCoverImagePath());    // Đường dẫn đến ảnh bìa
            pstmt.setString(10, book.getAudioPath());        // Đường dẫn đến file audio
            pstmt.setInt(11, book.getQuantity());            // Số lượng sách

            pstmt.executeUpdate();
            System.out.println("Book saved to database.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // lấy mọi sách từ database
    public ArrayList<Book> getBooksFromDatabase() {
        String sql = "SELECT * FROM Books";

        ArrayList<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Book book = new Book(rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("language"),
                        rs.getString("publisher"),
                        LocalDate.parse(rs.getString("published_date")),
                        rs.getString("book_path"),
                        rs.getString("cover_image_path"),
                        rs.getString("audio_path"),
                        rs.getInt("quantity"));
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return books;
    }

    // cập nhật thông tin người dùng
    public void updateUserInDatabase(User user) {
        // Câu lệnh SQL không bao gồm book_id
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, " +
                "avatar = ?, last_access = ?, date_of_birth = ?, streak = ?, longest_streak = ? WHERE id = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());  // Tên người dùng
            pstmt.setString(2, user.getPassword());  // Mật khẩu
            pstmt.setString(3, user.getEmail());     // Email
            pstmt.setString(4, user.getPathToProfilePicture());    // Đường dẫn đến ảnh đại diện
            pstmt.setString(5, user.getStreak().getLastAccess().toString()); // Last access
            pstmt.setString(6, user.getDateOfBirth().toString()); // Ngày sinh
            pstmt.setInt(7, user.getStreak().getStreak()); // Streak
            pstmt.setInt(8, user.getStreak().getLongestStreak()); // Longest streak
            pstmt.setInt(9, user.getId()); // ID người dùng

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // tra sách
    public List<Book> searchBook (String search) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR category LIKE ? OR language LIKE ? OR publisher LIKE ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + search + "%");
            pstmt.setString(2, "%" + search + "%");
            pstmt.setString(3, "%" + search + "%");
            pstmt.setString(4, "%" + search + "%");
            pstmt.setString(5, "%" + search + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("language"),
                        rs.getString("publisher"),
                        LocalDate.parse(rs.getString("published_date")),
                        rs.getString("book_path"),
                        rs.getString("cover_image_path"),
                        rs.getString("audio_path"),
                        rs.getInt("quantity"));
                // Create and add the Book object to the list
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return books;
    }

    // mượn sách
    public void BorrowBook(Transaction transaction) {
        // SQL statements
        String sqlInsert = "INSERT INTO BookTransaction(user_id, book_id, date_borrowed, due_date, status) " +
                "VALUES (?, ?, ?, ?, ?)";
        String sqlUpdate = "UPDATE Books SET quantity = quantity - 1 WHERE isbn = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {

            // Set parameters for the INSERT statement
            pstmtInsert.setInt(1, transaction.getUser().getId());
            pstmtInsert.setString(2, transaction.getBook().getIsbn());
            pstmtInsert.setString(3, transaction.getDateBorrowed().toString());
            pstmtInsert.setString(4, transaction.getDueDate().toString());
            pstmtInsert.setString(5, transaction.getStatus().name());

            pstmtInsert.executeUpdate();

            pstmtUpdate.setString(1, transaction.getBook().getIsbn());

            pstmtUpdate.executeUpdate();

            System.out.println("Transaction saved to database and book quantity updated.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // tra sách bằng mượn/trả
    public List<Book> searchBookByTransaction(String query) {
        List<Book> books = new ArrayList<>();

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("language"),
                        rs.getString("publisher"),
                        LocalDate.parse(rs.getString("published_date")),
                        rs.getString("book_path"),
                        rs.getString("cover_image_path"),
                        rs.getString("audio_path"),
                        rs.getInt("quantity"));
                books.add(book);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return books;
    }

    // thông tin về mượn trả sách
    public Transaction getTransaction(User user, Book book) {
        String sql = "SELECT * FROM BookTransaction WHERE user_id = ? AND book_id = ? AND status = 'Borrowed'";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, user.getId());
            pstmt.setString(2, book.getIsbn());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                LocalDate dateReturned = null;
                String dateReturnedStr = rs.getString("date_returned");
                if (dateReturnedStr != null) {
                    dateReturned = LocalDate.parse(dateReturnedStr);
                }
                return new Transaction(rs.getInt("id"),
                        user,
                        book,
                        LocalDate.parse(rs.getString("date_borrowed")),
                        LocalDate.parse(rs.getString("due_date")),
                        dateReturned,
                        Transaction.status.valueOf(rs.getString("status")));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving transaction from database: " + e.getMessage());
        }

        return null;
    }

    // trả sách
    public void returnBook(Transaction transaction) {
        // SQL statements
        String sqlUpdateTransaction = "UPDATE BookTransaction SET date_returned = ?, status = ? WHERE id = ?";
        String sqlUpdateBook = "UPDATE Books SET quantity = quantity + 1 WHERE isbn = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmtUpdateTransaction = conn.prepareStatement(sqlUpdateTransaction);
             PreparedStatement pstmtUpdateBook = conn.prepareStatement(sqlUpdateBook)) {

            // Update the transaction
            pstmtUpdateTransaction.setString(1, transaction.getDateReturned().toString());
            pstmtUpdateTransaction.setString(2, transaction.getStatus().name());
            pstmtUpdateTransaction.setInt(3, transaction.getId());
            pstmtUpdateTransaction.executeUpdate();

            // Update the book quantity
            pstmtUpdateBook.setString(1, transaction.getBook().getIsbn());
            pstmtUpdateBook.executeUpdate();

            System.out.println("Transaction updated in database and book quantity updated.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace(); // Log the stack trace for better debugging
        }
    }

    // truy vấn sql
    public boolean findQuery(String query) {
        String sql = query;

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books from database: " + e.getMessage());
        }

        return false;
    }

    public void loadQuery(String query) {
        String sql = query;

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
    }

    // lấy danh sách người dùng
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        LocalDate.parse(rs.getString("date_of_birth")),
                        rs.getString("avatar"),
                        rs.getString("role"),
                        new Streak(LocalDate.parse(rs.getString("last_access")), rs.getInt("streak"), rs.getInt("longest_streak")));
                // Create and add the User object to the list
                users.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving users from database: " + e.getMessage());
        }

        return users;
    }

    // lưu review vào database
    public void saveReviewToDatabase(Review review, Book currentBook) {
        // Optimize: Check if review exists for this specific user and book directly
        String checkQuery = "SELECT id FROM Reviews WHERE userId = ? AND isbn = ?";
        String insertQuery = "INSERT INTO Reviews (comment, rating, userId, isbn, user_avatar) VALUES (?, ?, ?, ?, ?)";
        String updateQuery = "UPDATE Reviews SET comment = ?, rating = ?, user_avatar = ? WHERE userId = ? AND isbn = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // Check if review already exists for this user and book
            checkStmt.setInt(1, review.getUser().getId());
            checkStmt.setString(2, currentBook.getIsbn());
            ResultSet rs = checkStmt.executeQuery();

            String profilePicturePath = review.getUser().getPathToProfilePicture();

            if (rs.next()) {
                // Update existing review
                updateStmt.setString(1, review.getComment());
                updateStmt.setInt(2, review.getRating());
                updateStmt.setString(3, profilePicturePath);
                updateStmt.setInt(4, review.getUser().getId());
                updateStmt.setString(5, currentBook.getIsbn());
                updateStmt.executeUpdate();
            } else {
                // Insert new review
                insertStmt.setString(1, review.getComment());
                insertStmt.setInt(2, review.getRating());
                insertStmt.setInt(3, review.getUser().getId());
                insertStmt.setString(4, currentBook.getIsbn());
                insertStmt.setString(5, profilePicturePath);
                insertStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Phương thức giả sử để lấy người dùng từ userId
    public static User getUserById(int userId) {
        // Đảm bảo bạn có phương thức này để truy vấn cơ sở dữ liệu và trả về đối tượng User
        // Ví dụ:
        String query = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPathToProfilePicture(rs.getString("avatar"));
                // Set các thuộc tính khác của User nếu cần
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;  // Trả về null nếu không tìm thấy user
    }


    // lấy đánh giá trung bình
    public double getAverageRatingForBook(Book book) {
        String query = "SELECT AVG(rating) AS avg_rating FROM Reviews WHERE isbn = ?";
        double avgRating = 0.0;

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getIsbn());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                avgRating = rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return avgRating;
    }

    // lấy review theo ISBN
    public List<Review> getReviewsByISBN(String isbn) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM reviews WHERE isbn = ?";
        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Review review = new Review();
                review.setId(resultSet.getInt("id"));
                review.setUserid(resultSet.getInt("userid"));
                review.setComment(resultSet.getString("comment"));
                review.setRating(resultSet.getInt("rating"));
                review.setTimestamp(resultSet.getTimestamp("timestamp"));
                review.setIsbn(resultSet.getString("isbn"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Update user streak information safely
    public void updateUserStreak(int userId, LocalDate lastAccess, int streak, int longestStreak) {
        String sql = "UPDATE users SET LAST_ACCESS = ?, STREAK = ?, LONGEST_STREAK = ? WHERE id = ?";

        try (Connection conn = ConnectionPool.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lastAccess.toString());
            pstmt.setInt(2, streak);
            pstmt.setInt(3, longestStreak);
            pstmt.setInt(4, userId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}