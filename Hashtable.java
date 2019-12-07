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

    //Make our array an array of lists, for chaining
    private List<List<HashtableEntry>> innerArray;
    private int entryCount;
    private int resizeCount;
    private final float LOAD_FACTOR = 0.65f;

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
        resizeCount = -1;
        entryCount = 0;
        int arraySize = HASHTABLE_PRIMES[++resizeCount];
        innerArray = new ArrayList<>();

        for (int i = 0; i < arraySize; i++)
            innerArray.add(new ArrayList<>());
    }

    public void resizeInnerArray(){
        int newArraySize = HASHTABLE_PRIMES[++resizeCount];
        List<List<HashtableEntry>> newInnerArray = new ArrayList<>();

        for (int i = 0; i < newArraySize; i++)
            newInnerArray.add(new LinkedList<>());

        for (int i = 0; i < innerArray.size(); i++){
            List<HashtableEntry> oldChain = innerArray.get(i);

            for (HashtableEntry entry : oldChain){
                List<HashtableEntry> newChain = newInnerArray.get(Math.abs(entry.key.hashCode()) % newArraySize);
                newChain.add(entry);
            }

        }
        innerArray = newInnerArray;
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
        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % HASHTABLE_PRIMES[resizeCount]);

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
        int arraySize = HASHTABLE_PRIMES[resizeCount];
        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % arraySize);

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
        int arraySize = HASHTABLE_PRIMES[resizeCount];

         if ((1.0f * entryCount / arraySize)  > LOAD_FACTOR) {
            resizeInnerArray();
            arraySize = HASHTABLE_PRIMES[resizeCount];
        }

        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % arraySize);

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key)) {
                entry.val = value;
                return ;
            }
        }

        entryCount++;
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
        List<HashtableEntry> chain = innerArray.get(Math.abs(key.hashCode()) % HASHTABLE_PRIMES[resizeCount]);

        for (HashtableEntry entry : chain){
            if (entry.key.equals(key)) {
                entryCount--;
                String ret = entry.val;
                chain.remove(entry);
                return ret;
            }
        }
        return null;
    }

    /**
     * List of efficient hashtable primes.
     *
     * From: https://planetmath.org/goodhashtableprimes
     */
    public final int[] HASHTABLE_PRIMES = {
                   769,       1543,       3079,       6151,
                 12289,      24593,      49157,      98317,
                196613,     393241,     786433,    1572869,
               3145739,    6291469,   12582917,   25165843,
              50331653,  100663319,  201326611,  402653189,
             805306457, 1610612741

    };

}
