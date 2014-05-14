-- ref: http://www.h2database.com/html/datatypes.html

DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  ID IDENTITY,
  NAME VARCHAR(255),
  AGE INT,
  MALE BOOLEAN,
  BIRTHDAY DATE,
  IMG_DATA VARBINARY,
  VERSION INT
);

INSERT INTO USER(NAME,        AGE,  MALE,   BIRTHDAY,     IMG_DATA,            VERSION)
           VALUES ('zhang3',    21,   true,   '1981-01-01', RAWTOHEX('111111'), 1);
INSERT INTO USER(NAME,        AGE,  MALE,   BIRTHDAY,     IMG_DATA,            VERSION)
           VALUES ('li4',       22,   false,  '1982-02-02', RAWTOHEX('222222'), 1);
INSERT INTO USER(NAME,        AGE,  MALE,   BIRTHDAY,     IMG_DATA,            VERSION)
           VALUES ('wang5',     23,   false,  '1983-03-03', RAWTOHEX('333333'), 1);
