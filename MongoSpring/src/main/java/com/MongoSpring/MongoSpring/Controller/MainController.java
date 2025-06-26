package com.MongoSpring.MongoSpring.Controller;

import com.MongoSpring.MongoSpring.Model.Student;
import com.MongoSpring.MongoSpring.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    StudentRepo studentRepo;
    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student){
        studentRepo.save(student);
    }

    @GetMapping("/getStudent/{id}")
    public Student getStudent(@PathVariable Integer id){
      return   studentRepo.findById(id).orElse(null);
    }

    @GetMapping("/fetchStudents")
    public List<Student> fetchStudents(){
        return studentRepo.findAll();
    }

    @PutMapping("/updateStudent")
    public void updateStudent(@RequestBody Student student){
        //fetch the object using id
        Student data=studentRepo.findById(student.getRno()).orElse(null);
        System.out.println(data);

    if (data!=null){
        data.setName(student.getName());
        data.setAddress(student.getAddress());
        studentRepo.save(data);
        }

    }
}
