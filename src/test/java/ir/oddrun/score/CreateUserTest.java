package ir.oddrun.score;

import ir.oddrun.score.data.dto.*;
import ir.oddrun.score.data.hibernate.HibernateUtil;
import ir.oddrun.score.jersey.Crud;
import org.boon.di.In;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateUserTest {

    @Before
    public void before(){
        String hql = "delete UserEntity";
        HibernateUtil.getSession().beginTransaction();
        Query query = HibernateUtil.getSession().createQuery(hql);
        query.executeUpdate();
        HibernateUtil.getSession().getTransaction().commit();
    }

    @Test
    public void scenario(){
        Crud c = new Crud();
        for (int i = 1; i < 16; i++) {
            String name = "a" + String.valueOf(i);
            String lastname = "b" + String.valueOf(i);
            String username = "c" + String.valueOf(i);
            CreateDTO dto = c.createUser(name, lastname, username);
            assertEquals(dto.getErrorCode(),Integer.valueOf(0));
        }

        {
            String name = "a";
            String lastname = "b";
            String username = "c" + String.valueOf(7);
            CreateDTO dto = c.createUser(name, lastname, username);
            assertEquals(dto.getErrorCode(),Integer.valueOf(1));
        }

        String[] scores = {"3","5","8","9","2","10","11","4","7","11","11","1","2","3","4"};
        for (int i = 1; i < 16; i++) {
            String username = "c" + String.valueOf(i);
            UpdateDTO dto = c.updateScore(username,scores[i-1]);
            assertEquals(dto.getErrorCode(),Integer.valueOf(0));
        }

        {
            String username = "c" + String.valueOf(8);
            ScoreDTO dto = c.getScore(username);
            assertEquals(dto.getErrorCode(),Integer.valueOf(0));
            assertEquals(dto.getScore(),Integer.valueOf(4));
        }


        {
            String[] best_scores = {"11","11","11","10","9","8","7","5","4","4"};
            BestScoreDTO dto = c.bestScore();
            UserDTO[] users = dto.getUsers();
            for (int i = 0; i < 10; i++) {
                assertEquals(best_scores[i],users[i].getScore().toString());
            }
        }

        {
            String username = "c" + String.valueOf(4);
            UpdateDTO dto = c.updateScore(username,"6");
            assertEquals(dto.getErrorCode(),Integer.valueOf(0));
            assertEquals(dto.getNew_score(),Integer.valueOf(6));

            username = "c" + String.valueOf(9);
            dto = c.updateScore(username,"12");
            assertEquals(dto.getErrorCode(),Integer.valueOf(0));
            assertEquals(dto.getNew_score(),Integer.valueOf(12));

            username = "c" + String.valueOf(13);
            dto = c.updateScore(username,"9");
            assertEquals(dto.getErrorCode(),Integer.valueOf(0));
            assertEquals(dto.getNew_score(),Integer.valueOf(9));
        }

        {
            String[] best_scores = {"12","11","11","11","10","9","8","6","5","4",};
            BestScoreDTO dto = c.bestScore();
            for (int i = 0; i < dto.getUsers().length; i++) {
                System.out.println(dto.getUsers()[i].toString());
                System.out.println();
            }
            UserDTO[] users = dto.getUsers();
            for (int i = 0; i < 10; i++) {
                System.out.println(best_scores[i]);
                System.out.println(users[i].getScore());
                assertEquals(best_scores[i],users[i].getScore().toString());
            }
        }

    }

    @Test
    public void testCreateUser(){
        Crud c = new Crud();

        {
            String name = "abolfazl";
            String lastName = "taheri";
            String userName = "bateternal";
            CreateDTO dto = c.createUser(name, lastName, userName);
            assertEquals(dto.getErrorCode(), Integer.valueOf(0));
        }

        {
            String name = "abolfazl";
            String lastName = "taheri";
            String userName = "bateternal";
            CreateDTO dto = c.createUser(name,lastName,userName);
            assertEquals(dto.getErrorCode(),Integer.valueOf(1));
        }

        {
            String name = "";
            String lastName = "taheri";
            String userName = "bateternal";
            CreateDTO dto = c.createUser(name,lastName,userName);
            assertEquals(dto.getErrorCode(),Integer.valueOf(2));
        }

        {
            String name = "abolfazl";
            String lastName = "";
            String userName = "bateternal";
            CreateDTO dto = c.createUser(name,lastName,userName);
            assertEquals(dto.getErrorCode(),Integer.valueOf(2));
        }

        {
            String name = "abolfazl";
            String lastName = "taheri";
            String userName = "";
            CreateDTO dto = c.createUser(name,lastName,userName);
            assertEquals(dto.getErrorCode(),Integer.valueOf(2));
        }

    }

    @Test
    public void TestGetScore(){
        Crud c = new Crud();
        testCreateUser();
        {
            String userName = "bateternal";
            ScoreDTO dto = c.getScore(userName);
            assertEquals(dto.getErrorCode(), Integer.valueOf(0));
            assertEquals(dto.getScore(), Integer.valueOf(0));
        }

        {
            String userName = "bat";
            ScoreDTO dto = c.getScore(userName);
            assertEquals(dto.getErrorCode(),Integer.valueOf(1));
        }

        {
            String userName = null;
            ScoreDTO dto = c.getScore(userName);
            assertEquals(dto.getErrorCode(),Integer.valueOf(3));
        }

        {
            String userName = "";
            ScoreDTO dto = c.getScore(userName);
            assertEquals(dto.getErrorCode(),Integer.valueOf(3));
        }

    }

}
