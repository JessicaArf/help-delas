package com.elastech.helpdelas.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("client") // Definindo o valor discriminador para esta classe
public class ClientModel extends UserModel {
}
