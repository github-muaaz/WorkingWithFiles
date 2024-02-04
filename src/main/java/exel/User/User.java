package exel.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private Long id;
    private String name;
    private String username;
    private String email;

    private Address address;
    private String phone;
    private String website;
    private Company company;

    public String getProperties(){
        return "1 - name\n2 - username\n3 - email" +
                "\n4 - address\n5 - phone\n6 - website\n7 - company";
    }

}
