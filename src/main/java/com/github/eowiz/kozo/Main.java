package com.github.eowiz.kozo;

import java.io.IOException;
import java.io.PrintWriter;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import picocli.CommandLine.MissingParameterException;
import picocli.CommandLine.ParseResult;

@Slf4j
public class Main {

  public static void main(String[] args) throws IOException {
    final var outWriter = new PrintWriter(System.out);
    final var errWriter = new PrintWriter(System.err);

    try {
      // Parse command-line arguments
      final var arguments = new Arguments();
      final ParseResult parseResult;
      try {
        parseResult = new CommandLine(arguments).parseArgs(args);
      } catch (MissingParameterException e) {
        errWriter.println(e.getMessage());
        log.debug("Failed to parse arguments", e);

        return;
      }

      // Check parse result
      log.debug("Parse Result: kozo = {}", arguments);
      if (!parseResult.errors().isEmpty()) {
        parseResult.errors().forEach(e -> log.debug("Parse Exception", e));
        return;
      }

      // Execute command
      final var kozo = new KozoImpl(parseResult, arguments, outWriter, errWriter);
      kozo.exec();
    } finally {
      outWriter.flush();
      errWriter.flush();
    }
  }
}
