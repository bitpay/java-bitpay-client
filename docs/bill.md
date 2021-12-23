# Bill

## Create a bill
Bills are payment requests addressed to specific buyers. Bill line items have fixed prices, typically denominated in fiat currency.

`POST /bills`

Facade **`MERCHANT`**

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
|  number | Bill identifier, specified by merchant | `string` | Optional |
|  currency | ISO 4217 3-character currency code. This is the currency associated with the price field | `string` | **Mandatory** |
|  name | Bill recipient's name | `string` | Optional |
|  address1 | Bill recipient's address | `string` | Optional |
|  address2 | Bill recipient's address | `string` | Optional |
|  city | Bill recipient's city | `string` | Optional |
|  state | Bill recipient's state or province | `string` | Optional |
| zip | Bill recipient's ZIP code | `string` | Optional |
| country | Bill recipient's country | `string` | Optional |
| email | Bill recipient's email address | `string` | **Mandatory** |
| cc | Email addresses to which a copy of the bill must be sent | `array` | Optional |
| phone | Bill recipient's phone number | `string` | Optional |
| dueDate | Date and time at which a bill is due, ISO-8601 format yyyy-mm-ddThh:mm:ssZ. (UTC) | `string` | Optional |
| passProcessingFee | If set to true, BitPay's processing fee will be included in the amount charged on the invoice | `boolean` | Optional |
| items | List of line items | `array` | **Mandatory** |
| &rarr; description | Line item description | `string` | **Mandatory** |
| &rarr; price | Line item unit price for the corresponding `currency` | `number` | **Mandatory** |
| &rarr; quantity | Bill identifier, specified by merchant | `number` | **Mandatory** |
| token | The API token can be retrieved from the dashboard (limited to pos facade) or using the Tokens resource to get access to the merchant facade. This is described in the section Request an API token).| `string` | **Mandatory** |
```java
// Create a list of items to add in the bill
List<Item> items = new ArrayList<>();
items.add(new Item() {{
    setPrice(30.0);
    setQuantity(9);
    setDescription("product-a");
}});
items.add(new Item() {{
    setPrice(14.0);
    setQuantity(16);
    setDescription("product-b");
}});

Bill bill = new Bill("1111", Currency.USD, "email@merchantsemail.com", items);

bill = bitpay.createBill(bill);
```
### HTTP Response

```json
{
  "facade": "merchant/bill",
  "data": {
    "status": "draft",
    "url": "https://bitpay.com/bill?id=3Zpmji8bRKxWJo2NJbWX5H&resource=bills",
    "number": "bill1234-EFGH",
    "createdDate": "2021-05-21T09:51:04.126Z",
    "dueDate": "2021-05-31T00:00:00.000Z",
    "currency": "USD",
    "name": "John Doe",
    "address1": "2630 Hegal Place",
    "address2": "Apt 42",
    "city": "Alexandria",
    "state": "VA",
    "zip": "23242",
    "country": "US",
    "email": "john@doe.com",
    "cc": [
      "jane@doe.com"
    ],
    "phone": "555-123-456",
    "passProcessingFee": true,
    "emailBill": true,
    "id": "3Zpmji8bRKxWJo2NJbWX5H",
    "merchant": "7HyKWn3d4xdhAMQYAEVxVq",
    "items": [
      {
        "id": "NV35GRWtrdB2cmGEjY4LKY",
        "description": "Test Item 1",
        "price": 6,
        "quantity": 1
      },
      {
        "id": "Apy3i2TpzHRYP8tJCkrZMT",
        "description": "Test Item 2",
        "price": 4,
        "quantity": 1
      }
    ],
    "token": "6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA"
  }
}
```

## Retrieve a bill

`GET /bills/:billid`

Facade **`MERCHANT`**

### HTTP Request

**URL Parameters**
| Parameter | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  ?token= | when fetching settlememts, pass a token as a URL parameter . | `string` | **Mandatory** |

**Headers**
| Fields | Description | Presence
| ------ | ------ | ------ |
|  X-Accept-Version  | must be set to `2.0.0` for requests to the BitPay API  | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API | **Mandatory** | 
|  X-Identity  | the hexadecimal public key generated from the client private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed.  | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. This header is required when using tokens with higher privileges (merchant facade). When using standard pos facade token directly from the BitPay dashboard (with "Require Authentication" disabled), this header is not needed. | **Mandatory** |

```java
Bill bill = bitpay.getBill(bill.getId());
```

### HTTP Response

