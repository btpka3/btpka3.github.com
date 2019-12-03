package com.github.btpka3.first.spring.data.jpa.conf;

import com.github.btpka3.first.spring.data.jpa.repo.MyRepo;
import com.github.btpka3.first.spring.data.jpa.repo.BaseRepoImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 */
@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepoImpl.class, basePackageClasses = MyRepo.class)
public class JpaConf {


}
