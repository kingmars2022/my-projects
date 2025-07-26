SHOW TABLES;
SELECT * FROM Period;
SELECT * FROM Locations;
SELECT * FROM CommonInfo;
SELECT * FROM Personnel;
SELECT * FROM Personnel_Assignments;
SELECT * FROM FamilyMembers;
SELECT * FROM ClubMembers;
SELECT * FROM Memberships;
SELECT * FROM Teams;
SELECT * FROM ClubMembers_Teams;
SELECT * FROM Payments;
SELECT * FROM SessionTypes;
SELECT * FROM PlayerRoles;
SELECT * FROM TeamFormations;
SELECT * FROM TeamFormationPlayers;
SELECT * FROM EmailLogs;
SELECT * FROM SecondaryFamilyMembers;
SELECT * FROM SecondaryFamilyMemberRelationships;


-- Insert sample data for Locations
INSERT INTO Locations (Name, Type, Address, City, Province, PostalCode, PhoneNumber, WebAddress, MaxCapacity) VALUES
('MYVC Head Office', 'Head', '123 Main St', 'Montreal', 'Quebec', 'H1A 1A1', '514-555-0123', 'www.myvc.ca', 100),
('MYVC Laval Branch', 'Branch', '456 Laval Ave', 'Laval', 'Quebec', 'H7A 2B2', '450-555-0123', 'www.myvc.ca/laval', 80),
('MYVC Longueuil Branch', 'Branch', '789 Longueuil Blvd', 'Longueuil', 'Quebec', 'J4K 3C3', '450-555-0124', 'www.myvc.ca/longueuil', 80),
('MYVC Brossard Branch', 'Branch', '101 Brossard St', 'Brossard', 'Quebec', 'J4W 1A2', '438-555-0125', 'www.myvc.ca/brossard', 75),
('MYVC Quebec City Branch', 'Branch', '202 Quebec Ave', 'Quebec City', 'Quebec', 'G1A 3B4', '418-555-0126', 'www.myvc.ca/quebec', 90),
('MYVC Gatineau Branch', 'Branch', '303 Gatineau Rd', 'Gatineau', 'Quebec', 'J8Y 4A2', '819-555-0127', 'www.myvc.ca/gatineau', 85),
('MYVC Sherbrooke Branch', 'Branch', '404 Sherbrooke Blvd', 'Sherbrooke', 'Quebec', 'J1L 3B5', '819-555-0128', 'www.myvc.ca/sherbrooke', 70),
('MYVC Trois-Rivières Branch', 'Branch', '505 Trois-Rivières St', 'Trois-Rivières', 'Quebec', 'G9A 4T1', '819-555-0129', 'www.myvc.ca/trois-rivieres', 80),
('MYVC Montreal North Branch', 'Branch', '606 Montreal North Ave', 'Montreal', 'Quebec', 'H2A 3C6', '514-555-0130', 'www.myvc.ca/montrealnorth', 90),
('MYVC Laval East Branch', 'Branch', '707 Laval East Ave', 'Laval', 'Quebec', 'H7N 2X1', '450-555-0131', 'www.myvc.ca/lavaleast', 75);

