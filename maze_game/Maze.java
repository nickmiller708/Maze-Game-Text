package maze_game;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.*;
public class Maze {
	private Tile[][] myMaze;
	static final int col = 40, rows = 40;
	public Maze(String s, ArrayList<Enemy> e, ArrayList<Treasure> treasure, ArrayList<Weapon> weapon, ArrayList<Character> monsterlist, ArrayList<Character> weaponlist, ArrayList<Character> treasurelist, Player p)
	{
		//reads in from a file and creates the maze with tiles
		try {
			FileReader fin = new FileReader(s); //creates a filereader for the file, creates a print statement if it doesn't work
			Scanner myScan = new Scanner(fin); //scanner to read the file!
			myMaze = new Tile[rows][col];
			for (int i = 0; i < rows; i ++)
			{
				String toRead = myScan.nextLine();
				for (int j = 0; j < col; j ++)
				{
					char inserter = toRead.charAt(j);
					Tile adder = new Tile(inserter, i, j, weapon, treasure, e, monsterlist, weaponlist, treasurelist,  p);
					myMaze[i][j] = adder;
				}
			}
		}
		catch (FileNotFoundException j)
		{
			System.out.println("Map File not found!");
		}
		
	}
	public Tile grabTile(int row, int col)
	{	
		//returns the tile in the maze if it is available, else return null
		try
		{
		return myMaze[row][col];
		}	
		catch(Error e)
		{
			System.out.println("Cannot get tile");
			return null;
		}
	}
	public void removeTile(int i, int j) 
	{
		myMaze[i][j] = null;
	}
	public void addTile(int i, int j, Tile t)
	{
		myMaze[i][j] = t;
	}
	public void  printMaze() 
	{
		//Prints the full maze regardless if visible to the player or not. Helpful for testing.
		for (int i = 0; i <= rows-1; i ++)
		{
			System.out.println("");
			for (int j = 0; j <= col-1 ;j ++) 
			{
				Tile printer = grabTile(i,j);
				System.out.print(printer.getTile());
			}
		}
	}
	public void updateMaze()
	{
		//Method used in the actual game. Will only show part of the maze visible to player.
		Tile[][] floorPlan = new Tile[rows][col];
		for (int i = 0; i < rows; i++)
		{
			System.out.println("");
			for (int j = 0; j < col; j++)
			{
				Tile printer = grabTile(i,j);
				if (printer.getVisible() == true)
				{
					floorPlan[i][j] = printer;
					System.out.print(printer.getTile());
				}
				else
				{
					floorPlan[i][j] = printer;
					System.out.print(" ");
				}
			}
			
		}	
	}
	public void setVisibles(Player p)
	{
		//Used to set visible tiles up to print on the map.
		int row = p.getRow();
		int col = p.getColumn();
		if ((row == 0) && (col == 0))
			{
			grabTile(row,col+1).setVisible();
			grabTile(row+1,col).setVisible();
			}
		else if ((row == 0)&&(col != 0))
		{
			grabTile(row,col-1).setVisible();
			grabTile(row,col+1).setVisible();
			grabTile(row+1,col).setVisible();
		}
		else if ((col == getCol()-1) &&(row != 0))
		{
			grabTile(row+1,col).setVisible();
			grabTile(row-1,col).setVisible();
			grabTile(row,col-1).setVisible();
		}
		else if ((col == getCol()-1) && (row == 0))
		{
			grabTile(row+1,col).setVisible();
			grabTile(row,col+1).setVisible();
		}
		else if ((col == getCol() -1 ) && (row == getRow()-1))
		{
			grabTile(row+1,col).setVisible();
			grabTile(row,col-1).setVisible();
		}
		else if (col == getCol()-1)
		{
			grabTile(row+1,col).setVisible();
			grabTile(row,col-1).setVisible();
			grabTile(row+1,col).setVisible();
		}
		else if ((row == getRow() -1) && (col ==0))
		{
			grabTile(row-1,col).setVisible();
			grabTile(row,col+1).setVisible();
		}
		else if ((row == getRow() -1) && (col == getCol() -1))
		{
			grabTile(row-1,col).setVisible();
			grabTile(row,col-1).setVisible();
		}
		else if ((col == 0))
		{
			grabTile(row+1,col).setVisible();
			grabTile(row-1,col).setVisible();
			grabTile(row,col+1).setVisible();
		}
		else 
		{
			if ((grabTile(row+1, col).checkMonster() == false)&&(grabTile(row+1,col).checkTreasure() == false)&&(grabTile(row+1,col).checkWeapon() == false)&&(grabTile(row+1,col).checkExit() == false))
				{
				grabTile(row+1,col).setVisible();
				}
			if ((grabTile(row-1,col).checkMonster() == false) && (grabTile(row-1,col).checkTreasure() == false)&&(grabTile(row-1,col).checkWeapon() == false)&&(grabTile(row-1,col).checkExit()==false))
			{
				grabTile(row-1,col).setVisible();
			}
			if ((grabTile(row,col+1).checkMonster() == false) && (grabTile(row,col+1).checkTreasure() == false)&&(grabTile(row,col+1).checkWeapon()==false)&&(grabTile(row,col+1).checkExit()==false))
			{
				grabTile(row,col+1).setVisible();
			}
			if ((grabTile(row,col-1).checkMonster() == false) && (grabTile(row,col-1).checkTreasure() == false)&&(grabTile(row,col-1).checkWeapon()==false)&&(grabTile(row,col-1).checkExit()==false))
			{
				grabTile(row,col-1).setVisible();
			}
		}
	}
	public int getCol()
	{
		return col;
	}
	public int getRow()
	{
		return rows;
	}
	public boolean TreasureTile(int i, int j)
	{
		//will check to see if there is Treasure on a tile on a specific tile.
		return grabTile(i,j).checkTreasure();
	}
	public Treasure getTreasure(int i, int j)
	{
		//If a treasure is available in a Tile, found by TreasureTile, we can return that treasure using this function
		return grabTile(i,j).getTreasure();
	}
	public Enemy getEnemy(int i, int j)
	{
		return grabTile(i,j).getMonster();
	}
	public boolean MonsterTile(int i, int j)
	{
		//will check tile to see if there is a monster in that tile. If so, there will be a getMonster function to get the monster, then run the attack sequence in game.java
		return grabTile(i,j).checkMonster();
	}
	public boolean WeaponTile(int i, int j)
	{
		//will check tile to see if there is a weapon in that tile. If so, there will be a getWeapon function to get that weapon and equip it to the player
		return grabTile(i,j).checkWeapon();
	}
	public Weapon getWeapon(int i, int j)
	{
		return grabTile(i,j).getWeapon();
	}
	public boolean ExitTile(int i, int j)
	{
		return grabTile(i,j).checkExit();
	}
}
