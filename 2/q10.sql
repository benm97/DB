DELETE FROM Patient 
WHERE pid NOT IN (
SELECT pid FROM Visit
) 
