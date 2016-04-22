package main;

import businessLogic.DataWindowManager;
import presentationLayer.DataWindow;

public class Main {
    public static void main(String[] args) {
        new DataWindowManager(new DataWindow());
    }
}