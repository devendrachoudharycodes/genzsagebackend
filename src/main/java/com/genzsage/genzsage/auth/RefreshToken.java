package com.genzsage.genzsage.auth;

import com.genzsage.genzsage.sage.Sage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // CHANGED: Use ManyToOne to allow multiple tokens per user (multi-device support)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Sage user;

    @Column(nullable = false, unique = true)
    private String token;

    private String deviceId;
    private String otherMeta;

    @Column(nullable = false)
    private Instant expiryDate;
}