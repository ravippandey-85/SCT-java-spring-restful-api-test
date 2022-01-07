package uk.co.huntersix.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;


import java.util.List;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    /* updated the code to send the response as "NO CONTENT" when no match is found for the request
     *Used HTTP Status NO CONTENT 204 here.
     *This is not the case for 404/Bad Request so not used Error codes.
     * Updated Return to be a response entity
     * Exercise 2*/

    @GetMapping("/person/{lastName}/{firstName}")
    public ResponseEntity<Person> person(@PathVariable(value = "lastName") String lastName,
                                         @PathVariable(value = "firstName") String firstName) {
        Person result = personDataService.findPerson(lastName, firstName);
        if (result!=null) {
            return new ResponseEntity<Person>(result, HttpStatus.OK);

        } else {
            return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
        }

    }

    /* Exercise 3 -
     * Added new End point to return records for a surname
     * Used HTTP Status NO CONTENT 204 here when there are no records.
     * Used HTTP Status OK 200 here when there is exact one record.
     * Used HTTP Status MULTIPLE CHOICES 300 here when there are more than one records.
     * Still returning the list of person when size is 1 or more
     * Related messages can be displayed based on the Response code returned
     * */

    @GetMapping("/person/{lastName}")
    public ResponseEntity<List<Person>> personWithSurname(@PathVariable(value = "lastName") String lastName) {
        List<Person> listOfPersonWithLastName = personDataService.findPersonWithLastName(lastName);

        if (listOfPersonWithLastName.isEmpty())
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);
        else if (listOfPersonWithLastName.size() == 1)
            return new ResponseEntity<List<Person>>(listOfPersonWithLastName, HttpStatus.OK);
        else
            return new ResponseEntity<List<Person>>(listOfPersonWithLastName, HttpStatus.MULTIPLE_CHOICES);

    }

    /* Exercise 4 -
     * Added new End point to add a new record in person data
     * Used HTTP Status OK 200 here when record is added successfully.
     * Used HTTP Status CONFLICT 409 here when there is already an existing record.
     * Related messages can be displayed based on the Response code returned
     * */

    @PostMapping("/person/{lastName}/{firstName}")
    public ResponseEntity<Person> addPerson(@PathVariable(value = "lastName") String lastName,
                                            @PathVariable(value = "firstName") String firstName) {
        boolean isRecordAdded = personDataService.addPerson(lastName, firstName);
        if (isRecordAdded) {
            return new ResponseEntity<Person>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Person>(HttpStatus.CONFLICT);
        }

    }
}