package life.expert.algo.research;
//@Header@
//--------------------------------------------------------------------------------
//
//                          winter  life.expert.algo.research
//                           wilmer 2019/07/25
//
//--------------------------------------------------------------------------------

import life.expert.algo.research.app.OutputHelper;
import life.expert.common.async.LogUtils;
import life.expert.algo.research.app.OutputHelper;
import life.expert.common.async.PrintUtils;
import lombok.extern.slf4j.Slf4j;

//import static life.expert.common.base.Preconditions.*;  //checkCollection
//import static life.expert.common.function.Patterns.*;    //for-comprehension

//@Header@
//--------------------------------------------------------------------------------
//
//                          riso  life.expert.riso
//                           wilmer 2019/07/25
//
//--------------------------------------------------------------------------------
@Slf4j
public final class TestFunctionalHelper
	implements OutputHelper
	{
	StringBuilder terminal = new StringBuilder();
	
	/**
	 * Print message to the console in the success color.
	 *
	 * @param message
	 * 	message to print
	 */
	@Override
	public void printAtSuccess( String message )
		{
		print( message );
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
		terminal.append( message );
		PrintUtils.print( message );
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
		print( message );
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
		print( "Error: " + error );
		}
	
	@Override
	public void flush()
		{
		terminal.setLength( 0 );
		}
	
	@Override
	public String getBuffer()
		{
		return terminal.toString();
		}
	}
