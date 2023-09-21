package com.mycompany.myapp.service.dto;

public class WalkingTotalStepsDTO {
    private String userId;
    private Long count;

    public WalkingTotalStepsDTO(String userId, Long count) {
        this.userId = userId;
        this.count = count;
    }

    public String getUserId() {
        return userId;
    }

    public Long getCount() {
        return count;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}
