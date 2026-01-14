package com.genzsage.genzsage.sage;

import com.genzsage.genzsage.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Import validation constraints
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Sage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Identity cannot be empty")
    @Size(min = 3, max = 50, message = "Identity must be between 3 and 50 characters")
    @Column(nullable = false, unique = true) // Likely needs to be unique
    private String identity;

    @NotBlank(message = "Profile name cannot be empty")
    @Size(min = 2, max = 100, message = "Profile name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String profileName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password hash is required")
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    @Column(nullable = false)
    private String phoneNumber;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Country is required")
    private String country;

    @Builder.Default
    @NotNull(message = "Language preference is required")
    @Min(value = 0, message = "Language preference cannot be negative")
    private Long languagePreference = 0L;

    // --- TIMESTAMPS (Corrected) ---

    @CreationTimestamp(source = SourceType.DB) // Corrected: Use CreationTimestamp here
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

    @UpdateTimestamp(source = SourceType.DB) // Corrected: Use UpdateTimestamp here
    @Column(nullable = false)
    private Instant updatedDate;

    // ------------------------------

    @PastOrPresent(message = "Last login cannot be in the future")
    private Instant lastLogin;

    @Size(max = 2048, message = "Profile picture URL is too long")
    private String profilePicUrl;

    @Builder.Default
    @NotNull
    private Boolean isPrivate = false;

    @Size(max = 500, message = "Bio cannot exceed 500 characters")
    private String bio;

    // Following and Followers relationship
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name="sage_following",
            joinColumns=@JoinColumn(name="follower_id"),
            inverseJoinColumns=@JoinColumn(name = "following_id")
    )
    private Set<Sage> following = new HashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "following")
    private Set<Sage> followers = new HashSet<>();

    // Sage's preferred tags
    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "sage_interests",
            joinColumns = @JoinColumn(name = "sage_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> interests = new HashSet<>();
}