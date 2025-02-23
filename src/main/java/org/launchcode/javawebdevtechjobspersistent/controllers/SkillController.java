package org.launchcode.javawebdevtechjobspersistent.controllers;

import org.launchcode.javawebdevtechjobspersistent.models.Skill;
import org.launchcode.javawebdevtechjobspersistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("skills")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping("")
    public String displayAllSkills(Model model){
        model.addAttribute("title","Skills");
        model.addAttribute("skills",skillRepository.findAll());
        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm (Model model){
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkill, Model model, Errors errors){
        if(errors.hasErrors()){
            return "redirect:skills/add";
        }

        skillRepository.save(newSkill);
        return "redirect:";
    }

    @GetMapping("view/{skillId}")
    public String displayViewSkill(Model model,@PathVariable int skillId){
        Optional optSkill = skillRepository.findById(skillId);
        if(optSkill.isPresent()){
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill",skill);
            model.addAttribute("jobs",skill.getJobs());
            return "skills/view";
        }
        return "redirect:../";

    }


}
