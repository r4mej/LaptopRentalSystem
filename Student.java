public class Student {
    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public Student(String username, String password, String id, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstname(){
        return firstName;
    }
    public void setFirstname(String firstName){
        this.firstName = firstName;
    }
    public String getLastname(){
        return lastName;
    }
    public void setLastname(String lastName){
        this.lastName = lastName;
    }
}
