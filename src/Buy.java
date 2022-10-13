import java.time.LocalDateTime;

public class Buy extends Order {
  private double amountCurrency;
  private String currency;
  private double strikePriceInEUR;

  // Buy 10 USD by tomorrow 11 AM if at some moment USD price 1 USD <= 0.95 EUR per USD,
  // and pay price 1 USD * exchange rate USD -> EUR
  public Buy(Portfolio portfolio, double amountCurrency, String currency,
             LocalDateTime dateTime, double strikePriceInEUR) {
    super(portfolio, dateTime);
    this.amountCurrency = amountCurrency;
    this.currency = currency;
    this.strikePriceInEUR = strikePriceInEUR;
  }

  @Override
  public void execute() {
    double exchangeRate = CurrencyConverter.getExchangeRate(currency, "EUR");
    System.out.println("exchange rate " + exchangeRate + " <= strike price " + strikePriceInEUR);
    if (exchangeRate <= strikePriceInEUR) {
      // portfolio.buy(amountCurrency, currency);
      // Order class knows Portfolio but not Account, but then Portfolio does all the job for each order
      // and the class becomes too complex and non cohesive, does not follow the SRP principle

      Account fromAccount = portfolio.findAccount("EUR");
      Account toAccount = portfolio.findAccount(currency);
      double amountToPayInEUR = amountCurrency * CurrencyConverter.getExchangeRate(currency, "EUR");
      if (fromAccount.getAmount() >= amountToPayInEUR) {
        fromAccount.subtract(amountToPayInEUR);
        toAccount.add(amountCurrency);
        done = true;
        System.out.println("Executed " + this);
      } else {
        System.out.println("Can not execute " + this + " because there's not enough money"
            + " in the EUR account");
      }
    } else {
      System.out.println("Can not execute " + this + " because it's too expensive");
      System.out.println(exchangeRate + " > " + strikePriceInEUR);
    }
  }

  @Override
  public String toString() {
    return "Buy " + amountCurrency + " " + currency + " before " + dateTime
            + " if price 1 " + currency + " <= " + strikePriceInEUR + " EUR";
  }
}
