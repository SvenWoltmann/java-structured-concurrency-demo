package eu.happycoders.demo;

import static eu.happycoders.demo.model.Repository.loadCustomerFromDatabase;
import static eu.happycoders.demo.model.Repository.loadInvoiceTemplateFromFile;
import static eu.happycoders.demo.model.Repository.loadOrderFromOrderService;
import static eu.happycoders.demo.util.SimpleLogger.log;

import eu.happycoders.demo.model.Customer;
import eu.happycoders.demo.model.Invoice;
import eu.happycoders.demo.model.Order;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class StructuredConcurrencyDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    StructuredConcurrencyDemo demo = new StructuredConcurrencyDemo();
    demo.createInvoice(10012, 61157, "en");
  }

  Invoice createInvoice(int orderId, int customerId, String language)
      throws InterruptedException, ExecutionException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      log("Forking tasks");
      Subtask<Order> orderSubtask = scope.fork(() -> loadOrderFromOrderService(orderId));

      Subtask<Customer> customerSubtask = scope.fork(() -> loadCustomerFromDatabase(customerId));

      Subtask<String> invoiceTemplateSubtask =
          scope.fork(() -> loadInvoiceTemplateFromFile(language));

      log("Waiting for all tasks to finish");
      scope.join();
      scope.throwIfFailed();

      log("Retrieving results");
      Order order = orderSubtask.get();
      Customer customer = customerSubtask.get();
      String invoiceTemplate = invoiceTemplateSubtask.get();

      log("Generating invoice");
      Invoice invoice = Invoice.generate(order, customer, invoiceTemplate);

      log("Done");
      return invoice;
    }
  }
}
