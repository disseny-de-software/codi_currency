import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class OrderExecuter {
  private List<Order> orders = new ArrayList<>();
  private Timer timer = new Timer();
  private final long delay = 0L;
  private final long period = 2000L; // milliseconds

  private TimerTask repeatedTask = new TimerTask() {
    public void run() {
      LocalDateTime now = LocalDateTime.now();
      //System.out.println("Task performed on " + now);
      System.out.print(".");
      List<Order> ordersToRemove = new ArrayList<>();
      // better do not edit the list during traversal

      for (Order order : orders) {
        if (now.isBefore(order.getDateTime())) {
          order.execute();
          if (order.isDone()) {
            ordersToRemove.add(order);
            System.out.println("Order " + order + " to be removed because it has completed");
          }
        } else {
          ordersToRemove.add(order);
          System.out.println("Order " + order + " to be removed because it's past its deadline "
                  + order.getDateTime());
        }
      }
      for (Order order : ordersToRemove) {
        orders.remove(order);
      }
    }
  };

  public OrderExecuter() {
    timer.scheduleAtFixedRate(repeatedTask, delay, period);
  }

  public void placeOrder(Order order) {
    if (order.getDateTime().isAfter(LocalDateTime.now())) {
      orders.add(order);
      System.out.println("Added order " + order);
    }
  }
}
