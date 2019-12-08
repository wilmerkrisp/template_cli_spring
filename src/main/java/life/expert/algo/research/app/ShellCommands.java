package life.expert.algo.research.app;
//---------------------------------------------
//      ___       __        _______   ______
//     /   \     |  |      /  _____| /  __  \
//    /  ^  \    |  |     |  |  __  |  |  |  |
//   /  /_\  \   |  |     |  | |_ | |  |  |  |
//  /  _____  \  |  `----.|  |__| | |  `--'  |
// /__/     \__\ |_______| \______|  \______/
//
//              Wilmer Krisp 2019
//---------------------------------------------

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
@ShellCommandGroup( "Drawing Commands" )
public class ShellCommands
	{
	
	@Autowired OutputHelper shellHelper;
	
	@Autowired private ApplicationState applicationState;
	
	
	/**
	 * Add int.
	 *
	 * @param a
	 * 	the a
	 * @param b
	 * 	the b
	 *
	 * @return the int
	 */
	@ShellMethod( value = "Add two integers" )
	public int add( final int a ,
	                final int b )
		{
		return a + b;
		}
	
	/**
	 * Add int.
	 *
	 * @param a
	 * 	the a
	 * @param b
	 * 	the b
	 *
	 * @return the int
	 */
	@ShellMethod( value = "Add two integers" )
	public void add2( final int a ,
	                  final int b )
		{
		int c = a + b;
		shellHelper.print( "" + c );
		//return c;
		}
		
	}
	
