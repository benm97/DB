SELECT DISTINCT Patient.pid, pname
FROM  Doctor,Patient, Visit
WHERE Patient.pid = Visit.pid and specialty='orthopedist' and Visit.did = Doctor.did
INTERSECT
SELECT DISTINCT Patient.pid, pname
FROM Visit, Doctor,Patient
WHERE Patient.pid = Visit.pid and Visit.did = Doctor.did AND specialty='pediatrician'
ORDER by pid, pname  ASC
