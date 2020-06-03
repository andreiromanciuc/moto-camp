package org.fasttrackit.motocamp;

import org.fasttrackit.motocamp.domain.Profile;
import org.fasttrackit.motocamp.service.ProfileService;
import org.fasttrackit.motocamp.steps.ProfileTestSteps;
import org.fasttrackit.motocamp.steps.UserTestSteps;
import org.fasttrackit.motocamp.transfer.profile.CreateProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class ProfileIntegrationTests {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private UserTestSteps userTestSteps;
    @Autowired
    private ProfileTestSteps profileTestSteps;


    @Test
    void addProfileToUser_whenNewProfile_thenProfileIsCreated() {
        profileTestSteps.createProfile();
    }

    @Test
    void getProfile_whenProfileExist_thenReturnProfile() {
        Profile profile = profileTestSteps.createProfile();

        Profile response = profileService.getProfile(profile.getId());

        assertThat(response, notNullValue());
        assertThat(response.getId(), is(profile.getId()));
        assertThat(response.getImageUrl(), is(profile.getImageUrl()));
        assertThat(response.getFullName(), is(profile.getFullName()));
    }


    @Test
    void updateProfile_whenProfileExist_thenReturnProfile() {
        Profile profile = profileTestSteps.createProfile();

        CreateProfile request = new CreateProfile();
        request.setImageUrl(profile.getImageUrl() + "update");
        request.setFullName(profile.getFullName() + "update");

        profileService.updateProfile(profile.getId(), request);

        assertThat(request, notNullValue());
        assertThat(request.getImageUrl(), not(profile.getImageUrl()));
        assertThat(request.getFullName(), not(profile.getFullName()));
    }
}
