package com.bitpay.sdk.model.Rate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RateTest {
  @Test
  public void it_should_get_name() {
    String expectedName = "Bitcoin";

    Rate rate = new Rate();
    rate.setName(expectedName);

    String actualName = rate.getName();

    assertEquals(expectedName, actualName);
  }

  @Test
  public void it_should_get_code() {
    String expectedCode = "BTC";

    Rate rate = new Rate();
    rate.setCode(expectedCode);

    String actualCode = rate.getCode();

    assertEquals(expectedCode, actualCode);
  }

  @Test
  public void it_should_vet_value() {
    Double expectedValue = 1.0;

    Rate rate = new Rate();
    rate.setValue(expectedValue);

    Double actualValue = rate.getValue();

    assertEquals(expectedValue, actualValue);
  }
}
