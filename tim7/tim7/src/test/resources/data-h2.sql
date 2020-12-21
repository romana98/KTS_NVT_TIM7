insert into administrators (id, username, email, password, verified) values (nextval('person_seq'),'mico', 'mico@admin.com', '$2y$12$NFN7DJUX1lFfaDX1tc9/6uBtgls9SZOU9iwjhrlXJc0xr471vgKAK', true);
insert into registered (id, username, email, password, verified) values (NEXTVAL('person_seq'), 'micoR', 'mico@reg.com', '$2y$12$NFN7DJUX1lFfaDX1tc9/6uBtgls9SZOU9iwjhrlXJc0xr471vgKAK', true); 
insert into registered (id, username, email, password, verified) values (NEXTVAL('person_seq'), 'nijeMico', 'notmico@reg.com', '$2y$12$NFN7DJUX1lFfaDX1tc9/6uBtgls9SZOU9iwjhrlXJc0xr471vgKAK', true); 

insert into pictures (picture, cultural_offer_id) values ('http://dummyimage.com/129x140.bmp', null);
insert into pictures (picture, cultural_offer_id) values ('http://dummyimage.com/219x161.bmp', null);

insert into categories (name) values ('Category1');
insert into subcategories (name, category_id) values ('Subcategory1', 1);
insert into locations (name, latitude, longitude) values ('Novi Sad', 45.25167, 19.83694);

insert into cultural_offers (name, start_date, end_date, location_id, subcategory_id, description) values ('Exit', '2020-07-08', '2020-07-11 22:34:31 UTC', 1, 1, 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.');
insert into cultural_offers (name, start_date, end_date, location_id, subcategory_id, description) values ('Exit1', '2020-07-08', '2020-07-11 22:34:31 UTC', 1, 1, 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.');

insert into newsletters (name, published_date, cultural_offer_id, picture_id, description) values ('Newsletter1', '2020-03-01', 1, 1, 'Nulla ac enim.');
insert into newsletters (name, published_date, cultural_offer_id, picture_id, description) values ('Newsletter2', '2020-11-19', 1, 1, 'Pellentesque ultrices mattis odio.');
insert into newsletters (name, published_date, cultural_offer_id, picture_id, description) values ('Newsletter3', '2020-01-31', 1, 2, 'Etiam faucibus cursus urna. Ut tellus.');

insert into subscribed_cultural_offers (cultural_offer_id, registered_id) values (1, 2);




