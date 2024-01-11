package com.bitpay.sdk.examples.Merchant;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.model.invoice.Refund;
import com.bitpay.sdk.examples.ClientProvider;

public class RefundRequests {

    public void createRefund() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Refund refund = client.createRefund("myInvoiceId", 12.34, true, true, true, "no");
    }

    public void updateRefund() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Refund updateRefund = client.updateRefund("refundId", "created");

        Refund updateRefundByGuid = client.updateRefundByGuid("someGuid", "created");
    }

    public void getRefund() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Refund refund = client.getRefund("someRefundId");

        Refund refundByGuid = client.getRefundByGuid("someGuid");
    }

    public void cancelRefund() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Refund cancelRefund = client.cancelRefund("someRefundId");

        Refund cancelRefundByGuid = client.cancelRefundByGuid("someGuid");
    }

    public void requestRefundNotificationToBeResent() throws BitPayGenericException, BitPayApiException {
        Client client = ClientProvider.create();

        Boolean result = client.sendRefundNotification("someRefundId");
    }
}
