package db.entity;

/**
 * @author Anton Zhukov
 */
public class Specialty extends Entity {
    
    private String name;
    private long science_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScience_id() {
        return science_id;
    }

    public void setScience_id(long science_id) {
        this.science_id = science_id;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
