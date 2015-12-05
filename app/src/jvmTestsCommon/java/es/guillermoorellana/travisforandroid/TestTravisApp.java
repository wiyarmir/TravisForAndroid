package es.guillermoorellana.travisforandroid;

public class TestTravisApp extends TravisApp {
    @Override
    protected void developerTools() {
        // noop to prevent robolectric tests interference
    }
}
