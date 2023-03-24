package com.musalasoft.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@Entity
public class DroneBatteryAudit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String serialNumber;
    private float batteryCapacity;
    @CreationTimestamp
    private LocalDateTime createDateTime;

    public DroneBatteryAudit(String serialNumber, float batteryCapacity) {
        this.serialNumber = serialNumber;
        this.batteryCapacity = batteryCapacity;
    }

    public DroneBatteryAudit() {
    }
}
