package woo.core;

import java.io.Serializable;

public class Sale extends Transaction implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 52L;
    private int _paymentDate;
    private int _limitDate;
    private double _amountPaid;
    private Client _cl;

    /**
     *
     * @param transID
     * @param date
     * @param limitDate
     * @param cl
     */
    public Sale(int transID, int date,int limitDate, Client cl) {
        super(transID, date, false);
        _limitDate = limitDate;
        _amountPaid = 0;
        _cl = cl;
        _paymentDate = -1;
    }

    public String toString(){
        return _transID + "|" + _cl.getId() + "|" + getSaleProdID() + "|" + getQT() + "|" + (int) Math.rint(_cost) + "|" + (int) Math.rint(_amountPaid) +
                "|" + _limitDate + (_isPaid?("|" + _paymentDate):"");
    }

    public String getSaleProdID(){
        return getItems().get(0).getProdID();
    }

    public int getQT(){
        return getItems().get(0).getQuantity();
    }

    public String getClientID(){
        return _cl.getId();
    }

    public int getLimitDate(){
        return _limitDate;
    }

    public int getPaymentDate(){
        return _paymentDate;
    }

    public double getAmountPaid(){
        return _amountPaid;
    }

    public void setAmountPaid(double quantity){
        _amountPaid = quantity;
    }

    public void setPaymentDate(int paymentDate){
        _paymentDate = paymentDate;
    }


}
