package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.core.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Show all products.
 */
public class DoShowAllProducts extends Command<StoreManager> {


  public DoShowAllProducts(StoreManager receiver) {
    super(Label.SHOW_ALL_PRODUCTS, receiver);
  }

  @Override
  public final void execute() throws DialogException {

    Map<String,Product> allProducts = _receiver.getStore().getProducts();
    Map<String,Product> sortedProducts = new TreeMap<>(allProducts);

    sortedProducts.forEach((key, value) ->  _display.addLine(value.toString()));

    _display.display();
  }

}
