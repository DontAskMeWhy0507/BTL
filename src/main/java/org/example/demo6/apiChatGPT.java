package org.example.demo6;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class apiChatGPT {
    private static final String API_KEY = "sk-your-api-key";  // Thay thế bằng API key của bạn
    private static final String URL = "https://api.openai.com/v1/completions";

    public String getChatGPTResponse(String prompt) {
        try {
            // Tạo JSON object cho dữ liệu yêu cầu
            JSONObject json = new JSONObject();
            json.put("model", "text-davinci-003"); // Hoặc model khác như gpt-3.5-turbo
            json.put("prompt", prompt);
            json.put("max_tokens", 150);  // Đặt số lượng tokens tối đa cho phản hồi
            json.put("temperature", 0.7);

            // Tạo yêu cầu HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                    .build();

            // Gửi yêu cầu và lấy phản hồi
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Xử lý phản hồi JSON từ API
            JSONObject responseJson = new JSONObject(response.body());
            return responseJson.getJSONArray("choices").getJSONObject(0).getString("text");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
