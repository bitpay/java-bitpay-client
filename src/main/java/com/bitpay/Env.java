package com.bitpay;

public class Env {
    public static final String Test = "Test";
    public static final String Prod = "Prod";
    public static final String TestUrl = "https://test.bitpay.com/";
    public static final String ProdUrl = "https://bitpay.com/";
    public static final String BitpayApiVersion = "2.0.0";
    public static final String TokensFolderPath = "tokens";
    public static final String BitpayPluginInfo = "BitPay_Java_Client_v2.4.1908";
    public class Tokens
    {
        public String POS;
        public String Merchant;
        public String Payout;
    }
}