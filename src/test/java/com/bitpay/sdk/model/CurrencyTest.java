package com.bitpay.sdk.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrencyTest {
  @Test
  public void it_should_validate() {
    String currency = "BTC";
    Boolean isValid = Currency.isValid(currency);
    
    assertTrue(isValid);
  }

  @Test
  public void it_should_not_validate() {
    String currency = "FAKECURRENCY";
    Boolean isValid = Currency.isValid(currency);
    
    assertFalse(isValid);
  }
}
