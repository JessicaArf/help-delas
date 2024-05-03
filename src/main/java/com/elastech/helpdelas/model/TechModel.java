package com.elastech.helpdelas.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("tech") // Definindo o valor discriminador para esta classe
public class TechModel extends UserModel{
}
