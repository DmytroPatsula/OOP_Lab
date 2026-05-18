package org.example.ui;

import org.example.business.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class Controller {

    private final AppFacade appFacade;

    public Controller(AppFacade appFacade) {
        this.appFacade = appFacade;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return appFacade.getAllUsers();
    }

    @PostMapping("/simulate")
    public String simulateMonth() {
        return appFacade.simulateMonth();
    }

    @PostMapping("/citizens/pay")
    public String payTax(@RequestParam int id, @RequestParam int amount) {
        return appFacade.payCitizenTax(id, amount);
    }

    @PostMapping("/citizens/add")
    public String addCitizen(@RequestParam String name, @RequestParam int id, @RequestParam String address, @RequestParam int monthlyDebt) {
        return appFacade.addCitizen(name, id, address, monthlyDebt);
    }

    @PutMapping("/citizens/address")
    public String changeAddress(@RequestParam int id, @RequestParam String newAddress) {
        return appFacade.changeAddress(id, newAddress);
    }

    @DeleteMapping("/users/delete")
    public String deleteUser(@RequestParam int id) {
        return appFacade.deleteUser(id);
    }
}