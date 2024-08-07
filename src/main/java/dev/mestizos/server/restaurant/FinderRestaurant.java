package dev.mestizos.server.restaurant;

import dev.mestizos.server.restaurant.dto.Floor;
import dev.mestizos.server.restaurant.dto.Place;
import java.util.List;

import com.unicenta.data.loader.Session;

public class FinderRestaurant {

    public static List<Floor> getFloors(Session session) {
        System.out.println("Get Floors");
        DataLogicRestaurant dlRestaurant = new DataLogicRestaurant();

        dlRestaurant.init(session);

        return dlRestaurant.getFloors();
    }

    public static List<Place> getPlaces(Session session, String floorId) {
        System.out.println("Get Places");
        DataLogicRestaurant dlRestaurant = new DataLogicRestaurant();

        dlRestaurant.init(session);

        return dlRestaurant.getPlaces(floorId);
    }
}
