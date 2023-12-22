public class Admin {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String id;

    public Admin(String username, String password, String firstName, String lastName, String id) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // For security reasons, it's better not to expose the password through a getter
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

    // Optional: Other methods specific to an Admin

}
