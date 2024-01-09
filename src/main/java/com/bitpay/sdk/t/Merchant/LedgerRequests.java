package com.bitpay.sdk.t.Merchant;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.ledger.Ledger;
import com.bitpay.sdk.model.ledger.LedgerEntry;
import com.bitpay.sdk.t.ClientProvider;
import java.util.List;

public class LedgerRequests {

    public void getLedgers() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        List<Ledger> ledgers = client.getLedgers();
    }

    public void getLedgerEntries() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        List<LedgerEntry> ledgerEntries = client.getLedgerEntries("USD", "2023-08-14", "2023-08-22");
    }
}
