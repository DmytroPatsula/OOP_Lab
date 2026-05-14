package org.example.ui;

import java.util.Scanner;

public abstract class BaseMenu implements Menu {

    @Override
    public void showMenu(Scanner sc) {
        while (true) {
            System.out.println("\n" + getTitle());
            printOptions();
            System.out.print("Your choice: ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();

                if (!handleChoice(choice, sc)) {
                    return;
                }
            } else {
                System.out.println("Wrong input, please enter a number.");
                sc.nextLine();
            }
        }
    }


    protected abstract String getTitle();
    protected abstract void printOptions();
    protected abstract boolean handleChoice(int choice, Scanner sc);
}
