package adventure;

/* TODO add a static data structure or another enum class
that lists all the valid commands.  Then add methods for validating
commands 

You may add other methods to this class if you wish*/

public class Command {
    private String action;
    private String noun;
    private final String[] commandList = {"go","look","take", "inventory","quit","commands","eat","wear","toss","read"};

  /**
     * Create a command object with default values.  
     * both instance variables are set to null
     * 
     */
    public Command() throws InvalidCommandException {
        this(null, null);
    }

  /**
     * Create a command object given only an action.  this.noun is set to null
     *
     * @param command The first word of the command. 
     * 
     */
    public Command(String command) throws InvalidCommandException{
        // a single-word action
        if (!isValid(command)
        || !command.equals("look") && !command.equals("inventory") 
        && !command.equals("quit") && !command.equals("commands")){
            throw new InvalidCommandException();
        }         
        this.action = command;
    }

    /**
     * Create a command object given both an action and a noun
     *
     * @param command The first word of the command. 
     * @param what      The second word of the command.
     */
    public Command(String command, String what) throws InvalidCommandException{
        //TODO validate the command here and ensure that the noun provided
        String[] dirs = {"N","S","E","W","up","down"};
        if (what == null || !isValid(command)){
            throw new InvalidCommandException();
        }
        // is a legitimate second word for the command
        if (command.equals("go")){
            int index = -1;
                for (int i=0;i<dirs.length;i++) {
                    if (dirs[i].equals(what)) {
                        index = i;
                        break;
                }
            } if (index == -1){
                throw new InvalidCommandException();
            }
        }
        this.action = command;
        this.noun = what;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     *
     * @return The command word.
     */
    public String getActionWord() {
        return this.action;
    }

    /**
     * @return The second word of this command. Returns null if there was no
     * second word.
     */
    public String getNoun() {
        return this.noun;
    }

    /**
     * @return true if the command has a second word.
     */
    public boolean hasSecondWord() {
        return (noun != null);
    }

     /**
      * @param command the command
     * @return true if the is valid
     */
    public boolean isValid(String command){
        //is it a valid command?
        int index = -1;
        for (int i=0; i<commandList.length; i++){
            if(commandList[i].equals(command)){
                index = 1;
                return true;
            }
        }
        return false;
    }
}
