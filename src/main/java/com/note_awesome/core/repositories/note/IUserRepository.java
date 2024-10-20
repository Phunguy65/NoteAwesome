package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);
}
