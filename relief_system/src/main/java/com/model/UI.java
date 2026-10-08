package com.model;

import java.util.Scanner;

public class UI {
    private ReliefFacade reliefFacade;

    ReliefFacadeUI() {
        reliefFacade = new ReliefFacade();
    
    }

    public void run() {
        scenario1();
        scenario2();
    }

    public void scenario1() {
        System.out.println();

        if(!reliefFacade.login("hwells", "topS$cr$t")) {
            System.out.println("Sorry, we couldn't log you in");
        return;
        }
        System.out.println("Hannah Wells is now logged in!");
    }

    public void scenario2() {
        System.out.println();
        if(!reliefFacade.login("kyled","892EijL0Ws")) {
            System.out.println("Sorry, we couldn;t log you in.");
            return;
        }
        System.out.println("Kyle Donaldson is now logged in!");
    }

    public static void main(String[] args)
{
    ReliefFacadeUI facadeInterface = new ReliefFacadeUI();
    facadeInterface.run();
}    
}
