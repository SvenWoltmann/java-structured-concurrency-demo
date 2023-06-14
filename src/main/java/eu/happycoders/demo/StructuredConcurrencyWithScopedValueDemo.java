package eu.happycoders.demo;

import static eu.happycoders.demo.model.RepositoryWithScopedValue.loadCustomerFromDatabase;
import static eu.happycoders.demo.model.RepositoryWithScopedValue.loadInvoiceTemplateFromFile;
import static eu.happycoders.demo.model.RepositoryWithScopedValue.loadOrderFromOrderService;
import static eu.happycoders.demo.util.SimpleLogger.log;

import eu.happycoders.demo.model.Customer;
import eu.happycoders.demo.model.Invoice;
import eu.happycoders.demo.model.Order;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class StructuredConcurrencyWithScopedValueDemo {

  public static void main(String[] args) throws Exception {
    StructuredConcurrencyWithScopedValueDemo demo = new StructuredConcurrencyWithScopedValueDemo();
    demo.createInvoice(10012, 61157, "en");
  }

  public static final ScopedValue<String> INVOICE_NUMBER = ScopedValue.newInstance();

  Invoice createInvoice(int orderId, int customerId, String language) throws Exception {
    return ScopedValue.where(INVOICE_NUMBER, "2023-437")
        .call(
            () -> {
              try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                log("Forking tasks");
                Subtask<Order> orderFuture = scope.fork(() -> loadOrderFromOrderService(orderId));

                Subtask<Customer> customerFuture =
                    scope.fork(() -> loadCustomerFromDatabase(customerId));

                Subtask<String> invoiceTemplateFuture =
                    scope.fork(() -> loadInvoiceTemplateFromFile(language));

                log("Waiting for all tasks to finish");
                scope.join();
                scope.throwIfFailed();

                log("Retrieving results");
                Order order = orderFuture.get();
                Customer customer = customerFuture.get();
                String invoiceTemplate = invoiceTemplateFuture.get();

                log("Generating invoice");
                Invoice invoice = Invoice.generate(order, customer, invoiceTemplate);

                log("Done");
                return invoice;
              }
            });
  }
}
