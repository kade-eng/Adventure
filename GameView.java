package adventure;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.*;
import java.awt.*;

public class GameView extends JFrame{
    private Game myGame;
    private Adventure adv; 
    private Room currentRoom;

    public static final int WIDTH = 400;
    public static final int HEIGHT = 800;
    private TextArea gameText;
    private TextArea inv;

    public GameView(Game game){
        super();
        setSize(WIDTH, HEIGHT);
        setTitle("ADVENTURE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        myGame = game;
        //making menu 
        JPanel menu = new JPanel();
        TextField text1 = new TextField("MENU");
        text1.setEditable(false);

        //right title and text field
        JPanel enterInfo = new JPanel();
        enterInfo.setLayout(new BoxLayout(enterInfo, BoxLayout.PAGE_AXIS));
        TextField text2 = new TextField("Enter file below:");
        text2.setEditable(false);
        enterInfo.add(text2);

        TextField file = new TextField();
        enterInfo.add(file);

        //middle load buttons
        JPanel notEdit = new JPanel();
        notEdit.setLayout(new BoxLayout(notEdit, BoxLayout.PAGE_AXIS));;
        JButton load1 = new JButton("Load Save");
        notEdit.add(load1);

        JButton load2 = new JButton("Load New ");
        load2.addActionListener(ev->{
            gameText.append(String.format("Welcome to adventure!\n"));
            gameText.append(String.format("(Don't forget to set a character name!)\n"));
            try {
            myGame.loadingNew(file.getText());
            file.setText("");
            startGame();
            } catch (NullPointerException ex){
                popError();
            } catch (InvalidCommandException exc){
                jsonError();
            }
        });
        notEdit.add(load2);

        //character name and title
        JPanel nameStuff = new JPanel();
        nameStuff.setLayout(new BoxLayout(nameStuff, BoxLayout.PAGE_AXIS));
        TextField nameTitle = new TextField("Edit character name below:");
        nameTitle.setEditable(false);
        nameStuff.add(nameTitle);

        TextField name = new TextField();
        nameStuff.add(name);

        //SAVE BUTTON
        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(ev->{
            myGame.setAdventure(adv);
            myGame.setRoom(currentRoom);
            myGame.saveGameGUI(file.getText());
            gameText.append("SAVED\n");
        });

        
        //add to menu
        menu.add(text1);
        menu.add(notEdit);
        menu.add(enterInfo);
        menu.add(nameStuff);
        menu.add(saveButton);
        menu.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));


        //typing area
        JPanel thePanel = new JPanel();
        JPanel invPanel = new JPanel();
        invPanel.setLayout(new BoxLayout(invPanel, BoxLayout.PAGE_AXIS));
        TextField welcome = new TextField("INVENTORY");
        welcome.setEditable(false);
        invPanel.add(welcome);
        inv = new TextArea(15,20);
        inv.setEditable(false);
        invPanel.add(inv);
        invPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        gameText = new TextArea(17,55);
        gameText.setEditable(false);

        //editing name
        name.addActionListener(ev->{
            myGame.returnPlayer().setName(name.getText());
            welcome.setText(String.format("INVENTORY - " + myGame.returnPlayer().getName()));
            name.setText("");
        });

        load1.addActionListener(ev->{
            gameText.append(String.format("Welcome to adventure!\n"));
            myGame.loadingSaved(file.getText());
            file.setText("");
            if (myGame.returnPlayer().getName() == null){
                popError();
            } else {
            startGame();
            printInventoryMenu();
            welcome.setText(String.format("INVENTORY - " + myGame.returnPlayer().getName()));
            }
        });

        thePanel.add(invPanel);
        thePanel.add(gameText);

        //where you enter commands
        JPanel thePanel2 = new JPanel();

        TextField cmdTitle = new TextField("Command line");
        cmdTitle.setEditable(false);
        thePanel2.add(cmdTitle);

        TextField commands = new TextField();
        commands.setColumns(32);
        commands.addActionListener(ev->{
            hitEnter(commands.getText());
            commands.setText("");
        });
        thePanel2.add(commands);

        //adding to the pane
        contentPane.add(menu);
        contentPane.add(thePanel);
        contentPane.add(thePanel2);

