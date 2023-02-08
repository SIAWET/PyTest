package com.example.pytest.app.model.event;

public class CouponEvent {

    private String message;

    private String num;

    public CouponEvent(String message, String num) {
        this.message = message;
        this.num = num;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
