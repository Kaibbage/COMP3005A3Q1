# COMP3005A3Q1

Video link: https://www.youtube.com/watch?v=0xI8AadERAg

Instructions:
1. Download these files from my github
2. Create a new database in pgAdmin
3. Open the query tool for your new database
4. Enter the following paragraph and execute to initialize a table


    CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    enrollment_date DATE
    );

5. Enter the following paragraph to add some values to the new table 


    INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
    ('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
    ('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
    ('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');


6. Change these three variables so they match the values on your computer


    String url = "jdbc:postgresql://<HOST>:<PORT>/<DATABASE_NAME>";
    String user = "<USERNAME>";
    String password = "<PASSWORD>";

7. Run the java program (I run mine in Intellij)


Notes:
- For this program to run my lib folder needs to contain a .jar file that I downloaded for jdbc and add it to the project as a library
- If you downloaded my files from github it shouldn't matter as it will already be there
- If you do need to download it here is the linlk: https://jdbc.postgresql.org/download/