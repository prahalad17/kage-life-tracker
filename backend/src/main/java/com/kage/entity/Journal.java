package com.kage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "journal",
        indexes = {
                @Index(name = "idx_journal_user", columnList = "user_id"),
                @Index(name = "idx_journal_day", columnList = "day_entry_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Journal extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_entry_id")
    private DayEntry dayEntry;

    @Column(length = 200)
    private String title; // optional

    @Lob
    @Column(nullable = false)
    private String content;
}
