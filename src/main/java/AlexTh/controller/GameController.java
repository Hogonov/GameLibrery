package AlexTh.controller;

import AlexTh.models.*;
import AlexTh.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/games")
public class GameController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    DeveloperRepository devRepository;
    @Autowired
    GameGenreRepository gameGenreRepository;
    @Autowired
    GameDeveloperRepository gameDeveloperRepository;
    @Autowired
    AutoUserRepository autoUserRepository;
    @Autowired
    GamesReviewRepository gamesReviewRepository;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addGame(Model model) {
        Game game = new Game();
        model.addAttribute("game", game);
        return "addGame";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveGame(
            @Valid @ModelAttribute("game") Game game,
            BindingResult errors,
            Model model,
            @RequestParam("developer") Long devId) {
        if (errors.hasErrors()) {
            return "addGame";
        }
        Developer dev = devRepository.findOne(devId);
        game.setDeveloper(dev.getName());
        gameRepository.save(game);
        System.out.println(game.getId() + " " + dev.getName() + " " + devId);
        GameDeveloper gameDeveloper = new GameDeveloper(game.getId(), dev.getId());
        gameDeveloperRepository.save(gameDeveloper);
        GameGenre gameGenre = new GameGenre(game.getId(), game.getGenre());

        return "redirect:/games/allgames";
    }

    @RequestMapping("/allgames")
    public String allGames(Model model) {
        List<Game> games = gameRepository.findAll();
        model.addAttribute("games", games);
        return "allGames";
    }

    @RequestMapping("/game")
    public String game(Model model, @RequestParam("gameId") Long gameId) {
        System.out.println(gameId);
        Game game = gameRepository.findOne(gameId);
        System.out.println(game);
        model.addAttribute("game", game);
        return "game";
    }

    @RequestMapping("/review")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String review(Model model, @RequestParam("gameId") Long gameId,
                         @RequestParam("rank") Integer rank) {
        System.out.println(rank);
        String s = "redirect:/games/game?gameId=" + gameId;
        Game game = gameRepository.findOne(gameId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = auth.getName();
        if (nameUser.equals("anonymousUser")) {
            System.out.println("->>>>"+auth);
            return s;
        }

        System.out.println(nameUser);
        AutoUser autoUser = autoUserRepository.findByUsername(nameUser);


        GamesReview gamesReview = gamesReviewRepository.findByGameIdAndUserId(gameId, autoUser.getAutoUserId());
        if (gamesReview != null && gamesReview.getRank().equals(rank)) {
            return s;
        } else if (gamesReview == null) {
            gamesReview = new GamesReview(gameId, autoUser.getAutoUserId(), rank);
            gamesReviewRepository.save(gamesReview);
        } else {
            gamesReview.setRank(rank);
            gamesReviewRepository.updateRank(autoUser.getAutoUserId(), gameId, rank);
        }

        System.out.println("-->>>" + gamesReviewRepository.findByGameIdAndUserId(gameId, autoUser.getAutoUserId()));
        Integer sum = 0;
        List<GamesReview> gr = gamesReviewRepository.findAllByGameId(gameId);
        for (GamesReview i : gr) {
            sum += i.getRank();
        }
        int division = (gr.size() > 0) ? gr.size() : 1;
        Double gameRank = Double.valueOf(sum) / division;
        game.setRank(gameRank);
        gameRepository.updateGameRank(gameId, gameRank);
        System.out.println(rank);
        System.out.println(game);
        return s;
    }

    @RequestMapping("/delete/game")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteGame(Model model, @RequestParam("gameId") Long gameId) {
        System.out.println(gameId);
        Game game = gameRepository.findOne(gameId);
        gameRepository.delete(game);
        return "redirect:/games/allgames";
    }

    @RequestMapping(value = "/searched", method = RequestMethod.POST)
    public String searchGameP(Pageable page, Model model,
                              @RequestParam("name") String name,
                              @RequestParam("genre") String genre,
                              @RequestParam("developer") String developer,
                              @RequestParam("sort") String sort) {
        Page<Game> games;
        //System.out.println(name + " " + genre + " " + developer.length());
        Sort sorts = new Sort(sort);
        Pageable pageNew = new PageRequest(page.getPageNumber(), 10, sorts);

        if (name.length() > 0 && genre.length() > 0 && developer.length() > 0) {
            games = gameRepository.findAllByNameAndGenreAndDeveloper(name, genre, developer, pageNew);
        } else if (name.length() > 0 && genre.length() > 0) {
            games = gameRepository.findAllByNameAndGenre(name, genre, pageNew);
        } else if (genre.length() > 0 && developer.length() > 0) {
            games = gameRepository.findAllByGenreAndDeveloper(genre, developer, pageNew);
        } else {
            games = gameRepository.findAllByGenre(genre, pageNew);
        }

        System.out.println(games);

        model.addAttribute("games", games);
        Sort.Order o = null;
        if (sort != null) o = sorts.iterator().next();
        model.addAttribute("sort", sort != null ? o.getProperty() : null);
        model.addAttribute("game", sort != null ? o.getDirection() : null);
        model.addAttribute("page", games);
        model.addAttribute("genre", genre);
        model.addAttribute("name", name);
        model.addAttribute("developer", developer);
        return "searchGames";
    }

    @RequestMapping(value = "/searched")
    public String searchGame(Pageable page, Model model,
                             @RequestParam("name") String name,
                             @RequestParam("genre") String genre,
                             @RequestParam("developer") String developer,
                             @RequestParam("sort") String sorts) {
        Page<Game> games;
        Pageable pageNew = new PageRequest(page.getPageNumber(), 10, page.getSort());
        Sort sort = new Sort(sorts);
        System.out.println(name + " " + genre + " " + developer);
        if (name.length() > 0 && genre.length() > 0 && developer.length() > 0) {
            games = gameRepository.findAllByNameAndGenreAndDeveloper(name, genre, developer, pageNew);
        } else if (name.length() > 0 && genre.length() > 0) {
            games = gameRepository.findAllByNameAndGenre(name, genre, pageNew);
        } else if (genre.length() > 0 && developer.length() > 0) {
            games = gameRepository.findAllByGenreAndDeveloper(genre, developer, pageNew);
        } else {
            games = gameRepository.findAllByGenre(genre, pageNew);
        }
        System.out.println(games);
        Sort.Order o = null;
        if (sort != null) o = sort.iterator().next();
        model.addAttribute("sort", sort != null ? o.getProperty() : null);
        model.addAttribute("game", sort != null ? o.getDirection() : null);
        model.addAttribute("page", games);
        model.addAttribute("genre", genre);
        model.addAttribute("name", name);
        model.addAttribute("developer", developer);
        return "searchGames";
    }

    @RequestMapping(value = "/search")
    public String selectForm(Model model) {
        return "searchFormGame";
    }


    @RequestMapping(value = "/addgenre", method = RequestMethod.GET)
    public String addGenres(Model model) {
        GameGenre genre = new GameGenre();
        model.addAttribute("gameGenre", genre);
        return "addGenre";
    }

    @RequestMapping(value = "/game/addgenre", method = RequestMethod.POST)
    public String addGenres(
            @ModelAttribute("gameGenre") GameGenre gameGenre,
            BindingResult errors,
            Model model) {
        if (errors.hasErrors()) {
            return "addGenre";
        }
        gameGenreRepository.save(gameGenre);
        System.out.println(gameGenre);
        return "redirect:/games/allgames";
    }

    @ModelAttribute("sorts")
    public List<String> getSorts() {
        return Arrays.asList("Name", "Developer", "Rank", "Genre", "Year");
    }

    @ModelAttribute("gameGenres")
    public List<GameGenre> getGenres() {
        return gameGenreRepository.findAll();
    }

    @ModelAttribute("gameDevelopers")
    public List<Developer> getDevelopers() {
        return devRepository.findAll();
    }
}
