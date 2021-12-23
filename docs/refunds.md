# Refunds

## Create a new refund request

`POST /refunds`

Facades **`MERCHANT`**

### HTTP Request

**Headers**

| Fields | Description | Presence |
| --- | --- | :---: |
| X-Accept-Version | Must be set to `2.0.0` for requests to the BitPay API. | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API. | **Mandatory** |
| X-Identity | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | **Mandatory** |

**Body**

| Name | Description | Type | Presence |
| --- | --- | :---: | :---: |
| invoiceId | the id of the invoice to refund | `string` | **Mandatory** |
| amount | Amount to be refunded in the `currency` indicated | `number` | **Mandatory** |
| currency | reference currency used for the refund, usually the same as the currency used to create the invoice. | `string` | **Mandatory** |
| preview | whether to create the refund request as a preview (which will not be acted on until status is updated) | `boolean` | Default: `false` |
| immediate | whether funds should be removed from merchant ledger immediately on submission or at time of processing | `boolean` | Default: `false` |
| buyerPaysRefundFee | whether the buyer should pay the refund fee (default is `merchant`) | `boolean` | Default: `false` |

An example code of the create a new refund request

```java
Refund createRefund = bitpay.createRefund(invoice.getId(), 1.0, null, true, false, false);
```

**Response Body Fields**

| Name | Description | Type |
| --- | --- | :---: |
| id | the refund request id | `string` |
| requestDate | the date that the refund request was created (ISO-8601) | `string` |
| status | refund lifecycle status of the request string. `preview` - no funds deducted, refund will not proceed automatically. `created` - funds deducted/allocated if immediate, will proceed when txs are confirmed and required data is collected. `pending` - refund is in process of being fulfilled. `cancelled` - refund was canceled by merchant action. `success` - refund was successfully processed. `failure` - refund failed during processing (this is really more of an internal state) | `string` |
| amount | Amount to be refunded in the currency indicated | `number` |
| currency | reference currency used for the refund, usually the same as the currency used to create the invoice. | `string` |
| immediate | whether funds should be removed from merchant ledger immediately on submission or at time of processing | `boolean` |
| refundFee | the amount of refund fee expressed in terms of pricing currency | `number` |
| lastRefundNotification | the last time notification of buyer was attempted | `date` |
| invoice | associated invoice id | `string` |
| buyerPaysRefundFee | whether the buyer should pay the refund fee (default is `merchant`) | `boolean` |

### HTTP Response

```java
{
    id: 'TPxL75VtRRuoNexUaNQ8da',
    invoice: '8g6B3UsQ9QWvjqFrrYBssm',
    status: 'created',
    amount: 1,
    currency: 'USD',
    refundFee: 0.09,
    immediate: false,
    buyerPaysRefundFee: false,
    requestDate: '2021-12-21T14:42:58.000Z'
}
```

**Error Scenarios & Format**

| Name | Description | Type |
| --- | --- | :---: |
| status | will be “error” in an error scenario | `string` |
| code | six digit code that maps to an error on BitPay’s side | `string` |
| data | will be `null` in an error scenario | `object` |
| Name | error message pertaining to the specific error | `string` |

**Example error response**

```json
{
    "status": "error",
    "code": "010207",
    "data": null,
    "error": "Invalid invoice state for refund"
}
```


## Get existing refund request

`GET /refunds/:refundId`

Facades **`MERCHANT`**

### HTTP Request

**URL Parameters**

| Name | Description | Type | Presence |
| --- | --- | :---: | :---: |
| refundId | The BitPay refund ID. | `string` | **Mandatory** |


```java
Refund retieveRefund = bitpay.getRefund(createRefund.getId());
```

**Response Body Fields**

| Name | Description | Type |
| --- | --- | :---: |
| id | the refund request id | `string` |
| requestDate | the date that the refund request was created (ISO-8601) | `string` |
| status | refund lifecycle status of the request string. `preview` - no funds deducted, refund will not proceed automatically. `created` - funds deducted/allocated if immediate, will proceed when txs are confirmed and required data is collected. `pending` - refund is in process of being fulfilled. `cancelled` - refund was canceled by merchant action. `success` - refund was successfully processed. `failure` - refund failed during processing (this is really more of an internal state) | `string` |
| amount | Amount to be refunded in the currency indicated | `number` |
| currency | reference currency used for the refund, usually the same as the currency used to create the invoice. | `string` |
| immediate | whether funds should be removed from merchant ledger immediately on submission or at time of processing | `boolean` |
| refundFee | the amount of refund fee expressed in terms of pricing currency | `number` |
| lastRefundNotification | the last time notification of buyer was attempted | `date` |
| invoice | associated invoice id | `string` |
| buyerPaysRefundFee | whether the buyer should pay the refund fee (default is `merchant`) | `boolean` |


