package org.fasttrackit.motocamp.web;


import org.fasttrackit.motocamp.domain.Profile;
import org.fasttrackit.motocamp.service.MotorService;
import org.fasttrackit.motocamp.service.ProfileService;
import org.fasttrackit.motocamp.service.UserService;
import org.fasttrackit.motocamp.transfer.profile.CreateProfile;
import org.fasttrackit.motocamp.transfer.profile.GetProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/signup/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final MotorService motorService;
    private final UserService userService;

    @Autowired
    public ProfileController(ProfileService profileService, MotorService motorService, UserService userService) {
        this.profileService = profileService;
        this.motorService = motorService;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<Profile> addProfileToUser(@Valid @RequestBody CreateProfile addProfile) {
        Profile profile = profileService.addProfileToUser(addProfile);
        return new ResponseEntity<>(profile, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable long id) {
        Profile profile = profileService.getProfile(id);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Profile>> getProfilesBySearching(GetProfileRequest request, Pageable pageable) {
        Page<Profile> profilesBySearching = profileService.getProfilesBySearching(request, pageable);
        return new ResponseEntity<>(profilesBySearching, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfileAndUser(@PathVariable long id) {
        profileService.deleteProfile(id);
        motorService.deleteMotorcycle(id);
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
