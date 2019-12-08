package life.expert.algo.research;
//@Header@
//--------------------------------------------------------------------------------
//
//                          winter  life.expert.algo.research
//                           wilmer 2019/07/25
//
//--------------------------------------------------------------------------------

import life.expert.algo.research.app.ApplicationState;
import life.expert.algo.research.app.OutputHelper;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.PromptProvider;

/**
 * The type Test application configuration.
 */
@TestConfiguration
public class TestApplicationConfiguration
	{
	
	/**
	 * After each command invocation, the shell waits for new input from the user, displaying a prompt in yellow.
	 *
	 * @return the prompt provider
	 */
	@Bean
	public PromptProvider customPromptProvider()
		{
		return () -> new AttributedString( "enter command: " , AttributedStyle.DEFAULT.foreground( AttributedStyle.YELLOW ) );
		}
	
	/**
	 * Shell helper shell helper.
	 *
	 * @return the shell helper
	 */
	@Bean
	public OutputHelper shellHelper()
		{
		return new TestFunctionalHelper();
		}
	
	/**
	 * Application state application state.
	 *
	 * @return the application state
	 */
	@Bean
	public ApplicationState applicationState()
		{
		return new ApplicationState();
		}
	
	 
	}