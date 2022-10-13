import java.time.LocalDateTime;

public class Main {
  public static void main(String[] args) {
    CurrencyConverter currencyConverter = new CurrencyConverter();
    System.out.println(currencyConverter);

    OrderExecuter orderExecuter = new OrderExecuter();
    // orders will start executing if possible from now on

    // this is what a customer would do on its portfolio through the user interface
    Portfolio portfolio = new Portfolio(); // this would be in Customer constructor
    System.out.println(portfolio);
    LocalDateTime now = LocalDateTime.now();
    Order order1 = new Buy(portfolio, 10., "USD", now.plusSeconds(3), 0.39);
    // will be done
    Order order2 = new Buy(portfolio, 20., "CHF", now.plusSeconds(5), 0.40);
    // won't be done
    Order order3 = new Sell(portfolio, 5, "USD", now.plusSeconds(6), 0.20);
    // will be done once we have completed order1 because before we don't
    // own any USD, we start operating with 1000 EUR only
    orderExecuter.placeOrder(order3);
    orderExecuter.placeOrder(order1);
    orderExecuter.placeOrder(order2);
  }
}