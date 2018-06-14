package ir.oddrun.score.data.dto;

import ir.oddrun.score.model.entity.UserEntity;

public class BestScoreDTO {

    private Integer errorCode;
    private UserDTO[] users;

    public UserDTO[] getUsers() {
        return users;
    }

    public void setUsers(UserDTO[] users) {
        this.users = users;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
