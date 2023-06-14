package eu.happycoders.demo.model;

import static eu.happycoders.demo.util.SimpleLogger.log;

import eu.happycoders.demo.StructuredConcurrencyWithScopedValueDemo;
import java.util.concurrent.ThreadLocalRandom;

public final class RepositoryWithScopedValue {

  private RepositoryWithScopedValue() {}

  public static Order loadOrderFromOrderService(int orderId) throws InterruptedException {
    String invoiceNumber = StructuredConcurrencyWithScopedValueDemo.INVOICE_NUMBER.get();
    log("Loading order for invoice " + invoiceNumber);
    Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
    log("Finished loading order");
    return new Order();
  }

  public static Customer loadCustomerFromDatabase(int customerId) throws InterruptedException {
    String invoiceNumber = StructuredConcurrencyWithScopedValueDemo.INVOICE_NUMBER.get();
    log("Loading customer for invoice " + invoiceNumber);
    Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
    log("Finished loading customer");
    return new Customer();
  }

  public static String loadInvoiceTemplateFromFile(String language) throws InterruptedException {
    String invoiceNumber = StructuredConcurrencyWithScopedValueDemo.INVOICE_NUMBER.get();
    log("Loading template for invoice " + invoiceNumber);
    Thread.sleep(ThreadLocalRandom.current().nextLong(500, 1000));
    log("Finished loading template");
    return "TEMPLATE";
  }
}
