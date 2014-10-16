package objects;

public class Attribute {

    private String name;
    private String value;

    public Attribute(String _name, String _value) {
        this.name = _name;
        this.value = _value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
