package org.airTribe.taskTrackingSystem.entity;


import lombok.*;
import java.util.*;
import jakarta.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "appuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;

    @Column(length = 60)
    private String password;
    private boolean isEnabled;
}

