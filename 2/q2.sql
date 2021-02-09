SELECT DISTINCT pname 
FROM Patient, Visit, Doctor
WHERE Patient.pid = Visit.pid and Visit.did = Doctor.did
 and Visit.fee=0 and Doctor.dname='Avi Cohen'
ORDER BY pname ASC
 