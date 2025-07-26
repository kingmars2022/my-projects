-- 1. Create/Delete/Edit/Display a Location

-- Create a Location
INSERT INTO Locations (Type, Name, Address, City, Province, PostalCode, PhoneNumber, WebAddress, MaxCapacity)
VALUES ('Branch', 'Montreal South Branch', '100 South St', 'Montreal', 'QC', 'H1B 1B1', '514-555-0007', 'www.myvc-montrealsouth.com', 50);

-- Edit a Location
UPDATE Locations
SET Name = 'Montreal South Branch Updated', PhoneNumber = '514-555-0010'
WHERE LocationID = 5;

-- Delete a Location
DELETE FROM Locations WHERE LocationID = 5;

-- Display Locations
SELECT * FROM Locations;


-- 2. Create/Delete/Edit/Display a Personnel

-- Create a Personnel
INSERT INTO Personnel (CommonID, Role, Mandate, LocationID) 
VALUES (4, 'Assistant Coach', 'Salaried', 2);

-- Edit a Personnel
UPDATE Personnel
SET Role = 'Head Coach', Mandate = 'Salaried'
WHERE PersonnelID = 1;

-- Delete a Personnel
DELETE FROM Personnel WHERE PersonnelID = 3;

-- Display Personnel
SELECT * FROM Personnel;


-- 3. Create/Delete/Edit/Display a FamilyMember (Primary/Secondary)

-- Create a Family Member
INSERT INTO FamilyMembers (CommonID, LocationID, Relationship) 
VALUES (4, 1, 'Father');

-- Edit a Family Member
UPDATE FamilyMembers
SET Relationship = 'Grandfather'
WHERE FamilyMemberID = 2;

-- Delete a Family Member
DELETE FROM FamilyMembers WHERE FamilyMemberID = 3;

-- Display Family Members
SELECT * FROM FamilyMembers;


-- 4. Create/Delete/Edit/Display a ClubMember

-- Create a Club Member
INSERT INTO ClubMembers (CommonID, FamilyMemberID, LocationID, Height, Weight) 
VALUES (4, 1, 1, 170.5, 65.0);

-- Edit a Club Member
UPDATE ClubMembers
SET Height = 172, Weight = 70
WHERE MemberID = 2;

-- Delete a Club Member
DELETE FROM ClubMembers WHERE MemberID = 3;

-- Display Club Members
SELECT * FROM ClubMembers;


-- 5. Create/Delete/Edit/Display a TeamFormation

-- Create a Team Formation
INSERT INTO TeamFormations (TeamID, SessionTypeID, SessionDate, StartTime, EndTime, Address, Score) 
VALUES (1, 1, '2024-02-20', '18:00:00', '20:00:00', '123 Main St, Montreal', NULL);

-- Edit a Team Formation
UPDATE TeamFormations
SET SessionDate = '2024-02-21', StartTime = '19:00:00'
WHERE FormationID = 1;

-- Delete a Team Formation
DELETE FROM TeamFormations WHERE FormationID = 2;

-- Display Team Formations
SELECT * FROM TeamFormations;


-- 6. Assign/Delete/Edit a club member to a team formation (and conflict check)

-- Assign a Club Member to a Team Formation
INSERT INTO TeamFormationPlayers (FormationID, MemberID, RoleID) 
VALUES (1, 1, 1);

-- Check for conflicting assignments (same player in two formations on same day)
SELECT tf.FormationID, tf.SessionDate, tf.StartTime, tfp.MemberID
FROM TeamFormations tf
JOIN TeamFormationPlayers tfp ON tf.FormationID = tfp.FormationID
WHERE tfp.MemberID = 1
AND tf.SessionDate = '2024-02-20';

-- Delete a club member from a team formation
DELETE FROM TeamFormationPlayers WHERE FormationID = 1 AND MemberID = 1;


-- 7. Get complete details for every location

