/**
 * Author: Levin Weinstein
 * File: Hashtable.java
 *
 * This implementation of a hashtable uses chaining.
 */

import java.util.LinkedList;
import java.util.List;

public class Hashtable {

    // User a prime number for array size to minimize collisions
    private final int ARRAY_SIZE = 49157;
    //Make our array an array of lists, for chaining
    private List<HashtableEntry>[] innerArray;



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
        innerArray = new List[ARRAY_SIZE];

        for (int i = 0; i < ARRAY_SIZE; i++)
            innerArray[i] = new LinkedList<>();
    }


    public void put(String key, String value){
        List<HashtableEntry> chain = innerArray[Math.abs(key.hashCode()) % ARRAY_SIZE];

        HashtableEntry newEntry = new HashtableEntry(key, value);

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key)) {
                entry.val = value;
                return ;
            }
        }
        chain.add(newEntry);
    }

    public String get(String key){
        HashtableEntry tmp = new HashtableEntry(key, key);

        List<HashtableEntry> chain = innerArray[Math.abs(key.hashCode()) % ARRAY_SIZE];

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key))
                return entry.val;
        }
        return null;
    }

    public String remove(String key){
        HashtableEntry tmp = new HashtableEntry(key, key);

        List<HashtableEntry> chain = innerArray[Math.abs(key.hashCode()) % ARRAY_SIZE];

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key)) {
                String ret = entry.val;
                chain.remove(entry);
                return ret;
            }
        }
        return null;
    }

    public boolean containsKey(String key){
        HashtableEntry tmp = new HashtableEntry(key, key);

        List<HashtableEntry> chain = innerArray[Math.abs(key.hashCode()) % ARRAY_SIZE];

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key))
                return true;
        }
        return false;
    }
}
