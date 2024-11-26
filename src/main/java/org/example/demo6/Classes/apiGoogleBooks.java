package org.example.demo6.Classes;


import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volumes;
import com.google.api.services.books.model.Volume;
import javafx.concurrent.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class apiGoogleBooks {

    private static final String API_KEY = "AIzaSyCyibUKHM6uRFhx6bzrhHyNDbt1GO3h-Lk";

    // tìm sách
    public static List<Book> searchBooks1(String query) throws IOException {
        List<Book> books = new ArrayList<>();

        Books.Builder builder = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
                new com.google.api.client.json.jackson2.JacksonFactory(),
                null);
        builder.setApplicationName("LibraryApp");
        builder.setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY));
        Books booksApi = builder
                .build();

        Books.Volumes.List volumesList = booksApi.volumes().list(query);
        Volumes volumes = volumesList.execute();

        if (volumes.getTotalItems() > 0 && volumes.getItems() != null) {
            for (Volume volume : volumes.getItems()) {
                Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
                String isbn = volumeInfo.getIndustryIdentifiers() != null ? volumeInfo.getIndustryIdentifiers().get(0).getIdentifier() : "Unknown";
                String title = volumeInfo.getTitle();
                String author = volumeInfo.getAuthors() != null ? volumeInfo.getAuthors().get(0) : "Unknown";
                String category = volumeInfo.getCategories() != null ? volumeInfo.getCategories().get(0) : "Unknown";
                String description = volumeInfo.getDescription() != null ? volumeInfo.getDescription() : "No description available";
                String language = volumeInfo.getLanguage();
                String publisher = volumeInfo.getPublisher() != null ? volumeInfo.getPublisher() : "Unknown";
                LocalDate publishedDate = parsePublishedDate(volumeInfo.getPublishedDate());

                String bookPath  = null;
                String coverImagePath = volumeInfo.getImageLinks() != null ? volumeInfo.getImageLinks().getThumbnail() : null;
                String audioPath = null;

                Book book = new Book(isbn, title, author, category, description, language, publisher, publishedDate, bookPath,coverImagePath, audioPath,100);
                books.add(book);
            }
        }
        return books;
    }

    // ngày phát hành sách
    private static LocalDate parsePublishedDate(String publishedDate) {
        if (publishedDate == null || publishedDate.isBlank()) {
            return LocalDate.now();
        }

        try {
            if (publishedDate.matches("\\d{4}")) {
                return LocalDate.parse(publishedDate + "-01-01");
            } else if (publishedDate.matches("\\d{4}-\\d{2}")) {
                return LocalDate.parse(publishedDate + "-01");
            } else {
                return LocalDate.parse(publishedDate);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse publishedDate: " + publishedDate);
            return LocalDate.now();
        }
    }

}

