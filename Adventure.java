package adventure;
import java.util.ArrayList;

public class Adventure implements java.io.Serializable{
    /* you will need to add some private member variables */
    private String curDesc;
    private ArrayList<Room> advRoomList = new ArrayList<Room>();
    private ArrayList<Item> advItemList = new ArrayList<Item>();
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private static final long serialVersionUID = -4819641487504728362L;

    /* ======== Required public methods ========== */
        /* note,  you don't have to USE all of these
        methods but you do have to provide them.
        We will be using them to test your code */

    //default constructor
    public Adventure(){

    }

    /**
     * 
     * @param room room to add to list of room
     */
    public void addListAllRooms(Room room){
        advRoomList.add(room);
    }

    
    /**
     * @return Returns list of all rooms
     * 
     */
    public ArrayList<Room> listAllRooms(){
        return advRoomList;
    }

    
    /**
     * 
     * @param item item to add to list
     */
    public void addListAllItems(Item item){
        advItemList.add(item);
    }
    
    /**
     * 
     * @return room list
     */
    public ArrayList<Item> listAllItems(){
        return advItemList;
    }

    
    /**
     * 
     * @param theDesc setting member varible to param
     */
    public void setCurrentRoomDescription(String theDesc){
        curDesc = theDesc;
    }

    
    /**
     * 
     * @return return current description
     */
    public String getCurrentRoomDescription(){
        return curDesc;
    }

    /**
     * @param theRoom current room
     * @return return current description
     */
    public String lookRoom(Room theRoom){
        String longDesc = theRoom.getLongDescription();
        return longDesc;
    }

    /**
     * @return return inventory
     */
    public ArrayList<Item> returnInv(){
        return inventory;
    }

    /**
     * @param inv
     * 
     */
    public void setInv(ArrayList<Item> inv){
        inventory = inv;
    }

    /**
     * @param itemN item name
     * @return return description of item
     */
    public String lookItem(String itemN) throws InvalidCommandException{
        int num = 0;
        for (int counter = 0; counter < advItemList.size(); counter++) { 
            if (itemN.equals(advItemList.get(counter).getName())){   
                return advItemList.get(counter).getLongDescription();
            } 
        }   
         //only gets here if it doesnt return anything
            throw new InvalidCommandException(); 
    }
}
