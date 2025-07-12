package hexagon.shop.bootstrap;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

public class ApplicationLauncher {

  private UndertowJaxrsServer applicationServer;

  public static void main(String[] args) {
    new ApplicationLauncher().startOnDefaultPort();
  }

  private void startOnDefaultPort() {
    applicationServer = new UndertowJaxrsServer();
    startApplicationServer();
  }

  private void startApplicationServer() {
    applicationServer.start();
    applicationServer.deploy(RestEasyUndertowShopApplication.class);
  }
}
