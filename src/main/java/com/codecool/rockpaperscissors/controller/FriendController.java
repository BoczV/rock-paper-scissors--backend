package com.codecool.rockpaperscissors.controller;

import com.codecool.rockpaperscissors.model.FriendRequest;
import com.codecool.rockpaperscissors.model.Player;
import com.codecool.rockpaperscissors.repository.FriendRequestRepository;
import com.codecool.rockpaperscissors.repository.PlayerRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friend")
@CrossOrigin("*")
public class FriendController {

    @Autowired
    FriendRequestRepository friendRequestRepository;

    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/add")
    public void addFriend(@RequestBody Map<String, Object> data) throws JSONException {
        String friendUserName = data.get("newFriend").toString();
        String myUserName = data.get("me").toString();
        Player me = playerRepository.findByUserName(myUserName).get();
        Player myNewFriend = playerRepository.findByUserName(friendUserName).get();
        if(me.getFriends() == null){
            me.setFriends(new LinkedList<>());
        }
        if(myNewFriend.getFriends() == null){
            myNewFriend.setFriends(new LinkedList<>());
        }
        me.getFriends().add(friendUserName);
        myNewFriend.getFriends().add(myUserName);
        playerRepository.save(me);
        playerRepository.save(myNewFriend);
    }

    @PostMapping("/send-request")
    public void sendRequest(@RequestBody Map<String, Object> data){
        String friendUserName = data.get("newFriend").toString();
        String myUserName = data.get("me").toString();
        FriendRequest friendRequest = FriendRequest.builder().sender(myUserName).receiver(friendUserName).build();
        friendRequestRepository.save(friendRequest);
    }

    @DeleteMapping("/delete/{me}/{myFriend}")
    public void deleteFriend(@PathVariable("me") String me, @PathVariable("myFriend") String myFriend){
        Player meAsPlayer = playerRepository.findByUserName(me).get();
        Player myFriendAsPlayer = playerRepository.findByUserName(myFriend).get();
        meAsPlayer.getFriends().remove(myFriendAsPlayer.getUserName());
        myFriendAsPlayer.getFriends().remove(meAsPlayer.getUserName());
        playerRepository.save(meAsPlayer);
        playerRepository.save(myFriendAsPlayer);
    }

    @DeleteMapping("/delete/my-created-request/{me}/{newFriend}")
    public void deleteCreatedRequest(@PathVariable("me") String me, @PathVariable("newFriend") String newFriend){
        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(me, newFriend).get();
        friendRequestRepository.delete(friendRequest);
    }

    @DeleteMapping("/delete/my-received-request/{me}/{newFriend}")
    public void deleteReceivedRequest(@PathVariable("me") String me, @PathVariable("newFriend") String newFriend){
        FriendRequest friendRequest = friendRequestRepository.findBySenderAndReceiver(newFriend, me).get();
        friendRequestRepository.delete(friendRequest);
    }

    @GetMapping("/get-my-friends/{username}")
    public List<String> getMyFriends(@PathVariable("username") String username) throws JSONException {
        Player me = playerRepository.findByUserName(username).get();
        return me.getFriends();
    }

    @GetMapping("/get-my-sent-requests/{username}")
    public List<String> getMySentRequests(@PathVariable("username") String username) throws JSONException {
        List<FriendRequest> sentRequestsByMe = friendRequestRepository.findBySender(username);
        List<String> receiversNames = new ArrayList<>();
        for (FriendRequest friendRequest : sentRequestsByMe) {
            receiversNames.add(friendRequest.getReceiver());
        }
        return receiversNames;
    }

    @GetMapping("/get-my-received-requests/{username}")
    public List<String> getMyReceivedRequests(@PathVariable("username") String username){
        List<FriendRequest> receivedRequests = friendRequestRepository.findByReceiver(username);
        List<String> sendersNames = new ArrayList<>();
        for (FriendRequest receivedRequest : receivedRequests) {
            sendersNames.add(receivedRequest.getSender());
        }
        return sendersNames;
    }
}
