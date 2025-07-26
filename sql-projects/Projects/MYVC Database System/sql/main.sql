CREATE DATABASE myvc;
USE myvc;

CREATE TABLE TeamFormations (
  FormationID INT AUTO_INCREMENT PRIMARY KEY,
  DateTime DATETIME NOT NULL,
  Address VARCHAR(255),
  Team1ID INT,
  Team2ID INT,
  Score1 INT,
  Score2 INT,
  FOREIGN KEY (Team1ID) REFERENCES Teams(TeamID),
  FOREIGN KEY (Team2ID) REFERENCES Teams(TeamID)
);

-- Disable foreign key checks for clean slate
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS EmailLogs;
DROP TABLE IF EXISTS Payments;
DROP TABLE IF EXISTS ClubMembers_Teams;
DROP TABLE IF EXISTS Teams;
DROP TABLE IF EXISTS Memberships;
DROP TABLE IF EXISTS ClubMembers;
DROP TABLE IF EXISTS SecondaryFamilyMembers;
DROP TABLE IF EXISTS FamilyMembers;
DROP TABLE IF EXISTS Personnel_Assignments;
DROP TABLE IF EXISTS Personnel;
DROP TABLE IF EXISTS CommonInfo;
DROP TABLE IF EXISTS Locations;
DROP TABLE IF EXISTS Period;
SET FOREIGN_KEY_CHECKS = 1;

-- Time period reference
CREATE TABLE Period (
  PeriodID INT AUTO_INCREMENT PRIMARY KEY,
  StartDate DATE NOT NULL,
  EndDate DATE
);

-- Club locations
CREATE TABLE Locations (
  LocationID INT AUTO_INCREMENT PRIMARY KEY,
  Type ENUM('Head', 'Branch') NOT NULL,
  Name VARCHAR(100) NOT NULL,
  Address VARCHAR(255),
  City VARCHAR(100),
  Province VARCHAR(50),
  PostalCode VARCHAR(10),
  PhoneNumber VARCHAR(20),
  WebAddress VARCHAR(100),
  MaxCapacity INT
);

-- Shared info for all people
CREATE TABLE CommonInfo (
  CommonID INT AUTO_INCREMENT PRIMARY KEY,
  FirstName VARCHAR(100),
  LastName VARCHAR(100),
  DateOfBirth DATE,
  SSN CHAR(9) UNIQUE NOT NULL,
  MedicareCardNumber VARCHAR(20) UNIQUE NOT NULL,
  PhoneNumber VARCHAR(20),
  Email VARCHAR(100),
  Address VARCHAR(255),
  City VARCHAR(100),
  Province VARCHAR(50),
  PostalCode VARCHAR(10)
);

