# yawoen_challenge

Requirements
------------
1. Java 8
2. Maven
3. Local MongoDB instance running. The database settings may be customized in the file `src/main/resources/config.properties`

Deploy
-----
1. `make run`

Obs: When running for the first time, the application need to download the dependencies, and it can take a while. Also, while deploying the first time, the application will create a MongoDB database named as provided in the configuration file. By default it will be ``yawoen``

Using the application
---------------------

By default, the application will load the `Q1_CATALOG.csv` provided in the test, when it started. But, you can change to another file by changing the property `Q1_CATALOG_PAT` into the `config.properties` file.

#### 1. Create or Update Companies ####

To load a file into the application, you can use the follow command, passing the CSV file as a parameter: 

        curl -X POST -H "Content-Type: text/plain" --data-binary @q2_clientData.csv http://localhost:8080/yawoen_challenge-1.0-SNAPSHOT/service/company
        
If you dont provide the header Content-Type as "text/plain" the application will reject the POST by Unsupported Media Type.       
        
#### 2. Querying ####

Query for company named "redbox" and zipAddres "17602":

        GET localhost:8080/yawoen_challenge-1.0-SNAPSHOT/search?name=redbox&zip=17602 

You can provide a part of company name in the query param:
  
        GET localhost:8080/yawoen_challenge-1.0-SNAPSHOT/search?name=edbo&zip=17602
        
The reponse body:

    

TODOs
-----

1. Migrate the project to a Docker Container
2. Improve the error messages for the web services
