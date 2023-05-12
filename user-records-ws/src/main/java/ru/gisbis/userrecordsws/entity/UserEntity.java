package ru.gisbis.userrecordsws.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "users")
public class UserEntity {

    private Long id;
    private String name;
    private String password;
}
