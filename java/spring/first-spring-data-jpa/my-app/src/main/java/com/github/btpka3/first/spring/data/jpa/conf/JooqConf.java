package com.github.btpka3.first.spring.data.jpa.conf;

import org.jooq.conf.Settings;
import org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class JooqConf {

  @Bean
  public Settings jooqSettings(){
      return new Settings()
              .withMapJPAAnnotations(true);
  }


//    /**
//     * @param cfg
//     * @see JooqAutoConfiguration
//     */
//    public void conf(org.jooq.Configuration cfg) {
//        cfg.settings()
//                .withMapJPAAnnotations(false);
//    }

}
