/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.model;

import java.lang.reflect.Field;

/**
 * The type Currency.
 */
public class Currency {

    /**
     * The constant Bitcoin Cash (BCH).
     */
    // Crypto
    public static final String BCH = "BCH";
    /**
     * The constant Bitcoin (BTC).
     */
    public static final String BTC = "BTC";
    /**
     * The constant Ethereum (ETH).
     */
    public static final String ETH = "ETH";
    /**
     * The constant USD Coin (USDC).
     */
    public static final String USDC = "USDC";
    /**
     * The constant Gemini Dollar (GUSD).
     */
    public static final String GUSD = "GUSD";
    /**
     * The constant BUSD (Binance USD).
     */
    public static final String BUSD = "BUSD";
    /**
     * The constant Pax Dollar (PAX).
     */
    public static final String PAX = "PAX";
    /**
     * The constant XRP.
     */
    public static final String XRP = "XRP";
    /**
     * The constant Dogecoin (DOGE).
     */
    public static final String DOGE = "DOGE";
    /**
     * The constant Litecoin (LTC).
     */
    public static final String LTC = "LTC";

    /**
     * The constant Dirham (AED).
     */
    // FIAT
    public static final String AED = "AED";
    /**
     * The constant AFN.
     */
    public static final String AFN = "AFN";
    /**
     * The constant ALL.
     */
    public static final String ALL = "ALL";
    /**
     * The constant AMD.
     */
    public static final String AMD = "AMD";
    /**
     * The constant ANG.
     */
    public static final String ANG = "ANG";
    /**
     * The constant AOA.
     */
    public static final String AOA = "AOA";
    /**
     * The constant ARS.
     */
    public static final String ARS = "ARS";
    /**
     * The constant AUD.
     */
    public static final String AUD = "AUD";
    /**
     * The constant AWG.
     */
    public static final String AWG = "AWG";
    /**
     * The constant AZN.
     */
    public static final String AZN = "AZN";
    /**
     * The constant BAM.
     */
    public static final String BAM = "BAM";
    /**
     * The constant BBD.
     */
    public static final String BBD = "BBD";
    /**
     * The constant BDT.
     */
    public static final String BDT = "BDT";
    /**
     * The constant BGN.
     */
    public static final String BGN = "BGN";
    /**
     * The constant BHD.
     */
    public static final String BHD = "BHD";
    /**
     * The constant BIF.
     */
    public static final String BIF = "BIF";
    /**
     * The constant BMD.
     */
    public static final String BMD = "BMD";
    /**
     * The constant BND.
     */
    public static final String BND = "BND";
    /**
     * The constant BOB.
     */
    public static final String BOB = "BOB";
    /**
     * The constant BOV.
     */
    public static final String BOV = "BOV";
    /**
     * The constant BRL.
     */
    public static final String BRL = "BRL";
    /**
     * The constant BSD.
     */
    public static final String BSD = "BSD";
    /**
     * The constant BTN.
     */
    public static final String BTN = "BTN";
    /**
     * The constant BWP.
     */
    public static final String BWP = "BWP";
    /**
     * The constant BYR.
     */
    public static final String BYR = "BYR";
    /**
     * The constant BZD.
     */
    public static final String BZD = "BZD";
    /**
     * The constant CAD.
     */
    public static final String CAD = "CAD";
    /**
     * The constant CDF.
     */
    public static final String CDF = "CDF";
    /**
     * The constant CHE.
     */
    public static final String CHE = "CHE";
    /**
     * The constant CHF.
     */
    public static final String CHF = "CHF";
    /**
     * The constant CHW.
     */
    public static final String CHW = "CHW";
    /**
     * The constant CLF.
     */
    public static final String CLF = "CLF";
    /**
     * The constant CLP.
     */
    public static final String CLP = "CLP";
    /**
     * The constant CNY.
     */
    public static final String CNY = "CNY";
    /**
     * The constant COP.
     */
    public static final String COP = "COP";
    /**
     * The constant COU.
     */
    public static final String COU = "COU";
    /**
     * The constant CRC.
     */
    public static final String CRC = "CRC";
    /**
     * The constant CUC.
     */
    public static final String CUC = "CUC";
    /**
     * The constant CUP.
     */
    public static final String CUP = "CUP";
    /**
     * The constant CVE.
     */
    public static final String CVE = "CVE";
    /**
     * The constant CZK.
     */
    public static final String CZK = "CZK";
    /**
     * The constant DJF.
     */
    public static final String DJF = "DJF";
    /**
     * The constant DKK.
     */
    public static final String DKK = "DKK";
    /**
     * The constant DOP.
     */
    public static final String DOP = "DOP";
    /**
     * The constant DZD.
     */
    public static final String DZD = "DZD";
    /**
     * The constant EGP.
     */
    public static final String EGP = "EGP";
    /**
     * The constant ERN.
     */
    public static final String ERN = "ERN";
    /**
     * The constant ETB.
     */
    public static final String ETB = "ETB";
    /**
     * The constant EUR.
     */
    public static final String EUR = "EUR";
    /**
     * The constant FJD.
     */
    public static final String FJD = "FJD";
    /**
     * The constant FKP.
     */
    public static final String FKP = "FKP";
    /**
     * The constant GBP.
     */
    public static final String GBP = "GBP";
    /**
     * The constant GEL.
     */
    public static final String GEL = "GEL";
    /**
     * The constant GHS.
     */
    public static final String GHS = "GHS";
    /**
     * The constant GIP.
     */
    public static final String GIP = "GIP";
    /**
     * The constant GMD.
     */
    public static final String GMD = "GMD";
    /**
     * The constant GNF.
     */
    public static final String GNF = "GNF";
    /**
     * The constant GTQ.
     */
    public static final String GTQ = "GTQ";
    /**
     * The constant GYD.
     */
    public static final String GYD = "GYD";
    /**
     * The constant HKD.
     */
    public static final String HKD = "HKD";
    /**
     * The constant HNL.
     */
    public static final String HNL = "HNL";
    /**
     * The constant HRK.
     */
    public static final String HRK = "HRK";
    /**
     * The constant HTG.
     */
    public static final String HTG = "HTG";
    /**
     * The constant HUF.
     */
    public static final String HUF = "HUF";
    /**
     * The constant IDR.
     */
    public static final String IDR = "IDR";
    /**
     * The constant ILS.
     */
    public static final String ILS = "ILS";
    /**
     * The constant INR.
     */
    public static final String INR = "INR";
    /**
     * The constant IQD.
     */
    public static final String IQD = "IQD";
    /**
     * The constant IRR.
     */
    public static final String IRR = "IRR";
    /**
     * The constant ISK.
     */
    public static final String ISK = "ISK";
    /**
     * The constant JMD.
     */
    public static final String JMD = "JMD";
    /**
     * The constant JOD.
     */
    public static final String JOD = "JOD";
    /**
     * The constant JPY.
     */
    public static final String JPY = "JPY";
    /**
     * The constant KES.
     */
    public static final String KES = "KES";
    /**
     * The constant KGS.
     */
    public static final String KGS = "KGS";
    /**
     * The constant KHR.
     */
    public static final String KHR = "KHR";
    /**
     * The constant KMF.
     */
    public static final String KMF = "KMF";
    /**
     * The constant KPW.
     */
    public static final String KPW = "KPW";
    /**
     * The constant KRW.
     */
    public static final String KRW = "KRW";
    /**
     * The constant KWD.
     */
    public static final String KWD = "KWD";
    /**
     * The constant KYD.
     */
    public static final String KYD = "KYD";
    /**
     * The constant KZT.
     */
    public static final String KZT = "KZT";
    /**
     * The constant LAK.
     */
    public static final String LAK = "LAK";
    /**
     * The constant LBP.
     */
    public static final String LBP = "LBP";
    /**
     * The constant LKR.
     */
    public static final String LKR = "LKR";
    /**
     * The constant LRD.
     */
    public static final String LRD = "LRD";
    /**
     * The constant LSL.
     */
    public static final String LSL = "LSL";
    /**
     * The constant LYD.
     */
    public static final String LYD = "LYD";
    /**
     * The constant MAD.
     */
    public static final String MAD = "MAD";
    /**
     * The constant MDL.
     */
    public static final String MDL = "MDL";
    /**
     * The constant MGA.
     */
    public static final String MGA = "MGA";
    /**
     * The constant MKD.
     */
    public static final String MKD = "MKD";
    /**
     * The constant MMK.
     */
    public static final String MMK = "MMK";
    /**
     * The constant MNT.
     */
    public static final String MNT = "MNT";
    /**
     * The constant MOP.
     */
    public static final String MOP = "MOP";
    /**
     * The constant MRU.
     */
    public static final String MRU = "MRU";
    /**
     * The constant MUR.
     */
    public static final String MUR = "MUR";
    /**
     * The constant MVR.
     */
    public static final String MVR = "MVR";
    /**
     * The constant MWK.
     */
    public static final String MWK = "MWK";
    /**
     * The constant MXN.
     */
    public static final String MXN = "MXN";
    /**
     * The constant MXV.
     */
    public static final String MXV = "MXV";
    /**
     * The constant MYR.
     */
    public static final String MYR = "MYR";
    /**
     * The constant MZN.
     */
    public static final String MZN = "MZN";
    /**
     * The constant NAD.
     */
    public static final String NAD = "NAD";
    /**
     * The constant NGN.
     */
    public static final String NGN = "NGN";
    /**
     * The constant NIO.
     */
    public static final String NIO = "NIO";
    /**
     * The constant NOK.
     */
    public static final String NOK = "NOK";
    /**
     * The constant NPR.
     */
    public static final String NPR = "NPR";
    /**
     * The constant NZD.
     */
    public static final String NZD = "NZD";
    /**
     * The constant OMR.
     */
    public static final String OMR = "OMR";
    /**
     * The constant PAB.
     */
    public static final String PAB = "PAB";
    /**
     * The constant PEN.
     */
    public static final String PEN = "PEN";
    /**
     * The constant PGK.
     */
    public static final String PGK = "PGK";
    /**
     * The constant PHP.
     */
    public static final String PHP = "PHP";
    /**
     * The constant PKR.
     */
    public static final String PKR = "PKR";
    /**
     * The constant PLN.
     */
    public static final String PLN = "PLN";
    /**
     * The constant PYG.
     */
    public static final String PYG = "PYG";
    /**
     * The constant QAR.
     */
    public static final String QAR = "QAR";
    /**
     * The constant RON.
     */
    public static final String RON = "RON";
    /**
     * The constant RSD.
     */
    public static final String RSD = "RSD";
    /**
     * The constant RUB.
     */
    public static final String RUB = "RUB";
    /**
     * The constant RWF.
     */
    public static final String RWF = "RWF";
    /**
     * The constant SAR.
     */
    public static final String SAR = "SAR";
    /**
     * The constant SBD.
     */
    public static final String SBD = "SBD";
    /**
     * The constant SCR.
     */
    public static final String SCR = "SCR";
    /**
     * The constant SDG.
     */
    public static final String SDG = "SDG";
    /**
     * The constant SEK.
     */
    public static final String SEK = "SEK";
    /**
     * The constant SGD.
     */
    public static final String SGD = "SGD";
    /**
     * The constant SHP.
     */
    public static final String SHP = "SHP";
    /**
     * The constant SLL.
     */
    public static final String SLL = "SLL";
    /**
     * The constant SOS.
     */
    public static final String SOS = "SOS";
    /**
     * The constant SRD.
     */
    public static final String SRD = "SRD";
    /**
     * The constant SSP.
     */
    public static final String SSP = "SSP";
    /**
     * The constant STN.
     */
    public static final String STN = "STN";
    /**
     * The constant SVC.
     */
    public static final String SVC = "SVC";
    /**
     * The constant SYP.
     */
    public static final String SYP = "SYP";
    /**
     * The constant SZL.
     */
    public static final String SZL = "SZL";
    /**
     * The constant THB.
     */
    public static final String THB = "THB";
    /**
     * The constant TJS.
     */
    public static final String TJS = "TJS";
    /**
     * The constant TMT.
     */
    public static final String TMT = "TMT";
    /**
     * The constant TND.
     */
    public static final String TND = "TND";
    /**
     * The constant TOP.
     */
    public static final String TOP = "TOP";
    /**
     * The constant TRY.
     */
    public static final String TRY = "TRY";
    /**
     * The constant TTD.
     */
    public static final String TTD = "TTD";
    /**
     * The constant TWD.
     */
    public static final String TWD = "TWD";
    /**
     * The constant TZS.
     */
    public static final String TZS = "TZS";
    /**
     * The constant UAH.
     */
    public static final String UAH = "UAH";
    /**
     * The constant UGX.
     */
    public static final String UGX = "UGX";
    /**
     * The constant USD.
     */
    public static final String USD = "USD";
    /**
     * The constant USN.
     */
    public static final String USN = "USN";
    /**
     * The constant UYI.
     */
    public static final String UYI = "UYI";
    /**
     * The constant UYU.
     */
    public static final String UYU = "UYU";
    /**
     * The constant UZS.
     */
    public static final String UZS = "UZS";
    /**
     * The constant VEF.
     */
    public static final String VEF = "VEF";
    /**
     * The constant VND.
     */
    public static final String VND = "VND";
    /**
     * The constant VUV.
     */
    public static final String VUV = "VUV";
    /**
     * The constant WST.
     */
    public static final String WST = "WST";
    /**
     * The constant XAF.
     */
    public static final String XAF = "XAF";
    /**
     * The constant XCD.
     */
    public static final String XCD = "XCD";
    /**
     * The constant XDR.
     */
    public static final String XDR = "XDR";
    /**
     * The constant XOF.
     */
    public static final String XOF = "XOF";
    /**
     * The constant XPF.
     */
    public static final String XPF = "XPF";
    /**
     * The constant XSU.
     */
    public static final String XSU = "XSU";
    /**
     * The constant XUA.
     */
    public static final String XUA = "XUA";
    /**
     * The constant YER.
     */
    public static final String YER = "YER";
    /**
     * The constant ZAR.
     */
    public static final String ZAR = "ZAR";
    /**
     * The constant ZMW.
     */
    public static final String ZMW = "ZMW";
    /**
     * The constant ZWL.
     */
    public static final String ZWL = "ZWL";

    /**
     * It checks that currency as string is valid.
     *
     * @param value the value
     * @return the boolean
     */
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
