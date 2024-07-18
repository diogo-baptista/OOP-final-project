package woo.core;

import woo.app.exception.UnavailableProductException;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Product implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 2L;

    protected int _price;
    protected int _criticalValue;
    protected int _currentQuantity;
    protected String _id;
    protected Supplier _supplier;
    protected Map<String,Client> _notClients;  // clientes com notificaçoes inativas

    /**
     *
     * @param id
     * @param sup
     * @param price
     * @param criticalValue
     */
    public Product(String id,Supplier sup, int price, int criticalValue){
        _price = price;
        _criticalValue = criticalValue;
        _currentQuantity = 0;
        _id = id;
        _supplier = sup;
        _notClients = new LinkedHashMap<>();

    }

    public abstract String toString();

    public String getId(){
        return _id;
    }

    public int getPrice(){
        return _price;
    }

    public String getSupId(){
        return _supplier.getId();
    }

    public int getQuantity(){
        return _currentQuantity;
    }

    public Map<String,Client> getNotClients(){
        return _notClients;
    }

    /**
     *
     * @param quantity
     */
    public void addQuantity(int quantity){
        _currentQuantity += quantity;
    }

    /**
     *
     * @param quantity
     * @throws UnavailableProductException
     */
    public void reduceQuantity(int quantity) throws UnavailableProductException {
        if(_currentQuantity < quantity){
            throw new UnavailableProductException(_id,quantity,_currentQuantity);
        }
        _currentQuantity -= quantity;
    }

    /**
     *
     * @param quantity
     */
    public void changePrice(int quantity){
        _price = quantity;
    }


}
