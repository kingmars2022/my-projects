from flask import Flask, request, jsonify
from flask_cors import CORS
import pymysql
import pymysql.cursors
import logging
from datetime import timedelta

# Initialize Flask App
app = Flask(__name__)

# Enable CORS for frontend communication
CORS(app, origins="http://localhost:63342")

# Database Configuration
DB_CONFIG = {
    'host': 'fqc353.encs.concordia.ca',
    'user': 'fqc353_4',
    'password': 'Pa55w0rd',
    'database': 'fqc353_4',
    'cursorclass': pymysql.cursors.DictCursor,
    'autocommit': True  # Ensures changes are committed immediately
}

# Set up logging
logging.basicConfig(
    level=logging.DEBUG,  # Capture detailed logs
    format="%(asctime)s - %(levelname)s - %(message)s",
    handlers=[logging.StreamHandler()]
)


def get_db_connection():
    """Creates and returns a new database connection."""
    try:
        logging.debug("Attempting to connect to the database...")
        connection = pymysql.connect(**DB_CONFIG)
        logging.info("Database connection established successfully.")
        return connection
    except pymysql.MySQLError as e:
        logging.error(f"Database connection error: {e}")
        return None


@app.route('/get_table', methods=['GET'])
def get_table():
    """Fetch table data dynamically."""
    table_name = request.args.get('name')

    if not table_name:
        logging.warning("Table name is missing in request.")
        return jsonify({'error': 'Table name is required'}), 400

    logging.info(f"Received request to fetch data from table: {table_name}")
    return get_table_data(table_name)


