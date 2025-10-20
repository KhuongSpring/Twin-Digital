package org.example.carservice.constant;

public class ErrorMessage {

  public static final String ERR_EXCEPTION_GENERAL = "exception.general";
  public static final String UNAUTHORIZED = "exception.unauthorized";
  public static final String FORBIDDEN = "exception.forbidden";

  // Validation errors
  public static final String INVALID_SOME_THING_FIELD = "invalid.general";
  public static final String INVALID_FORMAT_SOME_THING_FIELD = "invalid.general.format";
  public static final String INVALID_SOME_THING_FIELD_IS_REQUIRED = "invalid.general.required";
  public static final String NOT_BLANK_FIELD = "invalid.general.not-blank";

  public static class User {
    public static final String ERR_USER_NOT_FOUND = "exception.user.not.found";
    public static final String ERR_USER_ID_REQUIRED = "exception.user.id.required";

    private User() {
    }
  }

  public static class Car {
    public static final String ERR_CAR_MODEL_REQUIRED = "exception.car.model.required";
    public static final String ERR_CAR_MODEL_INVALID_FORMAT = "exception.car.model.invalid.format";
    public static final String ERR_STATIC_SPEC_SERVICE_UNAVAILABLE = "exception.car.static.spec.service.unavailable";
    public static final String ERR_DYNAMIC_SPEC_SERVICE_UNAVAILABLE = "exception.car.dynamic.spec.service.unavailable";
    public static final String ERR_ENTER_CAR_FAILED = "exception.car.enter.failed";
    public static final String ERR_ROLLBACK_FAILED = "exception.car.rollback.failed";

    private Car() {
    }
  }
}
