public class SantoriniGame {
	
	private  static String[][] board = new String[5][5];
	private Worker[] workers;
	
	public SantoriniGame()
	{
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				board[i][j] = "";
		
		workers = new Worker[4];
		workers[0] = new Worker("w11");
		workers[1] = new Worker("w12");
		workers[2] = new Worker("w21");
		workers[3] = new Worker("w22");
	}
	
	public String toString()
	{
		
		return	"\n"
			+	"\t   0       1       2       3       4   \n"
			+	"\t-------|-------|-------|-------|-------\n"
			+	"     0  " + board[0][0] + "\t" + board[0][1] + "\t" + board[0][2] + "\t" + board[0][3] + "\t" + board[0][4] + "\n"
			+	"\t-------|-------|-------|-------|-------\n"
			+	"     1  " + board[1][0] + "\t" + board[1][1] + "\t" + board[1][2] + "\t" + board[1][3] + "\t" + board[1][4] + "\n"
			+	"\t-------|-------|-------|-------|-------\n"
			+	"     2  " + board[2][0] + "\t" + board[2][1] + "\t" + board[2][2] + "\t" + board[2][3] + "\t" + board[2][4] + "\n"
			+	"\t-------|-------|-------|-------|-------\n"
			+	"     3  " + board[3][0] + "\t" + board[3][1] + "\t" + board[3][2] + "\t" + board[3][3] + "\t" + board[3][4] + "\n"
			+	"\t-------|-------|-------|-------|-------\n"
			+	"     4  " + board[4][0] + "\t" + board[4][1] + "\t" + board[4][2] + "\t" + board[4][3] + "\t" + board[4][4] + "\n"
			+	"\t-------|-------|-------|-------|-------\n";
	}
	
	public boolean build(int currentPositionX, int currentPositionY, int newPosX, int newposY)
	{
		boolean ableToBuildB = false , ableToBuildD = false;
		
		int numBBox = 0;
		for(int i = 0; i < board[newPosX][newposY].length(); i++) //1,0 2,0
			if (board[newPosX][newposY].charAt(i) == 'B')
				numBBox++;

			if(Math.abs(newPosX - currentPositionX) <=  1 && Math.abs(newposY - currentPositionY) <= 1)
				if(board[newPosX][newposY].contains("w") == false)
					if(board[newPosX][newposY].contains("D") == false)
						if		(numBBox <= 2)
							ableToBuildB = true;
						else if (numBBox == 3)
							ableToBuildD = true;
		
		if(ableToBuildB) {
			board[newPosX][newposY] += "B";
			return true;
		}
		else if(ableToBuildD) {
			board[newPosX][newposY] += "D";
			return true;
		}
		
		else
			return false;
	}
	
	public boolean hasWon()
	{
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				if(board[i][j].contains("BBBw"))
					return true;
		return false;
	}
	
	public int isTrapped()
	{
		boolean w11Trapped = true, w12Trapped = true, w21Trapped = true, w22Trapped = true;
		int posX = 0, posY = 0;
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				if(board[i][j].contains("w11"))
				{
					posX = i;
					posY = j;
				}
		
		for(int i = posX - 1; i <= posX + 1; i++)
			for(int j = posY - 1; j <= posY + 1; j++)
			{
				if(i >= 0 && i <= 4 && j >= 0 && j <= 4)
					if(!board[i][j].contains("w") && !board[i][j].contains("D") && numberOfB(i, j) <= 1)
						w11Trapped = false;
			}
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				if(board[i][j].contains("w12"))
				{
					posX = i;
					posY = j;
				}
		
		for(int i = posX - 1; i <= posX + 1; i++)
			for(int j = posY - 1; j <= posY + 1; j++)
			{
				if(i >= 0 && i <= 4 && j >= 0 && j <= 4)
					if(!board[i][j].contains("w") && !board[i][j].contains("D") && numberOfB(i, j) <= 1)
						w12Trapped = false;
			}
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				if(board[i][j].contains("w21"))
				{
					posX = i;
					posY = j;
				}
		
		for(int i = posX - 1; i <= posX + 1; i++)
			for(int j = posY - 1; j <= posY + 1; j++)
			{
				if(i >= 0 && i <= 4 && j >= 0 && j <= 4)
					if(!board[i][j].contains("w") && !board[i][j].contains("D") && numberOfB(i, j) <= 1)
						w21Trapped = false;
			}
		
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				if(board[i][j].contains("w22"))
				{
					posX = i;
					posY = j;
				}
		
		for(int i = posX - 1; i <= posX + 1; i++)
			for(int j = posY - 1; j <= posY + 1; j++)
			{
				if(i >= 0 && i <= 4 && j >= 0 && j <= 4)
					if(!board[i][j].contains("w") && !board[i][j].contains("D") && numberOfB(i, j) <= 1)
						w22Trapped = false;
			}
		
		if		(w11Trapped && w12Trapped)
			return 1;
		else if	(w21Trapped && w22Trapped)
			return 2;
		
		return 0;
	}
	
	public void reset()
	{
		for(int i = 0; i < 5; i++)
			for(int j = 0; j < 5; j++)
				board[i][j] = "";
		
		for(int i = 0; i < workers.length; i++)
		{
			workers[i].setPosX(-1);
			workers[i].setPosY(-1);
		}
	}
	
	public String[][] getBoard()
	{
		return board;
	}
	
	public Worker[] getWorkers()
	{
		return workers;
	}
	
	public int getWorkersLength()
	{
		return workers.length;
	}
	
	public static int numberOfB(int posX, int posY)
	{
		int count = 0;
		for(int i = 0; i < board[posX][posY].length(); i++)
			if (board[posX][posY].charAt(i) == 'B') 
				count++;
		
		return count;
	}
}