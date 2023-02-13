/*
 * Copyright (c) 2019 BitPay
 */

package com.bitpay.sdk.client;

import com.bitpay.sdk.exceptions.BitPayException;
import com.bitpay.sdk.model.Payout.PayoutRecipient;
import com.bitpay.sdk.model.Payout.PayoutRecipients;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PayoutRecipientsClientTest extends AbstractClientTest {

    @Test
    public void it_should_submit_payout_recipients() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/recipients",
            "POST",
            getPreparedJsonDataFromFile("submitPayoutRecipientsRequest.json"),
            getPreparedJsonDataFromFile("submitPayoutRecipientsResponse.json")
        );

        // when
        List<PayoutRecipient> result = this.getTestedClass().submit(this.getExamplePayoutRecipients());

        // then
        Assertions.assertEquals(2, result.size());

        Assertions.assertNull(result.get(0).getGuid());
        Assertions.assertEquals("alice@email.com", result.get(0).getEmail());
        Assertions.assertEquals("Alice", result.get(0).getLabel());
        Assertions.assertEquals("invited", result.get(0).getStatus());
        Assertions.assertEquals("JA4cEtmBxCp5cybtnh1rds", result.get(0).getId());
        Assertions.assertEquals("2LVBntm7z92rnuVjVX5ZVaDoUEaoY4LxhZMMzPAMGyXcejgPXVmZ4Ae3oGaCGBFKQf", result.get(0).getToken());
        Assertions.assertNull(result.get(0).getShopperId());

        Assertions.assertNull(result.get(0).getGuid());
        Assertions.assertEquals("bob@email.com", result.get(1).getEmail());
        Assertions.assertEquals("Bob", result.get(1).getLabel());
        Assertions.assertEquals("invited", result.get(1).getStatus());
        Assertions.assertEquals("X3icwc4tE8KJ5hEPNPpDXW", result.get(1).getId());
        Assertions.assertNull(result.get(1).getShopperId());
        Assertions.assertEquals("2LVBntm7z92rnuVjVX5ZVaDoUEaoY4LxhZMMzPAMGyXrrBAB9vRY3BVxGLbAa6uEx7", result.get(1).getToken());
    }

    @Test
    public void it_should_get_payout_recipients_by_status() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/recipients?token=somePayoutToken&status=invited",
            "GET",
            null,
            getPreparedJsonDataFromFile("getPayoutRecipientsResponse.json")
        );

        // when
        List<PayoutRecipient> result = this.getTestedClass().getRecipientsByFilters("invited", null, null);

        // then
        Assertions.assertEquals(2, result.size());

        Assertions.assertNull(result.get(0).getGuid());
        Assertions.assertEquals("alice@email.com", result.get(0).getEmail());
        Assertions.assertEquals("Alice", result.get(0).getLabel());
        Assertions.assertEquals("invited", result.get(0).getStatus());
        Assertions.assertEquals("JA4cEtmBxCp5cybtnh1rds", result.get(0).getId());
        Assertions.assertNull(result.get(0).getShopperId());
        Assertions.assertEquals("2LVBntm7z92rnuVjVX5ZVaDoUEaoY4LxhZMMzPAMGyXcejgPXVmZ4Ae3oGaCGBFKQf", result.get(0).getToken());

        Assertions.assertNull(result.get(0).getGuid());
        Assertions.assertEquals("bob@email.com", result.get(1).getEmail());
        Assertions.assertEquals("Bob", result.get(1).getLabel());
        Assertions.assertEquals("invited", result.get(1).getStatus());
        Assertions.assertEquals("X3icwc4tE8KJ5hEPNPpDXW", result.get(1).getId());
        Assertions.assertNull(result.get(1).getShopperId());
        Assertions.assertEquals("2LVBntm7z92rnuVjVX5ZVaDoUEaoY4LxhZMMzPAMGyXrrBAB9vRY3BVxGLbAa6uEx7", result.get(1).getToken());
    }

    @Test
    public void it_should_get_payout_recipient() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/recipients/JA4cEtmBxCp5cybtnh1rds?token=somePayoutToken",
            "GET",
            null,
            getPreparedJsonDataFromFile("getPayoutRecipientResponse.json")
        );

        // when
        PayoutRecipient result = this.getTestedClass().get("JA4cEtmBxCp5cybtnh1rds");

        // then
        Assertions.assertNull(result.getGuid());
        Assertions.assertEquals("john.smith@email.com", result.getEmail());
        Assertions.assertEquals("John Smith", result.getLabel());
        Assertions.assertEquals("invited", result.getStatus());
        Assertions.assertEquals("JA4cEtmBxCp5cybtnh1rds", result.getId());
        Assertions.assertNull(result.getShopperId());
        Assertions.assertEquals("2LVBntm7z92rnuVjVX5ZVaDoUEaoY4LxhZMMzPAMGyXcejgPXVmZ4Ae3oGaCGBFKQf", result.getToken());
    }

    @Test
    public void it_should_update_payout_recipient() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/recipients/X3icwc4tE8KJ5hEPNPpDXW",
            "PUT",
            null,
            getPreparedJsonDataFromFile("updatePayoutRecipientResponse.json")
        );
        PayoutRecipient payoutRecipient = new PayoutRecipient();
        payoutRecipient.setLabel("Bob123");

        // when
        PayoutRecipient result = this.getTestedClass().update("X3icwc4tE8KJ5hEPNPpDXW", payoutRecipient);

        // then
        Assertions.assertEquals("Bob123", result.getLabel());
    }

    @Test
    public void it_should_delete_payout_recipient() throws BitPayException {
        // given
        this.addServerJsonResponse(
            "/recipients/X3icwc4tE8KJ5hEPNPpDXW?token=somePayoutToken",
            "DELETE",
            null,
            getPreparedJsonDataFromFile("deletePayoutRecipientResponse.json")
        );

        // when
        Boolean result = this.getTestedClass().delete("X3icwc4tE8KJ5hEPNPpDXW");

        // then
        Assertions.assertTrue(result);
    }

    private PayoutRecipients getExamplePayoutRecipients() {
        PayoutRecipient payoutRecipient1 = new PayoutRecipient();
        payoutRecipient1.setEmail("alice@email.com");
        payoutRecipient1.setLabel("Alice");
        PayoutRecipient payoutRecipient2 = new PayoutRecipient();
        payoutRecipient2.setEmail("bob@email.com");
        payoutRecipient2.setLabel("Bob");

       return new PayoutRecipients(Arrays.asList(payoutRecipient1, payoutRecipient2));
    }

    private PayoutRecipientsClient getTestedClass() {
        return new PayoutRecipientsClient(this.getBitPayClient(), this.getAccessTokens(), this.uuidGenerator);
    }
}
