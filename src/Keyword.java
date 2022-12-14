public class Keyword {
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

    public int getWeightId() {
        return weightId;
    }

    public void setWeightId(int weightId) {
        this.weightId = weightId;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weightId=" + weightId +
                '}';
    }

    public Keyword(String name, int weightId) {
        this.name = name;
        this.weightId = weightId;
    }

    public Keyword(int id, String name, int weightId) {
        this.id = id;
        this.name = name;
        this.weightId = weightId;
    }

    private int id;
    private String name;
    private int weightId;
}
