import java.time.LocalDateTime;

public class Trade extends Order {
  public Trade(Portfolio portfolio, double amountCurrencyFrom, String currencyFrom, String currencyTo,
               LocalDateTime dateTime, double strikePriceFrom, double strikePriceTo) {
    super(portfolio, dateTime);
    // TODO
  }

  @Override
  public void execute() {
    // TODO
  }
}
