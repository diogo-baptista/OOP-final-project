package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException; 
import pt.tecnico.po.ui.Input;
import woo.core.Notification;
import woo.core.StoreManager;
import woo.core.Supplier;
import woo.core.Client;
import java.util.List;
import java.util.Map;


/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<StoreManager> {

  public DoShowAllClients(StoreManager storefront) {
    super(Label.SHOW_ALL_CLIENTS, storefront);

  }

  @Override
  public void execute() throws DialogException {
    Map<String,Client> clients = _receiver.getStore().getClients();

    clients.forEach((key, value) ->  {
      _display.addLine(value.toString());

    });
    _display.display();

  }
}
