package org.example.staticspecservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "static_parameter")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaticSpecParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String paramName;

    String stringValue;

    Double doubleValue;

    Boolean booleanValue;

    ValueType valueType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    StaticSpecGroup group;

}