```json
{
  "facade": "merchant/bill",
  "data": {
    "status": "draft",
    "url": "https://bitpay.com/bill?id=3Zpmji8bRKxWJo2NJbWX5H&resource=bills",
    "number": "bill1234-EFGH",
    "createdDate": "2021-05-21T09:51:04.126Z",
    "dueDate": "2021-05-31T00:00:00.000Z",
    "currency": "USD",
    "name": "John Doe",
    "address1": "2630 Hegal Place",
    "address2": "Apt 42",
    "city": "Alexandria",
    "state": "VA",
    "zip": "23242",
    "country": "US",
    "email": "john@doe.com",
    "cc": [
      "jane@doe.com"
    ],
    "phone": "555-123-456",
    "passProcessingFee": true,
    "emailBill": true,
    "id": "3Zpmji8bRKxWJo2NJbWX5H",
    "merchant": "7HyKWn3d4xdhAMQYAEVxVq",
    "items": [
      {
        "id": "NV35GRWtrdB2cmGEjY4LKY",
        "description": "Test Item 1",
        "price": 6,
        "quantity": 1
      },
      {
        "id": "Apy3i2TpzHRYP8tJCkrZMT",
        "description": "Test Item 2",
        "price": 4,
        "quantity": 1
      }
    ],
    "token": "6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA"
  }
}
```

## Retrieve a bills

`GET /bills/`

Facade **`MERCHANT`**

### HTTP Request

**URL Parameters**
| Parameter | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  ?token=  | when fetching settlememts, pass a merchant facade token as a URL parameter . | `string` | **Mandatory** |
|  &status=  | the bill status you want to query on. | `string` | Optional |

**Headers**
| Fields | Description | Presence
| ------ | ------ | ------ |
|  X-Accept-Version  | must be set to `2.0.0` for requests to the BitPay API  | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API | **Mandatory** | 
|  X-Identity  | the hexadecimal public key generated from the client private key. | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. | **Mandatory** |

```java
List<Bill> bills = bitpay.getBills();
List<Bill> bills = bitpay.getBills(BillStatus.Draft);
```

### HTTP Response

```json
{
  "facade": "merchant/bill",
  "data": [
    {
      "status": "draft",
      "url": "https://bitpay.com/bill?id=X6KJbe9RxAGWNReCwd1xRw&resource=bills",
      "number": "bill1234-ABCD",
      "createdDate": "2021-05-21T09:48:02.373Z",
      "dueDate": "2021-05-31T00:00:00.000Z",
      "currency": "USD",
      "name": "John Doe",
      "address1": "2630 Hegal Place",
      "address2": "Apt 42",
      "city": "Alexandria",
      "state": "VA",
      "zip": "23242",
      "country": "US",
      "email": "john@doe.com",
      "cc": [
        "jane@doe.com"
      ],
      "phone": "555-123-456",
      "passProcessingFee": true,
      "emailBill": true,
      "id": "X6KJbe9RxAGWNReCwd1xRw",
      "merchant": "7HyKWn3d4xdhAMQYAEVxVq",
      "items": [
        {
          "id": "EL4vx41Nxc5RYhbqDthjE",
          "description": "Test Item 1",
          "price": 6,
          "quantity": 1
        },
        {
          "id": "6spPADZ2h6MfADvnhfsuBt",
          "description": "Test Item 2",
          "price": 4,
          "quantity": 1
        }
      ],
      "token": "6EBQR37MgDJPfEiLY3jtRqBMYLg8XSDqhp2kp7VSDqCMHGHnsw4bqnnwQmtehzCvSo"
    },
    {
      "status": "draft",
      "url": "https://bitpay.com/bill?id=3Zpmji8bRKxWJo2NJbWX5H&resource=bills",
      "number": "bill1234-EFGH",
      "createdDate": "2021-05-21T09:51:04.126Z",
      "dueDate": "2021-05-31T00:00:00.000Z",
      "currency": "USD",
      "name": "John Doe",
      "address1": "2630 Hegal Place",
      "address2": "Apt 42",
      "city": "Alexandria",
      "state": "VA",
      "zip": "23242",
      "country": "US",
      "email": "john@doe.com",
      "cc": [
        "jane@doe.com"
      ],
      "phone": "555-123-456",
      "passProcessingFee": true,
      "emailBill": true,
      "id": "3Zpmji8bRKxWJo2NJbWX5H",
      "merchant": "7HyKWn3d4xdhAMQYAEVxVq",
      "items": [
        {
          "id": "NV35GRWtrdB2cmGEjY4LKY",
          "description": "Test Item 1",
          "price": 6,
          "quantity": 1
        },
        {
          "id": "Apy3i2TpzHRYP8tJCkrZMT",
          "description": "Test Item 2",
          "price": 4,
          "quantity": 1
        }
      ],
      "token": "6EBQR37MgDJPfEiLY3jtRq7eTP2aodR5V5wmXyyZhru5FM5yF4RCGKYQtnT7nhwHjA"
    }
  ]
}
```

## Update a bill

`PUT /bills/:billid`

Facade **`MERCHANT`**

### HTTP Request

