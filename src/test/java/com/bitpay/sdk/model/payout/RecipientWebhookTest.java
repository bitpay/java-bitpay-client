package com.bitpay.sdk.model.payout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RecipientWebhookTest {

    @Test
    public void testManipulateEmail() {
        RecipientWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setEmail(expected);

        Assertions.assertSame(expected, testedClass.getEmail());
    }
    
    @Test
    public void testManipulateLabel() {
        RecipientWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setLabel(expected);
        
        Assertions.assertSame(expected, testedClass.getLabel());
    }
    
    @Test
    public void testManipulateStatus() {
        RecipientWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setStatus(expected);
        
        Assertions.assertSame(expected, testedClass.getStatus());
    }
    
    @Test
    public void testManipulateId() {
        RecipientWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setId(expected);
        
        Assertions.assertSame(expected, testedClass.getId());
    }
    
    @Test
    public void testManipulateShopperId() {
        RecipientWebhook testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setShopperId(expected);
        
        Assertions.assertSame(expected, testedClass.getShopperId());
    }
    
    private RecipientWebhook getTestedClass() {
        return new RecipientWebhook();
    }
}
