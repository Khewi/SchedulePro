package model;


/***
 * This class contains getter and setter functions for contacts.
 */
public class contact {

    private int contactID;
    private String contactName;
    private String email;

    public contact(){
        super();
    }

    /**
     * Constructor method for new contacts.
     * @param contactID
     * @param contactName
     * @param email
     */
    public contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }


    public contact(int contactID, String contactName){
        this.contactID = contactID;
        this.contactName = contactName;
    }

    /**
     * getter function for contactID.
     * @return contactID
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * setter function for contactID
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * getter function for contactName.
     * @return contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * setter function for contactName
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    /**
     * getter function for email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter function for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method overrides Oracles default return style of combo box selections for the contact class.
     * @return contactName
     */
    @Override
    public String toString(){
        return contactID + " " + contactName;
    }
}
