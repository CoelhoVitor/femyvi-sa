import connection.HealthChecker;
import connection.Server;

public class Main {
    public static void main(String[] args) {
        Server s = new Server();
        HealthChecker hc = new HealthChecker();
        
        // Starting HealthCheck Thread
        hc.start();
        
        // Starting Server Thread
        s.start();
    }
}
