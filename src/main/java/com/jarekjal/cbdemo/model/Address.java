package com.jarekjal.cbdemo.model;

import lombok.*;

@Value
@Builder(toBuilder = true)
public class Address {

    String street;
    String city;
    String zipCode;
}
