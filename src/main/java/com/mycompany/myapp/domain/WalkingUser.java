package com.mycompany.myapp.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A WalkingUser.
 */
@Document(collection = "walking_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WalkingUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("userId")
    private String userId;

    @Field("steps")
    private List<Step> steps;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public WalkingUser id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public WalkingUser userId(String name) {
        this.setUserId(name);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Step> getSteps() {
        return this.steps;
    }

    public WalkingUser steps(List<Step> steps) {
        this.setSteps(steps);
        return this;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WalkingUser)) {
            return false;
        }
        return id != null && id.equals(((WalkingUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalkingUser{" +
            "id=" + getId() +
            ", name='" + getUserId() + "'" +
            ", step=" + getSteps() +
            "}";
    }
}
