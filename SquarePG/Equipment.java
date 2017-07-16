package SquarePG;

import java.util.ArrayList;

public class Equipment {
	private ArrayList<Item> list = new ArrayList<Item>();
    public Item sword = new Item (20, 5, 0, "Sword", "An ordinary sword for attacking", 1000, 500);
    private Item shield = new Item (0, 0, 100, "Shield", "An ordinary shield that increased defense", 1000, 500);
    private Item wand = new Item (10, 20, 0, "Wand", "A wand used by novice wizards", 1000, 500);
    private Item bow = new Item (30, 10, 0, "Bow", "A bow used buy novice archers", 1000, 500);
     
    private Item robe = new Item (10, 40, 100, "Magician's Robes", "Linen robes made for magicians", 2000, 500);
    private Item leatherArmour = new Item (10, 10, 200, "Leather Armour", "Leather armour suitable for hunters and bowSquares", 2000, 500);
    private Item cuArmour = new Item (0, 10, 300, "Copper Armour", "Cheap set of armour made from copper", 2000, 500);
    private Item feArmour = new Item (0, 20, 400, "Iron Armour", "Mid-Tiered armour made for knights", 3000, 750);
    
    public static final int SWORD = 0;
    public static final int SHIELD = 1;
    public static final int WAND = 2;
    public static final int BOW = 3;
    public static final int ROBE = 4;
    public static final int LEATHER_ARMOUR = 5;
    public static final int COPPER_ARMOUR = 6;
    public static final int IRON_ARMOUR = 7;
    
    public Equipment(){
        list.add(sword);
        list.add(shield);
        list.add(wand);
        list.add(bow);
        list.add(robe);
        list.add(leatherArmour);
        list.add(cuArmour);
        list.add(feArmour);
    }
    
    //Returns the equipment object in question
    public Item getItem (int listNum){
        return list.get(listNum);
    }
    
    public ArrayList getList (){
        return list;
    }
	
}
