package dev.mestizos.server.product;

import dev.mestizos.server.product.dto.Category;
import dev.mestizos.server.product.dto.Product;
import java.util.List;

import com.unicenta.data.loader.Session;

public class FinderProduct {

    public static List<Category> getCategories(Session session) {
        System.out.println("Get Caregories");

        DataLogicProduct dlProduct = new DataLogicProduct();
        dlProduct.init(session);

        return dlProduct.getCategories();
    }

    public static List<Product> getProducts(Session session, String categoryId) {
        System.out.println("Get Products");
        
        DataLogicProduct dlProduct = new DataLogicProduct();
        dlProduct.init(session);

        return dlProduct.getProducts(categoryId);
    }
}
