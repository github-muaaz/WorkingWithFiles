package exel.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Geo implements Serializable {
    private String lat;
    private String lng;

    public String getProperties(){
        return "1 - lat\n2 - lng";
    }
}
