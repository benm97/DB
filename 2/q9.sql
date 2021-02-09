UPDATE Doctor
SET 	Clinic = CASE
		WHEN clinic=1 THEN 2
		ELSE 2
		END
WHERE clinic IN(1,2)

