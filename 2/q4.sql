SELECT DISTINCT pid, did
FROM Patient, Doctor
EXCEPT
SELECT DISTINCT pid, did
FROM Visit
ORDER BY pid, did ASC
