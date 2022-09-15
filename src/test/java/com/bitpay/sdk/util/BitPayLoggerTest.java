package com.bitpay.sdk.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class BitPayLoggerTest {
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @BeforeEach
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  public void givenLogLevelOff_whenInvokeLogger_thenDoNotOutputMessage() {  
    new BitPayLogger(BitPayLogger.OFF);
    String expectedConstructorMessage = "Logging level set to: OFF";
    assertEquals(expectedConstructorMessage, outputStreamCaptor.toString().trim());
  }

  @Test
  public void givenLogLevelInfo_whenInvokeLogger_thenOutputMessage() {  
    BitPayLogger logger = new BitPayLogger(BitPayLogger.INFO);
    String expectedConstructorMessage = "Logging level set to: INFO";
    String expectedInfoMessage = "INFO: This is a test";
    logger.info("This is a test");
    assertEquals(expectedConstructorMessage + "\n" + expectedInfoMessage, outputStreamCaptor.toString().trim());

    BitPayLogger logger2 = new BitPayLogger(BitPayLogger.OFF);
    logger2.info("This is a test");
  }

  @Test
  public void givenLogLevelWarn_whenInvokeLogger_thenOutputMessage() {  
    BitPayLogger logger = new BitPayLogger(BitPayLogger.WARN);
    String expectedConstructorMessage = "Logging level set to: WARN";
    String expectedInfoMessage = "WARN: This is a test";
    logger.warn("This is a test");
    assertEquals(expectedConstructorMessage + "\n" + expectedInfoMessage, outputStreamCaptor.toString().trim());
 
    BitPayLogger logger2 = new BitPayLogger(BitPayLogger.INFO);
    logger2.warn("This is a test");
  }

  @Test
  public void givenLogLevelErr_whenInvokeLogger_thenOutputMessage() {  
    BitPayLogger logger = new BitPayLogger(BitPayLogger.ERR);
    String expectedConstructorMessage = "Logging level set to: ERR";
    String expectedInfoMessage = "ERR: This is a test";
    logger.err("This is a test");
    assertEquals(expectedConstructorMessage + "\n" + expectedInfoMessage, outputStreamCaptor.toString().trim());

    BitPayLogger logger2 = new BitPayLogger(BitPayLogger.INFO);
    logger2.err("This is a test");
  }

  @Test
  public void givenLogLevelDebug_whenInvokeLogger_thenOutputMessage() {  
    BitPayLogger logger = new BitPayLogger(BitPayLogger.DEBUG);
    String expectedConstructorMessage = "Logging level set to: DEBUG";
    String expectedInfoMessage = "DEBUG: This is a test";
    logger.debug("This is a test");
    assertEquals(expectedConstructorMessage + "\n" + expectedInfoMessage, outputStreamCaptor.toString().trim());
  }

  @AfterEach
  public void tearDown() {
      System.setOut(standardOut);
  }
}
