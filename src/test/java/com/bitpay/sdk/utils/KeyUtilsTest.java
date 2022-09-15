package com.bitpay.sdk.utils;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bitcoinj.core.ECKey;

import com.bitpay.sdk.util.KeyUtils;

import org.junit.jupiter.api.Test;

public class KeyUtilsTest {

  @Test
  public void testKeyUtils() {
    KeyUtils keyUtils = new KeyUtils();
    assertNotNull(keyUtils);
  }

  @Test
  public void testCreateEcKeyClass() {
    ECKey expectedClass = new ECKey();

    ECKey actualClass = KeyUtils.createEcKey();

    assertEquals(expectedClass.getClass(), actualClass.getClass());
  }

  @Test
  public void testCreateEcKeyNotNull() {
    ECKey actualKey = KeyUtils.createEcKey();

    assertNotNull(actualKey);
  }

  @Test
  public void testHexToBytes() {
    String hex = "0123456789abcdef";
    byte[] expectedBytes = { 1, 35, 69, 103, -119, -85, -51, -17 };
    
    byte[] actualBytes = KeyUtils.hexToBytes(hex);
    
    assertArrayEquals(expectedBytes, actualBytes);
  }

  @Test
  public void testBytesToHex() {
    byte[] bytes = { 1, 35, 69, 103, -119, -85, -51, -17 };
    String expectedHex = "0123456789abcdef";
    
    String actualHex = KeyUtils.bytesToHex(bytes);
    
    assertEquals(expectedHex, actualHex);
  }
}
