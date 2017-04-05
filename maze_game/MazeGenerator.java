package maze_game;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Random;
public class MazeGenerator {
	//This class will have 2 functions, a constructor which will generate three different mazes
	//And it will also have a maze checker using the flood fill method. 
	//The flood fill method will work towards checking whether or not a maze is able to be finished.
	//Will output mazes as txt files, which will be read by Game.java
	public MazeGenerator(ArrayList<Character> monsters, ArrayList<Character> treasures, ArrayList<Character> weapons)
	{
		//Size of the mazes will always be 40x40
		try {
		int mazescreated = 1;
		String[] filenames = {"bin//maze_game//map01.txt","bin//maze_game//map02.txt","bin//maze_game//map03.txt"};
		int monstercount = 100; //Number of monsters found in each maze
		int wallcount = 600; //Number of walls found in each maze
		int treasurecount = 50; //Number of treasures found in each maze
		int weaponcount = 25; //Number of weapons found in each maze
		int possiblemonsters = monsters.size();
		int possibletreasures = treasures.size();
		int possibleweapons = weapons.size();
		while (mazescreated<= 3)
		{
			//Generate maze
			char[][] matrix = generate(wallcount, treasurecount, monstercount,weaponcount,possiblemonsters,possibletreasures,possibleweapons,monsters,treasures,weapons);
			while (FloodFill(matrix)!= true)
			{
				matrix = generate(wallcount, treasurecount, monstercount,weaponcount,possiblemonsters,possibletreasures,possibleweapons,monsters,treasures,weapons);
			}
			PrintWriter printer = new PrintWriter(filenames[mazescreated-1], "UTF-8");
			char[] OutputLine = new char[40];
			for (int i = 0; i < 40; i++)
			{
				for (int j = 0; j < 40; j++)
				{
					OutputLine[j] = matrix[i][j]; 
				}
				printer.println(OutputLine);
			}
			printer.close();
			mazescreated++;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public char[][] generate(int wallcount, int treasurecount, int monstercount, int weaponcount, int possiblemonsters, int possibletreasures, int possibleweapons, ArrayList<Character> monsters, ArrayList<Character> treasures, ArrayList<Character> weapons)
	{
		char[][] matrix = new char[40][40];
		Random generator = new Random();
		for (int i = 0; i < 40; i++)
		{
			for(int j = 0; j < 40; j++)
				matrix[i][j] = ' ';
		}
		matrix[1][1] = 'U';
		for (int i = 0; i < wallcount; i++)
		{
			int row = generator.nextInt(39);
			int col = generator.nextInt(39);
			while (matrix[row][col] != ' ')
			{
				row = generator.nextInt(39);
				col = generator.nextInt(39);
			}
			matrix[row][col] = 'W';//fills the maze with walls
		}
		for (int j = 0; j < monstercount; j++)
		{
			int row = generator.nextInt(39);
			int col = generator.nextInt(39);
			int mnumber = generator.nextInt(possiblemonsters);
			while (matrix[row][col] != ' ')
			{
				row = generator.nextInt(39);
				col = generator.nextInt(39);//fills the maze with monsters
			}
			Character adder = monsters.get(mnumber);
			matrix[row][col] = (char) adder;
			
		}
		for (int k = 0; k < treasurecount; k++)
		{
			int row = generator.nextInt(39);
			int col = generator.nextInt(39);
			int tnumber = generator.nextInt(possibletreasures);
			while (matrix[row][col] != ' ')
			{
				row = generator.nextInt(39);
				col = generator.nextInt(39);
			}
			Character adder = treasures.get(tnumber);
			matrix[row][col] = (char) adder; //fills the maze with treasures
		}
		for (int l = 0; l < weaponcount; l++)
		{
			int row = generator.nextInt(39);
			int col = generator.nextInt(39);
			int wnumber = generator.nextInt(possibleweapons);
			while (matrix[row][col] != ' ')
			{
				row = generator.nextInt(39);
				col = generator.nextInt(39);
			}
			Character adder = weapons.get(wnumber);
			matrix[row][col] = (char) adder; //fills the maze with weapons
		}
		int erow = generator.nextInt(39);
		int ecol = generator.nextInt(39);
		while (matrix[erow][ecol] != ' ')
		{
			erow = generator.nextInt(39);
			ecol = generator.nextInt(39);
		}
		matrix[erow][ecol] = 'E'; //finds an exit spot.
		return matrix;
	}
	public boolean FloodFill(char[][] m)
	{
		//Will check if a maze m has a path from the player to the end point
		//If so, then it will return true
		//Else, return false
		int erow = 0;
		int ecol= 0;
		boolean returner;
		boolean[][] floodfill = new boolean[40][40]; //if the player spot and exit spot are both marked, then we're good
		//We know that the player will always start at (1,1). While going through the for loop, should also find/store information on where the exit will be.
		boolean breaker = false;
		for (int a = 0; a < 40; a++)
		{
			for (int b = 0; b < 40; b ++)
			{
				floodfill[a][b] = false;
			}
		}
		int i = 1;
		int j = 1;
		while (i <= 39)
		{
			while (j <= 39)
			{
				//need to check for all possible special cases and boundaries.
				//any test variable 

				char compare = m[i][j];
				if (compare != 'W')
				{
					floodfill[i][j] = true;
					if ((i == 0) && (j == 0))
					{
						char test1 = m[i][j+1];
						char test2 = m[i+1][j];
						if (test1 != 'W')
						{
							floodfill[i][j+1] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i+1][j] = true;
						}
					}
					else if ((i==0) &&((j!=0)||(j!=39)))
					{
						char test1 = m[i][j+1];
						char test2 = m[i][j-1];
						char test3 = m[i+1][j];
						if (test1 != 'W')
						{
							floodfill[i][j+1] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i][j-1] = true;
						}
						if (test3 != 'W')
						{
							floodfill[i+1][j] = true;
						}
					}
					else if ((i==0) && (j == 39))
					{
						char test1 = m[i][j-1];
						char test2 = m[i+1][j];
						if (test1 != 'W')
						{
							floodfill[i][j-1] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i+1][j] = true;
						}
					}
					else if ((j == 0)&&(i==39))
					{
						char test1 = m[i-1][j];
						char test2 = m[i][j+1];
						if (test1 != 'W')
						{
							floodfill[i-1][j] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i][j+1] = true;
						}
					}
					else if ((j == 0) && ((i !=0) ||(i!= 39)))
					{
						char test1 = m[i+1][j];
						char test2 = m[i-1][j];
						char test3 = m[i][j+1];
						if (test1 != 'W')
						{
							floodfill[i+1][j] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i-1][j] = true;
						}
						if (test3 != 'W')
						{
							floodfill[i][j+1] = true;
						}
					}
					else if ((i == 39) &&(j!=0) &&(j!=39))
					{
						char test1 = m[i][j+1];
						char test2 = m[i-1][j];
						char test3 = m[i][j-1];
						if (test1 != 'W')
						{
							floodfill[i][j+1] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i-1][j] = true;
						}
						if (test3!='W')
						{
							floodfill[i][j-1] = true;
						}
					}
					else if ((i==39)&&(j==39))
					{
						char test1 = m[i-1][j];
						char test2 = m[i][j-1];
						if (test1 != 'W')
						{
							floodfill[i-1][j] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i][j-1] = true;
						}
					}
					else if ((j==39) && ((i!=0) ||(i!=39)))
					{
						char test1 = m[i+1][j];
						char test2 = m[i-1][j];
						char test3 = m[i][j-1];
						if (test1 != 'W')
						{
							floodfill[i+1][j] = true;
						}
						if (test2 != 'W')
						{
							floodfill[i-1][j] = true;
						}
						if (test3 !='W')
						{
							floodfill[i][j-1] = true;
						}
					}
					else
					{
						char test1 = m[i+1][j];
						char test2 = m[i-1][j];
						char test3 = m[i][j+1];
						char test4 = m[i][j-1];
						if (test1 != 'W')
						{
							floodfill[i+1][j] = true;
						}
						if (test2 !='W')
						{
							floodfill[i-1][j] = true;
						}
						if (test3 != 'W')
						{
							floodfill[i][j+1] = true;
						}
						if (test4 != 'W')
						{
							floodfill[i][j-1] = true;
						}
					}
					if (compare == 'E')//there should only be one exit
					{
						erow = i;
						ecol = j;
					}

				}
				else if (compare == 'W')
				{
					break;
				}
				j = j + 1;
				if (compare == 'E')//there should only be one exit
				{
					erow = i;
					ecol = j;
				}				
				if (j==39)
				{
					break;
				}
			}
			j = 0; 
			i = i + 1;
			if (i == 40)
			{
				break;
			}
		}
		if ((floodfill[1][1] == true)&&(floodfill[erow][ecol]==true))
		{
			returner = true;
		}
		else
		{
			returner = false;
		}
		return returner;
	}
}
