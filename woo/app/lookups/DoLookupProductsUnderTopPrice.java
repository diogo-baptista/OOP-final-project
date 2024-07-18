package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.lookups.Message;
import woo.core.Product;
import woo.core.StoreManager;

import java.util.Map;


/**
 * Lookup products cheaper than a given price.
 */
public class DoLookupProductsUnderTopPrice extends Command<StoreManager> {

  private Input<Integer> _price;

  public DoLookupProductsUnderTopPrice(StoreManager storefront) {
    super(Label.PRODUCTS_UNDER_PRICE, storefront);
    _price = _form.addIntegerInput(woo.app.lookups.Message.requestPriceLimit());
  }

  @Override
  public void execute() throws DialogException {
    _form.parse();

    if(_price.value() <= 0){
      return;
    }

    Map<String, Product> products = _receiver.getStore().getProducts();

    products.forEach((key, value) -> {
      if(value.getPrice() < _price.value()){
        _display.addLine(value.toString());
      }
    });

    _display.display();

  }
}
