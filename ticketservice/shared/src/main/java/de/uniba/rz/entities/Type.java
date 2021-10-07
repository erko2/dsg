package de.uniba.rz.entities;

import org.glassfish.jersey.server.JSONP;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.io.Serializable;

/**
 * Enumeration to describe the Type of a {@link Ticket}
 * 
 * Possible Values:
 * <ul>
 * <li>{@code TASK}</li>
 * <li>{@code ENHANCEMENT}</li>
 * <li>{@code BUG}</li>
 * <li>{@code QUESTION}</li>
 * </ul>
 * 
 */
public enum Type implements Serializable {
    TASK,
    ENHANCEMENT,
    BUG,
    QUESTION
}
