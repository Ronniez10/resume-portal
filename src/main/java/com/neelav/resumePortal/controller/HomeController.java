package com.neelav.resumePortal.controller;

import com.neelav.resumePortal.model.Job;
import com.neelav.resumePortal.model.User;
import com.neelav.resumePortal.model.UserProfile;
import com.neelav.resumePortal.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("/")
    public String home()
    {
        UserProfile profile1 = new UserProfile();
        profile1.setId(1);
        profile1.setUserName("Einstein");
        profile1.setTheme(1);
        profile1.setFirstName("Albert");
        profile1.setLastName("Einstein");
        profile1.setDesignation("Scientist");
        profile1.setSummary("History Changer");
        profile1.setEmail("einstein@gmail.com");
        profile1.setPhone("1111");

        Job job1 = new Job();
        job1.setId(1);
        job1.setCompany("Company 1");
        job1.setDesignation("Software Engineer");
        job1.setStartDate(LocalDate.of(2020,1,1));
        job1.setEndDate(LocalDate.of(2023,1,1));

        Job job2 = new Job();
        job2.setId(2);
        job2.setCompany("Company 2");
        job2.setDesignation("Lead Software Engineer");
        job2.setStartDate(LocalDate.of(2019,1,1));
        job2.setEndDate(LocalDate.of(2019,12,31));

        profile1.setJobs(Arrays.asList(job1,job2));

        userProfileRepository.save(profile1);
        return "profile";
    }

    @GetMapping("/edit")
    public String edit()
    {
        return "edit";
    }

    @GetMapping("/view/{userId}")
    public String view(@PathVariable String userId, Model model)
    {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);

        userProfileOptional.orElseThrow(()-> new RuntimeException("No User Found with :"+userId));

        model.addAttribute("userId",userId);

        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile",userProfile);

        System.out.println(userProfile);

        return "profile-templates/"+userProfile.getTheme()+"/index";
    }
}
