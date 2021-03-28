import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Santorini {
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner inFile = new Scanner(new FileInputStream("wining_file.txt"));
		SantoriniGame game = new SantoriniGame();
		
		boolean	isMovingOtherWorkers = false, isPlayer1 = false, ableToMove = false, ableToBuild = false;
		int		workerIndex = 0, posX = 0, posY = 0;
		String resetDecision = "";
		
		System.out.println("Welcome to Santorini Board Game Created by Team 25!\n\n\n");
		System.out.print("Enter Player 1 Name: ");
		String player1 = inFile.next();
		System.out.println();
		System.out.print("Enter Player 2 Name: ");
		String player2 = inFile.next();
		System.out.println();
		
		//The loop for the whole game, next iteration when user asks to reset game.
		do {
			
			//Loop for initial placement for workers.
			for (int i = 0; i < game.getWorkersLength(); i++) {
				
				if (i <= 1)
					System.out.print(player1 + ", please enter one of your workers name, followed by the desired position [w11 or w12]: ");
				else
					System.out.print(player2 + ", please enter one of your workers name, followed by the desired position [w21 or w22]: ");
				System.out.println();
				switch (inFile.next().toLowerCase()) {
				case "w11": workerIndex = 0; break;
				case "w12": workerIndex = 1; break;
				case "w21": workerIndex = 2; break;
				case "w22": workerIndex = 3; break;
				default:
					workerIndex = -1;
					System.out.println("Error, please enter either w11, w12, w21, w22.");
					i--;
				}
				
				posX = inFile.nextInt();
				posY = inFile.nextInt();
				
				if(i <= 1 && (workerIndex == 2 || workerIndex == 3)) {
					System.out.println("Error, you can only move w11 or w12 (your workers).");
					i--;
				}
				
				else if(i >= 2 && (workerIndex == 0 || workerIndex == 1)) {
					System.out.println("Error, you can only move w21 or w22 (your workers)");
					i--;
				}
				
				
				else if(workerIndex != -1)
				{
					Worker[] tempWorkers = game.getWorkers();
					if (tempWorkers[workerIndex].placeWorker(posX, posY, game) == false)
						i--;
				}

			}
			
			//In this position, all workers have been deployed in their positions.
			
			System.out.println(game);
			
			//Loop for the turns (Player 1 starts first)
			do {
				isPlayer1 = !isPlayer1;

				if (isPlayer1)
					System.out.println("Player 1 [" + player1 + "] is playing now!");
				else
					System.out.println("Player 2 [" + player2 + "] is playing now!");
				
				//Loop to check worker choice depending on player.
				do {
					System.out.print("Which worker do you want to move? ");
					switch (inFile.next().toLowerCase()) {
					case "w11": workerIndex = 0; break;
					case "w12": workerIndex = 1; break;
					case "w21": workerIndex = 2; break;
					case "w22": workerIndex = 3; break;
					}
					
					if		( isPlayer1 && (workerIndex == 2 || workerIndex == 3)) {
						System.out.println("Error, you can only move w11 or w12 (your workers)");
						isMovingOtherWorkers = true;
						}
					
					else if (!isPlayer1 && (workerIndex == 0 || workerIndex == 1)) {
						System.out.println("Error, you can only move w21 or w22 (your workers)");
						isMovingOtherWorkers = true;
					}
					
					else
						isMovingOtherWorkers = false;
					
				} while (isMovingOtherWorkers);

				
				//Loop to check if worker is able to move
				do {
					System.out.print("Enter the worker's new position: ");
					posX = inFile.nextInt();
					posY = inFile.nextInt();
					
					Worker[] tempWorkers = game.getWorkers();
					ableToMove = tempWorkers[workerIndex].move(posX, posY, game);
					if (!ableToMove)
						System.out.println("Error, please try again.");
				} while (!ableToMove);

				
				System.out.println(game);

				//Check winning
				if (game.hasWon()) {
					if (isPlayer1)
						System.out.println("Congratulations " + player1 + ", You Won!");
					else
						System.out.println("Congratulations " + player2 + ", You Won!");
				}
				
				//Check is a player is trapped (both his workers)
				else if	(game.isTrapped() == 1)
					System.out.println(player1 + " has been trapped, the winner is " + player2);
				else if	(game.isTrapped() == 2)
					System.out.println(player2 + " has been trapped, the winner is " + player1);
				
				//No one won nor trapped, continue game.
				else
				{
					//Loop to check if worker is able to build
					do {
						System.out.print("Enter building position: ");
						
						Worker[] tempWorkers = game.getWorkers();
						ableToBuild = game.build(tempWorkers[workerIndex].getPosX(), tempWorkers[workerIndex].getPosY(), inFile.nextInt(),
								inFile.nextInt());
						
						if (!ableToBuild)
							System.out.println("Error, please try again.");
					} while (!ableToBuild);
					
					System.out.println(game);

					//Check if someone is trapped, no winning checking here (BBBw) because no one logically can win after a building proccess.
					if		(game.isTrapped() == 1)
						System.out.println(player1 + " has been trapped, the winner is " + player2);
					else if (game.isTrapped() == 2)
						System.out.println(player2 + " has been trapped, the winner is " + player1);
				}
			}while(!game.hasWon() && game.isTrapped() != 1 && game.isTrapped() != 2);


			System.out.print("\nWould you like to play again? [Y to play again, and any other letter to end]: ");
			resetDecision = inFile.next();
			
			if(resetDecision.equalsIgnoreCase("Y"))
				game.reset();
			
		} while(resetDecision.equalsIgnoreCase("Y"));
		
		System.out.println("\nThank you for playing.");
		System.out.println("Team: 25\n1. Abdulaziz\t201746690\n2. Abdulelah\t201727090\n3. Nawaf\t201744870\n");
		inFile.close();
	}
}