package com.example.Noteify.Model;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.Data;

@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date duedate;

    private String priority;

    private String status;

    private Boolean isImportant;

    private String category;

    @Column(name = "created_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reminderAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userid")
    private User user;  // Linked to User entity
}
