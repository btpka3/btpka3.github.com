SELECT
	T.`name`                AS `name`,
	T.`age`                 AS `age`
FROM S,
    LATERAL TABLE(MyPersonUdtf(MAP(
        'name',                 S.`name`,
        'age',                  S.`age`
    ))) AS T (
        `name`,
        `age`
    )


/*
SELECT *
FROM S

*/

/*
SELECT
	T.`name`                AS `name`,
	T.`age`                 AS `age`
FROM
    lateral table(MyPersonUdtf(MAP(
        'name',                 S.`name`,
        'age',                  S.`age`
    ))) as T (
        `name`,
        `age`
    )
 */