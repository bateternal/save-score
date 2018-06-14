package ir.oddrun.score.data.dto;

public class UserDTO {
    private Integer id;
    private String name;
    private String last_name;
    private String user_name;
    private Integer score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString(){
        return "name :" + name + "\n" +
                "lastname" + last_name + "\n" +
                "username" + user_name + "\n" +
                "score" + score;
    }
}
