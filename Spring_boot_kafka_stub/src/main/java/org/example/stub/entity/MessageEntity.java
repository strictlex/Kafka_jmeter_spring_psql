package org.example.stub.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="messages")
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "msguuid", nullable = false,length = 36)
    private String msgUuid;

    @Column(name = "head", nullable = false)
    private  Boolean head;

    @Column(name = "timerq", nullable = false)
    private Long timeRq;
}













