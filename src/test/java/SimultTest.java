import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class SimultTest
{
  Logger logger;
  public final String url = "http://localhost:9000/api/score/scores";
  CountDownLatch latch;

  public SimultTest()
  {
    logger = Logger.getLogger("bs");
  }

  private void putUser()
  {
    Builder req = ClientBuilder.newClient()
        .target(url)
        .request()
        .header("Content-type", "application/json");
    Entity<String> ent = Entity.json("{\"password\":\"ok\"}");
    synchronized(this) {
      System.out.println("before request");
    }
    Response resp = req.get(); //post(ent);
    synchronized (this) {
      System.out.println(resp.getStatus() + " " + resp.readEntity(String.class));
    }
    resp.close();
    latch.countDown();
  }
  
  @Test
  public void simult()
  {
    int c = 60;
    latch = new CountDownLatch(c);
    for(int i = 1; i <= c; i++) {
      Thread th = new Thread(() -> {
        System.out.println("start");
        putUser();
      });
      th.start();
    }
    try {
      latch.await();
    } catch (Exception ign) {}
  }
}