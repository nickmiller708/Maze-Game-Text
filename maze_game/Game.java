package maze_game;
import java.util.Scanner;
import java.util.Random;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
public class Game {
	//private String[] mazes;
	private int currentLevel;
	protected ArrayList<Enemy> monsters;
	protected ArrayList<Treasure> treasures;
	protected ArrayList<Weapon> weapons;
	protected Player p;
	protected ArrayList<Character> potentialmonsters, potentialtreasures, potentialweapons;
	protected ArrayList<Maze> mazes; //An Array List that contains all the mazes created by the Maze Generator
	private MazeGenerator generator; //Will generate 3 mazes and write them to text files with the name "map01.txt", "map02.txt", "map03.txt
	public Game()
	{
		currentLevel = 1;
		try
		{
		FileReader fin = new FileReader("bin//maze_game//monsters.txt");
		FileReader fan = new FileReader("bin//maze_game//treasure.txt");
		p = new Player(1,1); //the player will always start at 1,1 in the map
		Scanner mymonster = new Scanner(fin);
		Scanner mytreasure = new Scanner(fan);
		monsters = new ArrayList<Enemy>();
		treasures = new ArrayList<Treasure>();
		weapons = new ArrayList<Weapon>();
		potentialmonsters = new ArrayList<Character>();
		potentialtreasures = new ArrayList<Character>();
		potentialweapons = new ArrayList<Character>();
		mazes = new ArrayList<Maze>();
		int i = 0;
		i = 0; //index for weapon
		int j = 0; //index for treasure
		String type = mytreasure.nextLine();
		while (mytreasure.hasNextLine()) 
			{
				String itemname = mytreasure.nextLine();
				String value = mytreasure.nextLine();
				Integer val = new Integer(value);
				int vadd = val.intValue();
				String healthrestore = mytreasure.nextLine();
				Integer hr = new Integer(healthrestore);
				int hradd = hr.intValue();
				String attackbonus = mytreasure.nextLine();
				Integer ab = new Integer(attackbonus);
				int abadd = ab.intValue();
				String damagebonus = mytreasure.nextLine();
				Integer db = new Integer(damagebonus);
				int dbadd = db.intValue();
				String dropprobability = mytreasure.nextLine();
				Integer dp = new Integer(dropprobability);
				int dpadd = dp.intValue();
				if (type.equals("Weapon"))
				{
					Weapon w = new Weapon(type,itemname,vadd,hradd,abadd,dbadd,dpadd);
					weapons.add(w);
					i = i+1;
					Character inserter = new Character(w.getName().charAt(0));
					potentialweapons.add(inserter);
				}
				else
				{
					Treasure t = new Treasure(type,itemname,vadd,hradd,abadd,dbadd,dpadd);
					treasures.add(t);
					j = j+1;
					Character inserter = new Character(t.getName().charAt(0));
					potentialtreasures.add(inserter);
				}
				if (mytreasure.hasNextLine())
				{
				type = mytreasure.nextLine();
				}
				else
				{
					break;
				}
			}		
		mytreasure.close();
		String name = mymonster.nextLine();
		while (mymonster.hasNextLine())
		{
				String health =  mymonster.nextLine();
				Integer h = new Integer(health);
				int hadd = h.intValue();
				String attack = mymonster.nextLine();
				Integer a = new Integer(attack);
				int aadd = a.intValue();
				String defense = mymonster.nextLine();
				Integer def = new Integer(defense);
				int defadd = def.intValue();
				String damage = mymonster.nextLine();
				Integer dam = new Integer(damage);
				int damadd = dam.intValue();
				String potion = mymonster.nextLine();
				Integer pot = new Integer(potion);
				int padd = pot.intValue();
				String gold = mymonster.nextLine();
				Integer gol = new Integer(gold);
				int gadd = gol.intValue();
				Enemy m = new Enemy(name, hadd, aadd, defadd, damadd, padd, gadd,treasures);
				monsters.add(m);
				i = i +1;
				Character inserter = new Character(m.getName().charAt(0));
				potentialmonsters.add(inserter);
				if (mymonster.hasNextLine())
				{
				name = mymonster.nextLine();
				}
				else
				{
					break;
				}
		}
		mymonster.close();
		generator= new MazeGenerator(potentialmonsters,potentialtreasures,potentialweapons);
		Maze currentMaze1 = new Maze("bin//maze_game//map01.txt", monsters, treasures, weapons, potentialmonsters, potentialweapons, potentialtreasures, p);
		Maze currentMaze2= new Maze("bin//maze_game//map02.txt", monsters, treasures, weapons, potentialmonsters, potentialweapons, potentialtreasures, p);
		Maze currentMaze3  = new Maze("bin//maze_game//map03.txt", monsters, treasures, weapons, potentialmonsters, potentialweapons, potentialtreasures, p);
		mazes.add(currentMaze1);
		mazes.add(currentMaze2);
		mazes.add(currentMaze3);
		}
		catch (FileNotFoundException e)
		{
			System.out.println(" Some File Not Found");
		}
	}
	
