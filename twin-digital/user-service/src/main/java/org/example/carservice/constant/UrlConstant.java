package org.example.carservice.constant;

public class UrlConstant {

  public static class Car {
    private static final String PRE_FIX = "/car";

    public static final String ENTER_CAR = PRE_FIX + "/enter";
    public static final String GET_CAR_BY_USER_ID = PRE_FIX + "/{userId}";

    private Car() {
    }
  }
}
