package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 * 
 * @author Richard McKenna
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E>  {
    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty name;
    private final StringProperty email;
    boolean isUndergrad;
    
    /**
     * Constructor initializes both the TA name and email.
     */
    public TeachingAssistant(String initName, String initEmail, boolean isUG ) {
        name = new SimpleStringProperty(initName);
        email = new SimpleStringProperty(initEmail);
        isUndergrad= isUG;
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES
    public String getName() {
        return name.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }
    
    public boolean isIsUndergrad() {
        return isUndergrad;
    }
    
    public void setIsUndergrad(Boolean ug){
        isUndergrad=ug;
    }
    
    

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant)otherTA).getName());
    }
    
    @Override
    public String toString() {
        return name.getValue();
    }
    
    
}