package com.bitpay.sdk.examples.Payout;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.payout.PayoutRecipient;
import com.bitpay.sdk.model.payout.PayoutRecipients;
import com.bitpay.sdk.examples.ClientProvider;
import java.util.ArrayList;
import java.util.List;

public class RecipientRequests {

    public void inviteRecipients() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        PayoutRecipient recipient1 = new PayoutRecipient();
        PayoutRecipient recipient2 = new PayoutRecipient();
        recipient1.setEmail("alice@email.com");
        recipient1.setLabel("Alice");
        recipient2.setEmail("bob@email.com");
        recipient2.setLabel("Bob");
        List<PayoutRecipient> recipients = new ArrayList<>();
        recipients.add(recipient1);
        recipients.add(recipient2);

        PayoutRecipients payoutRecipients = new PayoutRecipients(recipients);

        List<PayoutRecipient> result = client.submitPayoutRecipients(payoutRecipients);
    }

    public void getRecipient() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        PayoutRecipient recipient = client.getPayoutRecipient("someRecipientId");

        List<PayoutRecipient> recipients = client.getPayoutRecipients("invited", null, null);
    }

    public void removeRecipient() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Boolean result = client.deletePayoutRecipient("somePayoutRecipientId");
    }

    public void updateRecipient() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Boolean result = client.deletePayoutRecipient("somePayoutRecipientId");
    }
}
