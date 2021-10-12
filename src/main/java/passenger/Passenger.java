package passenger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Passenger {

    protected Passenger next;

    private String name;

    private Integer waitTime;

    public Passenger(String name) {
        this.name = name;
        this.next = null;
        this.waitTime = 0;
    }

}