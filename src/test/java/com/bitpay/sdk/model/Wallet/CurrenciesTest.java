package com.bitpay.sdk.model.Wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CurrenciesTest {

    @Test
    public void it_should_get_code() {
        String expectedCode = "BTC";

        Currencies currencies = new Currencies();
        currencies.setCode(expectedCode);

        String actualCode = currencies.getCode();

        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void it_should_get_p2p() {
        Boolean expectedP2p = true;

        Currencies currencies = new Currencies();
        currencies.setP2p(expectedP2p);

        Boolean actualP2p = currencies.getP2p();

        assertEquals(expectedP2p, actualP2p);
    }

    @Test
    public void it_should_get_dapp_browser() {
        Boolean expectedDappBrowser = true;

        Currencies currencies = new Currencies();
        currencies.setDappBrowser(expectedDappBrowser);

        Boolean actualDappBrowser = currencies.getDappBrowser();

        assertEquals(expectedDappBrowser, actualDappBrowser);
    }

    @Test
    public void it_should_get_pay_pro() {
        Boolean expectedPayPro = true;

        Currencies currencies = new Currencies();
        currencies.setPayPro(expectedPayPro);

        Boolean actualPayPro = currencies.getPayPro();

        assertEquals(expectedPayPro, actualPayPro);
    }

    @Test
    public void it_should_get_currency_qr() {
        CurrencyQr expectedCurrencyQr = new CurrencyQr();
        expectedCurrencyQr.setCollapsed(true);
        expectedCurrencyQr.setType("BIP21");

        Currencies currencies = new Currencies();
        currencies.setQr(expectedCurrencyQr);

        CurrencyQr actualCurrencyQr = currencies.getQr();

        assertEquals(expectedCurrencyQr, actualCurrencyQr);
    }

    @Test
    public void it_should_get_image() {
        String expectedImage = "https://bitpay.com/api/images/logo-6fa5404d.svg";

        Currencies currencies = new Currencies();
        currencies.setImage(expectedImage);

        String actualImage = currencies.getImage();

        assertEquals(expectedImage, actualImage);
    }

    @Test
    public void it_should_manipulate_withdrawal_fee() {
        String expected = "expectedString";

        Currencies currencies = new Currencies();
        currencies.setWithdrawalFee(expected);

        String actual = currencies.getWithdrawalFee();

        assertEquals(expected, actual);
    }

    @Test
    public void it_should_manipulate_wallet_connect() {
      Boolean expected = true;

      Currencies currencies = new Currencies();
      currencies.setWalletConnect(expected);

      Boolean actual = currencies.getWalletConnect();

      assertEquals(expected, actual);
    }
}