SELECT 
    l.Name,
    l.Address,
    l.City,
    l.Province,
    l.PostalCode,
    l.PhoneNumber,
    l.WebAddress,
    l.Type,
    l.MaxCapacity,
    CONCAT(p.FirstName, ' ', p.LastName) as ManagerName,
    COUNT(DISTINCT cm.MemberID) as ActiveMembers
FROM Locations l
LEFT JOIN Personnel per ON l.LocationID = per.LocationID AND per.Role = 'General Manager'
LEFT JOIN CommonInfo p ON per.CommonID = p.CommonID
LEFT JOIN ClubMembers cm ON l.LocationID = cm.LocationID
GROUP BY l.LocationID
ORDER BY l.Province, l.City;


-- 8. For a given family member, get details of all locations and associated members

SELECT 
    fm.FamilyMemberID,
    CONCAT(ci.FirstName, ' ', ci.LastName) as FamilyMemberName,
    ci.PhoneNumber as FamilyMemberPhone,
    l.Name as LocationName,
    CONCAT(sfm.FirstName, ' ', sfm.LastName) as SecondaryFamilyMemberName,
    sfm.PhoneNumber as SecondaryFamilyMemberPhone,
    cm.MemberID,
    CONCAT(cmci.FirstName, ' ', cmci.LastName) as ClubMemberName,
    cmci.DateOfBirth,
    cmci.SSN,
    cmci.MedicareCardNumber,
    cmci.PhoneNumber as ClubMemberPhone,
    cmci.Address,
    cmci.City,
    cmci.Province,
    cmci.PostalCode,
    sfmr.Relationship as SecondaryFamilyMemberRelationship
FROM FamilyMembers fm
JOIN CommonInfo ci ON fm.CommonID = ci.CommonID
LEFT JOIN Locations l ON fm.LocationID = l.LocationID
LEFT JOIN SecondaryFamilyMembers sfm ON fm.FamilyMemberID = sfm.PrimaryFamilyMemberID
LEFT JOIN CommonInfo sfmci ON sfm.CommonID = sfmci.CommonID
LEFT JOIN SecondaryFamilyMemberRelationships sfmr ON sfm.SecondaryID = sfmr.SecondaryID
LEFT JOIN ClubMembers cm ON sfmr.MemberID = cm.MemberID
LEFT JOIN CommonInfo cmci ON cm.CommonID = cmci.CommonID
WHERE fm.FamilyMemberID = 1;


-- 9. For a given location and week, get team formations

SELECT 
    tf.FormationID,
    CONCAT(p.FirstName, ' ', p.LastName) as HeadCoachName,
    tf.SessionDate,
    tf.StartTime,
    tf.Address,
    st.Type as SessionType,
    t.TeamName,
    tf.Score,
    CONCAT(cmci.FirstName, ' ', cmci.LastName) as PlayerName,
    pr.RoleName as PlayerRole
FROM TeamFormations tf
JOIN Teams t ON tf.TeamID = t.TeamID
JOIN SessionTypes st ON tf.SessionTypeID = st.SessionTypeID
JOIN TeamFormationPlayers tfp ON tf.FormationID = tfp.FormationID
JOIN ClubMembers cm ON tfp.MemberID = cm.MemberID
JOIN CommonInfo cmci ON cm.CommonID = cmci.CommonID
JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID
JOIN Personnel per ON t.LocationID = per.LocationID AND per.Role = 'Coach'
JOIN CommonInfo p ON per.CommonID = p.CommonID
WHERE t.LocationID = 1
AND tf.SessionDate BETWEEN '2024-02-19' AND '2024-02-25'
ORDER BY tf.SessionDate, tf.StartTime;


-- 10. Get details of active members associated with three different locations

