
package org.example.demo6;
import org.example.demo6.Classes.ApiGoogleGemini;
import org.example.demo6.Classes.Book;
import org.example.demo6.Classes.apiGoogleBooks;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PathCheck {

    public static void main(String[] args) throws IOException {
        apiGoogleBooks apiGoogleBooks = new apiGoogleBooks();
        List<Book> books = apiGoogleBooks.searchBooks1("Harry");
        for (Book book : books) {
            System.out.println(book.getIsbn());
            System.out.println(book.getTitle());
            System.out.println(book.getAuthor());
            System.out.println(book.getPublisher());
            System.out.println(book.getPublishedDate());
            System.out.println(book.getDescription());
            System.out.println(book.getCoverImagePath());
            System.out.println(book.getCategory());
        }
    }


}