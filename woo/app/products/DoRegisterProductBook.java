package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.products.Message;
import woo.core.Book;
import woo.core.Notification;
import woo.core.StoreManager;
import woo.core.Supplier;

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<StoreManager> {

  private Input<String> _id;
  private Input<Integer> _price;
  private Input<Integer> _criticalValue;
  private Input<String> _supplierId;
  private Input<String> _title;
  private Input<String> _author;
  private Input<String> _isbn;

  public DoRegisterProductBook(StoreManager receiver) {
    super(Label.REGISTER_BOOK, receiver);

    _id = _form.addStringInput(Message.requestProductKey());
    _title = _form.addStringInput(Message.requestBookTitle());
    _author = _form.addStringInput(Message.requestBookAuthor());
    _isbn = _form.addStringInput(Message.requestISBN());
    _price = _form.addIntegerInput(Message.requestPrice());
    _criticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierId = _form.addStringInput(Message.requestSupplierKey());





  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();

    if(_price.value() <= 0){
      return;
    }

    Supplier sup = _receiver.getStore().getSupplier(_supplierId.value());
    Book book = new Book(_id.value(),sup,_price.value(),_criticalValue.value(), _title.value(), _author.value(), _isbn.value());
    _receiver.getStore().addProduct(book);


  }
}
