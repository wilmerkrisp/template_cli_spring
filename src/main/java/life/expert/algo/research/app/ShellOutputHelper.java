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
import org.jline.terminal.Terminal;



/**
 * service (static class)
 *
 * <pre>{@code
 *               ShellHelper.compute();
 *               var s=ShellHelper.MY_CONSTANT;
 * }*</pre>
 */
@Slf4j
public final class ShellOutputHelper
	implements OutputHelper
	{
	
	private Terminal terminal;
	
	/**
	 * Instantiates a new Shell helper.
	 *
	 * @param terminal
	 * 	the terminal
	 */
	public ShellOutputHelper( Terminal terminal )
		{
		this.terminal = terminal;
		}
	
	/**
	 * Print message to the console in the success color.
	 *
	 * @param message
	 * 	message to print
	 */
	@Override
	public void printAtSuccess( String message )
		{
		print( "Success: " + message );
		}
	
	/**
	 * Generic Print to the console method.
	 *
	 * @param message
	 * 	message to print
	 */
	@Override
	public void print( String message )
		{
		terminal.writer()
		        .println( message );
		terminal.flush();
		}
	
	/**
	 * Print message to the console in the info color.
	 *
	 * @param message
	 * 	message to print
	 */
	@Override
	public void printAtInfo( String message )
		{
		print( "Info: " + message );
		}
	
	/**
	 * Print message to the console in the warning color.
	 *
	 * @param message
	 * 	message to print
	 */
	@Override
	public void printAtWarning( String message )
		{
		print( "Warning: " + message );
		}
	
	/**
	 * Print message to the console in the error color.
	 *
	 * @param error
	 * 	the error
	 */
	@Override
	public void printAtError( Throwable error )
		{
		print( "Error: " + error.getMessage() );
		}
	
	@Override
	public void flush()
		{
		terminal.flush();
		}
	
	@Override
	public String getBuffer()
		{
		return terminal.toString();
		}
		
	}

