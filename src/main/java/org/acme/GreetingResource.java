package org.acme;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String hello() {
    Log.fatalf("Yes hello from quarkus REST");
    return "Hello from Quarkus REST";
  }

  @GET
  @Path("/error")
  @Produces(MediaType.TEXT_PLAIN)
  public String hello_error() {
    var cause = new IllegalStateException("Yes me be illegal");
    throw new RuntimeException("FAILURE", cause);
  }
}
