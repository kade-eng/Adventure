package adventure;
public class SmallFood extends Food implements Tossable{

    //default constructor
    public SmallFood(){

    }

    //paramaterized constructor
    public SmallFood(String name, String desc, long id){
        setName(name);
        setDescription(desc);
        setID(id);
    }

    /**
     * @return String to tell suer item is tossed
     */
    @Override
    public String toss(){
        return getName() + " has been tossed.\n";
    }

}
