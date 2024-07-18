package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.*;

import java.util.Map;


/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<StoreManager> {

  private Input<String> _id;

  public DoShowSupplierTransactions(StoreManager receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _id = _form.addStringInput(Message.requestSupplierKey());

  }

  @Override
  public void execute() throws DialogException {
    _form.parse();

    Supplier sup = _receiver.getStore().getSupplier(_id.value());

    Map<Integer, Order> orders = sup.getOrders();

    orders.forEach((key, value) ->{
      _display.addLine(value.toString());
      for(Item it : value.getItems()){
        _display.addLine(it.toString());
      }
    }  );

    _display.display();
  }
}
