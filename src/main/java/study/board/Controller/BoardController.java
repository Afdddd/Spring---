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
        log.info("id={}", id);
        model.addAttribute("board", service.boardView(id));
        return "BoardView";
    }

    @GetMapping("/boards/delete")
    public String boardDelete(Integer id) {
        service.boardDelete(id);
        return "redirect:/boards";
    }

    @GetMapping("/boards/update/{id}")
    public String updateForm(@PathVariable("id") Integer id, Model model) throws SQLException {
        Board board = service.boardView(id);
        model.addAttribute("board", board);
        return "BoardUpdate";
    }

    @PostMapping("/boards/update/{id}")
    public String update(Board board, @PathVariable("id") Integer id) throws SQLException {
        service.boardUpdate(id,board);
        return "redirect:/boards";
    }
}

