package com.fiap.GastroHub.modules.users.infra.orm.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//@Entity
//@Getter
//@Setter
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String username;
//    private String email;
//    private String password;
//    private String name;
//    private String address;
//    private Date createdAt;
//    private Date lastUpdatedAt;
//}

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;
}
