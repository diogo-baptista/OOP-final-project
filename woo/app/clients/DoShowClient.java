package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.clients.Message;
import woo.core.Notification;
import woo.core.StoreManager;
import woo.core.Client;

/**
 * Show client.
 */
public class DoShowClient extends Command<StoreManager> {

  private Input<String> _id;

  public DoShowClient(StoreManager storefront) {
    super(Label.SHOW_CLIENT, storefront);
    _id = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();
    Client cl = _receiver.getStore().getClient(_id.value());
    _display.addLine(cl.toString());

    for(Notification n : cl.getNots()){
      _display.addLine(n.toString());
    }

    _display.display();
    cl.removeNots();
  }

}
