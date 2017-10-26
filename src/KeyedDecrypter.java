public abstract class KeyedDecrypter extends Decrypter{
    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    private Object key;
}
