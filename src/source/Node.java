/**
 * Written by Nihaal Maipady on 03/05/20.
 */
/**
 * Implements a node of the Fibonacci heap. It holds the information necessary
 * for maintaining the structure of the heap. It also holds the reference to the
 * key value (which is used to determine the heap structure).
 *
 */
class Node<T>
{
    //~ Instance fields --------------------------------------------------------

    /**
     * Node data.
     */
    T data;

    
    /**
     * left sibling node
     */
    Node<T> prev;


    /**
     * right sibling node
     */
    Node<T> next;


    /**
     * first child node
     */
    Node<T> child;


    /**
     * parent node
     */
    Node<T> parent;


    /** Set to false by RemoveMax() operation. Set to true if this node 
     * has lost a child since it became a child of its parent
     */
    boolean childCut;

    /**
     * key value for this node
     */
    double key;

    /**
     * Number of children of this node (grandchildren not included)
     */
    int degree;

    //~ Constructors -----------------------------------------------------------

    /**
     * Default constructor initializes the next and prev pointers, making this
     * a circular doubly-linked list
     *
     * @param data data for this node
     * @param key initial key for node
     */
    public Node(T data, double key)
    {
        next = this;
        prev = this;
        this.data = data;
        this.key = key;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Get the key for this node.
     *
     * @return the key
     */
    public final double getKey()
    {
        return key;
    }

    /**
     * Get the data for this node.
     *
     * @return the data
     */
    public final T getData()
    {
        return data;
    }

}

