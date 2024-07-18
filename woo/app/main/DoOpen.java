package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.FileOpenFailedException;
import woo.core.StoreManager;
import woo.core.Store;
import woo.core.exception.ImportFileException;
import woo.core.exception.UnavailableFileException;

import java.io.IOException;

/**
 * Open existing saved state.
 */
public class DoOpen extends Command<StoreManager> {

  private Input<String> _filename;

  /** @param receiver */
  public DoOpen(StoreManager receiver) {
    super(Label.OPEN, receiver);

    _filename = _form.addStringInput(Message.openFile());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() throws DialogException {
    _form.parse();
    try {
      _receiver.load(_filename.value());

    } catch (IOException | ClassNotFoundException | UnavailableFileException e) {
      throw new FileOpenFailedException(_filename.value());
    }

    _display.display();
  }


}
