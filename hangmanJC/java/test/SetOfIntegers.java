/*    test.SetOfIntegers.java
 *
 *      $Id: SetOfIntegers.java,v 1.1 2007/04/10 23:47:02 kutoriku Exp $
 *
 *      test.SetOfIntegers
 *      A set of elements
 *
 *      maj             2002/03/26
 *      date            $Date: 2007/04/10 23:47:02 $
 *      revision        $Revision: 1.1 $
 *
 * ---------------------------------------------------------------------------
 */
package test;

import  java.util.*;

class VideAvecTest {
    /*
     * @tstart
     *  @tunit unit1 : tunit unit 1
     *  @tustart
     *   System.out.println("test");
     *  @tuend
     * 
     *  @tcase case1 : case 1
     *   @list unit1
     *  @tcend
     * 
     *  @tsuite suite : suite de VideAvecTest0
     *       @list case1 
     *  @tsend
     * @tend
     */
}


/**
 *  Description
 *
 *    A trivial set of Integers.
 *    The set stores references to Integer objects.
 *
 *  Principle and features
 *
 *    There can not be two equal Integers in the set.
 *
 *  Conventions
 *
 *    Queries return a result but do not change the visible properties of
 *    the object. Commands might change the object but do not return a result.
 *
 * @author JFLC
 * @version $Revision: 1.1 $
 *
 * @invariant   size() >= 0     // the size of a set should always be positive or equal to zero
 */
public class SetOfIntegers extends VideAvecTest {
    // Features: Instance variables ------------------------------------------

    /**
     * elements : containing the elements
     */
    private Integer[] content;

    /**
     * numberOfElements : number of elements in the set
     */
    private int numberOfElements;

    /**
     * capacity : capacity of the set
     */
    private int capacity;

    // Features: Public constants --------------------------------------------

    /**
     * DEFAULT_MAX_CAPACITY : max capacity of the set if not specified at instanciation
     */
    public static final int DEFAULT_MAX_CAPACITY = 100;

    /*
     *          Public methods
     */

    // Features: Constructors ------------------------------------------------


    /**
     * SetOfIntegers constructor with default capacity
     *
     * Builds a new empty set with a default maximum number of elements.
     *
     * @post size() == 0                        // empty at construction
     * @post getCapacity() == DEFAULT_MAX_CAPACITY  // capacity to default
     */
    public SetOfIntegers() {
        capacity = DEFAULT_MAX_CAPACITY;
        content = new Integer[capacity];
        numberOfElements = 0;
    } // ----------------------------------------------------- SetOfIntegers()

    /**
     * SetOfIntegers constructor with capacity
     *
     * Builds a new empty set that may contain a maximum of maxSize elements.
     *
     * @post size() ==  0               // empty at construction
     * @post getCapacity() == maxSize   // capacity initialized
     */
    public SetOfIntegers(int maxSize) {
        capacity = maxSize;
        content = new Integer[capacity];
        numberOfElements = 0;
    } // ----------------------------------------------------- SetOfIntegers()

    /**
     * SetOfIntegers constructor with initialization
     *
     * Builds a new set that will be equal to the parameter 'otherSet'.
     * Each element of the parameter set is cloned before being added to the
     * new set. The new set will have the same maximum number of elements as
     * the parameter one. 
     *
     * @pre otherSet != null                    // argument not null
     *
     * @post size() == otherSet.size()          // same size as parameter
     * @post equals(otherSet)                   // equal to parameter 
     */
    public SetOfIntegers(SetOfIntegers otherSet) {
        Integer element;

        capacity = otherSet.getCapacity();
        content = new Integer[capacity];
        numberOfElements = 0;
        for( Enumeration e = otherSet.elements(); e.hasMoreElements(); ) {
            element =(Integer)e.nextElement();
            add(new Integer(element.intValue()));
        } // for ...
    } // ----------------------------------------------------- SetOfIntegers()

    /*
     *          Modifiers
     */

    // Features: add and remove ----------------------------------------------

