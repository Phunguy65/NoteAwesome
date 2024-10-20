package com.note_awesome.core.repositories.note;

import com.note_awesome.core.entities.note.ProfileSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfileSettingRepository extends JpaRepository<ProfileSetting, Long> {
}