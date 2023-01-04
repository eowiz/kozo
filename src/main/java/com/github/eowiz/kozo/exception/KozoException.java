package com.github.eowiz.kozo.exception;

public class KozoException extends RuntimeException {

  public KozoException() {}

  public KozoException(String message) {
    super(message);
  }

  public KozoException(String message, Throwable cause) {
    super(message, cause);
  }

  public KozoException(Throwable cause) {
    super(cause);
  }

  public KozoException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
