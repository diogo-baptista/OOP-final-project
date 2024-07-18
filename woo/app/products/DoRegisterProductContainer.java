package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.app.exception.UnknownServiceLevelException;
import woo.app.exception.UnknownServiceTypeException;
import woo.core.*;


/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<StoreManager> {
  private Input<String> _id;
  private Input<Integer> _price;
  private Input<Integer> _criticalValue;
  private Input<String> _supplierId;
  private Input<String> _serviceType;
  private Input<String> _serviceLevel;

  public DoRegisterProductContainer(StoreManager receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _criticalValue = _form.addIntegerInput(Message.requestStockCriticalValue());
    _supplierId = _form.addStringInput(Message.requestSupplierKey());
    _serviceType = _form.addStringInput(Message.requestServiceType());
    _serviceLevel = _form.addStringInput(Message.requestServiceLevel());

  }

  private boolean checkST(){
    for(ServiceType st : ServiceType.values()){
      if(st.name().equals(_serviceType.value())){
        return true;
      }
    }
    return false;
  }

  private boolean checkSL(){
    for(ServiceLevel sl : ServiceLevel.values()){
      if(sl.name().equals(_serviceLevel.value())){
        return true;
      }
    }
    return false;
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();

    if(_price.value() <= 0){
      return;
    }

    if(!checkST()){
      throw new UnknownServiceTypeException(_serviceType.value());
    }

    if(!checkSL()){
      throw new UnknownServiceLevelException(_serviceLevel.value());
    }

    Supplier sup = _receiver.getStore().getSupplier(_supplierId.value());
    Container cont = new Container(_id.value(),sup,_price.value(),_criticalValue.value(), _serviceType.value(), _serviceLevel.value());
    _receiver.getStore().addProduct(cont);

  }
}
