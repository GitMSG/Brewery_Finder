BEGIN TRANSACTION;

DROP TABLE IF EXISTS users                      cascade;
DROP TABLE IF EXISTS brewery                    cascade;
DROP TABLE IF EXISTS beer                       cascade;
DROP TABLE IF EXISTS review                     cascade;
DROP TABLE IF EXISTS brewer                     cascade;


CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar(255) NOT NULL UNIQUE,     -- Username
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  role varchar(255) NOT NULL default('user'),
  picture varchar(255)
);

CREATE TABLE brewer (
  id serial PRIMARY KEY,
  user_id integer,
  brewery_id integer,
  awards text[],
  experience varchar(64)
);
  
CREATE TABLE brewery (
  id serial PRIMARY KEY,
  name varchar(255),
  address varchar(64),
  city varchar(64),
  zipcode integer,
  description varchar(800),
  image varchar(255),
  hours_of_operation varchar(255),
  phone_number varchar(64),
  email varchar(64),
  website varchar(64)
);

CREATE TABLE beer (
  id serial PRIMARY KEY,
  brewery_id varchar(10),
  name varchar(64),
  description varchar(255),
  abv integer,
  ibu integer,
  type varchar(64),
  image varchar(255),
  rating double precision
);

CREATE TABLE review (
  id serial PRIMARY KEY,
  beer_id varchar(64),
  rating integer,
  text varchar(255),
  username varchar(255)
);

ALTER TABLE brewer
ADD FOREIGN KEY(user_id)
REFERENCES users(id);

ALTER TABLE brewer
ADD FOREIGN KEY(brewery_id)
REFERENCES brewery(id);

--ALTER TABLE beer
--ADD FOREIGN KEY(brewery_id)
--REFERENCES brewery(id);

--ALTER TABLE review
--ADD FOREIGN KEY(beer_id)
--REFERENCES beer(id);

--ALTER TABLE review
--ADD FOREIGN KEY(username)
--REFERENCES users(username);

INSERT INTO brewery(name, address, city, zipcode, description, image, hours_of_operation, phone_number, email, website)
        VALUES('Great Lakes', '2516 Market Ave', 'Cleveland, Oh', 44113, 'Two Irish brothers with limited brewing experience. A city that shuttered its last production brewery in the early 80s. A neighborhood in serious need of a facelift. In 1986 when Patrick and Daniel Conway opened their fledgling operation in Clevelands Ohio City neighborhood, the odds were stacked against them. Fortunately, they surrounded themselves with a staff of passionate, knowledgeable people, and from the start committed themselves to bringing a sophisticated, diverse selection of craft beer to their home state.', 
        'https://res.cloudinary.com/brillhart/image/upload/v1576700843/x5te06spdvxfqd1fh0cc.jpg', '12:00PM,2:00AM', '(216) 771-4404','info@greatlakes.com','https://www.greatlakesbrewing.com/')
;       
INSERT INTO brewery(name, address, city, zipcode, description, image, hours_of_operation, phone_number, email, website)        
        VALUES('Crooked Pecker Brewing', '8284 E Washington St', 'Chagrin Falls, OH', 44233, 'A small brewery in northeast Ohio', 'https://res.cloudinary.com/brillhart/image/upload/v1576711754/fwfrrwku5r8ihgfipgjg.png', '12:00PM,2:00AM', '(440) 384-3452','crookedpeckers@gmail.com','http://www.crookedpeckerbrewing.com/')
;
INSERT INTO brewery(name, address, city, zipcode, description, image, hours_of_operation, phone_number, email, website)        
        VALUES('Fat Heads Brewing', '17450 Engle Lake Dr', 'Middleburg Hts, OH', 44130, 'Fat Heads Saloon was founded in 1992 in the historic South Side of Pittsburgh, PA. We established ourselves as an early pioneer of craft beer in the area and remain a premiere craft beer tap house and popular local restaurant.', 
        'https://res.cloudinary.com/brillhart/image/upload/v1576753617/fzdjgmrpgzxztq432hn8.jpg', '12:00PM,2:00AM', '(216) 898-0242','fatheadinfo@gmail.com','http://www.fatheads.com')
;

INSERT INTO beer(brewery_id, name, description, abv, ibu, type, image, rating)
	   VALUES('2', 'ADHDJ',	'A hint of fruitiness, with a smooth finish', 5, 22, 'Pale Ale', 'https://res.cloudinary.com/brillhart/image/upload/v1576751446/tpr3ve3xy0wkj22kl8bg.jpg', 0.0)
;
INSERT INTO beer(brewery_id, name, description, abv, ibu, type, image, rating)
	   VALUES('2', 'IPE (ee-pay)', 'A delicious Brown Ale with coffee.', 5,	30, 'Brown Ale', 'https://res.cloudinary.com/brillhart/image/upload/v1576751619/t3jlyhilnq9am9iri2to.jpg', 0.0)
;
INSERT INTO beer(brewery_id, name, description, abv, ibu, type, image, rating)
	   VALUES('3', 'Head Hunter', 'Head Hunter is an aggressively dry-hopped, West Coast-Style IPA with a huge hop display of pine, grapefruit, citrus and pineapple. A punch-you-in-the-mouth brew for those who truly love their hops! Uncivilized. Aggressive. Award-Winning.'
	   , 7,	87, 'West Coast-Style IPA', 'https://res.cloudinary.com/brillhart/image/upload/v1576753800/qqnhqhvoa5xjij8vzdjl.jpg', 0.0)
;
INSERT INTO beer(brewery_id, name, description, abv, ibu, type, image, rating)
	   VALUES('3', 'Gudenhoppy', 'Grassy, more grains, medium hoppiness, a bit more dark and rounded leaning like Czech style. Crisp, spicy floral on finish.'
	   , 5,	10, 'German Pilsner', 'https://res.cloudinary.com/brillhart/image/upload/v1576754072/qvvaz6hu1nzglutmus8u.png', 0.0)
;
	   
update users set role = 'admin' where username = 'matt';


COMMIT TRANSACTION;
