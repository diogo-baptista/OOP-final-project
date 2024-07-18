package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;
import woo.core.Supplier;


/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<StoreManager> {
  private Input<String> _id;
  private Input<String> _address;
  private Input<String> _name;


  public DoRegisterSupplier(StoreManager receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);

    _id = _form.addStringInput(Message.requestSupplierKey());
    _name = _form.addStringInput(Message.requestSupplierName());
    _address = _form.addStringInput(Message.requestSupplierAddress());
  }

  @Override
  public void execute() throws DialogException {

    _form.parse();

    Supplier sup = new Supplier(_id.value(),_name.value(),_address.value());
    _receiver.getStore().addSupplier(sup);
  }
}
