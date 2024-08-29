package org.tejas.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.tejas.aop.model.Circle;
//the way to make a class an Aspect is by using this annotation @Aspect. The methods inside an Aspect class are called Advices. These advices can be applied to any other methods.

@Aspect
public class LoggingAspect {

    //we want this loggingadvice to run BEFORe the execution of any getMethod (any method that starts with the word "get".), irrespective of the returnType (since we have put * in place of the returnType as well). This * is called a wildcard.
    //that's why this method runs when we do a getCircle() as well.
//    @Before("allGetters()")
//    public void LoggingAdvice() {
//        System.out.println("Advice run. Get method called.");
//    }
//
//    @Before("allGetters()")
//    public void secondAdvice() {
//        System.out.println("Second Advice executed.");
//    }
//
//    //to run this advice only when both the pointcuts are satisfied. In this case, this advice will run only when the method being executed is any get() method inside the Circle class.
//    @Before("allGetters() && allMethodsOfCircleClass()")
//    public void ThirdAdvice() {
//        System.out.println("Third Advice run.");
//    }

    //execute this advice after method execution. this advice runs after the method runs, no matter whether the method completed successfully or not. the advice will execute even if the method threw an exception.
//    @After("args(name)")
//    public void stringArgumentMethods(String name) {
//        System.out.println("A method that takes String arguments has been called. We are also able to access the argument in the advice.");
//        System.out.println("The argument is : " + name);
//        System.out.println();
//    }

    //this advice executes AFTER method execution, ONLY IF the method executed and returned successfully. if any exception is encountered when the method runs, then this advice won't be triggered.
    //returning is to access the returnValue of the method that triggered this advice.
    @AfterReturning(pointcut = "args(name)", returning = "returnString")
    public void stringArgumentMethodsAfterReturning(String name, String returnString) { //the parameter types here for this advice, tell Spring that this advice should be executed only when a method that takes a String arg is called, and the method's returnType should also be a String.
        System.out.println("The method executed successfully. that's why this advice was triggered.");
        System.out.println("The parameter passed to this method was: " + name);
        System.out.println("The returnString is: " + returnString);
        System.out.println();
    }

    //this advice executes AFTER method execution, ONLY IF the method failed to execute successfully. It won't be called upon successful execution.
    @AfterThrowing(pointcut = "args(name)", throwing = "ex")
    public void exceptionAdvice(String name, RuntimeException ex) {
        System.out.println("The method failed to execute successfully. That's why this advice was triggered.");
        System.out.println(ex);
        System.out.println();
    }


    //JoinPoint means all the places in your code where you can apply an advice.
    //in spring aop, joinpoints are just methods. because we can only apply advices to method calls. But, if you use aspectj, then data members can be joinpoints as well. EG: we can run an advice whenever the value of a datamember changes(with or without using the variable's setter method).
    //JoinPoint has information about the actual method call that triggered this advice. Using this, we can run different code if required based on which method triggered this advice.
    @Before("allMethodsOfCircleClass()")
    public void FourthAdvice(JoinPoint joinPoint) {
        System.out.println("Fourth Advice run. " + joinPoint.toString());
        Circle circle = (Circle) joinPoint.getTarget();  //joinPoint.getTarget() gives us the object whose method was called that triggered the advice.
        circle.callThisMethodFromInsideTheAdvice();
        System.out.println();
    }


    //to run the advice method BEFORE and AFTER the method execution. basically, this advice will run AROUND the method.
    //the first condition for an Around advice method is that this advice has to take a compulsory method argument (called the ProceedingJoinPoint). it can take more arguments as well if required, but proceedingjoinpoint is compulsory.
    //the second condition is that if you want the actual target method to get executed (the method that triggered the advice), you need to use proceedingJoinPoint.proceed() method. this will start executing the target method.
//    @Around("allGetters()")
//    public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
//        Object returnValue = null;
//        try {
//            System.out.println("myAroundAdvice(). Before advice");
//            returnValue = proceedingJoinPoint.proceed();  //the line that actually executes the target method. so all lines before this are essentially @Before, and lines after this are @After.
//            System.out.println("myAroundAdvice(). After Returning");
//        } catch (Throwable e) {
//            System.out.println("myAroundAdvice(). After Throwing");
//        }
//        System.out.println("myAroundAdvice(). After Finally");
//        return returnValue; //the returnValue can be modified in @Around block. this can't be done in an @AfterReturning block.
//    }

    //telling this advice to run for all target methods that are annotated with this annotation. this is very readable, because now you don't have to play around with method signatures and classes. straightforward hai.
    @Around("@annotation(org.tejas.aop.aspect.Loggable)")
    public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;
        try {
            System.out.println("myAroundAdvice(). Before advice");
            returnValue = proceedingJoinPoint.proceed();  //the line that actually executes the target method. so all lines before this are essentially @Before, and lines after this are @After.
            System.out.println("myAroundAdvice(). After Returning");
        } catch (Throwable e) {
            System.out.println("myAroundAdvice(). After Throwing");
        }
        System.out.println("myAroundAdvice(). After Finally");
        return returnValue; //the returnValue can be modified in @Around block. this can't be done in an @AfterReturning block.
    }


    //allGetters is a dummy method. it's only use is to hold a Pointcut expression. Now, instead of specifying the big joinPoint everywhere, we can just specify the PointCut to specify where we want our advice to be executed.
    @Pointcut("execution(public * get*())")
    public void allGetters() {}

    @Pointcut("within(org.tejas.aop.model.Circle)")
    public void allMethodsOfCircleClass() {}

}


//we want this loggingadvice to run BEFORE the execution of a method with a signature like public String getName().
//@Before("execution(public String getName())")

//we want this loggingadvice to run BEFORE the execution of the getName() method of the Circle class.
//@Before("execution(public String org.tejas.aop.model.Circle.getName())")

//we want this loggingadvice to run BEFORE the execution of the getName() method of the Circle class. But, the getName() method should have atleast one parameter. since we used *
//@Before("execution(public * get*(*))")

//we want this loggingadvice to run BEFORE the execution of the getName() method of the Circle class. But, it's not necessary that the getName() method should have atleast one parameter. since we used ..
//@Before("execution(public * get*(..))")


//JoinPoint
//A JoinPoint represents a point in your application where you can plug-in AOP aspect. You can also say, it is the actual place in the application where an action will be taken(an advice will be executed) using Spring AOP framework. Consider the following examples −
//
//All methods classes contained in a package(s).
//
//A particular methods of a class.


//Pointcut
//Pointcut is a set of one or more JoinPoint where an advice should be executed. You can specify Pointcuts using expressions or patterns as we will see in our AOP examples. In Spring, Pointcut helps to use specific JoinPoints to apply the advice. Consider the following examples −
//
//@Pointcut("execution(* com.tutorialspoint.*.*(..))")
//
//@Pointcut("execution(* com.tutorialspoint.Student.getName(..))")