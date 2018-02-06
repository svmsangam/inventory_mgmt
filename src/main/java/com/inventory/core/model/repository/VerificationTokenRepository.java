package com.inventory.core.model.repository;

import com.inventory.core.model.entity.User;
import com.inventory.core.model.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by dhiraj on 2/6/18.
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken , Long> {

    VerificationToken findById(long verificationId);

    VerificationToken findByToken(String token);

    @Query("select vt.user from VerificationToken vt where vt.token = ?1")
    User findUserByToken(String token);

    VerificationToken findByUser_Id(long userId);

    void deleteByToken(String token);
}
