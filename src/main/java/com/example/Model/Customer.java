package com.example.Model;

import com.example.DAO.CustomerDAO;

public class Customer
{
    private String customerName;
    private String customerLicense;

    public Customer(String customerName, String customerLicense)
    {
        this.customerName = customerName;
        this.customerLicense = customerLicense;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public String getCustomerLicense()
    {
        return customerLicense;
    }

    public void saveCustomerToDatabase()
    {
        new CustomerDAO().createCustomerRecord(customerName,customerLicense);
    }

}
