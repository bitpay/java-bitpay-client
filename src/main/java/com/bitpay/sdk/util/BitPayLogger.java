/*
 * Copyright (c) 2019 BitPay
 */
package com.bitpay.sdk.util;

/**
 * The type Bit pay logger.
 */
public class BitPayLogger {

    /**
     * The constant OFF.
     */
    public static final int OFF = 0;
    /**
     * The constant INFO.
     */
    public static final int INFO = 1;
    /**
     * The constant WARNING.
     */
    public static final int WARN = 2;
    /**
     * The constant ERROR.
     */
    public static final int ERR = 3;
    /**
     * The constant DEBUG.
     */
    public static final int DEBUG = 4;

    /**
     * The Level.
     */
    int _level = OFF;

    /**
     * Instantiates a new Bit pay logger.
     *
     * @param level the level
     */
    public BitPayLogger(int level) {
        _level = level;

        String strLevel;
        switch (level) {
            case 0:
                strLevel = "OFF";
                break;
            case 1:
                strLevel = "INFO";
                break;
            case 2:
                strLevel = "WARN";
                break;
            case 3:
                strLevel = "ERR";
                break;
            case 4:
            default:
                strLevel = "DEBUG";
                break;
        }
        System.out.println("Logging level set to: " + strLevel);
    }

    /**
     * Log info message.
     *
     * @param message the message
     */
    public void info(String message) {
        if (_level >= INFO) {
            System.out.println("INFO: " + message);
        }
    }

    /**
     * Log warning message.
     *
     * @param message the message
     */
    public void warn(String message) {
        if (_level >= WARN) {
            System.out.println("WARN: " + message);
        }
    }

    /**
     * Log error message.
     *
     * @param message the message
     */
    public void err(String message) {
        if (_level >= ERR) {
            System.out.println("ERR: " + message);
        }
    }

    /**
     * Log debug message.
     *
     * @param message the message
     */
    public void debug(String message) {
        if (_level >= DEBUG) {
            System.out.println("DEBUG: " + message);
        }
    }

}