    /**
     * adds an Integer to the set.
     *
     * The argument is added to the set. The size is increased by one.
     * Ensure the element is not in the set before trying to add it.
     * This is a command: it changes the object but does not return
     * any result.
     *
     * @param element     the element to be added to the set.
     *
     * @pre element != null     // argument not null
     * @pre !has(element)       // argument not already in the set
     * @pre !isFull()           // current set not full
     *
     * @post size() == size()@pre + 1   // size increased
     * @post has(element)               // argument now in the set
     */
    public void add(Integer element) {

        content[numberOfElements] = element;
        numberOfElements++;
    } // --------------------------------------------------------------- add()

    /**
     * removes an element from the set.
     *  
     * The argument is removed from the set. The size is decreased by one.
     * Ensure the element is in the set before trying to remove it.
     * This is a command: it changes the object but does not return
     * any result.
     *
     * @param element     the element to be removed.
     *
     * @pre element != null             // argument not null
     * @pre has(element)                // set contains argument
     *
     * @post size() == size()@pre - 1   // size decreased.
     * @post !has(element)              // argument not in set any more
     */
    public void remove(Integer element) {
        int     i;

        /*
         * First we have to find the element to be removed
         */
        i = 0;
        while((i < numberOfElements) &&(!element.equals(content[i])) ) {
            i++;
        }       // while ...

        /*
         * Then we shift following elements
         */
        i++;
        while( i < numberOfElements ) {
            content[i - 1] = content[i];
            i++;
        }       // while ...

        numberOfElements--;
    } // ------------------------------------------------------------ remove()

    /*
     *          Accessors
     */

    // Features: basic querys ------------------------------------------------

    /**
     * maximum number of elements the set may contain
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @return The maximum number of elements the set may contain.
     */
    public int getCapacity() {
        return(capacity);
    } // ------------------------------------------------------- getCapacity()

    /**
     * returns the size of the set
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @return the number of elements currently in the set
     */
    public int size() {
        return(numberOfElements);
    } // -------------------------------------------------------------- size()

    /**
     * returns an Enumeration containing the elements of the set
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @return an Enumeration containing the elements of the set
     */
    public Enumeration elements() {
        Vector vector;

        vector = new Vector();
        for( int i = 0; i < numberOfElements; i++ ) {
            vector.add(content[i]);
        }       // for ...

        return(vector.elements());
    } // ---------------------------------------------------------- elements()

    // Features: derived queries ---------------------------------------------

    /**
     * is the set empty ?
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @return true if the set is empty
     *
     * @post return ==( size() == 0 )  
     *                  // an empty set has a size equal to zero
     */
    public boolean isEmpty() {
        return(size() == 0);
    } // ----------------------------------------------------------- isEmpty()

    /**
     * is the set full ?
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * This implementation uses an array created with a limited size.
     * So it is considered full when the number of elements in the set
     * reaches the maximum authorized size.
     *
     * @return true if the set is full
     *
     * @post return ==( size() >= getCapacity() )      // the set is full
     */
    public boolean isFull() {
        return(size() >= getCapacity());
    } // ------------------------------------------------------------ isFull()

    /**
     * is the argument in the current set?
     *
     * Tests against equality.
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @param element The element to be controlled.
     * @return true if the argument is in the set
     *
     * @pre element != null                     // argument not null
     *
     * @post return == true implies 
     *  exists Integer e in elements() |(e.equals(element) == true)
     *                                          // argument in the set
     * @post return == false implies 
     *  forall Integer e in elements() |(e.equals(element) == false)   
     *                                          // argument not in the set
     */
    public boolean has(Integer         element) {
        int     i;
        boolean result;

        result = false;
        i = 0;
        while((i < numberOfElements) &&(!result) ) {
            result = element.equals(content[i]);
            i++;
        }       // while ...

        return(result);
    } // --------------------------------------------------------------- has()

    // Features: sets compare ------------------------------------------------

