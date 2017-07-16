package characterEntities;

import java.util.ArrayList;

public class Consumables {
	private ArrayList<Item> list = new ArrayList<Item>();
    private Item potion = new Item (10, 0, "Potion", "Restores a little bit of health", 100, 50);
    private Item elixir = new Item (0, 10, "Elixir", "Restores a little bit of mana", 100, 50);
    private Item Mipotion = new Item (100, 0, "Mid Potion", "Restores a fair amount of health", 200, 100);
    private Item Mielixir = new Item (0, 100, "Mid Elixir", "Restores a fair amount of mana", 200, 100);
    private Item Restore = new Item (200, 200, "Mid Restore", "Restores health and mana", 200, 100);
    
    public static final int POTION = 0;
    public static final int ELIXIR = 1;
    public static final int MIPOTION = 2;
    public static final int MIELIXIER = 3;
    public static final int RESTORE = 4;
    
    public Consumables(){
        list.add(potion);
        list.add(elixir);
        list.add(Mipotion);
        list.add(Mielixir);
        list.add(Restore);
    }
    
    //returns the item to be looked up
    public Item getItem (int ListNum){
        return list.get(ListNum);
    } 
    
    public ArrayList getList (){
        return list;
    }
}