	public void playGame()
	{
		System.out.println("You wake up in a cold, damp, dark area.  You're lying on the ground in a pool of blood and vomit. \n It appears to be your own. Wow!  What a wild night last night was.  \n You remember so little, but your head pounds and you wish you were home in bed (or even in Dave's CSC 300 class - anywhere but here).  \n Oh well.  You stagger to your feet and bump up against a slimy wall.  Ewwwwww!  Well, time to get out of here. \n You notice your pockets are empty.  Even your trusty dagger is gone. This so sucks.  Well, you're not getting home by standing here...  Get moving!   ");
		//format that line much better! It looks weird in the terminal
		Scanner myscan = new Scanner(System.in);
		boolean playeralive = true;
		
		while ((currentLevel <= 3) && (playeralive == true))
		{
			boolean nextLevel = false;
			Maze currentMaze = mazes.get(currentLevel-1);
			Player p = currentMaze.grabTile(1, 1).getPlayer();// since player always starts at 1 1.
			currentMaze.setVisibles(p);
			currentMaze.updateMaze();
			while ((nextLevel != true)&&(playeralive==true))
			{
			System.out.println(" \nWhat direction do you want to move in?");
			String movechar = myscan.nextLine();
			while (Move(currentMaze, movechar, p) != true)
			{
				System.out.println("There's a wall or boundary there!");
				System.out.println("What direction do you want to move in?");
				movechar = myscan.nextLine();
			}
			currentMaze.setVisibles(p);
			currentMaze.updateMaze();
			if (currentMaze.TreasureTile(p.getRow(), p.getColumn()) == true)
			{
				Treasure tile = currentMaze.getTreasure(p.getRow(), p.getColumn()); //grabs the treasure in that tile and adds to the player2
				if (tile.checkPickedUp() == false)
				{
					System.out.println("\nYou found a treasure! Look at you!\n");
					System.out.println("Type of treasure: " + tile.getType());
					
					tile.increase(p);
					tile.setPickedUp();
					if (tile.getType().equals("Potion"))
					{
						System.out.println("Health increased by: " + tile.getValue());
						System.out.println("New current health: " + p.getCurrentHealth());
					}
					else if (tile.getType().equals("Money"))
					{
						System.out.println("New gold amount: " + p.getGold());
					}
				}
			}
			else if (currentMaze.WeaponTile(p.getRow(), p.getColumn()) == true)
			{
				System.out.println("\nYou found a weapon! Look at you!\n");
				Weapon w = currentMaze.getWeapon(p.getRow(), p.getColumn());

				p.setWeapon(w);
			}
			else if (currentMaze.MonsterTile(p.getRow(), p.getColumn()) == true)
			{
				System.out.println("\nYou've encountered a monster! Oh no. Face off: You vs. " + currentMaze.getEnemy(p.getRow(),p.getColumn()).getName());
				playeralive = AttackSequence(p,currentMaze.getEnemy(p.getRow(), p.getColumn()));
				currentMaze.updateMaze();//printing extra maze because attack sequence can be long!
			}
			else if (currentMaze.ExitTile(p.getRow(),p.getColumn()) == true)
			{
				currentLevel = currentLevel+1;
				p.setRow(1);
				p.setColumn(1);
				nextLevel = true;
				System.out.println("\nLevel " + (currentLevel-1) + " completed! Moving onto next level! \n");
			}
			
		}
		
		}
		if (playeralive == false)
		{
			System.out.println("\n YOU DIED. TRY AGAIN");
		}
		else
		{
			System.out.println("YOU WIN!");
			System.out.println("Final Gold: " + p.getGold());
			System.out.println("Final Health: " + p.getCurrentHealth());
		}
		System.out.println("Hope you enjoyed. Game Created by Nick Miller.");
		System.out.println("God Bless America. Go Kasich");
	}
	public boolean AttackSequence(Player p, Enemy e)
	{
		//Attack sequence between the player and a monster
		//Player attempts to strike first and then the monster. This will continue until the player or monster dies.
		//If the monster dies, they have a chance of dropping a potion or gold.
		int playerattack = p.getAttack() + p.getAttackBonus();
		int playerdamage = p.getDamage() + p.getDamageBonus();
		int playerdefense = p.getDefense();
		int playerhealth = p.getCurrentHealth();
		Random playerrandom = new Random();
		int monsterattack = e.getAttack();
		int monsterdamage = e.getDamage();
		int monsterdefense = e.getDefense();
		int monsterhealth = e.getHealth();
		Random monsterandom = new Random();
		String name = e.getName();
		boolean playeralive = true;
		boolean monsteralive = true;
		while ((playeralive == true) && (monsteralive == true))
		{
			int mattackthisround = monsterandom.nextInt(monsterattack); //Attack the monster can deal this round.
			int pdefensethisround = playerrandom.nextInt(playerdefense);
			if (mattackthisround > pdefensethisround)
			{
				int damageinflict = monsterandom.nextInt(monsterdamage);
				playerhealth = playerhealth - damageinflict;
				System.out.println(e.getName()+ " hit you! He dealt " + damageinflict + " damage");
				if (playerhealth <= 0)
				{
					playeralive = false;
					break;
				}
				System.out.println("Your Current Health: " + playerhealth + " You can do it!");
			}
			else if (mattackthisround <= pdefensethisround)
			{
				System.out.println("That " + e.getName() + " missed! You're still going strong!");
			}
			int pattackthisround = playerrandom.nextInt(playerattack); //Attack the player can deal this round
			int mdefensethisround = monsterandom.nextInt(monsterdefense);
			if ((pattackthisround > mdefensethisround) && (playeralive == true)) //If the player dies, they can't attack!
			{
				int dmageinflict = playerrandom.nextInt(playerdamage);
				monsterhealth = monsterhealth - dmageinflict;
				System.out.println("You damage " + name + " and deal " + dmageinflict + " damage!");
				if (monsterhealth > 0)
				{
					System.out.println("The " + e.getName() + " now has "+ monsterhealth + " health.");
				}
				else if  (monsterhealth <= 0)
				{
					System.out.println("The " + e.getName() + " died! You're a champ!");
					monsteralive = false;
					break;
				}
			}
			else if ((pattackthisround <= mdefensethisround)&&(playeralive == true)) //If the player dies they can't attack! And they'll miss if they are alive!
			{
				System.out.println("You missed! Better luck next time!");
			}
			System.out.println("");

		}
		if (monsteralive == false)
		{
			//have the ability to drop a treasure or gold for the monster
			Treasure potion = e.dropPotion();
			Treasure gold = e.dropGold();
			if (potion != null)
			{
				potion.increase(p);
				System.out.println("You won! The " + e.getName() + " dropped a " + potion.getName());
				System.out.println("Player health increased by: " + potion.getValue());
				System.out.println("Current Health: " + p.getCurrentHealth());
			}
			if (gold != null)
			{
				gold.increase(p);
				System.out.println("You won! The " + e.getName() + " dropped a " + gold.getName());
				System.out.println("Player gold increased by: " + gold.getValue());
				System.out.println("Current Gold: " + p.getGold());
			}
		}
		System.out.println("Current Health: " + p.getCurrentHealth());
		return playeralive;
	}
	public boolean Move(Maze m, String s, Player p) 
	{
		//allows player character to move north (n), south (s), east (e), or west (w)
		//returns a boolean whether or not the player is able to move into a square or not
		int playerrow = p.getRow();
		int playercol = p.getColumn();
		Tile currentTile = m.grabTile(playerrow, playercol); //useful for removing player from current tile.
		boolean returner; //assume can't move until you can!
		if (s.equals("n"))
		{
			Tile checker = m.grabTile(playerrow-1, playercol);
			if ((checker == null)||(checker.checkWall() == true))
			{
				p.setRow(playerrow);
				p.setColumn(playercol);
				returner = false;
			}
			else
			{
				p.setColumn(playercol);
				p.setRow(playerrow-1);
				currentTile.setPlayerOff();
				checker.setPlayerOn(p);
				m.removeTile(playerrow, playercol);
				m.removeTile(playerrow-1, playercol);
				m.addTile(playerrow, playercol, currentTile);
				m.addTile(playerrow-1, playercol, checker);
				returner = true;
			}
		}
		else if (s.equals("s"))
		{
			Tile checker = m.grabTile(playerrow+1, playercol);
			if ((checker == null) || (checker.checkWall() == true))
			{
				returner = false;
			}
			else
			{
				p.setColumn(playercol);
				p.setRow(playerrow+1);
				currentTile.setPlayerOff();
				checker.setPlayerOn(p);
				m.removeTile(playerrow, playercol);
				m.removeTile(playerrow+1, playercol);
				m.addTile(playerrow, playercol, currentTile);
				m.addTile(playerrow+1, playercol, checker);
				returner = true;
			}
		}
		else if (s.equals("e"))
		{
			Tile checker = m.grabTile(playerrow, playercol + 1);
			if ((checker == null)|| (checker.checkWall() == true))
			{
				returner = false;
			}
			else 
			{
				p.setColumn(playercol+1);
				p.setRow(playerrow);
				currentTile.setPlayerOff();
				checker.setPlayerOn(p);
				m.removeTile(playerrow, playercol);
				m.removeTile(playerrow, playercol+1);
				m.addTile(playerrow, playercol, currentTile);
				m.addTile(playerrow, playercol+1, checker);
				returner = true;

			}
		}
		else if (s.equals("w"))
		{
			Tile checker = m.grabTile(playerrow, playercol-1);
			if ((checker == null)|| (checker.checkWall() == true))
			{
				returner = false;
			}
			else
			{
				p.setColumn(playercol-1);
				p.setRow(playerrow);
				currentTile.setPlayerOff();
				checker.setPlayerOn(p);
				m.removeTile(playerrow, playercol);
				m.removeTile(playerrow, playercol-1);
				m.addTile(playerrow, playercol, currentTile);
				m.addTile(playerrow, playercol-1, checker);
				returner =  true;
			}
		}
		else
			returner = false;
		return returner;
	}
	public static void main(String[] args)
	{
		Game playable = new Game();
		playable.playGame();
	}
}
