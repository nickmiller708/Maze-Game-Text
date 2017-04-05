package maze_game;
import java.util.ArrayList;
public class Tile {
	private boolean monster, treasure, weapon, exit, wall, player, visible;
	private char container;
	private Enemy e;
	private Treasure thold;
	private Weapon whold;
	private Player phold;
	
	public Tile(char s, int r, int j, ArrayList<Weapon> w, ArrayList<Treasure> t, ArrayList<Enemy> e, ArrayList<Character> monsterlist, ArrayList<Character> weaponlist, ArrayList<Character> treasurelist, Player p)
	{
		//when creating the maze, we can get the description of what goes in a tile from the map0x.txt
		///this will create tile with that attribute attached to it
		//for example, if there's a monster there, it will be a monster
		//If there is supposed to be an object somewhere, read from a text file that there should be an object there.
		//Will detect whether a tile needs a monster or treasure, and then pull from the monsters[] and treasures[] made by the Game class.
		Character inserter = new Character(s);
		if (s == 'W')
		{
			// if the spot contains a wall
			wall = true;
			monster = false;
			treasure = false;
			weapon = false;
			exit = false;
			player = false;
			visible = false;
			container = 'W';
		}
		else if (s == 'U')
		{
			wall = false;
			monster = false;
			treasure = false;
			weapon = false;
			exit = false;
			player = true;
			visible = true;
			container = 'U';
			phold  = p;
			w = null;
			t = null;
			e = null;
		}
		else if (treasurelist.contains(inserter) == true)
			//if the spot contains treasure
			//creates the treasure object to hold in that space
		{
			wall = false;
			monster = false;
			treasure = true;
			weapon = false;
			exit = false;
			player = false;
			visible = false;
			container = s;
			p = null;
			w = null;
			e = null;
			int index = treasurelist.indexOf(inserter);
			Treasure adders = t.get(index);
			String type = adders.getType();
			String name = adders.getName();
			int value = adders.value();
			int healthrestore = adders.healthrestore();
			int attackbonus = adders.attackbonus();
			int damagebonus = adders.damagebonus();
			int drop = adders.getDropProb();
			thold = new Treasure(type,name,value,healthrestore,attackbonus,damagebonus,drop);
			
		}
		else if (monsterlist.contains(inserter) == true)
		{
			//if the spot contains a monster
			wall = false;
			monster = true;
			treasure = false;
			weapon = false;
			exit = false;
			player = false;
			visible = false;
			container = s;
			p = null;
			w = null;
			t = null;
			int index = monsterlist.indexOf(inserter);
			this.e = e.get(index);
			//Create a monster by reading in from monsters.txt
		}
		else if (s == 'E')
		{
			wall = false;
			monster = false;
			treasure = false;
			weapon = false;
			exit = true;
			player = false;
			visible = false;
			container = 'E';
		}
		else if (weaponlist.contains(inserter) == true)
		{
			wall = false;
			monster = false;
			treasure = false;
			weapon = true;
			exit = false;
			player = false;
			visible = false;
			container =  s;
			int index = weaponlist.indexOf(inserter);
			Weapon adders = w.get(index);
			String type = adders.getType();
			String name = adders.getName();
			int value = adders.value();
			int healthrestore = adders.healthrestore();
			int attackbonus = adders.attackbonus();
			int damagebonus = adders.damagebonus();
			int drop = adders.getDropProb();
			whold = new Weapon(type,name,value,healthrestore,attackbonus,damagebonus,drop);
		}
		else
		{
			//There's nothing special there
			wall = false;
			monster = false;
			treasure = false;
			weapon = false;
			exit = false;
			visible = false;
			container = ' ';
		}
	}
	public boolean checkWall()
	{
		return wall;
	}
	public boolean checkMonster()
	{
		return monster;
	}
	public boolean checkTreasure()
	{
		return treasure;
	}
	public boolean checkWeapon()
	{
		return weapon;
	}
	public boolean checkExit()
	{
		return exit;
	}
	public Treasure getTreasure()
	{
		return thold;
	}
	public Enemy getMonster()
	{
		return e;
	}
	public Weapon getWeapon()
	{
		return whold;
	}
	public void setPlayerOff()
	{
		//sets player boolean to false when a player leaves a tile
		player = false;
		phold= null;
		container = ' ';
	}
	public void setPlayerOn(Player p)
	{
		//sets player boolean to true when a player gets onto a tile
		player = true;
		visible = true;
		phold = p;
		container = 'U';
	}
	public void setVisible()
	{
		visible = true;
	}
	public boolean getVisible()
	{
		return visible;
	}
	public Player getPlayer()
	{
		return phold;
	}
	public void setTreasure()
	{
		thold = null;
	}
	public char getTile()
	{
		return container;
	}
}
