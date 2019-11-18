import org.simonscode.telegrammenulibrary.Callback;
import org.simonscode.telegrammenulibrary.GotoCallback;
import org.simonscode.telegrammenulibrary.SimpleMenu;
import org.simonscode.telegrammenulibrary.UpdateHook;


public class CleanupTest {
    public static void main(String[] args) {
        SimpleMenu sm = new SimpleMenu();

        Callback ca = new GotoCallback(sm);
        Callback cb = new GotoCallback(sm);

        System.out.println(ca.equals(cb));

        UpdateHook.register(ca);
        UpdateHook.register(cb);

        System.out.println(UpdateHook.clearCallbacks());
    }
}
