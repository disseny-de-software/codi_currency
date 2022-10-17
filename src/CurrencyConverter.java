import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

public class CurrencyConverter {
  private static final String[] currencies = new String[] {"EUR", "GBP", "CHF", "JPY", "USD"};
  private static HashMap<String, HashMap<String, Double>> tableExchangeRates = new HashMap<>();
  private static String dateLastUpdate;

  public CurrencyConverter() throws IOException {
    for (String currencyFrom : currencies) {
      for (String currencyTo : currencies) {
        HashMap<String, Double> map = new HashMap<>();
        map.put(currencyTo, 0.0);
        tableExchangeRates.put(currencyFrom, map);
      }
    }
    //fakeExchangeRates();
    realExchangeRatesTable();
  }

  private static void fakeExchangeRates() {
    // fake rates, random but fixed along executions
    int seed = 1234;
    Random generator = new Random(seed);
    for (String currencyFrom : currencies) {
      for (String currencyTo : currencies) {
        tableExchangeRates.get(currencyFrom).put(currencyTo, generator.nextDouble());
      }
    }
  }

  private static void realExchangeRatesTable() throws IOException {
    // real rates can be obtained like in this example
    // https://www.exchangerate-api.com/docs/java-currency-api
    // by using the GSON library https://github.com/google/gson
    // This is the URL of Exchangerate
    // String url_str = "https://v6.exchangerate-api.com/v6/YOUR-API-KEY/latest/USD";
    // but needs an API key, updated every hour
    String url_str_base = "https://open.er-api.com/v6/latest/"; // append currency here like USD
    // no API key needed but updated only once per day
    for (String currencyFrom : currencies) {
      // Making Request
      URL url = new URL(url_str_base + currencyFrom);
      HttpURLConnection request = (HttpURLConnection) url.openConnection();
      request.connect();
      JsonParser jp = new JsonParser(); // Convert to JSON
      JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
      JsonObject jsonobj = root.getAsJsonObject();
      //System.out.println(jsonobj);
      dateLastUpdate = jsonobj.get("time_last_update_utc").getAsString() + " UTC";
      assert jsonobj.get("result").getAsString() == "success";
      JsonObject rates = jsonobj.getAsJsonObject("rates");
      for (String currencyTo : currencies) {
        if (currencyFrom != currencyTo) {
          double rate = rates.get(currencyTo).getAsDouble();
          //System.out.println("1 " + currencyFrom + " = " +  rate + " " + currencyTo);
          tableExchangeRates.get(currencyFrom).put(currencyTo, rate);
        }
      }
    }
  }

  public static double getExchangeRate(String currencyFrom, String currencyTo) {
    return tableExchangeRates.get(currencyFrom).get(currencyTo);
  }

  @Override
  public String toString() {
    String res = dateLastUpdate + "\n";
    for (String currencyFrom : currencies) {
      for (String currencyTo : currencies) {
        if (currencyTo != currencyFrom) {
          res += "1 " + currencyFrom + " = "
                  + tableExchangeRates.get(currencyFrom).get(currencyTo) + " " + currencyTo + "\n";
        }
      }
    }
    return res;
  }
}
