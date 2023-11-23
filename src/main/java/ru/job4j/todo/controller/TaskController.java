package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final PriorityService priorityService;

    private final CategoryService categoryService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/onlyNew")
    public String getNew(Model model) {
        model.addAttribute("tasks", taskService.findByBoolean(false));
        return "tasks/list";
    }

    @GetMapping("/onlyDone")
    public String getDone(Model model) {
        model.addAttribute("tasks", taskService.findByBoolean(true));
        return "tasks/list";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task,
                         @SessionAttribute User user,
                         @RequestParam(required = false) Set<Integer> cIds) {
        task.setUser(user);
        if (!cIds.isEmpty()) {
            task.setCategories(categoryService.findByIds(cIds));
        }
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        boolean rsl = taskService.delete(id);
        if (!rsl) {
            model.addAttribute("message", "Ошибка удаления");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        Optional<Task> taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдена");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/edit";
    }

    @PostMapping("/update")
    public String update(Model model,
                         @ModelAttribute Task task,
                         @SessionAttribute User user,
                         @RequestParam(required = false) Set<Integer> cIds) {
        task.setUser(user);
        if (!cIds.isEmpty()) {
           task.setCategories(categoryService.findByIds(cIds));
        }
        boolean rsl = taskService.update(task.getId(), task);
        if (!rsl) {
            model.addAttribute("message", "Ошибка обновления");
            return "errors/404";
        }
        return "redirect:/tasks/one";
    }

    @GetMapping("/done/{id}")
    public String done(Model model, @PathVariable int id) {
        boolean rsl = taskService.done(id);
        if (!rsl) {
            model.addAttribute("message", "Ошибка обновления");
            return "errors/404";
        }
        return "redirect:/tasks/one";
    }

}
