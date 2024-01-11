package com.bitpay.sdk.examples.Payout;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.payout.Payout;
import com.bitpay.sdk.model.payout.PayoutGroup;
import com.bitpay.sdk.examples.ClientProvider;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PayoutRequests {

    public void createPayout() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Payout payout = new Payout(12.34, "USD", "USD");
        payout.setNotificationEmail("myEmail@email.com");
        payout.setNotificationUrl("https://my-url.com");

        Payout result = client.submitPayout(payout);

        Collection<Payout> payoutsCollection = Collections.singletonList(result);
        PayoutGroup payouts = client.submitPayouts(payoutsCollection);
    }

    public void getPayout() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Payout payout = client.getPayout("somePayoutId");

        List<Payout> payouts = client.getPayouts(
            "2023-08-14",
            "2023-08-22",
            null,
            null,
            null,
            null,
            null
        );
    }

    public void cancelPayout() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Boolean cancelPayout = client.cancelPayout("somePayoutId");

        // String groupId = payout.getGroupId();
        PayoutGroup cancelPayouts = client.cancelPayouts("someGroupId");
    }

    public void requestPayoutWebhookToBeResent() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        client.requestPayoutNotification("somePayoutId");
    }
}
