package org.example.business;

public class Citizen extends User {
    private String address;

    private int currentDebt;
    private final int monthlyDebt;
    private final Account account;


    public Citizen(String name, int id, String address, int monthlyDebt) {
        super(name, id);
        this.address = address;
        this.currentDebt = 0;
        this.monthlyDebt = monthlyDebt;
        this.account = new Account();
    }

    class Account{
        void monthlyTax(){
            currentDebt += monthlyDebt;
        }
    }


    public void setAddress(String newAddress) {
        this.address = newAddress;
        System.out.println("Address updated to: " + this.address);
    }

    public void setCurrentDebt(int currentDebt) {
        this.currentDebt = currentDebt;
    }

    public int getCurrentDebt() {
        return currentDebt;
    }

    public int getMonthlyDebt() {
        return monthlyDebt;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String getUserInfo() {
        String taxStatus;
        if (currentDebt >0){
            taxStatus =" -- Taxes: Debt - " + currentDebt;
        }
        else{
            taxStatus =" -- Taxes: Paid";
        }
        return "Address: " + address + taxStatus;
    }

    public void monthSimulation(){
        account.monthlyTax();
    }



    public void checkTax() {
        if (currentDebt >0) {
            System.out.println(getName() + " You have a debt: " + currentDebt);
        } else {
            System.out.println(getName() + " Taxes are paid");
        }
    }

    public void payTax() {
        if (currentDebt <=0) {
            System.out.println("Already paid");
        } else {
            currentDebt = 0;
            System.out.println("Taxes are successfully paid");
        }
    }
    public void payTax(int amount) {
        if (currentDebt <=0) {
            System.out.println("Already paid");
        } else if (amount <= 0) {
            System.out.println("Amount must be greater than 0");
        }else {
            System.out.println("You paid " + amount + " bucks");
            if(amount> currentDebt){
                int change = amount - currentDebt;
                System.out.println("Here is your change: " + change);
                currentDebt = 0;
            }
            else{
                currentDebt -=amount;
                System.out.println("Your debt now is: " + currentDebt);
            }
        }
    }
}
