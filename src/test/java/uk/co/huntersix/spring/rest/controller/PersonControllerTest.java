package uk.co.huntersix.spring.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("firstName").value("Mary"))
                .andExpect(jsonPath("lastName").value("Smith"));
    }

    @Test
    public void shouldNotReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(null);
        this.mockMvc.perform(get("/person/pandey/ravi"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("id").doesNotExist());
    }

    @Test
    public void shouldAddANewPerson() throws Exception {
        when(personDataService.addPerson(any(), any())).thenReturn(true);
        this.mockMvc.perform(post("/person/Pandey/Ravi"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotAddANewPerson() throws Exception {
        when(personDataService.addPerson(any(), any())).thenReturn(false);
        this.mockMvc.perform(post("/person/smith/mary"))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnOneRecordForLastName() throws Exception {
        Person person = new Person("Mary", "Smith");
        List<Person> personList = new ArrayList<Person>();
        personList.add(person);
        when(personDataService.findPersonWithLastName(any())).thenReturn(personList);
        this.mockMvc.perform(get("/person/smith"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNoRecordForLastName() throws Exception {

        List<Person> personList = new ArrayList<Person>();

        when(personDataService.findPersonWithLastName(any())).thenReturn(personList);
        this.mockMvc.perform(get("/person/pandey"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnMultipleRecordForLastName() throws Exception {
        Person person = new Person("Collin", "Brown");
        Person person2 = new Person("Adam", "Brown");
        List<Person> personList = new ArrayList<Person>();
        personList.add(person);
        personList.add(person2);

        when(personDataService.findPersonWithLastName(any())).thenReturn(personList);
        this.mockMvc.perform(get("/person/brown"))
                .andDo(print())
                .andExpect(status().isMultipleChoices());
    }
}