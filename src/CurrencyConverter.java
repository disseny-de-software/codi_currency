import java.util.HashMap;
import java.util.Random;

public class CurrencyConverter {
  private static final String[] currencies = new String[] {"EUR", "GBP", "CHF", "JPY", "USD"};
  private static HashMap<String, HashMap<String, Double>> tableExchangeRates = new HashMap<>();

  public CurrencyConverter() {
    for (String currencyFrom : currencies) {
      for (String currencyTo : currencies) {
        HashMap<String, Double> map = new HashMap<>();
        map.put(currencyTo, 0.0);
        tableExchangeRates.put(currencyFrom, map);
      }
    }
    // fake rates, random but fixed along executions
    // maybe real rates can be obtained like in this example
    // https://www.exchangerate-api.com/docs/java-currency-api
    int seed = 1234;
    Random generator = new Random(seed);
    for (String currencyFrom : currencies) {
      for (String currencyTo : currencies) {
        tableExchangeRates.get(currencyFrom).put(currencyTo, generator.nextDouble());
      }
    }
  }

  public static double getExchangeRate(String currencyFrom, String currencyTo) {
    return tableExchangeRates.get(currencyFrom).get(currencyTo);
  }

  @Override
  public String toString() {
    String res = "";
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
