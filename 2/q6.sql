SELECT DISTINCT did
FROM 	Patient, Visit
WHERE	 bmi > 30 AND Visit.pid=Patient.pid
GROUP BY 	Visit.did
HAVING COUNT(*) = 3
ORDER BY did ASC
