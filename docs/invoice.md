# Invoice

Invoices are time-sensitive payment requests addressed to specific buyers. An invoice has a fixed price, typically denominated in fiat currency. It also has an equivalent price in the supported cryptocurrencies, calculated by BitPay, at a locked exchange rate with an expiration time of 15 minutes.

## Create an invoice

`POST /invoices`

Facade **`POS` `MERCHANT`**

### HTTP Request

**Headers**
| Fields | Description | Presence
| ------ | ------ | ------ |
|  X-Accept-Version  | must be set to `2.0.0` for requests to the BitPay API  | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API | **Mandatory** | 
|  X-Identity  | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed.  | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed. | **Mandatory** |

**Body**
| Name | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  token  | The API token can be retrieved from the dashboard (limited to pos facade) or using the Tokens resource to get access to the merchant facade. This is described in the section Request an API token).| `string` | **Mandatory** |
| price | Fixed price amount for the checkout, in the "currency" of the invoice object. | `number` | **Mandatory** |
| currency | ISO 4217 3-character currency code. This is the currency associated with the price field, supported currencies are available via the Currencies resource.| `string` | **Mandatory** |
| orderId | Can be used by the merchant to assign their own internal Id to an invoice. If used, there should be a direct match between an orderId and an invoice id. | `string` | Optional |
| itemDesc | Invoice description - will be added as a line item on the BitPay checkout page, under the merchant name. | `string` | Optional |
| itemCode | "bitcoindonation" for donations, otherwise do not include the field in the request. | `string` | Optional |
| notificationEmail | Merchant email address for notification of invoice status change. It is also possible to configure this email via the account setting on the BitPay dashboard or disable the email notification| `string` | Optional |
| notificationURL | URL to which BitPay sends webhook notifications. HTTPS is mandatory. | `string` | Optional |
| redirectURL | The shopper will be redirected to this URL when clicking on the Return button after a successful payment or when clicking on the Close button if a separate closeURL is not specified. Be sure to include "http://" or "https://" in the url. | `string` | Optional |
| closeURL | URL to redirect if the shopper does not pay the invoice and click on the Close button instead. Be sure to include "http://" or "https://" in the url. | `string` | Optional |
| autoRedirect | Set to false by default, merchant can setup automatic redirect to their website by setting this parameter to true. This will applied to the following scenarios:When the invoice is paid, it automatically redirects the shopper to the redirectURL indicated When the invoice expires, it automatically redirects the shopper to the closeURL if specified and to the redirectURL otherwiseNote: If automatic redirect is enabled, redirectURL becomes a mandatory invoice parameters.| `boolean` | Optional |
| posData | A passthru variable provided by the merchant during invoice creation and designed to be used by the merchant to correlate the invoice with an order or other object in their system. This passthru variable can be a serialized object, e.g.: "posData": "\"{ \"ref\" : 711454, \"item\" : \"test_item\" }\"" | `string` | Optional |
| transactionSpeed | This is a risk mitigation parameter for the merchant to configure how they want to fulfill orders depending on the number of block confirmations for the transaction made by the consumer on the selected cryptocurrency. | `string` | Optional |
| fullNotifications | This parameter is set to true by default, meaning all standard notifications are being sent for a payment made to an invoice. If you decide to set it to false instead, only 1 webhook will be sent for each invoice paid by the consumer. This webhook will be for the "confirmed" or "complete" invoice status, depending on the transactionSpeed selected. | `boolean` | Optional |
| extendedNotifications | Allows merchants to get access to additional webhooks. For instance when an invoice expires without receiving a payment or when it is refunded. If set to true, then fullNotifications is automatically set to true. When using the extendedNotifications parameter, the webhook also have a payload slightly different from the standard webhooks. | `boolean` | Optional |
| physical | Indicates whether items are physical goods. Alternatives include digital goods and services. | `boolean` | Optional |
| buyer | Allows merchant to pass buyer related information in the invoice object | ` object ` | Optional |
| paymentCurrencies | Allow the merchant to select the cryptocurrencies available as payment option on the BitPay invoice. Possible values are currently "BTC", "BCH", "ETH", "GUSD", "PAX", "BUSD", "USDC", "XRP", "DOGE", "DAI" and "WBTC". For instance "paymentCurrencies": ["BTC"] will create an invoice with only XRP available as transaction currency, thus bypassing the currency selection step on the invoice. | `array` | Optional |
| jsonPayProRequired | If set to true, this means that the invoice will only accept payments from wallets which have implemented the BitPay JSON Payment Protocol | `boolean` | Optional |

