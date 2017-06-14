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
  
  @Test
  public void newClient()
  {
    logger.info("Calling with new client...");
    measure(3, () -> {
      Response resp = ClientBuilder.newClient()
      .target("http://localhost:8083")
      .request()
      .get();
      //String respText = resp.readEntity(String.class);
      //System.out.println("resp: " + respText);
    });
  }

  @Test
  public void sameClient()
  {
    logger.info("Calling with same client...");
    measure(3, () -> {
      Builder req = ClientBuilder.newClient()
          .target("http://localhost:8083")
          .request();
      Response resp = req.get();
      //String respText = resp.readEntity(String.class);
      //System.out.println("resp: " + respText);
    });
  }
}