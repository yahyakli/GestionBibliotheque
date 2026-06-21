package models;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DataChangeNotifier {
    private static final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    public static void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public static void notifyDataChanged() {
        listeners.forEach(Runnable::run);
    }
}
