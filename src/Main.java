import connection.HealthChecker;
import connection.Ports;
import connection.Server;

public class Main {
    public static void main(String[] args) {
        Server s = new Server();
        HealthChecker hc1 = new HealthChecker(Ports.HEALTHCHECK_1);
        HealthChecker hc2 = new HealthChecker(Ports.HEALTHCHECK_2);
        
        // Starting HealthCheckers Threads
        hc1.start();
        hc2.start();
        
        // Starting Server Thread
        s.start();
    }
}
