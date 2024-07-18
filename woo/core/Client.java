package woo.core;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 3L;
    private String _id;
    private String _name;
    private String _address;
    private String _status;
    private int _points;
    private int _valuePM;    //compras efetuadas
    private int _valuePP;    //compras pagas
    private List<Notification> _nots;
    private Map<Integer,Sale> _clSales;


    /**
     *
     * @param id
     * @param name
     * @param address
     */
    public Client(String id, String name, String address){
        _id = id;
        _name = name;
        _address = address;
        _points = 0;
        _valuePM = 0;
        _valuePP = 0;
        _nots = new ArrayList<>();
        _clSales = new LinkedHashMap<>();
        _status = ClientStatus.NORMAL.name();
    }

    public String getId(){
        return _id;
    }

    public String toString(){
        return _id + "|" + _name + "|" + _address + "|" + _status + "|" + _valuePM + "|" + _valuePP;
    }

    public Map<Integer,Sale> getSales(){
        return _clSales;
    }

    public List<Notification> getNots(){
        return _nots;
    }

    public int getPoints(){
        return _points;
    }

    public String getStatus(){
        return _status;
    }

    public void removeNots(){
        Iterator<Notification> it = _nots.iterator();

        while(it.hasNext()){
            it.next();
            it.remove();
        }
    }

    /**
     *
     * @param qt
     */
    public void addPM(int qt){
        _valuePM += qt;
    }

    /**
     *
     * @param qt
     */
    public void addPP(int qt){
        _valuePP += qt;
    }

    /**
     *
     * @param points
     */
    public void addPoints(int points){
        _points += points;
    }

    /**
     *
     * @param points
     */
    public void setPoints(int points){
        _points = points;
    }

    public void updateStatus(){
        if(_points <= 2000){
            _status = ClientStatus.NORMAL.name();
        }
        else if(_points <= 25000 ){
            _status = ClientStatus.SELECTION.name();

        }
        else{
            _status = ClientStatus.ELITE.name();
        }
    }
}

