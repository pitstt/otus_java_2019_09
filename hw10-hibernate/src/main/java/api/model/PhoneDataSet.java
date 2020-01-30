package api.model;

import javax.persistence.*;

@Entity
public class PhoneDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String number;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public PhoneDataSet(String number, User user) {
        this.number = number;
        this.user = user;
    }

    public PhoneDataSet(){}

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "number='" + number + '\'' +
                '}';
    }
}
