package com.bitpay.sdk.t.Public;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.wallet.Wallet;
import com.bitpay.sdk.t.ClientProvider;
import java.util.List;

public class WalletRequests {

    public void getSupportedWallets() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        List<Wallet> wallets = client.getSupportedWallets();
    }
}
