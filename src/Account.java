public class Account {
  private final String currency;
  private double amount;

  public Account(String currency, double anAmount) {
    this.currency = currency;
    this.amount = anAmount;
  }

  public double getAmount() {
    return amount;
  }

  public void add(double anAmount) {
    this.amount += anAmount;
    System.out.println("added " + anAmount + " to account in " + currency);
  }

  public void subtract(double anAmount) {
    this.amount -= anAmount;
    System.out.println("subtracted " + anAmount + " to account in " + currency);
  }

  @Override
  public String toString() {
    return "Account in " + currency + " has balance of " + amount;
  }

}
