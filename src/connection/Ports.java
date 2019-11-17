package connection;

public enum Ports {
    HEALTHCHECK_1(4000),
    HEALTHCHECK_2(4001),
    DOWNLOAD(4002),
    REMOVE(4003),
    UPLOAD(4004);
    
    private final int value;
    
    Ports(int portValue){
        value = portValue;
    }
    
    public int getValue(){
        return value;
    }
    
}
