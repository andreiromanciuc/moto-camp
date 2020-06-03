package org.fasttrackit.motocamp.steps;

import org.fasttrackit.motocamp.domain.Profile;
import org.fasttrackit.motocamp.domain.User;
import org.fasttrackit.motocamp.service.ProfileService;
import org.fasttrackit.motocamp.transfer.profile.CreateProfile;
import org.fasttrackit.motocamp.domain.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Component
public class ProfileTestSteps {

    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserTestSteps userTestSteps;

    public Profile createProfile() {
        User user = userTestSteps.createUser();

        CreateProfile createProfile = new CreateProfile();
        createProfile.setUserId(user.getId());
        createProfile.setFullName("Andrei Romanciuc");
        createProfile.setImageUrl("21313213");

        Profile profile = profileService.addProfileToUser(createProfile);

        assertThat(profile, notNullValue());
        assertThat(profile.getImageUrl(), is(createProfile.getImageUrl()));
        assertThat(profile.getFullName(), is(createProfile.getFullName()));
        assertThat(profile.getId(), is(createProfile.getUserId()));

        return profile;
    }
}
