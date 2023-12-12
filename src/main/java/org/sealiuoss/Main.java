package org.sealiuoss;

import org.sealiuoss.config.PersonConfig;
import org.sealiuoss.service.PersonService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context=new AnnotationConfigApplicationContext(Main.class);
        context.registerShutdownHook();;
        PersonService service=context.getBean(PersonService.class);
        service.savePersons();
        context.close();
    }
}