-- Club personnel
CREATE TABLE Personnel (
  PersonnelID INT AUTO_INCREMENT PRIMARY KEY,
  CommonID INT NOT NULL,
  Role ENUM('Administrator', 'Captain', 'Coach', 'Assistant Coach', 'Other', 'General Manager', 'Deputy Manager', 'Treasurer', 'Secretary') NOT NULL,
  Mandate ENUM('Volunteer', 'Salaried') NOT NULL,
  LocationID INT NOT NULL,
  FOREIGN KEY (CommonID) REFERENCES CommonInfo(CommonID),
  FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

-- Personnel assignment periods
CREATE TABLE Personnel_Assignments (
  AssignmentID INT AUTO_INCREMENT PRIMARY KEY,
  PersonnelID INT NOT NULL,
  LocationID INT NOT NULL,
  PeriodID INT NOT NULL,
  FOREIGN KEY (PersonnelID) REFERENCES Personnel(PersonnelID),
  FOREIGN KEY (LocationID) REFERENCES Locations(LocationID),
  FOREIGN KEY (PeriodID) REFERENCES Period(PeriodID)
);

-- Primary family members
CREATE TABLE FamilyMembers (
  FamilyMemberID INT AUTO_INCREMENT PRIMARY KEY,
  CommonID INT NOT NULL,
  LocationID INT,
  Relationship ENUM('Father', 'Mother', 'Grandfather', 'Grandmother', 'Uncle', 'Ant', 'Tutor', 'Partner', 'Friend', 'Other'),
  FOREIGN KEY (CommonID) REFERENCES CommonInfo(CommonID),
  FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

-- Secondary family members
CREATE TABLE SecondaryFamilyMembers (
  SecondaryID INT AUTO_INCREMENT PRIMARY KEY,
  PrimaryFamilyMemberID INT NOT NULL,
  CommonID INT NOT NULL,
  Relationship ENUM('Father', 'Mother', 'Grandfather', 'Grandmother', 'Uncle', 'Ant', 'Tutor', 'Partner', 'Friend', 'Other'),
  FOREIGN KEY (PrimaryFamilyMemberID) REFERENCES FamilyMembers(FamilyMemberID),
  FOREIGN KEY (CommonID) REFERENCES CommonInfo(CommonID)
);

-- Club members
CREATE TABLE ClubMembers (
  MemberID INT AUTO_INCREMENT PRIMARY KEY,
  CommonID INT NOT NULL,
  FamilyMemberID INT NOT NULL,
  LocationID INT,
  Height DECIMAL(5, 2),
  Weight DECIMAL(5, 2),
  FOREIGN KEY (CommonID) REFERENCES CommonInfo(CommonID),
  FOREIGN KEY (FamilyMemberID) REFERENCES FamilyMembers(FamilyMemberID),
  FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

-- Membership payments
CREATE TABLE Memberships (
  MembershipID INT AUTO_INCREMENT PRIMARY KEY,
  MemberID INT NOT NULL,
  Year INT NOT NULL,
  Status ENUM('Active', 'Inactive') DEFAULT 'Active',
  TotalPaid DECIMAL(10, 2) DEFAULT 0,
  DonationAmount DECIMAL(10, 2) AS (GREATEST(TotalPaid - 100, 0)) STORED,
  FOREIGN KEY (MemberID) REFERENCES ClubMembers(MemberID)
);

-- Volleyball teams
CREATE TABLE Teams (
  TeamID INT AUTO_INCREMENT PRIMARY KEY,
  TeamName VARCHAR(100),
  TeamType ENUM('Boys', 'Girls'),
  LocationID INT NOT NULL,
  FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

-- Team assignments
CREATE TABLE ClubMembers_Teams (
  TeamID INT,
  MemberID INT,
  LocationID INT,
  Role ENUM('Outside Hitter', 'Opposite', 'Setter', 'Middle Blocker', 'Libero', 'Defensive Specialist', 'Serving Specialist'),
  PRIMARY KEY (TeamID, MemberID),
  FOREIGN KEY (TeamID) REFERENCES Teams(TeamID),
  FOREIGN KEY (MemberID) REFERENCES ClubMembers(MemberID),
  FOREIGN KEY (LocationID) REFERENCES Locations(LocationID)
);

-- Payment tracking
CREATE TABLE Payments (
  PaymentID INT AUTO_INCREMENT PRIMARY KEY,
  MembershipID INT NOT NULL,
  PaymentDate DATE NOT NULL,
  Amount DECIMAL(10, 2) NOT NULL,
  PaymentMethod ENUM('Cash', 'Debit', 'Credit'),
  FOREIGN KEY (MembershipID) REFERENCES Memberships(MembershipID)
);

-- Email logs
CREATE TABLE EmailLogs (
  EmailID INT AUTO_INCREMENT PRIMARY KEY,
  EmailDate DATETIME,
  Sender VARCHAR(100),
  Recipient VARCHAR(100),
  CC VARCHAR(255),
  Subject VARCHAR(255),
  BodyStart VARCHAR(100)
);

-- Trigger: Deactivate members over 18
DELIMITER //
CREATE TRIGGER trg_deactivate_over_18
BEFORE INSERT ON Memberships
FOR EACH ROW
BEGIN
  DECLARE dob DATE;
  DECLARE age INT;
  SELECT DateOfBirth INTO dob
  FROM CommonInfo
  WHERE CommonID = (SELECT CommonID FROM ClubMembers WHERE MemberID = NEW.MemberID);
  SET age = TIMESTAMPDIFF(YEAR, dob, CURDATE());
  IF age > 18 THEN
    SET NEW.Status = 'Inactive';
  END IF;
END;//
DELIMITER ;


-- Q1. Get complete details for every location

SELECT 
    L.*, 
    CI.FirstName AS GeneralManagerFirstName, 
    CI.LastName AS GeneralManagerLastName,
    (SELECT COUNT(*) FROM Personnel WHERE LocationID = L.LocationID) AS NumPersonnel,
    (SELECT COUNT(*) FROM ClubMembers WHERE LocationID = L.LocationID) AS NumClubMembers
FROM Locations L
LEFT JOIN Personnel P ON L.LocationID = P.LocationID AND P.Role = 'General Manager'
LEFT JOIN CommonInfo CI ON P.CommonID = CI.CommonID
ORDER BY L.Province, L.City;

-- Q2. Family members with active club members (aged 11–18)
SELECT 
    CI.FirstName, CI.LastName, COUNT(*) AS NumActiveMembers
FROM FamilyMembers FM
JOIN CommonInfo CI ON FM.CommonID = CI.CommonID
JOIN ClubMembers CM ON FM.FamilyMemberID = CM.FamilyMemberID
JOIN CommonInfo CI2 ON CM.CommonID = CI2.CommonID
WHERE TIMESTAMPDIFF(YEAR, CI2.DateOfBirth, CURDATE()) BETWEEN 11 AND 18
GROUP BY FM.FamilyMemberID;

-- Q3. Personnel at a given location (e.g., ID = 1)
SELECT 
    CI.FirstName, CI.LastName, CI.DateOfBirth, CI.SSN, CI.MedicareCardNumber,
    CI.PhoneNumber, CI.Email, CI.Address, CI.City, CI.Province, CI.PostalCode,
    P.Role, P.Mandate
FROM Personnel P
JOIN CommonInfo CI ON P.CommonID = CI.CommonID
WHERE P.LocationID = 1;

-- Q4. List of all club members with location and active status

SELECT 
    M.MembershipID, CI.FirstName, CI.LastName,
    TIMESTAMPDIFF(YEAR, CI.DateOfBirth, CURDATE()) AS Age,
    L.Name AS LocationName,
    M.Status
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN Memberships M ON CM.MemberID = M.MemberID
JOIN Locations L ON CM.LocationID = L.LocationID
ORDER BY L.Name, Age;


-- Q5. Show club members registered under a given family member
SELECT 
    M.MembershipID, CI.FirstName, CI.LastName, CI.DateOfBirth, CI.SSN, CI.MedicareCardNumber,
    CI.PhoneNumber, CI.Address, CI.City, CI.Province, CI.PostalCode,
    FM.Relationship, M.Status
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN Memberships M ON CM.MemberID = M.MemberID
JOIN FamilyMembers FM ON CM.FamilyMemberID = FM.FamilyMemberID
WHERE FM.FamilyMemberID = 1
ORDER BY M.MembershipID;

-- Q6. Show family members who have active club members and personnel in same location
SELECT DISTINCT 
    CI.FirstName AS FamilyMemberFirstName,
    CI.LastName AS FamilyMemberLastName,
    CI.PhoneNumber
FROM FamilyMembers FM
JOIN CommonInfo CI ON FM.CommonID = CI.CommonID
JOIN ClubMembers CM ON FM.FamilyMemberID = CM.FamilyMemberID
JOIN Memberships M ON CM.MemberID = M.MemberID
JOIN Personnel P ON FM.LocationID = P.LocationID
WHERE M.Status = 'Active'
  AND P.Role IN ('General Manager', 'Deputy Manager', 'Treasurer', 'Secretary', 'Coach', 'Assistant Coach', 'Captain')
ORDER BY CI.FirstName, CI.LastName;

-- Q7. Payment history of a given member (e.g., MemberID = 1)
SELECT 
    P.PaymentDate,
    P.Amount,
    YEAR(P.PaymentDate) AS YearPaid
FROM Payments P
JOIN Memberships M ON P.MembershipID = M.MembershipID
WHERE M.MemberID = 1
ORDER BY P.PaymentDate;


-- Q8. Total membership fees paid & donations collected in 2025

SELECT 
    SUM(P.Amount) AS TotalFeesPaid,
    SUM(M.DonationAmount) AS TotalDonations
FROM Payments P
JOIN Memberships M ON P.MembershipID = M.MembershipID
WHERE YEAR(P.PaymentDate) = 2025;


-- Q9. All team formations in a given location and week

SELECT 
    TF.DateTime,
    TF.Address,
    TF.FormationID,
    T1.TeamName AS Team1,
    T2.TeamName AS Team2,
    TF.Score1,
    TF.Score2,
    CI.FirstName AS HeadCoachFirstName,
    CI.LastName AS HeadCoachLastName
FROM TeamFormations TF
JOIN Teams T1 ON TF.Team1ID = T1.TeamID
JOIN Teams T2 ON TF.Team2ID = T2.TeamID
JOIN Personnel P ON P.LocationID = T1.LocationID AND P.Role = 'Coach'
JOIN CommonInfo CI ON P.CommonID = CI.CommonID
WHERE WEEK(TF.DateTime) = WEEK(CURDATE())  -- this week
ORDER BY TF.DateTime;

-- Q10. Active club members in ≥3 locations, max membership 3 years
SELECT 
    CM.MemberID, CI.FirstName, CI.LastName
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN Memberships M ON CM.MemberID = M.MemberID
WHERE M.Status = 'Active'
  AND TIMESTAMPDIFF(YEAR, CI.DateOfBirth, CURDATE()) BETWEEN 11 AND 18
GROUP BY CM.MemberID
HAVING COUNT(DISTINCT CM.LocationID) >= 3 AND MAX(M.Year) - MIN(M.Year) <= 3
ORDER BY CM.MemberID;

-- Q11. Report on teams' formations in given date range
SELECT 
    L.Name AS LocationName,
    COUNT(CASE WHEN TF.Score1 IS NULL THEN 1 END) AS NumTrainingSessions,
    COUNT(CASE WHEN TF.Score1 IS NOT NULL THEN 1 END) AS NumGameSessions,
    COUNT(*) AS TotalFormations
FROM TeamFormations TF
JOIN Teams T ON TF.Team1ID = T.TeamID
JOIN Locations L ON T.LocationID = L.LocationID
WHERE TF.DateTime BETWEEN '2025-01-01' AND '2025-03-31'
GROUP BY L.LocationID
HAVING COUNT(CASE WHEN TF.Score1 IS NOT NULL THEN 1 END) >= 2
ORDER BY NumGameSessions DESC;

-- Q12. Active members never assigned to any formation
SELECT 
    M.MembershipID, CI.FirstName, CI.LastName, 
    TIMESTAMPDIFF(YEAR, CI.DateOfBirth, CURDATE()) AS Age,
    CI.PhoneNumber, CI.Email, L.Name AS Location
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN Memberships M ON CM.MemberID = M.MemberID
JOIN Locations L ON CM.LocationID = L.LocationID
WHERE CM.MemberID NOT IN (
    SELECT MemberID FROM ClubMembers_Teams
)
AND M.Status = 'Active'
ORDER BY L.Name, M.MembershipID;

-- Q13. Active members only ever assigned as outside hitter
SELECT DISTINCT 
    CM.MemberID, CI.FirstName, CI.LastName, 
    TIMESTAMPDIFF(YEAR, CI.DateOfBirth, CURDATE()) AS Age,
    CI.PhoneNumber, CI.Email, L.Name AS Location
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN ClubMembers_Teams CMT ON CM.MemberID = CMT.MemberID
JOIN Locations L ON CM.LocationID = L.LocationID
WHERE CM.MemberID NOT IN (
    SELECT MemberID FROM ClubMembers_Teams WHERE Role <> 'Outside Hitter'
)
ORDER BY L.Name, CM.MemberID;

-- Q14. Active members played all 7 roles at least once

SELECT 
    CM.MemberID, CI.FirstName, CI.LastName, CI.PhoneNumber, CI.Email, L.Name AS Location
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN ClubMembers_Teams CMT ON CM.MemberID = CMT.MemberID
JOIN Locations L ON CM.LocationID = L.LocationID
WHERE CM.MemberID IN (
    SELECT MemberID
    FROM ClubMembers_Teams
    GROUP BY MemberID
    HAVING COUNT(DISTINCT Role) = 7
)
ORDER BY L.Name, CM.MemberID;

-- Q15. List of family members who are also captains for same location
SELECT 
    CI.FirstName, CI.LastName, CI.PhoneNumber
FROM FamilyMembers FM
JOIN CommonInfo CI ON FM.CommonID = CI.CommonID
JOIN ClubMembers CM ON FM.FamilyMemberID = CM.FamilyMemberID
JOIN ClubMembers_Teams CMT ON CM.MemberID = CMT.MemberID
JOIN Teams T ON CMT.TeamID = T.TeamID
WHERE CM.MemberID IN (
    SELECT MemberID FROM ClubMembers_Teams WHERE Role = 'Captain'
)
AND FM.LocationID = T.LocationID
GROUP BY FM.FamilyMemberID;

-- Q16. Members who never lost a game they played
SELECT DISTINCT 
    CM.MemberID, CI.FirstName, CI.LastName, 
    TIMESTAMPDIFF(YEAR, CI.DateOfBirth, CURDATE()) AS Age,
    CI.PhoneNumber, CI.Email, L.Name AS Location
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN ClubMembers_Teams CMT ON CM.MemberID = CMT.MemberID
JOIN Teams T ON CMT.TeamID = T.TeamID
JOIN Locations L ON T.LocationID = L.LocationID
WHERE CM.MemberID NOT IN (
    SELECT CMT.MemberID
    FROM ClubMembers_Teams CMT
    JOIN TeamFormations TF ON CMT.TeamID = TF.Team1ID OR CMT.TeamID = TF.Team2ID
    WHERE (CMT.TeamID = TF.Team1ID AND TF.Score1 < TF.Score2)
       OR (CMT.TeamID = TF.Team2ID AND TF.Score2 < TF.Score1)
)
ORDER BY L.Name, CM.MemberID;

-- Q17. Report of all past/current treasurers

SELECT 
    CI.FirstName, CI.LastName, PA.PeriodID, PE.StartDate, PE.EndDate
FROM Personnel P
JOIN Personnel_Assignments PA ON P.PersonnelID = PA.PersonnelID
JOIN Period PE ON PA.PeriodID = PE.PeriodID
JOIN CommonInfo CI ON P.CommonID = CI.CommonID
WHERE P.Role = 'Treasurer'
ORDER BY CI.FirstName, CI.LastName, PE.StartDate;

-- Q18. Deactivated members due to age > 18
SELECT 
    CI.FirstName, CI.LastName, CI.PhoneNumber, CI.Email,
    M.Status, M.MembershipID, L.Name AS LastLocation, P.Role AS LastRole
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN Memberships M ON CM.MemberID = M.MemberID
JOIN Locations L ON CM.LocationID = L.LocationID
LEFT JOIN Personnel P ON P.LocationID = CM.LocationID
WHERE M.Status = 'Inactive'
  AND TIMESTAMPDIFF(YEAR, CI.DateOfBirth, CURDATE()) > 18
ORDER BY L.Name, P.Role, CI.FirstName, CI.LastName;

-- Q19. Show trigger(s) used in your system
SHOW TRIGGERS;

-- Q20. Demonstrate constraint logic (e.g., conflicting team assignment)

SELECT 
    CMT1.MemberID,
    TF1.DateTime AS FirstFormation,
    TF2.DateTime AS SecondFormation,
    TIMESTAMPDIFF(HOUR, TF1.DateTime, TF2.DateTime) AS HoursDiff
FROM ClubMembers_Teams CMT1
JOIN TeamFormations TF1 ON CMT1.TeamID = TF1.Team1ID OR CMT1.TeamID = TF1.Team2ID
JOIN ClubMembers_Teams CMT2 ON CMT1.MemberID = CMT2.MemberID AND CMT1.TeamID <> CMT2.TeamID
JOIN TeamFormations TF2 ON CMT2.TeamID = TF2.Team1ID OR CMT2.TeamID = TF2.Team2ID
WHERE DATE(TF1.DateTime) = DATE(TF2.DateTime)
  AND ABS(TIMESTAMPDIFF(HOUR, TF1.DateTime, TF2.DateTime)) < 3;

-- Q21. Demonstrate email logging (team formation / deactivation)
SELECT * FROM EmailLogs;



