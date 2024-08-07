/*
 * Copyright (C) 2024 Jorge Luis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package dev.mestizos.server.restaurant;

import dev.mestizos.server.restaurant.dto.Floor;
import dev.mestizos.server.restaurant.dto.Place;
import com.unicenta.basic.BasicException;
import com.unicenta.data.loader.PreparedSentence;
import com.unicenta.data.loader.SerializerReadClass;
import com.unicenta.data.loader.SerializerWriteString;
import com.unicenta.data.loader.Session;
import com.unicenta.data.loader.StaticSentence;
import com.unicenta.pos.forms.BeanFactoryDataSingle;

import lombok.val;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Luis
 */
public class DataLogicRestaurant extends BeanFactoryDataSingle {

    private Session s;

    public DataLogicRestaurant() {
    }

    @Override
    public void init(Session s) {
        this.s = s;
    }

    public List<Floor> getFloors() {
        try {
            val sent = new StaticSentence(
                    s,
                    "SELECT ID, NAME FROM floors ORDER BY NAME",
                    null,
                    new SerializerReadClass(Floor.class));
            return sent.list();

        } catch (BasicException ex) {
            System.out.println("BasicException " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Place> getPlaces(String floorId) {
        try {
            val sent = new PreparedSentence(
                    s,
                    "SELECT ID, NAME, SEATS, FLOOR, CUSTOMER, WAITER, "
                            + "TICKETID, TABLEMOVED, GUESTS, OCCUPIED "
                            + "FROM places "
                            + "WHERE FLOOR = ? "
                            + "ORDER BY name ",
                    SerializerWriteString.INSTANCE,
                    new SerializerReadClass(Place.class));

            return sent.list(floorId);

        } catch (BasicException ex) {
            System.out.println("BasicException " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public Place getPlace(String placeId) {
        try {
            val sent = new PreparedSentence(
                    s,
                    "SELECT ID, NAME, SEATS, FLOOR, CUSTOMER, WAITER, "
                            + "TICKETID, TABLEMOVED, GUESTS, OCCUPIED "
                            + "FROM places "
                            + "WHERE ID = ? ",
                    SerializerWriteString.INSTANCE,
                    new SerializerReadClass(Place.class));

            return (Place) sent.find(placeId);

        } catch (BasicException ex) {
            System.out.println("BasicException " + ex.getMessage());
            return null;
        }
    }
}