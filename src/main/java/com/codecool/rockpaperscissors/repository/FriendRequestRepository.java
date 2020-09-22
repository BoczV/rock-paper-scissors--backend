package com.codecool.rockpaperscissors.repository;

import com.codecool.rockpaperscissors.model.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Optional<FriendRequest> findBySenderAndReceiver(String sender, String receiver);
    List<FriendRequest> findBySender(String sender);
    List<FriendRequest> findByReceiver(String receiver);
}
