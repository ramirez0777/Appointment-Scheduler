package main;

import c195.c195.Customer;
import helper.Queries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static void addCustomer(Customer newCustomer){
        allCustomers.add(newCustomer);
        System.out.println("Size: " + allCustomers.size() + " ID: " + newCustomer.getCustomerId());
    }

    public static void updateCustomer(int index, Customer updatedCustomer){
        allCustomers.set(index, updatedCustomer);
    }

    public static boolean deleteCustomer(Customer selectedCustomer){
        if(selectedCustomer != null && selectedCustomer.getAppointments().size() <= 0) {
            allCustomers.remove(selectedCustomer);
            return true;
        }
        return false;

    }

    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }

    public static void clearCustomers() {
        allCustomers.clear();
    }
}