-- Insert sample data for CommonInfo (Personnel & Family Members)
INSERT INTO CommonInfo (FirstName, LastName, DateOfBirth, SSN, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode) VALUES
('John', 'Smith', '1980-01-01', '123456789', 'ABC123456', '514-555-0125', 'john.smith@myvc.ca', '123 Main St', 'Montreal', 'Quebec', 'H1A 1A1'),
('Jane', 'Doe', '1985-02-15', '987654321', 'XYZ789012', '514-555-0126', 'jane.doe@myvc.ca', '456 Laval Ave', 'Laval', 'Quebec', 'H7A 2B2'),
('Mike', 'Johnson', '1990-03-20', '456789123', 'DEF456789', '450-555-0127', 'mike.johnson@myvc.ca', '789 Longueuil Blvd', 'Longueuil', 'Quebec', 'J4K 3C3'),
('Robert', 'Wilson', '1975-04-10', '111222333', 'GHI789012', '514-555-0128', 'robert.wilson@email.com', '321 Oak St', 'Montreal', 'Quebec', 'H1A 1A2'),
('Mary', 'Brown', '1978-05-25', '444555666', 'JKL012345', '450-555-0129', 'mary.brown@email.com', '654 Pine Ave', 'Laval', 'Quebec', 'H7A 2B3'),
('Alice', 'Green', '2013-06-18', '222333444', 'LMN567890', '438-555-0130', 'alice.green@email.com', '890 Elm St', 'Brossard', 'Quebec', 'J4W 1A3'),
('Brian', 'Adams', '2013-09-12', '555666777', 'OPQ678901', '418-555-0131', 'brian.adams@email.com', '321 Maple St', 'Quebec City', 'Quebec', 'G1A 3B5'),
('Sam', 'O’Connor', '2013-11-21', '678123456', 'PQR987654', '514-555-0132', 'sam.oconnor@email.com', '432 Oak Ave', 'Montreal', 'Quebec', 'H1A 1B3'),
('Lisa', 'Davis', '2013-03-12', '234567891', 'STU345678', '450-555-0133', 'lisa.davis@email.com', '543 Maple Ave', 'Laval', 'Quebec', 'H7A 2B4'),
('Chris', 'Thompson', '2013-09-01', '987123456', 'VWX567890', '514-555-0134', 'chris.thompson@email.com', '654 Oak Blvd', 'Longueuil', 'Quebec', 'J4K 3D5');

-- Insert sample data for Personnel
INSERT INTO Personnel (CommonID, Role, Mandate, LocationID) VALUES
(1, 'General Manager', 'Salaried', 1),
(2, 'Coach', 'Salaried', 2),
(3, 'Coach', 'Salaried', 3),
(4, 'Assistant Coach', 'Volunteer', 4),
(5, 'Treasurer', 'Salaried', 5),
(6, 'Secretary', 'Volunteer', 1),
(7, 'Administrator', 'Salaried', 2),
(8, 'Coach', 'Salaried', 6),
(9, 'Coach', 'Salaried', 7),
(10, 'Administrator', 'Salaried', 3);

-- Insert sample data for Family Members
INSERT INTO FamilyMembers (CommonID, LocationID, Relationship) VALUES
(4, 1, 'Father'),
(5, 2, 'Mother'),
(6, 3, 'Friend'),
(7, 4, 'Tutor'),
(8, 5, 'Friend'),
(9, 6, 'Friend'),
(10, 7, 'Partner');

-- Insert sample data for Club Members
INSERT INTO CommonInfo (FirstName, LastName, DateOfBirth, SSN, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode) VALUES
('Tom', 'Wilson', '2010-07-15', '123123123', 'PQR678901', '514-555-0131', 'tom.wilson@email.com', '321 Oak St', 'Montreal', 'Quebec', 'H1A 1A2'),
('Sarah', 'Brown', '2010-08-20', '456456456', 'STU901234', '450-555-0132', 'sarah.brown@email.com', '654 Pine Ave', 'Laval', 'Quebec', 'H7A 2B3'),
('Jack', 'Miller', '2010-10-05', '789789789', 'UVW345678', '514-555-0133', 'jack.miller@email.com', '890 Elm St', 'Brossard', 'Quebec', 'J4W 1A3'),
('David', 'Green', '2011-01-30', '555123555', 'PQR456789', '438-555-0134', 'david.green@email.com', '123 Maple St', 'Montreal', 'Quebec', 'H1A 1A4'),
('Emma', 'Adams', '2012-02-12', '666234666', 'RST567890', '450-555-0135', 'emma.adams@email.com', '321 Pine Ave', 'Laval', 'Quebec', 'H7A 2B5'),
('Megan', 'White', '2013-03-18', '777345777', 'UVW678901', '514-555-0136', 'megan.white@email.com', '432 Oak Ave', 'Brossard', 'Quebec', 'J4W 1A4');

