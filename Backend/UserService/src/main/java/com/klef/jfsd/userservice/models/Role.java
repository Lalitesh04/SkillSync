package com.klef.jfsd.userservice.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @Enumerated(EnumType.STRING)
    private RoleType type;

    @Column(nullable = false)
    private String name;


}