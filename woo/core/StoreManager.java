/*
/////////////////////////////
// Projeto de PO           //
// Curso: LETI             //
// Grupo: 93               //
//                         //
// Gonçalo Veiga  - 96738  //
// Diogo Baptista - 96733  //
/////////////////////////////
 */


package woo.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import woo.app.exception.*;
import woo.core.exception.UnavailableFileException;
import woo.core.exception.MissingFileAssociationException;
import woo.core.exception.ImportFileException;
import woo.core.exception.BadEntryException;

/**
 * StoreManager: façade for the core classes.
 */
public class StoreManager {

  /** Current filename. */
  private String _filename = "";

  /** The actual store. */
  private Store _store;

  public StoreManager(){
    _store = new Store();
  }

  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save() throws IOException, FileNotFoundException, MissingFileAssociationException {
    try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(_filename))){
      output.writeObject(_store);
    }
  }

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws MissingFileAssociationException, FileNotFoundException, IOException {
    _filename = filename;
    save();
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String filename) throws IOException, ClassNotFoundException, UnavailableFileException {

    ObjectInputStream objIn = null;
    try{
      objIn = new ObjectInputStream(new FileInputStream(filename));
      Object anObject = objIn.readObject();
      _store = (Store) anObject;
      _filename = filename;
    } finally {
      if(objIn != null)
        objIn.close();
    }

  }

  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
    } catch (IOException | BadEntryException | DuplicateSupplierKeyException | DuplicateClientKeyException
            | DuplicateProductKeyException | UnknownProductKeyException | UnknownSupplierKeyException e) {
      throw new ImportFileException(textfile);
    }
  }


  public Store getStore(){
    return _store;
  }

  public String getFileName(){
    return _filename;
  }

}
