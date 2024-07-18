package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.Notification;
import woo.core.Product;
import woo.core.StoreManager;


/**
 * Change product price.
 */
public class DoChangePrice extends Command<StoreManager> {

  private Input<String> _id;
  private Input<Integer> _price;
  
  public DoChangePrice(StoreManager receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();

    if(_price.value() <= 0){
      return;
    }

    Product prod = _receiver.getStore().getProduct(_id.value());
    if(prod.getPrice() > _price.value()){
      Notification not = new Notification("BARGAIN",prod);
      _receiver.getStore().putNotification(not,prod);
    }
    prod.changePrice(_price.value());


  }
}
