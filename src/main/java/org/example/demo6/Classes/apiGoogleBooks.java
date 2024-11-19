package org.example.demo6.Classes;


import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volumes;
import com.google.api.services.books.model.Volume;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class apiGoogleBooks {

    // Replace with your API Key from Google Cloud Console
    private static final String API_KEY = "AIzaSyCyibUKHM6uRFhx6bzrhHyNDbt1GO3h-Lk";


    public static void searchBooks(String query) throws IOException {
        // Initialize the Books API client
        Books books = new Books.Builder(new com.google.api.client.http.javanet.NetHttpTransport(),
                new com.google.api.client.json.jackson2.JacksonFactory(),
                null)
                .setApplicationName("LibraryApp")
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
                .build();

        // Perform the search query
        Books.Volumes.List volumesList = books.volumes().list(query);
        Volumes volumes = volumesList.execute();

        // Print the titles of the returned books
        if (volumes.getTotalItems() > 0 && volumes.getItems() != null) {
            for (Volume volume : volumes.getItems()) {
                Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
                System.out.println(volumeInfo.getTitle() + " by " + volumeInfo.getAuthors()
                        + " - " + volumeInfo.getPublishedDate() + " - " + volumeInfo.getPageCount() + " pages"
                        + " - " + volumeInfo.getCategories() + " - " + volumeInfo.getImageLinks());

            }
        } else {
            System.out.println("No matches found.");
        }
    }
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

    private static LocalDate parsePublishedDate(String publishedDate) {
        if (publishedDate == null || publishedDate.isBlank()) {
            return LocalDate.now(); // Default to current date if null or empty
        }

        try {
            if (publishedDate.matches("\\d{4}")) { // Year only (e.g., "1875")
                return LocalDate.parse(publishedDate + "-01-01"); // Assume January 1
            } else if (publishedDate.matches("\\d{4}-\\d{2}")) { // Year and month only (e.g., "2024-11")
                return LocalDate.parse(publishedDate + "-01"); // Assume the first day of the month
            } else { // Full date (e.g., "2024-11-19")
                return LocalDate.parse(publishedDate);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse publishedDate: " + publishedDate);
            return LocalDate.now(); // Default to current date on failure
        }
    }

}

