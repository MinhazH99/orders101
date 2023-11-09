INSERT INTO ADDRESS
VALUES
    ('1','Test Street','T3ST','England');

INSERT INTO CUSTOMER
VALUES
    ('1','cust1@gmail.com','1','John');

INSERT INTO LINE_ITEM
VALUES
    ('15',125.10,'test','1','hammer'),
    ('25',100,'test1','2','screwdriver'),
    ('40',65,'test2','3','drill');

INSERT INTO BASKET
VALUES
    ('1');

INSERT INTO BASKET_LINE_ITEMS
VALUES
    ('1','1'),
    ('1','2'),
    ('1','3');


INSERT INTO orders
VALUES
    (DATE '2023-10-30','1','1','1','1','COMPLETED','AUTHORISED');