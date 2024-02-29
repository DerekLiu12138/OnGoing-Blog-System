package web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojos.User;
import util.JSONUtils;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class API {
    private static API instance;

    private static final String BASE_URL = "http://localhost:3000/api";

    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    private final CookieManager cookieManager;
    private final HttpClient client;

    private API() {
        this.cookieManager = new CookieManager();

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(this.cookieManager)
                .build();
    }


    public User userLogin(String username, String password) throws IOException, InterruptedException, NotAdminException, LoginFailedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))  // API end point
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String responseBody = response.body();

        // 处理服务器的响应
        if (statusCode == 204) {
            // 验证成功
            return null;
        } else if (statusCode == 401) {
            // 验证失败
            if (responseBody.contains("You are not an admin!")) {
                throw new NotAdminException();
            } else {
                throw new LoginFailedException();
            }
        } else {
            throw new IOException("Error：" + statusCode);
        }
    }

    public class NotAdminException extends Exception {
        public NotAdminException() {
            super("You are not an admin!");
        }
    }

    public class LoginFailedException extends Exception {
        public LoginFailedException() {
            super("Username or password is incorrect!");
        }
    }


    public List<User> getUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/user"))  // change endpoint to /user
                .setHeader("Accept", "application/json")
                .method("POST", HttpRequest.BodyPublishers.noBody())  // change method to POST
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get users: status code " + response.statusCode());
        }
        String responseJson = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(responseJson, new TypeReference<List<User>>(){});
    }

    public void deleteUser(int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/delete/" + userId))
                .method("DELETE", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
