package woo.core;

import java.io.Serializable;

public class Notification implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 4L;
    private String _type;
    private Product _product;

    /**
     *
     * @param type
     * @param product
     */
    public Notification(String type, Product product){
        _type = type;
        _product = product;
    }

    public String toString(){
        return _type + "|" + _product.getId() + "|" + _product.getPrice();
    }
}
