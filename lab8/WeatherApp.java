// Legacy API Fetcher

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

class WeatherAPIClient {
    public double getTemperatureInFahrenheit(double x, double y) {
        try {
            String apiUrl = "https://api.weather.gov/points/" + x + "," + y;
            String response = fetchAPI(apiUrl);

            // Extract forecast URL manually using substring and indexOf
            String forecastUrl = extractValue(response, "\"forecastHourly\":\"", "\"");

            if (forecastUrl == null) {
                return -1; // Error case
            }

            // Fetch forecast data
            String forecastResponse = fetchAPI(forecastUrl);
            String tempFString = extractValue(forecastResponse, "\"temperature\":", ",");

            if (tempFString == null) {
                return -1; // Error case
            }

            return Double.parseDouble(tempFString);
        } catch (Exception e) {
            return -1; // Handle API fetch error
        }
    }

    public double getHumidity(double x, double y) {
        try {
            String apiUrl = "https://api.weather.gov/points/" + x + "," + y;
            String response = fetchAPI(apiUrl);

            // Extract forecast URL manually
            String forecastUrl = extractValue(response, "\"forecastHourly\":\"", "\"");

            if (forecastUrl == null) {
                return -1; // Error case
            }

            // Fetch forecast data
            String forecastResponse = fetchAPI(forecastUrl);
            String humidityString = extractValue(forecastResponse, "\"relativeHumidity\":", ",");

            if (humidityString == null) {
                return -1; // Error case
            }

            return Double.parseDouble(humidityString);
        } catch (Exception e) {
            return -1; // Handle API fetch error
        }
    }

    private String fetchAPI(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return response.toString();
    }

    private String extractValue(String json, String startKey, String endKey) {
        int startIndex = json.indexOf(startKey);
        if (startIndex == -1) return null;
        startIndex += startKey.length();
        int endIndex = json.indexOf(endKey, startIndex);
        if (endIndex == -1) return null;
        return json.substring(startIndex, endIndex).trim();
    }
}

interface WeatherService {
    double getTemperatureInCelsius(double x, double y);
    double getHumidity(double x, double y);
}

class WeatherAdapter implements WeatherService {
    private WeatherAPIClient apiClient;

    public WeatherAdapter(WeatherAPIClient apiClient) {
        this.apiClient = apiClient;
    }
    

    // Add public to function overrides as they by default went package-private.
    @Override
    public double getTemperatureInCelsius(double x, double y) {
        double fTemp;
        double cTemp;
        fTemp = apiClient.getTemperatureInFahrenheit(x, y);
        cTemp = (fTemp - 32) * 5 / 9;

        return cTemp;
    }

    @Override
    public double getHumidity(double x, double y) {
        return apiClient.getHumidity(x, y);
    }

}

public class WeatherApp {
    public static void main(String[] args) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter latitude (x): ");
            double x = scanner.nextDouble();
            System.out.print("Enter longitude (y): ");
            double y = scanner.nextDouble();
            scanner.close();

            WeatherAPIClient apiClient = new WeatherAPIClient();
            WeatherService weatherService = new WeatherAdapter(apiClient);

            // TODO: Fetch and print temperature in Celsius
            System.out.println("Temperature: " + weatherService.getTemperatureInCelsius(x, y));
            // TODO: Fetch and print humidity
            System.out.println("Humidity: " + weatherService.getHumidity(x, y));

    }
}