import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class Tests
{
  Logger logger;
  public final String url = "http://localhost:8080/jpkok/test";

  public Tests()
  {
    logger = Logger.getLogger("bs");
  }

  private void measure(int cTimes, Runnable r)
  {
    long t0 = System.currentTimeMillis();
    IntStream.rangeClosed(1, cTimes).forEach(i -> {
      r.run();
    });
    long t = System.currentTimeMillis() - t0;
    System.out.println("average: " + (double)t / cTimes);
  }
  
  //@Test
  public void newClient()
  {
    logger.info("Calling with new client...");
    Runnable fun = () -> {
      Response resp = ClientBuilder.newClient()
      .target(url)
      .request()
      .get();
      // Must read the response or close to enable keep-alive.
      //String respText = resp.readEntity(String.class);
      //System.out.println("resp: " + respText);
      resp.close();
    };
    measure(1, fun);
    measure(20, fun);
  }

  @Test
  public void sameClient()
  {
    logger.info("Calling with same client...");
    long t0 = System.currentTimeMillis();
    Builder req = ClientBuilder.newClient()
        .target(url)
        .request();
    long t = System.currentTimeMillis() - t0;
    System.out.println("request built: " + t);
    Runnable fun = () -> {
      Response resp = req.get();
       // Must read the response or close to enable keep-alive.
      //String respText = resp.readEntity(String.class);
      //System.out.println("resp: " + respText);
      resp.close();
    }; 
    measure(1, fun);
    measure(50, fun);
  }
}