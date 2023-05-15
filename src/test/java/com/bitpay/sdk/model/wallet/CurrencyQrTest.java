/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.model.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CurrencyQrTest {
  @Test
  public void it_should_get_collapsed() {
    Boolean expectedCollapsed = true;

    CurrencyQr currencyQr = new CurrencyQr();
    currencyQr.setCollapsed(expectedCollapsed);

    Boolean actualCollapsed = currencyQr.getCollapsed();

    assertEquals(expectedCollapsed, actualCollapsed);
  }

  @Test
  public void it_should_get_type() {
    String expectedType = "ADDRESS";

    CurrencyQr currencyQr = new CurrencyQr();
    currencyQr.setType(expectedType);

    String actualType = currencyQr.getType();

    assertEquals(expectedType, actualType);
  }
}
