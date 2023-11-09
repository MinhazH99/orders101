# Orders101
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
2. etc