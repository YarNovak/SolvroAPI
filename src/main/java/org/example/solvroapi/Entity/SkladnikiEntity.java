package org.example.solvroapi.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "ingredients")

public class SkladnikiEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private boolean alcoholic;
    private String photoUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
