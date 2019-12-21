package db.entity;

public class Subjects extends Entity {
    private String name;
    private int department_name;
    private int type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(int department_name) {
        this.department_name = department_name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
