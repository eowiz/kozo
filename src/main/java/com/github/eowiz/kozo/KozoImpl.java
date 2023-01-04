package com.github.eowiz.kozo;

import static pebble.data.ExceptionalFunction.Try;

import com.github.eowiz.kozo.inferrer.SaasquatchInferrer;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pebble.data.Either;
import pebble.data.Option;
import pebble.io.Filez;
import picocli.CommandLine.ParseResult;

@Slf4j
@RequiredArgsConstructor
public class KozoImpl implements Kozo {

  private final ParseResult parseResult;

  private final Arguments arguments;

  private final PrintWriter outWriter;

  private final PrintWriter errWriter;

  @Override
  public void exec() throws IOException {
    // Command: help
    if (arguments.isHelpRequested()) {
      this.help();
      return;
    }

    final var inferrer = new SaasquatchInferrer();
    try {
      final var paths = Filez.listGlobs(Path.of("."), arguments.getGlobs());

      // noinspection DataFlowIssue
      final var jsons =
          paths
              .filter(Objects::nonNull)
              .map(Try(Files::readString))
              .map(Either::option)
              .flatMap(Option::stream);
      final var schema = inferrer.generateJsonSchema(jsons);
      log.debug("schema = {}", schema);

      if (arguments.getOutput() == null || arguments.getOutput().isBlank()) {
        System.out.println(schema);
        return;
      }

      Files.writeString(
          Path.of(arguments.getOutput()),
          schema,
          StandardOpenOption.CREATE,
          StandardOpenOption.WRITE);
    } catch (IOException e) {
      log.debug("IOException", e);

      this.errWriter.println("IOException is thrown");
    }
  }

  void help() throws IOException {
    this.outWriter.println(this.parseResult.commandSpec().usageMessage().toString());
  }
}
