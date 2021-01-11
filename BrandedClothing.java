package adventure;
public class BrandedClothing extends Clothing implements Readable{

    //default constructor
    public BrandedClothing(){

    }

    //paramaterized constructor
    public BrandedClothing(String name, String desc, long id){
        setName(name);
        setDescription(desc);
        setID(id);
    }

    /**
     * @return return a string after reading object
     */
    @Override
    public String read(){
       return "Slogan on " + getName() + " reveals little information.";
    }

}
