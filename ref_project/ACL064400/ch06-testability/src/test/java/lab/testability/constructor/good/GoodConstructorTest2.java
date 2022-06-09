package lab.testability.constructor.good;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import lab.testability.DatabaseDependency;
import lab.testability.FileReadDependency;

@RunWith(MockitoJUnitRunner.class)
public class GoodConstructorTest2 {
	
	@Mock
	DatabaseDependency dep1;
	@Mock
	FileReadDependency dep2;

	@InjectMocks
	GoodConstructor instance;

	@Test
	public void show_object_is_not_null() throws Exception {
		assertNotNull(instance);
		assertEquals("Jim", instance.someMethod("Jim"));
	}
}
