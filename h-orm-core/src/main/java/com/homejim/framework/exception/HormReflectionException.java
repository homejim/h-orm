package com.homejim.framework.exception;

/**
 * @author Clinton Begin
 */
public class HormReflectionException extends HormException {

  private static final long serialVersionUID = 7642570221267566591L;

  public HormReflectionException() {
    super();
  }

  public HormReflectionException(String message) {
    super(message);
  }

  public HormReflectionException(String message, Throwable cause) {
    super(message, cause);
  }

  public HormReflectionException(Throwable cause) {
    super(cause);
  }

}
