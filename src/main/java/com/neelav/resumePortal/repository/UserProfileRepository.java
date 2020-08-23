package com.neelav.resumePortal.repository;

import com.neelav.resumePortal.model.User;
import com.neelav.resumePortal.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {

    public Optional<UserProfile> findByUserName(String userName);
}
