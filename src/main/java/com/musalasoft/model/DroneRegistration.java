package com.musalasoft.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class DroneRegistration implements Serializable {
    private String serialNumber;
    private Model model;
    private float weightLimit;
    private float batteryCapacity;
    private State state;
}
