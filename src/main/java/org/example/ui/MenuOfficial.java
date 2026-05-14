package org.example.ui;

import org.example.business.Official;

import java.util.Scanner;

public class MenuOfficial extends BaseMenu {
    private final Official official;

    public MenuOfficial(Official official) {
        this.official = official;
    }

    @Override
    protected String getTitle() {
        return "Official menu, Hello " + official.getName() + " from " + official.getDepartment();
    }

    @Override
    protected void printOptions() {
        System.out.println("1. Add citizen");
        System.out.println("2. Delete citizen");
        System.out.println("3. Change citizen address");
        System.out.println("4. Return to main menu");
    }

    @Override
    protected boolean handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1:
                official.addCitizen(sc);
                break;
            case 2:
                official.deleteCitizen(sc);
                break;
            case 3:
                official.changeCitizenAddress(sc);
                break;
            case 4:
                return false;
            default:
                System.out.println("Wrong input");
        }
        return true;
    }
}
