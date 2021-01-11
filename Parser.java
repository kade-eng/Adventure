package adventure;


public class Parser {

    public Parser(){

    }

     /**
     * @param input the string to be parsed to command
     * @return return a command
     */
   public Command parseUserCommand(String input) throws InvalidCommandException{
        String[] singles= input.split(" ");
        String verb = "wowidk";
        String noun = "seven0";

        if (singles.length == 1){
            //one word
            verb = singles[0];
            Command oneCommand = new Command(verb);
            return oneCommand;
        } else {
            //two
            verb = singles[0];
            noun = singles[1];
            for (int i=2;i<singles.length;i++){
                noun = noun + " " + singles[i];
            }
            Command twoCommand = new Command(verb, noun);
            return twoCommand;
        }
    }

     /**
     * @return string of all commands
     */
    public String allCommands(){
        return "Commands: go, commands, take, inventory, quit, eat, wear, toss, read and look";
    }

}
