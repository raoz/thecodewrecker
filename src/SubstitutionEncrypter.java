import java.util.Map;

public class SubstitutionEncrypter extends KeyedEncrypter{
    public SubstitutionEncrypter(String key) {
        super(key);
    }

    public SubstitutionEncrypter(Map<Character, Character> key){
        super(key);
    }

    @Override
    public String encrypt(String s) {
        return null;
    }

    @Override
    public Map<Character, Character> getKey() {
        return (Map<Character, Character>)super.getKey();
    }

    @Override
    public void setKey(Object key) {
        if (!(key instanceof Map)){
            throw new IllegalArgumentException("Substitution Encrypter needs a Map!");
        }
        super.setKey(key);
    }
}
