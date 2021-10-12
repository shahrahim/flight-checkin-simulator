package util;

import java.util.concurrent.TimeUnit;

public class SleepUtil {

    public static void doSleep() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch(Exception e) {}
    }

    public static void doSleep(Integer interval) {
        try {
            TimeUnit.SECONDS.sleep(interval);
        } catch(Exception e) {}
    }
    
}
