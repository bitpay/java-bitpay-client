package com.bitpay.sdk.model.Wallet;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CurrencyQrTest {
  @Test
  public void givenSetCurrencyQrFields_thenReturnGetPolicyFields() {
    CurrencyQr currencyQr = new CurrencyQr();

    Boolean expectedCollapsed = true;
    String expectedType = "ADDRESS";

    currencyQr.setCollapsed(expectedCollapsed);
    currencyQr.setType(expectedType);

    Boolean actualCollapsed = currencyQr.getCollapsed();
    String actualType = currencyQr.getType();

    assertEquals(expectedCollapsed, actualCollapsed);
    assertEquals(expectedType, actualType);
  }
}
