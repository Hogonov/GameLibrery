package AlexTh.controller;

import AlexTh.models.AutoUser;
import AlexTh.models.Game;
import AlexTh.repository.AutoUserRepository;
import AlexTh.repository.DeveloperRepository;
import AlexTh.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    AutoUserRepository autoUserRepository;
    @Autowired
    GameRepository gameRepository;

    @RequestMapping(value="/")
    public String home(){
        return "home";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute AutoUser user, BindingResult error) {
        if (error.hasErrors())
            return "redirect:/register";
        user.setRole("ROLE_USER");
        //user.setAutoUserId(2L);
        if(autoUserRepository.findByUsername(user.getUsername()) != null)
            return "registration";
        autoUserRepository.save(user);

        //Program authentication
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String goRegister() {
        return "registration";
    }

    @RequestMapping(value="/access")
    public String access(){
        return "access";
    }

    @RequestMapping(value="/somethingerror")
    public String error500(){
        return "500";
    }

    @RequestMapping(value="/notfound")
    public String notfound(){
        return "notFound";
    }

    @ResponseBody
    @RequestMapping("/allgames")
    public List<Game> getAllGames() {
        return gameRepository.getGames(new PageRequest(0,20));
    }
}
