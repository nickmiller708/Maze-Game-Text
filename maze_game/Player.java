package maze_game;
import java.util.Scanner;
import java.io.*;
public class Player {
	private int MaxHealth, CurrentHealth, Attack, Defense, Damage, Gold, column, row;
	private Weapon BestWeapon;
	public Player(int row, int col)
	{
		//sets player location and then reads in player stats from a txt file
		column = col;
		this.row = row;
		try {
			FileReader fin = new FileReader("bin//maze_game//player.txt");
			Scanner myscan = new Scanner(fin);
			MaxHealth = myscan.nextInt();
			Attack = myscan.nextInt();
			Defense = myscan.nextInt();
			Damage = myscan.nextInt();
			Gold = 0;
			CurrentHealth = MaxHealth;
			myscan.close();
		}
		catch (Exception e)
		{
			System.out.println("Player File not found!");
		}
	}
	public void setCurrHealth(int n)
	{
		//Can set the Current Health of the Player. Used when increasing health via Health Potion
		CurrentHealth = CurrentHealth + n;
		if (CurrentHealth >= MaxHealth)
		{
			CurrentHealth = MaxHealth;
		}
	}
	public void setAttack(int n)
	{
		//Changes attack of the player based on weapon pick up. 
		//Only called when picking up a new weapon
		Attack = Attack + n;
	}
	public void setDamage(int n)
	{
		//Changes the damage of the player based on weapon pick up.
		Damage = Damage + n;
	}
	public void increaseGold(int n)
	{
		//Increases gold based on treasure pick ups
		Gold = Gold + n;
	}
	public int getGold()
	{
		return Gold;
	}
	public void setRow(int n)
	{
		row = n;
	}
	public void setColumn(int n)
	{
		column = n;
	}
	public void setWeapon(Weapon w)
	{
		//sees if the weapon picked up is better than the current weapon and adds it if it is
		if (BestWeapon != null) // when the player is holding a weapon
		{
		int comparer = BestWeapon.compareTo(w); //need to write compareTo function
		if (comparer == -1) //current weapon is weakekr than the new weapon 
			{
				BestWeapon = w;
				System.out.println("You picked up a new weapon!");
				System.out.println("Weapon name: " + w.getName());
				System.out.println("Damage Bonus: " + w.getDamageBonus());
				System.out.println("Attack Bonus: " + w.getAttackBonus());
			}
		else if (comparer == 1)
		{
			//Current weapon is stronger. 
			System.out.println("Your weapon is stronger. You drop the other one in the TRAAAASSSSHSHSH");

		}
		}
		else
		{
			BestWeapon = w;
			System.out.println("You picked up a new weapon!");
			System.out.println("Weapon name: " + w.getName());
			System.out.println("Damage Bonus: " + w.getDamageBonus());
			System.out.println("Attack Bonus: " + w.getAttackBonus());
		}
	}
	public int getCurrentHealth()
	{
		return CurrentHealth;
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
	public int getRow()
	{
		return row;
	}
	public int getAttackBonus()
	{
		int returner = 0;
		if (BestWeapon == null)
		{
			returner = 0;
		}
		else
		{
			returner = BestWeapon.getAttackBonus();
		}
		return returner;
	}
	public int getDamageBonus()
	{
		int returner = 0;
		if (BestWeapon == null)
		{
			returner = 0;
		}
		else
		{
			returner = BestWeapon.getDamageBonus();
		}
		return returner;
	}
	public int getColumn()
	{
		return column;
	}
	public static void main(String[] args)
	{
		Player p = new Player(1,1);
		System.out.println(p.getAttack());
	}
}