@app.route('/query', methods=['GET'])
def run_query():
    """Executes queries dynamically based on the query ID."""

    query_id = request.args.get('name')
    if not query_id:
        logging.warning("Query ID is missing in request.")
        return jsonify({'error': 'Query ID is required'}), 400

    logging.info(f"Received request to execute query: {query_id}")

    # Define queries
    QUERIES = {
        "Q7": """SELECT 
                    L.Name AS LocationName, L.Address, L.City, L.Province, L.PostalCode, 
                    L.PhoneNumber, L.WebAddress, L.Type AS LocationType, L.MaxCapacity AS Capacity, 
                    CONCAT(CI.FirstName, ' ', CI.LastName) AS GeneralManagerName, 
                    COUNT(CM.MemberID) AS NumOfClubMembers
                 FROM Locations L
                 JOIN Personnel P ON P.LocationID = L.LocationID
                 JOIN CommonInfo CI ON CI.CommonID = P.CommonID
                 LEFT JOIN ClubMembers CM ON CM.LocationID = L.LocationID
                 GROUP BY L.LocationID, CI.FirstName, CI.LastName
                 ORDER BY L.Province ASC, L.City ASC;""",

        "Q8": """SELECT 
                    FM.FamilyMemberID AS PrimaryFamilyMemberID, L.Name AS LocationName,
                    C2.FirstName AS SecondaryFirstName, C2.LastName AS SecondaryLastName,
                    C2.PhoneNumber AS SecondaryPhoneNumber, CM.MemberID AS ClubMembershipNumber,
                    C3.FirstName AS ClubMemberFirstName, C3.LastName AS ClubMemberLastName,
                    C3.DateOfBirth AS ClubMemberDOB, C3.SSN AS ClubMemberSSN,
                    C3.MedicareCardNumber AS ClubMemberMedicareCard, 
                    C3.PhoneNumber AS ClubMemberPhoneNumber, C3.Address AS ClubMemberAddress,
                    C3.City AS ClubMemberCity, C3.Province AS ClubMemberProvince, 
                    C3.PostalCode AS ClubMemberPostalCode, 
                    SFM.Relationship AS SecondaryFamilyRelationship
                 FROM FamilyMembers FM
                 LEFT JOIN Locations L ON FM.LocationID = L.LocationID
                 LEFT JOIN SecondaryFamilyMembers SFM ON SFM.PrimaryFamilyMemberID = FM.FamilyMemberID
                 LEFT JOIN CommonInfo C2 ON SFM.CommonID = C2.CommonID
                 LEFT JOIN ClubMembers CM ON CM.FamilyMemberID = FM.FamilyMemberID
                 LEFT JOIN CommonInfo C3 ON CM.CommonID = C3.CommonID
                 WHERE FM.FamilyMemberID = 3
                 ORDER BY L.Name, C3.LastName, C3.FirstName;""",
        "Q9": """SELECT 
                    TF.SessionDate,
                    TF.StartTime,
                    L.Address AS SessionAddress,
                    ST.Type AS SessionType,
                    T.TeamName,
                    TF.Team1Score,
                    C1.FirstName AS HeadCoachFirstName,
                    C1.LastName AS HeadCoachLastName,
                    C2.FirstName AS PlayerFirstName,
                    C2.LastName AS PlayerLastName,
                    PR.RoleName AS PlayerRole
                FROM TeamFormations TF
                JOIN Locations L ON TF.LocationID = L.LocationID
                JOIN SessionTypes ST ON TF.SessionTypeID = ST.SessionTypeID
                JOIN Teams T ON TF.Team1ID = T.TeamID
                LEFT JOIN Personnel P ON P.LocationID = L.LocationID AND P.Role = 'Coach'
                LEFT JOIN CommonInfo C1 ON P.CommonID = C1.CommonID
                JOIN TeamFormationPlayers TFP ON TF.FormationID = TFP.FormationID
                JOIN ClubMembers CM ON TFP.MemberID = CM.MemberID
                JOIN CommonInfo C2 ON CM.CommonID = C2.CommonID
                JOIN PlayerRoles PR ON TFP.RoleID = PR.RoleID
                WHERE 
                    TF.LocationID = 1
                    AND TF.SessionDate BETWEEN '2025-03-01' AND '2025-04-07'
                ORDER BY 
                    TF.SessionDate ASC,
                    TF.StartTime ASC;""",
        "Q10": """SELECT 
    cm.MemberID AS ClubMembershipNumber,
    ci.FirstName,
    ci.LastName
FROM 
    ClubMembers cm
JOIN 
    CommonInfo ci ON ci.CommonID = cm.CommonID
JOIN 
    ClubMembers_Locations cml ON cml.MemberID = cm.MemberID
JOIN 
    Memberships m ON m.MemberID = cm.MemberID
WHERE 
    m.Status = 'Active'  -- Ensure that the member is currently active
    AND (2025 - m.Year) <= 3  -- Ensure they have been members for at most 3 years
GROUP BY 
    cm.MemberID, ci.FirstName, ci.LastName
HAVING 
    COUNT(DISTINCT cml.LocationID) >= 3  -- Ensure that the member is associated with at least 3 distinct locations
ORDER BY 
    cm.MemberID ASC;  -- Sort results by club membership number in ascending order
""",
        "Q11": """SELECT 
    l.Name AS LocationName,
    COUNT(DISTINCT CASE WHEN tf.SessionTypeID = (SELECT SessionTypeID FROM SessionTypes WHERE Type = 'Training') THEN tf.FormationID END) AS TotalTrainingSessions,
    COUNT(DISTINCT CASE WHEN tf.SessionTypeID = (SELECT SessionTypeID FROM SessionTypes WHERE Type = 'Training') THEN CONCAT(tfp.MemberID, '-', tf.FormationID) END) AS TotalPlayersInTrainingSessions,
    COUNT(DISTINCT CASE WHEN tf.SessionTypeID = (SELECT SessionTypeID FROM SessionTypes WHERE Type = 'Game') THEN tf.FormationID END) AS TotalGameSessions,
    COUNT(DISTINCT CASE WHEN tf.SessionTypeID = (SELECT SessionTypeID FROM SessionTypes WHERE Type = 'Game') THEN CONCAT(tfp.MemberID, '-', tf.FormationID) END) AS TotalPlayersInGameSessions
FROM TeamFormations tf
JOIN Locations l ON tf.LocationID = l.LocationID
LEFT JOIN TeamFormationPlayers tfp ON tf.FormationID = tfp.FormationID
WHERE tf.SessionDate BETWEEN '2025-01-01' AND '2025-03-31'
GROUP BY l.LocationID
ORDER BY TotalGameSessions DESC;""",
        "Q12": """SELECT 
    cm.MemberID AS MembershipNumber,
    ci.FirstName,
    ci.LastName,
    TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) AS Age,
    m.Year AS DateOfJoining,
    ci.PhoneNumber,
    ci.Email,
    loc.Name AS CurrentLocationName
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
JOIN Memberships m ON cm.MemberID = m.MemberID
LEFT JOIN Locations loc ON cm.LocationID = loc.LocationID
LEFT JOIN TeamFormationPlayers tfp ON cm.MemberID = tfp.MemberID
WHERE m.Status = 'Active' AND tfp.MemberID IS NULL
ORDER BY loc.Name ASC, cm.MemberID ASC;""",
        "Q13": """SELECT 
    cm.MemberID AS MembershipNumber,
    ci.FirstName,
    ci.LastName,
    TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) AS Age,
    ci.PhoneNumber,
    ci.Email,
    loc.Name AS CurrentLocationName
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
JOIN Memberships m ON cm.MemberID = m.MemberID
LEFT JOIN Locations loc ON cm.LocationID = loc.LocationID
WHERE m.Status = 'Active' 
AND cm.MemberID IN (
    SELECT tfp.MemberID 
    FROM TeamFormationPlayers tfp
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID
    WHERE pr.RoleName = 'Outside Hitter'
    GROUP BY tfp.MemberID
    HAVING COUNT(DISTINCT pr.RoleName) = 1
)
ORDER BY loc.Name ASC, cm.MemberID ASC;""",
        "Q14": """SELECT 
    cm.MemberID AS MembershipNumber,
    ci.FirstName,
    ci.LastName,
    TIMESTAMPDIFF(YEAR, ci.DateOfBirth, CURDATE()) AS Age,
    ci.PhoneNumber,
    ci.Email,
    loc.Name AS CurrentLocationName
FROM ClubMembers cm
JOIN CommonInfo ci ON cm.CommonID = ci.CommonID
JOIN Memberships m ON cm.MemberID = m.MemberID
LEFT JOIN ClubMembers_Locations cml ON cm.MemberID = cml.MemberID AND cml.Current_location = TRUE
LEFT JOIN Locations loc ON cml.LocationID = loc.LocationID
WHERE m.Status = 'Active' 
AND cm.MemberID IN (
    SELECT tfp.MemberID
    FROM TeamFormationPlayers tfp
    JOIN TeamFormations tf ON tfp.FormationID = tf.FormationID
    JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID
    JOIN SessionTypes st ON tf.SessionTypeID = st.SessionTypeID
    WHERE st.Type = 'Game'
    GROUP BY tfp.MemberID
    HAVING COUNT(DISTINCT pr.RoleName) = (SELECT COUNT(*) FROM PlayerRoles)
)
ORDER BY loc.Name ASC, cm.MemberID ASC;""",
        "Q15": """SELECT 
    ci.FirstName,
    ci.LastName,
    ci.PhoneNumber
FROM FamilyMembers fm
JOIN CommonInfo ci ON fm.CommonID = ci.CommonID
JOIN ClubMembers cm ON fm.FamilyMemberID = cm.FamilyMemberID
JOIN Memberships m ON cm.MemberID = m.MemberID
JOIN Personnel p ON cm.LocationID = p.LocationID
JOIN TeamFormationPlayers tfp ON cm.MemberID = tfp.MemberID
JOIN TeamFormations tf ON tfp.FormationID = tf.FormationID
JOIN Locations loc ON tf.LocationID = loc.LocationID
JOIN PlayerRoles pr ON tfp.RoleID = pr.RoleID
WHERE m.Status = 'Active' 
AND p.Role = 'Captain'
AND cm.LocationID = tf.LocationID
GROUP BY fm.FamilyMemberID
ORDER BY ci.LastName, ci.FirstName;""",
        "Q16": """SELECT DISTINCT 
    CM.MemberID AS MembershipNumber,
    CI.FirstName,
    CI.LastName,
    TIMESTAMPDIFF(YEAR, CI.DateOfBirth, CURDATE()) AS Age,
    CI.PhoneNumber,
    CI.Email,
    L.Name AS CurrentLocation
FROM ClubMembers CM
JOIN CommonInfo CI ON CM.CommonID = CI.CommonID
JOIN Memberships M ON CM.MemberID = M.MemberID
JOIN ClubMembers_Locations CML ON CM.MemberID = CML.MemberID AND CML.Current_location = TRUE
JOIN Locations L ON CML.LocationID = L.LocationID
JOIN TeamFormationPlayers TFP ON CM.MemberID = TFP.MemberID
JOIN TeamFormations TF ON TFP.FormationID = TF.FormationID
JOIN SessionTypes ST ON TF.SessionTypeID = ST.SessionTypeID
WHERE 
    M.Status = 'Active'
    AND ST.Type = 'Game'
    AND NOT EXISTS (
        SELECT 1
        FROM TeamFormations TF_Loss
        JOIN TeamFormationPlayers TFP_Loss ON TF_Loss.FormationID = TFP_Loss.FormationID
        WHERE 
            TF_Loss.SessionTypeID = ST.SessionTypeID
            AND TFP_Loss.MemberID = CM.MemberID
            AND (
                TF_Loss.Team1Score IS NOT NULL
                AND TF_Loss.Team1Score < (
                    SELECT MAX(TF_Other.Team1Score)
                    FROM TeamFormations TF_Other
                    WHERE TF_Other.SessionDate = TF_Loss.SessionDate
                )
            )
    )
ORDER BY L.Name ASC, CM.MemberID ASC;""",
        "Q17": """SELECT 
    ci.FirstName,
    ci.LastName,
    MIN(pa.PeriodID) AS StartDateAsTreasurer,  -- Start date is the first period where they were assigned as Treasurer
    MAX(pa.PeriodID) AS LastDateAsTreasurer   -- Last date is the most recent period they were assigned as Treasurer (null for current)
FROM 
    Personnel p
JOIN 
    CommonInfo ci ON p.CommonID = ci.CommonID
JOIN 
    Personnel_Assignments pa ON p.PersonnelID = pa.PersonnelID
WHERE 
    p.Role = 'Treasurer'
GROUP BY 
    ci.FirstName, ci.LastName
ORDER BY 
    ci.FirstName ASC, ci.LastName ASC, StartDateAsTreasurer ASC;
""",
        "Q18": """SELECT 
    c.FirstName,
    c.LastName,
    c.PhoneNumber,
    c.Email,
    l.Name AS LastLocationName,
    IF(m.Deactivation = 1, 'Deactivated', 'Active') AS Status
FROM 
    ClubMembers cm
JOIN 
    CommonInfo c ON cm.CommonID = c.CommonID
JOIN 
    Memberships m ON cm.MemberID = m.MemberID
JOIN 
    ClubMembers_Locations cl ON cm.MemberID = cl.MemberID
JOIN 
    Locations l ON cl.LocationID = l.LocationID
WHERE 
    m.Deactivation = 1
ORDER BY 
    l.Name ASC,
    c.FirstName ASC,
    c.LastName ASC;""",

        # Add more queries as needed
    }

    # Check if the query ID is valid
    if query_id not in QUERIES:
        logging.warning(f"Invalid query ID: {query_id}")
        return jsonify({'error': 'Invalid query ID'}), 400

    # Fetch the result from the database
    connection = get_db_connection()
    if not connection:
        return jsonify({'error': 'Database connection failed'}), 500

    try:
        with connection.cursor() as cursor:
            query = QUERIES[query_id]
            logging.debug(f"Executing query: {query}")
            cursor.execute(query)
            result = cursor.fetchall()

            if not result:
                logging.info(f"No data found for query: {query_id}")
                return jsonify({'message': 'No data available'}), 200

            # Convert timedelta objects to string before returning JSON
            result = serialize_timedelta(result)

            logging.info(f"Fetched {len(result)} records for query: {query_id}")
            return jsonify(result)

    except pymysql.MySQLError as e:
        logging.error(f"SQL Error for query {query_id}: {e}")
        return jsonify({'error': str(e)}), 500

    finally:
        logging.debug("Closing database connection.")
        connection.close()


