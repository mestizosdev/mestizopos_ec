package dev.mestizos.server.product.dto;

import com.unicenta.basic.BasicException;
import com.unicenta.data.loader.DataRead;
import com.unicenta.data.loader.SerializableRead;
import lombok.Getter;

/**
 *
 * @author  Jorge Luis
 * @version 
 */
public class Category implements SerializableRead {

    @Getter
    private String id;
    @Getter
    private String name;
    @Getter
    private String catOrder;

    public Category() {}

    @Override
    public void readValues(DataRead dr) throws BasicException {
        id = dr.getString(1);
        name = dr.getString(2);
        catOrder = dr.getString(3);
    }
}

