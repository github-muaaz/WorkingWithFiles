package exel.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Address implements Serializable {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;

    public String getProperties(){
        return "1 - street\n2 - city\n3 - zipcode\n4 - Geo";
    }
}
