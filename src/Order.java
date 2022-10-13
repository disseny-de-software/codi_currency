import java.time.LocalDateTime;

abstract class Order {
  protected final Portfolio portfolio;
  protected final LocalDateTime dateTime;
  protected boolean done = false;
  public abstract void execute();

  public Order(Portfolio portfolio, LocalDateTime dateTime) {
    this.portfolio = portfolio;
    this.dateTime = dateTime;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public boolean isDone() { return done; }
}
