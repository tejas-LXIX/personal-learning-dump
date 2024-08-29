package org.tejas.aop.model;

public class Circle {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printStringAndThrowException(String dummy) {
        System.out.println("Inside the printStringAndThrowExceptionMethod");
        throw new RuntimeException("test");
    }

    public String printStringAndReturn(String dummy) {
        return dummy + " 123";
    }

    public void callThisMethodFromInsideTheAdvice() {
        System.out.println("This method was called from inside the advice by getting a handle on the object");
    }
}