SELECT 
    cm.MemberID,
    CONCAT(ci.FirstName, ' ', ci.LastName) as MemberName
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
WHERE cm.LocationID IS NOT NULL
AND TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) BETWEEN 11 AND 18
GROUP BY cm.MemberID
HAVING COUNT(DISTINCT cm.LocationID) >= 3
AND MAX(TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE())) <= 3
ORDER BY cm.MemberID;


-- 11. Report on teams’ formations for a period
SELECT 
    l.Name as LocationName,
    COUNT(CASE WHEN st.Type = 'Training' THEN 1 END) as TotalTrainingSessions,
    COUNT(DISTINCT CASE WHEN st.Type = 'Training' THEN tfp.MemberID END) as TotalTrainingPlayers,
    COUNT(CASE WHEN st.Type = 'Game' THEN 1 END) as TotalGameSessions,
    COUNT(DISTINCT CASE WHEN st.Type = 'Game' THEN tfp.MemberID END) as TotalGamePlayers
FROM Locations l
JOIN Teams t ON l.LocationID = t.LocationID
JOIN TeamFormations tf ON t.TeamID = tf.TeamID
JOIN SessionTypes st ON tf.SessionTypeID = st.SessionTypeID
JOIN TeamFormationPlayers tfp ON tf.FormationID = tfp.FormationID
WHERE tf.SessionDate BETWEEN '2025-01-01' AND '2025-03-31'
GROUP BY l.LocationID
HAVING COUNT(CASE WHEN st.Type = 'Game' THEN 1 END) >= 2
ORDER BY COUNT(CASE WHEN st.Type = 'Game' THEN 1 END) DESC;


-- 12. Active club members never assigned to any formation
SELECT 
    cm.MemberID,
    CONCAT(ci.FirstName, ' ', ci.LastName) as MemberName,
    TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) as Age,
    ci.PhoneNumber,
    ci.Email,
    l.Name as CurrentLocation
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
JOIN Locations l ON cm.LocationID = l.LocationID
WHERE TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) BETWEEN 11 AND 18
AND NOT EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp WHERE tfp.MemberID = cm.MemberID
)
ORDER BY l.Name, cm.MemberID;

-- 13. Club members only assigned as Outside Hitter

SELECT 
    cm.MemberID,
    CONCAT(ci.FirstName, ' ', ci.LastName) as MemberName,
    TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) as Age,
    ci.PhoneNumber,
    ci.Email,
    l.Name as CurrentLocation
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
JOIN Locations l ON cm.LocationID = l.LocationID
WHERE TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) BETWEEN 11 AND 18
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Outside Hitter'
)
AND NOT EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName != 'Outside Hitter'
)
ORDER BY l.Name, cm.MemberID;

-- 14. Club members assigned to all roles

SELECT 
    cm.MemberID,
    CONCAT(ci.FirstName, ' ', ci.LastName) as MemberName,
    TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) as Age,
    ci.PhoneNumber,
    ci.Email,
    l.Name as CurrentLocation
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
JOIN Locations l ON cm.LocationID = l.LocationID
WHERE TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) BETWEEN 11 AND 18
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Outside Hitter'
)
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Opposite'
)
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Setter'
)
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Middle Blocker'
)
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Libero'
)
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Defensive Specialist'
)
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp 
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID 
    WHERE tfp.MemberID = cm.MemberID AND pr.RoleName = 'Serving Specialist'
)
ORDER BY l.Name, cm.MemberID;


-- 15. Family members who are captains

SELECT 
    CONCAT(ci.FirstName, ' ', ci.LastName) as FamilyMemberName,
    ci.PhoneNumber
FROM FamilyMembers fm
JOIN CommonInfo ci ON fm.CommonID = ci.CommonID
JOIN ClubMembers cm ON fm.FamilyMemberID = cm.FamilyMemberID
JOIN TeamFormationPlayers tfp ON cm.MemberID = tfp.MemberID
JOIN TeamFormations tf ON tfp.FormationID = tf.FormationID
JOIN Teams t ON tf.TeamID = t.TeamID
WHERE t.LocationID = 1
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp2
    JOIN PlayerRoles pr ON tfp2.RoleID = pr.RoleID
    WHERE tfp2.MemberID = cm.MemberID
    AND pr.RoleName = 'Captain'
)
GROUP BY fm.FamilyMemberID;