    /**
     * tests set equality
     *
     * Returns `true` if all elements of the two sets are the same
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @param otherSet set to be compared to the current one
     * @return true if all the elements of the two sets are the same
     *
     * @pre otherSet != null                    // argument not null
     *
     * @post return == true implies size() == otherSet.size()
     *                  // identical size is necessary for set equality
     * @post return == true implies 
     *         forall Integer e in elements() |(otherSet.has(e))       
     *                  // the current set is equal to the argument one
     * @post return == false implies size() != otherSet.size()
     *                  // different sizes means different sets
     * @post (return == false &&size() == otherSet.size()) implies
     *          exists Integer e in elements() |(!otherSet.has(e))
     *                  // the current set is not equal to the argument one
     */
    public boolean equals(SetOfIntegers   otherSet) {
        int i;
        boolean result;

        result =(size() == otherSet.size());
        i = 0;
        while( result &&(i < numberOfElements) ) {
            result = otherSet.has(content[i]);
            i++;
        }       // while ...

        return(result);
    } // ------------------------------------------------------------ equals()

    /**
     * is the current set a subset of the argument?
     *  
     * Returns `true` if all elements of the current set are in `otherSet`
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @param otherSet set that should contain the current one
     * @return true if the argument set contains the current one
     *
     * @pre otherSet != null                    // argument not null
     *
     * @post return == true implies
     *          forall Integer e in elements() |(otherSet.has(e))
     *                  // current set is a subset of the argument one
     * @post return == false implies
     *         exists Integer e in elements() |(otherSet.has(e) == false)
     *                  // current set is a subset of the argument one
     */
    public boolean isSubSetOf(SetOfIntegers   otherSet) {
        int i;
        boolean result;

        result =(size() <= otherSet.size());
        i = 0;
        while( result &&(i < numberOfElements) ) {
            result = otherSet.has(content[i]);
            i++;
        }       // while ...

        return(result);
    } // -------------------------------------------------------- isSubSetOf()


    // Features: set operations ----------------------------------------------

    /**
     * performs the union of the current set with the argument one.
     *
     * Returns a new set that is the union of the current set with the
     * argument one.
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @param otherSet set with which the union should be performed
     * @return a new set that is the union of the current with the argument
     *
     * @pre otherSet != null                    // argument is not null
     *
     * @post isSubSetOf(return)         // resulting set contains the current
     * @post otherSet.isSubSetOf(return)        
     *                                  // resulting set contains the argument
     * @post return.size() == size() + otherSet.size() - inter(otherSet).size()
     *                                  // right count of elements
     */
    public SetOfIntegers union(SetOfIntegers   otherSet) {
        SetOfIntegers result;
        Integer element;

        result = new SetOfIntegers(this);

        for( Enumeration e = otherSet.elements(); e.hasMoreElements(); ) {
            element =(Integer)e.nextElement();
            if(! result.has(element)) {
                result.add(element);
            }
        }       // while ...

        return(result);
    } // ------------------------------------------------------------- union()

    /**
     * performs the intersection of the current set with the argument one.
     *
     * Returns a new set that is the intersection of the current set with the
     * argument one.
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @param otherSet     set with which the intersection should be performed
     * @return         a new set that is the intersection of the current set with the
     *         argument one
     *
     * @pre otherSet != null                    // argument not null
     *
     * @post return.isSubSetOf(this)            
     *                  // resulting set is a subset of the current one
     * @post return.isSubSetOf(otherSet)
     *                  // resulting set is a subset of the argument one
     */
    public SetOfIntegers inter(SetOfIntegers    otherSet) {
        SetOfIntegers result;

        result = new SetOfIntegers();

        for( int i = 0; i < numberOfElements; i++ ) {
            if( otherSet.has(content[i]) ) {
                result.add(content[i]);
            }   // if ...
        }       // for ...
        return(result);
    } // ------------------------------------------------------------- inter()

