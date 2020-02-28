DROP TABLE  IF EXISTS entreprise;

CREATE TABLE entreprise  (
    id bigint serial NOT NULL PRIMARY KEY,
    idFourni int,
    rs VARCHAR(500),
    dateCreation VARCHAR(40)
);

DROP TABLE  IF EXISTS err;
CREATE TABLE err  (
    id bigint serial NOT NULL PRIMARY KEY,
    numLine integer,
    contenu VARCHAR(2000),
    typeException VARCHAR(500),
    stackTrace VARCHAR(2000),
    
    dateCrea VARCHAR(40)
);