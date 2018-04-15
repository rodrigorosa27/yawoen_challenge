# yawoen_challenge

Requirements
------------
1. Java 8
2. Wildfly 12
3. Local MongoDB instance running. The database settings may be customized in the file `src/main/resources/config.properties`

Build
-----
1. `mvn install`

Deploy
------
1. Copy `target/yawoen_challenge-<VERSION>-SNAPSHOT.war` to the Wildfly 12 `deployments` directory
2. While deploying the first time, the application will create a MongoDB database named `yawoen`
 
Using the application
---------------------

By default, the application will load the `Q1_CATALOG.csv` provided in the test, when it started. But, you can change to another file by changing the property `Q1_CATALOG_PAT` into the `config.properties` file.

#### 1. Create or Update Companies ####

You can pass the second file in the body payload:

        POST http://localhost:8080/yawoen_challenge-1.0-SNAPSHOT/service/company
        
#### 2. Querying ####

Query for users company named redbox and zipAddres 17602:

        GET localhost:8080/yawoen_challenge-1.0-SNAPSHOT/search?name=redbox&zip=17602 

You can provide a part of company name in the query param:
  
        GET localhost:8080/yawoen_challenge-1.0-SNAPSHOT/search?name=edbo&zip=17602
