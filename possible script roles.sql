DROP SCHEMA IF EXISTS shoppingcart;
CREATE SCHEMA shoppingcart;

CREATE TABLE shoppingcart.USERS (
	USER_ID NUMERIC(10) NOT NULL,
    NAME VARCHAR(50) NOT NULL,
    LAST_NAME VARCHAR(50),
    BIO VARCHAR(200),
    EMAIL VARCHAR(200) NOT NULL,
    PASSWORD varchar(200) NOT NULL,
    AREA_OF_INTEREST VARCHAR(300),
    PROFILE_PICTURE_URL VARCHAR(300) DEFAULT "public/default-profile-picture.jpg",
    PRIMARY KEY (USER_ID)
);

CREATE TABLE shoppingcart.PRODUCTS(
	PRODUCT_ID NUMERIC(10) NOT NULL,
    NAME VARCHAR (100) NOT NULL,
    PRICE NUMERIC(10) NOT NULL,
    IMAGE VARCHAR(300),
    DESCRIPTION VARCHAR(200),
    TOTAL_PRODUCTS_INVENTORY NUMERIC(10) NOT NULL,
    STATUS BOOLEAN NOT NULL,
    PRIMARY KEY (PRODUCT_ID)
);

CREATE TABLE shoppingcart.ORDER_HISTORY(
	ORDER_ID NUMERIC(10) NOT NULL,
    ORDER_DATE DATE NOT NULL,
    USER_ID NUMERIC (10),
    PRODUCT_ID NUMERIC (10),
    PRIMARY KEY (ORDER_ID)
);

CREATE TABLE shoppingcart.WISHLIST(
	WISH_ID NUMERIC(10) NOT NULL PRIMARY KEY,
    USER_ID NUMERIC(10),
    PRODUCT_ID NUMERIC(10)
);

CREATE TABLE shoppingcart.ROLES(
	ROLE_ID NUMERIC(10) NOT NULL PRIMARY KEY,
    NAME varchar(25) NOT NULL
);

CREATE TABLE shoppingcart.USERS_ROLES(
	USER_ID NUMERIC(10) NOT NULL,
    ROLE_ID NUMERIC(10) NOT NULL
);

ALTER TABLE shoppingcart.USERS_ROLES
	ADD FOREIGN KEY (USER_ID) REFERENCES shoppingcart.USERS(USER_ID),
    ADD FOREIGN KEY (ROLE_ID) REFERENCES shoppingcart.ROLES(ROLE_ID);

ALTER TABLE shoppingcart.ORDER_HISTORY
ADD FOREIGN KEY (USER_ID) REFERENCES shoppingcart.USERS(USER_ID),
ADD FOREIGN KEY (PRODUCT_ID) REFERENCES shoppingcart.PRODUCTS(PRODUCT_ID);

ALTER TABLE shoppingcart.WISHLIST
	ADD FOREIGN KEY (USER_ID) REFERENCES shoppingcart.USERS(USER_ID),
	ADD FOREIGN KEY (PRODUCT_ID) REFERENCES shoppingcart.PRODUCTS(PRODUCT_ID);

USE shoppingcart;
INSERT INTO USERS(USER_ID, NAME, LAST_NAME, BIO, EMAIL, PASSWORD, AREA_OF_INTEREST)
VALUES
	(1, 'Fernando', 'Cervantes', 'An Admin Bio', 'fer@gmail.com', '$2a$10$Zvjr.iUM3bqean7RDp3RxO/cnMshNZrd37mOJKOukiFut5JW95xUW', 'Everything'),
	(2, 'Juan', 'Hernandez', 'A Customer Bio', 'juan@gmail.com', '$2a$10$Zvjr.iUM3bqean7RDp3RxO/cnMshNZrd37mOJKOukiFut5JW95xUW', 'Tech');

INSERT INTO PRODUCTS(PRODUCT_ID, NAME, PRICE, IMAGE, DESCRIPTION, TOTAL_PRODUCTS_INVENTORY, STATUS)
VALUES
	(1, 'MSR-7', 329, 'public/product-1.jpg', 'MSR-7 Description', 10, 1),
    (2, 'PRO-2', 199, 'public/product-2.jpg', 'PRO-2 Description', 0, 1),
    (3, 'Xbox Controller', 29, 'public/product-3.jpg', 'Xbox Controller Description', 3, 1),
    (4, 'Smart Watch', 19, 'public/product-4.jpg', 'Smart Watch Description', 5, 1),
    (5, 'LSTQ', 199, 'public/product-5.jpg', 'LSTQ Description', 0, 1),
    (6, 'Protector', 9, 'public/product-6.jpg', 'Protector Description', 20, 1),
    (7, 'Smart Pro 2', 399, 'public/product-7.jpg', 'Smart Pro Description', 3, 1),
    (8, 'Regular Watch', 5, 'public/product-8.jpg', 'Regular Watch Description', 3, 1),
    (9, 'ZML', 199, 'public/product-9.jpg', 'ZML Description', 4, 1),
    (10, 'Xbox Controller Pro', 79, 'public/product-10.jpg', 'Xbox Controller Pro Description', 4, 1),
	(11, 'MSR-7 (2)', 329, 'public/product-1.jpg', 'MSR-7 (2) Description', 10, 1),
    (12, 'PRO-2 (2)', 199, 'public/product-2.jpg', 'PRO-2 (2) Description', 0, 1),
    (13, 'Xbox Controller (2)', 29, 'public/product-3.jpg', 'Xbox Controller (2) Description', 3, 1),
    (14, 'Smart Watch (2)', 19, 'public/product-4.jpg', 'Smart Watch (2) Description', 5, 1),
    (15, 'LSTQ (2)', 199, 'public/product-5.jpg', 'LSTQ (2) Description', 0, 1),
    (16, 'Protector (2)', 9, 'public/product-6.jpg', 'Protector (2) Description', 20, 1),
    (17, 'Smart Pro 2 (2)', 399, 'public/product-7.jpg', 'Smart Pro (2) Description', 3, 1),
    (18, 'Regular Watch (2)', 5, 'public/product-8.jpg', 'Regular Watch (2) Description', 3, 1),
    (19, 'ZML (2)', 199, 'public/product-9.jpg', 'ZML (2) Description', 4, 1),
    (20, 'Xbox Controller Pro (2)', 79, 'public/product-10.jpg', 'Xbox Controller (2) Pro Description', 4, 1)
;

INSERT INTO ROLES VALUES (1,'ADMIN'),(2,'USER');
INSERT INTO USERS_ROLES VALUES (1,1),(2,2);

SELECT * FROM USERS;
SELECT * FROM ROLES;
SELECT * FROM USERS_ROLES;
SELECT * FROM PRODUCTS;
SELECT * FROM ORDER_HISTORY;
SELECT * FROM WISHLIST;