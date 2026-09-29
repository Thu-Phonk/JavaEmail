package murach.email;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public final class MailUtilGmail {

    private MailUtilGmail() {
    }

    public static void sendMail(
            String to,
            String from,
            String subject,
            String body,
            boolean bodyIsHTML) throws Exception {

        String apiKey = requireEnv("BREVO_API_KEY");
        String senderEmail = from;

        if (senderEmail == null || senderEmail.isBlank()) {
            senderEmail = System.getenv("MAIL_FROM");
        }

        if (senderEmail == null || senderEmail.isBlank()) {
            senderEmail = System.getenv("MAIL_USERNAME");
        }

        if (senderEmail == null || senderEmail.isBlank()) {
            throw new IllegalStateException(
                    "Missing sender email. Set MAIL_FROM in Render.");
        }

        String content;

        if (bodyIsHTML) {
            content = "\"htmlContent\":\"" + escapeJson(body) + "\"";
        } else {
            content = "\"textContent\":\"" + escapeJson(body) + "\"";
        }

        String json = "{"
                + "\"sender\":{\"email\":\"" + escapeJson(senderEmail) + "\"},"
                + "\"to\":[{\"email\":\"" + escapeJson(to) + "\"}],"
                + "\"subject\":\"" + escapeJson(subject) + "\","
                + content
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.brevo.com/v3/smtp/email"))
                .header("accept", "application/json")
                .header("api-key", apiKey)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        json, StandardCharsets.UTF_8))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException(
                    "Brevo API error: HTTP "
                    + response.statusCode()
                    + " - "
                    + response.body());
        }
    }

    private static String requireEnv(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable: " + name);
        }

        return value.trim();
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }
}