import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderExecuter {
  private List<Order> orders = new ArrayList<>();
  private Timer timer = new Timer();
  private final long delay = 0L;
  private final long period = 2000L; // milliseconds
  static Logger logger = LoggerFactory.getLogger(OrderExecuter.class);

  private TimerTask repeatedTask = new TimerTask() {
    public void run() {
      LocalDateTime now = LocalDateTime.now();
      //System.out.println("Task performed on " + now);
      //System.out.print(".");
      logger.debug("Task performed on " + now);

      List<Order> ordersToRemove = new ArrayList<>();
      // better do not edit the list during traversal

      for (Order order : orders) {
        if (now.isBefore(order.getDateTime())) {
          order.execute();
          if (order.isDone()) {
            ordersToRemove.add(order);
            String message = "Order " + order + " to be removed because it has completed";
            //System.out.println(message);
            logger.info(message);
          }
        } else {
          ordersToRemove.add(order);
          String message = "Order " + order + " to be removed because it's past its deadline "
                  + order.getDateTime();
          //System.out.println(message);
          logger.info(message);
        }
      }
      for (Order order : ordersToRemove) {
        orders.remove(order);
      }
    }
  };

  public OrderExecuter() { timer.scheduleAtFixedRate(repeatedTask, delay, period); }

  public void placeOrder(Order order) {
    if (order.getDateTime().isAfter(LocalDateTime.now())) {
      orders.add(order);
      System.out.println("Added order " + order);
    }
  }

  public void cancelOrder(Order order) {
    if (orders.contains(order)) {
      orders.remove(order);
      System.out.println("Order " + order + " removed by customer");
    }
  }
}
