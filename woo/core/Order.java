package woo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order extends Transaction implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 51L;
    private Supplier _sup;

    /**
     *
     * @param transID
     * @param date
     * @param sup
     */
    public Order(int transID, int date, Supplier sup) {
        super(transID, date,true);
        _sup = sup;

    }

    public String toString(){
        return _transID + "|" + _sup.getId() + "|" + (int) Math.rint(_cost) + "|" + _date;
    }


}
