# POC Property Management Applicaton

A simple application as PoC for Spring Boot and security, with their corresponding tests.

### TABLES

The application uses RDBMS as backend, and uses a single table. Before attempting to run the application, you need to set up your database details in

> src/main/resources/application.properties

> spring.datasource.url

> spring.datasource.username

> spring.datasource.password

Once your database is ready, you can create the table with the query below:

    CREATE TABLE `property` (
      `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
      `goal` varchar(20) NOT NULL DEFAULT '',
      `location` text,
      `size` float NOT NULL,
      `description` text NOT NULL,
      `deposit` float DEFAULT '0',
      `fee` float DEFAULT '0',
      `price` float NOT NULL,
      `seller` varchar(20) NOT NULL DEFAULT '',
      `negotiable` tinyint(1) DEFAULT '0',
      `status` varchar(20) NOT NULL DEFAULT '',
      `type` varchar(20) NOT NULL DEFAULT '',
      `furnish` varchar(20) NOT NULL DEFAULT '',
      `bedrooms` int(11) NOT NULL,
      `bathrooms` int(11) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;


### Running the application

>  mvn spring-boot:run

As it uses authorization, for every single request, you need to add the below http header to you request, to enable access with the predefined test user

> Authorization  = Basic YWRtaW46cGFzc3dvcmQ=

To run the tests

> mvn test

#### Limitations

The application assumes very basic input formats and operations as follows:

creating a property
updating a property
approving a pending property creation
searching

The requests requires JSON format.

##### Example search queries

{ } - Returns everything, with default boolean field values

{"size":50 } - Returns everything, with default boolean field values, with mininum size of 50 square meters

{"size":50, "maxSize":100 } - Returns everything, with default boolean field values, with mininum size of 50 to a max size of 100 square meters

For further details on the fields and names, please check 

`src/main/java/property/management/api/utils/PropertySearchQuery.java` 


##### The application works with JDK 8+
