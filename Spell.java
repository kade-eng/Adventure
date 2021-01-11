package adventure;
public class Spell extends Item implements Readable{

    //default constructor
    public Spell(){

    }

    //paramaterized constructor
    public Spell(String name, String desc, long id){
        setName(name);
        setDescription(desc);
        setID(id);
    }

    /**
     * @return string to tell user item has been read
     */
    @Override
    public String read(){
        return "Knowledge gained from reading " + getName() + ".";
    }

}
