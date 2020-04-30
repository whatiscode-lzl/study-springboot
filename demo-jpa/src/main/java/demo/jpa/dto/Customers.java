package demo.jpa.dto;

import javax.persistence.*;

/**
 * @Author liaozhenglong
 * @Date 2020/4/29 11:36
 **/
@Entity
public class Customers {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    protected Customers() {}
    public Customers(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