@app.route('/api/<query_type>/<entity>', methods=['GET'])
def handle_query(query_type, entity):
    connection = get_db_connection()
    cursor = connection.cursor(dictionary=True)

    try:
        if query_type == 'create':
            if entity == 'location':
                cursor.execute("""
                    INSERT INTO Locations (Type, Name, Address, City, Province, PostalCode, PhoneNumber, WebAddress, MaxCapacity) 
                    VALUES ('Branch', 'West Side', '789 West St', 'Montreal', 'QC', 'H3A 2K6', '514-555-7890', 'www.westside.com', 100)
                """)
                connection.commit()
            elif entity == 'personnel':
                cursor.execute("""
                    INSERT INTO CommonInfo (FirstName, LastName, DateOfBirth, SSN, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode) 
                    VALUES ('Joh123n', 'D123oe', '1985-06-15', '323323323', 'MC2222222', '514-555-9999', 'johndoe@email.com', '123 Main St', 'Montreal', 'QC', 'H3A 1B1')
                """)
                cursor.execute("""
                    INSERT INTO Personnel (CommonID, Role, Mandate, LocationID) 
                    VALUES (LAST_INSERT_ID(), 'Coach', 'Salaried', 1)
                """)
                connection.commit()
            # Add more entities here...

        elif query_type == 'delete':
            if entity == 'location':
                cursor.execute("DELETE FROM Locations WHERE LocationID = 11")
                connection.commit()
            elif entity == 'personnel':
                cursor.execute("DELETE FROM Personnel WHERE PersonnelID = 2")
                connection.commit()
            # Add more entities here...

        elif query_type == 'edit':
            if entity == 'location':
                cursor.execute("""
                    UPDATE Locations 
                    SET Name = 'New Name', Address = '456 Updated St', City = 'Laval', MaxCapacity = 150
                    WHERE LocationID = 3
                """)
                connection.commit()
            elif entity == 'personnel':
                cursor.execute("""
                    UPDATE Personnel 
                    SET Role = 'General Manager', LocationID = 2 
                    WHERE PersonnelID = 3
                """)
                connection.commit()
            # Add more entities here...

        elif query_type == 'display':
            if entity == 'location':
                cursor.execute("SELECT * FROM Locations")
                result = cursor.fetchall()
                return jsonify(result)
            elif entity == 'personnel':
                cursor.execute("""
                    SELECT P.PersonnelID, CI.FirstName, CI.LastName, P.Role, CI.Email, L.Name AS Location 
                    FROM Personnel P
                    JOIN CommonInfo CI ON P.CommonID = CI.CommonID
                    JOIN Locations L ON P.LocationID = L.LocationID
                """)
                result = cursor.fetchall()
                return jsonify(result)
            # Add more entities here...

        return jsonify({'status': 'success'}), 200

    except Exception as e:
        return jsonify({'status': 'error', 'message': str(e)}), 500
    finally:
        cursor.close()
        connection.close()


