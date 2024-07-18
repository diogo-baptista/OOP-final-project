package woo.core;

import java.io.Serializable;

public class Box extends Product implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 22L;
    protected String _serviceType;

    /**
     *
     * @param id
     * @param sup
     * @param price
     * @param criticalValue
     * @param serviceType
     */
    public Box(String id,Supplier sup, int price, int criticalValue, String serviceType){
        super(id,sup,price,criticalValue);
        _serviceType = serviceType;
    }

    /**
     *
     * @param id
     * @param sup
     * @param price
     * @param criticalValue
     * @param serviceType
     * @param currentValue
     */
    public Box(String id,Supplier sup, int price, int criticalValue, String serviceType, int currentValue){
        super(id,sup,price,criticalValue);
        _serviceType = serviceType;
        _currentQuantity = currentValue;
    }

    public String toString(){
        return "BOX|" + _id + "|" + _supplier.getId() + "|" + _price + "|" + _criticalValue + "|" + _currentQuantity
                + "|" + _serviceType;
    }

    public String toString2(){
        return "BOX|" + _id + "|" + _serviceType + "|" + _supplier.getId() + "|" + _price + "|" + _criticalValue
                + "|" + _currentQuantity;
    }

    public String getId(){
        return _id;
    }

    public String getSupId(){
        return _supplier.getId();
    }

}
