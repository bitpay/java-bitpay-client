package com.bitpay.sdk.utils;

import org.bitcoinj.core.ECKey;
import org.junit.Test;

import com.bitpay.sdk.util.KeyUtils;

import static org.junit.Assert.*;

public class KeyUtilsTest {

  @Test
  public void whenInstantiated_thenReturnKeyUtils() {
    KeyUtils keyUtils = new KeyUtils();
    assertNotNull(keyUtils);
  }

  @Test
  public void whenECKeyCreated_thenReturnBitcoinJECKey() {
    ECKey expectedClass = new ECKey();
    ECKey actualClass = KeyUtils.createEcKey();
    assertEquals(expectedClass.getClass(), actualClass.getClass());
  }

  @Test
  public void whenCreateEcKey_thenReturnKey() {
    ECKey actualKey = KeyUtils.createEcKey();
    assertNotNull(actualKey);
  }

  @Test
  public void givenHex_whenConvertToBytes_thenReturnBytes() {
    String hex = "0123456789abcdef";
    byte[] expectedBytes = { 1, 35, 69, 103, -119, -85, -51, -17 };
    byte[] actualBytes = KeyUtils.hexToBytes(hex);
    assertArrayEquals(expectedBytes, actualBytes);
  }

  @Test
  public void givenBytes_whenConvertToHex_thenReturnHex() {
    byte[] bytes = { 1, 35, 69, 103, -119, -85, -51, -17 };
    String expectedHex = "0123456789abcdef";
    String actualHex = KeyUtils.bytesToHex(bytes);
    assertEquals(expectedHex, actualHex);
  }
}
