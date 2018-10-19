package com.alevel.jeemod;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class Questions {

    private final Long id;

    private final String question;

    private final String answer;

    public Questions(String text) {
        this.id = null;
        this.question = text;
        this.answer = "";
    }

    @JsonCreator
    public Questions(
            @JsonProperty("id") Long id,
            @JsonProperty("question") String question,
            @JsonProperty("answer") String  answer
    ) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }


    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Questions questions = (Questions) o;
        return Objects.equals(id, questions.id) &&
                Objects.equals(question, questions.question) &&
                Objects.equals(answer, questions.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, answer);
    }


}