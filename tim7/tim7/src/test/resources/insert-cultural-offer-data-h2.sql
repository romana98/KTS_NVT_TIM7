insert into cultural_offers (id, name, start_date, end_date, location_id, subcategory_id, description) values (1,'Exit', '2020-07-08', '2020-07-11 22:34:31 UTC', 1, 1, 'Proslava 20. godišnjice EXIT festivala biće održana u njegovom poznatom punom formatu od 8. do 11. jula 2021. na Petrovaradinskoj tvrđavi.');

insert into newsletters (id, name, published_date, cultural_offer_id, picture_id, description) values (1, 'Newsletter1', '2020-03-01', 1, 1, 'Nulla ac enim.');
insert into newsletters (id, name, published_date, cultural_offer_id, picture_id, description) values (2, 'Newsletter2', '2020-11-19', 1, 1, 'Pellentesque ultrices mattis odio.');
insert into newsletters (id, name, published_date, cultural_offer_id, picture_id, description) values (3, 'Newsletter3', '2020-01-31', 1, 1, 'Etiam faucibus cursus urna. Ut tellus.');

insert into subscribed_cultural_offers (cultural_offer_id, registered_id) values (1, 2);
insert into subscribed_cultural_offers (cultural_offer_id, registered_id) values (1, 3);

update comments set cultural_offer_id = 1;
