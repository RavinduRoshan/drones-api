package com.musalasoft.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class DroneRegistration {
    private String serialNumber;
    private Model model;
    private float weight;
    private float batteryCapacity;
    private State state;
}
