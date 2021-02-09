CREATE TABLE Plane(
Plane_ID INTEGER PRIMARY KEY,
Model VARCHAR(50) NOT NULL,
Year DATE NOT NULL
);
CREATE TABLE Flight(
Flight_ID INTEGER PRIMARY KEY,
Hour INTEGER NOT NULL,
Flight_date DATE NOT NULL
);
CREATE TABLE Pilot(
Pilot_ID INTEGER PRIMARY KEY,
Name VARCHAR(50) NOT NULL
);
CREATE TABLE Fly( 
Pilot_ID INTEGER ,
Flight_ID INTEGER,
PRIMARY KEY(Pilot_ID,Flight_ID),
FOREIGN KEY(Pilot_ID) 
	REFERENCES Pilot(Pilot_ID),
FOREIGN KEY(Flight_ID) 
	REFERENCES Flight(Flight_ID)
);
CREATE TABLE Seat(
Letter CHAR(1)CHECK(letter='A' or letter='B' or letter='C' or letter='D' or letter='E' or letter='F' or letter='G' or letter='H'),
Seat_number INTEGER CHECK (seat_number>1 and seat_number<21) ,
Flight_ID INTEGER,
PRIMARY KEY(Flight_ID,Seat_number,Letter),
FOREIGN KEY(Flight_ID) 
	REFERENCES Flight(Flight_ID)
);
CREATE TABLE Customer(
Customer_ID INTEGER PRIMARY KEY,
Name VARCHAR(50) NOT NULL ,
Phone VARCHAR(50) NOT NULL ,
Birth_day DATE 
);
CREATE TABLE VIP(
Customer_ID INTEGER PRIMARY KEY,
Points INTEGER NOT NULL,
FOREIGN KEY(Customer_ID) 
	REFERENCES Customer(Customer_ID)
);
CREATE TABLE First_class(
Flight_ID INTEGER,
Letter VARCHAR(1) CHECK(letter='A' or letter='B' or letter='C' or letter='D' or letter='E' or letter='F' or letter='G' or letter='H'),
Seat_number INTEGER CHECK (seat_number>1 and seat_number<21),
PRIMARY KEY(Flight_ID,Seat_number,letter),
FOREIGN KEY(Flight_ID,Seat_number,Letter) 
	REFERENCES Seat (Flight_ID,Seat_number,Letter) 
);

CREATE TABLE Orders(
Customer_ID INTEGER NOT NULL,
Price INTEGER CHECK(price>0),
Letter CHAR(1) NOT NULL CHECK(letter='A' or letter='B' or letter='C' or letter='D' or letter='E' or letter='F' or letter='G' or letter='H') ,
Seat_number INTEGER NOT NULL CHECK (seat_number>1 and seat_number<21), 
Flight_ID INTEGER NOT NULL,
PRIMARY KEY(Flight_ID,Seat_number,Letter),
FOREIGN KEY(Flight_ID,Seat_number,Letter) 
	REFERENCES Seat (Flight_ID,Seat_number,Letter),
FOREIGN KEY(Customer_ID) 
	REFERENCES Customer(Customer_ID)
);
CREATE TABLE Orders_VIP(
Customer_ID INTEGER NOT NULL,
Price INTEGER CHECK(price>0),
Letter CHAR(1) NOT NULL CHECK(letter='A' or letter='B' or letter='C' or letter='D' or letter='E' or letter='F' or letter='G' or letter='H'), 
Seat_number INTEGER NOT NULL CHECK (seat_number>1 and seat_number<21), 
Flight_ID INTEGER NOT NULL,
PRIMARY KEY(Flight_ID,Seat_number,Letter),
FOREIGN KEY(Flight_ID,Seat_number,Letter) 
	REFERENCES Seat (Flight_ID,Seat_number,Letter),
FOREIGN KEY(Customer_ID) 
	REFERENCES Customer(Customer_ID)
);
