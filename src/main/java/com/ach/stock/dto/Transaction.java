package com.ach.stock.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id @SequenceGenerator(name = "trans_seq_gen", sequenceName = "trans_seq", allocationSize = 1)
    private int id;
    private int orderId;
    private int executedPrice;
    private Date executedDate;
}
