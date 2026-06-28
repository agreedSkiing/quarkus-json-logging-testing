package org.acme;

import io.quarkus.logging.json.runtime.JsonFormatter.JsonLogGenerator;
import io.quarkus.logging.json.runtime.JsonProvider;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.jboss.logmanager.ExtLogRecord;

public class StackTraceInMessageProvider implements JsonProvider {

  @Override
  public void writeTo(JsonLogGenerator generator, ExtLogRecord record)
    throws Exception {
    String message = record.getMessage();
    Throwable thrown = record.getThrown();
    if (thrown != null) {
      StringWriter sw = new StringWriter();
      thrown.printStackTrace(new PrintWriter(sw));
      message = message + "" + sw;
    }
    generator.add("message", message);
  }
}
