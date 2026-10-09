/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


import com.bitpay.sdk.exceptions.BitPayGenericException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;
import org.bitcoinj.crypto.ECKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class KeyUtilsTest {

  @Test
  public void it_should_not_be_null() {
    KeyUtils keyUtils = new KeyUtils();
    assertNotNull(keyUtils);
  }

  @Test
  public void it_should_be_correct_class() {
    ECKey expectedClass = new ECKey();

    ECKey actualClass = KeyUtils.createEcKey();

    assertEquals(expectedClass.getClass(), actualClass.getClass());
  }

  @Test
  public void it_should_be_not_null() {
    ECKey actualKey = KeyUtils.createEcKey();

    assertNotNull(actualKey);
  }

  @Test
  public void it_should_convert_hex_to_bytes() throws BitPayGenericException {
    String hex = "0123456789abcdef";
    byte[] expectedBytes = { 1, 35, 69, 103, -119, -85, -51, -17 };
    
    byte[] actualBytes = KeyUtils.hexToBytes(hex);
    
    assertArrayEquals(expectedBytes, actualBytes);
  }

  @Test
  public void it_should_reject_a_non_hex_key_digit() {
    assertThrows(BitPayGenericException.class, () -> KeyUtils.hexToBytes("0G"));
  }

  @Test
  public void it_should_convert_bytes_to_hex() {
    byte[] bytes = { 1, 35, 69, 103, -119, -85, -51, -17 };
    String expectedHex = "0123456789abcdef";
    
    String actualHex = KeyUtils.bytesToHex(bytes);
    
    assertEquals(expectedHex, actualHex);
  }

  @Test
  public void it_should_save_the_key_file_readable_only_by_its_owner(@TempDir Path dir) throws IOException {
    assumePosix();
    File file = dir.resolve("bitpay_private_test.key").toFile();
    ECKey key = KeyUtils.createEcKey();

    KeyUtils.privateKeyExists(file.getPath());
    KeyUtils.saveEcKey(key);

    assertEquals("rw-------", permissionsOf(file));
    assertEquals(key.getPrivateKeyAsHex(), KeyUtils.loadEcKey().getPrivateKeyAsHex());
  }

  @Test
  public void it_should_tighten_an_existing_key_file(@TempDir Path dir) throws IOException {
    assumePosix();
    File file = dir.resolve("bitpay_private_test.key").toFile();
    Files.write(file.toPath(), "old key".getBytes(StandardCharsets.UTF_8));
    Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rw-r--r--"));

    KeyUtils.privateKeyExists(file.getPath());
    KeyUtils.saveEcKey(KeyUtils.createEcKey());

    assertEquals("rw-------", permissionsOf(file));
  }

  @Test
  public void it_should_save_the_hex_key_file_readable_only_by_its_owner(@TempDir Path dir) throws IOException {
    assumePosix();
    File file = dir.resolve("bitpay_private_test.txt").toFile();
    ECKey key = KeyUtils.createEcKey();

    KeyUtils.privateKeyExists(file.getPath());
    KeyUtils.saveEcKeyAsHex(key);

    assertEquals("rw-------", permissionsOf(file));
    assertEquals(
        KeyUtils.loadEcKeyAsHex(key) + System.lineSeparator(),
        new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8)
    );
  }

  // POSIX permissions do not apply on Windows.
  private static void assumePosix() {
    assumeTrue(FileSystems.getDefault().supportedFileAttributeViews().contains("posix"));
  }

  private static String permissionsOf(File file) throws IOException {
    return PosixFilePermissions.toString(Files.getPosixFilePermissions(file.toPath()));
  }
}
