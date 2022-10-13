import java.time.LocalDateTime;

public class Sell extends Order {
  private double amountCurrency;
  private String currency;
  private double strikePriceInEUR;

  public Sell(Portfolio portfolio, double amountCurrency, String currency, LocalDateTime dateTime,
              double strikePriceInEUR) {
    super(portfolio, dateTime);
    this.amountCurrency = amountCurrency;
    this.currency = currency;
    this.strikePriceInEUR = strikePriceInEUR;
  }

  @Override
  public void execute() {
    double exchangeRate = CurrencyConverter.getExchangeRate(currency, "EUR");
    System.out.println("exchange rate + " + exchangeRate + " >= strike price " + strikePriceInEUR);
    if (exchangeRate >= strikePriceInEUR) {
      Account fromAccount = portfolio.findAccount(currency);
      Account toAccount = portfolio.findAccount("EUR");
      double amountToGetInEUR = amountCurrency * CurrencyConverter.getExchangeRate(currency, "EUR");
      if (fromAccount.getAmount() >= amountCurrency) {
        fromAccount.subtract(amountCurrency);
        toAccount.add(amountToGetInEUR);
        done = true;
        System.out.println("Executed " + this);
      } else {
        System.out.println("Can not execute " + this + " because there's not enough money"
                + " in the " + currency + " account");
      }
    } else {
      System.out.println("Can not execute " + this + " because it's too cheap");
      System.out.println(exchangeRate + " < " + strikePriceInEUR);
    }
  }

  @Override
  public String toString() {
    return "Sell " + amountCurrency + " " + currency + " before " + dateTime
            + " if price 1 " + currency + " >= " + strikePriceInEUR + " EUR";
  }
}