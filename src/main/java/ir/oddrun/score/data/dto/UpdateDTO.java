package ir.oddrun.score.data.dto;

public class UpdateDTO {

    private Integer errorCode;
    private String message;
    private Integer new_score;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNew_score() {
        return new_score;
    }

    public void setNew_score(Integer new_score) {
        this.new_score = new_score;
    }
}
