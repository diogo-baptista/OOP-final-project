package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Item;
import woo.core.Sale;
import woo.core.StoreManager;
import woo.core.Transaction;

import java.util.Map;

import woo.app.transactions.Message;


/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<StoreManager> {

  private Input<Integer> _transId;

  public DoShowTransaction(StoreManager receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _transId = _form.addIntegerInput(woo.app.transactions.Message.requestTransactionKey());

  }


  @Override
  public final void execute() throws DialogException {
    _form.parse();

    Transaction tr = _receiver.getStore().getTransaction(_transId.value());

    if(tr.getClass().getSimpleName().equals("Sale")){
      Sale sale = (Sale) tr;
      _receiver.getStore().UpdateSalePrice(sale,false);
      _display.addLine(tr.toString());
    }
    else{
      _display.addLine(tr.toString());
      for(Item it : tr.getItems() ){
        _display.addLine(it.toString());
      }
    }


    _display.display();

  }
}
