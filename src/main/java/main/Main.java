package main;

import businessLogic.DataWindowManager;

/**
 * The NBPImporter program implements an application that
 * simply download exchange rates and print on the app.
 *
 * @author Bartosz Sledz
 * @version 1.1
 * @since 2016-04-24
 */
public class Main {
    public static void main(final String[] args) {
        new DataWindowManager();
    }
}