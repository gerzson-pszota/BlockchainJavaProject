package app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;

import static app.MainChain.difficulty;

public class WebServer {

  private static boolean blockchainValid = false;

  public static void main(String[] args) throws Exception {
    startServer();
  }

  static void startServer() throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
    server.createContext("/", new BlockchainHandler());

    // register the app.RunCodeHandler to handle the /runcode endpoint
    server.createContext("/runcode", new RunCodeHandler());

    server.setExecutor(null); // creates a default executor
    server.start();
    System.out.println("Server is running on port 8080...");
  }

  static class BlockchainHandler implements com.sun.net.httpserver.HttpHandler {
    @Override
    public void handle(com.sun.net.httpserver.HttpExchange exchange) throws IOException {
      String response = generateResponse();
      exchange.sendResponseHeaders(200, response.getBytes().length);
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }

  private static String generateResponse() {
    StringBuilder htmlBuilder = new StringBuilder();
    htmlBuilder.append("<html><body><h1>Blockchain</h1>");
    htmlBuilder.append("<p>Mining difficulty is set to <strong>" + difficulty + "</strong> for quick testing. </p>");

    htmlBuilder.append("<p>(At the time of coding, Litecoins difficulty is around 32.67 Million)</p>");

    // add Run Code button
    htmlBuilder.append("<form action=\"/runcode\" method=\"post\">");
    htmlBuilder.append("<input type=\"submit\" value=\"Run Code\">");
    htmlBuilder.append("</form>");

    // display blockchain data
    htmlBuilder.append("<pre>");
    String blockchainJson =
        new GsonBuilder().setPrettyPrinting().create().toJson(MainChain.blockchain);
    htmlBuilder.append(blockchainJson);
    htmlBuilder.append("</pre>");

    // display isBlockchainValid() result if available
    if (blockchainValid) {
      htmlBuilder.append("<p>Blockchain is Valid: ").append(blockchainValid).append("</p>");
    }

    htmlBuilder.append("</body></html>");
    return htmlBuilder.toString();
  }

  // method to update blockchain validity status
  static void setBlockchainValid(boolean isValid) {
    blockchainValid = isValid;
  }
}
