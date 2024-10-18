package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.mapper.UserMapper;
import exercise.model.Task;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TaskMapper taskMapper;

    private final UserMapper userMapper;

    @Autowired
    public TasksController(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
        this.userMapper = userMapper;
    }


    // GET /tasks – просмотр списка всех задач
    @GetMapping("")
    public List<TaskDTO> index() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::map)
                .toList();
    }

    // GET /tasks/{id} – просмотр конкретной задачи
    @GetMapping("/{id}")
    public TaskDTO show(@PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found"));
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    // POST /tasks – создание новой задачи
    @PostMapping("")
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO taskData) {
        var task = taskMapper.map(taskData);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    // PUT /tasks/{id} – редактирование задачи.
    // При редактировании мы должны иметь возможность поменять название,
    // описание задачи и ответственного разработчика
    @PutMapping("/{id}")
    public TaskDTO update(@PathVariable long id, @Valid @RequestBody TaskUpdateDTO taskData) {
        var task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found"));
        taskMapper.update(taskData, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    // DELETE /tasks/{id} – удаление задачи
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found"));
        taskRepository.deleteById(id);
        taskRepository.delete(task);
    }
    // END
}
