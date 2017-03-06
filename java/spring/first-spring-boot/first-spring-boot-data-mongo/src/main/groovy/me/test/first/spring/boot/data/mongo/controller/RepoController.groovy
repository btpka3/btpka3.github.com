package me.test.first.spring.boot.data.mongo.controller

import me.test.first.spring.boot.data.mongo.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 *
 */
@Controller
@RequestMapping("/repo")
class RepoController {

    @Autowired
    UserRepository userRepo

}
