package com.izeye.throwaway;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link Application}.
 *
 * @author Johnny Lim
 */
@RunWith(SpringRunner.class)
// FIXME: SpringBootTest.WebEnvironment.NONE causes NoSuchBeanDefinitionException.
@SpringBootTest
public class ApplicationTests {

	@Test
	public void test() {
	}

}
