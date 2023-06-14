package eu.happycoders.demo;

import static eu.happycoders.demo.model.Repository.loadCustomerFromDatabase;
import static eu.happycoders.demo.model.Repository.loadInvoiceTemplateFromFile;
import static eu.happycoders.demo.model.Repository.loadOrderFromOrderService;
import static eu.happycoders.demo.util.SimpleLogger.log;

import eu.happycoders.demo.model.Customer;
import eu.happycoders.demo.model.Invoice;
import eu.happycoders.demo.model.Order;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorServiceDemo demo = new ExecutorServiceDemo();
    demo.createInvoice(10012, 61157, "en");
    demo.shutDown();
  }

  private final ExecutorService executor = Executors.newCachedThreadPool();

  Invoice createInvoice(int orderId, int customerId, String language)
      throws ExecutionException, InterruptedException {
    log("Submitting tasks");

    Future<Order> orderFuture = executor.submit(() -> loadOrderFromOrderService(orderId));

    Future<Customer> customerFuture = executor.submit(() -> loadCustomerFromDatabase(customerId));

    Future<String> invoiceTemplateFuture =
        executor.submit(() -> loadInvoiceTemplateFromFile(language));

    log("Waiting for order");
    Order order = orderFuture.get();

    log("Waiting for customer");
    Customer customer = customerFuture.get();

    log("Waiting for template");
    String invoiceTemplate = invoiceTemplateFuture.get();

    log("Generating invoice");
    Invoice invoice = Invoice.generate(order, customer, invoiceTemplate);

    log("Done");
    return invoice;
  }

  private void shutDown() {
    executor.shutdown();
  }
}
