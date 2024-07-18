package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;
import woo.app.main.Message;

/**
 * Show global balance.
 */
public class DoShowGlobalBalance extends Command<StoreManager> {


  public DoShowGlobalBalance(StoreManager receiver) {
    super(Label.SHOW_BALANCE, receiver);

  }

  @Override
  public final void execute() {

    _receiver.getStore().CalculateBalances();
    int contBalance = (int) Math.rint(_receiver.getStore().getContBalance());
    int availableBalance = (int) Math.rint(_receiver.getStore().getAvailableBalance());

    _display.addLine(Message.currentBalance(availableBalance,contBalance));
    _display.display();
  }
}
