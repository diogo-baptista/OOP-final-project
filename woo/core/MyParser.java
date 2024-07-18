package woo.core;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Serializable;

import woo.app.exception.*;
import woo.core.exception.BadEntryException;


public class MyParser implements Serializable {
    private Store _store;

    /** Serial number for serialization. */
    private static final long serialVersionUID = 1L;

    MyParser(Store s) {
        _store = s;
    }

    /**
     *
     * @param fileName
     * @throws IOException
     * @throws BadEntryException
     * @throws DuplicateSupplierKeyException
     * @throws DuplicateClientKeyException
     * @throws DuplicateProductKeyException
     * @throws UnknownProductKeyException
     * @throws UnknownSupplierKeyException
     */
    void parseFile(String fileName) throws IOException, BadEntryException, DuplicateSupplierKeyException,
            DuplicateClientKeyException, DuplicateProductKeyException, UnknownProductKeyException,
            UnknownSupplierKeyException {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null)
                parseLine(line);
        }
    }

    /**
     *
     * @param line
     * @throws BadEntryException
     * @throws DuplicateSupplierKeyException
     * @throws UnknownSupplierKeyException
     * @throws DuplicateClientKeyException
     * @throws UnknownProductKeyException
     * @throws DuplicateProductKeyException
     */
    private void parseLine(String line) throws BadEntryException, DuplicateSupplierKeyException, UnknownSupplierKeyException,
            DuplicateClientKeyException, UnknownProductKeyException, DuplicateProductKeyException {

        String[] components = line.split("\\|");

        switch(components[0]) {
            case "SUPPLIER":
                parseSupplier(line, components);
                break;

            case "CLIENT":
                parseClient(line, components);
                break;

            case "BOX":
                parseBox(line, components);
                break;

            case "CONTAINER":
                parseContainer(line, components);
                break;

            case "BOOK":
                parseBook(line, components);
                break;

            default:
                throw new BadEntryException("Type of line not supported: " + line);
        }
    }

    /**
     *
     * @param line
     * @param components
     * @throws BadEntryException
     * @throws DuplicateSupplierKeyException
     * @throws UnknownSupplierKeyException
     */
    // Format: SUPPLIER|id|nome|endereço
    private void parseSupplier(String line, String[] components) throws BadEntryException,
            DuplicateSupplierKeyException, UnknownSupplierKeyException {
        if (components.length != 4)
            throw new BadEntryException("Invalid number of fields in supplier description (4) " + line);

        String id = components[1];
        String name = components[2];
        String address = components[3];

        Supplier sup = new Supplier(id,name,address);
        _store.addSupplier(sup);

    }

    /**
     *
     * @param line
     * @param components
     * @throws BadEntryException
     * @throws DuplicateClientKeyException
     */
    // Format: CLIENT|id|nome|endereço
    private void parseClient(String line, String[] components) throws BadEntryException, DuplicateClientKeyException {
        if (components.length != 4)
            throw new BadEntryException("Invalid number of fields (4) in client description: " + line);


        String id = components[1];
        String name = components[2];
        String address = components[3];

        Client cl = new Client(id,name,address);
        _store.addClient(cl);
    }

    /**
     *
     * @param line
     * @param components
     * @throws BadEntryException
     * @throws DuplicateProductKeyException
     * @throws UnknownProductKeyException
     * @throws UnknownSupplierKeyException
     */
    // Format: BOX|id|tipo-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
    private void parseBox(String line, String[] components) throws BadEntryException, DuplicateProductKeyException,
            UnknownProductKeyException, UnknownSupplierKeyException {
        if (components.length != 7)
            throw new BadEntryException("wrongr number of fields in box description  (7) " + line);


        int price = Integer.parseInt(components[4]);
        int criticalValue = Integer.parseInt(components[5]);
        int currentValue = Integer.parseInt(components[6]);

        String id = components[1];
        String serviceType = components[2];
        String supId = components[3];

        Box box = new Box(id,_store.getSupplier(supId),price,criticalValue,serviceType,currentValue);
        _store.addProduct(box);
    }

    /**
     *
     * @param line
     * @param components
     * @throws BadEntryException
     * @throws UnknownSupplierKeyException
     * @throws DuplicateProductKeyException
     * @throws UnknownProductKeyException
     */
    // Format: BOOK|id|título|autor|isbn|id-fornecedor|preço|valor-crítico|exemplares
    private void parseBook(String line, String[] components) throws BadEntryException,
            UnknownSupplierKeyException, DuplicateProductKeyException, UnknownProductKeyException {
        if (components.length != 9)
            throw new BadEntryException("Invalid number of fields (9) in box description: " + line);

        int price = Integer.parseInt(components[6]);
        int criticalValue = Integer.parseInt(components[7]);
        int currentValue = Integer.parseInt(components[8]);

        String id = components[1];
        String title = components[2];
        String author = components[3];
        String isbn = components[4];
        String supId = components[5];


        Book book = new Book(id,_store.getSupplier(supId),price,criticalValue,title,author,isbn,currentValue);
        _store.addProduct(book);
    }

    /**
     *
     * @param line
     * @param components
     * @throws BadEntryException
     * @throws UnknownSupplierKeyException
     * @throws DuplicateProductKeyException
     * @throws UnknownProductKeyException
     */
    // Format: CONTAINER|id|tipo-de-serviço|nível-de-serviço|id-fornecedor|preço|valor-crítico|exemplares
    private void parseContainer(String line, String[] components) throws BadEntryException, UnknownSupplierKeyException,
            DuplicateProductKeyException, UnknownProductKeyException {
        if (components.length != 8)
            throw new BadEntryException("Invalid number of fields (8) in container description: " + line);

        int price = Integer.parseInt(components[5]);
        int criticalValue = Integer.parseInt(components[6]);
        int currentValue = Integer.parseInt(components[7]);

        String id = components[1];
        String serviceType = components[2];
        String serviceLevel = components[3];
        String supId = components[4];

        Container cont = new Container(id,_store.getSupplier(supId),price,criticalValue,serviceType,serviceLevel,currentValue);
        _store.addProduct(cont);
    }
}