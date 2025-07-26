# Database Normalization Analysis

## 1. BCNF Analysis

### Relations in BCNF:
1. `Locations`
   - All attributes are atomic
   - No non-trivial functional dependencies
   - Primary key is `LocationID`

2. `SessionTypes`
   - All attributes are atomic
   - No non-trivial functional dependencies
   - Primary key is `SessionTypeID`

3. `PlayerRoles`
   - All attributes are atomic
   - No non-trivial functional dependencies
   - Primary key is `RoleID`

4. `Period`
   - All attributes are atomic
   - No non-trivial functional dependencies
   - Primary key is `PeriodID`

### Relations in 3NF but not BCNF:

1. `CommonInfo`
   - In 3NF but not BCNF because:
   - Has functional dependencies:
     - `SSN → {FirstName, LastName, DateOfBirth, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode}`
     - `MedicareCardNumber → {FirstName, LastName, DateOfBirth, SSN, PhoneNumber, Email, Address, City, Province, PostalCode}`
   - These dependencies are not superkeys

2. `Personnel`
   - In 3NF but not BCNF because:
   - Has functional dependencies:
     - `CommonID → {Role, Mandate}`
   - `CommonID` is not a superkey

3. `FamilyMembers`
   - In 3NF but not BCNF because:
   - Has functional dependencies:
     - `CommonID → {LocationID, Relationship}`
   - `CommonID` is not a superkey

4. `ClubMembers`
   - In 3NF but not BCNF because:
   - Has functional dependencies:
     - `CommonID → {FamilyMemberID, LocationID, Height, Weight}`
   - `CommonID` is not a superkey

5. `TeamFormations`
   - In 3NF but not BCNF because:
   - Has functional dependencies:
     - `TeamID → {SessionTypeID, SessionDate, StartTime, EndTime, Address, Score}`
   - `TeamID` is not a superkey

## 2. 3NF Analysis

All relations are in 3NF because:
1. They are in 2NF (no partial dependencies)
2. No transitive dependencies exist
3. All non-prime attributes are fully functionally dependent on the primary key

## 3. Functional Dependencies

### CommonInfo:
```
SSN → {FirstName, LastName, DateOfBirth, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode}
MedicareCardNumber → {FirstName, LastName, DateOfBirth, SSN, PhoneNumber, Email, Address, City, Province, PostalCode}
```

### Personnel:
```
CommonID → {Role, Mandate}
```

### FamilyMembers:
```
CommonID → {LocationID, Relationship}
```

### ClubMembers:
```
CommonID → {FamilyMemberID, LocationID, Height, Weight}
```

### TeamFormations:
```
TeamID → {SessionTypeID, SessionDate, StartTime, EndTime, Address, Score}
```

## 4. Why Not Decompose Further?

The relations that are in 3NF but not BCNF are kept in their current form because:

1. **CommonInfo**: 
   - The functional dependencies are based on business rules (SSN and MedicareCardNumber are unique identifiers)
   - Decomposing would create unnecessary complexity in queries
   - The current structure maintains data integrity while being practical

2. **Personnel, FamilyMembers, ClubMembers**:
   - The functional dependencies are based on the relationship with CommonInfo
   - Decomposing would create unnecessary joins in queries
   - The current structure maintains referential integrity

3. **TeamFormations**:
   - The functional dependencies are based on the relationship with Teams
   - Decomposing would complicate the team formation tracking
   - The current structure maintains data consistency

## 5. Integrity Constraints

1. **Primary Keys**:
   - All relations have a primary key
   - Primary keys are atomic and unique

2. **Foreign Keys**:
   - All foreign keys reference existing primary keys
   - Referential integrity is maintained

3. **Unique Constraints**:
   - SSN and MedicareCardNumber in CommonInfo
   - LocationID in Locations
   - TeamID in Teams

4. **Check Constraints**:
   - Age validation (11-18 years)
   - Time conflict validation (3 hours minimum)
   - Payment amount validation (max 4 installments)
   - Team gender validation (all boys or all girls) 