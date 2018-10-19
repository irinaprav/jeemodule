package com.alevel.jeemod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebServlet("/question")
public class QuestionsServlet extends HttpServlet {

    private ObjectMapper objectMapper;

    private HikariDataSource dataSource;

    private QuestionsRepository questionsRepository;

    @Override
    public void init() {
        HikariConfig hikariConfig = new HikariConfig("/hikary.properties");
        dataSource = new HikariDataSource(hikariConfig);
        questionsRepository = new QuestionsRepository(dataSource);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String question = req.getParameter("answer");
        String answer;
        try {
            answer = questionsRepository.getAnswer(question);
            System.out.println(question);
        } catch (QuestionsException e) {
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
            return;
        }
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=utf8");
        Map<String, String> responseData = Collections.singletonMap("answer", answer);
        objectMapper.writeValue(resp.getOutputStream(), responseData);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Questions question = objectMapper.readValue(req.getInputStream(), Questions.class);
        Long id;
        try {
            id = questionsRepository.save(question);
        } catch (QuestionsException e) {
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );
            return;
        }

        resp.setStatus(HttpServletResponse.SC_CREATED);
    }


}
