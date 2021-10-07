package de.uniba.rz.backend;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;

import com.sun.net.httpserver.HttpServer;
import de.uniba.rz.backend.rest.RestApi;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class RestRemoteAccess implements RemoteAccess {
    private final String serverURI;

    URI baseUri = null;
    ResourceConfig config = null;
    HttpServer httpServer = null;

    public RestRemoteAccess(String host, String port) {
        serverURI = "http://" + host + ":" + port + "/";
    }

    @Override
    public void prepareStartup(TicketStore ticketStore) {
        baseUri = UriBuilder.fromUri(serverURI).build();
        config = ResourceConfig.forApplicationClass(RestApi.class);
    }

    @Override
    public void shutdown() {
        httpServer.stop(0);
        System.out.println("\t[JAX-RS-SERVER]: STOPPED");

    }

    @Override
    public void run() {
        httpServer = JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("\t[JAX-RS-SERVER]: READY");

    }
}
