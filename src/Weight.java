public class Weight {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public Weight(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Weight(int id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    private int id;
    private String name;
    private int value;
}
