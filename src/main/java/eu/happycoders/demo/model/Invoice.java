package eu.happycoders.demo.model;

public record Invoice() {
  public static Invoice generate(Order order, Customer customer, String invoiceTemplate) {
    return new Invoice();
  }
}
