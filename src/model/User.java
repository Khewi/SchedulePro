package model;


/***
 * This class contains getter and setter functions for users.
 */
public class User {
    private int userID;
    private String username;
    private String password;


    /**
     * Constructor method for new users.
     */
    public User(){
        super();
    }

    public User(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }


    public User(int id, String username){
        this.userID = id;
        this.username = username;
    }

    /**
     * getter function for userID.
     * @return user ID
     */
    public int getUserID() {
        return userID;
    }

    /**
     * setter function for userID.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * getter function for username.
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter function for username.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter function for password.
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter function for password.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * This method overrides Oracles default return style of combo box selections for the user class.
     * @return username
     */
    @Override
    public String toString(){
        return userID + " " + username;
    }
}
