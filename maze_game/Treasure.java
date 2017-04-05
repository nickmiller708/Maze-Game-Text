package maze_game;
import java.util.Scanner;
import java.io.FileReader;
public class Treasure {
	private String type, name;
	private int value, healthrestore, dropprobability, AttackBonus, DamageBonus;
	private boolean PickedUp;
	public Treasure (String type, String name, int value, int health, int attack, int damage, int drop)
	{
		this.type = type;
		this.name = name;
		this.value = value;
		healthrestore = health;
		AttackBonus = attack;
		DamageBonus = damage;
		dropprobability = drop;
		PickedUp = false;
	}
	public void increase(Player p)
	{
		if ((healthrestore != 0)&&(type.equals("Potion")))
		{
			p.setCurrHealth(healthrestore);
		}
		if ((value != 0)&&(type.equals("Money")))
		{
			p.increaseGold(value);
		}
	}
	public int getValue()
	{
		//returns value based on the type of the potion.
		int returner = 0;
		if (type.equals("Potion"))
		{
			returner = healthrestore;
		}
		else if (type.equals("Money"))
		{
			returner = value;
		}
		return returner;
	}
	public String getName()
	{
		return name;
	}
	public String getType()
	{
		return type;
	}
	public boolean checkPickedUp()
	{
		//returns whether or not the item is picked up. Useful for when player enters a new tile with an item
		return PickedUp;
	}
	public void setPickedUp()
	{
		PickedUp = true;
	}
	public int getDropProb()
	{
		return dropprobability;
	}
	public int attackbonus()
	{
		return AttackBonus;
	}
	public int damagebonus()
	{
		return DamageBonus;
	}
	public int value()
	{
		return value;
	}
	public int healthrestore()
	{
		return healthrestore;
	}
}
