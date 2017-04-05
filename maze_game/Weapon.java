package maze_game;
public class Weapon extends Treasure{
	private int AttackBonus,DamageBonus;
	public Weapon(String type, String name, int value, int health, int attack, int damage, int drop)
	{
		super(type,name,value,health,attack, damage,drop);
	}
	public int compareTo(Weapon w)
	{
		int returner = 0;
		if ((super.attackbonus()+super.damagebonus() > (w.getAttackBonus() + w.getDamageBonus())))
			//This weapon is greater than the weapon equipped by the player
			returner = 1;
		else if ((super.attackbonus()+super.attackbonus() == (w.getAttackBonus()+w.getDamageBonus())))
			//The weapon on the ground is the same as the weapon held by the player
			returner = 0;
		else if (super.attackbonus()+ super.damagebonus() < (w.getDamageBonus()+w.getAttackBonus()))
		{
			//The weapon on the ground is weaker than the weapon held by the player
			returner = -1;
		}
		return returner;
	}
	public int getAttackBonus()
	{
		return super.attackbonus();
	}
	public int getDamageBonus()
	{
		return super.damagebonus();
	}
}
