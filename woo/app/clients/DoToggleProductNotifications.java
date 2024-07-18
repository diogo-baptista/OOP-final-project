package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Product;
import woo.core.Client;
import woo.core.StoreManager;


/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<StoreManager> {

  private Input<String> _clId;
  private Input<String> _prodId;

  public DoToggleProductNotifications(StoreManager storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _clId = _form.addStringInput(woo.app.clients.Message.requestClientKey());
    _prodId = _form.addStringInput(woo.app.clients.Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();

    Product p = _receiver.getStore().getProduct(_prodId.value());
    Client c = _receiver.getStore().getClient(_clId.value());
    if(p.getNotClients().containsKey(_clId.value().toLowerCase())){
      p.getNotClients().remove(c.getId().toLowerCase());
      _display.addLine(Message.notificationsOn(_clId.value(),_prodId.value()));
    }
    else {
      p.getNotClients().put(c.getId().toLowerCase(),c);
      _display.addLine(Message.notificationsOff(_clId.value(),_prodId.value()));
    }

    _display.display();

  }

}