def serialize_timedelta(data):
    """
    Converts timedelta objects into string format (HH:MM:SS).
    """
    for row in data:
        for key, value in row.items():
            if isinstance(value, timedelta):
                row[key] = str(value)  # Convert timedelta to string (e.g., "02:30:00")
    return data


def get_table_data(table_name):
    """Retrieves all records from the specified table safely."""
    connection = get_db_connection()
    if not connection:
        return jsonify({'error': 'Database connection failed'}), 500

    try:
        with connection.cursor() as cursor:
            query = f"SELECT * FROM `{table_name}`"
            logging.debug(f"Executing query: {query}")
            cursor.execute(query)
            result = cursor.fetchall()

            if not result:
                logging.info(f"No data found for table: {table_name}")
                return jsonify({'message': 'No data available'}), 200

            # Convert timedelta objects to string before returning JSON
            result = serialize_timedelta(result)

            logging.info(f"Fetched {len(result)} records from table {table_name}.")
            return jsonify(result)

    except pymysql.MySQLError as e:
        logging.error(f"SQL Error for table {table_name}: {e}")
        return jsonify({'error': str(e)}), 500

    finally:
        logging.debug("Closing database connection.")
        connection.close()


@app.route('/insert-location', methods=['POST'])
def insert_location():
    data = request.get_json()
    required_fields = ['type', 'name', 'address', 'city', 'province', 'postalCode',
                       'phoneNumber', 'webAddress', 'maxCapacity']

    if not all(field in data and data[field] for field in required_fields):
        return jsonify({'error': 'All fields are required'}), 400

    try:
        conn = get_db_connection()
        with conn.cursor() as cursor:
            sql = """
                INSERT INTO Locations 
                (Type, Name, Address, City, Province, PostalCode, PhoneNumber, WebAddress, MaxCapacity)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s);
            """
            cursor.execute(sql, (
                data['type'],
                data['name'],
                data['address'],
                data['city'],
                data['province'],
                data['postalCode'],
                data['phoneNumber'],
                data['webAddress'],
                data['maxCapacity']
            ))
        conn.commit()
        conn.close()
        return jsonify({'message': f'Location \"{data["name"]}\" inserted successfully!'})
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/delete-location', methods=['DELETE'])
def delete_location():
    data = request.get_json()
    location_id = data.get('location_id')

    if location_id is None:
        return jsonify({'error': 'Location ID is required'}), 400

    try:
        conn = get_db_connection()
        with conn.cursor() as cursor:
            sql = "DELETE FROM Locations WHERE LocationID = %s"
            cursor.execute(sql, (location_id,))
        conn.close()
        return jsonify({'message': f'Location with ID {location_id} deleted successfully'})
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/update-location', methods=['POST'])
def update_location():
    data = request.get_json()

    # Check if all required fields are present
    required_fields = ['update_name', 'update_address', 'update_city', 'update_locationID', 'update_maxCapacity']
    if not all(field in data for field in required_fields):
        return jsonify({'error': 'Missing required fields'}), 400

    try:
        conn = get_db_connection()
        with conn.cursor() as cursor:
            sql = """
                UPDATE Locations SET
                Name = %s, Address = %s, City = %s, MaxCapacity = %s
                WHERE LocationID = %s;
            """
            cursor.execute(sql, (
                data['update_name'],
                data['update_address'],
                data['update_city'],
                data['update_maxCapacity'],
                data['update_locationID']
            ))
        conn.commit()
        conn.close()
        return jsonify({'message': f'Location "{data["update_name"]}" updated successfully!'})
    except Exception as e:
        return jsonify({'error': str(e)}), 500


