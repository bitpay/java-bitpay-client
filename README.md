java-bitpay-client
==================

This is the Java client library for the BitPay Payment Gateway.  This library implements BitPay's new cryptographically-secure API.

Dependencies
------------
BitPay merchant account

ApacheHttpClient

java-json

json-simple

Getting Started
---------------

Log into your BitPay merchant account and generate a Private Key and SIN. Then all you need to do is instantiate a BitPay object, and pass in your private key and the SIN.

```java
String privateKey = KeyUtils.readBitcoreKeyFromFile(privateKeyFile);
ECKey key = KeyUtils.loadKey(privateKey);
this.bitpay = new BitPay(key, SIN);
```

####Create an invoice
```java
Invoice invoice = bitpay.createInvoice(100, "USD");

String invoiceUrl = invoice.getURL();

String status = invoice.getStatus();
```

####Retreive an invoice
```java
invoice = bitpay.getInvoice(invoice.getId());
```
####Exchange Rates

You can also get BitPay's exchange rates.
```java
Rates rates = this.bitpay.getRates();

double rate = rates.getRate("USD");

rates.update();
```
####Advanced Invoices

You can add additional params to the invoice by passing an InvoiceParams object. You don't have to set all of the advanced parameters. It will only use the ones you do set.
```java
InvoiceParams params = new InvoiceParams();

params.setBuyerName("Satoshi");
params.setBuyerEmail("satoshi@bitpay.com");
params.setFullNotifications(true);
params.setNotificationEmail("satoshi@bitpay.com");
		
Invoice invoice = this.bitpay.createInvoice(100, "USD", params);
```

# Support

* https://github.com/bitpay/java-bitpay-client/issues
* https://support.bitpay.com/

# License

The MIT License (MIT)

Copyright (c) 2014 BitPay, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
