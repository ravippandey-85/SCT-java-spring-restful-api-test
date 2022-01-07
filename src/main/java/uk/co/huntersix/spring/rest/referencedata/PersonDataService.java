package uk.co.huntersix.spring.rest.referencedata;

import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDataService {
    public static final List<Person> PERSON_DATA = Arrays.asList(
            new Person("Mary", "Smith"),
            new Person("Brian", "Archer"),
            new Person("Collin", "Brown"),
            new Person("Adam", "Brown") // Added this record for exercise 3, multiple records for same surname
    );

    /* updated the code for Exercise 2
     * returning null when no matching record is found
     * */
    public Person findPerson(String lastName, String firstName) {
        List<Person> person = PERSON_DATA.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        if (person.size() > 0)
            return person.get(0);
        else
            return null;

    }

    /* added the code for Exercise 3
     * to find a person with matching surname/lastname
     * it could result in multiple records , so have used List
     * */

    public List<Person> findPersonWithLastName(String lastName) {
        return PERSON_DATA.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    /* added the code for Exercise 4
     * to add a new Person record
     * it validates if record already exists and then process
     * */
    public boolean addPerson(String lastName, String firstName) {
        boolean isRecordAdded = false;
        boolean isExistingRecord = PERSON_DATA.stream()
                .anyMatch(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName));
        if (!isExistingRecord) {
            Person newPerson = new Person(firstName, lastName);

            // Below code was added to test Add new Person
            // PERSON_DATA will not support add so made a copy and then performed add Operation.

            List<Person> modifiableList = new ArrayList<>(PERSON_DATA);
            modifiableList.add(newPerson);

            isRecordAdded = true;
        }
        return isRecordAdded;
    }
}
