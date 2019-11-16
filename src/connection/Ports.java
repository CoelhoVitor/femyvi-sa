package connection;

public enum Ports {
    HEALTHCHECK(2000), DOWNLOAD(5000), REMOVE(5001);
    
    private final int value;
    
    Ports(int portValue){
        value = portValue;
    }
    
    public int getValue(){
        return value;
    }
    
}
