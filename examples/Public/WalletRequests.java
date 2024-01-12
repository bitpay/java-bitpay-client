package com.bitpay.sdk.examples.Public;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.wallet.Wallet;
import com.bitpay.sdk.examples.ClientProvider;
import java.util.List;

public class WalletRequests {

    public void getSupportedWallets() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        List<Wallet> wallets = client.getSupportedWallets();
    }
}
