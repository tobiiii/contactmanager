# Contact manager API

An application to manage list companies and their contacts

In order to run the application, you need to have:

1. JDK 17.0.6 (https://www.oracle.com/java/technologies/downloads/)
2. Maven 3.6.3 (https://maven.apache.org/download.cgi).
3. Git (https://git-scm.com/downloads)
   You also need to configure your user and system path variables for both java and maven. You also need to have git
   installed in order to clone the project .

### Run it locally

1. Open terminal and clone the repo:

```shell
git clone https://github.com/tobiiii/contactmanager.git
```

2. Make sure you are in project directory in order to build and package the application with the following Cmd Commande:

```shell
mvn clean install
```

3. Go to /target inside contactmanager and run the following command to lunch the application:

```shell
java -jar contactmanager-0.0.1-SNAPSHOT.jar
```

## Data Base

This application has H2 Memory Database,

You can access it by browsing after running the app : http://localhost:8080/h2-console/

Password = admin

User = admin

### Docker
To create an image from our Dockerfile, we have to run ?docker build':
```shell
$> docker build -t my-contactmanager-image .
```

Finally, we're able to run the container from our image:
```shell
$> docker run -p 8080:8080 my-contactmanager-image
```
### Deploy on Render.com Using Jenkins 

1.Set up a Jenkins instance on Render.com:

   Create a new service on Render.com and select "Jenkins" as the service type.

   Configure the necessary settings, such as the service name and environment.

   Deploy the Jenkins service.

2.Configure your Jenkins project:

   Open the Jenkins web interface and navigate to your Jenkins instance.

   Create a new Jenkins project or configure an existing one.

   Configure the necessary settings, such as the project name and repository details.

   Connect the Jenkins project to your GitHub repository.

3.Configure the Jenkins project to use the Jenkinsfile:

   In the Jenkins project configuration, go to the "Pipeline" section.
   
   Select "Pipeline script from SCM" as the Definition.
   
   Choose "Git" as the SCM.
   
   Provide your repository details (e.g., repository URL and credentials).

   Set the "Script Path" to Jenkinsfile.

   Save the Jenkins project configuration.

Now, when you trigger a build for your Jenkins project, it will execute the steps defined in the Jenkinsfile. The Docker image will be built, and the application will be deployed as a container on Render.com, accessible through port 8080.


## API Documentation

The documentation of the API is made by OpenAPI 3.0 , it shows all the Endpoints of the application

You can access it after running the application by browsing : http://localhost:8080/swagger-ui/index.html

## User Guide

once you run the project spring jpa flyway automatically create the tables ,to populate the database with the minimum data you need to user the api execute the scripts from resources/populate/populate_h2_database.sql in the h2 user interface : http://localhost:8080/h2-console/ .

### Use the api :

please get into : http://localhost:8080/swagger-ui/index.html

then follow these instructions :

1. Click on [POST] /api/auth/authentification
2. Click on "Try it out" Button

```shell
{
 "password": "Admin@23",
 "email": "superadmin@test.com"
}
```

3. You need and email and a password to authentifie if you are not the super-admin you need to asq the admin to create a
   user for you.
4. When you create a new user you got an autogenerated password which you can use to log in for the first time and then
   you should change it using the change password api.
5. There are 2 type of users (User,Admin) each one have different profile with different privileges.
6. Once you log in you got an access token that you have to send in any request you made.

### Examples of requests :

create a company:

request:

[POST]
/api/company/add

```shell
{
  "code": "code1",
  "address": "cheraga alger",
  "tva": "BE 0123 456 789",
  "contacts": []
}

 ```

Update an contact:

request:

[PUT]
/api/contact/update/84

```shell
{
  "code": "test",
  "firstName": "iyed",
  "lastName": "rabidi",
  "address": "Boumerdes",
  "tva": "BE 0123 456 111",
  "type": "FREELANCE",
  "companies": []
}

  ```





