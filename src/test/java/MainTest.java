import org.example.demo6.Classes.*;
import org.junit.Test;

import java.time.LocalDateTime;

public class MainTest {
    @Test
    public void testTransactionBook() {
        // Create a new book
        Book book1 = new Book("123", "Harry Potter", "J.K.Rowling",
                "Fantasy", "A book about a wizard", "English",
                "Bloomsbury", "1997", "path/to/book",
                "path/to/cover", "path/to/audio", 10);
        // Create a new user
        User user1 = new User(1, "user1", "password", "test@gmail.com",
                "01/01/2000", "path/to/profile", "user");
        // Create a new transaction
        Transaction transaction1 = new Transaction(user1, book1, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
        // Check if the transaction is overdue
        assert !transaction1.isOverdue();
        // Return the book
        transaction1.returnBook(LocalDateTime.now().plusDays(7));
        // Check if the transaction is not overdue after returning the book
        assert !transaction1.isOverdue();
    }

    @Test
    public void testReturnBook() {

    }
}