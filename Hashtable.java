/**
 * Author: Levin Weinstein
 * File: Hashtable.java
 *
 * This implementation of a hashtable uses chaining.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Hashtable {

    // User a prime number for array size to minimize collisions
    private final int ARRAY_SIZE = 49157;
    //Make our array an array of lists, for chaining
    private List<List<HashtableEntry>> innerArray;



    /* HashtableEntry: contains key and val */
    private static class  HashtableEntry {
        String key;
        String val;

        HashtableEntry(String k, String v){
            key = k;
            val = v;
        }

    }

    // constructor
    public Hashtable(){

        innerArray = new ArrayList<>();

        for (int i = 0; i < ARRAY_SIZE; i++)
            innerArray.add(new LinkedList<>());
    }

    /**
     * containsKey: determine whether the Hashtable contains a given key.
     *
     * @param key
     *          The key to check for in the table
     * @return
     *          Whether or not the key is in the table
     */
    public boolean containsKey(String key){
        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % ARRAY_SIZE);

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key))
                return true;
        }
        return false;
    }


    /**
     * get: gets the value for a given key from the Hashtable
     *
     * @param key
     *         The key to get the corresponding value of from the table.
     * @return
     *         The value corresponding to {key} in the table, or null if key not in table.keys.
     */
    public String get(String key){
        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % ARRAY_SIZE);

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key))
                return entry.val;
        }
        return null;
    }

    /**
     * put: Puts the value {value} at the key {key}.
     */
    public void put(String key, String value){
        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % ARRAY_SIZE);


        for (HashtableEntry entry : chain){
            if (entry.key.equals(key)) {
                entry.val = value;
                return ;
            }
        }

        HashtableEntry newEntry = new HashtableEntry(key, value);
        chain.add(newEntry);
    }

    /**
     * Remove: removed a key/value pair from the Hashtable.
     *
     * @param key
     *          the key to remove from the hashtable
     * @return
     */
    public String remove(String key){

        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % ARRAY_SIZE);

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key)) {
                String ret = entry.val;
                chain.remove(entry);
                return ret;
            }
        }
        return null;
    }

}
