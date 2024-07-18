package woo.core;

import java.io.Serializable;

public class Book extends Product implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 21L;
    private String _title;
    private String _author;
    private String _isbn;


    /**
     *
     * @param id
     * @param sup
     * @param price
     * @param criticalValue
     * @param title
     * @param author
     * @param isbn
     */
    public Book(String id,Supplier sup, int price, int criticalValue, String title,String author,String isbn){
        super(id,sup,price,criticalValue);
        _title = title;
        _author = author;
        _isbn = isbn;
    }

    /**
     *
     * @param id
     * @param sup
     * @param price
     * @param criticalValue
     * @param title
     * @param author
     * @param isbn
     * @param currentValue
     */
    public Book(String id,Supplier sup, int price, int criticalValue, String title,String author,String isbn, int currentValue){
        super(id,sup,price,criticalValue);
        _title = title;
        _author = author;
        _isbn = isbn;
        _currentQuantity = currentValue;
    }

    public String toString(){
        return "BOOK|" + _id + "|" + _supplier.getId() + "|" + _price + "|" + _criticalValue + "|" + _currentQuantity
                + "|" + _title + "|" + _author + "|" + _isbn;
    }

    public String toString2(){
        return "BOOK|" + _id + "|" +_title + "|" + _author + "|" + _isbn+ "|" + _supplier.getId() + "|" + _price + "|" + _criticalValue + "|" + _currentQuantity;
    }

    public String getId(){
        return _id;
    }

    public String getSupId(){
        return _supplier.getId();
    }

}