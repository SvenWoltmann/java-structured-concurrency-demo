package eu.happycoders.demo.model;

import static eu.happycoders.demo.util.SimpleLogger.log;

import java.util.concurrent.ThreadLocalRandom;

public final class Repository {

  private Repository() {}

  public static Order loadOrderFromOrderService(int orderId) throws InterruptedException {
    log("Loading order");
    Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));

    if (ThreadLocalRandom.current().nextDouble() > 0.8) {
      log("Error loading order");
      throw new RuntimeException("Error loading order");
    }

    log("Finished loading order");
    return new Order();
  }

  public static Customer loadCustomerFromDatabase(int customerId) throws InterruptedException {
    log("Loading customer");
    Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));

    if (ThreadLocalRandom.current().nextDouble() > 0.8) {
      log("Error loading customer");
      throw new RuntimeException("Error loading customer");
    }

    log("Finished loading customer");
    return new Customer();
  }

  public static String loadInvoiceTemplateFromFile(String language) throws InterruptedException {
    log("Loading template");
    Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));

    if (ThreadLocalRandom.current().nextDouble() > 0.8) {
      log("Error loading template");
      throw new RuntimeException("Error loading template");
    }

    log("Finished loading template");
    return "TEMPLATE";
  }
}
