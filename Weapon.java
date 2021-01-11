package adventure;
public class Weapon extends Item implements Tossable{

    //default constructor
    public Weapon(){

    }

    //paramaterized constructor
    public Weapon(String name, String desc, long id){
        setName(name);
        setDescription(desc);
        setID(id);
    }

    /**
     * @return String to say item is tossed
     */
    @Override
    public String toss(){
        return getName() + " has been tossed.\n";
    }

}
