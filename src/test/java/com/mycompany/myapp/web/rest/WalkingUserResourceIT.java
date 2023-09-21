//package com.mycompany.myapp.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.mycompany.myapp.IntegrationTest;
//import com.mycompany.myapp.domain.WalkingUser;
//import com.mycompany.myapp.repository.WalkingUserRepository;
//import java.util.List;
//import java.util.UUID;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//
///**
// * Integration tests for the {@link WalkingUserResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class WalkingUserResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final Long DEFAULT_STEP = 1L;
//    private static final Long UPDATED_STEP = 2L;
//
//    private static final String ENTITY_API_URL = "/api/walking-users";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    @Autowired
//    private WalkingUserRepository walkingUserRepository;
//
//    @Autowired
//    private MockMvc restWalkingUserMockMvc;
//
//    private WalkingUser walkingUser;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WalkingUser createEntity() {
//        WalkingUser walkingUser = new WalkingUser().name(DEFAULT_NAME).step(DEFAULT_STEP);
//        return walkingUser;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static WalkingUser createUpdatedEntity() {
//        WalkingUser walkingUser = new WalkingUser().name(UPDATED_NAME).step(UPDATED_STEP);
//        return walkingUser;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        walkingUserRepository.deleteAll();
//        walkingUser = createEntity();
//    }
//
//    @Test
//    void createWalkingUser() throws Exception {
//        int databaseSizeBeforeCreate = walkingUserRepository.findAll().size();
//        // Create the WalkingUser
//        restWalkingUserMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walkingUser)))
//            .andExpect(status().isCreated());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeCreate + 1);
//        WalkingUser testWalkingUser = walkingUserList.get(walkingUserList.size() - 1);
//        assertThat(testWalkingUser.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testWalkingUser.getStep()).isEqualTo(DEFAULT_STEP);
//    }
//
//    @Test
//    void createWalkingUserWithExistingId() throws Exception {
//        // Create the WalkingUser with an existing ID
//        walkingUser.setId("existing_id");
//
//        int databaseSizeBeforeCreate = walkingUserRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restWalkingUserMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walkingUser)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    void getAllWalkingUsers() throws Exception {
//        // Initialize the database
//        walkingUserRepository.save(walkingUser);
//
//        // Get all the walkingUserList
//        restWalkingUserMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(walkingUser.getId())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].step").value(hasItem(DEFAULT_STEP.intValue())));
//    }
//
//    @Test
//    void getWalkingUser() throws Exception {
//        // Initialize the database
//        walkingUserRepository.save(walkingUser);
//
//        // Get the walkingUser
//        restWalkingUserMockMvc
//            .perform(get(ENTITY_API_URL_ID, walkingUser.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(walkingUser.getId()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.step").value(DEFAULT_STEP.intValue()));
//    }
//
//    @Test
//    void getNonExistingWalkingUser() throws Exception {
//        // Get the walkingUser
//        restWalkingUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    void putExistingWalkingUser() throws Exception {
//        // Initialize the database
//        walkingUserRepository.save(walkingUser);
//
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//
//        // Update the walkingUser
//        WalkingUser updatedWalkingUser = walkingUserRepository.findById(walkingUser.getId()).orElseThrow();
//        updatedWalkingUser.name(UPDATED_NAME).step(UPDATED_STEP);
//
//        restWalkingUserMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedWalkingUser.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedWalkingUser))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//        WalkingUser testWalkingUser = walkingUserList.get(walkingUserList.size() - 1);
//        assertThat(testWalkingUser.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testWalkingUser.getStep()).isEqualTo(UPDATED_STEP);
//    }
//
//    @Test
//    void putNonExistingWalkingUser() throws Exception {
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//        walkingUser.setId(UUID.randomUUID().toString());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWalkingUserMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, walkingUser.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(walkingUser))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    void putWithIdMismatchWalkingUser() throws Exception {
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//        walkingUser.setId(UUID.randomUUID().toString());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWalkingUserMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(walkingUser))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    void putWithMissingIdPathParamWalkingUser() throws Exception {
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//        walkingUser.setId(UUID.randomUUID().toString());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWalkingUserMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walkingUser)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    void partialUpdateWalkingUserWithPatch() throws Exception {
//        // Initialize the database
//        walkingUserRepository.save(walkingUser);
//
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//
//        // Update the walkingUser using partial update
//        WalkingUser partialUpdatedWalkingUser = new WalkingUser();
//        partialUpdatedWalkingUser.setId(walkingUser.getId());
//
//        restWalkingUserMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWalkingUser.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWalkingUser))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//        WalkingUser testWalkingUser = walkingUserList.get(walkingUserList.size() - 1);
//        assertThat(testWalkingUser.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testWalkingUser.getStep()).isEqualTo(DEFAULT_STEP);
//    }
//
//    @Test
//    void fullUpdateWalkingUserWithPatch() throws Exception {
//        // Initialize the database
//        walkingUserRepository.save(walkingUser);
//
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//
//        // Update the walkingUser using partial update
//        WalkingUser partialUpdatedWalkingUser = new WalkingUser();
//        partialUpdatedWalkingUser.setId(walkingUser.getId());
//
//        partialUpdatedWalkingUser.name(UPDATED_NAME).step(UPDATED_STEP);
//
//        restWalkingUserMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedWalkingUser.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWalkingUser))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//        WalkingUser testWalkingUser = walkingUserList.get(walkingUserList.size() - 1);
//        assertThat(testWalkingUser.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testWalkingUser.getStep()).isEqualTo(UPDATED_STEP);
//    }
//
//    @Test
//    void patchNonExistingWalkingUser() throws Exception {
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//        walkingUser.setId(UUID.randomUUID().toString());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restWalkingUserMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, walkingUser.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(walkingUser))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    void patchWithIdMismatchWalkingUser() throws Exception {
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//        walkingUser.setId(UUID.randomUUID().toString());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWalkingUserMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(walkingUser))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    void patchWithMissingIdPathParamWalkingUser() throws Exception {
//        int databaseSizeBeforeUpdate = walkingUserRepository.findAll().size();
//        walkingUser.setId(UUID.randomUUID().toString());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restWalkingUserMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(walkingUser))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the WalkingUser in the database
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    void deleteWalkingUser() throws Exception {
//        // Initialize the database
//        walkingUserRepository.save(walkingUser);
//
//        int databaseSizeBeforeDelete = walkingUserRepository.findAll().size();
//
//        // Delete the walkingUser
//        restWalkingUserMockMvc
//            .perform(delete(ENTITY_API_URL_ID, walkingUser.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<WalkingUser> walkingUserList = walkingUserRepository.findAll();
//        assertThat(walkingUserList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
