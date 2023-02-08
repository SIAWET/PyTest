package com.example.pytest.app.model.event;

public class MessageEvent {

    private String message;
    private String type;

    public MessageEvent(String message){
        this.message = message;
    }

    public MessageEvent(String message,String type){
        this.message = message;
        this.type =type;
    }

    public String getMessage(){
        return message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
