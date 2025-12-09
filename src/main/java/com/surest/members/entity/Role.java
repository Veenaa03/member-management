package com.surest.members.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @Column(columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(unique=true, nullable=false)
    private String name;

    // getters/setters
    public UUID getId(){ return id; }
    public void setId(UUID id){ this.id = id; }
    public String getName(){ return name; }
    public void setName(String name){ this.name = name; }
}
