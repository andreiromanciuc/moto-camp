package org.fasttrackit.motocamp.service;


import org.fasttrackit.motocamp.domain.Profile;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.exception.ResourceNotFoundException;
import org.fasttrackit.motocamp.persistance.ProfileRepository;
import org.fasttrackit.motocamp.transfer.profile.CreateProfile;
import org.fasttrackit.motocamp.transfer.profile.GetProfileRequest;
import org.fasttrackit.motocamp.domain.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProfileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private  final ProfileRepository profileRepository;
    private final UserService userService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserService userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }


    public Profile getProfile(long id) {
        LOGGER.info("Retrieving profile {}", id);
        return profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile " + id + " not found"));
    }

    public Page<Profile> getProfilesBySearching(GetProfileRequest request, Pageable pageable) {
        LOGGER.info("Retrieving profiles by searching {}", request);
        if (request.getNamePartialName() == null) {
            profileRepository.findByUserUsername(request.getPartialUserName(), pageable);
        }
        profileRepository.findByFullNameContaining(request.getNamePartialName(), pageable);

        return profileRepository.findAll(pageable);
    }

    public Profile updateProfile(long id, CreateProfile request) {
        LOGGER.info("Updating profile {}: {}",id, request);
        Profile profile = getProfile(id);
        BeanUtils.copyProperties(request, profile);
        return profileRepository.save(profile);
    }

    @Transactional
    public Profile addProfileToUser(CreateProfile request) {
        LOGGER.info("Adding profile to user {}", request);
        Profile profile = profileRepository.findById(request.getUserId())
                .orElse(new Profile());

        profile.setFullName(request.getFullName());
        profile.setImageUrl(request.getImageUrl());

        if (profile.getUser() == null) {
            User user = userService.getUser(request.getUserId());
            profile.setUser(user);
            profile.setUserName(user.getUsername());
        }
        return profileRepository.save(profile);
    }

    public void deleteProfile(long id) {
        LOGGER.info("Deleting profile {}", id);

        profileRepository.deleteById(id);

    }
}
