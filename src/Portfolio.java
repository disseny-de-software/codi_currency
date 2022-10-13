import java.util.HashMap;

public class Portfolio {
  private HashMap<String, Account> accounts = new HashMap<>();

  public Portfolio() {
    accounts.put("EUR", new Account("EUR", 1000));
    accounts.put("USD", new Account("USD", 0.));
    accounts.put("GBP", new Account("GBP", 0.));
    accounts.put("JPY", new Account("JPY", 0.));
    accounts.put("CHF", new Account("CHF", 0.));
  }

  /*
  // in this version Order class does not know Account but Portfolio does all the job
  public void buy(double amountCurrency, String currency) {
    double amountToPayInEUR = amountCurrency * CurrencyConverter.getExchangeRate(currency, "EUR");
    accounts.get("EUR").subtract(amountToPayInEUR);
    accounts.get(currency).add(amountCurrency);
  }
  */

  // in this version Order knows Account and does its job on it
  public Account findAccount(String currency) {
    return accounts.get(currency);
  }

  @Override
  public String toString() {
    String result = "";
    for (Account account : accounts.values()) { result += account + "\n"; }
    return result;
  }

}
