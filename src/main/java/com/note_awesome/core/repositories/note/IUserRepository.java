package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUsername(String username);

    boolean existsByUsername(@NonNull String username);

    Optional<User> findByUsername(String username);

}
