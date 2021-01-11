package adventure;

public class Item implements java.io.Serializable{
    /* you will need to add some private member variables */
    private String itemN = "WOW";
    private String itemDesc;
    private Room itemRoom = new Room();
    private long itemID;
    private static final long serialVersionUID = -3053712707751394262L;
    private Adventure adventure = new Adventure();

    /* required public methods */
        //default constructor
        public Item(){

        }

        //paramaterized constructor
        public Item(String name, String desc, long id){
            setName(name);
            setDescription(desc);
            setID(id);
        }

     /**
     * @param itemName set name of item
     */
    public void setName(String itemName){
        itemN = itemName;
    }

     /**
     * @return item name
     */
    public String getName(){
        return itemN;
    }

     /**
     * @param theDesc set desc
     */
    public void setDescription(String theDesc){
        itemDesc = theDesc;
    }

     /**
     * @return description
     */
    public String getLongDescription(){
        return itemDesc;
    }
    
     /**
     * @param id set id
     */
    public void setID(long id){
        itemID = id;
    }

     /**
     * @return return id
     */
    public long getID(){
        return itemID;
    }

     /**
     * @param room set room
     */
    public void setContainingRoom(Room room){
        itemRoom = room;
    }

     /**
     * @return return the room
     */
    public Room getContainingRoom(){
        //returns a reference to the room that contains the item
        return itemRoom;
    }

    /* you may wish to add some helper methods*/
}
