public class Restaurant {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstablishmentYear() {
        return establishmentYear;
    }

    public void setEstablishmentYear(int establishmentYear) {
        this.establishmentYear = establishmentYear;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", establishmentYear=" + establishmentYear +
                ", score=" + score +
                '}';
    }

    public Restaurant(String name, String address, String description, int establishmentYear, int score) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.establishmentYear = establishmentYear;
        this.score = score;
    }

    public Restaurant(int id, String name, String address, String description, int establishmentYear, int score) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.establishmentYear = establishmentYear;
        this.score = score;
    }

    private int id;
    private String name;
    private String address;
    private String description;
    private int establishmentYear;
    private int score;
}
