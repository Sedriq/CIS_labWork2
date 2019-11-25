package db.entity;

/**
 * @author Anton Zhukov
 */
public class ScienceType extends Entity {
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
