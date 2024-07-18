package woo.core;

import java.io.Serializable;

public class Item implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 500L;
    private int _qt;
    private Product _prod;

    /**
     *
     * @param qt
     * @param prod
     */
    public Item(int qt, Product prod ){
        _qt = qt;
        _prod = prod;
    }

    public String toString(){
        return _prod.getId() + "|" +_qt;
    }

    public int getQuantity(){
        return _qt;
    }

    public int prodPrice(){
        return _prod.getPrice();
    }

    public String getProdID(){
        return _prod.getId();
    }
}
