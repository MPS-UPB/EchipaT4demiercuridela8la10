package ro.mps.gui;

import ro.mps.data.concrete.Root;
import ro.mps.gui.base.Screen;
import ro.mps.gui.screens.SelectionScreen;
import ro.mps.gui.screens.generators.TreeGenerator;
import ro.mps.gui.screens.lines.CharacterEditingScreen;
import ro.mps.gui.screens.lines.LinesEditingScreen;
import ro.mps.gui.screens.paragraph.ParagraphEditingScreen;
import ro.mps.screen.concrete.RootUsedInEditingScreen;

import javax.swing.*;

@SuppressWarnings("serial")
public class UserInterface extends JFrame {
    private ParagraphEditingScreen paragraphEditingScreen;
    private LinesEditingScreen linesEditingScreen;
    private CharacterEditingScreen characterEditingScreen;
    private Root root;

    public UserInterface() {
        this.init();
    }

    public UserInterface(Root root) {
        this.root = root;
        this.init();
    }

    /**
     * Initializeaza fereastra principala
     */
    private void init() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(Screen.WINDOW_WIDTH, Screen.WINDOW_HEIGHT);
        this.setVisible(true);
        createScreens();
        registerObservers();

        addTabbedPannel();
    }

    private void createScreens() {
        paragraphEditingScreen = new ParagraphEditingScreen(root);
        linesEditingScreen = new LinesEditingScreen(root);
        characterEditingScreen = new CharacterEditingScreen(root);
    }

    private void registerObservers() {
        paragraphEditingScreen.registerObserver(linesEditingScreen);
        paragraphEditingScreen.registerObserver(characterEditingScreen);
        linesEditingScreen.registerObserver(paragraphEditingScreen);
        linesEditingScreen.registerObserver(characterEditingScreen);
        characterEditingScreen.registerObserver(paragraphEditingScreen);
        characterEditingScreen.registerObserver(linesEditingScreen);
    }


    /**
     * Builds the initial tabbed pannel.
     */
    private void addTabbedPannel() {
        TabbedPannel tabbedPannel = new TabbedPannel();

        tabbedPannel.addPane(paragraphEditingScreen);
        tabbedPannel.addPane(linesEditingScreen);
        tabbedPannel.addPane(characterEditingScreen);

        getContentPane().add(tabbedPannel);
    }


}