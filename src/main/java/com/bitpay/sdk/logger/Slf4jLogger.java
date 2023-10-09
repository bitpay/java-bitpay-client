/*
 * Copyright (c) 2019 BitPay.
 * All rights reserved.
 */

//package com.bitpay.sdk.logger;
//
//import org.slf4j.LoggerFactory;
//
//public class Slf4jLogger implements BitPayLogger {
//
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Slf4jLogger.class);
//
//    @Override
//    public void logRequest(String method, String endpoint, String json) {
//        logger.info("Request method: " + method + " Endpoint: " + endpoint + " Json: " + json);
//    }
//
//    @Override
//    public void logResponse(String method, String endpoint, String json) {
//        logger.info("Response method: " + method + " Endpoint: " + endpoint + " Json: " + json);
//    }
//
//    @Override
//    public void logError(String message) {
//        logger.error(message);
//    }
//}