INSERT INTO ClubMembers (CommonID, FamilyMemberID, LocationID, Height, Weight) VALUES
(6, 1, 1, 170, 65),
(7, 2, 2, 165, 60),
(8, 3, 3, 180, 75),
(9, 4, 4, 160, 55),
(10, 5, 5, 175, 70),
(11, 6, 6, 150, 50),
(12, 7, 7, 185, 80);

-- Insert sample data for Teams
INSERT INTO Teams (TeamName, TeamType, LocationID) VALUES
('Montreal Boys U15', 'Boys', 1),
('Laval Girls U16', 'Girls', 2),
('Brossard Boys U17', 'Boys', 3),
('Montreal Girls U14', 'Girls', 1),
('Laval Boys U18', 'Boys', 2),
('Brossard Girls U12', 'Girls', 3),
('Quebec City Boys U16', 'Boys', 4),
('Montreal Boys U13', 'Boys', 5),
('Laval Girls U17', 'Girls', 6),
('Sherbrooke Boys U14', 'Boys', 7);




-- Insert sample data for Team Formations
INSERT INTO TeamFormations (TeamID, SessionTypeID, SessionDate, StartTime, EndTime, Address, Score) VALUES
(1, 1, '2025-03-20', '18:00:00', '20:00:00', '123 Main St, Montreal', NULL),
(2, 2, '2025-03-21', '19:00:00', '21:00:00', '456 Laval Ave, Laval', NULL),
(3, 1, '2025-03-22', '17:00:00', '19:00:00', '890 Elm St, Brossard', NULL),
(4, 2, '2025-03-23', '16:00:00', '18:00:00', '321 Oak St, Montreal', NULL),
(5, 1, '2025-03-24', '18:00:00', '20:00:00', '543 Maple Ave, Laval', NULL),
(6, 2, '2025-03-25', '19:00:00', '21:00:00', '432 Oak Ave, Brossard', NULL),
(7, 1, '2025-03-26', '17:00:00', '19:00:00', '654 Pine Ave, Laval', NULL),
(8, 2, '2025-03-27', '18:00:00', '20:00:00', '890 Elm St, Montreal', NULL),
(9, 1, '2025-03-28', '19:00:00', '21:00:00', '321 Oak Blvd, Longueuil', NULL),
(10, 2, '2025-03-29', '16:00:00', '18:00:00', '432 Oak Ave, Montreal', NULL);

-- Insert sample data for Team Formation Players
INSERT INTO TeamFormationPlayers (FormationID, MemberID, RoleID) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 1),
(5, 5, 2),
(6, 6, 3),
(7, 7, 1);

-- Insert sample data for Email Logs
INSERT INTO EmailLogs (EmailDate, Sender, Recipient, CCList, Subject, BodyPreview, EmailType) VALUES
(NOW(), 'system@myvc.ca', 'tom.wilson@email.com', NULL, 'Team Formation Update', 'Dear Tom, Your upcoming training session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'sarah.brown@email.com', NULL, 'Team Formation Update', 'Dear Sarah, Your upcoming game session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'jack.miller@email.com', NULL, 'Team Formation Update', 'Dear Jack, Your upcoming training session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'david.green@email.com', NULL, 'Team Formation Update', 'Dear David, Your upcoming training session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'emma.adams@email.com', NULL, 'Team Formation Update', 'Dear Emma, Your upcoming game session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'megan.white@email.com', NULL, 'Team Formation Update', 'Dear Megan, Your upcoming training session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'chris.thompson@email.com', NULL, 'Team Formation Update', 'Dear Chris, Your upcoming training session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'lisa.davis@email.com', NULL, 'Team Formation Update', 'Dear Lisa, Your upcoming game session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'sam.oconnor@email.com', NULL, 'Team Formation Update', 'Dear Sam, Your upcoming training session details...', 'Team Formation'),
(NOW(), 'system@myvc.ca', 'robert.wilson@email.com', NULL, 'Team Formation Update', 'Dear Robert, Your upcoming game session details...', 'Team Formation');