# Insert Personnel Endpoint
@app.route('/insertPersonnel', methods=['POST'])
def insert_personnel():
    data = request.get_json()

    first_name = data.get('firstName')
    last_name = data.get('lastName')
    ssn = data.get('ssn')
    medicare_card = data.get('medicareCard')
    phone_number = None
    date_of_birth = None
    email = None
    address = None
    city = None
    province = None
    postal_code = None
    role = 'Coach'
    mandate = 'Salaried'
    location_id = data.get('location_id')

    # Step 1: Insert into CommonInfo table and get the CommonID
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        INSERT INTO CommonInfo (FirstName, LastName, DateOfBirth, SSN, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    ''', (first_name, last_name, date_of_birth, ssn, medicare_card, phone_number, email, address, city, province,
          postal_code))
    conn.commit()

    # Get the CommonID
    cursor.execute('SELECT CommonID FROM CommonInfo WHERE SSN = %s', (ssn,))
    common_id_result = cursor.fetchone()['CommonID']

    # Step 2: Insert into Personnel table
    cursor.execute('''
        INSERT INTO Personnel (CommonID, Role, Mandate, LocationID) 
        VALUES (%s, %s, %s, %s);
    ''', (common_id_result, role, mandate, location_id))
    conn.commit()

    conn.close()
    return jsonify({"message": "Personnel inserted successfully"}), 201


# Update Personnel Endpoint
@app.route('/updatePersonnel', methods=['POST'])
def update_personnel():
    data = request.get_json()

    personnel_id = data.get('personnelID')
    role = data.get('role')

    # Step 1: Update the Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        UPDATE Personnel 
        SET Role = %s
        WHERE PersonnelID = %s
    ''', (role, personnel_id))
    conn.commit()

    conn.close()
    return jsonify({"message": "Personnel updated successfully"}), 200


