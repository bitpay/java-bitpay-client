package com.bitpay.sdk.model.Rate;

import org.junit.Test;

import static org.junit.Assert.*;

public class RateTest {
  @Test
  public void givenSetRateFields_thenReturnGetRateFields() {
    String expectedName = "Bitcoin";
    String expectedCode = "BTC";
    Double expectedValue = 1.0;

    Rate rate = new Rate();
    rate.setName(expectedName);
    rate.setCode(expectedCode);
    rate.setValue(expectedValue);

    String actualName = rate.getName();
    String actualCode = rate.getCode();
    Double actualValue = rate.getValue();

    assertEquals(expectedName, actualName);
    assertEquals(expectedCode, actualCode);
    assertEquals(expectedValue, actualValue);
  }
}
