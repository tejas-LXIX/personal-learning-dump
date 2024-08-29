//An array in Java is an object (i.e., an instance of an unnamed class), so, like any object, it may have instance fields.
//Every Java array has a field defined as public int length; that stores the size of (or the number of elements in) the array.

//WHEREAS, C++ arrays are not objects.
//Alternatively, an array in C++ is a primitive, unstructured data type - it is an address that is incapable of storing additional data.
//C++ programmers must maintain a separate, distinct variable to track the size of an array.
//C++ arrays do NOT have a .length instance field.

//Empty Array in Java still points to an Object. Whereas when arr=NULL implies that arr does not point to any memory location. THIS IS THE DIFFERENCE BETWEEN EMPTY AND NULL ARRAY.

public class NullVSEmptyArray {
    public static void main(String[] args) {
        // int[] arr = new int[1];
        // arr[0]=34;
        int[] arr = new int[0]; //arr still points to an object i.e it stores the reference of the array object.
        System.out.println(arr.getClass().getConstructors());
        System.out.println(arr);
        System.out.println("SEPARATOR");
        arr = null;     //can be assigned to null, since arr is just a pointer to an array (and arrays are objects in java). It's not the array itself.
        //Unlike in C++, where an array is not a pointer; it's the array itself. so you can't assign arr to null in c++ since arr is not a pointer. arr represents itself in c++ rather than representing a separate object.

        //in java, arr exists separately as a pointer and the actual array exists as a separate entity. Therefore, arr can be reassigned to point to another array.
        // Whereas in C++, arr doesn't exist separately as a pointer.  arr represents itself and therefore can't be reassigned.

        System.out.println(arr);
        System.out.println("SEPARATOR");
        int[] arrNew = null;
        System.out.println(arrNew);
        System.out.println("SEPARATOR");
        int[] randomArr = new int[3];
        randomArr[0] = 74;
        randomArr = new int[3];
        randomArr[0] = 947;
        System.out.println(randomArr[0]);
        // int[] arr = null;
    }
}


/*
C++ code.

// Online C++ compiler to run C++ program online
#include <iostream>

using namespace std;

int main() {
    int arr[3]={1,2,3};
    cout<<arr<<endl;
    cout<<&arr<<endl;
    cout<<*arr<<endl;
    // arr=NULL;   not possible, gives Incompatible Types compilation error. Because arr is not a pointer to an array, it's the array itself.
    //error: incompatible types in assignment of 'long int' to 'int [3]'

    // int secondArr[3]=NULL;
    //error: array must be initialized with a brace-enclosed initializer

    int *newArr=new int[10];
    newArr[0]=73;
    cout<<newArr[0]<<endl;
    newArr=NULL;    //possible, since newArr is just a pointer to an array. It's not the array itself.
    // cout<<newArr[0]<<endl;   causes segmentation fault since newArr is NULL.
    cout<<newArr<<endl;
}
 */