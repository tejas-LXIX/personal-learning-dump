package org.tejas.aop.aspect;

//describing a custom annotation for AOP. In our LoggingAspect, we write our Pointcut so that any method that has this annotation will be advised by our adviser.
public @interface Loggable {
}
