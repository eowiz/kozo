package com.github.eowiz.kozo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Arguments {

  @Parameters(paramLabel = "GLOB", arity = "1..*", description = "at least one GLOB")
  private String[] globs;

  @Option(names = {"-o", "--output"})
  private String output;

  @Builder.Default
  @Option(
      names = {"-h", "--help"},
      usageHelp = true,
      description = "display a help message")
  private boolean helpRequested = false;
}
