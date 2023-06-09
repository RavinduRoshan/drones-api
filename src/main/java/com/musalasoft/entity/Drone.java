package com.musalasoft.entity;

import com.musalasoft.model.Model;
import com.musalasoft.model.State;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@Entity
public class Drone implements Serializable {
    @Id
    private String serialNumber;
    private Model model;
    private float weightLimit;
    private float batteryCapacity;
    private State state;

    @OneToMany(targetEntity = Medication.class)
    private List<Medication> medications;

    public Drone(String serialNumber, Model model, float weightLimit, float batteryCapacity, State state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weightLimit = weightLimit;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public Drone() {
    }
}
