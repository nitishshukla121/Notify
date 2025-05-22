package com.example.Noteify.Model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private boolean pinned;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedate;

    private String category; // Optional: For DSA topics

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    private User user;
}
