## Simple books API

Simple book API with crud operations, created with the ktor framework, stored using MySQL via JetBrains Exposed and handling database connections with [HikariCP](https://github.com/brettwooldridge/HikariCP) connection pool

## Installation

1. Open cmd / terminal in your computer and enter this command,  then input your mysql password
```bash
mysql -uroot -p
```

2. Create a book database or use another database name on your computer 

```MySQL
CREATE DATABASE books_finder; # or replace it with another name
```

3. Replace hikariConfig.jdbcUrl in file [DatabaseConnection.kt](/src/main/DatabaseConnection.kt) with the name of the database you created earlier

```Kotlin
hikariConfig.jdbcUrl = "jdbc:mysql://localhost:3306/books_finder" // Replace with your database name
```

4. Replace hikariConfig.username and hikariConfig.password in file [DatabaseConnection.kt](/src/main/DatabaseConnection.kt) with the your username and password MySQL database 

```Kotlin
hikariConfig.username = "root" // change to your MySQL username;
hikariConfig.password = "root" // change to your MySQL password;

```

5. Run the application on your computer

6. Run CRUD operations through an application like Postman, you can see the available endpoints in the file [Routing.kt](/src/main/kotlin/dev/onedive/books/finder/infrastructure/ktor/Routing.kt)
