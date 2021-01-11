package adventure;
public class Food extends Item implements Edible{

    //default constructor
    public Food(){

    }

    //paramaterized constructor
    public Food(String name, String desc, long id){
        setName(name);
        setDescription(desc);
        setID(id);
    }

    
     /**
     * @return return string saying you have eaten the food
     */
    @Override
    public String eat(){
        return getName() + " has been eaten.\n";
    }

}
