package ir.oddrun.score.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_user_jersey")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_jersey_generator")
    @SequenceGenerator(name = "user_jersey_generator", sequenceName = "user_jersey_seq", allocationSize = 50)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_lastname")
    private String last_name;

    @Column(name = "c_username", unique = true, nullable = false)
    private String user_name;

    @Column(name = "c_score")
    private Integer score;

    public long getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString(){
        return "name : " + name + "\n" +
                "lastname ‌: " + last_name + "\n" +
                "username : " + user_name + "\n" +
                "score : " + score;
    }

}
