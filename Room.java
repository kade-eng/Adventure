package adventure;
import java.util.ArrayList;
import java.util.HashMap;

public class Room implements java.io.Serializable{
    /* you will need to add some private member variables */
    private String roomName;
    private String roomDesc;
    private String curDesc;
    private long roomID;
    private long startID;
    private String exitDir;
    private long exitID;
    private ArrayList<Item> roomItems = new ArrayList<Item>();
    private HashMap<String, Integer> map = new HashMap<String, Integer>();
    private static final long serialVersionUID = -4972949515430556316L;
    private ArrayList<Room> advRoomList = new ArrayList<Room>(); // room list 
    private ArrayList<Item> advItemList = new ArrayList<Item>(); // item list 

        //default constructor
        public Room(){

        }
    
     /**
     * @param item add item to room loot
     */
    public void addLoot(Item item){
        roomItems.add(item);
    }

    /**
     * used to test for incorrect exit direction in JUnit
     */
    public void testExitDir() throws InvalidCommandException{
        for (String key : map.keySet()){
            if (!key.equals("N") && !key.equals("S") && !key.equals("E") 
            && !key.equals("W") && !key.equals("up") && !key.equals("down")){
                throw new InvalidCommandException();
            }
        } 
    }

    /**
     * used to test if the room exit even exists
     */
    public void testRoomExist() throws InvalidCommandException{
        int num = 0;
        for (int i=0; i<advRoomList.size(); i++){
            if (advRoomList.get(i).getID() != getExitId()){
               num++; 
            }
        }
        if (num == advRoomList.size()){
            throw new InvalidCommandException();
        }
    }

    /**
     * used to test for if no exits exist
     */
    public void testRoomExitsExist() throws InvalidCommandException{
        if (map.size() == 0){
            throw new InvalidCommandException();
        }
    }

    /**
     * used to test for if items in room exist
     */
    public void testRoomItemsExist() throws InvalidCommandException{
        for (int counter = 0; counter < roomItems.size(); counter++) { 
            if (!advItemList.contains(roomItems.get(counter))){
                throw new InvalidCommandException(); 
            }
        }   
    }

    /**
     * @param itemN item Name
     * @param inventory used to remove item from the list
     * @return item added confirmation
     */
    public ArrayList<Item> takeItem(String itemN, ArrayList<Item> inventory) throws InvalidCommandException{
        for (int counter = 0; counter < roomItems.size(); counter++) { 
            if (itemN.equals(roomItems.get(counter).getName())){
                inventory.add(roomItems.get(counter));
                roomItems.remove((roomItems.get(counter)));
                return inventory;
            } 
        }   
        throw new InvalidCommandException(); 
    }

    /**
     * @return list of room items
     */
    public ArrayList<Item> listLoot(){
        return roomItems;
    }

    /** 
     * @param theList the list used to set the member variable
     */
    public void setListAllRooms(ArrayList<Room> theList){
        advRoomList = theList;
    }

    
    /**
     * @return Returns list of all rooms
     * 
     */
    public ArrayList<Room> listAllRooms(){
        return advRoomList;
    }

    /** 
     * @param theList the list used to set the member variable for items
     */
    public void setListAllItems(ArrayList<Item> theList){
        advItemList = theList;
    }

    
    /**
     * @return Returns list of all items
     * 
     */
    public ArrayList<Item> listAllItems(){
        return advItemList;
    }

    /**
     * @param newName name for room
     */
    public void setName(String newName){
        roomName = newName;
    }

    /**
     * @return name of room
     */
    public String getName(){
        return roomName;
    }

    /**
     * @param newDesc desc for the room
     */
    public void setDescription(String newDesc){
        roomDesc = newDesc;
    }

    /**
     * @return long desc of room
     */
    public String getLongDescription(){
        return roomDesc;
    }

    /**
     * @param id for room
     */
    public void setID(long id){
        roomID = id;
    }

    /**
     * @return room id
     */
    public long getID(){
        return roomID;
    }

    /**
     * @param id for starting room
     */
    public void setStart(long id){
        startID = id;
    }

    /**
     * @return start id
     */
    public long getStart(){
        return startID;
    }

    /**
     * @param id for room exit
     */
    public void setExitId(long id){
        exitID = id;
    }
    /**
     * @return exit id
     */
    public long getExitId(){
        return exitID;
    }
    /**
     * @param dir direction for room exit
     */
    public void setExitDir(String dir){
        exitDir = dir;
    }
    /**
     * @return exit Dir
     */
    public String getExitDir(){
        return exitDir;
    }
    /**
     * @param theDesc desc for the room
     */
    public void setCurrentRoomDescription(String theDesc){
        curDesc = theDesc;
    }
    /**
     * @return current room desc
     */
    public String getCurrentRoomDescription(){
        return curDesc;
    }
    /**
     * @param exitD dir for exit
     * @param idforExit id for exit
     */
    public void addMap(String exitD,Integer idforExit){
        map.put(exitD, idforExit); 
    }
    /**
     * @return the hashmap
     */
    public HashMap getMap(){
        return map;
    }

    /**
     * @return room to string
     */
    public String toString(){
        String word = getName() + ": " + getCurrentRoomDescription();
        return word;
    }

    //connected room
    /**
     * @param direction direction of room
     * @return the room in direction
     */
    public Room getConnectedRoom(String direction) throws InvalidCommandException{
        Room theRoom = new Room();
        for (int i=0; i<=getMap().size();i++){
            if (getMap().containsKey(direction)){
                String idk = getMap().get(direction).toString();
                long idk2 = Long.parseLong(idk);
                setExitId(idk2);
                setExitDir(direction);
            }
        if (direction.equals(getExitDir())){ //match with exits?
            for (int counter = 0; counter < advRoomList.size(); counter++) {      
                if (getExitId() == advRoomList.get(counter).getID()){
                    theRoom = advRoomList.get(counter); // change room
                    return theRoom;
                }
            } 
        }
    }
        throw new InvalidCommandException();
    }
}
