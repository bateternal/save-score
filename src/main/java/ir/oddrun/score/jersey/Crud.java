package ir.oddrun.score.jersey;

import ir.oddrun.score.data.dto.*;
import ir.oddrun.score.data.hibernate.HibernateUtil;
import ir.oddrun.score.model.entity.UserEntity;
import ir.oddrun.score.model.repository.UserRepository;
import ir.oddrun.score.utils.StringUtil;
import org.eclipse.persistence.internal.sessions.DirectCollectionChangeRecord;

import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import java.util.List;

@Path("/record")
public class Crud {

    @POST
    @Path("/create")
    public CreateDTO createUser(@HeaderParam("name") String name, @HeaderParam("lastname") String lastName, @HeaderParam("username") String username) {
        if (!StringUtil.isValid(name) || !StringUtil.isValid(lastName) || !StringUtil.isValid(username)){
            CreateDTO dto = new CreateDTO();
            dto.setMessage("information not valid");
            dto.setErrorCode(2);
            dto.setUser_id(-1L);
            return dto;
        }
        UserRepository urep = new UserRepository();
        try {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(name);
            userEntity.setLastName(lastName);
            userEntity.setUserName(username);
            userEntity.setScore(0);

            urep.setEntity(userEntity);
            urep.save(true);

            CreateDTO dto = new CreateDTO();
            dto.setUser_id(userEntity.getId());
            dto.setErrorCode(0);
            dto.setMessage("User successfully created");
            return dto;
        }catch (PersistenceException e){
            e.printStackTrace();
            CreateDTO dto = new CreateDTO();
            dto.setErrorCode(1);
            dto.setUser_id(-1L);
            dto.setMessage("this username is taken");
            return dto;
        }catch (Exception e){
            e.printStackTrace();
            CreateDTO dto = new CreateDTO();
            dto.setErrorCode(3);
            dto.setUser_id(-1L);
            dto.setMessage("sorry, please try again later");
            return dto;
        }
    }

    @GET
    @Path("get-score")
    public ScoreDTO getScore(@HeaderParam("username") String username){
        if (!StringUtil.isValid(username)){
            ScoreDTO dto = new ScoreDTO();
            dto.setMessage("username is not valid");
            dto.setScore(-1);
            dto.setErrorCode(3);
            return dto;
        }

        try {
            UserRepository urep = new UserRepository();
            UserEntity userEntity = urep.findByUserName(username);
            if (userEntity == null){
                ScoreDTO dto = new ScoreDTO();
                dto.setScore(-1);
                dto.setMessage("this user does not exist");
                dto.setErrorCode(1);
                return dto;
            }

            ScoreDTO dto = new ScoreDTO();
            dto.setErrorCode(0);
            dto.setScore(userEntity.getScore());
            return dto;
        }
        catch (Exception e){
            e.printStackTrace();
            ScoreDTO dto = new ScoreDTO();
            dto.setScore(-1);
            dto.setErrorCode(2);
            dto.setMessage("sorry, please try again later");
            return dto;
        }
    }


    @POST
    @Path("/update")
    public UpdateDTO updateScore(@HeaderParam("username") String username,@HeaderParam("score") String score){
        try {
            UserRepository urep = new UserRepository();
            Integer error = urep.updateScore(username, Integer.valueOf(score));
            if (error == 0){
                UpdateDTO dto = new UpdateDTO();
                dto.setNew_score(-1);
                dto.setMessage("this user does not exist");
                dto.setErrorCode(2);
                return dto;
            }
            UpdateDTO updateDTO = new UpdateDTO();
            updateDTO.setNew_score(Integer.valueOf(score));
            updateDTO.setErrorCode(0);
            updateDTO.setMessage("The score successfully updated");
            return updateDTO;
        }
        catch (Exception e){
            e.printStackTrace();
            UpdateDTO dto = new UpdateDTO();
            dto.setNew_score(-1);
            dto.setErrorCode(1);
            dto.setMessage("sorry, please try again later");
            return dto;
        }
    }

    @GET
    @Path("/best")
    public BestScoreDTO bestScore(){
        UserRepository urep = new UserRepository();
        List list = urep.getAllUsers();
        for (int i = 0; i < list.size(); i++) {
            HibernateUtil.getSession().refresh(list.get(i));
        }

        int size = Math.min(10,list.size());
        UserDTO[] arr = new UserDTO[size];
        for (int i = 0; i < size; i++) {
            UserDTO dto = new UserDTO();
            UserEntity userEntity = (UserEntity) list.get(i);
            dto.setId((int) userEntity.getId());
            dto.setLast_name(userEntity.getLastName());
            dto.setName(userEntity.getName());
            dto.setScore(userEntity.getScore());
            dto.setUser_name(userEntity.getUserName());
            arr[i] = dto;
        }

        BestScoreDTO dto = new BestScoreDTO();
        dto.setUsers(arr);
        dto.setErrorCode(0);
        return dto;
    }
}
