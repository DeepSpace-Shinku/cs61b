public class Palindrome {
    public Deque<Character> wordToDeque(String word)
    {
        Deque<Character> charDeque = new LinkedListDeque<>();
        for(int i = 0; i < word.length(); i += 1) {
            charDeque.addLast(word.charAt(i));
        }
        return charDeque;
    }

    public boolean isPalindrome(String word)
    {
        LinkedListDeque<Character> charDeque =(LinkedListDeque<Character>) wordToDeque(word);
        while (charDeque.size() > 1){
            if (!(charDeque.removeFirst() == charDeque.removeLast())){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc)
    {
        Deque<Character> charDeque = wordToDeque(word);
        while (charDeque.size() > 1){
            if (!(cc.equalChars(charDeque.removeFirst(), charDeque.removeLast()))){
                return false;
            }
        }
        return true;
    }
}
