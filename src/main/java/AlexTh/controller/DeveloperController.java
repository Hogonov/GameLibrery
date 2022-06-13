package AlexTh.controller;


import AlexTh.models.AutoUser;
import AlexTh.models.DevReview;
import AlexTh.models.Developer;
import AlexTh.repository.AutoUserRepository;
import AlexTh.repository.DevReviewRepository;
import AlexTh.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/developers")
public class DeveloperController {
    @Autowired
    DeveloperRepository devRepository;
    @Autowired
    AutoUserRepository autoUserRepository;
    @Autowired
    DevReviewRepository devReviewRepository;



    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addDev(Model model){
        Developer dev = new Developer();
        model.addAttribute("dev", dev);
        return "addDev";
    }

    @RequestMapping(value="/add",method= RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String saveDev(
            @Valid @ModelAttribute("dev") Developer dev,
            BindingResult errors,
            Model model){
        if(errors.hasErrors()){
            return "addDev";
        }
        devRepository.save(dev);
       // System.out.println(dev);
        return "redirect:/developers/alldevs";
    }

    @RequestMapping("/alldevs")
    public String allDevs(Model model){
        model.addAttribute("devs", devRepository.findAll());
        System.out.println(devRepository.findAll());
        return "allDevs";
    }

    @RequestMapping("/delete/dev")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteDev(Model model, @RequestParam("devId") Long devId){
        System.out.println(devId);
        Developer dev = devRepository.findOne(devId);
        devRepository.delete(dev);
        return "redirect:/developers/alldevs";
    }

    @RequestMapping("/dev")
    public String dev(Model model, @RequestParam("devId") Long devId){
        System.out.println(devId);
        Developer dev = devRepository.findOne(devId);
        System.out.println(dev.toString());
        model.addAttribute("dev", dev);
        model.addAttribute("games", dev.getGames());
        return "dev";
    }

    @RequestMapping("/review")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String review(Model model, @RequestParam("devId") Long devId,
                         @RequestParam("rank") Integer rank) {
        System.out.println(rank);
        String s = "redirect:/developers/dev?devId=" + devId;
        Developer dev = devRepository.findOne(devId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nameUser = auth.getName();
        if (nameUser.equals("anonymousUser")) {
            System.out.println("->>>>"+auth);
            return s;
        }

        System.out.println(nameUser);
        AutoUser autoUser = autoUserRepository.findByUsername(nameUser);


        DevReview devReview = devReviewRepository.findByDevIdAndUserId(devId, autoUser.getAutoUserId());
        if (devReview != null && devReview.getRank().equals(rank)) {
            return s;
        } else if (devReview == null) {
            devReview = new DevReview(devId, autoUser.getAutoUserId(), rank);
            devReviewRepository.save(devReview);
        } else {
            devReview.setRank(rank);
            devReviewRepository.updateRank(autoUser.getAutoUserId(), devId, rank);
        }

        System.out.println("-->>>" + devReviewRepository.findByDevIdAndUserId(devId, autoUser.getAutoUserId()));
        Integer sum = 0;
        List<DevReview> devRank = devReviewRepository.findAllByDevId(devId);
        for (DevReview i : devRank) {
            sum += i.getRank();
        }
        int division = (devRank.size() > 0) ? devRank.size() : 1;
        Double developerRank = Double.valueOf(sum) / division;
        dev.setRank(developerRank);
        devRepository.updateDevRank(devId, developerRank);
        System.out.println(rank);
        System.out.println(dev);
        return s;
    }


    @RequestMapping(value = "/searched", method = RequestMethod.POST)
    public String searchDevP(Pageable page, Model model,
                              @RequestParam("name") String name,
                              @RequestParam("sort") String sort) {
        Page<Developer> devs;
        //System.out.println(name + " " + genre + " " + developer.length());
        Sort sorts = new Sort(sort);
        Pageable pageNew = new PageRequest(page.getPageNumber(), 5, sorts);

        if (name.length() > 0 ){
            devs = devRepository.findAllByName(name, pageNew);
        } else {
            devs = devRepository.findAll(pageNew);
        }

        System.out.println(devs);
        model.addAttribute("developers", devs);
        Sort.Order o = null;
        if (sort != null) o = sorts.iterator().next();
        model.addAttribute("sort", sort != null ? o.getProperty() : null);
        model.addAttribute("game", sort != null ? o.getDirection() : null);
        model.addAttribute("page", devs);
        model.addAttribute("name", name);
        return "searchDevs";
    }

    @RequestMapping(value = "/searched")
    public String searchDev(Pageable page, Model model,
                             @RequestParam("name") String name,
                             @RequestParam("sort") String sort) {
        Page<Developer> devs;
        //System.out.println(name + " " + genre + " " + developer.length());
        Sort sorts = new Sort(sort);
        Pageable pageNew = new PageRequest(page.getPageNumber(), 5, sorts);

        if (name.length() > 0 ){
            devs = devRepository.findAllByName(name, pageNew);
        } else {
            devs = devRepository.findAll(pageNew);
        }

        System.out.println(devs);
        model.addAttribute("developers", devs);
        Sort.Order o = null;
        if (sort != null) o = sorts.iterator().next();
        model.addAttribute("sort", sort != null ? o.getProperty() : null);
        model.addAttribute("game", sort != null ? o.getDirection() : null);
        model.addAttribute("page", devs);
        model.addAttribute("name", name);
        return "searchDevs";
    }



    @RequestMapping(value = "/search")
    public String selectForm(Model model) {
        return "searchFormDev";
    }

    @ModelAttribute("sorts")
    public List<String> getSorts() {
        return Arrays.asList("Name", "Rank", "Year");
    }
}
