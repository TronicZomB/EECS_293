import java.util.HashMap;
import java.util.Map;

/**
 * Types of tokens an English Number
 * can contain.
 * 
 */
enum TokenType {
    /** "zero" or "naught" */
    ZERO("zero|naught"),
    /** "minus" or "negative" */
    MINUS("minus|negative"),
    MILLION("million"),
    THOUSAND("thousand"),
    HUNDRED("hundred"),
    TEN("ten"),
    /**
     * "twenty", "thirty", etc. 
     * Token's value should represent tens digit
     */
    NTY("twenty|thirty|forty|fifty|sixty|seventy|eighty|ninety"){
        private Map<String, Integer> values = new HashMap<String, Integer>(8);
        {
            values.put("twenty", 2);
            values.put("thirty", 3);
            values.put("forty", 4);
            values.put("fifty", 5);
            values.put("sixty", 6);
            values.put("seventy", 7);
            values.put("eighty", 8);
            values.put("ninety", 9);
        }
        
        @Override
        protected Map<String, Integer> getValues() {
            return values;
        }
    },
    /**
     * "eleven", "twelve", etc.
     * Token's value should represent ones digit
     */
    TEEN("eleven|twelve|thirteen|fourteen|fifteen|sixteen|seventeen|eighteen|nineteen"){
        private Map<String, Integer> values = new HashMap<String, Integer>(9);
        {
            values.put("eleven", 1);
            values.put("twelve", 2);
            values.put("thirteen", 3);
            values.put("fourteen", 4);
            values.put("fifteen", 5);
            values.put("sixteen", 6);
            values.put("seventeen", 7);
            values.put("eighteen", 8);
            values.put("nineteen", 9);
        }
        
        @Override
        protected Map<String, Integer> getValues() {
            return values;
        }
    },
    /**
     * "one", "two", etc.
     * Token's value should represent ones digit
     */
    DIGIT("one|two|three|four|five|six|seven|eight|nine"){
        private Map<String, Integer> values = new HashMap<String, Integer>(9);
        {
            values.put("one", 1);
            values.put("two", 2);
            values.put("three", 3);
            values.put("four", 4);
            values.put("five", 5);
            values.put("six", 6);
            values.put("seven", 7);
            values.put("eight", 8);
            values.put("nine", 9);
        }
        
        @Override
        protected Map<String, Integer> getValues() {
            return values;
        }
    };
    
    /** The pattern that matches this token */
    private String pattern;
    
    /** 
     * Sets the pattern 
     * 
     * @param pattern the string pattern to set as this TokenType's pattern
     */
    private TokenType(String pattern){
        this.pattern = pattern;
    }
    
    /** 
     * Gets the pattern that matches this token 
     * 
     * @return the string pattern for this token
     */
    String getPattern(){
        return pattern;
    }
    
    /** 
     * Gets the value for this token based on its original String 
     * 
     * @return the integer value for this token, or -1 if no value was found
     */
    int getValue(String s){
        Map<String, Integer> values = getValues();
        if (values.containsKey(s)){
            return values.get(s);
        }
        
        return NumberToken.NO_VAL;
    }
    
    /**
     * Mapping of strings to what token values 
     * Default implementation is an empty map
     * 
     * @return an empty HashMap by default
     */
    protected Map<String, Integer> getValues(){
        return new HashMap<String, Integer>();
    }
}
