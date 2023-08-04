package eu.happycoders.demo.util;

public final class SimpleLogger {

  private SimpleLogger() {}

  public static void log(String description) {
    System.out.printf("[%s] %s%n", Thread.currentThread(), description);
  }
}
