-- ref: http://www.h2database.com/html/datatypes.html

DROP TABLE IF EXISTS PERSON;

CREATE TABLE PERSON (
  ID IDENTITY,
  NAME VARCHAR(255),
  AGE INT,
  MALE BOOLEAN,
  BIRTHDAY DATE,
  IMG_DATA VARBINARY
);

INSERT INTO PERSON(NAME,        AGE,  MALE,   BIRTHDAY,     IMG_DATA)
           VALUES ('zhang3',    21,   true,   '1981-01-01', RAWTOHEX('111111'));
INSERT INTO PERSON(NAME,        AGE,  MALE,   BIRTHDAY,     IMG_DATA)
           VALUES ('li4',       22,   false,  '1982-02-02', RAWTOHEX('222222'));
INSERT INTO PERSON(NAME,        AGE,  MALE,   BIRTHDAY,     IMG_DATA)
           VALUES ('wang5',     23,   false,  '1983-03-03', RAWTOHEX('333333'));
