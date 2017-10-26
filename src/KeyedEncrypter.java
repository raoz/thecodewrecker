abstract class KeyedEncrypter implements Encrypter{
    Object key;

    public KeyedEncrypter(Object key) {
        this.key = key;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }
    public String encrypt(String s){
        return "";
    }
}
