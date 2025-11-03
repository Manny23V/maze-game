import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class MazeGame {

public static char[][] maze;
public static int[] playerPosition = new int[2];
	public static void main(String[] args) {

	Scanner input = new Scanner(System.in);

		System.out.println("Enter maze level to play: ");
        System.out.println("1. Level 1");
        System.out.println("2. Level 2");
        System.out.println("3. level 3");
        String choice = input.nextLine();
        
        if(choice.equals("1")){
            createSquareMaze("maze1.txt");
		    loadMaze("maze1.txt");
        }else if(choice.equals("2")){
            createSquareMaze("maze2.txt");
		    loadMaze("maze2.txt");
        }else if(choice.equals("3")){
            createSquareMaze("maze3.txt");
		    loadMaze("maze3.txt");
        }

		playerPosition = findStartPosition();

		boolean gameIsOn = true;
		int playerMoveCount = 0;
		int highScore = 99;

		while(gameIsOn) { 

			displayMaze();
			System.out.println("Move (WASD):");
			String movement = input.nextLine();

			if(movePlayer(movement)) {
				playerMoveCount++;
			}

			if(maze[playerPosition[0]][playerPosition[1]] == 'F') {
				displayMaze();
				System.out.println("Congratulations, you've found the goal!!");

				if(playerMoveCount < highScore) {
					highScore = playerMoveCount;
					System.out.println("NEW HIGH SCORE!!");
					System.out.println("Player number of moves: " + highScore);
				}else{
					System.out.println("Player number of moves: " + highScore);
				}

				System.out.println("Try agin?(yes/no)");
				String continueGame = input.nextLine();
				if(continueGame.equals("yes")) {
					playerMoveCount = 0;
					playerPosition = findStartPosition(); 
				}else if(continueGame.equals("no")) {
					gameIsOn = false;
				}
			}
		}

	System.out.println("Thanks for playing");

	}

	public static void createSquareMaze(String mazeName) {

		try {

		File mazeFile =  new File(mazeName);
		Scanner mazeScanner = new Scanner(mazeFile);

		int size  = 0;

			while(mazeScanner.hasNextLine()) {
				size++;
				mazeScanner.nextLine();
			}
			mazeScanner.close();

		maze = new char[size][size];

		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}

	}

	public static void loadMaze(String mazeName) {

		try {

		File mazeFile =  new File(mazeName);
		Scanner mazeScanner = new Scanner(mazeFile);

		int mazeSize = maze.length;
		int row = 0;

		while(mazeScanner.hasNextLine()) {

			String content = mazeScanner.nextLine();

				for(int c = 0; c < mazeSize; c++) {
					maze[row][c] = content.charAt(c);
				}
				row++;
		}
		mazeScanner.close();

		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}

	}

	public static int[] findStartPosition() {

		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				if(maze[i][j] == 'S') {
					playerPosition[0] = i;
					playerPosition[1] = j;
				}
			}
		}
		return playerPosition;
		
	}

	public static void displayMaze() {

		for(int i = 0; i < maze.length; i++) {
			for(int j = 0; j < maze[i].length; j++) {
				if(i == playerPosition[0] && j == playerPosition[1]){
					System.out.print('P');
				}else{
					System.out.print(maze[i][j]);
				}
			}
			System.out.println();
		}
	}

	public static boolean movePlayer(String movement) {

		int newRow = playerPosition[0];
		int newColumn = playerPosition[1];

		if(movement.equals("w") && maze[newRow - 1][newColumn] != '#') {
			playerPosition[0] = newRow-1;
			return true;
		}else if(movement.equals("s") && maze[newRow + 1][newColumn] != '#') {
			playerPosition[0] = newRow+1;
			return true;
		}else if(movement.equals("a") && maze[newRow][newColumn - 1] != '#') {
			playerPosition[1] = newColumn-1;
			return true;
		}else if(movement.equals("d") && maze[newRow][newColumn + 1] != '#') {
			playerPosition[1] = newColumn+1;
			return true;
		}else if(!(movement.equals("w") || movement.equals("a") || movement.equals("s") || movement.equals("d"))){
			System.out.println("Invalid move");
			return false;
		}
		System.out.println("You can't move through walls!");
		return false;
	}

}

