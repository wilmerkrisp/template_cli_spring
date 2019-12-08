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


public interface OutputHelper
	{
	public void printAtSuccess( String message );
	
	public void print( String message );
	
	public void printAtInfo( String message );
	
	public void printAtWarning( String message );
	
	public void printAtError( Throwable error );
	
	public void flush();
	
	public String getBuffer();
	}