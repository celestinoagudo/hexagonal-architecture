package hexagon.shop.bootstrap;

import hexagon.shop.SpringAppConfiguration;
import org.springframework.boot.SpringApplication;

public class ApplicationLauncher {
  public static void main(String[] args) {
    SpringApplication.run(SpringAppConfiguration.class, args);
  }
}
