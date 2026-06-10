import javax.swing.JLabel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SYLLABUS: LAB 4 – Multithreading
 *
 * SystemClockThread implements Runnable interface to run the clock
 * in a separate background thread. The GUI remains responsive while
 * the clock updates every second independently.
 */
public class SystemClockThread implements Runnable {

    private final JLabel clockLabel;
    private volatile boolean running = true;

    /**
     * @param clockLabel JLabel in GUI where date/time will be displayed
     */
    public SystemClockThread(JLabel clockLabel) {
        this.clockLabel = clockLabel;
    }

    /**
     * run() method executed by the background thread.
     * Updates clock label every 1 second until stopped.
     */
    @Override
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy  HH:mm:ss");

        while (running) {
            try {
                String currentTime = dateFormat.format(new Date());
                // Swing components must be updated on Event Dispatch Thread
                javax.swing.SwingUtilities.invokeLater(() ->
                        clockLabel.setText("  " + currentTime + "  "));

                Thread.sleep(1000); // pause thread for 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * Stops the clock thread gracefully.
     */
    public void stopClock() {
        running = false;
    }
}
