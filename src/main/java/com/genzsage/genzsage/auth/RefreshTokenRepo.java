package com.genzsage.genzsage.auth;


import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    public Optional<RefreshToken> findByToken(String token);
}
