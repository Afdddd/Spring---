package study.board;

import lombok.Data;

@Data
public class Board {

    private int id;
    private String title;
    private String contents;

    public Board() {
    }

    public Board(String title, String content) {
        this.title = title;
        this.contents = content;
    }
}
