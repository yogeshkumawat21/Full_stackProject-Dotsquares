package com.App.Yogesh.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
}
