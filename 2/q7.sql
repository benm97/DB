SELECT    did, MAX(fee), MIN(fee), AVG(fee)
FROM      Visit
GROUP BY (did)
ORDER BY  did ASC;