**URL Parameters**
| Parameter | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  billId  | the id of the bill you want to update . | `string` | **Mandatory** |


**Headers**
| Fields | Description | Presence
| ------ | ------ | ------ |
|  X-Accept-Version  | must be set to `2.0.0` for requests to the BitPay API  | **Mandatory** |
| Content-Type | must be set to `application/json` for requests to the BitPay API | **Mandatory** | 
|  X-Identity  | the hexadecimal public key generated from the client private key. | **Mandatory** |
| X-Signature | header is the ECDSA signature of the full request URL concatenated with the request body, signed with your private key. | **Mandatory** |

**Body**

| Name | Description | Type | Presence |
| --- | --- | :---: | :---: |
| items | List of line items | `array` | Optional |
| &rarr; description | Line item description | `string` | Optional |
| &rarr; price | Buyer's address | `number` | Optional |
| &rarr; quantity | Buyer's appartment or suite number | `number` | Optional |
| currency | Amount to be refunded in the `currency` indicated | `number` | Optional |
| email | reference currency used for the refund, usually the same as the currency used to create the invoice. | `string` | **Mandatory** |
| token | reference currency used for the refund, usually the same as the currency used to create the invoice. | `string` | **Mandatory** |


```java
List<Item> items = new ArrayList<Item>();
items.add(new Item() {{
    setPrice(30.0);
    setQuantity(9);
    setDescription("product-a");
}});
items.add(new Item() {{
    setPrice(14.0);
    setQuantity(16);
    setDescription("product-b");
}});
items.add(new Item() {{
    setPrice(3.90);
    setQuantity(42);
    setDescription("product-c");
}});
items.add(new Item() {{
    setPrice(6.99);
    setQuantity(12);
    setDescription("product-d");
}});

Bill bill = new Bill("7", Currency.USD, "", items);
Bill basicBill = null;
Bill retrievedBill = null;
Bill updatedBill = null;
basicBill = bitpay.createBill(bill);
retrievedBill = bitpay.getBill(basicBill.getId());
retrievedBill.setCurrency(Currency.EUR);
retrievedBill.setName("updatedBill");
retrievedBill.getItems().add(new Item() {{
    setPrice(60.0);
    setQuantity(7);
    setDescription("product-added");
}});
updatedBill = bitpay.updateBill(retrievedBill, retrievedBill.getId());

```

### HTTP Response

```json
{
  "facade": "merchant/bill",
  "data": {
    "url": "https://bitpay.com/bill?id=3Zpmji8bRKxWJo2NJbWX5H&resource=bills",
    "number": "bill1234-EFGH",
    "createdDate": "2021-05-21T09:51:04.126Z",
    "dueDate": "2021-05-31T00:00:00.000Z",
    "currency": "USD",
    "name": "John Doe",
    "address1": "2630 Hegal Place",
    "address2": "Apt 42",
    "city": "Alexandria",
    "state": "VA",
    "zip": "23242",
    "country": "US",
    "email": "john@doe.com",
    "cc": [
      "jane@doe.com"
    ],
    "phone": "555-123-456",
    "passProcessingFee": true,
    "emailBill": true,
    "id": "3Zpmji8bRKxWJo2NJbWX5H",
    "merchant": "7HyKWn3d4xdhAMQYAEVxVq",
    "items": [
      {
        "id": "8vXbhqWDL1A9F66ZwJAiyJ",
        "description": "Test Item 1",
        "price": 6,
        "quantity": 1
      },
      {
        "id": "WmgAaPiyuY9L6vBX7KvF2R",
        "description": "Test Item 2",
        "price": 4,
        "quantity": 1
      },
      {
        "id": "89xhSLYPnLDBczsQHCvJ2D",
        "description": "Test Item 3",
        "price": 5,
        "quantity": 1
      }
    ],
    "token": "7dnoyMe27VDKY1WNrCTqgK5RWbEi4XkvBSUTTwET6XnNYfWKYdrnSyg7myn7oc3vms"
  }
}
```

## Deliver a bill

`GET /bills/:billid/deliveries`

Facade **`MERCHANT`**

### HTTP Request

**URL Parameters**
| Parameter | Description |Type | Presence
| ------ | ------ | ----- |------ |
|  billId  | the id of the bill you want to deliver via email . | `string` | **Mandatory** |

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
|  token | The resource token for the billId you want to deliver via email. You need to retrieve this token from the bill object itself (see section Retrieve a bill). | `string` | **Mandatory** |
```java
String deliveryResult = bitpay.deliverBill(bill.getId(), bill.getToken());
```

### HTTP Response

**Body**

| Name | Description | Type |
| --- | --- | :---: |
| data | set to `"Success"` once a bill is successfully sent via email. | `string` |

```json
{
    "data": "Success"
}
```


### [Back to guide index](GUIDE.md)