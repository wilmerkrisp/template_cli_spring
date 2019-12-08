package life.expert.algo.research.app;
//@Header@
//--------------------------------------------------------------------------------
//
//                          winter  life.expert.algo.research.base
//                           wilmer 2019/07/25
//
//--------------------------------------------------------------------------------

//@Header@
//--------------------------------------------------------------------------------
//
//                          riso  life.expert.riso.app
//                           wilmer 2019/07/25
//
//--------------------------------------------------------------------------------

//public class ShellCommandsTest
import life.expert.algo.research.InvokeHelper;
import life.expert.algo.research.app.ShellCommands;
import life.expert.algo.research.InvokeHelper;
import life.expert.algo.research.TestApplicationConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.ConfigurableCommandRegistry;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.standard.StandardMethodTargetRegistrar;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.util.ReflectionUtils.findMethod;

/**
 * Illustrative functional tests for the Spring Shell Calculator application. These
 * functional tests use Spring Shell commands auto-wired by the Spring Test Runner outside
 * of the shell, to test functionality of the commands.
 *
 * @author Sualeh Fatehi
 */
@RunWith( SpringJUnit4ClassRunner.class )

@ContextConfiguration( classes = { TestApplicationConfiguration.class ,
                                   ShellCommands.class } )
public class ShellCommandsTest
	extends InvokeHelper
	{
	
	private static final Class<ShellCommands> COMMAND_CLASS_UNDER_TEST = ShellCommands.class;
	
	private final ConfigurableCommandRegistry registry = new ConfigurableCommandRegistry();
	
	@Autowired private OutputHelper outputHelper;
	
	@Autowired private ApplicationContext context;
	
	@Before
	public void setup()
		{
		final StandardMethodTargetRegistrar registrar = new StandardMethodTargetRegistrar();
		registrar.setApplicationContext( context );
		registrar.register( registry );
		
		///state.clear();
		}
	
	@Test
	public void testAdd()
		{
		final String command       = "add";
		final String commandMethod = "add";
		
		final MethodTarget commandTarget = lookupCommand( registry , command );
		assertThat( commandTarget , notNullValue() );
		assertThat( commandTarget.getGroup() , is( "Drawing Commands" ) );
		assertThat( commandTarget.getHelp() , is( "Add two integers" ) );
		assertThat( commandTarget.getMethod() , is( findMethod( COMMAND_CLASS_UNDER_TEST , commandMethod , int.class , int.class ) ) );
		assertThat( commandTarget.getAvailability()
		                         .isAvailable() , is( true ) );
		assertThat( invoke( commandTarget , 1 , 2 ) , is( 3 ) );
		//assertThat( state.getMemory() , is( 0 ) );
		}
	
	@Test
	public void testAdd2()
		{
		final String command       = "add2";
		final String commandMethod = "add2";
		
		final MethodTarget commandTarget = lookupCommand( registry , command );
		assertThat( commandTarget , notNullValue() );
		assertThat( commandTarget.getGroup() , is( "Drawing Commands" ) );
		assertThat( commandTarget.getHelp() , is( "Add two integers" ) );
		assertThat( commandTarget.getMethod() , is( findMethod( COMMAND_CLASS_UNDER_TEST , commandMethod , int.class , int.class ) ) );
		assertThat( commandTarget.getAvailability()
		                         .isAvailable() , is( true ) );
		invoke( commandTarget , 1 , 2 );
		//assertThat( invoke( commandTarget , 1 , 2 ) , is( 3 ) );
		assertThat( outputHelper.getBuffer() , is( "3" ) );
		//System.out.println( "ShellCommandsTest testAdd2 " + outputHelper.getBuffer() );
		
		///assertThat( state.getMemory() , is( 0 ) );
		}
	
	//	@Test
	//	public void testaddToMemory()
	//		{
	//		final String command       = "add-to-memory";
	//		final String commandMethod = "addToMemory";
	//
	//		final MethodTarget commandTarget = lookupCommand( registry , command );
	//		assertThat( commandTarget , notNullValue() );
	//		assertThat( commandTarget.getGroup() , is( "Calculator Commands" ) );
	//		assertThat( commandTarget.getHelp() , is( "Add an integer to the value in memory" ) );
	//		assertThat( commandTarget.getMethod() , is( findMethod( COMMAND_CLASS_UNDER_TEST , commandMethod , int.class ) ) );
	//		assertThat( commandTarget.getAvailability()
	//		                         .isAvailable() , is( true ) );
	//
	//		state.setMemory( 1 );
	//		assertThat( invoke( commandTarget , 2 ) , is( 3 ) );
	//		assertThat( state.getMemory() , is( 3 ) );
	//		}
	
	}