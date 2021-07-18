import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

/** A word trie set that can only take in characters*/
public class WordTrieSet {
    private Node root;
    private final char minChar = 'a';
    private final char maxChar = 'z';

    public WordTrieSet()
    {
        // '_' is just a random character that was picked for the root.
        root = new Node('_', false, null);
    }

    /**
     * Put the Key and the Value into the set
     */
    public void put(String key) {
        //validateString(key);
        Node node = root;
        for (char c:  key.toCharArray()){
            if (node.hasChild(c)){
                node = node.getChild(c);
            }else{
                node = node.addChild(c, false);
            }
        }
        node.isKey = true;
    }

    /**
     * If the value is in the set return true, or return false.
     */
    public boolean contains(String key) {
        //validateString(key);
        Node node = root;
        for (char c:  key.toCharArray()){
            if (node.hasChild(c)){
                node = node.getChild(c);
            }else{
                return false;
            }
        }
        return node.isKey()? true: false;
    }

    public Node getRoot()
    {
        return root;
    }
    /**
    private void validateString(String s)
    {
        if (s == null) throw new IllegalArgumentException("The key shouldn't be null");
        for (char c: s.toCharArray()){
            validateChar(c);
        }
    }

    private void validateChar(char c)
    {
        if (!(c >= minChar) && (c <= maxChar)){
            throw new IllegalArgumentException("The character " + c + " is not a lowercase Latin letter.");
        }
    }*/

    public static class Node {
        private char c;
        private boolean isKey;
        private Map<Character, Node> children;
        private Node parent;
        //private final char minChar = 'a';
        //private final char maxChar = 'z';

        private Node(char c, boolean isKey, Node parent) {
            //validateChar(c);
            this.c = c;
            this.isKey = isKey;
            children = new HashMap<>();
        }

        public Node addChild(char c, boolean isKey)
        {
            //validateChar(c);
            Node child = new Node(c, isKey, this);
            children.put(c, child);
            return child;
        }

        public boolean hasChild(char c)
        {
            //validateChar(c);
            return children.containsKey(c);
        }

        public char getChar()
        {
            return c;
        }

        public boolean isKey()
        {
            return isKey;
        }

        public Node getChild(char c)
        {
            //validateChar(c);
            return children.get(c);
        }

        /**rivate void validateChar(char c)
        {
            if (!(c >= minChar) && (c <= maxChar)){
                throw new IllegalArgumentException("The character " + c + " is not a lowercase Latin letter.");
            }
        }*/

        /**
        @Override
        public boolean equals(Object o)
        {
            if (o == null) return false;
            if (o.getClass() != this.getClass()) return false;
            Node n = (Node) o;
            if (n.getChar() == this.getChar() && n.isKey() == this.isKey()) return true;
            else return false;
        }*/

    }

    public static void main(String[] args)
    {
        /** The main method is intended to test the Node class.*/
        Node n1 = new Node('a', false, null);
        assertEquals('a', n1.getChar());
        assertFalse(n1.isKey());
        assertFalse(n1.hasChild('b'));
        n1.addChild('b', false);
        assertTrue(n1.hasChild('b'));
        assertEquals('b', n1.getChild('b').getChar());
    }
}
