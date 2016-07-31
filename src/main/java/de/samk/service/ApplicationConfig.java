/*
 * ApplicationConfig.java
 * 
 * 29.07.2016
 * 
 */
package de.samk.service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A singleton class used to store transaction, this class can be replaced with
 * persistence layer if required.
 * 
 * @author S.Kulkarni
 * 
 * @changed S.Kulkarni 29.07.2016 - created.
 */
@ApplicationPath("rest")
public class ApplicationConfig extends Application {

}