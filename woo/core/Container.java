package woo.core;


import java.io.Serializable;

public class Container extends Box implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 23L;
    private String _serviceLevel; //enum ServiceLevel

    /**
     *
     * @param id
     * @param sup
     * @param price
     * @param criticalValue
     * @param serviceType
     * @param serviceLevel
     */
    public Container(String id,Supplier sup, int price, int criticalValue,String serviceType, String serviceLevel){
        super(id,sup,price,criticalValue,serviceType);
        _serviceLevel = serviceLevel;
    }

    /**
     *
     * @param id
     * @param sup
     * @param price
     * @param criticalValue
     * @param serviceType
     * @param serviceLevel
     * @param currentValue
     */
    public Container(String id,Supplier sup, int price, int criticalValue, String serviceType, String serviceLevel, int currentValue){
        super(id,sup,price,criticalValue,serviceType);
        _serviceLevel = serviceLevel;
        _currentQuantity = currentValue;
    }

    public String toString(){
        return "CONTAINER|" + _id + "|" + _supplier.getId() + "|" + _price + "|" + _criticalValue + "|" + _currentQuantity
                + "|" + _serviceType + "|" +  _serviceLevel;
    }

    public String toString2(){
        return "CONTAINER|" + _id + "|" + _serviceType + "|" + _serviceLevel + "|" + _supplier.getId() + "|" + _price + "|" + _criticalValue
                + "|" + _currentQuantity;
    }

    public String getId(){
        return _id;
    }

    public String getSupId(){
        return _supplier.getId();
    }

}