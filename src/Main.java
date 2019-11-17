
import connection.FileFetch;
import connection.FileRemove;
import connection.FileUpload;
import connection.HealthChecker;
import connection.Ports;

public class Main {

    public static void main(String[] args) {
        HealthChecker hc1 = new HealthChecker(Ports.HEALTHCHECK_1);
        HealthChecker hc2 = new HealthChecker(Ports.HEALTHCHECK_2);

        FileUpload fup1 = new FileUpload(Ports.UPLOAD_1);
        FileUpload fup2 = new FileUpload(Ports.UPLOAD_2);

        FileRemove fr1 = new FileRemove(Ports.REMOVE_1);
        FileRemove fr2 = new FileRemove(Ports.REMOVE_2);

        FileFetch ff1 = new FileFetch(Ports.FETCH_1);
        FileFetch ff2 = new FileFetch(Ports.FETCH_2);

        // Starting HealthCheckers Threads
        hc1.start();
        hc2.start();

        // Starting File Upload Threads
        fup1.start();
        fup2.start();

        // Starting File Remove Threads
        fr1.start();
        fr2.start();

        // Starting File Fetch Threads
        ff1.start();
        ff2.start();
    }
}
