# E/R Diagram for MYVC Database System

```mermaid
erDiagram
    Locations ||--o{ Personnel : "employs"
    Locations ||--o{ FamilyMembers : "hosts"
    Locations ||--o{ Teams : "has"
    Locations ||--o{ ClubMembers : "hosts"
    
    CommonInfo ||--o{ Personnel : "identifies"
    CommonInfo ||--o{ FamilyMembers : "identifies"
    CommonInfo ||--o{ ClubMembers : "identifies"
    CommonInfo ||--o{ SecondaryFamilyMembers : "identifies"
    
    Personnel ||--o{ Personnel_Assignments : "has"
    Locations ||--o{ Personnel_Assignments : "has"
    Period ||--o{ Personnel_Assignments : "defines"
    
    FamilyMembers ||--o{ ClubMembers : "registers"
    FamilyMembers ||--o{ SecondaryFamilyMembers : "designates"
    SecondaryFamilyMembers ||--o{ SecondaryFamilyMemberRelationships : "has"
    ClubMembers ||--o{ SecondaryFamilyMemberRelationships : "has"
    
    Teams ||--o{ TeamFormations : "participates"
    SessionTypes ||--o{ TeamFormations : "categorizes"
    TeamFormations ||--o{ TeamFormationPlayers : "includes"
    ClubMembers ||--o{ TeamFormationPlayers : "participates"
    PlayerRoles ||--o{ TeamFormationPlayers : "defines"
    
    ClubMembers ||--o{ Memberships : "has"
    Memberships ||--o{ Payments : "receives"
    
    TeamFormations ||--o{ EmailLogs : "generates"
    ClubMembers ||--o{ EmailLogs : "receives"

    Locations {
        int LocationID PK
        enum Type
        string Name
        string Address
        string City
        string Province
        string PostalCode
        string PhoneNumber
        string WebAddress
        int MaxCapacity
    }

    CommonInfo {
        int CommonID PK
        string FirstName
        string LastName
        date DateOfBirth
        string SSN UK
        string MedicareCardNumber UK
        string PhoneNumber
        string Email
        string Address
        string City
        string Province
        string PostalCode
    }

    Personnel {
        int PersonnelID PK
        int CommonID FK
        enum Role
        enum Mandate
        int LocationID FK
    }

    Personnel_Assignments {
        int AssignmentID PK
        int PersonnelID FK
        int LocationID FK
        int PeriodID FK
    }

    Period {
        int PeriodID PK
        date StartDate
        date EndDate
    }

    FamilyMembers {
        int FamilyMemberID PK
        int CommonID FK
        int LocationID FK
        enum Relationship
    }

    ClubMembers {
        int MemberID PK
        int CommonID FK
        int FamilyMemberID FK
        int LocationID FK
        decimal Height
        decimal Weight
    }

    SecondaryFamilyMembers {
        int SecondaryID PK
        int PrimaryFamilyMemberID FK
        int CommonID FK
        enum Relationship
    }

    SecondaryFamilyMemberRelationships {
        int SecondaryID FK
        int MemberID FK
        enum Relationship
    }

    Teams {
        int TeamID PK
        string TeamName
        enum TeamType
        int LocationID FK
    }

    SessionTypes {
        int SessionTypeID PK
        enum Type
    }

    PlayerRoles {
        int RoleID PK
        enum RoleName
    }

    TeamFormations {
        int FormationID PK
        int TeamID FK
        int SessionTypeID FK
        date SessionDate
        time StartTime
        time EndTime
        string Address
        int Score
    }

    TeamFormationPlayers {
        int FormationID FK
        int MemberID FK
        int RoleID FK
    }

    Memberships {
        int MembershipID PK
        int MemberID FK
        int Year
        enum Status
        decimal TotalPaid
        decimal DonationAmount
    }

    Payments {
        int PaymentID PK
        int MembershipID FK
        date PaymentDate
        decimal Amount
        enum PaymentMethod
    }

    EmailLogs {
        int EmailLogID PK
        datetime EmailDate
        string Sender
        string Recipient
        string CCList
        string Subject
        string BodyPreview
        enum EmailType
    }
```

## Constraints Not Captured in E/R Diagram

1. **Age Constraints**:
   - Club members must be between 11-18 years old
   - Trigger `validate_member_age` enforces this

2. **Time Conflict Constraints**:
   - Players cannot be assigned to sessions less than 3 hours apart
   - Trigger `validate_session_time_conflict` enforces this

3. **Membership Status**:
   - Members are inactive if fees are not paid
   - Automatic deactivation at age 18
   - Trigger `check_member_age_monthly` handles this

4. **Team Formation Rules**:
   - All players in a team must be from the same location
   - Teams must be either all boys or all girls
   - Each team must have exactly one captain

5. **Payment Rules**:
   - Maximum 4 installments per year
   - Excess over $100 is considered donation
   - Annual fee is $100

6. **Email Generation Rules**:
   - Weekly emails for upcoming sessions
   - Monthly age check and deactivation emails
   - Email logging requirements 