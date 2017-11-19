
/** Timer class for roughly calculating running time of programs
 *  Original @author rbk
 *  Ver 1.0: 2017/08/08
 *  Usage:  Timer timer = new Timer();
 *          timer.start();
 *          timer.end();
 *          System.out.println(timer);  // output statistics
 *  Corrected memory calculations to 1048576 instead of 1000000
 *
 *
 * Timer code used by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 **/

package cs6301.g50;
public class Timer {
    long startTime, endTime, elapsedTime, memAvailable, memUsed;

    public Timer() {
        startTime = System.currentTimeMillis();
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public Timer end() {
        endTime = System.currentTimeMillis();
        elapsedTime = endTime-startTime;
        memAvailable = Runtime.getRuntime().totalMemory();
        memUsed = memAvailable - Runtime.getRuntime().freeMemory();
        return this;
    }

    public String toString() {
        return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed) + " MB / " + (memAvailable) + " MB.";
    }

}


