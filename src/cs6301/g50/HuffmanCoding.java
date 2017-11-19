/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 **/

package cs6301.g50;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public final class HuffmanCoding {
    private static final int SIZE = 256;

    /*
    * Huffman result class that is used to give external functions the Huffman tree or the result
    *
    * */
    public static final class HuffResult{
        private final String encoded;
        private final HuffmanNode mapping;
        private final Map<Character, String> lookupTable;

        HuffResult(String encoded, HuffmanNode mapping, Map<Character, String> lookupTable) {
            this.encoded = encoded;
            this.mapping = mapping;
            this.lookupTable = lookupTable;
        }

        public String toString(){
            return this.encoded;
        }

        public HuffmanNode getMapping(){
            return this.mapping;
        }

        public Map<Character, String> getLookupTable(){
            return this.lookupTable;
        }
    }

    /*
    * Class that is the building block of a Huffman Tree
    *
    * */
    private static class HuffmanNode implements Comparable<HuffmanNode>{
        char ch;
        int frequency;
        HuffmanNode left;
        HuffmanNode right;

        HuffmanNode(char ch, int frequency,  HuffmanNode left,  HuffmanNode right) {
            this.ch = ch;
            this.frequency = frequency;
            this.left = left;
            this.right = right;
        }

        public int compareTo(HuffmanNode huffmanNode) {
            return this.frequency>huffmanNode.frequency?1:(this.frequency<huffmanNode.frequency?-1:0);
        }

        public boolean isLeaf() {
            return null==left&&null==right;
        }
    }

    /*
    * Function to build a Frequency map given an input sentence
    *
    * */
    private static int[] getFrequencyMap(String sentence) {
        final int[] map = new int[SIZE];
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            map[ch]++;
        }
        return map;
    }

    /*
    * Function to build a Huffman Tree given a frequency map
    *
    * */
    private static HuffmanNode buildHuffmanTree(int[] map){
        final PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        for(char i=0; i<SIZE; i++){
            if(map[i]>0){
                priorityQueue.add(new HuffmanNode(i, map[i],null,null));
            }
        }

        while (priorityQueue.size()>1){
            final HuffmanNode left = priorityQueue.poll();
            final HuffmanNode right = priorityQueue.poll();
            final HuffmanNode parent = new HuffmanNode('\0',left.frequency+right.frequency,left,right);
            priorityQueue.add(parent);
        }

        return priorityQueue.poll();
    }

    /*
    * Function to build a lookup table given a Huffman tree
    *
    * */
    private static Map<Character, String> buildLookupTable(final HuffmanNode root){
        final Map<Character, String> lookupTable = new HashMap<>();
        buildLookupTableImpl(root,"",lookupTable);
        return lookupTable;
    }

    /*
    * Implementation function to build a lookup table, given a Huffman tree
    *
    * */
    private static void buildLookupTableImpl(final HuffmanNode root, final String s, final Map<Character, String> lookupTable) {
        if(!root.isLeaf()){
            buildLookupTableImpl(root.left,s+'0',lookupTable);
            buildLookupTableImpl(root.right,s+'1',lookupTable);
        }else{
            lookupTable.put(root.ch, s);
        }
    }

    /*
    * Function to encodeData given a string and a created lookupTable
    *
    * */
    private String encodeData(final String input, final Map<Character, String> lookupTable){
        final StringBuilder builder = new StringBuilder();
        for(final char ch:input.toCharArray()){
            String encoded = lookupTable.get(ch);
            builder.append(encoded);
        }
        return builder.toString();
    }

    /*
    * Function to encode a message given String
    *
    * */
    public HuffResult encode(final String input){
        final int[] map  = getFrequencyMap(input);
        final HuffmanNode huffTree = buildHuffmanTree(map);
        final Map<Character, String> lookupTable = buildLookupTable(huffTree);
        return new HuffResult(encodeData(input,lookupTable),huffTree,lookupTable);
    }

    /*
    * Function to decode a message given a Huffman Tree
    *
    * */
    public String decodeMessage(String input, HuffmanNode node){
        final StringBuilder builder = new StringBuilder();
        final char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length;) {
            HuffmanNode temp = node;
            while (temp.left != null) {
                if (charArray[i]=='0') {
                    temp = temp.left;
                } else {
                    temp = temp.right;
                }
                i = i + 1;
            }
            builder.append(temp.ch);
        }
        return builder.toString();
    }
}