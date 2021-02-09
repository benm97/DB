SELECT DISTINCT dname
FROM            Doctor
WHERE           specialty = 'pediatrician' 
                AND NOT EXISTS (SELECT * 
                                FROM PATIENT
                                WHERE gender = 'M' AND bmi > 30 AND Patient.pid NOT IN(
                                                                    SELECT Visit.pid
                                                                    FROM Visit
                                                                    WHERE Visit.did = Doctor.did
                                                                    )
					 		   );         
