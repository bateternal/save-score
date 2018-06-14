package ir.oddrun.score.model.repository;

import ir.oddrun.score.data.hibernate.HibernateUtil;
import ir.oddrun.score.model.entity.UserEntity;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository extends GenericRepository<UserEntity> {

    public UserEntity findByUserName(String username){
        String hql = "from UserEntity u where u.user_name = :username";
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("username",username);
        List results = query.list();
        if (results.size() == 0)
            return null;
        System.out.println(results.get(0));
        return (UserEntity) results.get(0);
    }

    public Integer updateScore(String username,Integer score){
        String hql = "update UserEntity u set u.score = :score where u.user_name = :username";
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.setParameter("score",score);
        query.setParameter("username",username);
        int error = query.executeUpdate();
        HibernateUtil.getSession().getTransaction().commit();
        return error;
    }

    public List getAllUsers(){
        String hql = "from UserEntity u order by u.score DESC";
        Query query = HibernateUtil.getSession().createQuery(hql);
        return query.list();
    }
}
