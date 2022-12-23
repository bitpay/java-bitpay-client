/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Wallet.Wallet;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WalletClientTest extends AbstractClientTest {

    @Test
    public void it_should_get_supported_wallets() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/supportedwallets",
            "GET",
            null,
            getPreparedJsonDataFromFile("getSupportedWallets.json")
        );
        WalletClient client = new WalletClient(this.getBitPayClient());

        // when
        List<Wallet> result = client.getSupportedWallets();

        // then
        Assertions.assertEquals(7, result.size());
        Assertions.assertEquals("bitpay", result.get(0).getKey());
        Assertions.assertEquals("BitPay", result.get(0).getDisplayName());
        Assertions.assertEquals(true, result.get(0).getPayPro());
        Assertions.assertEquals("bitpay-wallet.png", result.get(0).getAvatar());
        Assertions.assertEquals("https://bitpay.com/img/wallet-logos/bitpay-wallet.png", result.get(0).getImage());
        Assertions.assertEquals(15, result.get(0).getCurrencies().size());
        Assertions.assertEquals("BTC", result.get(0).getCurrencies().get(0).getCode());
        Assertions.assertEquals(true, result.get(0).getCurrencies().get(0).getPayPro());
        Assertions.assertEquals("BIP72b", result.get(0).getCurrencies().get(0).getQr().getType());
        Assertions.assertEquals("https://bitpay.com/img/icon/currencies/BTC.svg", result.get(0).getCurrencies().get(0).getImage());
    }
}