        pack();
    }

    /**
     * load stuff into the game 
     */
    public void startGame(){
        adv = myGame.returnAdventure();
        currentRoom = myGame.returnTheRoom();
        gameText.append(String.format("You start your adventure in %s.\n %s\n",
            currentRoom.getName(), currentRoom.getCurrentRoomDescription()));
        printLoot();
    }

    /**
     * load stuff into the game 
     */
    public void popError(){
        gameText.setText("");
        inv.setText("");
        JOptionPane.showMessageDialog(new JFrame(), "ERROR: File not found. Please try again.");
    }

    /**
     * load stuff into the game 
     */
    public void jsonError(){
        gameText.setText("");
        inv.setText("");
        JOptionPane.showMessageDialog(new JFrame(), "ERROR: Errors detected in json file. Please try again.");
    }

    /**
     * @param input if they enter a command
     */
    public void hitEnter(String input){
        try {
            Command cmd = myGame.parse(input);
            whatToDO(myGame.followCommandsGUI(cmd));
            printInventoryMenu();
        } catch (InvalidCommandException ex){
            if (!input.equals(""))
                gameText.append(String.format("Unrecognized command\n"));
        }
    }

    /**
     * @param input if they enter a command
     */
    public void whatToDO(String input){
        if (input.equals("printInfo")){
            currentRoom = myGame.returnRoom();
            printRoom();
        } else if (input.equals("printInventory")){
            printInventory();
        } else if (input.equals("quit")){
            System.exit(0);
        } else {
            if (input.equalsIgnoreCase("wow")){
                gameText.append("No such item in inventory.\n\n");
            } else if (!input.equals("none")){
                gameText.append(String.format(input));
            }
        }
    }
    public static void main(String[] args){
        Game g = new Game();
        GameView thing = new GameView(g);
        thing.setVisible(true);
    }

    /**
     * printing the loot to screen
     */
    public void printLoot(){
        gameText.append(String.format("items: "));
        if (currentRoom.listLoot().size() == 0){
            gameText.append(String.format("NONE"));
        }
        for (int counter = 0; counter < currentRoom.listLoot().size(); counter++) { 
            gameText.append(String.format(currentRoom.listLoot().get(counter).getName() + ", ")); 
        }
        gameText.append(String.format("\n\n"));
    }

    /**
     * printing the loot to inventory screen
     */
    public void printInventoryMenu(){
        inv.setText("");;
        for (int i=0; i<adv.returnInv().size();i++){
            inv.append(String.format(adv.returnInv().get(i).getName()));
            try {
                Clothing clothing = (Clothing) adv.returnInv().get(i);
                if (clothing.worn()){
                    inv.append(String.format(" (equipped)\n"));
                } else {
                    inv.append(String.format("\n"));
                }
            } catch (ClassCastException ex){
                inv.append(String.format("\n"));
            }
        }
    }

    /**
     * printing the room info
     */
    public void printRoom(){
        gameText.append(String.format(currentRoom.getName() + "\n"));
        gameText.append(String.format(currentRoom.getCurrentRoomDescription() + "\n"));
        gameText.append(String.format("items: "));
        if (currentRoom.listLoot().size() == 0){
            gameText.append(String.format("NONE"));
        }
        for (int counter = 0; counter < currentRoom.listLoot().size(); counter++) { 
            gameText.append(String.format(currentRoom.listLoot().get(counter).getName() + ", "));
        }
        gameText.append(String.format("\n\n"));
    }

    /**
     * printing the loot to game screen
     */
    public void printInventory(){
        gameText.append(String.format("Inventory: "));
        for (int i=0; i<adv.returnInv().size();i++){
            gameText.append(String.format(adv.returnInv().get(i).getName()));
            try {
                Clothing clothing = (Clothing) adv.returnInv().get(i);
                if (clothing.worn()){
                    gameText.append(String.format(" (equipped),"));
                } else {
                    gameText.append(String.format(", "));
                }
            } catch (ClassCastException ex){
                gameText.append(String.format(", "));
            }
        }
        gameText.append(String.format("\n\n"));
    }
}
