package com.bitpay.sdk.t.General;

import com.bitpay.sdk.Client;
import com.bitpay.sdk.exceptions.BitPayApiException;
import com.bitpay.sdk.exceptions.BitPayGenericException;
import com.bitpay.sdk.logger.BitPayLogger;
import com.bitpay.sdk.logger.LoggerProvider;
import com.bitpay.sdk.model.invoice.Invoice;
import com.bitpay.sdk.t.ClientProvider;

final public class UseLogger {

    public void execute() throws BitPayGenericException, BitPayApiException {
        LoggerProvider.setLogger(new BitPayLogger() {
            @Override
            public void logRequest(String method, String endpoint, String json) {
                // some logic
            }

            @Override
            public void logResponse(String method, String endpoint, String json) {
                // some logic
            }

            @Override
            public void logError(String message) {
                // some logic
            }
        });

        Client client = ClientProvider.create();

        Invoice invoice = client.getInvoice("someInvoiceId");
    }
}
