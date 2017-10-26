import java.util.Map;

public class SubstitutionEncrypter extends KeyedEncrypter{
    Map<Character, Character> key;
    public SubstitutionEncrypter(String key) {
        super();
    }

    public SubstitutionEncrypter(Map<Character, Character> key){
        super();
        this.key =  key;
    }

    @Override
    public String encrypt(String s) {
        return null;
    }
}
