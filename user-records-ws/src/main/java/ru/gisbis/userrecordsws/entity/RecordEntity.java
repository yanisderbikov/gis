package ru.gisbis.userrecordsws.entity;


import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name="record")
public class RecordEntity {
    @Column("id")
    private Long id;
    @Column("user_id")
    public Long userId;
    @Column("text")
    private String text;

}
