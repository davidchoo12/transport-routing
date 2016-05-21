package models;
public class BusService {
    private String serviceNo;
    private int direction;

    public BusService(String serviceNo, int direction) {
        this.serviceNo = serviceNo;
        this.direction = direction;
    }

    public String getServiceNo() {
        return serviceNo;
    }

    public void setServiceNo(String serviceNo) {
        this.serviceNo = serviceNo;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        return this.serviceNo.hashCode() + this.direction * prime;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && ((BusService)obj).serviceNo.equals(this.serviceNo) && ((BusService)obj).direction == this.direction; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return this.serviceNo + ", " + this.direction; //To change body of generated methods, choose Tools | Templates.
    }
    
}
