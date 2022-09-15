package com.bitpay.sdk.model.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CurrencyQrTest {
  @Test
  public void testGetCollapsed() {
    Boolean expectedCollapsed = true;

    CurrencyQr currencyQr = new CurrencyQr();
    currencyQr.setCollapsed(expectedCollapsed);

    Boolean actualCollapsed = currencyQr.getCollapsed();

    assertEquals(expectedCollapsed, actualCollapsed);
  }

  @Test
  public void testGetType() {
    String expectedType = "ADDRESS";

    CurrencyQr currencyQr = new CurrencyQr();
    currencyQr.setType(expectedType);

    String actualType = currencyQr.getType();

    assertEquals(expectedType, actualType);
  }
}
