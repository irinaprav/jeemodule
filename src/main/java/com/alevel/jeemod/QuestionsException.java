package com.alevel.jeemod;

class QuestionsException extends Exception {
    QuestionsException(Throwable cause) {
        super(cause);
    }

    public QuestionsException(String name) {
        super(name);
    }
}