An example code of the create invoice

```java
// Setting mandatory parameters in invoice i.e price and currency.
Invoice invoice = new Invoice(100.0, "USD");

// Setting invoice optional parameters
invoice.setOrderId("98e572ea-910e-415d-b6de-65f5090680f6");
invoice.setFullNotifications(true);
invoice.setExtendedNotifications(true);
invoice.setTransactionSpeed("medium");
invoice.setNotificationURL("https://hookbin.com/lJnJg9WW7MtG9GZlPVdj");
invoice.setRedirectURL("https://hookbin.com/lJnJg9WW7MtG9GZlPVdj");
invoice.setPosData("98e572ea35hj356xft8y8cgh56h5090680f6");
invoice.setItemDesc("Ab tempora sed ut.");
invoice.setNotificationEmail("");

// Creating Invoice
invoice = bitpay.createInvoice(invoice);

// To get the generated invoice url and status
String invoiceUrl = invoice.getUrl();
String invoiceStatus = invoice.getStatus();
System.out.println(invoiceUrl);
System.out.println(invoiceStatus);
```

### HTTP Response

```json
{
    "facade": "pos/invoice",
    "data": {
        "url": "https://bitpay.com/invoice?id=KSnNNfoMDsbRzd1U9ypmVH",
        "status": "new",
        "price": 10,
        "currency": "USD",
        "orderId": "20210511_abcde",
        "invoiceTime": 1620734545366,
        "expirationTime": 1620735445366,
        "currentTime": 1620734545415,
        "id": "KSnNNfoMDsbRzd1U9ypmVH",
        "lowFeeDetected": false,
        "amountPaid": 0,
        "displayAmountPaid": "0",
        "exceptionStatus": false,
        "redirectURL": "https://merchantwebsite.com/shop/return",
        "refundAddressRequestPending": false,
        "buyerProvidedInfo": {},
        "paymentSubtotals": {
            "BTC": 18200,
            "BCH": 744500,
            "ETH": 2535000000000000,
            "GUSD": 1000,
            "PAX": 10000000000000000000,
            "BUSD": 10000000000000000000,
            "USDC": 10000000,
            "XRP": 7084249,
            "DOGE": 2068707100,
            "DAI": 9990000000000000000,
            "WBTC": 18100
        },
        "paymentTotals": {
            "BTC": 29800,
            "BCH": 744500,
            "ETH": 2535000000000000,
            "GUSD": 1000,
            "PAX": 10000000000000000000,
            "BUSD": 10000000000000000000,
            "USDC": 10000000,
            "XRP": 7084249,
            "DOGE": 2068707100,
            "DAI": 9990000000000000000,
            "WBTC": 18100
        },
        "paymentDisplayTotals": {
            "BTC": "0.000298",
            "BCH": "0.007445",
            "ETH": "0.002535",
            "GUSD": "10.00",
            "PAX": "10.00",
            "BUSD": "10.00",
            "USDC": "10.00",
            "XRP": "7.084249",
            "DOGE": "20.687071",
            "DAI": "9.99",
            "WBTC": "0.000181"
        },
        "paymentDisplaySubTotals": {
            "BTC": "0.000182",
            "BCH": "0.007445",
            "ETH": "0.002535",
            "GUSD": "10.00",
            "PAX": "10.00",
            "BUSD": "10.00",
            "USDC": "10.00",
            "XRP": "7.084249",
            "DOGE": "20.687071",
            "DAI": "9.99",
            "WBTC": "0.000181"
        },
        "exchangeRates": {
            "BTC": {
            "USD": 55072.459995,
            "EUR": 45287.42496000001,
            "BCH": 40.884360403999914,
            "ETH": 13.953840617367156,
            "GUSD": 55072.459995,
            "PAX": 55072.459995,
            "BUSD": 55072.459995,
            "USDC": 55072.459995,
            "XRP": 38907.54307403195,
            "DOGE": 113694.39064944115,
            "DAI": 55018.486859390934,
            "WBTC": 0.9983514430763876
            },
            "BCH": {
            "USD": 1343.1537000000003,
            "EUR": 1104.481875,
            "BTC": 0.02437664632426631,
            "ETH": 0.34031805835672807,
            "GUSD": 1343.1537000000003,
            "PAX": 1343.1537000000003,
            "BUSD": 1343.1537000000003,
            "USDC": 1343.1537000000003,
            "XRP": 948.9100440136494,
            "DOGE": 2772.8748903518513,
            "DAI": 1341.8373575522414,
            "WBTC": 0.024348638771359274
            },
            "ETH": {
            "USD": 3944.6466899999996,
            "EUR": 3242.8077850000004,
            "BTC": 0.07159065804331831,
            "BCH": 2.9284029977060646,
            "GUSD": 3944.6466899999996,
            "PAX": 3944.6466899999996,
            "BUSD": 3944.6466899999996,
            "USDC": 3944.6466899999996,
            "XRP": 2786.8105223000134,
            "DOGE": 8143.529484384802,
            "DAI": 3940.7807840508463,
            "WBTC": 0.07150840394174397
            },
            ...
        },
        "supportedTransactionCurrencies": {
            "BTC": {
            "enabled": true
            },
            "BCH": {
            "enabled": true
            },
            "ETH": {
            "enabled": true
            },
            ...
        },
        "minerFees": {
            "BTC": {
            "satoshisPerByte": 79.152,
            "totalFee": 11600
            },
            "BCH": {
            "satoshisPerByte": 0,
            "totalFee": 0
            },
            "ETH": {
            "satoshisPerByte": 0,
            "totalFee": 0
            },
            ...
        },
        "jsonPayProRequired": false,
        "paymentCodes": {
            "BTC": {
            "BIP72b": "bitcoin:?r=https://bitpay.com/i/KSnNNfoMDsbRzd1U9ypmVH",
            "BIP73": "https://bitpay.com/i/KSnNNfoMDsbRzd1U9ypmVH"
            },
            "BCH": {
            "BIP72b": "bitcoincash:?r=https://bitpay.com/i/KSnNNfoMDsbRzd1U9ypmVH",
            "BIP73": "https://bitpay.com/i/KSnNNfoMDsbRzd1U9ypmVH"
            },
            "ETH": {
            "EIP681": "ethereum:?r=https://bitpay.com/i/KSnNNfoMDsbRzd1U9ypmVH"
            },
            ...
        },
        "token": "8nPJSGgi7omxcbGGZ4KsSgqdi6juypBe9pVpSURDeAwx4VDQx1XfWPy5qqknDKT9KQ"
    }
}
```

