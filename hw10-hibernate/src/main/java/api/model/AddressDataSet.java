package api.model;

import javax.persistence.*;

@Entity
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String street;

    public AddressDataSet(){}

    public AddressDataSet(String street) {
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "street='" + street + '\'' +
                '}';
    }
}
