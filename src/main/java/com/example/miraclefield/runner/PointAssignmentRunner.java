package com.example.miraclefield.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.miraclefield.repository.UserRepository;
import com.example.miraclefield.repository.PointRepository;
import com.example.miraclefield.entity.Point;

@Component
public class PointAssignmentRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    public PointAssignmentRunner(UserRepository userRepository, PointRepository pointRepository) {
        this.userRepository = userRepository;
        this.pointRepository = pointRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        userRepository.findAll().forEach(user -> {
            if (user.getPoint() == null) {
                Point point = new Point();
                point.setAmount(5L);
                pointRepository.save(point);
                user.setPoint(point);
                userRepository.save(user);
            }
        });
    }
}
