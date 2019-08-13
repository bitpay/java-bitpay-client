package com.bitpay.model;

import java.lang.reflect.Field;

public class Currency {

    // Crypto
    public static final String BCH = "BCH";
    public static final String BTC = "BTC";
    public static final String ETH = "ETH";
    public static final String USDC = "USDC";
    public static final String GUSD = "GUSD";
    public static final String PAX = "PAX";

    // FIAT
    public static final String AED = "AED";
    public static final String AFN = "AFN";
    public static final String ALL = "ALL";
    public static final String AMD = "AMD";
    public static final String ANG = "ANG";
    public static final String AOA = "AOA";
    public static final String ARS = "ARS";
    public static final String AUD = "AUD";
    public static final String AWG = "AWG";
    public static final String AZN = "AZN";
    public static final String BAM = "BAM";
    public static final String BBD = "BBD";
    public static final String BDT = "BDT";
    public static final String BGN = "BGN";
    public static final String BHD = "BHD";
    public static final String BIF = "BIF";
    public static final String BMD = "BMD";
    public static final String BND = "BND";
    public static final String BOB = "BOB";
    public static final String BOV = "BOV";
    public static final String BRL = "BRL";
    public static final String BSD = "BSD";
    public static final String BTN = "BTN";
    public static final String BWP = "BWP";
    public static final String BYR = "BYR";
    public static final String BZD = "BZD";
    public static final String CAD = "CAD";
    public static final String CDF = "CDF";
    public static final String CHE = "CHE";
    public static final String CHF = "CHF";
    public static final String CHW = "CHW";
    public static final String CLF = "CLF";
    public static final String CLP = "CLP";
    public static final String CNY = "CNY";
    public static final String COP = "COP";
    public static final String COU = "COU";
    public static final String CRC = "CRC";
    public static final String CUC = "CUC";
    public static final String CUP = "CUP";
    public static final String CVE = "CVE";
    public static final String CZK = "CZK";
    public static final String DJF = "DJF";
    public static final String DKK = "DKK";
    public static final String DOP = "DOP";
    public static final String DZD = "DZD";
    public static final String EGP = "EGP";
    public static final String ERN = "ERN";
    public static final String ETB = "ETB";
    public static final String EUR = "EUR";
    public static final String FJD = "FJD";
    public static final String FKP = "FKP";
    public static final String GBP = "GBP";
    public static final String GEL = "GEL";
    public static final String GHS = "GHS";
    public static final String GIP = "GIP";
    public static final String GMD = "GMD";
    public static final String GNF = "GNF";
    public static final String GTQ = "GTQ";
    public static final String GYD = "GYD";
    public static final String HKD = "HKD";
    public static final String HNL = "HNL";
    public static final String HRK = "HRK";
    public static final String HTG = "HTG";
    public static final String HUF = "HUF";
    public static final String IDR = "IDR";
    public static final String ILS = "ILS";
    public static final String INR = "INR";
    public static final String IQD = "IQD";
    public static final String IRR = "IRR";
    public static final String ISK = "ISK";
    public static final String JMD = "JMD";
    public static final String JOD = "JOD";
    public static final String JPY = "JPY";
    public static final String KES = "KES";
    public static final String KGS = "KGS";
    public static final String KHR = "KHR";
    public static final String KMF = "KMF";
    public static final String KPW = "KPW";
    public static final String KRW = "KRW";
    public static final String KWD = "KWD";
    public static final String KYD = "KYD";
    public static final String KZT = "KZT";
    public static final String LAK = "LAK";
    public static final String LBP = "LBP";
    public static final String LKR = "LKR";
    public static final String LRD = "LRD";
    public static final String LSL = "LSL";
    public static final String LYD = "LYD";
    public static final String MAD = "MAD";
    public static final String MDL = "MDL";
    public static final String MGA = "MGA";
    public static final String MKD = "MKD";
    public static final String MMK = "MMK";
    public static final String MNT = "MNT";
    public static final String MOP = "MOP";
    public static final String MRU = "MRU";
    public static final String MUR = "MUR";
    public static final String MVR = "MVR";
    public static final String MWK = "MWK";
    public static final String MXN = "MXN";
    public static final String MXV = "MXV";
    public static final String MYR = "MYR";
    public static final String MZN = "MZN";
    public static final String NAD = "NAD";
    public static final String NGN = "NGN";
    public static final String NIO = "NIO";
    public static final String NOK = "NOK";
    public static final String NPR = "NPR";
    public static final String NZD = "NZD";
    public static final String OMR = "OMR";
    public static final String PAB = "PAB";
    public static final String PEN = "PEN";
    public static final String PGK = "PGK";
    public static final String PHP = "PHP";
    public static final String PKR = "PKR";
    public static final String PLN = "PLN";
    public static final String PYG = "PYG";
    public static final String QAR = "QAR";
    public static final String RON = "RON";
    public static final String RSD = "RSD";
    public static final String RUB = "RUB";
    public static final String RWF = "RWF";
    public static final String SAR = "SAR";
    public static final String SBD = "SBD";
    public static final String SCR = "SCR";
    public static final String SDG = "SDG";
    public static final String SEK = "SEK";
    public static final String SGD = "SGD";
    public static final String SHP = "SHP";
    public static final String SLL = "SLL";
    public static final String SOS = "SOS";
    public static final String SRD = "SRD";
    public static final String SSP = "SSP";
    public static final String STN = "STN";
    public static final String SVC = "SVC";
    public static final String SYP = "SYP";
    public static final String SZL = "SZL";
    public static final String THB = "THB";
    public static final String TJS = "TJS";
    public static final String TMT = "TMT";
    public static final String TND = "TND";
    public static final String TOP = "TOP";
    public static final String TRY = "TRY";
    public static final String TTD = "TTD";
    public static final String TWD = "TWD";
    public static final String TZS = "TZS";
    public static final String UAH = "UAH";
    public static final String UGX = "UGX";
    public static final String USD = "USD";
    public static final String USN = "USN";
    public static final String UYI = "UYI";
    public static final String UYU = "UYU";
    public static final String UZS = "UZS";
    public static final String VEF = "VEF";
    public static final String VND = "VND";
    public static final String VUV = "VUV";
    public static final String WST = "WST";
    public static final String XAF = "XAF";
    public static final String XCD = "XCD";
    public static final String XDR = "XDR";
    public static final String XOF = "XOF";
    public static final String XPF = "XPF";
    public static final String XSU = "XSU";
    public static final String XUA = "XUA";
    public static final String YER = "YER";
    public static final String ZAR = "ZAR";
    public static final String ZMW = "ZMW";
    public static final String ZWL = "ZWL";

    public static boolean isValid(String value) {
        try {
            Class<?> currencyClass = Currency.class;
            Field symbol = currencyClass.getField(value);
            return symbol != null;
        } catch (Exception ex) {
            return false;
        }
    }
}
