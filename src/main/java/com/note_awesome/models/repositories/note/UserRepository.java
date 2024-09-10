package com.note_awesome.models.repositories.note;

import com.note_awesome.models.entities.note.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
