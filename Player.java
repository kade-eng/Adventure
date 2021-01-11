package adventure;

import java.util.ArrayList;

public class Player implements java.io.Serializable{
    private String name;
    private ArrayList<Item> inven = new ArrayList<Item>(); 
    private Room currentRoom = new Room();
    private String saveName;
    private static final long serialVersionUID = 5037028868893733370L;
    
    //default constructor
    public Player(){

    }

     /**
     * @param word the name 
     */
    public void setName(String word){
        name = word;
    }

    /**
     * @param inventory setting inventory from old list
     */
    public void setInventory(ArrayList<Item> inventory){
        inven = inventory;
    }

    /**
     * @param room current room
     */
    public void setCurrentRoom(Room room){
        currentRoom = room;
    }

    /**
     * @param word the name of save
     */
    public void setSaveGameName(String word){
        saveName = word;
    }

    /**
     * @return name
     */
    public String getName(){
       return name;
    }

     /**
     * @return return inventory
     */
    public ArrayList<Item> getInventory(){
        return inven;
    }

     /**
     * @return current room
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }

     /**
     * @return saved name
     */
    public String getSaveGameName(){
        return saveName;
    }

     /**
     * @return to string of player
     */
    public String toString(){
        String word = getName() + ": " + getSaveGameName();
        return word;
    }

}
