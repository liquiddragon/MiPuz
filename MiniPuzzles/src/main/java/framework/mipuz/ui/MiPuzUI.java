package framework.mipuz.ui;

import framework.mipuz.logic.Engine;

public class MiPuzUI implements Runnable {

    private final Engine engine;

    public MiPuzUI() {
        this.engine = new Engine();
    }

    @Override
    public void run() {
    }
}
