package com.kage.entity;

import com.kage.enums.DayStatus;
import com.kage.enums.Mood;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "day_entry",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "date"})
        },
        indexes = {
                @Index(name = "idx_day_entry_user_date", columnList = "user_id,date")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DayEntry extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Mood mood;

    @Column
    private Integer dayScore; // optional (user-defined or computed)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayStatus dayStatus;
}
