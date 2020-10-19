package com.example.seq.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDateTime created;
    @Column(nullable = false)
    private LocalDateTime updated;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
}
