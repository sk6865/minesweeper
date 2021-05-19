public class GenericArrayStack<E> implements Stack<E> {

    private E[] elems;
    private int top;

   // Constructor
    @SuppressWarnings("unchecked")
    public GenericArrayStack( int capacity ) {

        elems = (E[]) new Object[capacity];
        top = 0;

    }

    // Returns true if this ArrayStack is empty
    public boolean isEmpty() { 
        return top==0;

    }

    public void push( E elem ) {
        elems[top++] = elem;


    }
    public E pop() {
        E element = elems[--top];
        return element;

    }

    public E peek() {
        return elems[top-1];

    }
}
