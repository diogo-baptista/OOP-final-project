package woo.core;

import java.io.Serializable;
import java.io.IOException;

import woo.app.exception.*;
import woo.core.exception.BadEntryException;

import pt.tecnico.po.ui.Input;
import woo.core.exception.ImportFileException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Class Store implements a store.
 */

public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;
  private int _date;
  private int _totalTransactions;
  private double _totalOrders;
  private double _totalPaidSales;
  private double _totalAllSales;
  private double _contBalance;
  private double _availableBalance;
  private Map<String,Supplier> _suppliers;
  private Map<String,Product> _allProducts;
  private Map<String,Client> _clients;
  private Map<Integer,Transaction> _transactions;

  public Store(){
    _totalOrders = 0;
    _totalPaidSales = 0;
    _totalAllSales = 0;
    _date = 0;
    _totalTransactions = 0;
    _suppliers = new LinkedHashMap<>();
    _allProducts = new LinkedHashMap<>();
    _clients = new LinkedHashMap<>();
    _transactions = new LinkedHashMap<>();
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException, DuplicateSupplierKeyException,
          DuplicateClientKeyException, DuplicateProductKeyException, UnknownProductKeyException,
          UnknownSupplierKeyException, ImportFileException {

    try{
      MyParser parser = new MyParser(this);
      parser.parseFile(txtfile);
    } catch (IOException | BadEntryException e){
      throw new ImportFileException(e);
    }


  }

  public int getDate(){
    return _date;
  }

  public double getContBalance(){
    return _contBalance;
  }

  public double getAvailableBalance(){
    return _availableBalance;
  }

  /**
   *
   * @param date
   * @throws InvalidDateException
   */
  public void addDate(Input<Integer> date) throws InvalidDateException {
    if(date.value() <= 0){
      throw new InvalidDateException(date.value());
    }
    _date += date.value();
  }

  public int getTransactionID(){
    return _totalTransactions;
  }

  public void addTransactionID(){
    _totalTransactions++;
  }

  public Map<String,Supplier> getSuppliers(){
    return Collections.unmodifiableMap(_suppliers);
  }

  public Map<String,Product> getProducts(){
    return Collections.unmodifiableMap(_allProducts);
  }

  public Map<String,Client> getClients(){
    return Collections.unmodifiableMap(_clients);
  }

  public Map<Integer,Transaction> getTransactions(){
    return _transactions;
  }

  /**
   *
   * @param clId
   * @return
   * @throws UnknownClientKeyException
   */
  public Client getClient(String clId) throws UnknownClientKeyException {
    if(_clients.containsKey(clId.toLowerCase())){
      return _clients.get(clId.toLowerCase());
    }
    throw new UnknownClientKeyException(clId);
  }

  /**
   *
   * @param supId
   * @return
   * @throws UnknownSupplierKeyException
   */
  public Supplier getSupplier(String supId) throws UnknownSupplierKeyException {
    if(_suppliers.containsKey(supId.toLowerCase())){
      return _suppliers.get(supId.toLowerCase());
    }
    throw new UnknownSupplierKeyException(supId);
  }

  /**
   *
   * @param transId
   * @return
   * @throws UnknownTransactionKeyException
   */
  public Transaction getTransaction(int transId) throws UnknownTransactionKeyException {
    if(_transactions.containsKey(transId)){
      return _transactions.get(transId);
    }
    throw new UnknownTransactionKeyException(transId);
  }

  /**
   *
   * @param prodId
   * @return
   * @throws UnknownProductKeyException
   */
  public Product getProduct(String prodId) throws UnknownProductKeyException {
    if(_allProducts.containsKey(prodId.toLowerCase())){
      return _allProducts.get(prodId.toLowerCase());
    }
    throw new UnknownProductKeyException(prodId);
  }

  /**
   *
   * @param supId
   * @throws DuplicateSupplierKeyException
   */
  public void checkSupplier(String supId) throws DuplicateSupplierKeyException {
    if(_suppliers.containsKey(supId.toLowerCase())){
      throw new DuplicateSupplierKeyException(supId);
    }
  }

  /**
   *
   * @param supId
   * @throws UnknownSupplierKeyException
   */
  public void checkProdSupplier(String supId) throws UnknownSupplierKeyException {
    if(_suppliers.containsKey(supId.toLowerCase())){
      return;
    }
    throw new UnknownSupplierKeyException(supId);
  }

  /**
   *
   * @param clId
   * @throws DuplicateClientKeyException
   */
  public void checkClient(String clId) throws DuplicateClientKeyException {
    if(_clients.containsKey(clId.toLowerCase())){
      throw new DuplicateClientKeyException(clId);
    }
  }

  /**
   *
   * @param prodId
   * @throws DuplicateProductKeyException
   */
  public void checkDupProduct(String prodId) throws DuplicateProductKeyException {
    if(_allProducts.containsKey(prodId.toLowerCase())){
      throw new DuplicateProductKeyException(prodId);
    }

  }

  /**
   *
   * @param sup
   * @throws DuplicateSupplierKeyException
   */
  public void addSupplier(Supplier sup) throws DuplicateSupplierKeyException {
    checkSupplier(sup.getId());
    _suppliers.put(sup.getId().toLowerCase(),sup);
  }

  /**
   *
   * @param p
   * @throws DuplicateProductKeyException
   * @throws UnknownSupplierKeyException
   */
  public void addProduct(Product p) throws DuplicateProductKeyException, UnknownSupplierKeyException {
    checkDupProduct(p.getId());
    checkProdSupplier(p.getSupId());
    getSupplier(p.getSupId()).getSupProds().put(p.getId().toLowerCase(),p);
    _allProducts.put(p.getId().toLowerCase(),p);
  }

  /**
   *
   * @param c
   * @throws DuplicateClientKeyException
   */
  public void addClient(Client c) throws DuplicateClientKeyException {
    checkClient(c.getId());
    _clients.put(c.getId().toLowerCase(),c);
  }

  /**
   *
   * @param not
   * @param prod
   */
  public void putNotification(Notification not, Product prod){

    _clients.forEach((key, value) -> {
      if(!prod.getNotClients().containsKey(key)){
        value.getNots().add(not);
      }

    } );
  }

  /**
   *
   * @param sale
   * @param needPoints
   * @throws UnknownProductKeyException
   * @throws UnknownClientKeyException
   */
  public void UpdateSalePrice(Sale sale,boolean needPoints) throws UnknownProductKeyException, UnknownClientKeyException {
    boolean isPaid = sale.getPaid();
    if(isPaid){
      return;
    }
    int N = 0;
    double price = 0;
    int points;
    int limitDate = sale.getLimitDate();
    int actualDate = getDate();
    String prodID = sale.getItems().get(0).getProdID();
    Product prod = getProduct(prodID);
    String prodClass = prod.getClass().getSimpleName();
    Client cl =getClient(sale.getClientID());
    String ClientStatus = cl.getStatus();

    switch (prodClass){
      case "Box":
        N = 5;
        break;
      case "Container":
        N = 8;
        break;
      case "Book":
        N = 3;
        break;
      default:
        break;
    }

    // P1
    if(limitDate - actualDate >= N){
      price = sale.getCost() * 0.9;
      sale.setAmountPaid(price);

      if(needPoints){
        points = (int) Math.rint(price * 10);
        cl.addPoints(points);
      }
    }

    // P2
    if(0 <= (limitDate - actualDate) && (limitDate - actualDate) < N){
      switch (ClientStatus){
        case "NORMAL":
          price = sale.getCost();
          break;
        case "SELECTION":
          if(limitDate - actualDate >= 2){
            price = sale.getCost() * 0.95;
          }
          else{
            price = sale.getCost();
          }
          break;
        case "ELITE":
          price = sale.getCost() * 0.9;
          break;
        default:
          break;
      }

      sale.setAmountPaid(price);
      if(needPoints){
        points = (int) Math.rint(price * 10);
        cl.addPoints(points);
      }
    }

    // P3
    if(0 < (actualDate - limitDate) && (actualDate - limitDate) <= N){
      switch (cl.getStatus()){
        case "NORMAL":
          price = sale.getCost() * (((actualDate - limitDate) * 0.05) + 1);
          break;
        case "SELECTION":
          if(actualDate - limitDate > 1){
            if(needPoints && (actualDate - limitDate) > 2){
              points = (int) Math.rint(cl.getPoints() * 0.1);
              cl.setPoints(points);
            }
            price = sale.getCost() * (((actualDate - limitDate) * 0.02) + 1);
          }
          else{
            price = sale.getCost();
          }
          break;
        case "ELITE":
          price = sale.getCost() * 0.95;
          break;
        default:
          break;
      }
      sale.setAmountPaid(price);
    }

    // P4
    if((actualDate - limitDate) > N ){
      switch (cl.getStatus()){
        case "NORMAL":
          price = sale.getCost() * (((actualDate - limitDate) * 0.1) + 1);
          break;
        case "SELECTION":
          price = sale.getCost() * (((actualDate - limitDate) * 0.05) + 1);
          break;
        case "ELITE":
          if(needPoints && (actualDate - limitDate) > 15){
            points = (int) Math.rint(cl.getPoints() * 0.25);
            cl.setPoints(points);
          }
          price = sale.getCost();
          break;
        default:
          break;
      }
      sale.setAmountPaid(price);
    }
  }

  public void CalculateBalances(){
    Map<Integer,Transaction> transactions = getTransactions();
    _totalOrders = 0;
    _totalAllSales = 0;
    _totalPaidSales = 0;
    transactions.forEach((key, value) -> {
      String transClass = value.getClass().getSimpleName();
      if("Order".equals(transClass)){
        _totalOrders += value.getCost();
      }
      if("Sale".equals(transClass)){
        Sale sale = (Sale) value;
        if(sale.getPaid()){
          _totalPaidSales += sale.getAmountPaid();
        }

        else{
          try {
            UpdateSalePrice(sale,false);
          } catch (UnknownProductKeyException | UnknownClientKeyException e) {
            e.printStackTrace();
          }
        }

        _totalAllSales += sale.getAmountPaid();
      }

    });

    _availableBalance = _totalPaidSales - _totalOrders;
    _contBalance = _totalAllSales - _totalOrders;
  }
}
