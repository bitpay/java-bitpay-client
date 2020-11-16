package com.bitpay.sdk;

public class Env {
    public static final String Test = "Test";
    public static final String Prod = "Prod";
    public static final String TestUrl = "https://test.bitpay.com/";
    public static final String ProdUrl = "https://bitpay.com/";
    public static final String BitpayApiVersion = "2.0.0";
    public static final String BitpayPluginInfo = "BitPay_Java_Client_v5.2.2011";
    public static final String BitpayApiFrame = "custom";
    public static final String BitpayApiFrameVersion = "2.0.0";

    public static class Tokens {
        public String pos;
        public String merchant;
        public String payroll;
    }
}