# Delete Personnel Endpoint
@app.route('/deletePersonnel', methods=['DELETE'])
def delete_personnel():
    data = request.get_json()

    personnel_id = data.get('personnelID')

    # Step 1: Delete from Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        DELETE FROM Personnel WHERE PersonnelID = %s
    ''', (personnel_id,))
    conn.commit()

    conn.close()
    return jsonify({"message": "Personnel deleted successfully"}), 200





#Family member



@app.route('/insertFamilyMember', methods=['POST'])
def insertFamilyMember():
    data = request.get_json()

    FamilyMember_firstName=data.get('FamilyMember_firstName'),
    FamilyMember_lastName=data.get('FamilyMember_lastName'),
    FamilyMember_Relationship=data.get('FamilyMember_Relationship'),
    FamilyMember_ssn=data.get('FamilyMember_ssn'),
    FamilyMember_location_id=data.get('FamilyMember_location_id'),
    FamilyMember_medicareCard=data.get('FamilyMember_medicareCard'),
    phone_number = None
    date_of_birth = None
    email = None
    address = None
    city = None
    province = None
    postal_code = None

    # Step 1: Insert into CommonInfo table and get the CommonID
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        INSERT INTO CommonInfo (FirstName, LastName, DateOfBirth, SSN, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    ''', (FamilyMember_firstName, FamilyMember_lastName, date_of_birth, FamilyMember_ssn, FamilyMember_medicareCard, phone_number, email, address, city, province,
          postal_code))
    conn.commit()

    # Get the CommonID
    cursor.execute('SELECT CommonID FROM CommonInfo WHERE SSN = %s', (FamilyMember_ssn,))
    common_id_result = cursor.fetchone()['CommonID']

    # Step 2: Insert into Personnel table
    cursor.execute('''
        INSERT INTO FamilyMembers (CommonID, LocationID, Relationship) 
        VALUES (%s, %s, %s);
    ''', (common_id_result, FamilyMember_location_id,FamilyMember_Relationship))
    conn.commit()

    conn.close()
    return jsonify({"message": "FamilyMember inserted successfully"}), 201


# Update FamilyMember Endpoint
@app.route('/updateFamilyMember', methods=['POST'])
def updateFamilyMember():
    data = request.get_json()

    FamilyMember_update_ID= data.get('FamilyMember_update_ID')
    FamilyMember_updateRole= data.get('FamilyMember_updateRole')

    # Step 1: Update the Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        UPDATE FamilyMembers 
        SET Relationship =%s
        WHERE FamilyMemberID = %s;
    ''', (FamilyMember_updateRole, FamilyMember_update_ID))
    conn.commit()

    conn.close()
    return jsonify({"message": "FamilyMember updated successfully"}), 200


# Delete FamilyMember Endpoint
@app.route('/deleteFamilyMember', methods=['DELETE'])
def deleteFamilyMember():
    data = request.get_json()

    deleteFamilyMemberID = data.get('deleteFamilyMemberID')

    # Step 1: Delete from Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        DELETE FROM FamilyMembers WHERE FamilyMemberID = %s
    ''', (deleteFamilyMemberID,))
    conn.commit()

    conn.close()
    return jsonify({"message": "FamilyMembers deleted successfully"}), 200




