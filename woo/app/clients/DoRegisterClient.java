package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;
import woo.core.Client;
import woo.app.clients.Message;


/**
 * Register new client.
 */
public class DoRegisterClient extends Command<StoreManager> {
  private Input<String> _id;
  private Input<String> _name;
  private Input<String> _address;

  public DoRegisterClient(StoreManager storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _id = _form.addStringInput(woo.app.clients.Message.requestClientKey());
    _name = _form.addStringInput(woo.app.clients.Message.requestClientName());
    _address = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();

    Client cl = new Client(_id.value(),_name.value(),_address.value());
    _receiver.getStore().addClient(cl);

  }

}
