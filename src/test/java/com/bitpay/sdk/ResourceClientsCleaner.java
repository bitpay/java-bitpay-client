/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk;

import com.bitpay.sdk.client.BillClient;
import com.bitpay.sdk.client.CurrencyClient;
import com.bitpay.sdk.client.InvoiceClient;
import com.bitpay.sdk.client.LedgerClient;
import com.bitpay.sdk.client.PayoutClient;
import com.bitpay.sdk.client.PayoutRecipientsClient;
import com.bitpay.sdk.client.RateClient;
import com.bitpay.sdk.client.RefundClient;
import com.bitpay.sdk.client.ResourceClient;
import com.bitpay.sdk.client.SettlementClient;
import com.bitpay.sdk.client.WalletClient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ResourceClientsCleaner {

    /**
     * This method cleans all resource client singletons.
     */
    public static void execute() {
        List<Class<? extends ResourceClient>> classes = Arrays
            .asList(BillClient.class, CurrencyClient.class, InvoiceClient.class,
            LedgerClient.class, PayoutClient.class, PayoutRecipientsClient.class, RateClient.class, RefundClient.class,
            SettlementClient.class, WalletClient.class);
        classes.forEach(singleClass -> {
            try {
                Field field = singleClass.getDeclaredField("instance");
                field.setAccessible(true);
                field.set(null, null);
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
            }
        });
    }
}
