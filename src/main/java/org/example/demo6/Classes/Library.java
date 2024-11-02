package org.example.demo6.Classes;
import org.example.demo6.DBUltis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.demo6.Classes.FileUpload.uploadFile;

public class Library {



    public static void upLoadBook (Book book, File selectedFile, File selectedCover, File selectedAudio) {
        // Thư mục đích để lưu file
        String UPLOAD_DIRECTORY_BOOKS = "Uploaded/Books";
        String UPLOAD_DIRECTORY_AUDIO = "Uploaded/AudioBooks";
        String UPLOAD_DIRECTORY_IMAGE = "Uploaded/BookCovers";


        // Upload the book file
        FileUpload.uploadFile(selectedFile, UPLOAD_DIRECTORY_BOOKS);
        // Upload the cover image
        if (selectedCover != null) {
            FileUpload.uploadFile(selectedCover, UPLOAD_DIRECTORY_IMAGE);
        }
        if (selectedAudio != null) {
            FileUpload.uploadFile(selectedAudio, UPLOAD_DIRECTORY_AUDIO);
        }

        DBUltis.saveBookToDatabase(book);

    }

    public void borrowBook (User user, Book book) {
        Transaction transaction = new Transaction(user, book, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    public void returnBook (Transaction transaction) {
        transaction.returnBook(LocalDate.now());
    }








}
