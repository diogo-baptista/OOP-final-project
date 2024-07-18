package woo.core;

import woo.app.exception.WrongSupplierException;
import woo.app.suppliers.Message;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Supplier implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 5L;

    private String _id;
    private String _name;
    private String _address;
    private boolean _enable;
    private Map<String,Product> _supProds;
    private Map<Integer,Order> _supOrder;

    /**
     *
     * @param id
     * @param name
     * @param address
     */
    public Supplier(String id, String name, String address){
        _enable = true;
        _id = id;
        _name = name;
        _address = address;
        _supProds = new LinkedHashMap<>();
        _supOrder = new LinkedHashMap<>();
    }

    public boolean toggleActivation(){
        _enable = !_enable;
        return _enable;
    }

    public String getId(){
        return _id;
    }

    public String isActive(){
        return _enable?Message.yes():Message.no();
    }

    public String toString(){
        return _id + "|" + _name + "|" + _address + "|" + isActive();

    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o){
        if(o instanceof Supplier && _id.equals(((Supplier)o)._id)){
            return true;
        }
        return false;
    }

    public Map<String,Product> getSupProds(){
        return _supProds;
    }

    /**
     *
     * @param prodId
     * @return
     * @throws WrongSupplierException
     */
    public Product getProd(String prodId) throws WrongSupplierException {
        if(_supProds.containsKey(prodId.toLowerCase())){
            return _supProds.get(prodId.toLowerCase());
        }
        throw new WrongSupplierException(_id,prodId);

    }

    public Map<Integer,Order> getOrders(){
        return _supOrder;
    }

    /**
     *
     * @param or
     */
    public void addOrder(Order or){
        _supOrder.put(or.getID(),or);
    }

    public boolean getState(){
        return _enable;
    }

}
