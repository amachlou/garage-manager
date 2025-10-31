package com.amachlou.garages_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

@Entity(name = "opening_hours")
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"garage_id", "dayOfWeek"})
)
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class OpeningHour implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "garage_id")
    private Garage garage;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @JsonIgnore
    @OneToMany(mappedBy = "openingHour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningTime> openingTimes;

}
