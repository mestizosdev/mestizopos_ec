package dev.mestizos.server.restaurant.dto;

import com.unicenta.basic.BasicException;
import com.unicenta.data.loader.DataRead;
import com.unicenta.data.loader.SerializableRead;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Place implements SerializableRead {
    
    @Getter
    private String id;
    @Getter
    @Setter
    private String name;
    @Getter
    private String seats;
    @Getter
    private String floor;
    @Getter
    private String customer;
    @Getter
    private String waiter;
    @Getter
    private String ticketId;
    @Getter
    private Boolean tableMoved;
    @Getter
    @Setter
    private int guests;
    @Getter
    private Date occupied;
    @Getter
    @Setter
    private boolean people;
    
    public Place() {}

    @Override
    public void readValues(DataRead dr) throws BasicException {
        id = dr.getString(1);
        name = dr.getString(2);
        seats = dr.getString(3);        
        floor = dr.getString(4);
        customer = dr.getString(5);
        waiter = dr.getString(6);
        ticketId = dr.getString(7);
        tableMoved = dr.getBoolean(8);
        guests = dr.getInt(9);
        occupied = dr.getTimestamp(10);
    } 
}
