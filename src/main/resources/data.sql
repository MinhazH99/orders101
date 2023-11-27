INSERT INTO ADDRESS
VALUES
    ('Test Street','England','1','T3ST'),
    ('Fake Street', 'Scotland','2','E32QD');

INSERT INTO CUSTOMER
VALUES
    ('cust1@gmail.com','1','1','John'),
    ('delete@gmail.com','2','2','Delete');

INSERT INTO LINE_ITEM
VALUES
    ('15',125.10,'test','1','hammer'),
    ('25',100,'test1','2','screwdriver'),
    ('40',65,'test2','3','drill'),
    ('50',37,'test3','4','nails'),
    ('35',27,'test4','5','paint'),
    ('13',17,'test5','6','allen keys');

INSERT INTO BASKET
VALUES
    ('1'),
    ('2');

INSERT INTO BASKET_LINE_ITEMS
VALUES
    ('1','1'),
    ('1','2'),
    ('1','3'),
    ('2','4'),
    ('2','5'),
    ('2','6');


INSERT INTO orders
VALUES
    (DATE '2023-10-30','1','1','1','1','COMPLETED','AUTHORISED'),
    (DATE '2023-11-25','2','2','2','2','COMPLETED','AUTHORISED');