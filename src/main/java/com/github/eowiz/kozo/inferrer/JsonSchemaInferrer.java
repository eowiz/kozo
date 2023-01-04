package com.github.eowiz.kozo.inferrer;

import java.util.stream.Stream;

public interface JsonSchemaInferrer {

  String generateJsonSchema(Stream<String> jsonStream);
}
