package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Client;
import woo.core.Sale;
import woo.core.StoreManager;

import java.util.Map;


/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<StoreManager> {

  private Input<String> _id;

  public DoLookupPaymentsByClient(StoreManager storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    _id = _form.addStringInput(woo.app.clients.Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();

    Client cl = _receiver.getStore().getClient(_id.value());
    Map<Integer, Sale> sales = cl.getSales();

    sales.forEach((key, value) -> {
      if(value.getPaid()){
        _display.addLine(value.toString());
      }
    });

    _display.display();
  }
}
