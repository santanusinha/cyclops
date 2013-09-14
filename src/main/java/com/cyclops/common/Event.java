package com.cyclops.common;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * User: Santanu Sinha (santanu.sinha@flipkart.com)
 * Date: 14/09/13
 * Time: 1:05 PM
 */
@XmlRootElement
public class Event implements Serializable {
    private String source;
    private String message;

    public Event() {
    }

    public Event(String source, String message) {
        this.source = source;
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
