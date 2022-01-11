insert into artikels(naam, aankoopprijs, verkoopprijs, houdbaarheid, garantie, soort) values
    ('testFoodArtikel', 1.5, 3, 10, null, 'F'),
    ('testNonFoodArtikel', 1.5, 3, null, 10, 'NF');


insert into kortingen(artikelid, vanafAantal, percentage) values
    ((select id from artikels where naam = 'testFoodArtikel'), 1, 10);

insert into kortingen(artikelid, vanafAantal, percentage) values
    ((select id from artikels where naam = 'testNonFoodArtikel'), 1, 10);