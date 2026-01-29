

public class UserAccounts {
    
    private final String userId;
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

    }

    public boolean passwordMatch(String hash){
            return passwordH.equals(hash);
            }
}

