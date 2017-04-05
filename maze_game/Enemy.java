package maze_game;
import java.util.Random;
import java.util.ArrayList;
public class Enemy {
	private String Name;
	private ArrayList<Treasure> items;
	private int Health, Attack, Defense, Damage, PotionProb, GoldProb;
	public Enemy(String s, int h, int a, int d1, int d2, int pp, int gp, ArrayList<Treasure> t)
	{
		Name = s;
		Health = h;
		Attack = a;
		Defense = d1;
		Damage = d2;
		PotionProb = pp;
		GoldProb = gp;
		items = t;
	}
	public int getAttack()
	{
		return Attack;
	}
	public int getDefense()
	{
		return Defense;
	}
	public int getDamage()
	{
		return Damage;
	}
	public int getHealth()
	{
		return Health;
	}
	public String getName()
	{
		return Name;
	}
	public Treasure dropGold()
	{
		//when a monster dies, there is a chance that they will drop gold!
		//If they don't drop a treasure, return null.
		Random generator = new Random();
		int prob = generator.nextInt(99);
		Treasure t = null;
		if (prob <= GoldProb)
		{
			int i = 0;
			Treasure adder = items.get(i);
			while (adder.getType().equals("Money") == false)
			{
				i = i + 1;
				adder = items.get(i);
			}
			t = adder;
		}
		return t;
	}
	public Treasure dropPotion()
	{
		//when a monster dies, there is a chance that they will drop a potion!
		//If they don't drop a potion, return null!
		Random generator = new Random();
		int prob = generator.nextInt(99);
		Treasure t = null;
		if (prob <= PotionProb)
		{
			int i = 0;
			Treasure adder = items.get(i);
			while (adder.getType().equals("Potion") == false)
			{
				i = i + 1;
				adder = items.get(i);
			}
			t = adder;
		}
		return t;
	}
	public String toString()
	{
		String returner = "";
		returner = Name + Health + Attack + Defense + Damage + PotionProb + GoldProb;
		return returner;
	}
}
