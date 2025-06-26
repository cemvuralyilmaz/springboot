package com.MongoSpring.MongoSpring.Repository;

import com.MongoSpring.MongoSpring.Model.Student;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepo  extends MongoRepository<Student,Long> {

    @Aggregation(pipeline = { " {$group:  { _id: '', total: {$max: $mark}}}"})
    public Integer max();

    @Aggregation(pipeline = { " {$group:  { _id: '', total: {$min: $mark}}}"})
    public Integer min();
}
