package gunlukdovizkuruservisi;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gunlukdovizkuruservisi.DatabaseConnector;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;

public class CurrencyConverter {

    public static void main(String[] args) {
        Properties properties = new Properties();
        try (InputStream input = CurrencyConverter.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return; 
        }

        String API_KEY = properties.getProperty("API_KEY");

        String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(BASE_URL);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String jsonResponse = EntityUtils.toString(entity);

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

            if (jsonObject.get("result").getAsString().equals("success")) {
                JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");
                double tryRate = rates.get("TRY").getAsDouble();

                LocalDate currentDate = LocalDate.now();

                saveToDatabase(currentDate, tryRate);

            } else {
                System.out.println("API basarısız.");
            }

            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveToDatabase(LocalDate date, double tryRate) {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String insertQuery = "INSERT INTO daily_exchange_rates (DATE, USD_TO_TRY) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(date);
                preparedStatement.setDate(1, sqlDate);
                preparedStatement.setDouble(2, tryRate);
                preparedStatement.executeUpdate();
                System.out.println("Başarılı");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