#ClubMember


@app.route('/insertClubMember', methods=['POST'])
def insertClubMember():
    data = request.get_json()

    ClubMember_firstName=data.get('ClubMember_firstName'),
    ClubMember_lastName=data.get('ClubMember_lastName'),
    ClubMember_FamilyMemberID=data.get('ClubMember_FamilyMemberID'),
    ClubMember_ssn=data.get('ClubMember_ssn'),
    ClubMember_location_id=data.get('ClubMember_location_id'),
    ClubMember_medicareCard=data.get('ClubMember_medicareCard'),
    phone_number = None
    date_of_birth = None
    email = None
    address = None
    city = None
    province = None
    postal_code = None
    Height= None
    Weight= None

    # Step 1: Insert into CommonInfo table and get the CommonID
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        INSERT INTO CommonInfo (FirstName, LastName, DateOfBirth, SSN, MedicareCardNumber, PhoneNumber, Email, Address, City, Province, PostalCode) 
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
    ''', (ClubMember_firstName, ClubMember_lastName, date_of_birth, ClubMember_ssn, ClubMember_medicareCard, phone_number, email, address, city, province,
          postal_code))
    conn.commit()

    # Get the CommonID
    cursor.execute('SELECT CommonID FROM CommonInfo WHERE SSN = %s', (ClubMember_ssn,))
    common_id_result = cursor.fetchone()['CommonID']

    # Step 2: Insert into Personnel table
    cursor.execute('''
        INSERT INTO ClubMembers (CommonID, FamilyMemberID, LocationID, Height, Weight) 
        VALUES (%s, %s, %s, %s, %s);
    ''', (common_id_result, ClubMember_FamilyMemberID,ClubMember_location_id,Height, Weight))
    conn.commit()

    conn.close()
    return jsonify({"message": "ClubMember inserted successfully"}), 201


# Update ClubMember Endpoint
@app.route('/updateClubMember', methods=['POST'])
def updateClubMember():
    data = request.get_json()

    ClubMember_update_ID= data.get('ClubMember_update_ID')
    ClubMember_updateHeight= data.get('ClubMember_updateHeight')
    ClubMember_updateWeight= data.get('ClubMember_updateWeight')

    # Step 1: Update the Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        UPDATE ClubMembers 
        SET Height =%s, Weight = %s
        WHERE MemberID = %s;
    ''', (ClubMember_update_ID, ClubMember_updateHeight,ClubMember_updateWeight))
    conn.commit()

    conn.close()
    return jsonify({"message": "ClubMember updated successfully"}), 200


# Delete ClubMember Endpoint
@app.route('/deleteClubMember', methods=['DELETE'])
def deleteClubMember():
    data = request.get_json()

    deleteClubMemberID = data.get('deleteClubMemberID')

    # Step 1: Delete from Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        DELETE FROM ClubMembers WHERE MemberID = %s
    ''', (deleteClubMemberID,))
    conn.commit()

    conn.close()
    return jsonify({"message": "ClubMembers deleted successfully"}), 200


#Teamformations


@app.route('/insertTeamFormation', methods=['POST'])
def insertTeamFormation():
    data = request.get_json()

    TeamFormation_Team1ID_insert=data.get('TeamFormation_Team1ID_insert')
    TeamFormation_SessionTypeID_insert=data.get('TeamFormation_SessionTypeID_insert')
    TeamFormation_SessionDate=data.get('TeamFormation_SessionDate')
    TeamFormation_StartTime=data.get('TeamFormation_StartTime')
    TeamFormation_EndTime=data.get('TeamFormation_EndTime'),
    TeamFormation_LocationID_insert=data.get('TeamFormation_LocationID_insert')
    TeamFormation_Team1Score_insert=data.get('TeamFormation_Team1Score_insert')

    # Step 1: Insert into CommonInfo table and get the CommonID
    conn = get_db_connection()
    cursor = conn.cursor()

    # Step 2: Insert into Personnel table
    cursor.execute('''
        INSERT INTO TeamFormations (Team1ID, SessionTypeID, SessionDate, StartTime, EndTime, LocationID, Team1Score)
        VALUES (%s, %s, %s, %s, %s, %s, %s);
    ''', (TeamFormation_Team1ID_insert, TeamFormation_SessionTypeID_insert,
	TeamFormation_SessionDate,TeamFormation_StartTime, TeamFormation_EndTime,
	TeamFormation_LocationID_insert,TeamFormation_Team1Score_insert))
    conn.commit()

    conn.close()
    return jsonify({"message": "TeamFormation inserted successfully"}), 201


# Update TeamFormation Endpoint
@app.route('/updateTeamFormation', methods=['POST'])
def updateTeamFormation():
    data = request.get_json()

    TeamFormation_FormationID= data.get('TeamFormation_FormationID')
    TeamFormation_Team1Score= data.get('TeamFormation_Team1Score')
    TeamFormation_SessionTypeID= data.get('TeamFormation_SessionTypeID')
    TeamFormation_LocationID= data.get('TeamFormation_LocationID')

    # Step 1: Update the Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        UPDATE TeamFormations 
        SET Team1Score =%s, SessionTypeID = %s, LocationID = %s
        WHERE FormationID = %s;
    ''', (TeamFormation_Team1Score, TeamFormation_SessionTypeID,
	TeamFormation_LocationID,TeamFormation_FormationID))
    conn.commit()

    conn.close()
    return jsonify({"message": "TeamFormation updated successfully"}), 200


