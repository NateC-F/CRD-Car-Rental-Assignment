package com.example.Model;

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
}
