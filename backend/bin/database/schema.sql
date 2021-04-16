BEGIN TRANSACTION;

DROP TABLE IF EXISTS closets;
DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS parent_child;
DROP TABLE IF EXISTS children;
DROP TABLE IF EXISTS mascots;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
 INCREMENT BY 1
 NO MAXVALUE
 NO MINVALUE
 CACHE 1;
 
CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	email varchar(50), -- ADDED
	active boolean DEFAULT true,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role, first_name, last_name, email, active) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'user', 'user', 'fake@email.com', DEFAULT);
INSERT INTO users (username,password_hash,role, first_name, last_name, email, active) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'admin', 'admin', 'fake@email.com', DEFAULT);

CREATE TABLE items (
    item_id serial NOT NULL,
    item_type varchar(50) NOT NULL,
    item_style varchar(50) NOT NULL,
    item_color varchar(50) NOT NULL,
    item_price int NOT NULL,
    description varchar(200),
    CONSTRAINT PK_item PRIMARY KEY (item_id)
);

--HATS
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Hat', 'Ballcap', 'Red', 5, 'Red baseball hat');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Hat', 'Ballcap', 'Blue', 5, 'Blue baseball hat');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Hat', 'Ballcap', 'Black', 5, 'Black baseball hat');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Hat', 'Beanie', 'Red', 5, 'Red beanie');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Hat', 'Beanie', 'Blue', 5, 'Blue beanie');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Hat', 'Beanie', 'Black', 5, 'Black beanie');
--TOPS

INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Shortsleeve', 'Red', 5, 'Red shortsleeve shirt');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Shortsleeve', 'Blue', 5, 'Blue shortsleeve shirt');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Shortsleeve', 'Black', 5, 'Purple shortsleeve shirt');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Longsleeve', 'Red', 5, 'Red longsleeve shirt');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Longsleeve', 'Blue', 5, 'Blue longsleeve shirt');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Longsleeve', 'Purple', 5, 'Purple longsleeve shirt');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Tank top', 'Red', 5, 'Red tanktop');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Tank top', 'Blue', 5, 'Blue tanktop');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Top', 'Tank top', 'Purple', 5, 'Purple tanktop');

--BOTTOMS
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Bottom', 'Shorts', 'Red', 5, 'Red shorts');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Bottom', 'Shorts', 'Blue', 5, 'Blue shorts');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Bottom', 'Shorts', 'Purple', 5, 'Purple shorts');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Bottom', 'Pants', 'Red', 5, 'Red pants');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Bottom', 'Pants', 'Blue', 5, 'Blue pants');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Bottom', 'Pants', 'Purple', 5, 'Purple pants');

--SHOES
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Shoes', 'Sneakers', 'Red', 5, 'Red sneakers');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Shoes', 'Sneakers', 'Blue', 5, 'Blue sneakers');
INSERT INTO items (item_type, item_style, item_color, item_price, description) VALUES ('Shoes', 'Sneakers', 'Purple', 5, 'Purple sneakers');

CREATE TABLE mascots (
    mascot_id serial NOT NULL,
    name varchar(50) NOT NULL,
    price int NOT NULL,
    CONSTRAINT PK_mascot PRIMARY KEY (mascot_id)
);

INSERT INTO mascots (name, price) VALUES ('Human', 25);
INSERT INTO mascots (name, price) VALUES ('Alien', 50);

CREATE TABLE children (
    child_user_id int NOT NULL,
    steps int DEFAULT 0,
    playtime_balance int DEFAULT 0,
    carrot_balance int DEFAULT 0,
    activity_minutes int DEFAULT 0,
    mascot_id int DEFAULT 1,
    CONSTRAINT PK_child PRIMARY KEY (child_user_id),
    FOREIGN KEY (child_user_id) REFERENCES users (user_id),
    FOREIGN KEY (mascot_id) REFERENCES mascots (mascot_id)
);

CREATE TABLE parent_child (
    parent_user_id int NOT NULL,
    child_user_id int NOT NULL,
    CONSTRAINT PK_parent_child_parent_user_id_child_user_id PRIMARY KEY (parent_user_id, child_user_id),
    FOREIGN KEY (child_user_id) REFERENCES children (child_user_id),
    FOREIGN KEY (parent_user_id) REFERENCES users (user_id) 
);     

CREATE TABLE closets (
    closet_id serial NOT NULL,
    child_user_id int NOT NULL,
    item_id int NULL,
    mascot_id int DEFAULT 1,
    equipped boolean DEFAULT FALSE,
    CONSTRAINT PK_closet PRIMARY KEY (closet_id, child_user_id, mascot_id),
    FOREIGN KEY (child_user_id) REFERENCES children (child_user_id),
    FOREIGN KEY (item_id) REFERENCES items (item_id)
);

CREATE TABLE purchases (
    purchase_id serial NOT NULL,
    item_id int NULL, 
    mascot_id int NULL,
    child_user_id int NOT NULL,
    purchase_date DATE NOT NULL,
    payment_amount int NOT NULL,
    CONSTRAINT PK_purchases PRIMARY KEY (purchase_id),
    FOREIGN KEY (child_user_id) REFERENCES children (child_user_id),
    FOREIGN KEY (item_id) REFERENCES items (item_id)
);

INSERT INTO users (username, password_hash, role, first_name, last_name, email, active) VALUES ('parent-username', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_USER', 'parent-first', 'parent-last', 'test-parent@gmail.com', DEFAULT);
INSERT INTO users (username, password_hash, role, first_name, last_name, email, active) VALUES ('child-username', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'ROLE_CHILD', 'child-first', 'child-last', 'test-child@gmail.com', DEFAULT);

INSERT INTO children (child_user_id) VALUES ((SELECT user_id FROM users WHERE first_name ILIKE 'child-first'));

INSERT INTO parent_child (parent_user_id, child_user_id) VALUES ((SELECT user_id FROM users WHERE first_name ILIKE 'parent-first'), (SELECT user_id FROM users WHERE first_name ILIKE 'child-first'));

INSERT INTO closets (closet_id, child_user_id, item_id) VALUES (DEFAULT, (SELECT user_id FROM users WHERE first_name ILIKE 'child-first'), DEFAULT);

COMMIT TRANSACTION;
