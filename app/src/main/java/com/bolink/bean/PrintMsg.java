package com.bolink.bean;

/**
 * Created by xulu on 2017/10/12.
 */

public class PrintMsg {
    String title;
    String content;
    String tale;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTale() {
        return tale;
    }

    public void setTale(String tale) {
        this.tale = tale;
    }

    @Override
    public String toString() {
        return "PrintMsg{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tale='" + tale + '\'' +
                '}';
    }
}
