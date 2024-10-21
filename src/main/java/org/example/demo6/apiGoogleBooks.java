package org.example.demo6;


import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volumes;
import com.google.api.services.books.model.Volume;

import java.io.IOException;

public class apiGoogleBooks {

    // Replace with your API Key from Google Cloud Console
    private static final String API_KEY = "AIzaSyCyibUKHM6uRFhx6bzrhHyNDbt1GO3h-Lk";

//    public static void main(String[] args) {
//        try {
//            searchBooks("Khong gia dinh");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

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
                        + " - " + volumeInfo.getCategories() );

            }
        } else {
            System.out.println("No matches found.");
        }
    }
}

