package com.MongoSpring.MongoSpring.Controller;

import com.MongoSpring.MongoSpring.Model.DatabaseSequence;
import com.MongoSpring.MongoSpring.Model.Student;
import com.MongoSpring.MongoSpring.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import org.springframework.data.mongodb.core.query.Criteria;

import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
public class MainController {

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(Criteria.where("_id").is(seqName)), new Update().inc("seq", 1), options().returnNew(true).upsert(true), DatabaseSequence.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }


    @PostMapping("/addStudent")
    public void addStudent(@RequestBody Student student) {
        Student studentTemp = new Student();
        studentTemp.setRno(generateSequence(Student.SEQUENCE_NAME));
        studentTemp.setMark(student.getMark());
        studentTemp.setName(student.getName());
        studentRepo.save(studentTemp);
    }

    @GetMapping("/getStudent/{id}")
    public Student getStudent(@PathVariable Integer id) {
        return studentRepo.findById(Long.valueOf(id)).orElse(null);
    }

    @GetMapping("/fetchStudents")
    public List<Student> fetchStudents() {
        return studentRepo.findAll();
    }

    @PutMapping("/updateStudent")
    public void updateStudent(@RequestBody Student student) {
        //fetch the object using id
        Student data = studentRepo.findById(student.getRno()).orElse(null);
        System.out.println(data);

        if (data != null) {
            data.setName(student.getName());
            data.setMark(student.getMark());
            studentRepo.save(data);
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentRepo.deleteById(Long.valueOf(id));
    }

    @PostMapping("/addStudentLİst")
    public void addStudentList(@RequestBody List<Student> student) {
        studentRepo.saveAll(student);
    }
    @GetMapping("/getMax")
    public Integer getMax() {
        return studentRepo.max();
    }

    @GetMapping("/getMin")
    public Integer getMin() {
        return studentRepo.min();
    }
}
