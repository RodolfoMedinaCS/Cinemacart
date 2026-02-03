//Holds the users information, user ID, username, and email

public class UserAccount {
    
    private final String userId; //unique numerical ID number for every account
    private final String username;
    private final String email;
    private final String passwordH;

    public UserAccount(String userId, String username, String email, String passwordH){

        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordH = passwordH;

    }

    public String getUserId(){
        return userId;
    }


    public String getUsername(){
        return username;

    }

    public String getEmail(){
        return email;
    }

    //Checks if password hash matches stored hash
    public boolean passwordMatch(String hash){
            return passwordH.equals(hash);
            }
}