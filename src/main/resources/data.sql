INSERT INTO CUSTOMER
VALUES
    ('1','cust1@gmail.com','John');

INSERT INTO LINE_ITEM
VALUES
    ('15',125.10,'test','hammer','1'),
    ('25',100,'test1','screwdriver','2'),
    ('40',65,'test2','drill','3');

INSERT INTO BASKET
VALUES
    ('1');

INSERT INTO BASKET_PRODUCT
VALUES
    ('1','1'),
    ('1','2'),
    ('1','3');

INSERT INTO orders
VALUES
    (DATE '2023-10-30', 0,0,'1','1','1');