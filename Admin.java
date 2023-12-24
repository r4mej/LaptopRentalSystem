public class Admin {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String id;

    //not gonna collect id, firstname, and lastname. 
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getID(){
        return id;
    }
    public void setID(String id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Optional method to authenticate the password
    public boolean authenticate(String enteredPassword) {
        String hashedEnteredPassword = PasswordUtils.hashPassword(enteredPassword);
        return hashedEnteredPassword.equals(this.password);
    }

}
