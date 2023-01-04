package com.github.eowiz.kozo.inferrer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.eowiz.kozo.exception.KozoException;
import com.saasquatch.jsonschemainferrer.SpecVersion;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder(toBuilder = true)
public class SaasquatchInferrer implements JsonSchemaInferrer {

  private final ObjectMapper mapper;

  private final com.saasquatch.jsonschemainferrer.JsonSchemaInferrer inferrer;

  public SaasquatchInferrer() {
    this(
        new ObjectMapper(),
        com.saasquatch.jsonschemainferrer.JsonSchemaInferrer.newBuilder()
            .setSpecVersion(SpecVersion.DRAFT_07)
            .build());
  }

  @Override
  public String generateJsonSchema(Stream<String> jsonStream) {
    final var jsonNodes =
        jsonStream
            .map(
                it -> {
                  try {
                    return this.mapper.readTree(it);
                  } catch (JsonProcessingException e) {
                    throw new KozoException(e);
                  }
                })
            .toList();

    final var schema = this.inferrer.inferForSamples(jsonNodes);

    return schema.toPrettyString();
  }
}
