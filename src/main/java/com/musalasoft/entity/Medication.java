package com.musalasoft.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@Entity
public class Medication implements Serializable {
    @Id
    private String code;
    private String name;
    private float weight;
    private String image;

    public Medication() {
    }

    public Medication(String code, String name, float weight, String image) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.image = image;
    }
}
