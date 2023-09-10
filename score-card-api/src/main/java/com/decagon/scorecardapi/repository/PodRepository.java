package com.decagon.scorecardapi.repository;

import com.decagon.scorecardapi.enums.Role;
import com.decagon.scorecardapi.model.Pod;
import com.decagon.scorecardapi.model.Stack;
import com.decagon.scorecardapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface PodRepository extends JpaRepository<Pod, Long> {

    boolean existsByPodName(String podName);

    List<Pod> findAllByStack(Stack stack);

}

