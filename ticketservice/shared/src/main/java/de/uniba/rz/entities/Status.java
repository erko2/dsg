package de.uniba.rz.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.io.Serializable;

/**
 * Enumeration to describe the Status of a {@link Ticket}
 * 
 * Possible Values:
 * <ul>
 * <li>{@code NEW}</li>
 * <li>{@code ACCEPTED}</li>
 * <li>{@code REJECTED}</li>
 * <li>{@code CLOSED}</li>
 * </ul>
 * 
 */
public enum Status implements Serializable {
	NEW,
	ACCEPTED,
	REJECTED,
	CLOSED
}
