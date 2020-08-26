package com.neelav.resumePortal.controller;

import com.neelav.resumePortal.model.Education;
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
        Optional<UserProfile> neelavOptional = userProfileRepository.findByUserName("Neelav");

        neelavOptional.orElseThrow(()-> new RuntimeException("No User Found with : Neelav"));

        UserProfile neelav = neelavOptional.get();

        Job job1 = new Job();
        job1.setId(1);
        job1.setCompany("Company 1");
        job1.setDesignation("Software Engineer");
        job1.setStartDate(LocalDate.of(2020,1,1));
        //job1.setEndDate(LocalDate.of(2023,1,1));
        job1.setCurrentJob(true);
        job1.getResponsibilities().add("Create Resume Portal");
        job1.getResponsibilities().add("Create Simple Payment App");
        job1.getResponsibilities().add("Create Ecommerce App");

        Job job2 = new Job();
        job2.setId(2);
        job2.setCompany("Company 2");
        job2.setDesignation("Lead Software Engineer");
        job2.setStartDate(LocalDate.of(2019,1,1));
        job2.setEndDate(LocalDate.of(2019,12,31));
        job2.getResponsibilities().add("Create Resume Portal");
        job2.getResponsibilities().add("Create Simple Payment App");
        job2.getResponsibilities().add("Create Ecommerce App");

        neelav.getJobs().clear();
        neelav.getJobs().add(job1);
        neelav.getJobs().add(job2);

        Education e1=new Education();
        e1.setCollege("Awesome College");
        e1.setStartDate(LocalDate.of(2019,1,1));
        e1.setEndDate(LocalDate.of(2019,12,31));
        e1.setQualification("Useless Degree");
        e1.setSummary("Studied a Lot");

        Education e2=new Education();
        e2.setCollege("Awesome College");
        e2.setStartDate(LocalDate.of(2019,1,1));
        e2.setEndDate(LocalDate.of(2019,12,31));
        e2.setQualification("Useless Degree");
        e2.setSummary("Studied a Lot");

        neelav.getEducations().clear();
        neelav.getEducations().add(e1);
        neelav.getEducations().add(e2);

        neelav.getSkills().clear();
        neelav.getSkills().add("Java Buff");
        neelav.getSkills().add("Spring Buff");
        neelav.getSkills().add("Footballer");


        userProfileRepository.save(neelav);
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
