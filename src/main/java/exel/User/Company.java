package exel.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Company implements Serializable {
    private String name;
    private String catchPhrase;
    private String bs;

    public String getProperties(){
        return "1 - name\n2 - catchPhrase\n3 - bs";
    }
}
