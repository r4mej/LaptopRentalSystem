public class Admin {
    private String username;
    private String password;
    
    //not gonna collect id, firstname, and lastname. 
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
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
