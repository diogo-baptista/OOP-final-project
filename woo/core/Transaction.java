package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Transaction implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 5L;
    protected int _transID;
    protected double _cost;
    protected int _date;
    protected boolean _isPaid;
    protected List<Item> _items;

    /**
     *
     * @param transID
     * @param date
     * @param isPaid
     */
    public Transaction(int transID, int date, boolean isPaid){
        _transID = transID;
        _date = date;
        _isPaid = isPaid;
        _items = new ArrayList<>();
    }

    public double getCost(){
        return _cost;
    }

    public boolean getPaid(){
        return _isPaid;
    }

    public abstract String toString();

    public List<Item> getItems(){
        return _items;
    }

    public int getID(){
        return _transID;
    }

    /**
     *
     * @param it
     */
    public void addItem(Item it){
        _items.add(it);
    }

    public void Pay(){
        _isPaid = true;
    }

    public void Calculate(){
        int total = 0;
        for(Item it : _items){
            total += it.getQuantity() * it.prodPrice();
        }
        _cost = total;
    }

}
