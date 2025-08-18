package com.team3.api_collab_dev.repository;

import com.team3.api_collab_dev.entity.Project;
import com.team3.api_collab_dev.entity.User;
import com.team3.api_collab_dev.enumType.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectRepo extends CrudRepository<Project, Long> {

    List<Project> findByStatus(Status status);

    List<Project> findByAuthor(User author);

    long countByAuthor(User author);

}
