package org.example.ui;

import org.example.business.Citizen;
import org.example.business.Official;
import org.example.business.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@Component
public class AppFacade {
    private final UserRepository userRepository;
    private final Scanner sc = new Scanner(System.in);

    public AppFacade(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void start(){
        while (true) {
            System.out.println("Main menu");
            System.out.println("1. Enter as citizen");
            System.out.println("2. Enter as official");
            System.out.println("3. Print all User info");
            System.out.println("4. Simulate one month");
            System.out.println("5. Exit program");
            System.out.print("Your choice: ");

            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();

                int inputId;
                User foundUser;

                switch (choice) {
                    case 1:
                        System.out.print("Enter your ID:");
                        inputId = sc.nextInt();
                        sc.nextLine();

                        foundUser = userRepository.findUserById(inputId);

                        if (foundUser instanceof Citizen) {
                            Menu menu = new MenuCitizen((Citizen) foundUser, userRepository);
                            menu.showMenu(sc);
                        } else {
                            System.out.println("Error, no citizen found with this ID");
                            sc.nextLine();
                        }
                        break;

                    case 2:
                        System.out.print("Enter your ID: ");
                        if (sc.hasNextInt()) {
                            inputId = sc.nextInt();
                            sc.nextLine();

                            foundUser = userRepository.findUserById(inputId);

                            if (foundUser instanceof Official) {
                                Menu menu = new MenuOfficial((Official) foundUser);
                                menu.showMenu(sc);
                            } else {
                                System.out.println("Error: Official not found.");
                            }
                        } else {
                            System.out.println("Invalid ID.");
                            sc.nextLine();
                        }
                        break;

                    case 3:
                        System.out.println("Name---ID---Address and Taxes/Department");

                        List<User> allUsers = userRepository.getAllUsers();
                        allUsers.sort(new Comparator<>() {
                            @Override
                            public int compare(User u1, User u2) {
                                return Integer.compare(u1.getId(), u2.getId());
                            }
                        });

                        for (User u : allUsers) {
                            System.out.println(u.getName() + " - " + u.getId() + " - " + u.getUserInfo());
                        }
                        break;

                    case 4:
                        allUsers = userRepository.getAllUsers();
                        for (User u : allUsers) {
                            if (u instanceof Citizen citizen) {
                                citizen.monthSimulation();
                                userRepository.updateCitizen(citizen);
                            }
                        }
                        System.out.println("1 month passed. Taxes added for all citizens.");
                        break;
                    case 5:
                        System.out.println("Exiting program");
                        return;

                    default:
                        System.out.println("Enter number 1-5");
                }
            } else {
                System.out.println("Wrong input");
                sc.nextLine();
            }
        }
    }
}
