package com.project.projectManagementSystem.repository;

import com.project.projectManagementSystem.domain.entity.Project;
import com.project.projectManagementSystem.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContainingAndTeamContains(String partialName, User user);
    /*
    List<Project> findByOwner(User owner);

    @Query("SELECT p FROM Project p join p.team t where t =: user")
    List<Project> findProjectByTeam(@Param("user") User user);
    */
    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
