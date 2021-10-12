package station;
import lombok.Getter;
import lombok.Setter;
import passenger.Passenger;

@Getter
@Setter
public class Station {

    private String name;
    
    private Passenger passenger;

    private Integer serviceTimeRemaining;

    private Integer timeBusy;

    private Float percentBusy;

    public Station(String name){
        this.name = name;
        this.passenger = null;
        this.timeBusy = 0;
        this.percentBusy = 0.0F;
    }

    public void doComputePercentBusy(Integer totalServiceTime) {
        this.percentBusy =  ((float)this.timeBusy / (float)totalServiceTime);
    }
    
}
