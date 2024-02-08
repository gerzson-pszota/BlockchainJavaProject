import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class RunCodeHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    // execute the main method of MainChain
    try {
      MainChain.main(null);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // check if the blockchain is valid
    boolean isBlockchainValid = MainChain.isChainValid();

    // update the blockchain validity status in WebServer
    WebServer.setBlockchainValid(isBlockchainValid);

    // redirect back to the /blockchain page after running the code
    exchange.getResponseHeaders().set("Location", "/blockchain");
    exchange.sendResponseHeaders(302, -1);
  }
}
