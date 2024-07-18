package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.*;


/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<StoreManager> {

  private Input<String> _clId;
  private Input<Integer> _limit;
  private Input<String> _prodId;
  private Input<Integer> _qt;

  public DoRegisterSaleTransaction(StoreManager receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);

    _clId = _form.addStringInput(woo.app.transactions.Message.requestClientKey());
    _limit = _form.addIntegerInput(woo.app.transactions.Message.requestPaymentDeadline());
    _prodId = _form.addStringInput(woo.app.transactions.Message.requestProductKey());
    _qt = _form.addIntegerInput(woo.app.transactions.Message.requestAmount());

  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();

    if(_qt.value() <= 0){
      return;
    }

    Product p = _receiver.getStore().getProduct(_prodId.value());
    Item itt = new Item(_qt.value(), p);
    _receiver.getStore().getProduct(_prodId.value()).reduceQuantity(_qt.value());
    Client cl = _receiver.getStore().getClient(_clId.value());

    int transID = _receiver.getStore().getTransactionID();
    int date = _receiver.getStore().getDate();
    Sale sl = new Sale(transID,date, _limit.value(),cl);
    sl.addItem(itt);
    sl.Calculate();


    _receiver.getStore().getTransactions().put(sl.getID(),sl);
    cl.getSales().put(sl.getID(),sl);
    cl.addPM((int) Math.rint(sl.getCost()));
    _receiver.getStore().addTransactionID();

  }

}
