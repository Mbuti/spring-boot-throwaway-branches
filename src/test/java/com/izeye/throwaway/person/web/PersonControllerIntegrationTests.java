package com.izeye.throwaway.person.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izeye.throwaway.person.domain.Person;
import com.izeye.throwaway.person.domain.TestDomainFactory;
import com.izeye.throwaway.person.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Integration tests for {@link PersonController}.
 *
 * @author Johnny Lim
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonControllerIntegrationTests {
	
	@MockBean
	PersonService personService;
	
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
		setUpRestTemplate();

		given(this.personService.findAll())
				.willReturn(Collections.singletonList(TestDomainFactory.createPerson()));

		String firstName = "John";
		Person person = new Person();
		person.setFirstName(firstName);
		given(this.personService.findByFirstName(firstName))
				.willReturn(person);
	}

	private void setUpRestTemplate() {
		RestTemplate innerRestTemplate = this.restTemplate.getRestTemplate();
		for (HttpMessageConverter<?> messageConverter : innerRestTemplate.getMessageConverters()) {
			if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter
						= (MappingJackson2HttpMessageConverter) messageConverter;
				mappingJackson2HttpMessageConverter.setObjectMapper(this.objectMapper);
			}
		}
	}

	@Test
	public void testFindAllWithStringResponse() {
		String response = this.restTemplate.getForObject("/persons", String.class);
		System.out.println(response);
	}

	@Test
	public void testFindAllWithTypedResponse() {
		ResponseEntity<List<Person>> response = this.restTemplate.exchange(
				"/persons", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Person>>() {});
		List<Person> persons = response.getBody();
		System.out.println(persons);
		assertThat(persons.size()).isEqualTo(1);
		
		Person person = persons.get(0);
		assertThat(person.getId()).isNotNull();
		assertThat(person.getFirstName()).isNotNull();
		assertThat(person.getLastName()).isNotNull();
		assertThat(person.getAge()).isNotNull();
		assertThat(person.getCreatedTime()).isNotNull();
	}
	
	@Test
	public void testFindByFirstName() {
		String firstName = "John";
		
		Person person = this.restTemplate.getForObject(
				"/persons/search?firstName={firstName}", Person.class, firstName);
		System.out.println(person);
		assertThat(person.getFirstName()).isEqualTo(firstName);
	}
	
}