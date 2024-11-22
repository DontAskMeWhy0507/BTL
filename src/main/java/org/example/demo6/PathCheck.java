package org.example.demo6;

import org.example.demo6.Classes.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PathCheck {
    public static void main(String[] args) {
        ApiGoogleGemini apiGoogleGemini = new ApiGoogleGemini();
        System.out.println(apiGoogleGemini.sendPostRequest("Hello, how are you?"));

    }
}
