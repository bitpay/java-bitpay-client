package com.bitpay.sdk.utils;

import org.bitcoinj.core.ECKey;
import org.junit.Test;

import com.bitpay.sdk.util.KeyUtils;

import static org.junit.Assert.*;

public class KeyUtilsTest {

  @Test
  public void whenCreateEcKey_thenReturnKey() {
    ECKey actualKey = KeyUtils.createEcKey();
    assertNotNull(actualKey);
  }

  @Test
  public void givenHex_whenConvertToBytes_thenReturnBytes() {
    String hex = "0123456789ABCDEF";
    byte[] expectedBytes = { 1, 35, 69, 103, -119, -85, -51, -17 };
    byte[] actualBytes = KeyUtils.hexToBytes(hex);
    assertArrayEquals(expectedBytes, actualBytes);
  }
}
