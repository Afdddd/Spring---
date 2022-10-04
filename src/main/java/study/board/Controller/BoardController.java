package study.board.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.board.Board;
import study.board.Service.BoardService;

import java.sql.SQLException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {


    private final BoardService service;

    @RequestMapping("/")
    public String home() {
        log.info("home controller");
        return "home";
    }

    @GetMapping("/boards/write")
    public String createForm() {
        log.info("createForm");
        return "BoardWrite";
    }

    @PostMapping("/boards/write")
    public String create(Board board) throws SQLException {
        service.write(board);
        log.info("create");

        return "redirect:/";
    }

    @GetMapping("/boards")
    public String boardList(Model model) throws SQLException {
        model.addAttribute("list", service.boardList());
        return "BoardList";
    }

    @GetMapping("/boards/view")
    public String boardView(Model model, Integer id) throws SQLException {
        log.info("id={}",id);
        model.addAttribute("board",service.boardView(id));
        return "BoardView";
    }
}