    /**
     * performs the substraction of the current set with the argument one.
     *  
     * Returns a new set that is the sets substraction, i.e. 
     *          `current set` - `argument set`
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @param otherSet set to be substracted to the current one
     * @return a new set: current set minus argument set
     *
     * @pre otherSet != null                    // argument not null
     *
     * @post return.isSubSetOf(this)
     *          // the resulting set is a subset of the current one
     * @post forall Integer e in return.elements() |(!otherSet.has(e))
     *          // resulting set and argument one have no common element
     */
    public SetOfIntegers minus(SetOfIntegers   otherSet) {
        SetOfIntegers result;

        result = new SetOfIntegers();

        for( int i = 0; i < numberOfElements; i++ ) {
            if( ! otherSet.has(content[i]) ) {
                result.add(content[i]);
            }   // if ...
        }       // for ...


        return(result);
    } // ------------------------------------------------------------- minus()


    // Features: operations on set elements ----------------------------------

    /**
     * computes the sum of the Integers that are in the set
     *  
     * Returns null when the sum is greater than Integer.MAX_VALUE
     * or lower than Integer.MIN_VALUE.
     * Otherwise it returns the result into an Integer object.
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * @return The sum of the Integers that are in the set, or null if this
     *         sum is out of the domain for Integers.
     *
     * @pre !isEmpty()      // one can not ask for the sum of an empty set
     * 
     * @post true       // if result is null, sum > Integer.MAX_VALUE || 
     *                                        sum < Integer.MIN_VALUE
     */
    public Integer sum() {
        Integer result;
        long tmp;
        int i;
        boolean overflow;

        overflow = false;
        tmp = 0;
        i = 0;
        while((i < numberOfElements) &&(!overflow) ) {
            tmp += content[i].longValue();
            overflow =(tmp > Integer.MAX_VALUE) ||(tmp < Integer.MIN_VALUE);
            i++;
        }       // while ...

        if(overflow) {
            result = null;
        } else {
            result = new Integer((int)tmp);
        }

        return(result);
    } // --------------------------------------------------------------- sum()


    // Features: set conversion ----------------------------------------------

    /**
     * string displaying the entire set
     *
     * This is a query: it returns a result but does not change
     * the visible properties of the object.
     *
     * Returns a `String` that displays the set :
     *     empty set : {}
     *     set containing elements 1,2 and 3 : {1,2,3}
     * The element order is not important ; the second example could also
     * show {3,1,2}.
     *
     * @return a string showing the set
     *
     * @post return != null && return.length() > 0 // returns a valid string
     */
    public String toString() {
        String result;
        int i;

        result = "{";
        for( i = 0; i < numberOfElements-1; i++ ) {
            result = result + content[i] + ",";
        } // for ...
        if( i ==(numberOfElements-1) ) {
            result = result + content[i];
        } // if

        result = result + "}";
        return(result);
    } // ---------------------------------------------------------- toString()


