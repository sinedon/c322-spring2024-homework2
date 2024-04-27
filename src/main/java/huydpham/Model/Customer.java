package huydpham.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(schema = "guitar")
public final class Customer {
    @Id
    private String username;
    private String password;
    private String email;

    public Customer() {
    }
    public Customer(String username,
                    String password,
                    String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String email() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Customer) obj;
        return Objects.equals(this.username, that.username) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }

    @Override
    public String toString() {
        return "Customer[" +
                "username=" + username + ", " +
                "password=" + password + ", " +
                "email=" + email + ']';
    }


}
