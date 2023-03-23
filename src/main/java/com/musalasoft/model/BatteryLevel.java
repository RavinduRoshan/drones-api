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
public class BatteryLevel implements Serializable {
    private String serialNumber;
    private float batteryCapacity;

    public BatteryLevel(String serialNumber, float batteryCapacity) {
        this.serialNumber = serialNumber;
        this.batteryCapacity = batteryCapacity;
    }
}
