package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.StoreManager;

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<StoreManager> {
  private Input<Integer> _add;

  public DoAdvanceDate(StoreManager receiver) {
    super(Label.ADVANCE_DATE, receiver);

    _add = _form.addIntegerInput(Message.requestDaysToAdvance());

  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    _receiver.getStore().addDate(_add);

  }
}
