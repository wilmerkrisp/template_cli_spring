 package life.expert.algo.research;
//@Header@
//--------------------------------------------------------------------------------
//
//                          riso  life.expert.riso
//                           wilmer 2019/07/22
//
//--------------------------------------------------------------------------------

import org.springframework.shell.CommandRegistry;
import org.springframework.shell.MethodTarget;

import javax.validation.constraints.NotNull;

import static org.springframework.util.ReflectionUtils.invokeMethod;

public class InvokeHelper
	{
	
	protected <T> T invoke( final MethodTarget methodTarget ,
	                        final Object... args )
		{
		return (T) invokeMethod( methodTarget.getMethod() , methodTarget.getBean() , args );
		}
	
	protected MethodTarget lookupCommand( @NotNull final CommandRegistry registry ,
	                                      @NotNull final String command )
		{
		return registry.listCommands()
		               .get( command );
		}
		
	}
