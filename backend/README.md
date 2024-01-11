# Orders101.
## Endpoints
### POST /orders
1. payload example
```Javascript
{
    "basket": {
        "lineItems": [
            {
                "name": "line1",
                "description": "hammers line items",
                "unitPrice": 11.99,
                "quantity": 1
            },
            {
                "name": "line2",
                "description": "nails line items",
                "unitPrice": 0.99,
                "quantity": 200
            },
            {
                "name": "line3",
                "description": "stepladders line items",
                "unitPrice": 21.99,
                "quantity": 1
            }
        ]
    },
    "customer": {
        "invoiceAddress": {
            "addressLine1": "Test Street",
            "postCode": "T3ST",
            "country": "England"
        }
    },
    "paymentStatus": "AUTHORISED",
    "orderStatus": "COMPLETED",
    "deliveryAddress": {
        "addressLine1": "Test Street",
        "postCode": "T3ST",
        "country": "England"
    },
    "createdDate": "2023-10-30"
}
```
### PATCH /orders
1. payload example
```Javascript
{
  "id": "1",
      "basket": {
    "id": "1",
        "lineItems": [
      {
        "id": "1",
        "name": "hammer",
        "description": "test",
        "unitPrice": 4.99,
        "quantity": 10
      },
      {
        "id": "2",
        "name": "screwdriver",
        "description": "test1",
        "unitPrice": 10.99,
        "quantity": 12
      },
      {
        "id": "3",
        "name": "drill",
        "description": "test2",
        "unitPrice": 65.00,
        "quantity": 2
      }
    ]
  },
  "totalPrice": null,
  "customer": {
    "id": "1",
        "invoiceAddress": {
      "id": "1",
          "addressLine1": "Test Street",
          "postCode": "England",
          "country": "T3ST"
    },
    "name": "John",
        "email": "cust1@gmail.com"
  },
  "paymentStatus": "CAPTURED",
      "orderStatus": "COMPLETED",
      "deliveryAddress": {
    "id": "1",
        "addressLine1": "Test Street",
        "postCode": "England",
        "country": "T3ST"
  },
  "createdDate": "2023-10-30"
}
```
2. etc
