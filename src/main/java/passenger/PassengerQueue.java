package passenger;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PassengerQueue {

    protected Passenger rear, front;

    private Integer maxLength;

    private Integer numPassengers;

    private Integer length;

    private List<Integer> waitTimes;

    private Integer avgWaitTime;

    private Integer maxWaitTime;

    private String name;

    public PassengerQueue(String name) {
        this.rear = null;
        this.front = null;
        this.maxLength = 0;
        this.numPassengers = 0;
        this.length = 0;
        this.waitTimes = new ArrayList<>();
        this.avgWaitTime = 0;
        this.maxWaitTime = 0;
        this.name = name;
    }

    public void doEnqueue(Passenger t) {
        if(this.rear == null) {
            this.front = t;
        } else {
            t.next = this.rear;
        }
        this.rear = t;
        this.length+=1;
        this.numPassengers+=1;
        if(length > maxLength) {
            this.maxLength = length;
        }
    }

    public Passenger dequeue() {
        Passenger temp = this.getFront();

        if(this.rear == null) {
            return null;
        } else if (this.rear == this.front) {
            this.rear = null;
            this.front = null;
        } else {
            Passenger ptr = this.rear;
            while(ptr.next != this.front) {
                ptr = ptr.next;
            }
            this.front = ptr;
            this.front.next = null;
        }
        if(length > 0) {
            length-=1;
        }
        doUpdateQueueStats(temp);
        return temp;
    }

    public void doDisplayQueue() {
        Passenger ptr = this.rear;
        if(rear == null) {
            System.out.print("-->");
        }
        while(ptr != null) {
            System.out.print("[" + ptr.getName() + " (Wait: " + ptr.getWaitTime() + ")] --> ");
            ptr = ptr.next;
        }
        System.out.println("\n");
    }

    public void doIncrementWaitTime() {
        Passenger ptr = this.rear;

        while(ptr != null) {
            ptr.setWaitTime(ptr.getWaitTime()+1);
            ptr = ptr.next;
        }
    }

    private void doUpdateQueueStats(Passenger p) {
        Integer finalWaitTime = p.getWaitTime() + 1;

        this.waitTimes.add(finalWaitTime);

        if(finalWaitTime > this.maxWaitTime) {
            this.setMaxWaitTime(finalWaitTime);
        }
        int sum = 0;

        for(Integer time: this.waitTimes) {
            sum+=time;
        }
        this.setAvgWaitTime(sum / this.waitTimes.size());  
    }

}