**WARNING**: 
If you get the following error when initiating the client for first time:
"500 Internal Server Error` response: {"error":"Account not setup completely yet."}"
Please, go back to your BitPay account and complete the required steps.
More info [here](https://support.bitpay.com/hc/en-us/articles/203010446-How-do-I-apply-for-a-merchant-account-)

## Create an invoice (extended)

You can add optional attributes to the invoice.  Attributes that are not set are ignored or given default values.
**Body**
| Name | Description | Type | Presence |
| --- | --- | :---: | :---: |
| buyer | Allows merchant to pass buyer related information in the invoice object | `object` | Optional |
| &rarr; name | Buyer's name | `string` | Optional |
| &rarr; address1 | Buyer's address | `string` | Optional |
| &rarr; address2 | Buyer's appartment or suite number | `string` | Optional |
| &rarr; locality | Buyer's city or locality | `string` | Optional |
| &rarr; region | Buyer's state or province | `string` | Optional |
| &rarr; postalCode | Buyer's Zip or Postal Code | `string` | Optional |
| &rarr; country | Buyer's Country code. Format ISO 3166-1 alpha-2 | `string` | Optional |
| &rarr; email | Buyer's email address. If provided during invoice creation, this will bypass the email prompt for the consumer when opening the invoice. | `string` | Optional |
| &rarr; phone | Buyer's phone number | `string` | Optional |
| &rarr; notify | Indicates whether a BitPay email confirmation should be sent to the buyer once he has paid the invoice | `boolean` | Optional |

```java
Buyer buyerData = new Buyer();
buyerData.setName("Satoshi");
buyerData.setAddress1("street");
buyerData.setAddress2("911");
buyerData.setLocality("Washington");
buyerData.setRegion("District of Columbia");
buyerData.setPostalCode("20000");
buyerData.setCountry("USA");
buyerData.setEmail("satoshi@buyeremaildomain.com");
buyerData.setNotify(true);

Invoice invoice = new Invoice(100.0, Currency.USD)
invoice.setBuyer(buyerData);

invoice = bitpay.createInvoice(invoice);
```
### HTTP Response

```json
{
  "facade": "merchant/invoice",
  "data": {
     "url": "https://bitpay.com/invoice?id=G3viJEJgE8Jk2oekSdgT2A",
     "status": "new",
     "price": 10,
     "currency": "USD",
     "orderId": "20210511_fghij",
     "invoiceTime": 1620733980748,
     "expirationTime": 1620734880748,
     "currentTime": 1620733980807,
     "id": "G3viJEJgE8Jk2oekSdgT2A",
     "lowFeeDetected": false,
     "amountPaid": 0,
     "displayAmountPaid": "0",
     "exceptionStatus": false,
     "targetConfirmations": 6,
     "transactions": [],
     "transactionSpeed": "medium",
     "buyer": {
        "email": "john@doe.com"
     },
     "redirectURL": "https://merchantwebsite.com/shop/return",
     "refundAddresses": [],
     "refundAddressRequestPending": false,
     "buyerProvidedEmail": "john@doe.com",
     "buyerProvidedInfo": {
        "emailAddress": "john@doe.com"
     },
     "paymentSubtotals": {
        "BTC": 18000,
        "BCH": 739100,
        "ETH": 2505000000000000,
        "GUSD": 1000,
        "PAX": 10000000000000000000,
        "BUSD": 10000000000000000000,
        "USDC": 10000000,
        "XRP": 7015685,
        "DOGE": 1998865000,
        "DAI": 9990000000000000000,
        "WBTC": 18000
     },
     "paymentTotals": {
        "BTC": 29600,
        "BCH": 739100,
        "ETH": 2505000000000000,
        "GUSD": 1000,
        "PAX": 10000000000000000000,
        "BUSD": 10000000000000000000,
        "USDC": 10000000,
        "XRP": 7015685,
        "DOGE": 1998865000,
        "DAI": 9990000000000000000,
        "WBTC": 18000
     },
     "paymentDisplayTotals": {
        "BTC": "0.000296",
        "BCH": "0.007391",
        "ETH": "0.002505",
        "GUSD": "10.00",
        "PAX": "10.00",
        "BUSD": "10.00",
        "USDC": "10.00",
        "XRP": "7.015685",
        "DOGE": "19.988650",
        "DAI": "9.99",
        "WBTC": "0.000180"
     },
     "paymentDisplaySubTotals": {
        "BTC": "0.000180",
        "BCH": "0.007391",
        "ETH": "0.002505",
        "GUSD": "10.00",
        "PAX": "10.00",
        "BUSD": "10.00",
        "USDC": "10.00",
        "XRP": "7.015685",
        "DOGE": "19.988650",
        "DAI": "9.99",
        "WBTC": "0.000180"
     },
     "exchangeRates": {
        "BTC": {
          "USD": 55413.609335,
          "EUR": 45540.39841,
          "BCH": 40.84109737914668,
          "ETH": 13.870219975470258,
          "GUSD": 55413.609335,
          "PAX": 55413.609335,
          "BUSD": 55413.609335,
          "USDC": 55413.609335,
          "XRP": 38758.09372049268,
          "DOGE": 110606.00665668662,
          "DAI": 55359.96552840298,
          "WBTC": 0.9981333606461704
        },
        "BCH": {
          "USD": 1352.90925,
          "EUR": 1111.2150000000001,
          "BTC": 0.02440102556111244,
          "ETH": 0.33863791096704754,
          "GUSD": 1352.90925,
          "PAX": 1352.90925,
          "BUSD": 1352.90925,
          "USDC": 1352.90925,
          "XRP": 946.2690507998013,
          "DOGE": 2700.4176646706587,
          "DAI": 1351.599550036015,
          "WBTC": 0.024369173431532262
        },
        "ETH": {
          "USD": 3992.672665000001,
          "EUR": 3278.9696950000002,
          "BTC": 0.0720117094001833,
          "BCH": 2.9426910658087726,
          "GUSD": 3992.672665000001,
          "PAX": 3992.672665000001,
          "BUSD": 3992.672665000001,
          "USDC": 3992.672665000001,
          "XRP": 2792.6060619837313,
          "DOGE": 7969.406516966069,
          "DAI": 3988.807510522304,
          "WBTC": 0.07191770817497412
        },
        "GUSD": {
          "USD": 1,
          "EUR": 0.821674,
          "BTC": 0.000018035966241721267,
          "BCH": 0.0007370228698196506,
          "ETH": 0.0002503034929852446,
          "PAX": 1,
          "BUSD": 1,
          "USDC": 1,
          "XRP": 0.6994327600316144,
          "DOGE": 1.9960079840319362,
          "DAI": 0.9990319380520276,
          "WBTC": 0.000018012422807762058
        },
        "PAX": {
          "USD": 1,
          "EUR": 0.821674,
          "BTC": 0.000018035966241721267,
          "BCH": 0.0007370228698196506,
          "ETH": 0.0002503034929852446,
          "GUSD": 1,
          "BUSD": 1,
          "USDC": 1,
          "XRP": 0.6994327600316144,
          "DOGE": 1.9960079840319362,
          "DAI": 0.9990319380520276,
          "WBTC": 0.000018012422807762058
        },
        "BUSD": {
          "USD": 1,
          "EUR": 0.821674,
          "BTC": 0.000018035966241721267,
          "BCH": 0.0007370228698196506,
          "ETH": 0.0002503034929852446,
          "GUSD": 1,
          "PAX": 1,
          "USDC": 1,
          "XRP": 0.6994327600316144,
          "DOGE": 1.9960079840319362,
          "DAI": 0.9990319380520276,
          "WBTC": 0.000018012422807762058
        },
        "USDC": {
          "USD": 1,
          "EUR": 0.821674,
          "BTC": 0.000018035966241721267,
          "BCH": 0.0007370228698196506,
          "ETH": 0.0002503034929852446,
          "GUSD": 1,
          "PAX": 1,
          "BUSD": 1,
          "XRP": 0.6994327600316144,
          "DOGE": 1.9960079840319362,
          "DAI": 0.9990319380520276,
          "WBTC": 0.000018012422807762058
        },
        "XRP": {
          "USD": 1.4253776249999999,
          "EUR": 1.17088545,
          "BTC": 0.00002570806272620483,
          "BCH": 0.0010505359077542177,
          "ETH": 0.0003567769983605121,
          "GUSD": 1.4253776249999999,
          "PAX": 1.4253776249999999,
          "BUSD": 1.4253776249999999,
          "USDC": 1.4253776249999999,
          "DOGE": 2.845065119760479,
          "DAI": 1.423997771159746,
          "WBTC": 0.00002567450444222371
        },
        "DOGE": {
          "USD": 0.5002839,
          "EUR": 0.4110702732486,
          "BTC": 0.000009023103531676658,
          "BCH": 0.0003687206757025671,
          "ETH": 0.00012522280765428083,
          "GUSD": 0.5002839,
          "PAX": 0.5002839,
          "BUSD": 0.5002839,
          "USDC": 0.5002839,
          "XRP": 0.3499149489763802,
          "DAI": 0.49979959419322684,
          "WBTC": 0.000009011325130716152
        },
        "DAI": {
          "USD": 1.000968,
          "EUR": 0.822469380432,
          "BTC": 0.000018053425057043255,
          "BCH": 0.0007377363079576361,
          "ETH": 0.00025054578676645436,
          "GUSD": 1.000968,
          "PAX": 1.000968,
          "BUSD": 1.000968,
          "USDC": 1.000968,
          "XRP": 0.7001098109433249,
          "DOGE": 1.9979401197604791,
          "WBTC": 0.000018029858833039973
        },
        "WBTC": {
          "USD": 55466.58,
          "EUR": 45575.44665492,
          "BTC": 1.000393364423732,
          "BCH": 40.88013797068123,
          "ETH": 13.883478717945511,
          "GUSD": 55466.58,
          "PAX": 55466.58,
          "BUSD": 55466.58,
          "USDC": 55466.58,
          "XRP": 38795.14313891434,
          "DOGE": 110711.73652694612,
          "DAI": 55412.88491451783
        }
     },
     "minerFees": {
        "BTC": {
          "satoshisPerByte": 79.151,
          "totalFee": 11600
        },
        "BCH": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "ETH": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "GUSD": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "PAX": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "BUSD": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "USDC": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "XRP": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "DOGE": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "DAI": {
          "satoshisPerByte": 0,
          "totalFee": 0
        },
        "WBTC": {
          "satoshisPerByte": 0,
          "totalFee": 0
        }
     },
     "shopper": {},
     "jsonPayProRequired": false,
     "supportedTransactionCurrencies": {
        "BTC": {
          "enabled": true
        },
        "BCH": {
          "enabled": true
        },
        "ETH": {
          "enabled": true
        },
        "GUSD": {
          "enabled": true
        },
        "PAX": {
          "enabled": true
        },
        "BUSD": {
          "enabled": true
        },
        "USDC": {
          "enabled": true
        },
        "XRP": {
          "enabled": true
        },
        "DOGE": {
          "enabled": true
        },
        "DAI": {
          "enabled": true
        },
        "WBTC": {
          "enabled": true
        }
     },
     "paymentCodes": {
        "BTC": {
          "BIP72b": "bitcoin:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A",
          "BIP73": "https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "BCH": {
          "BIP72b": "bitcoincash:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A",
          "BIP73": "https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "ETH": {
          "EIP681": "ethereum:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "GUSD": {
          "EIP681b": "ethereum:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "PAX": {
          "EIP681b": "ethereum:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "BUSD": {
          "EIP681b": "ethereum:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "USDC": {
          "EIP681b": "ethereum:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "XRP": {
          "BIP72b": "ripple:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A",
          "BIP73": "https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A",
          "RIP681": "https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "DOGE": {
          "BIP72b": "dogecoin:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A",
          "BIP73": "https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "DAI": {
          "EIP681b": "ethereum:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        },
        "WBTC": {
          "EIP681b": "ethereum:?r=https://bitpay.com/i/G3viJEJgE8Jk2oekSdgT2A"
        }
     },
     "token": "Svbfdow1xXc6chtQB3GVKqyRVhyLMqn3nhMhyTtf9T9vTCDKrVfdoA7n94nZUECZf"
  }
}
```
## Retrieve an invoice

`GET /invoices/:invoiceid`

Facade **`POS` `MERCHANT`**

### HTTP Request

**URL Parameters**

| Parameter | Description | Type | Presence |
| --- | --- | :---: | :---: |
| ?token= | When fetching an invoice via the `merchant` or the `pos` facade, pass the API token as a URL parameter - the same token used to create the invoice in the first place. | `string` | **Mandatory** |

**Headers**

| Fields | Description | Presence |
| --- | --- | :---: |
| X-Accept-Version | Must be set to `2.0.0` for requests to the BitPay API. | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API. | **Mandatory** |
| X-Identity | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | **Mandatory** |


To get the generated invoice details, pass the Invoice Id with URL parameter

```java
Invoice getInvoice = bitpay.getInvoice(invoice.getId());
```

## Retrieve invoices filtered by query

Facade **`MERCHANT`**

### HTTP Request

**URL Parameters**

| Parameter | Description | Type | Presence |
| --- | --- | :---: | :---: |
| ?token= | When fetching an invoice via the `merchant` or the `pos` facade, pass the API token as a URL parameter - the same token used to create the invoice in the first place. | `string` | **Mandatory** |
| &dateStart= | the start of the date window to query for invoices. Format `YYYY-MM-DD` | `string` | **Mandatory** |
| &dateEnd= | the end of the date window to query for invoices. Format `YYYY-MM-DD` | `string` | **Mandatory** |
| &status= | the invoice status you want to query on | `string` | Optional |
| &orderId= | the optional order id specified at time of invoice creation | `string` | Optional |
| &limit= | maximum results that the query will return (useful for paging results) | `number` | Optional |
| &offset= | number of results to offset (ex. skip 10 will give you results starting with the 11th result) | `number` | Optional |

**Headers**

| Fields | Description | Presence |
| --- | --- | :---: |
| X-Accept-Version | Must be set to `2.0.0` for requests to the BitPay API. | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API. | **Mandatory** |
| X-Identity | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | **Mandatory** |

To get the generated invoices filtered by query parameters 

```java
Invoice invoices = bitpay.getInvoices('YYYY-MM-DD', 'YYYY-MM-DD', InvoiceStatus::Complete, null, 10); //Always use the included InvoiceStatus model to avoid typos
```

## Update an invoice

Update an invoice with SMS or email in order to respond to a payment exception

:warning: Updating the invoice will require EITHER an SMS or E-mail, but not both.

:warning: smsCode required only when verifying SMS.

`PUT /invoices/:invoiceId`  

Facades  **`POS` `MERCHANT`**

### HTTP Request

**URL Parameters**

| Parameter | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  ?token=  | When fetching an invoice via the merchant or the pos facade, pass the API token as a URL parameter - the same token used to create the invoice in the first place. | `string` | **Mandatory** |

| Name | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  buyerEmail  | Email address of the buyer | `string` | :warning: |
|  buyerSms  | SMS to use to process any payment exception if necessary. ex. “+13415556589” | `string` | :warning: |
|  smsCode  | Verification code in order to complete the SMS verification process | `string` | :warning: |

**Headers**

| Fields | Description | Presence
| ------ | ------ | ------ |
|  X-Accept-Version  | must be set to `2.0.0` for requests to the BitPay API  | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API | **Mandatory** | 
|  X-Identity  | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed.  | C |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed. | C |

```java
Invoice updateInvoice = bitpay.updateInvoice(invoice.getId(), "sanbox@bitpay.com", "", "");
```

## Delete an invoice

:warning: Cancellation will require EITHER an SMS or E-mail to have already been set if the invoice has proceeded to the point where it may have
been paid.

`DELETE /invoices/:invoiceId`  

Facades  **`POS` `MERCHANT`**

### HTTP Request

**URL Parameters**

| Parameter | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  ?token=  | When fetching an invoice via the merchant or the pos facade, pass the API token as a URL parameter - the same token used to create the invoice in the first place. | `string` | **Mandatory** |


**Headers**

| Fields | Description | Presence
| ------ | ------ | ------ |
|  X-Accept-Version  | must be set to `2.0.0` for requests to the BitPay API  | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API | **Mandatory** | 
|  X-Identity  | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed.  | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed. | **Mandatory** |

```java
Invoice cancelInvoice = bitpay.cancelInvoice(invoice.getId());
```
### HTTP Response

| Field | Description |Type |
| ------ | ------ | ----- |
|  isCancelled  | Indicates whether or not the invoice was cancelled explicitly (vs expiring or being decline for other reasons) | `string` |

### Error Scenarios & Format:

| Field | Description |Type |
| ------ | ------ | ----- |
|  status  | will be “error” in an error scenario | `string` |
|  code  | six digit code that maps to an error on BitPay’s side | `string` |
|  data  | will be null in an error scenario | `string` |
|  message  | error message pertaining to the specific error | `string` |

```json
{
"status": "error",
"code": "040107",
"data": null,
"error": "Invalid invoice state for cancel"
}

```


### [Back to guide index](GUIDE.md)