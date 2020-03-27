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
     * first child node
     */
    Node<T> child;

    /**
     * left sibling node
     */
    Node<T> prev;

    /**
     * parent node
     */
    Node<T> parent;

    /**
     * right sibling node
     */
    Node<T> next;

    /**
     * true if this node has had a child removed since this node was added to
     * its parent
     */
    boolean mark;

    /**
     * key value for this node
     */
    double key;

    /**
     * number of children of this node (does not count grandchildren)
     */
    int degree;

    //~ Constructors -----------------------------------------------------------

    /**
     * Default constructor. Initializes the right and left pointers, making this
     * a circular doubly-linked list.
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
     * Obtain the key for this node.
     *
     * @return the key
     */
    public final double getKey()
    {
        return key;
    }

    /**
     * Obtain the data for this node.
     *
     * @return the data
     */
    public final T getData()
    {
        return data;
    }

}

