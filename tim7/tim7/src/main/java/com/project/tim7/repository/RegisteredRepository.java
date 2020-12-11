package com.project.tim7.repository;

import com.project.tim7.model.Newsletter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tim7.model.Registered;

import java.util.List;

@Repository
public interface RegisteredRepository extends JpaRepository<Registered, Integer> {
    Long countByEmailOrUsername(String email, String username);
    Registered findByEmail(String email);
    Registered findByUsernameOrEmail(String username, String email);

    @Query("SELECT r FROM Registered r JOIN r.subscribedCulturalOffers co WHERE co.id = ?1")
    List<Registered> findRegisteredForSubscribedCulturalOffers(int idCulturalOffer);
}
