package com.bitpay.sdk.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CurrencyTest {
  @Test
  public void givenValidCurrencyString_thenReturnTrue() {
    String currency = "BTC";
    Boolean isValid = Currency.isValid(currency);
    assertTrue(isValid);
  }

  @Test
  public void givenInvalidCurrencyString_thenReturnFalse() {
    String currency = "FAKECURRENCY";
    Boolean isValid = Currency.isValid(currency);
    assertFalse(isValid);
  }
}
