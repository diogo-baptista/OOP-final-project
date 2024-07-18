package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Product;
import woo.core.Sale;
import woo.core.StoreManager;
import woo.core.Transaction;
import woo.core.Client;


/**
 * Pay transaction (sale).
 */

public class DoPay extends Command<StoreManager> {

  private Input<Integer> _transId;
  
  public DoPay(StoreManager storefront) {
    super(Label.PAY, storefront);
    _transId = _form.addIntegerInput(woo.app.transactions.Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();

    Transaction tr = _receiver.getStore().getTransaction(_transId.value());

    if(tr.getPaid()){
      return;
    }

    Sale sale = (Sale) tr;
    Client cl = _receiver.getStore().getClient(sale.getClientID());

    _receiver.getStore().UpdateSalePrice(sale,true);

    sale.Pay();
    cl.addPP((int) Math.rint(sale.getAmountPaid()));
    cl.updateStatus();
    sale.setPaymentDate(_receiver.getStore().getDate());
    cl.getSales().replace(sale.getID(),sale);

  }
}
