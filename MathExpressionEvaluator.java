import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;


public class MathExpressionEvaluator {
    // ... Previous code ...
    private static final String API_URL = "https://api.mathjs.org/v4/";

    // Method to evaluate a mathematical expression using the MathJS API
    private static String evaluateExpression(String expression) throws IOException {
        String url = API_URL + "?expr=" + expression.replaceAll(" ", "%20");
        try {
            // Create a URI object from the URL string
            URI uri = new URI(url);
            // Open a connection to the API URL
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            // Get the response code from the API
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and collect the response from the API
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                // Throw an exception if the API request fails
                throw new IOException("Failed to evaluate the expression. Response Code: " + responseCode);
            }
        } catch (URISyntaxException e) {
             // Throw an exception if the URL is invalid
            throw new IOException("Invalid URL: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // ... Main method code to accept and evaluate expressions ...
        Scanner scanner = new Scanner(System.in);
        String expression;

        while (true) {
            System.out.print("Enter a mathematical expression (or 'exit' to quit): ");
            expression = scanner.nextLine();

            if (expression.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String result = evaluateExpression(expression);
                System.out.println("Result: " + result);
            } catch (IOException e) {
                System.out.println("An error occurred while evaluating the expression: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