    /*
     
    @tstart
         *  @tcreate new SetOfIntegers(15)
            
            
         *  @tunit defaultConstructor : default constructor and accessors
         *    basic accessors are used to control the object state. Not any oracle
         *    defined, post-condition is complete
         *   @tustart
         *    testMsg("object created");
         *    testMsg("size: " + size());
         *    testMsg("capacity: " + getCapacity());
         *  @tuend
     
         *  @tunit withCapConstructor : default constructor and accessors
         *    basic accessors are used to control the object state. Not any oracle
         *    defined, post-condition is complete
         *   @tustart
         *    testMsg("object created");
         *    testMsg("size: " + size());
         *    testMsg("capacity: " + getCapacity());
         *  @tuend
     
             *  @tunit orphanUnit : test of an orphan unit test
             *    default comment with default constructor
             *   @tustart
             *    testMsg("i am an orphan unit");
             *  @tuend
             
         *
         *  @tcase defaultConstruct :  default constructor test
         *   The default constructor is used by default(external tcreate)
         *   @list defaultConstructor
         *   @postamb
         *          String truc = new String("ceci est essai");
         *  @tcend
         *
         *  @tcase withCapConstruct :  with capacity constructor test
         *    Here, a local constructor is defined
         *   @tcreate new SetOfIntegers(8); 
         *   @list withCapConstructor
         *  @tcend
         *
         *  @tsuite construct : test the simples constructors
         *   The copy-constructor(clone) will be tested later in the add-remove
         *   test suite.
         *   @list
         *    defaultConstruct withCapConstruct
         *  @tsend
         *
         *  @tunit pass addInEmpty : add elements in empty set
         *   @tustart
         *     testMsg("size: " + size());
         *     testCheck("empty image ok", toString().equals("{}"));
         *     testMsg("empty image: " + toString());
         *     testMsg("Add '1'");
         *     add(el[1]);
         *     testMsg("size: " + size());
         *     testMsg("Has '1': " + has(el[1]));
         *     testCheck("set image ok", toString().equals("{1}"));
         *     testMsg("image: " + toString());
         *     testMsg("Add '2'");
         *     add(el[2]);
         *     testMsg("size: " + size());
         *     testMsg("Has '2': " + has(el[2]));
         *     testCheck("set image ok", toString().equals("{1,2}") ||
         *                                toString().equals("{2,1}"));
         *     testMsg("image: " + toString());
         *     testMsg("Add '6'");
         *     add(el[6]);
         *     testMsg("size: " + size());
         *     testMsg("Has '6': " + has(el[6])); // el[6]
         *     testCheck("set image ok", toString().equals("{1,2,6}"));
         *  //ca ne fait plus tout planter
         *     testMsg("image: " + toString());
         *  @tuend
     
         *  @tunit fail verif badAdd : double add test
         *     This test should throw a violation contract exception
         *   @tustart
         *     add(el[3]);
         *     testMsg("added '3'");
         *     add(el[4]);
         *     testMsg("added '4'");
             *     testMsg("size: " + size());
             *         testCheck("size is 2", size()==2);
         *     add(el[3]);
         *     testMsg("added '3'");
         *  @tuend
         *   
            @tunit valid fullAdd : add until fullness and remove
         *   @tustart
         *     for(int i = 1; i <= 15 ; i++) {
         *         add(new Integer(i));
         *         testMsg("Added value: " + i);
         *     }
         *     testCheck("should be full", isFull());
         *     testMsg("is full: " + isFull());
         *     testMsg(toString() + "\n");
         *     remove(el[9]);
         *     testCheck("should not be full", !isFull());
         *     testMsg("is not full: " + !isFull());
         *  @tuend
         *   
            @tunit reAdd : add, remove and readd
         *   @tustart
         *     for(int i = 0; i < 10 ; i++) {
         *         add(el[i]);
         *         testMsg(toString());
         *     }
         *     testCheck("should contain 1O elem", size() == 10);
         *     testMsg("contains " + size() + " elements");
         *     testMsg("remove '2' and '7'");
         *     remove(el[2]);
         *     remove(el[7]);
         *     testMsg(toString());
         *     testCheck("should contain 8 elem", size() == 8);
         *     testMsg("contains " + size() + " elements");
         *     add(el[7]);
         *     testMsg("'7' readded");
         *     testCheck("should contain 9 elem", size() == 9);
         *     testMsg("contains " + size() + " elements");
         *  @tuend
         *   
            @tunit access : accessors test
         *     Verfy that derived accessors(empty(), has()) are ok
         *   @tustart
         *     testCheck("set empty", isEmpty());
         *     add(el[1]); add(el[2]); add(el[6]); add(el[8]);
         *     testMsg(toString());
         *     testCheck("set not empty", !isEmpty());
         *     testCheck("has '2'", has(el[2]));
         *     testCheck("has '6'", has(el[6]));
         *     testCheck("not has '5'", !has(el[5]));
         *     testMsg("all checks passed");
         *     remove(el[1]); remove(el[2]); remove(el[6]); remove(el[8]);
         *     testMsg(toString());
         *     testCheck("set empty", isEmpty());
         *     testCheck("not has '2'", !has(el[2]));
         *  @tuend
     
     
     
         *  @tcase populate : add and remove methods test
         *    This test activates also basic accessors and viewer
         *   @preamb
         *    Integer[] el = new Integer[10];
         *    for(int i = 0; i < 10 ; i++) {
         *        el[i] = new Integer(i);
         *    }
         *   @list addInEmpty badAdd fullAdd reAdd access
         *  @tcend
             *
         *
         *@tend
    */
}

