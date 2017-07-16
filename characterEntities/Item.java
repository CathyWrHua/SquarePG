package characterEntities;

public class Item {
	private int gainHealth, gainMP;
	private int damage, mana, health;
	private String name;
	private String description;
	private int buyPrice, sellPrice;
	private int stock = 0;
	
	//Constructor for consumables
	public Item (int gainHealth, int gainMP, String name, String description, int buyPrice, int sellPrice){
		this.gainHealth = gainHealth;
		this.gainMP = gainMP;
		this.name = name;
		this.description = description;
		damage = mana = health = 0;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
	}
	
	//Constructor for weapons and armor
	public Item(int damage, int mana, int health, String name, String description, int buyPrice, int sellPrice){
		this.damage = damage;
		this.mana = mana;
		this.health = health;
		this.name = name;
		this.description = description;
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
		gainHealth = gainMP = 0;
	}
	
	public int getStock (){
		return stock;
	}
	
	public boolean changeStock(int change){
		if (stock + change >= 0){
			stock += change;
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	

    public int getBuyPrice (){
        return buyPrice;
    }
    
    public int getSellPrice(){
        return sellPrice;
    }
    
    public int getGainHealth(){
        return gainHealth;
    }
    
    public int getGainMP(){
        return gainMP;
    }
    
    public int getDamageIncrease (){
        return damage;
    }
    
    public int getHealthIncrease (){
        return health;
    }
    
    public int getManaIncrease () {
        return mana;
    }
	
}