# Delete TeamFormation Endpoint
@app.route('/deleteTeamFormation', methods=['DELETE'])
def deleteTeamFormation():
    data = request.get_json()

    deleteTeamFormationID = data.get('deleteTeamFormationID')

    # Step 1: Delete from Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        DELETE FROM TeamFormations WHERE FormationID = %s
    ''', (deleteTeamFormationID,))
    conn.commit()

    conn.close()
    return jsonify({"message": "TeamFormations deleted successfully"}), 200
#teamformations player


@app.route('/insertTeamFormationPlayers', methods=['POST'])
def insertTeamFormationPlayers():
    data = request.get_json()

    TeamFormationPlayers_FormationID=data.get('TeamFormationPlayers_FormationID')
    TeamFormationPlayers_TeamID=data.get('TeamFormationPlayers_TeamID')
    TeamFormationPlayers_MemberID=data.get('TeamFormationPlayers_MemberID')
    TeamFormationPlayers_RoleID=data.get('TeamFormationPlayers_RoleID')

    # Step 1: Insert into CommonInfo table and get the CommonID
    conn = get_db_connection()
    cursor = conn.cursor()

    # Step 2: Insert into Personnel table
    cursor.execute('''
        INSERT INTO TeamFormationPlayers (FormationID, TeamID, MemberID, RoleID) 
		VALUES (%s, %s, %s, %s);
    ''', (TeamFormationPlayers_FormationID,TeamFormationPlayers_TeamID,
	TeamFormationPlayers_MemberID,TeamFormationPlayers_RoleID))
    conn.commit()

    conn.close()
    return jsonify({"message": "TeamFormationPlayers inserted successfully"}), 201


# Update TeamFormationPlayers Endpoint
@app.route('/updateTeamFormationPlayers', methods=['POST'])
def updateTeamFormationPlayers():
    data = request.get_json()

    TeamFormationPlayers_update_ID= data.get('TeamFormationPlayers_update_ID')
    TeamFormationPlayers_update_Member_ID= data.get('TeamFormationPlayers_update_Member_ID')
    TeamFormationPlayers_updateRole= data.get('TeamFormationPlayers_updateRole')

    # Step 1: Update the Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        UPDATE TeamFormationPlayers 
        SET RoleID =%s
		WHERE FormationID =%s AND MemberID =%s;
    ''', (TeamFormationPlayers_updateRole, TeamFormationPlayers_update_ID,
	TeamFormationPlayers_update_Member_ID))
    conn.commit()

    conn.close()
    return jsonify({"message": "TeamFormationPlayers updated successfully"}), 200


# Delete TeamFormationPlayers Endpoint
@app.route('/deleteTeamFormationPlayers', methods=['DELETE'])
def deleteTeamFormationPlayers():
    data = request.get_json()

    deleteTeamFormationPlayersID = data.get('deleteTeamFormationPlayersID')
    deleteTeamFormationMemberID = data.get('deleteTeamFormationMemberID')

    # Step 1: Delete from Personnel table
    conn = get_db_connection()
    cursor = conn.cursor()
    cursor.execute('''
        DELETE FROM TeamFormationPlayerss 
		WHERE FormationID = %s AND MemberID = %s;
    ''', (deleteTeamFormationPlayersID,deleteTeamFormationMemberID,))
    conn.commit()

    conn.close()
    return jsonify({"message": "TeamFormationPlayerss deleted successfully"}), 200



if __name__ == '__main__':
    logging.info("Starting Flask server on port 5000...")
    app.run(debug=True, host='0.0.0.0', port=5000)
