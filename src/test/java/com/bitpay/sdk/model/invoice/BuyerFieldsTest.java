package com.bitpay.sdk.model.invoice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BuyerFieldsTest {

    @Test
    public void testManipulateBuyerName() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerName(expected);

        Assertions.assertEquals(expected, testedClass.getBuyerName());
    }

    @Test
    public void testManipulateBuyerAddress1() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerAddress1(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerAddress1());
    }
    
    @Test
    public void testManipulateBuyerAddress2() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerAddress2(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerAddress2());
    }
    
    @Test
    public void testManipulateBuyerCity() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerCity(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerCity());
    }
    
    @Test
    public void testManipulateBuyerState() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerState(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerState());
    }
    
    @Test
    public void testManipulateBuyerZip() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerZip(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerZip());
    }
    
    @Test
    public void testManipulateBuyerCountry() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerCountry(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerCountry());
    }
    
    @Test
    public void testManipulateBuyerPhone() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerPhone(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerPhone());
    }
    
    @Test
    public void testManipulateBuyerNotify() {
        BuyerFields testedClass = this.getTestedClass();
        Boolean expected = true;
        testedClass.setBuyerNotify(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerNotify());
    }
    
    @Test
    public void testManipulateBuyerEmail() {
        BuyerFields testedClass = this.getTestedClass();
        String expected = "someValue";
        testedClass.setBuyerEmail(expected);
        
        Assertions.assertEquals(expected, testedClass.getBuyerEmail());
    }

    private BuyerFields getTestedClass() {
        return new BuyerFields();
    }
}
