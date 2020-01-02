import java.util.prefs.Preferences;
public class PreferenceTest {
    private Preferences prefs;

    public void setPreference() throws Exception {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        String ID1 = "Test1";
        String ID2 = "Test2";
        String ID3 = "Test3";

        System.out.println(prefs.getBoolean(ID1, true));
        System.out.println(prefs.get(ID2, "Hello World"));
        System.out.println(prefs.getInt(ID3, 50));

        prefs.putBoolean(ID1, false);
        prefs.put(ID2, "Hello Europa");
        prefs.putInt(ID3, 45);
        prefs.remove(ID1);
        prefs.flush();
    }

    public static void main(String[] args) throws Exception {
        PreferenceTest test = new PreferenceTest();
        test.setPreference();
    }
}