# Hunter Six - Java Spring RESTful API Test

## How to build
```./gradlew clean build```

## How to test
```./gradlew test```

## Exercises
### Exercise 1
Make the tests run green (there should be one failing test)

Changes Made - 

1. 
- File name - Person.java
Change - 
- // Included keyword static to solve the failing test case - Exercise 1
private static final AtomicLong counter = new AtomicLong();
 

### Exercise 2
Update the existing `/person/{lastName}/{firstName}` endpoint to return an appropriate RESTful response when the requested person does not exist in the list
- prove your results

Changes Made - 
1. File Name - PersonController.java
updated the code to send the response as "NO CONTENT" when no match is found for the request
Used HTTP Status NO CONTENT 204 here.
This is not the case for 404/Bad Request so not used Error codes.
Updated Return to be a response entity

2. File Name - PersonDataService.java
   updated findPerson method to return null when no matching record is found

### Exercise 3
Write a RESTful API endpoint to retrieve a list of all people with a particular surname
- pay attention to what should be returned when there are no match, one match, multiple matches
- prove your results

Changes Made -
1. File Name - PersonController.java
   Added new End point to return records for a surname
   Used HTTP Status NO CONTENT 204 here when there are no records.
   Used HTTP Status OK 200 here when there is exact one record.
   Used HTTP Status MULTIPLE CHOICES 300 here when there are more than one records.
   Still returning the list of person when size is 1 or more
   Related messages can be displayed based on the Response code returned

2. File Name - PersonDataService.java
   added a new method findPersonWithLastName to find a person with matching surname/lastname
   it could result in multiple records , so have used List

Note : An alternate way to do this was to use existing end point for finding a person based on firstName and LastName, 
by making fields optional and then if firstName is null/empty check for lastName records and vice versa.


### Exercise 4
Write a RESTful API endpoint to add a new value to the list
- pay attention to what should be returned when the record already exists
- prove your resutls

Changes Made -
1. File Name - PersonController.java
   Added new End point to add a new record in person data
   Used HTTP Status OK 200 here when record is added successfully.
   Used HTTP Status CONFLICT 409 here when there is already an existing record.
   Related messages can be displayed based on the Response code returned

2. File Name - PersonDataService.java
   added a new method addPerson to add a new Person record after validating if record already exists