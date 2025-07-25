package com.MentorConnect.MentorConnect.Service;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MediaType;

import org.springframework.stereotype.Service;

import java.io.IOException;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;


@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final OkHttpClient client = new OkHttpClient();


    private String callOpenAI(String prompt) throws IOException {
        String json = "{"
                + "\"model\": \"gpt-4\","
                + "\"messages\": ["
                + "  {\"role\": \"user\", \"content\": \"" + prompt + "\"}"
                + "],"
                + "\"temperature\": 0.7"
                + "}";

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }

    }


    public String getSuggestions(String taskDescription) throws IOException {
        String prompt = "Give suggestions to solve this task: " + taskDescription;
        return callOpenAI(prompt);
    }


    public String reviewTask(String task, String submission) throws IOException {
        String prompt = "Review this submission for the task: " + task + "\nSubmission:\n" + submission;
        return callOpenAI(prompt);
    }


}

