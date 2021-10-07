package de.uniba.rz.entities;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import java.io.Serializable;

/**
 * Enumeration to describe the Priority of a {@link Ticket}
 * 
 * Possible Values:
 * <ul>
 * <li>{@code CRITICAL}</li>
 * <li>{@code MAJOR}</li>
 * <li>{@code MINOR}</li>
 * </ul>
 * 
 */
public enum Priority implements Serializable {
	CRITICAL,
	MAJOR,
	MINOR
}
