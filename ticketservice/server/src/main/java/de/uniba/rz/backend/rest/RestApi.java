package de.uniba.rz.backend.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import de.uniba.rz.backend.rest.get.*;
import de.uniba.rz.backend.rest.put.*;
import de.uniba.rz.backend.rest.post.*;

@ApplicationPath("/")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RestApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(Create.class);
        resources.add(ReadAll.class);
        resources.add(Update.class);
        resources.add(Search.class);

        return resources;

    }
}
