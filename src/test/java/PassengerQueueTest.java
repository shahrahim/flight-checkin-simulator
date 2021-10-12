import org.junit.Test;

import passenger.Passenger;
import passenger.PassengerQueue;

public class PassengerQueueTest {

    @Test
    public void testDequeue() {
        Passenger p1 = new Passenger("Mike");
        Passenger p2 = new Passenger("Sike");
        Passenger p3 = new Passenger("Fike");

        PassengerQueue passengerQueue = new PassengerQueue("");

        passengerQueue.doEnqueue(p1);
        passengerQueue.doEnqueue(p2);
        passengerQueue.doEnqueue(p3);

        Passenger enP1 = passengerQueue.dequeue();

        assert enP1.getName().equals("Mike");
    }

    @Test
    public void testRearFront() {
        Passenger p1 = new Passenger("Mike");

        PassengerQueue passengerQueue = new PassengerQueue("");

        passengerQueue.doEnqueue(p1);

        assert passengerQueue.getFront().equals(passengerQueue.getRear());
    }


}
