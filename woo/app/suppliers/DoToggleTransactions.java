package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;


/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<StoreManager> {
  private Input<String> _id;


  public DoToggleTransactions(StoreManager receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
    _id = _form.addStringInput(Message.requestSupplierKey());

  }

  @Override
  public void execute() throws DialogException {
      _form.parse();
      if(_receiver.getStore().getSupplier(_id.value()).toggleActivation()){
        _display.addLine(Message.transactionsOn(_id.value()));
      }
      else{
        _display.addLine(Message.transactionsOff(_id.value()));
      }
      _display.display();

  }

}