-- 16. Members who have never lost a game

SELECT 
    cm.MemberID,
    CONCAT(ci.FirstName, ' ', ci.LastName) as MemberName,
    TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) as Age,
    ci.PhoneNumber,
    ci.Email,
    l.Name as CurrentLocation
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
JOIN Locations l ON cm.LocationID = l.LocationID
WHERE TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) BETWEEN 11 AND 18
AND EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp
    JOIN TeamFormations tf ON tfp.FormationID = tf.FormationID
    JOIN SessionTypes st ON tf.SessionTypeID = st.SessionTypeID
    WHERE tfp.MemberID = cm.MemberID
    AND st.Type = 'Game'
)
AND NOT EXISTS (
    SELECT 1 FROM TeamFormationPlayers tfp
    JOIN TeamFormations tf ON tfp.FormationID = tf.FormationID
    JOIN SessionTypes st ON tf.SessionTypeID = st.SessionTypeID
    WHERE tfp.MemberID = cm.MemberID
    AND st.Type = 'Game'
    AND tf.Score < (
        SELECT tf2.Score
        FROM TeamFormations tf2
        WHERE tf2.FormationID = tf.FormationID
        AND tf2.TeamID != tf.TeamID
    )
)
ORDER BY l.Name, cm.MemberID;

-- 17. Treasurer history

SELECT 
    CONCAT(ci.FirstName, ' ', ci.LastName) as TreasurerName,
    MIN(pa.StartDate) as StartDate,
    MAX(pa.EndDate) as EndDate
FROM Personnel p
JOIN CommonInfo ci ON p.CommonID = ci.CommonID
JOIN Personnel_Assignments pa ON p.PersonnelID = pa.PersonnelID
WHERE p.Role = 'Treasurer'
GROUP BY p.PersonnelID
ORDER BY ci.FirstName, ci.LastName, MIN(pa.StartDate);


-- 18. Deactivated members (over 18 years old)
SELECT 
    CONCAT(ci.FirstName, ' ', ci.LastName) as MemberName,
    ci.PhoneNumber,
    ci.Email,
    el.EmailDate as DeactivationDate,
    l.Name as LastLocation,
    pr.RoleName as LastRole
FROM EmailLogs el
JOIN CommonInfo ci ON el.Recipient = ci.Email
JOIN ClubMembers cm ON ci.CommonID = cm.CommonID
JOIN Locations l ON cm.LocationID = l.LocationID
JOIN TeamFormationPlayers tfp ON cm.MemberID = tfp.MemberID
JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID
WHERE el.EmailType = 'Deactivation'
AND tfp.FormationID = (
    SELECT MAX(FormationID)
    FROM TeamFormationPlayers
    WHERE MemberID = cm.MemberID
)
ORDER BY l.Name, pr.RoleName, ci.FirstName, ci.LastName;


-- 19. Triggers
CREATE TRIGGER PreventConflictingAssignments
BEFORE INSERT ON TeamFormationPlayers
FOR EACH ROW
BEGIN
    DECLARE count_conflicts INT;
    SELECT COUNT(*)
    INTO count_conflicts
    FROM TeamFormations tf
    JOIN TeamFormationPlayers tfp ON tf.FormationID = tfp.FormationID
    WHERE tfp.MemberID = NEW.MemberID
    AND tf.SessionDate = NEW.SessionDate;
    
    IF count_conflicts > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Player is already assigned to a formation on the same day.';
    END IF;
END;


-- 20. Integrity to prevent conflicting assignments (check 19)

-- 21. Email logs generation  (check 8 & 18)
