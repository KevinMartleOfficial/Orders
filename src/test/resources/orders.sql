insert into orders (werknemerId, omschrijving, bedrag, goedgekeurd)
values
    ((select id from werknemers where voornaam = 'Jos'), 'test1', 5, NULL),
    ((select id from werknemers where voornaam = 'Piet'), 'test2', 7, NULL),
    ((select id from werknemers where voornaam = 'Katrien'), 'test3', 10, NULL);