### HTTP Response

```json
{
    "id": "TPxL75VtRRuoNexUaNQ8da",
    "invoice": "8g6B3UsQ9QWvjqFrrYBssm",
    "status": "created",
    "amount": 1,
    "currency": "USD",
    "refundFee": 0.09,
    "immediate": false,
    "buyerPaysRefundFee": false,
    "requestDate": "2021-12-21T14:42:58.000Z"
}
```

## Get all refund requests on an invoice

`GET /refunds?invoiceId=:invoiceId`

Facades **`MERCHANT`**

### HTTP Request

**URL Parameters**

| Parameter | Description | Type | Presence |
| --- | --- | :---: | :---: |
| invoiceId | The id of the invoice you want to look up. | `string` | Mandatory |
| ?token= | The resource token for the `invoiceId` you want to look up. You need to retrieve this token from the invoice object itself, using the `merchant` facade. | `string` | Mandatory |

**Headers**

| Fields | Description | Presence |
| --- | --- | :---: |
| X-Accept-Version | Must be set to `2.0.0` for requests to the BitPay API. | Mandatory |
| Content-Type | must be set to `application/json` for requests to the BitPay API. | Mandatory |
| X-Identity | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | Mandatory |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (`merchant` facade). When using standard `pos` facade token directly from the [BitPay dashboard](https://test.bitpay.com/dashboard/merchant/api-tokens) (with `"Require Authentication"` disabled), this header is not needed. | Mandatory |

To get all refund requesta for an invoice, pass the Invoice Id with URL parameter

```java
Refund retrievedRefunds = bitpay.getRefunds(invoice.getId());
```

Response body fields same as `POST /refunds`

### HTTP Response

```json
[
    {
        "id":"H9DpXzrNLxSzpKcVKPDqD1",
        "invoice":"8g6B3UsQ9QWvjqFrrYBssm",
        "status":"canceled",
        "amount":1,
        "currency":"USD",
        "lastRefundNotification":"2021-12-21T14:34:14.695Z",
        "refundFee":0.09,
        "immediate":false,
        "buyerPaysRefundFee":false,
        "requestDate":"2021-12-21T14:34:14.000Z"
    },
    {
        "id":"AtgkAGnW9RpAwqWs22mTF8",
        "invoice":"8g6B3UsQ9QWvjqFrrYBssm",
        "status":"canceled",
        "amount":1,
        "currency":"USD",
        "refundFee":0.09,
        "immediate":false,
        "buyerPaysRefundFee":false,
        "requestDate":"2021-12-21T14:34:21.000Z"
    },
    {
        "id":"TPxL75VtRRuoNexUaNQ8da",
        "invoice":"8g6B3UsQ9QWvjqFrrYBssm",
        "status":"canceled",
        "amount":1,
        "currency":"USD",
        "refundFee":0.09,
        "immediate":false,
        "buyerPaysRefundFee":false,
        "requestDate":"2021-12-21T14:42:58.000Z"
    },
    ...
]

```


## Update a Refund

Update status of refund request from preview to created

`PUT /refunds/:refundId`

Facades **`MERCHANT`**


### HTTP Request

**URL Parameters**

| Name | Description | Type | Presence |
| --- | --- | :---: | :---: |
| refundId | The BitPay refund ID. | `string` | **Mandatory** |
| status | set to ‘created’ in order to confirm the refund request and initiate the flow to send it to shopper | `string` | - |

```java
Refund updateRefund  = bitpay.updateRefund(invoice.getId(), RefundStatus.Created);
```


## Cancel a Refund 

No special parameters, if successful will return refund request with updated status ('cancelled')

`DELETE /refunds/:refundId`

Facades **`MERCHANT`**

### HTTP Request

**URL Parameters**

| Name | Description | Type | Presence |
| --- | --- | :---: | :---: |
| refundId | The refund Id for the refund to be cancelled. | `string` | **Mandatory** |

```java 
Refund cancelRefund = bitpay.cancelRefund(createRefund.getId());
```

## Request a Refund Notification

`POST /refunds/:refundId/notifications`

Facades **`MERCHANT`**

```java
Boolean result = bitpay.sendRefundNotification(createRefund.getId());
```

### HTTP Response

| Name | Description | Type |  
| --- | --- | :---: | 
| status | successful response status will be always be “success” | `string` |



### [Back to guide index](GUIDE.md)