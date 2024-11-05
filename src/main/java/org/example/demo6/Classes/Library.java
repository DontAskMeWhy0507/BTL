package org.example.demo6.Classes;

import java.io.File;
import java.time.LocalDate;

public class Library {


    public static void upLoadBook (Book book, File selectedFile, File selectedCover, File selectedAudio) {
        // Thư mục đích để lưu file
        String UPLOAD_DIRECTORY_BOOKS = "Uploaded/Books";
        String UPLOAD_DIRECTORY_AUDIO = "Uploaded/AudioBooks";
        String UPLOAD_DIRECTORY_IMAGE = "Uploaded/BookCovers";


        // Upload the book file
        if (selectedFile != null) {
            UpDownFile.uploadFile(selectedFile, UPLOAD_DIRECTORY_BOOKS);
            book.setBookPath("Uploaded/Books/" + selectedFile.getName());
        }
        // Upload the cover image
        if (selectedCover != null) {
            UpDownFile.uploadFile(selectedCover, UPLOAD_DIRECTORY_IMAGE);
            book.setCoverImagePath("Uploaded/BookCovers/" + selectedCover.getName());
        }
        if (selectedAudio != null) {
            UpDownFile.uploadFile(selectedAudio, UPLOAD_DIRECTORY_AUDIO);
            book.setAudioPath("Uploaded/AudioBooks/" + selectedAudio.getName());
        }

        //
        try {
            DBUltis.saveBookToDatabase(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void borrowBook (User user, Book book) {
        Transaction transaction = new Transaction(user, book, LocalDate.now(), LocalDate.now().plusDays(7));
    }

    public void returnBook (Transaction transaction) {
        transaction.returnBook(LocalDate.now());
    }








}
