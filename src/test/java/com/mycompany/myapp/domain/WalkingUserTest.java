package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WalkingUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalkingUser.class);
        WalkingUser walkingUser1 = new WalkingUser();
        walkingUser1.setId("id1");
        WalkingUser walkingUser2 = new WalkingUser();
        walkingUser2.setId(walkingUser1.getId());
        assertThat(walkingUser1).isEqualTo(walkingUser2);
        walkingUser2.setId("id2");
        assertThat(walkingUser1).isNotEqualTo(walkingUser2);
        walkingUser1.setId(null);
        assertThat(walkingUser1).isNotEqualTo(walkingUser2);
    }
}
