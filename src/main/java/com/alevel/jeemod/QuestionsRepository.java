package com.alevel.jeemod;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class QuestionsRepository {

    private final DataSource dataSource;

    public QuestionsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long save(Questions entity) throws QuestionsException {
        String insert = "INSERT INTO q_answers (question, answer)  VALUES (?,?)";
        String select = "SELECT LAST_INSERT_ID()";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement update = connection.prepareStatement(insert);
            PreparedStatement query = connection.prepareStatement(select)
        ) {
            update.setString(1, entity.getQuestion());
            update.setString(2, entity.getAnswer());
            update.executeUpdate();
            ResultSet resultSet = query.executeQuery();
            resultSet.first();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new QuestionsException(e);
        }
    }

    public void update(Questions questions) throws QuestionsException {
        String sql = "UPDATE todos SET text = ?, is_done = ? WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement query = connection.prepareStatement(sql)
        ) {
            query.setString(1, questions.getAnswer());
            query.setString(2, questions.getQuestion());
            query.setLong(3, questions.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            throw new QuestionsException(e);
        }
    }

    public void batchUpdate(Iterable<Questions> batch) throws QuestionsException {
        String sql = "UPDATE todos SET text = ?, is_done = ? WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement query = connection.prepareStatement(sql)
        ) {
            for (Questions questions : batch) {
                query.setString(1, questions.getAnswer());
                query.setString(2, questions.getQuestion());
                query.setLong(3, questions.getId());
                query.addBatch();
            }
            query.executeBatch();
        } catch (SQLException e) {
            throw new QuestionsException(e);
        }
    }

    public String getAnswer(String question) throws QuestionsException {
        String sql = "SELECT answer FROM q_answers WHERE question = ?";
        String respAnswer = "";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement query = connection.prepareStatement(sql)
        ) {
           query.setString(1,question);
            ResultSet resultSet = query.executeQuery();
            if (!resultSet.next()) throw new QuestionsException("sdfs");
            resultSet.first();
            respAnswer = resultSet.getString("answer");
        } catch (SQLException e) {
            throw new QuestionsException(e);
        }
        return respAnswer;
    }

//    public List<Questions> listAllNotDone() throws QuestionsException {
//        String sql = "SELECT * FROM todos WHERE is_done = false";
//        try(Connection connection = dataSource.getConnection();
//            PreparedStatement query = connection.prepareStatement(sql)
//        ) {
//            ResultSet resultSet = query.executeQuery();
//            List<Todo> todos = new LinkedList<>();
//            while (resultSet.next()) {
//                todos.add(new Todo(
//                        resultSet.getLong("id"),
//                        resultSet.getString("text"),
//                        false
//                ));
//            }
//            return todos;
//        } catch (SQLException e) {
//            throw new TodoException(e);
//        }
//    }

}