package adventure;
import java.util.Scanner;
import java.io.FileReader;
import java.io.Reader;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Game {

    /* this is the class that runs the game.
    You may need some member variables */
    private Parser parser = new Parser();
    private Adventure adv = new Adventure();
    private static Room theRoom = new Room();
    private Player player = new Player();
    private Scanner scnr = new Scanner(System.in);
    private String fileN;

     /**
     * 
     * Used to get player name and welcome them to game
     */
    public void welcome(){
        System.out.println("Welcome to Adventure!");
        System.out.println("Enter your name:");
        String ans = scnr.next();
        player.setName(ans);
    }

     /**
     * @param theName the name of file to load
     * finds starting room from new json file
     */
    public void loadingNew(String theName) throws InvalidCommandException{
        fileN = theName;
        // 4. Print the beginning of the adventure
        JSONObject adventureJSON = new Game().loadAdventureJson(fileN);  
        adv = new Game().generateAdventure(adventureJSON); 
        ArrayList<Room> advRoomList = adv.listAllRooms();
        ArrayList<Item> advItemList = adv.listAllItems();
        //set up connected room 
        for (int counter = 0; counter < advRoomList.size(); counter++) { 		      
            advRoomList.get(counter).setListAllRooms(advRoomList);
            advRoomList.get(counter).setListAllItems(advItemList);
            }
        //find starting room
        for (int counter = 0; counter < advRoomList.size(); counter++) { 		      
            if (advRoomList.get(counter).getStart() != 0){
                theRoom = advRoomList.get(counter);
            }	
        }
        //check if rooms valid
            for (int counter = 0; counter < advRoomList.size(); counter++) { 	
                try {
                    advRoomList.get(counter).testExitDir();
                    advRoomList.get(counter).testRoomExist();
                    advRoomList.get(counter).testRoomExitsExist();
                    advRoomList.get(counter).testRoomItemsExist();
                } catch (InvalidCommandException e){
                    throw new InvalidCommandException();
                }
            }
    }

    
     /**
     * @param nameOfSave given name of save file
     * finds starting room from save file
     */
    public void loadingSaved(String nameOfSave){
        // 4. Print the beginning of the adventure
       theRoom = loadGame(nameOfSave);
       ArrayList<Room> advRoomList = adv.listAllRooms();
       ArrayList<Item> advItemList = adv.listAllItems();
    }

    /**
     * @return return player for use in GUI
     * finds starting room from save file
     */
    public Player returnPlayer(){
        return  player;
    }

    /**
     * @return return room for use in GUI
     * finds starting room from save file
     */
    public Room returnRoom(){
        return theRoom;
    }

    
     /**
     * 
     * prints starting room
     */
    public void printStart(){
        System.out.println("STARTING ROOM IS " + theRoom.getName());
        System.out.println(adv.getCurrentRoomDescription());
        System.out.print("items: ");
        if (theRoom.listLoot().size() == 0){
            System.out.print("NONE");
        }
        for (int i = 0; i < theRoom.listLoot().size(); i++) { 
            System.out.print(theRoom.listLoot().get(i).getName() + " "); 
        }
        System.out.println("\n");
    }

    public static void main(String[] args){
        String input = "wow";

        /* You will need to instantiate an object of type
        game as we're going to avoid using static methods
        for this assignment */
        
        Game theGame = new Game();
        // 1. Print a welcome message to the user

        if (args.length>=2){
            if (args[0].equals("-a")){//new adventure
                theGame.welcome();
                try {
                    theGame.loadingNew(args[1]);
                } catch (InvalidCommandException excep){
                    System.out.println("ERROR: invalid JSON");
                    return;
                }
            } else if (args[0].equals("-l")){
                System.out.println("Welcome to Adventure!");
                theGame.loadingSaved(args[1]);
            }
        theGame.printStart();
        } else {
            System.out.println("ERROR: Need to load or start a new adventure via command line.");
            return;
        }


        // 5. Begin game loop here
        // 6. Get the user input. You'll need a Scanner
        /* 7+. Use a game instance method to parse the user
        input to learn what the user wishes to do*/
        //use a game instance method to execute the users wishes*/
        while (!input.equals("quit")){
            input = theGame.scnr.nextLine();
            //MOVE STUFF INTO THE TRY/CATCH
            try {
            Command cmd = theGame.parse(input);
                theGame.followCommands(cmd);
            } catch (InvalidCommandException ex){
                if (!input.equals(""))
                    System.out.println("Unrecognized command\n");
            }
            if (input.equals("quit")){
                return;
            }
        }

        /* if the user doesn't wish to quit,
        repeat the steps above*/
        
    }

    /**
     * @param toDo given command
     * follows the command
     */
    public void followCommands(Command toDo){
        if (toDo.getActionWord().equals("go")){
            Room prev = new Room();
            prev = theRoom;
            try {
            theRoom = theRoom.getConnectedRoom(toDo.getNoun());
            } catch (InvalidCommandException ex){
                System.out.println("Nothing in that direction.\n");
            } 
            if (!prev.getName().equals(theRoom.getName())){
                System.out.println(theRoom.getName());
                System.out.println(theRoom.getCurrentRoomDescription());
                System.out.print("items: ");
                if (theRoom.listLoot().size() == 0){
                    System.out.print("NONE");
                }
                for (int counter = 0; counter < theRoom.listLoot().size(); counter++) { 
                    System.out.print(theRoom.listLoot().get(counter).getName() + ", "); 
                }
                System.out.println("\n");
            }
        } else if (toDo.getActionWord().equals("look")){
            if (!toDo.hasSecondWord()){
                System.out.println(adv.lookRoom(theRoom));
            }else{
                try {//valid??
                    System.out.println(adv.lookItem(toDo.getNoun()));
                } catch (InvalidCommandException ez){
                    System.out.println("No item by that name in this room\n");
                }
            }
        } else if (toDo.getActionWord().equals("take")){
            try {
                adv.setInv(theRoom.takeItem(toDo.getNoun(), adv.returnInv()));
                System.out.println(toDo.getNoun() + " added to inventory\n");
            } catch (InvalidCommandException ez){
                System.out.println("No item by that name in this room\n");
            }
        } else if (toDo.getActionWord().equals("inventory")){
            System.out.print("Inventory: ");
            for (int i=0; i<adv.returnInv().size();i++){
                System.out.print(adv.returnInv().get(i).getName());
                try {
                    Clothing clothing = (Clothing) adv.returnInv().get(i);
                    if (clothing.worn()){
                        System.out.print(" (equipped), ");
                    } else {
                        System.out.print(", ");  
                    }
                } catch (ClassCastException ex){
                    System.out.print(", ");
                }
            }
            System.out.println("\n");
        } else if (toDo.getActionWord().equals("commands")){
            System.out.println(parser.allCommands());
        } else if (toDo.getActionWord().equals("eat")){
            eatItem(toDo.getNoun());
        } else if (toDo.getActionWord().equals("toss")){
            tossItem(toDo.getNoun());
        } else if (toDo.getActionWord().equals("wear")){
            wearItem(toDo.getNoun());
        } else if (toDo.getActionWord().equals("read")){
            readItem(toDo.getNoun());
        } else if (toDo.getActionWord().equals("quit")){
            String ans;
            System.out.println("Would you like to save your game? (y/n)");
            ans = scnr.next();
            if (ans.equals("y")){
                player.setInventory(adv.returnInv());
                player.setCurrentRoom(theRoom);
                System.out.println("Enter a name for saved game:");
                ans = scnr.next();
                player.setSaveGameName(ans);
                saveGame(player.getSaveGameName());
            }
        } 
    }

     /**
      * USED FOR THE GUI
     * @param toDo given comman
     * @return returns string from followed command
     * follows the command
     */
    public String followCommandsGUI(Command toDo){
        if (toDo.getActionWord().equals("go")){
            Room prev = new Room();
            prev = theRoom;
            try {
                theRoom = theRoom.getConnectedRoom(toDo.getNoun());
            } catch (InvalidCommandException ex){
                return "Nothing in that direction.\n\n";
            } 
            if (!prev.getName().equals(theRoom.getName())){
                return "printInfo";
            }
        } else if (toDo.getActionWord().equals("look")){
            if (!toDo.hasSecondWord()){
                return adv.lookRoom(theRoom) + "\n\n";
            }else{
                try {//valid??
                    return adv.lookItem(toDo.getNoun())+ "\n\n";
                } catch (InvalidCommandException ez){
                    return "No item by that name in this room\n\n";
                }
            }
        } else if (toDo.getActionWord().equals("take")){
            try {
                adv.setInv(theRoom.takeItem(toDo.getNoun(), adv.returnInv()));
                return toDo.getNoun() + " added to inventory\n\n";
            } catch (InvalidCommandException ez){
                return "No item by that name in this room\n\n";
            }
        } else if (toDo.getActionWord().equals("inventory")){
            return "printInventory";
        } else if (toDo.getActionWord().equals("commands")){
            return parser.allCommands();
        } else if (toDo.getActionWord().equals("eat")){
            return eatItemGUI(toDo.getNoun()) + "\n";
        } else if (toDo.getActionWord().equals("toss")){
            return tossItemGUI(toDo.getNoun()) + "\n";
        } else if (toDo.getActionWord().equals("wear")){
            return wearItemGUI(toDo.getNoun()) + "\n";
        } else if (toDo.getActionWord().equals("read")){
            return readItemGUI(toDo.getNoun()) + "\n";
        } else if (toDo.getActionWord().equals("quit")){
            return "quit";
        } 
        return "none";
    }

    /**
     * @param itemN the name of item to be eaten
     * finds item, casts as something edible, and then eats and removes from inventory
     */
    public void eatItem(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                SmallFood smallF = (SmallFood) adv.returnInv().get(counter);
                System.out.println(smallF.eat());
                adv.returnInv().remove(smallF);
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Food food = (Food) adv.returnInv().get(counter);
                        System.out.println(food.eat());
                        adv.returnInv().remove(food);
                    } catch (ClassCastException e){
                        System.out.println("Cannot eat this item.");  
                    }
                }
            }
        }
    }

      /**
     * @param itemN the name of item to be eaten
     * @return returns string from eaten item
     * finds item, casts as something edible, and then eats and removes from inventory
     */
    public String eatItemGUI(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                SmallFood smallF = (SmallFood) adv.returnInv().get(counter);
                adv.returnInv().remove(smallF);
                return smallF.eat() + "\n";
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Food food = (Food) adv.returnInv().get(counter);
                        adv.returnInv().remove(food);
                        return food.eat() + "\n";
                    } catch (ClassCastException e){
                        return "Cannot eat this item.\n";  
                    }
                }
            }
        }
        return "No such item in inventory\n";
    }

     /**
     * @param itemN the name of item to be eaten
     * finds item, casts as something tossable, and then adds to room and removes from inventory 
     */
    public void tossItem(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                SmallFood smallF = (SmallFood) adv.returnInv().get(counter);
                System.out.println(smallF.toss());
                adv.returnInv().remove(smallF);
                theRoom.addLoot(smallF);
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Weapon wpn = (Weapon) adv.returnInv().get(counter);
                        System.out.println(wpn.toss());
                        adv.returnInv().remove(wpn);
                        theRoom.addLoot(wpn);
                    } catch (ClassCastException e){
                        System.out.println("Cannot toss this item.");  
                    }
                }
            }
        }
    }

         /**
     * @param itemN the name of item to be tossed
     * @return returns string from tossed item
     * finds item, casts as something tossable, and then adds to room and removes from inventory 
     */
    public String tossItemGUI(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                SmallFood smallF = (SmallFood) adv.returnInv().get(counter);
                adv.returnInv().remove(smallF);
                theRoom.addLoot(smallF);
                return smallF.toss() + "\n";
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Weapon wpn = (Weapon) adv.returnInv().get(counter);
                        adv.returnInv().remove(wpn);
                        theRoom.addLoot(wpn);
                        return wpn.toss() + "\n";
                    } catch (ClassCastException e){
                        return "Cannot toss this item.\n";  
                    }
                }
            }
        }
        return "No such item in inventory\n";
    }

     /**
     * @param itemN the name of item to be worn
     * finds item, casts as something wearable, and then equips
     */
    public void wearItem(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                BrandedClothing shirt = (BrandedClothing) adv.returnInv().get(counter);
                System.out.println(shirt.wear());
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Clothing clothing = (Clothing) adv.returnInv().get(counter);
                        System.out.println(clothing.wear());
                    } catch (ClassCastException e){
                        System.out.println("Cannot wear this item.");  
                    }
                }
            }
        }
    }

      /**
     * @param itemN the name of item to be worn
     * @return returns string from worn item
     * finds item, casts as something wearable, and then equips
     */
    public String wearItemGUI(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                BrandedClothing shirt = (BrandedClothing) adv.returnInv().get(counter);
                return shirt.wear() + "\n";
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Clothing clothing = (Clothing) adv.returnInv().get(counter);
                        return clothing.wear() + "\n";
                    } catch (ClassCastException e){
                        return "Cannot wear this item.\n";  
                    }
                }
            }
        }
        return "No such item in inventory\n";
    }

     /**
     * @param itemN the name of item to be read
     * finds item, casts as something readable, and then reads
     */
    public void readItem(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                BrandedClothing shirt = (BrandedClothing) adv.returnInv().get(counter);
                System.out.println(shirt.read());
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Spell spell = (Spell) adv.returnInv().get(counter);
                        System.out.println(spell.read());
                    } catch (ClassCastException e){
                        System.out.println("Cannot read this item.");  
                    }
                }
            }
        }
    }

    /**
     * @param itemN the name of item to be read
     * @return returns string from read item
     * finds item, casts as something readable, and then reads
     */
    public String readItemGUI(String itemN){
        inInventory(itemN);
        for (int counter = 0; counter < adv.returnInv().size(); counter++) { 
            if (itemN.equals(adv.returnInv().get(counter).getName())){ 
                int num = 0;
                try { 
                BrandedClothing shirt = (BrandedClothing) adv.returnInv().get(counter);
                return shirt.read() + "\n";
                } catch (ClassCastException e){
                    num = 1;
                }
                if (num == 1){
                    try { 
                        Spell spell = (Spell) adv.returnInv().get(counter);
                        return spell.read() + "\n";
                    } catch (ClassCastException e){
                        return "Cannot read this item.\n";  
                    }
                }
            }
        }
        return "No such item in inventory\n";
    }

    /**
     * @param itemN name of item
     * check if item is in inventory
     */
    private void inInventory(String itemN){
        int num = 0;
        for (int i = 0; i<adv.returnInv().size(); i++){
            if (!adv.returnInv().get(i).getName().equals(itemN)){
                num++;
            }
        }
        if (num == adv.returnInv().size()){
            System.out.println("No such item in inventory.\n");
        }
    }

    /**
     * @param theSaveFile given name of save file
     * saves the game
     */
    private void saveGame(String theSaveFile){
        try {
            FileOutputStream fileOut = new FileOutputStream(theSaveFile);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
 
            objOut.writeObject(adv);  
            objOut.writeObject(player); 
            
            objOut.close();
            fileOut.close();

            System.out.println("SAVED");
        } catch (IOException err){
            System.out.println("Couldn't write to file.");
        }
    }

    /**
     * GUI VERSION
     * @param theSaveFile given name of save file
     * saves the game
     */
    public void saveGameGUI(String theSaveFile){
        try {
            FileOutputStream fileOut = new FileOutputStream(theSaveFile);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
 
            objOut.writeObject(adv);  
            objOut.writeObject(player); 
            
            objOut.close();
            fileOut.close();

        } catch (IOException err){
            System.out.println("Couldn't write to file."); //throw exception
        }
    }

     /**
     * @param theSaveFile given name of save file
     * @return the room you left off in
     * loads save file
     */
    private Room loadGame(String theSaveFile){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(theSaveFile)); ) {

            adv = (Adventure)in.readObject();
            player = (Player)in.readObject();

            System.out.println("LOADED");
        } catch (IOException err){
            System.out.println("Couldn't read from file.");
        } catch (ClassNotFoundException err){
            System.out.println("Class not found.");
        }
        return(player.getCurrentRoom());
    }

    /**
     * @return parsed command
     * @param userIn user input
     */
    public Command parse(String userIn)throws InvalidCommandException{
        return parser.parseUserCommand(userIn);
    }

    /* you must have these instance methods and may need more*/
    /**
     * @param inputStream the input stream to load
     * @return aloaded adventure json
     */
    public JSONObject loadAdventureJson(InputStream inputStream){
        JSONObject adventureJSON = null;
        return adventureJSON;    
    }

    /**
     * @return return loaded adventureJSOn
     * @param filename name of file to load from
     */
    public JSONObject loadAdventureJson(String filename){
        JSONObject adventureJSON;
        JSONParser theparser = new JSONParser();
        try (Reader reader = new FileReader(filename)){
            adventureJSON = (JSONObject) theparser.parse(reader);
        } catch (Exception e){
            System.out.println("ERROR: File not found.");
            adventureJSON = null;
        }
    return adventureJSON;
    }

    
     /**
     * 
     * @return adventure (used for GUI)
     */
    public Adventure returnAdventure(){
        return adv;
    }

    /**
     * 
     * @return room (used for GUI)
     */
    public Room returnTheRoom(){
        return theRoom;
    }

      /**
     * used in GUI
     * @param adventure the adventure to set 
     */
    public void setAdventure(Adventure adventure){
        adv = adventure;
    }

    /**
     * used in GUI
     * @param room room to set
     */
    public void setRoom(Room room){
        theRoom = room;
    }
    
    /**
     * @param adventureJSON from load adventure
     * @return adventure generated from json
     */
    public Adventure generateAdventure(JSONObject adventureJSON) {
        JSONObject one = (JSONObject) adventureJSON.get("adventure");
        Adventure theAdv = new Adventure();

                   //ITEMS
                   if (one != null){
                    JSONArray itemList = (JSONArray) one.get("item");
        
                    //going thrue all items
                    for (Object currentitem : itemList){
                        JSONObject item = (JSONObject) currentitem;
        
                        //getting names and stuff for every room
                        String currentName = (String) item.get("name");
                        String currentDesc = (String) item.get("desc");
                        long currentID = (long) item.get("id");
                        
                        //checking what type it should be
                        boolean edib = false;
                        boolean toss = false;
                        boolean readb = false;
                        boolean wrbl = false;
                        if (item.containsKey("edible")) {
                            edib = (boolean) item.get("edible");                     
                        }
                        if (item.containsKey("tossable")) {
                            toss = (boolean) item.get("tossable");                   
                        }
                        if (item.containsKey("readable")) {
                            readb = (boolean) item.get("readable");                   
                        }
                        if (item.containsKey("wearable")) {
                            wrbl = (boolean) item.get("wearable");                    
                        } 

                        if (edib && toss){
                            SmallFood nextItem = new SmallFood(currentName, currentDesc, currentID);
                            theAdv.addListAllItems(nextItem);
                        } else if (edib){
                            Food nextItem = new Food(currentName, currentDesc, currentID);
                            theAdv.addListAllItems(nextItem);
                        } else if (wrbl && readb){
                            BrandedClothing nextItem = new BrandedClothing(currentName, currentDesc, currentID);
                            theAdv.addListAllItems(nextItem);
                        } else if (wrbl){
                            Clothing nextItem = new Clothing(currentName, currentDesc, currentID);
                            theAdv.addListAllItems(nextItem);
                        } else if (toss){
                            Weapon nextItem = new Weapon(currentName, currentDesc, currentID);
                            theAdv.addListAllItems(nextItem);
                        } else if (readb){
                            Spell nextItem = new Spell(currentName, currentDesc, currentID);
                            theAdv.addListAllItems(nextItem);
                        } else {
                            Item nextItem = new Item(currentName, currentDesc, currentID);
                            theAdv.addListAllItems(nextItem);
                        }
                    }
                } 

        //ROOMS
        if (one != null){
            JSONArray roomList = (JSONArray) one.get("room");
            ArrayList<Item> advItemList = theAdv.listAllItems();

            //going thrue all rooms
            for (Object currentRoom : roomList){
                JSONObject room = (JSONObject) currentRoom;

                //getting names and stuff for every room
                String currentName = (String) room.get("name");
                String start = (String) room.get("start");
                String currentDesc = (String) room.get("long_description");
                String shortDesc = (String) room.get("short_description");
                long currentID = (long) room.get("id");
                JSONArray exits = (JSONArray) room.get("entrance");  
                JSONArray theLoot = (JSONArray) room.get("loot");            

                //setting room stufff
                Room nextRoom = new Room();
                // is starting room?
                if (start != null && start.equals("true")){
                    nextRoom.setStart(currentID);
                    theAdv.setCurrentRoomDescription(shortDesc);
                } else {
                    nextRoom.setStart(0);
                }
                nextRoom.setName(currentName);
                nextRoom.setDescription(currentDesc);
                nextRoom.setCurrentRoomDescription(shortDesc);
                nextRoom.setID(currentID);
                theAdv.addListAllRooms(nextRoom);
                //reading entrance
                for (Object s: exits){
                    JSONObject s2 = (JSONObject) s;
                    String dir = (String) s2.get("dir");
                    long id = (long) s2.get("id");
                    nextRoom.setExitDir(dir);
                    nextRoom.setExitId(id);
                    Integer num = (Integer) Math.toIntExact(id);
                    nextRoom.addMap(dir, num);
                }
                //adding loot to room list
                if (theLoot != null) {
                for (Object s: theLoot){
                    JSONObject s2 = (JSONObject) s;
                    long id = (long) s2.get("id");
                    for (int counter = 0; counter < advItemList.size(); counter++) { 
                        if (id == advItemList.get(counter).getID()){
                            nextRoom.addLoot(advItemList.get(counter));
                        }
                    }
                }
            }               
            }
        }    
        return theAdv;
    }

}
