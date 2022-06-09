package lab.mockito.more;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DelegateControllerTest_DoNothing {

	private DelegateController controller;

	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;
	@Mock
	private RequestDispatcher dispatcher;
	@Mock
	private LoginController loginController;
	@Mock
	private MessageRepository repository;
	@Mock
	private ErrorHandler errorHandler;

	@BeforeEach
	public void beforeEveryTest() {
		controller = new DelegateController(loginController, errorHandler, repository);
	}

	@Test
	public void Should_finds_error_message_and_routes_to_error_page_When_subsystem_throws_exception() 
																						throws Exception {
		// Given
		doThrow(new IllegalStateException("LDAP error"))
			.when(loginController).process(request, response);

		doNothing()
			.when(errorHandler).mapTo(isA(Error.class));
		
		when(request.getServletPath()).thenReturn("/logon.do");
		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
		
		// When
		controller.doGet(request, response);
		// Then
		verify(request).getRequestDispatcher(eq("error.jsp"));
		verify(dispatcher).forward(request, response);
	}
	
}