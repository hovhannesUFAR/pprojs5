public class UserPersonalDetails {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserPersonalDetails(String name, String surname, int age, String gender, String country) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.country = country;
    }

    public UserPersonalDetails(String name, String surname, int age, String gender, String country, int userId) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.gender = gender;
        this.country = country;
        this.userId = userId;
    }

    private String name;
    private String surname;
    private int age;
    private String gender;
    private String country;
    private int userId;
}
