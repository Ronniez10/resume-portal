package com.neelav.resumePortal.controller;

import com.neelav.resumePortal.model.Education;
import com.neelav.resumePortal.model.Job;
import com.neelav.resumePortal.model.User;
import com.neelav.resumePortal.model.UserProfile;
import com.neelav.resumePortal.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public String home()
    {
        return "index";
    }

    @GetMapping("/edit")
    public String edit(Principal principal,Model model,@RequestParam(required = false)String add)
    {
        String userId = principal.getName();
        model.addAttribute("userId",userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);

        userProfileOptional.orElseThrow(() -> new NullPointerException());

        UserProfile userProfile = userProfileOptional.get();

        if("job".equals(add))
            userProfile.getJobs().add(new Job());
        else if("education".equals(add))
            userProfile.getEducations().add(new Education());
        else if("skill".equals(add))
            userProfile.getSkills().add("");

        model.addAttribute("userProfile",userProfile    );
        return "profile-edit";
    }

    @GetMapping("/delete")
    public String delete(Principal principal,Model model,@RequestParam(required = false)String type,@RequestParam(required = false)int index)
    {
        String userId = principal.getName();
        model.addAttribute("userId",userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);

        userProfileOptional.orElseThrow(() -> new NullPointerException());

        UserProfile userProfile = userProfileOptional.get();

        if("job".equals(type))
            userProfile.getJobs().remove(index);
        else if("education".equals(type))
            userProfile.getEducations().remove(index);
        else
            userProfile.getSkills().remove(index);

       userProfileRepository.save(userProfile);
        return "redirect:/edit";
    }


    @PostMapping("/edit")
    public String postEdit(Principal principal, Model model, @ModelAttribute("userProfile")UserProfile userProfile)
    {

        String username = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(username);
        UserProfile userProfile1 = userProfileOptional.get();

        //Setting the id for the Received Edited Model Attribute
        userProfile.setId(userProfile1.getId());
        userProfile.setUserName(username);
        userProfileRepository.save(userProfile);
        return "redirect:/view/"+username;
    }


    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model,Principal principal)
    {


        if(principal !=null && principal.getName()!="")
        {
            boolean currentUserProfile = principal.getName().equals(userId);
            model.addAttribute("currentUserProfile",currentUserProfile);
        }

        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);

        userProfileOptional.orElseThrow(()-> new RuntimeException("No User Found with :"+userId));

        model.addAttribute("userId",userId);

        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile",userProfile);

        System.out.println(userProfile);

        return "profile-templates/"+userProfile.getTheme()+"/index";
    }

}
