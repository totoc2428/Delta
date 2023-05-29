package src.util.account;

import java.security.Signature;
import java.time.LocalDate;

public class Identity {
    String name;
    String forename;
    LocalDate birsthDate;
    boolean birsthGender;
    String birsthPostalCode;
    String postalCode;
    Signature signature;
    public Identity(String name, String forename,LocalDate birsthDate, boolean birsthGender, Signature signature){
        this.name = name;
        this.forename = forename;
        this.birsthDate = birsthDate;
        this.birsthGender = birsthGender;
        this.signature = signature;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((forename == null) ? 0 : forename.hashCode());
        result = prime * result + ((birsthDate == null) ? 0 : birsthDate.hashCode());
        result = prime * result + (birsthGender ? 1231 : 1237);
        result = prime * result + ((birsthPostalCode == null) ? 0 : birsthPostalCode.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Identity other = (Identity) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (forename == null) {
            if (other.forename != null)
                return false;
        } else if (!forename.equals(other.forename))
            return false;
        if (birsthDate == null) {
            if (other.birsthDate != null)
                return false;
        } else if (!birsthDate.equals(other.birsthDate))
            return false;
        if (birsthGender != other.birsthGender)
            return false;
        if (birsthPostalCode == null) {
            if (other.birsthPostalCode != null)
                return false;
        } else if (!birsthPostalCode.equals(other.birsthPostalCode))
            return false;
        if (postalCode == null) {
            if (other.postalCode != null)
                return false;
        } else if (!postalCode.equals(other.postalCode))
            return false;
        return true;
    }
    
}
