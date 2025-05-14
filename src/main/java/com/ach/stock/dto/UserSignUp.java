package com.ach.stock.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class UserSignUp {

    private String userId;
    private String email;
    private String password;
    private String nickName;
    private BigDecimal balance;
    private String role;

}
