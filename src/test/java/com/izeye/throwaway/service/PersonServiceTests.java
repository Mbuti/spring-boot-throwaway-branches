package com.izeye.throwaway.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link PersonService}.
 *
 * @author Johnny Lim
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTests {
	
	@Autowired
	private PersonService personService;
	
	@Test
	public void testGetMessage() {
		assertThat(this.personService.getMessage()).isEqualTo("Hello, world!");
	}
	
}
