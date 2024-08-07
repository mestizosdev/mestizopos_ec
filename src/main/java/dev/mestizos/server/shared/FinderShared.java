package dev.mestizos.server.shared;

import java.util.Date;

import com.unicenta.basic.BasicException;
import com.unicenta.pos.forms.AppView;
import com.unicenta.pos.sales.DataLogicReceipts;
import com.unicenta.pos.sales.TicketsEditor;
import com.unicenta.pos.sales.restaurant.RestaurantDBUtils;
import com.unicenta.pos.ticket.TicketInfo;

import dev.mestizos.server.restaurant.DataLogicRestaurant;
import dev.mestizos.server.restaurant.dto.Place;

public class FinderShared implements TicketsEditor {

    private final AppView app;
    private DataLogicReceipts dlReceipts;
    private DataLogicRestaurant dlRestaurant;
    private final RestaurantDBUtils restDB;

    protected TicketInfo ticketInfo;

    FinderShared(AppView app) {
        this.app = app;
        dlReceipts = new DataLogicReceipts();
        dlRestaurant = new DataLogicRestaurant();
        restDB = new RestaurantDBUtils(app);

        dlReceipts.init(app.getSession());
        dlRestaurant.init(app.getSession());
    }

    public TicketInfo getTicketInfo(Place place) {
        System.out.println("Get TicketInfo");

        try {
            return dlReceipts.getSharedTicket(place.getId());
        } catch (BasicException e) {
            return null;
        }
    }

    public void setTicketInfo(Place place, TicketInfo ticket) {
        System.out.println("Set TicketInfo");

        place.setName(dlRestaurant.getPlace(place.getId()).getName());

        if (ticket == null) {
            ticket = new TicketInfo();
            ticket.setUser(app.getAppUserView().getUser().getUserInfo());
            try {
                dlReceipts.insertSharedTicket(place.getId(), ticket, ticket.getPickupId());
            } catch (BasicException e) {
                System.out.println("Error " + e.getMessage());
            }
            place.setPeople(true);
            place.setGuests(restDB.updateGuestsInTable(place.getId()));
            setActivePlace(place, ticket);
        }
    }

    private void setActivePlace(Place place, TicketInfo ticket) {
        setActiveTicket(ticket, place.getName());
        Integer count = restDB.getGuestsInTable(place.getId());

        System.out.println("Table : " + place.getId() + " GuestsCount :" + count);

        try {
            dlReceipts.lockSharedTicket(place.getId(), "locked");
        } catch (BasicException ex) {
            System.out.println("BasicException " + ex.getMessage());
        }
    }

    @Override
    public void setActiveTicket(TicketInfo oTicket, Object oTicketExt) {
        ticketInfo = oTicket;

        if (ticketInfo != null) {
            ticketInfo.setUser(app.getAppUserView().getUser().getUserInfo());
            ticketInfo.setActiveCash(app.getActiveCashIndex());
            ticketInfo.setDate(new Date());

            if (restDB.getWaiterNameInTable(oTicketExt.toString()) == null
                    || "".equals(restDB.getWaiterNameInTable(oTicketExt.toString()))) {
                        
                restDB.setWaiterNameInTable(app.getAppUserView().getUser().getName(),
                        oTicketExt.toString());
            }

            restDB.setTicketIdInTable(ticketInfo.getId(), oTicketExt.toString());
            restDB.setGuestsInTable(
                    restDB.getGuestsInTable(ticketInfo.getId()),
                    ticketInfo.getId());
            restDB.setOccupied(ticketInfo.getId());
        }
    }

    @Override
    public TicketInfo getActiveTicket() {
        return ticketInfo;
    }
}
