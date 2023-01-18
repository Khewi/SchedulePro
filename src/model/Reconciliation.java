package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Reconciliation extends Task{

    private int ID;
    private String customer;
    private Date dueBy;

    public Reconciliation(int ID, String name, String description, String customer, Date dueBy) {
        super(name, description);
        this.ID = ID;
        this.customer = customer;
        this.dueBy = dueBy;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getDueBy() {
        return dueBy;
    }

    public void setDueBy(Date dueBy) {
        this.dueBy = dueBy;
    }
}
