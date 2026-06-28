package org.acme;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class Scheduler {

  private AtomicInteger counter = new AtomicInteger();

  public int get() {
    return counter.get();
  }

  @Scheduled(every = "10s")
  void increment() {
    counter.incrementAndGet();
  }

  @Scheduled(cron = "0 15 10 * * ?")
  void cronJob(ScheduledExecution execution) {
    counter.incrementAndGet();
    Log.fatalf("Message counters %s", execution.getScheduledFireTime());
  }

  @Scheduled(every = "15s")
  void cronJobWithExpressionInConfig() {
    counter.incrementAndGet();
    System.out.println("Cron expression configured in application.properties");
    var cause = new IllegalStateException("Yes me be illegal");
    throw new RuntimeException("FAILURE", cause);
  }
}
