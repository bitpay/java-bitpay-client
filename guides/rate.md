# Rates

## Get exchange rates
Rates are exchange rates, representing the number of fiat currency units equivalent to one BTC.
You can retrieve BitPay's [BBB exchange rates](https://bitpay.com/exchange-rates).

`GET rates/:basecurrency`

Facades : **`PUBLIC`**

## HTTP Request

**URL Parameters**
| Parameter | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  baseCurrency  | the cryptocurrency for which you want to fetch the rates. Current supported values are BTC and BCH | `string` | **Mandatory** |

**Headers**

| Fields | Description | Presence
| ------ | ------ | ------ |
|  X-Accept-Version  | must be set to `2.0.0` for requests to the BitPay API  | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API | **Mandatory** | 
|  X-Identity  | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed.  | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed. | **Mandatory** |
```java
Rates rates = bitpay.getRates();

Double rate = rates.getRate("USD");

rates.update();
```

## HTTP Response

**Body**

| Name | Description |Type | 
| ------ | ------ | ----- |
|  data  | array of currency rates for the requested baseCurrency | `array` | 
| &rarr; code  | ISO 4217 3-character currency code | `string` | 
| &rarr; name  | detailed currency name | `string` | 
| &rarr; rate  | rate for the requested baseCurrency /currency pair | `data` | 

```json
{
  "data":[
    {
      "code":"BTC",
      "name":"Bitcoin",
      "rate":1
    },
    {
      "code":"BCH",
      "name":"Bitcoin Cash",
      "rate":50.77
    },
    {
      "code":"USD",
      "name":"US Dollar",
      "rate":41248.11
    },
    {
      "code":"EUR",
      "name":"Eurozone Euro",
      "rate":33823.04
    },
    {
      "code":"GBP",
      "name":"Pound Sterling",
      "rate":29011.49
    },
    {
      "code":"JPY",
      "name":"Japanese Yen",
      "rate":4482741
    },
    {
      "code":"CAD",
      "name":"Canadian Dollar",
      "rate":49670.85
    },
    {
      "code":"AUD",
      "name":"Australian Dollar",
      "rate":53031.99
    },
    {
      "code":"CNY",
      "name":"Chinese Yuan",
      "rate":265266.57
    },
    ...
  ]
}
```


### [Back to guide index](../GUIDE.md)