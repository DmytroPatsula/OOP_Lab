package org.example.business;

import org.example.repository.UserRepository;

import java.util.Scanner;

public class Official extends User {
    private final String department;
    private final UserRepository userRepository;

    public Official(String name, int id, String department, UserRepository userRepository) {
        super(name, id);
        this.department = department;
        this.userRepository = userRepository;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getUserInfo() {
        return "Department: " + department;
    }

    public void addCitizen(Scanner sc) {
        System.out.println("Register new citizen ");

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter ID: ");
        int id;
        if (sc.hasNextInt()) {
            id = sc.nextInt();
            sc.nextLine();
        } else {
            System.out.println("Invalid ID");
            sc.nextLine();
            return;
        }

        if(userRepository.ifExists(id)) return;

        System.out.print("Enter address: ");
        String address = sc.nextLine();

        System.out.print("Enter amount of tax per month: ");
        int monthTax;
        if (sc.hasNextInt()) {
            monthTax = sc.nextInt();
            sc.nextLine();
        } else {
            System.out.println("Invalid input");
            sc.nextLine();
            return;
        }

        userRepository.addUser(new Citizen(name, id, address, monthTax));
        System.out.println("Citizen " + name + " successfully registered!");
    }

    public void deleteCitizen(Scanner sc) {
        System.out.println("Delete citizen");
        System.out.print("Enter the ID of the citizen: ");

        if (sc.hasNextInt()) {
            int searchId = sc.nextInt();
            sc.nextLine();

            User targetUser = userRepository.findUserById(searchId);
            if (targetUser != null) {
                if (targetUser instanceof Citizen targetCitizen) {
                    System.out.println("Do you want to delete citizen - " + targetCitizen.getName() + " ?");
                    System.out.println("Enter: 1 - Yes; 2 - No");
                    if (sc.hasNextInt()) {
                        int choice = sc.nextInt();
                        sc.nextLine();

                        switch (choice) {
                            case 1:
                                userRepository.deleteUser(searchId);
                                System.out.println("User " + targetCitizen.getName() + " successfully deleted");
                                break;
                            case 2:
                                System.out.println("Going back to menu");
                                break;
                            default:
                                System.out.println("Wrong input");
                        }
                    } else {
                        System.out.println("Invalid input.");
                        sc.nextLine();
                    }
                } else {
                    System.out.println("Error: User with ID " + searchId + " is an Official, not a Citizen.");
                }
            }else {
                System.out.println("User with ID " + searchId + " not found.");
            }
        } else {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    public void changeCitizenAddress(Scanner sc) {
        System.out.println("Change citizen address");
        System.out.print("Enter the ID of the citizen: ");

        if (sc.hasNextInt()) {
            int searchId = sc.nextInt();
            sc.nextLine();

            User targetUser = userRepository.findUserById(searchId);

            if (targetUser != null) {
                if (targetUser instanceof Citizen targetCitizen) {
                    System.out.println("Current " + targetCitizen.getName() + " address: " + targetCitizen.getAddress());
                        System.out.print("Enter new address: ");
                        String newAddress = sc.nextLine();

                    targetCitizen.setAddress(newAddress);
                    userRepository.updateCitizen(targetCitizen);
                    System.out.println("Address successfully saved in database!");
                    } else {
                    System.out.println("Error: User with ID " + searchId + " is an Official, not a Citizen.");
                }
            } else {
                System.out.println("User with ID " + searchId + " not found.");
            }
        } else {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }
}

