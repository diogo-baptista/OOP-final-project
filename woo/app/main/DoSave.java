package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.FileOpenFailedException;
import woo.core.StoreManager;
import woo.core.exception.ImportFileException;
import woo.core.exception.MissingFileAssociationException;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<StoreManager> {

  private Input<String> _filename;

  /** @param receiver */
  public DoSave(StoreManager receiver) {
    super(Label.SAVE, receiver);


  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute() {
    try{
      if(_receiver.getFileName().isEmpty()){
        _filename = _form.addStringInput(Message.newSaveAs());
        _form.parse();
        _receiver.saveAs(_filename.value());
      }
      else{
        _receiver.save();
      }
    } catch (IOException | MissingFileAssociationException e) {
      _display.addLine(e.getMessage());
    }
    _display.display();

  }

}
