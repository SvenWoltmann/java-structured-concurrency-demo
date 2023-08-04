package eu.happycoders.demo;

import static eu.happycoders.demo.util.SimpleLogger.log;

import eu.happycoders.demo.model.WeatherApiClient;
import eu.happycoders.demo.model.WeatherInfo;
import java.util.concurrent.ExecutionException;

public class CombinedWeatherInfoDemo {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CombinedWeatherInfoDemo demo = new CombinedWeatherInfoDemo();
    WeatherInfo weatherInfo = demo.loadCombinedWeatherInfo();
    log("Result: " + weatherInfo);
  }

  WeatherInfo loadCombinedWeatherInfo() throws InterruptedException, ExecutionException {
    try (var scope = new CombinedWeatherInfoTaskScope()) {
      log("Forking tasks");
      scope.fork(() -> WeatherApiClient.loadWeatherInfo("provider-a"));
      scope.fork(() -> WeatherApiClient.loadWeatherInfo("provider-b"));
      scope.fork(() -> WeatherApiClient.loadWeatherInfo("provider-c"));
      scope.fork(() -> WeatherApiClient.loadWeatherInfo("provider-d"));

      log("Waiting for result combined from first two responses");
      scope.join();

      log("Retrieving result");
      WeatherInfo result = scope.result();

      log("Done");
      return result;
    }
  }
}
