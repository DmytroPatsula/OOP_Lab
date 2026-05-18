package org.example.old_ui;

import org.example.repository.UserRepository;
import org.example.business.Citizen;

import java.util.Scanner;

public class MenuCitizen extends BaseMenu {
    private final Citizen citizen;
    private final UserRepository userRepository;

    public MenuCitizen(Citizen citizen, UserRepository userRepository) {
        this.citizen = citizen;
        this.userRepository = userRepository;
    }

    @Override
    protected String getTitle() {
        return "Citizen menu, Hello " + citizen.getName();
    }

    @Override
    protected void printOptions() {
        System.out.println("1. Check taxes");
        System.out.println("2. Pay taxes");
        System.out.println("3. Return to main menu");
    }

    private void handleTaxPayment(Scanner sc) {
        System.out.println("Enter 0 to pay full tax, or enter specific amount:");
        if (sc.hasNextInt()) {
            int amount = sc.nextInt();
            sc.nextLine();
            if (amount == 0) citizen.payTax();
            else citizen.payTax(amount);
            userRepository.updateCitizen(citizen);
        } else {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    @Override
    protected boolean handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1:
                citizen.checkTax();
                break;
            case 2:
                handleTaxPayment(sc);
                break;
            case 3:
                return false;
            default:
                System.out.println("Wrong input");
        }
        return true;
    }
}
