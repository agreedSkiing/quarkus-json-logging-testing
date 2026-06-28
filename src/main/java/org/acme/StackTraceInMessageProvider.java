package org.acme;

import io.quarkus.logging.json.runtime.JsonFormatter.JsonLogGenerator;
import io.quarkus.logging.json.runtime.JsonProvider;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import org.jboss.logmanager.ExtLogRecord;

public class StackTraceInMessageProvider implements JsonProvider {

  @Override
  public void writeTo(JsonLogGenerator generator, ExtLogRecord record)
    throws Exception {
    String message = getMessage(record);
    Throwable thrown = record.getThrown();
    if (thrown != null) {
      StringWriter sw = new StringWriter();
      thrown.printStackTrace(new PrintWriter(sw));
      message = message + "" + sw;
    }
    generator.add("log", message);
  }

  /**
   * Reimplementation of getFormattedMessage since it is marked as deprecated
   * @param record
   * @return formatted message
   */
  private String getMessage(ExtLogRecord record) {
    final String msg = record.getMessage();
    if (msg == null) return "Empty message";
    final Object[] parameters = record.getParameters();
    if (parameters == null || parameters.length == 0) {
      return msg;
    }
    return switch (record.getFormatStyle()) {
      case PRINTF -> String.format(msg, parameters);
      case MESSAGE_FORMAT -> msg.indexOf('{') >= 0
        ? MessageFormat.format(msg, parameters)
        : msg;
      // should be unreachable
      default -> msg;
    };
  }
}
