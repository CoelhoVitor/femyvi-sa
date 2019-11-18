package connection;

public enum Ports {
    HEALTHCHECK_1(4000),
    HEALTHCHECK_2(4001),
    FETCH_1(4002),
    FETCH_2(4003),
    REMOVE_1(4004),
    REMOVE_2(4005),
    UPLOAD_1(4006),
    UPLOAD_2(4007);

    private final int value;

    Ports(int portValue) {
        value = portValue;
    }

    public int getValue() {
        return value;
    }

}
