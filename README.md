Java version - 11
Postgres version- 12.2

Set up and installation steps:
1) Install any IDE Eclipse,STS or IntelliJ.
2) Download the ZIP from github.
3) Install Postgres 12.2 to the system.
4) Create a database in postgres with name "shareit_local" and port "5432" as mentioned in application.properties.
4) Make sure that system has right version of JDK installed.
5) Import project in IDE.
6) Clean up and build the project.
7) Run the project as spring boot application.

Features:
1) JWT based authentication.
2) File uploading along with title and description.
3) File downloading using the URL provided in response by upload request.
4) Files added can be listed by user by calling "fetchAllFiles" API.
5) File can be deleted by calling "deleteFile" API.

Note: Front-end has not been developed. So, installation of Postman is required to hit APIs and test application.

Steps to test application:
1) To register a user make a POST request using postman.
   Register API - http://localhost:8080/register
   Register Request Body Format - 
   {
	"username": "Rohan",
	"password": "123Pass",
	"email": "rohan@gmail.com"
  }
  Please edit the request body as required to register different users.
  
2) To login as a user make a POST request using postman.
   Login API - http://localhost:8080/authenticate
   Login Request Body Format - 
   {
	"username": "Rohan",
	"password": "123Pass",
	"email": "rohan@gmail.com"
  }
  Please edit the request body as required to login different users.
  Note copy the JWT token provided as response for future requests.
  
3) To upload a file make a POST request using postman.
    Upload API - http://localhost:8080/uploadFile
    Add JWT token to header using following steps:
    - Click on header tab
    - In the Key add Authorization
    - In corresponding value section add Bearer followed by space followed by jwt token received by login request.
    - Eg. Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmVybmEiLCJleHAiOjE1ODc5MTAzMzUsImlhdCI6MTU4Nzg5MjMzNX0.0wJrzuYwsid7OwIiJAJfLFz465PDb7ZTlGuwHhtY9lzaiVqWfI158RQdsXe0L7beu0Xe1G25YVdwWgpMfeZcRA
    - Click on body tab 
    - In the Key add "file" and hover to the right side of key input box to choose file among file/text from dropdown.
    - In the value section select files option will appear. Select any file from your system to upload upto 1GB size.
    - In next Key add "fileDTO" and in corresponding value input box add title and description as json
      eg.{
        "title":"Aadhar",
        "description": "My aadhar card"
      }
      - Click on send button once done with above steps.
      - A file download URL will appear as response which can be used to download file in future.
      
  4) To download a file make GET request through browser by entering the URL provided by upload request.
  Note- Download request is not authenticated so that provided URL can be shared and anybody receiving it can download file 
  irrespective of the fact that he/she is authenticated user or not.
  
  5) To fetch all uploaded files make a GET request through postman by adding JWT token in header as described in upload request.
    Fetch Files API: http://localhost:8080/fetchAllFiles
    
  6) To delete an uploaded file make A POST request through postman by adding JWT token in header as described in upload request.
    Delete File API: http://localhost:8080/deleteFile
    In Request body add any JSON object from the response of fetchALlFiles API.
    For eg. {
        "id": 23,
        "title": "Aadhar",
        "description": "My aadhar card",
        "createdAt": "2020-04-26T09:37:52.104+0000"
    }
    
  Note: All the above API need authorization header except register, login and download API. So, make sure that header is added with 
  correct JWT token before making any other request.
  
      
   
   
