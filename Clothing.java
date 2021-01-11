package adventure;
public class Clothing extends Item implements Wearable{
    private boolean isWorn = false;

    //default constructor
    public Clothing(){

    }

    //paramaterized constructor
    public Clothing(String name, String desc, long id){
        setName(name);
        setDescription(desc);
        setID(id);
    }

    /**
     * @return return string saying the clothing as been equpped
     */
    @Override
    public String wear(){
        isWorn = true;
        return getName() + " equipped.";
    }

    /**
     * @return boolean, represents if clothing item is worn or not
     */
    @Override
    public boolean worn(){
        return isWorn; 
    }

}
