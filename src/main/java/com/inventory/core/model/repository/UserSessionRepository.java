package com.inventory.core.model.repository;

import java.util.Date;
import java.util.List;

import com.inventory.core.model.entity.User;
import com.inventory.core.model.entity.UserSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserSessionRepository extends CrudRepository<UserSession, String> {

    @Query("select s from UserSession s WHERE s.sessionId = ?1")
    UserSession findBySessionId(String sessionId);

    @Query("select s from UserSession s WHERE s.user.id = ?1 and s.expired = false")
    List<UserSession> getUserSessions(Long userId);

    @Query("select s from UserSession s WHERE s.user.id = ?1")
    List<UserSession> getUserSessionsIncludingExpired(Long userId);

    //@Query("select distinct(s.user) from usersession s WHERE s.expired=false")
    @Query("select s.user from UserSession s WHERE s.expired=false")
    Page<User> findActiveUsers(Pageable page);

    @Query("select s from UserSession s WHERE s.expired=false")
    Page<UserSession> findActiveSessions(Pageable page);

    @Query("select count(s) from UserSession s WHERE s.expired=false")
    long countActiveSessions();

    @Query("select s from UserSession s WHERE s.sessionId = ?1 and s.expired = false")
    UserSession findByActiveSessionId(String sessionId);

    @Transactional
    @Modifying
    @Query("delete from UserSession s where s.expired=true or s.lastRequest < ?1")
    void deleteExpiredSessions(Date lastRefreshed);

    @Transactional
    @Modifying
    @Query("update UserSession s set s.lastRequest = now() where s.sessionId=?1")
    void refreshSession(String sessionId);

    @Query("select s from UserSession s where s.expired = false")
    List<UserSession> findActiveUser();

}
