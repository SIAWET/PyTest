package com.example.pytest.app.entity.chatEntity;

import java.io.Serializable;

public class EmojiEntity implements Serializable {

    private String shortname;
    private String unicode;

    public EmojiEntity() {
    }

    public String getName() {
        return shortname;
    }

    public void setName(String name) {
        this.shortname = name;
    }

    public String getUnicode() {
        return unicode;
        //new String(Character.toChars(Integer.parseInt(unicode, 16)))
        //return String.valueOf(Character.toChars(unicode));
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    @Override
    public String toString() {
        return "EmojiEntity{" +
                "shortname='" + shortname + '\'' +
                ", unicode=" + unicode +
                '}';
    }
}
