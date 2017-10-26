import java.util.Map;
//TODO: this class
public class SubstitutionEncrypter implements KeyedEncrypter{
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
