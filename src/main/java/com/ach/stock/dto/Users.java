package com.ach.stock.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "users")
public class Users {

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserWallet userWallet;

    @Id
    @Column(name="USERID")
    private String userId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NICKNAME")
    private String nickName;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role; // USER, ADMIN

    @Version
    private Integer version;
}
