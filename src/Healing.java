import java.util.ArrayList;
import java.util.Scanner;

/**
 * @Object: Healing()
 * @Function: This OOP class will help set up the healing objects that is needed from the item.txt
 * when parsing information.
 * This class supers back to the item class and then also stores health and stack amount
 * @author(s) Dakota Smith
 * @added 10/17/2022
 */
public class Healing extends Item implements itemInterface {
    private int healAmount;
    private int stackAmount;

    // accessing the game console class
    GameConsole game = new GameConsole();

    private ArrayList<Item> items;
    private ArrayList<Room> roomItems;
    private ArrayList<Healing> healingInventory;
    Scanner input;

    // will access the player's current health
    Item itemObjectHeal;


    /*---------------------------------------------Healing Constructors-----------------------------------------------*/
    /**
     * @Function: This is a no - arg constructor that will access the item parse method and add the data into
     * their object arraylist
     * @author(s): Shianne Lesure
     * @added: 10/29/2022
     */
    public Healing(){
        // an arraylist that will hold the item's data
        roomItems = new ArrayList();
        items = new ArrayList();

        // putting the items data into the item & room arraylist
        game.readItems(items, roomItems);

        healingInventory = new ArrayList<>();
    }

    /**
     * @param id
     * @param name
     * @param desc
     * @param text
     * @param heal
     * @param stack
     * @Function: constructor for pre existing data from the item text file
     * @author(s) Dakota Smith
     * 10/17/2022
     */
    public Healing(int id, String name, String desc, String text, int heal, int stack) {
        super(id, name, desc, text);
        this.healAmount = heal;
        this.stackAmount = stack;
    }


    /*---------------------------------Getters & Setters for Healing variables----------------------------------------*/
    public int getHealAmount() {
        return healAmount;
    }

    public void setHealAmount(int healAmount) {
        this.healAmount = healAmount;
    }

    public int getStackAmount() {
        return stackAmount;
    }

    public void setStackAmount(int stackAmount) {
        this.stackAmount = stackAmount;
    }


    /*--------------------------------Healing Methods for implementing the game---------------------------------------*/
    /**
     * @Method: use()
     * @Function: This method will allow the player use an item to heal their wounds and increase their health
     * @author(s): Shianne Lesure
     * @added: 10/29/2022
     */
    @Override
    public void use(Player player, String item) {
        String[] parts = item.split(" ");
        for(Item item1: items){
            itemObjectHeal = item1;
            if(parts[1].equalsIgnoreCase(super.getItemName())){ // if input contains the item name
                this.setStackAmount(getStackAmount() - 1); // subtract 1 from the healing item stack

                // add the healing points to the player's current health
                player.setCurrentHealth(this.getHealAmount() + player.getCurrentHealth());

                // print out to the player how much their current health has went up
                System.out.println("You health has jumped up by " + this.getHealAmount());
                break;
            }
        }
    }

    // SHIANNE LESURE 11/7/2022
    public void addingHealingItem(Player player){
        for(Item item : player.getInventory()){
            if(item.getItemName().equalsIgnoreCase("Bandage")){
                healingInventory.add((Healing) item);
            }
            if(item.getItemName().equalsIgnoreCase("Syringe")){
                healingInventory.add((Healing) item);
            }
            if(item.getItemName().equalsIgnoreCase("Med-Kit")){
                healingInventory.add((Healing) item);
            }
        }
    }

    // SHIANNE LESURE 11/7/2022
    public void useHealing(Player player, String item){
        addingHealingItem(player);
        String[] parts = item.split(" ");
            for(Healing healing : healingInventory){
                if(parts[1].equalsIgnoreCase(healing.getItemName())){
                    System.out.println("This " + healing.getItemName() + " has added " + healing.getHealAmount() + " points to your health bar.");
                    int updateHealth = player.getCurrentHealth() + healing.getHealAmount();
                    player.setCurrentHealth(updateHealth);
                    break;
                }
            }
    }
}
