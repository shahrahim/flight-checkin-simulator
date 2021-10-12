package station;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import passenger.Passenger;
import passenger.PassengerQueue;
import util.RandomGenerator;

@Getter
@Setter
public class ServiceStation {

    protected List<Station> stations;

    private final Integer avgServiceRate;

    private Integer totalServiceTime;

    public ServiceStation(Integer avgServiceRate){
        this.stations = Arrays.asList(new Station("Station 1"), new Station("Station 2"), 
            new Station("Station 3"), new Station("Station 4"), new Station("Station 5"));
        this.avgServiceRate = avgServiceRate;
        this.totalServiceTime = 0;
    }

    public void doDisplayAllStatus() {
        stations.forEach(station -> {
            String passengerName = station.getPassenger() == null ? null : station.getPassenger().getName();

            System.out.println(station.getName() + " handling " + passengerName + 
                " time remaining: " + station.getServiceTimeRemaining() + 
                " Percent Busy: " + (int)(station.getPercentBusy() * 100) + "%");
        });
        System.out.println();
    }

    public void doDisplayStationStatus(Integer index) {
        Station station = this.stations.get(index);
        String passengerName = station.getPassenger() == null ? null : station.getPassenger().getName();

        System.out.println(station.getName() + " handling " + passengerName + 
            " time remaining: " + station.getServiceTimeRemaining() + 
            " Percent Busy: " + (int)(station.getPercentBusy() * 100) + "%");
        System.out.println("\n");
    }

    public void doHandleStation(PassengerQueue pQueue) {
        stations.forEach(station -> {
            if(station.getPassenger() == null || station.getServiceTimeRemaining().equals(1)) {
                Passenger p = pQueue.dequeue();

                if(p != null) {
                    station.setPassenger(p);
                    station.setServiceTimeRemaining(RandomGenerator.getSanitizedNumber(avgServiceRate));
                    this.doHandleStationState(station, 1);
                } else {
                    station.setServiceTimeRemaining(null);
                    station.setPassenger(null);
                    this.doHandleStationState(station, 0);
                }

            } else {
                station.setServiceTimeRemaining(station.getServiceTimeRemaining()-1);
                this.doHandleStationState(station, 1);
            }
        });
    }

    public void doHandleStationQueue(List<PassengerQueue> queues) {
        Integer index = 0;
        for(Station station: this.stations){
            if(station.getPassenger() == null || station.getServiceTimeRemaining().equals(1)) {
                Passenger p = queues.get(index).dequeue();

                if(p != null) {
                    station.setPassenger(p);
                    station.setServiceTimeRemaining(RandomGenerator.getSanitizedNumber(avgServiceRate));
                    this.doHandleStationState(station, 1);
                } else {
                    station.setServiceTimeRemaining(null);
                    station.setPassenger(null);
                    this.doHandleStationState(station, 0);
                }

            } else {
                station.setServiceTimeRemaining(station.getServiceTimeRemaining()-1);
                this.doHandleStationState(station, 1);
            }
            index+=1;
        }
    }

    public boolean isServiceStationEmpty() {
        for(Station station: this.getStations()) {
            if(station.getPassenger() != null || station.getServiceTimeRemaining() != null) {
                return false;
            }
        }
        return true;
    }

    private void doHandleStationState(Station station, Integer offset) {
        station.setTimeBusy(station.getTimeBusy() + offset);
        station.doComputePercentBusy(this.totalServiceTime);
    }
    
}
