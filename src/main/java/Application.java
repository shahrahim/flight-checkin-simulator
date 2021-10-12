import java.util.Arrays;
import java.util.List;

import passenger.Passenger;
import passenger.PassengerQueue;
import station.DispatchPolicy;
import station.ServiceStation;
import util.RandomGenerator;
import util.SleepUtil;

public class Application {

    public static void main(String[] args) throws Exception {
        doValidateArgs(args);
        Integer duration = Integer.parseInt(args[0]);
        Integer avgArrival = Integer.parseInt(args[1]);

        option1(duration, avgArrival, avgArrival*5);
        option2(duration, avgArrival, avgArrival*5, DispatchPolicy.ROUND_ROBIN);
        option2(duration, avgArrival, avgArrival*5, DispatchPolicy.SHORTEST);
        option2(duration, avgArrival, avgArrival*5, DispatchPolicy.RANDOM);
    }

    static void doValidateArgs(String[] args) throws Exception {
        if(args.length != 2) {
            throw new Exception("Make sure to set the checkin duration arg and the average arrival rate arg: for example 'java -jar file.jar 20 2'");
        }

        for (int i = 0; i < args.length; i++) {
            try {
                Integer.parseInt(args[i]);
            } catch (NumberFormatException ex) {
                throw new Exception("Make sure to set the checkin duration arg and average arrival arg as integers!");               
            }
        }

        Integer avgArrivalRate = Integer.parseInt(args[1]);
        if(avgArrivalRate > 5 || avgArrivalRate < 1 ) {
            throw new Exception("Average arrival rate must be between 1-5 for an optimal simulation");               
        }
    }

    static void option1(Integer checkInDuration, Integer avgArrivalRate, Integer avgServiceRate) {
        System.out.println("OPTION 1 Simulation is about to begin\n\n==========================================================================================\n\n\n");
        SleepUtil.doSleep(7);

        ServiceStation serviceStation = new ServiceStation(avgServiceRate);
        PassengerQueue pQueue = new PassengerQueue("");

        do {
            serviceStation.setTotalServiceTime(serviceStation.getTotalServiceTime()+1);
            System.out.println("Total Duration: " + serviceStation.getTotalServiceTime() + "\n");

            if(serviceStation.getTotalServiceTime() < checkInDuration) {
                Integer numArrivals = RandomGenerator.getSanitizedNumber(avgArrivalRate);

                for (int j = 0; j < numArrivals; j++) {
                    pQueue.doEnqueue(new Passenger("Passenger-" + pQueue.getNumPassengers()));
                }
                System.out.println("Num Passengers Arrival: " + numArrivals);   
            }

            serviceStation.doHandleStation(pQueue);

            System.out.println("Max Queue Length: " + pQueue.getMaxLength());
            System.out.println("Current Queue Length: " + pQueue.getLength());
            System.out.println("Max wait time: " + pQueue.getMaxWaitTime());
            System.out.println("Avg wait time: " + pQueue.getAvgWaitTime());
            System.out.println();

            pQueue.doIncrementWaitTime();
            pQueue.doDisplayQueue();


            serviceStation.doDisplayAllStatus();
            System.out.println("\n\n");
            SleepUtil.doSleep();

        } while(pQueue.getLength() > 0 || !serviceStation.isServiceStationEmpty());
    }

    static void option2(Integer checkInDuration, Integer avgArrivalRate, Integer avgServiceRate, DispatchPolicy policy) {
        System.out.println("OPTION 2 " + policy + " Simulation is about to begin\n\n==========================================================================================\n\n\n");
        SleepUtil.doSleep(7);

        Integer totalPassengers = 0;

        List<PassengerQueue> queues = Arrays.asList(new PassengerQueue("1"), new PassengerQueue("2"), 
            new PassengerQueue("3"), new PassengerQueue("4"), new PassengerQueue("5"));
        
        ServiceStation serviceStation = new ServiceStation(avgServiceRate);

        Integer queueIndex = -1;
        do {
            serviceStation.setTotalServiceTime(serviceStation.getTotalServiceTime()+1);
            System.out.println("Total Duration: " + serviceStation.getTotalServiceTime() + "\n");
           
            if(serviceStation.getTotalServiceTime() < checkInDuration) {
                Integer numArrivals = RandomGenerator.getSanitizedNumber(avgArrivalRate);

                for (int j = 0; j < numArrivals; j++) {
                    totalPassengers+=1;
                    queueIndex = getDispatchDestinationIndex(policy, queueIndex, queues.size(), queues);
                    queues.get(queueIndex).doEnqueue(new Passenger("Passenger-" + totalPassengers));
                }
                System.out.println("Num Passengers Arrival: " + numArrivals);   
            }

            serviceStation.doHandleStationQueue(queues);
            
            for(int i = 0; i < queues.size(); i++) {
                PassengerQueue queue = queues.get(i);

                System.out.println("Queue: "  + queue.getName() + "\n");
                System.out.println("Max Queue Length: " + queue.getMaxLength());
                System.out.println("Current Queue Length: " + queue.getLength());
                System.out.println("Max wait time: " + queue.getMaxWaitTime());
                System.out.println("Avg wait time: " + queue.getAvgWaitTime());
                System.out.println();
    
                queue.doIncrementWaitTime();
                queue.doDisplayQueue();
                serviceStation.doDisplayStationStatus(i);    
            }
            System.out.println("\n\n");
            SleepUtil.doSleep();

        } while(!isQueuesListEmpty(queues) || !serviceStation.isServiceStationEmpty());      
    }

    static Integer getDispatchDestinationIndex(DispatchPolicy policy, Integer current, Integer max, List<PassengerQueue> queues) {
        Integer destinationIndex;

        if(policy.equals(DispatchPolicy.ROUND_ROBIN)) {
            destinationIndex = (current == (max-1) ? 0 : current+1);
        } else if(policy.equals(DispatchPolicy.RANDOM)) {
            destinationIndex = RandomGenerator.getRandomNumberWithinRange(0, max);
        } else {
            destinationIndex = getShortestQueueIndex(queues);
        }
        return destinationIndex;
    }

    static boolean isQueuesListEmpty(List<PassengerQueue> queues) {
        for(PassengerQueue queue: queues) {
            if(queue.getLength() > 0) {
                return false;
            }
        }
        return true;
    }

    static Integer getShortestQueueIndex(List<PassengerQueue> queues) {
        Integer shortestQueueLength = queues.get(0).getLength();
        Integer shortestQueueIndex = 0;

        for(int i = 1; i < queues.size(); i++) {
            PassengerQueue queue = queues.get(i);
            if(queue.getLength() < shortestQueueLength) {
                shortestQueueLength = queue.getLength();
                shortestQueueIndex = i;
            }
        }
        return shortestQueueIndex;
    }

}
