package com.slinger.app.bootstrap;

import com.slinger.app.domian.User;
import com.slinger.app.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;

    public Bootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
        log.debug("Load users, size: " + userRepository.count());
    }

    private void loadData() {
        //load users
        User user1 = User.builder().id(1L).name("Brent").username("brent90").email("brent@gmail.com").build();
        User user2 = User.builder().id(2L).name("Petey").username("dog2").email("dog@gmail.com").build();
        User user3 = User.builder().id(3L).name("Paul").username("gilbert78").email("gilbert@gmail.com").build();
        User user4 = User.builder().id(4L).name("Jon").username("jon374").email("jon@gmail.com").build();
        User user5 = User.builder().id(5L).name("Tessa").username("flower_head34").email("tessa@gmail.com").build();
        User user6 = User.builder().id(6L).name("Rebecca").username("spring_girl945").email("rebecca@gmail.com").build();
        User user7 = User.builder().id(7L).name("Kate").username("linux_girl34").email("kde@gmail.com").build();
        User user8 = User.builder().id(8L).name("Marty").username("guitar_man343").email("gibsonman@gmail.com").build();
        User user9 = User.builder().id(9L).name("Tony").username("ironman34").email("steelman@yahoo.com").build();
        User user10 = User.builder().id(10L).name("NA").username("docker002").email("whale@gmail.com").build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);
        userRepository.save(user7);
        userRepository.save(user8);
        userRepository.save(user9);
        userRepository.save(user10);



    }
}
