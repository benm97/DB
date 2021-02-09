SELECT DISTINCT did
FROM            Patient, Visit
WHERE           Visit.pid = Patient.pid 
GROUP BY        (Visit.did)
HAVING          AVG(Patient.bmi) >= ALL(
                                  SELECT   AVG(Patient.bmi)
								  FROM     Visit, Patient
								  WHERE    Visit.pid = Patient.pid 
								  GROUP BY (Visit.did)
								  )
ORDER BY        Visit.did ASC;
