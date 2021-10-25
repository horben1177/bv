package com.betvictor.task2.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "BV_HISTORY")
public class Message {

    @Id
    private Long id;

    private String message;

    private Date creationDate;

}
