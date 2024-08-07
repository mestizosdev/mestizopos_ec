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
package dev.mestizos.server;

/**
 *
 * @author Jorge Luis
 */

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import com.unicenta.pos.forms.AppView;

import dev.mestizos.server.product.AsyncServletCategory;
import dev.mestizos.server.product.AsyncServletProduct;
import dev.mestizos.server.restaurant.AsyncServletFloor;
import dev.mestizos.server.restaurant.AsyncServletPlaces;
import dev.mestizos.server.shared.AsyncServletShared;
import dev.mestizos.server.version.AsyncServletVersion;
import lombok.val;

import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {

    private Server server;

    public void start(AppView app) throws Exception {

        int maxThreads = 100;
        int minThreads = 10;
        int idleTimeout = 120;

        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);

        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[] { connector });

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        ServletHolder holderVersion = new ServletHolder(new AsyncServletVersion(app.getSession()));
        val holderFloor = new ServletHolder(new AsyncServletFloor(app.getSession()));
        val holderPlacesV1 = new ServletHolder(new AsyncServletPlaces(app.getSession()));
        val holderPlacesV2 = new ServletHolder(new AsyncServletPlaces(app.getSession()));
        val holderCategory = new ServletHolder(new AsyncServletCategory(app.getSession()));
        val holderProduct = new ServletHolder(new AsyncServletProduct(app.getSession()));
        val holderShared = new ServletHolder(new AsyncServletShared(app));

        servletHandler.addServletWithMapping(holderVersion, "/v1/version");

        servletHandler.addServletWithMapping(holderFloor, "/v1/restaurant/floors");
        /**
         * Use parameter floor to get places
         * For example: http://localhost:8090/v1/places?floor=parameter
         */
        servletHandler.addServletWithMapping(holderPlacesV1, "/v1/restaurant/places");
        /**
         * Use path parameter floor to get places
         * For example: http://localhost:8090/v2/places/parameter
         */
        servletHandler.addServletWithMapping(holderPlacesV2, "/v2/restaurant/places/*");

        servletHandler.addServletWithMapping(holderCategory, "/v1/product/categories");
        servletHandler.addServletWithMapping(holderProduct, "/v1/product/products/*");
        
        servletHandler.addServletWithMapping(holderShared, "/v1/restaurant/shared");
        
        server.start();
        
        System.out.println("Start Jetty Server on port " + connector.getPort());
    }

    public void stop() throws Exception {
        server.stop();
    }
}