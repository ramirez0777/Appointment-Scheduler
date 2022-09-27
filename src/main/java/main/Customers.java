package main;

import c195.c195.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customers {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public static void addCustomer(Customer newCustomer){
        allCustomers.add(newCustomer);
        System.out.println("Size: " + allCustomers.size());
    }

    public static void updateCustomer(int index, Customer updatedCustomer){}

    public static boolean deleteCustomer(Customer selectedCustomer){
        return false;
    }

    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }
}
