package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;
import woo.core.Supplier;
import java.util.Map;
import java.util.TreeMap;


/**
 * Show all suppliers.
 */
public class DoShowSuppliers extends Command<StoreManager> {


  public DoShowSuppliers(StoreManager receiver) {
    super(Label.SHOW_ALL_SUPPLIERS, receiver);

  }

  @Override
  public void execute() throws DialogException {
    Map<String,Supplier> suppliers = _receiver.getStore().getSuppliers();
    Map<String,Supplier> sortedSuppliers = new TreeMap<>(suppliers);
    sortedSuppliers.forEach((key, value) -> _display.addLine(value.toString()));

    _display.display();
  }
}
