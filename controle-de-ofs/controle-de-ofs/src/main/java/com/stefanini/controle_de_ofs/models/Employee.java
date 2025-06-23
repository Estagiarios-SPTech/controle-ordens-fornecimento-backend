package com.stefanini.controle_de_ofs.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "rt", referencedColumnName = "id")
    private User rt;

    @ManyToOne
    @JoinColumn(name = "manager", referencedColumnName = "id")
    private User manager;

    private String status;
}
