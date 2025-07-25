//package com.MentorConnect.MentorConnect.Controller;
//
//import com.MentorConnect.MentorConnect.Dto.ReviewRequest;
//import com.MentorConnect.MentorConnect.Dto.ReviewResponse;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//
//@RestController
//@CrossOrigin
//public class ReviewController {
//
//@Value("${openai.api.key}")
//private String openaiApiKey;
//
//@PostMapping("/review")
//public ReviewResponse reviewCode(@RequestBody ReviewRequest request) throws IOException {
// String prompt = "Review this Java code and rate it out of 10 with explanation:\n" + request.getCode();
//
// OkHttpClient client = new OkHttpClient();
// MediaType mediaType = MediaType.parse("application/json");
//
// String json = "{"
// + "\"model\": \"gpt-3.5-turbo\","
// + "\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt.replace("\"", "\\\"") + "\"}]"
// + "}";
//
// okhttp3.RequestBody body = okhttp3.RequestBody.create(json, mediaType);
//
// Request openAIRequest = new Request.Builder()
// .url("https://api.openai.com/v1/chat/completions")
// .post(body)
//.addHeader("Authorization", "Bearer " + openaiApiKey)
// .addHeader("Content-Type", "application/json")
// .build();
//
// try (Response response = client.newCall(openAIRequest).execute()) {
//if (!response.isSuccessful()) {
// return new ReviewResponse("Error: " + response.code());
// }
//
// String responseBody = response.body().string();
// String reviewText = responseBody.split("\"content\":\"")[1].split("\"}")[0].replace("\\n", "\n");
// return new ReviewResponse(reviewText);
// }
// }
//}
