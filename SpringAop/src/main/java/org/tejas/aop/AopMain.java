package org.tejas.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tejas.aop.service.ShapeService;

public class AopMain {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        ShapeService shapeService = ctx.getBean("shapeService", ShapeService.class);
        shapeService.getCircle().printStringAndReturn("dummy string");
        shapeService.getCircle().printStringAndThrowException("dummy string");
    }
}


//AOP is like a wrapper class which can be configured to be executed everytime a specific method(s) is called. EG: Here, we have defined a LoggingAspect.LoggingAdvice() method, which we call everytime Circle.getName() is called. Here, we have configured it to run BEFORE the Circle.getName() method is called.

