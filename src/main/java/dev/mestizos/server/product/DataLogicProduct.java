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
package dev.mestizos.server.product;

import dev.mestizos.server.product.dto.Category;
import dev.mestizos.server.product.dto.Product;
import com.unicenta.basic.BasicException;
import com.unicenta.data.loader.PreparedSentence;
import com.unicenta.data.loader.SerializerReadClass;
import com.unicenta.data.loader.SerializerWriteString;
import com.unicenta.data.loader.Session;
import com.unicenta.data.loader.StaticSentence;
import com.unicenta.pos.forms.BeanFactoryDataSingle;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge Luis
 */
public class DataLogicProduct extends BeanFactoryDataSingle {

    private Session s;

    public DataLogicProduct() {
    }

    @Override
    public void init(Session s) {
        this.s = s;
    }

    public List<Category> getCategories() {
        try {
            val sent = new StaticSentence(
                    s,
                    "SELECT "
                            + "ID, "
                            + "NAME, "
                            + "CATORDER "
                            + "FROM categories "
                            + "ORDER BY CATORDER, NAME",
                    null,
                    new SerializerReadClass(Category.class));
            return sent.list();

        } catch (BasicException ex) {
            System.out.println("BasicException " + ex.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Product> getProducts(String categoryId) {
        try {
            val sent = new PreparedSentence(
                    s,
                    "SELECT id, name, pricesell "
                    + "from products "
                    + "WHERE category = ?"
                    + "order by name ",
                    SerializerWriteString.INSTANCE,
                    new SerializerReadClass(Product.class));

            return sent.list(categoryId);

        } catch (BasicException ex) {
            System.out.println("BasicException " + ex.getMessage());
            return new ArrayList<>();
        }
    }
}