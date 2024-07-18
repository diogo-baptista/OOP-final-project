package woo.app.clients;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownClientKeyException;
import woo.app.exception.UnknownProductKeyException;
import woo.core.Item;
import woo.core.Sale;
import woo.core.StoreManager;
import woo.core.Client;

import java.util.Map;


/**
 * Show all transactions for a specific client.
 */

public class DoShowClientTransactions extends Command<StoreManager> {

  private Input<String> _id;

  public DoShowClientTransactions(StoreManager storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    _id = _form.addStringInput(woo.app.clients.Message.requestClientKey());

  }

  @Override
  public void execute() throws DialogException {
    _form.parse();

    Client cl = _receiver.getStore().getClient(_id.value());

    Map<Integer, Sale> sales = cl.getSales();

    sales.forEach((key, value) ->{
      try {
        _receiver.getStore().UpdateSalePrice(value,false);
      } catch (UnknownProductKeyException | UnknownClientKeyException e) {
        e.printStackTrace();
      }
      _display.addLine(value.toString());
    } );

    _display.display();


  }

}
