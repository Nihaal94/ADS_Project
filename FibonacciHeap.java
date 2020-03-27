/**
 * Written by Nihaal Maipady on 03/05/20.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Fibonacci heap.
 *
 */
class FibonacciHeap<T>
{
    
    //~ Instance fields --------------------------------------------------------

    /**
     * Points to the node with the maximum key in the heap.
     */
    private Node<T> maxNode;

    /**
     * Number of nodes in the heap.
     */
    private int numNodes;


    //~ Static fields/initializers ---------------------------------------------

    private static final double oneOverLogPhi =
            1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);


    //~ Constructors -----------------------------------------------------------

    /**
     * Constructs a FibonacciHeap object that contains no elements.
     */
    public FibonacciHeap()
    {
    } // FibonacciHeap

    //~ Methods ----------------------------------------------------------------

    /**
     * Removes all elements from this heap.
     */
    public void clear()
    {
        maxNode = null;
        numNodes = 0;
    }

    // clear



    /**
     * Inserts a new data element into the heap. No heap consolidation is
     * performed at this time, the new node is simply inserted into the root
     * list of this heap.
     *
     * <p>Running time: O(1) actual</p>
     *
     * @param node new node to insert into heap
     * @param key key value associated with data object
     */
    public void insert(Node<T> node, double key)
    {
        node.key = key;

        // concatenate node into max list
        if (maxNode != null) {
            node.prev = maxNode;
            node.next = maxNode.next;
            maxNode.next = node;
            node.next.prev = node;

            if (key > maxNode.key) {
                maxNode = node;
            }
        } else {
            maxNode = node;
        }

        numNodes++;
    }

    // insert

    /**
     * Returns the smallest element in the heap. This smallest element is the
     * one with the minimum key value.
     *
     * <p>Running time: O(1) actual</p>
     *
     * @return heap node with the smallest key
     */
    public Node<T> max()
    {
        return maxNode;
    }

    // max

    /**
     * Removes the smallest element from the heap. This will cause the trees in
     * the heap to be consolidated, if necessary.
     *
     * <p>Running time: O(log n) amortized</p>
     *
     * @return node with the smallest key
     */
    public Node<T> removeMax()
    {
        Node<T> z = maxNode;

        if (z != null) {
            int numKids = z.degree;
            Node<T> x = z.child;
            Node<T> tempNext;

            // for each child of z do cutting and adding to maxList
            while (numKids > 0) {
                tempNext = x.next;

                // remove x from child list
                x.prev.next = x.next;
                x.next.prev = x.prev;

                // add x to root list of heap
                x.prev = maxNode;
                x.next = maxNode.next;
                maxNode.next = x;
                x.next.prev = x;

                // set parent[x] to null
                x.parent = null;
                x = tempNext;
                numKids--;
            }

            // remove z from root list of heap
            z.prev.next = z.next;
            z.next.prev = z.prev;

            if (z == z.next) {
                maxNode = null;
            } else {
                maxNode = z.next;
                consolidate();
            }

            // decrement size of heap
            numNodes--;
        }

        return z;
    }

    // removeMax



    protected void consolidate()
    {
        int arraySize =
                ((int) Math.floor(Math.log(numNodes) * oneOverLogPhi)) + 1;

        List<Node<T>> array =
                new ArrayList<Node<T>>(arraySize);

        // Initialize degree array
        for (int i = 0; i < arraySize; i++) {
            array.add(null);
        }

        // Find the number of root nodes.
        int numRoots = 0;
        Node<T> x = maxNode;

        if (x != null) {
            numRoots++;
            x = x.next;

            while (x != maxNode) {
                numRoots++;
                x = x.next;
            }
        }

        // For each node in root list do...
        while (numRoots > 0) {
            // Access this node's degree..
            int d = x.degree;
            Node<T> Next = x.next;

            // ..and see if there's another of the same degree.
            for (;;) {
                Node<T> y = array.get(d);
                if (y == null) {
                    // Nope.
                    break;
                }

                // There is, make one of the nodes a child of the other.
                // Do this based on the key value.
                if (x.key < y.key) {
                    Node<T> temp = y;
                    y = x;
                    x = temp;
                }

                // Node<T> y disappears from root list.
                link(y, x);

                // We've handled this degree, go to next one.
                array.set(d, null);
                d++;
            }

            // Save this node for later when we might encounter another
            // of the same degree.
            array.set(d, x);

            // Move forward through list.
            x = Next;
            numRoots--;
        }

        // Set max to null (effectively losing the root list) and
        // reconstruct the root list from the array entries in array[].
        maxNode = null;

        for (int i = 0; i < arraySize; i++) {
            Node<T> y = array.get(i);
            if (y == null) {
                continue;
            }

            // We've got a live one, add it to root list.
            if (maxNode != null) {
                // First remove node from root list.
                y.prev.next = y.next;
                y.next.prev = y.prev;

                // Now add to root list, again.
                y.prev = maxNode;
                y.next = maxNode.next;
                maxNode.next = y;
                y.next.prev = y;

                // Check if this is a new min.
                if (y.key > maxNode.key) {
                    maxNode = y;
                }
            } else {
                maxNode = y;
            }
        }
    }

    // consolidate

    /**
     * Make node y a child of node x.
     *
     * <p>Running time: O(1) actual</p>
     *
     * @param y node to become child
     * @param x node to become parent
     */
    protected void link(Node<T> y, Node<T> x)
    {
        // remove y from root list of heap
        y.prev.next = y.next;
        y.next.prev = y.prev;

        // make y a child of x
        y.parent = x;

        if (x.child == null) {
            x.child = y;
            y.next = y;
            y.prev = y;
        } else {
            y.prev = x.child;
            y.next = x.child.next;
            x.child.next = y;
            y.next.prev = y;
        }

        // increase degree[x]
        x.degree++;

        // set mark[y] false
        y.mark = false;
    }

    // link

    /**
     * Increases the key value for a heap node, given the new value to take on.
     * The structure of the heap may be changed and will not be consolidated.
     *
     * <p>Running time: O(1) amortized</p>
     *
     * @param x node to decrease the key of
     * @param k new key value for node x
     *
     * @exception IllegalArgumentException Thrown if k is larger than x.key
     * value.
     */
    public void increaseKey(Node<T> x, double k)
    {
        x.key += k;

        Node<T> y = x.parent;

        if ((y != null) && (x.key > y.key)) {
            cut(x, y);
            cascadingCut(y);
        }

        if (x.key > maxNode.key) {
            maxNode = x;
        }
    }

    // increaseKey

    /**
     * The reverse of the link operation: removes x from the child list of y.
     * This method assumes that min is non-null.
     *
     * <p>Running time: O(1)</p>
     *
     * @param x child of y to be removed from y's child list
     * @param y parent of x about to lose a child
     */
    protected void cut(Node<T> x, Node<T> y)
    {
        // remove x from childlist of y and decrement degree[y]
        x.prev.next = x.next;
        x.next.prev = x.prev;
        y.degree--;

        // reset y.child if necessary
        if (y.child == x) {
            y.child = x.next;
        }

        if (y.degree == 0) {
            y.child = null;
        }

        // add x to root list of heap
        x.prev = maxNode;
        x.next = maxNode.next;
        maxNode.next = x;
        x.next.prev = x;

        // set parent[x] to nil
        x.parent = null;

        // set mark[x] to false
        x.mark = false;
    }

    // cut

    /**
     * Performs a cascading cut operation. This cuts y from its parent and then
     * does the same for its parent, and so on up the tree.
     *
     * <p>Running time: O(log n); O(1) excluding the recursion</p>
     *
     * @param y node to perform cascading cut on
     */
    protected void cascadingCut(Node<T> y)
    {
        Node<T> z = y.parent;

        // if there's a parent...
        if (z != null) {
            // if y is unmarked, set it marked
            if (!y.mark) {
                y.mark = true;
            } else {
                // it's marked, cut it from parent
                cut(y, z);

                // cut its parent as well
                cascadingCut(z);
            }
        }
    }

    // cascadingCut

}

