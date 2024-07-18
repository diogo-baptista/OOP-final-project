package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnauthorizedSupplierException;
import woo.app.transactions.Message;
import woo.core.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<StoreManager> {

  public DoRegisterOrderTransaction(StoreManager receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);

  }


  @Override
  public final void execute() throws DialogException {
    _form.clear();
    Input<String> _supId = _form.addStringInput(Message.requestSupplierKey());
    Input<String> _prodId = _form.addStringInput(Message.requestProductKey());
    Input<Integer> _quantity = _form.addIntegerInput(Message.requestAmount());
    Input<String> _more = _form.addStringInput(Message.requestMore());

    _form.parse();

    if(_quantity.value() <= 0){
      return;
    }

    int date = _receiver.getStore().getDate();
    Supplier sup = _receiver.getStore().getSupplier(_supId.value());
    if(!sup.getState()){
      throw new UnauthorizedSupplierException(_supId.value());
    }
    int qt = _quantity.value();

    _receiver.getStore().getProduct(_prodId.value());
    sup.getProd(_prodId.value());
    Product pr = sup.getProd(_prodId.value());

    if(_receiver.getStore().getProduct(_prodId.value()).getQuantity() == 0){
      Notification not = new Notification("NEW",pr);
      _receiver.getStore().putNotification(not,pr);
    }

    _receiver.getStore().getProduct(_prodId.value()).addQuantity(qt);
    Item it = new Item(qt, pr);

    int transID = _receiver.getStore().getTransactionID();
    Order or = new Order(transID ,date, sup);

    or.addItem(it);


    while (_more.value().equals("s")){
      _form.clear();

      _prodId = _form.addStringInput(woo.app.transactions.Message.requestProductKey());
      _quantity = _form.addIntegerInput(woo.app.transactions.Message.requestAmount());
      _more = _form.addStringInput(woo.app.transactions.Message.requestMore());

      _form.parse();

      int qtt = _quantity.value();
      _receiver.getStore().getProduct(_prodId.value());
      _receiver.getStore().getSupplier(_supId.value()).getProd(_prodId.value());
      Product p = _receiver.getStore().getSupplier(_supId.value()).getProd(_prodId.value());

      if(_receiver.getStore().getProduct(_prodId.value()).getQuantity() == 0){

        Notification not = new Notification("NEW",p);
        _receiver.getStore().putNotification(not,p);
      }

      _receiver.getStore().getProduct(_prodId.value()).addQuantity(qtt);

      Item itt = new Item(qtt, p);
      or.addItem(itt);
    }

    or.Calculate();
    sup.addOrder(or);
    _receiver.getStore().getTransactions().put(or.getID(),or);
    _receiver.getStore().addTransactionID();

  }
}
