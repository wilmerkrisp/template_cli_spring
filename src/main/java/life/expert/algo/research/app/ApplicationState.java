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


import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class ApplicationState
	{
	private int memory;
	
	public void clear()
		{
		memory = 0;
		}
	}
