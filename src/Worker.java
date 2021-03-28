
public class Worker {
	
	private String workerName;
	private int positionX = -1;
	private int positionY = -1;
	
	public Worker(String name)
	{
		workerName = name;
	}
	
	public boolean placeWorker(int newPosX, int newPosY, SantoriniGame game)
	{
		boolean occupied = false;
		Worker[] tempWorkers = game.getWorkers();
		
		for(int i = 0; i < tempWorkers.length; i++)
			if(tempWorkers[i].getPosX() == newPosX && tempWorkers[i].getPosY() == newPosY)
				occupied = true;
		
		if (occupied) {
			System.out.println("Error, there is already a worker in this position.");
			return false;
		}

		if (newPosX < 0 || newPosX > 4 || newPosY < 0 || newPosY > 4) {
			System.out.println("Error, please enter numbers from 0 to 4.");
			return false;
		}
		else {
			String[][] tempBoard = game.getBoard();
			positionX = newPosX;
			positionY = newPosY;
			
			tempBoard[newPosX][newPosY] = workerName;
			return true;
		}
	}
	
	public boolean move(int newPosX, int newPosY, SantoriniGame game)
	{
		String[][] tempBoard = game.getBoard();
		boolean ableToMove = false;
		int count = 0;
			
		for(int i = 0; i < tempBoard[positionX][positionY].length(); i++)
			if (tempBoard[positionX][positionY].charAt(i) == 'B') 
				count++;
		int numBCurrentBox = count;
		count = 0;
		
		for(int i = 0; i < tempBoard[newPosX][newPosY].length(); i++)
			if (tempBoard[newPosX][newPosY].charAt(i) == 'B') 
				count++;
		int numBNewBox	   = count;
		
		if(newPosX >= 0 && newPosX <= 4 && newPosY >= 0 && newPosY <= 4)
			if(Math.abs(newPosX - positionX) <=  1 && Math.abs(newPosY - positionY) <= 1)
				if(tempBoard[newPosX][newPosY].contains("w") == false)
					if(tempBoard[newPosX][newPosY].contains("D") == false)
						if(numBNewBox - numBCurrentBox < 2)
							ableToMove = true;
			
		if(!ableToMove)
			return false;
		
		tempBoard[positionX][positionY] = tempBoard[positionX][positionY].replaceAll(workerName, "");
		tempBoard[newPosX][newPosY] += workerName;
		
		positionX = newPosX;
		positionY = newPosY;
		
		return true;
	}
	
	public int getPosX()
	{
		return positionX;
	}
	
	public int getPosY()
	{
		return positionY;
	}
	
	public void setPosX(int x)
	{
		positionX = x;
	}
	
	public void setPosY(int y)
	{
		positionY = y;
	}
}
