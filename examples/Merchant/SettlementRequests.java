package com.bitpay.sdk.examples.Merchant;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.settlement.Settlement;
import com.bitpay.sdk.examples.ClientProvider;
import java.util.List;

public class SettlementRequests {

    public void getSettlement() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Settlement settlement = client.getSettlement("someSettlementId");

        List<Settlement> settlements = client.getSettlements("USD", "2023-08-14", "2023-08-22", null, null, null);
    }

    public void fetchReconciliationReport() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        client.getSettlementReconciliationReport("settlementId", "settlementToken");
    }
}
