package ro.mps.gui.screens.lines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Lapa
 * Date: 25.11.2012
 * Time: 14:12
 */
public class LinesPopupMenu {
    private static final String MERGE_WITH_NEXT_LINE = "Merge with next line";
    private static final String MERGE_WITH_PREVIOUS_LINE = "Merge with previous line";
    private static final String SPLIT_AT_POSITION = "Split at position";
    private static final String EDIT_LINES = "Edit lines";
    private static final String SPLIT = "Split";

    private PositionSpinner positionSpinner;
    private LinesEditingScreen linesEditingScreen;
    private JPopupMenu rightClickMenu;

    public LinesPopupMenu(LinesEditingScreen linesEditingScreen) {
        buildPopupMenu(linesEditingScreen);
    }

    /**
     * Returns the Popup Menu
     *
     * @return popup menu
     */
    public JPopupMenu getRightClickMenu() {
        return rightClickMenu;
    }

    /**
     * Listener for the popup menu
     */
    class RightClickActionListener implements ActionListener {
        private LinesEditingScreen linesEditingScreen;

        RightClickActionListener(LinesEditingScreen linesEditingScreen) {
            this.linesEditingScreen = linesEditingScreen;
        }

        /**
         * Returns the component that triggered the event
         *
         * @param event
         * @return
         */
        private Component getInvoker(ActionEvent event) {
            JMenuItem source = (JMenuItem) event.getSource();
            JPopupMenu jPopupMenu = (JPopupMenu) source.getParent();

            return jPopupMenu.getInvoker();
        }

        /**
         * Repaints my panel
         */
        private void repaintMyPanel() {
            linesEditingScreen.getContainingPanel().repaint();
            linesEditingScreen.repaintScrollPane();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Component componentInvoker = getInvoker(e);
            int index = linesEditingScreen.getComponentIndex(componentInvoker);
            int numberOfComponents = linesEditingScreen.getNumberOfComponents();

            if (e.getActionCommand().equals(MERGE_WITH_PREVIOUS_LINE) && index != 0) {
                linesEditingScreen.mergeWithPreviousLine(index);
            }

            if (e.getActionCommand().equals(MERGE_WITH_NEXT_LINE) && index != numberOfComponents - 1) {
                linesEditingScreen.mergeWithNextLine(index);
            }

            repaintMyPanel();
        }
    }

    class PositionSpinner extends JSpinner {
        PositionSpinner(int max) {
            super(new SpinnerNumberModel(1, 1, max, 1));
        }

        PositionSpinner() {
            this(100);
        }

        public SpinnerModel getSpinnerNumberModel() {
            return super.getModel();
        }
    }

    /**
     * Builds the right click menu
     *
     * @return right click menu
     */
    private JPopupMenu buildPopupMenu(LinesEditingScreen linesEditingScreen) {
        rightClickMenu = new JPopupMenu(EDIT_LINES);
        JMenuItem menuItem;

        JMenu splitAt = new JMenu(SPLIT_AT_POSITION);
        splitAt.add(getSplitContainer(linesEditingScreen));
        rightClickMenu.add(splitAt);
        rightClickMenu.addSeparator();

        menuItem = new JMenuItem(MERGE_WITH_PREVIOUS_LINE);
        menuItem.addActionListener(new RightClickActionListener(linesEditingScreen));
        rightClickMenu.add(menuItem);

        menuItem = new JMenuItem(MERGE_WITH_NEXT_LINE);
        menuItem.addActionListener(new RightClickActionListener(linesEditingScreen));
        rightClickMenu.add(menuItem);

        return rightClickMenu;
    }

    /**
     * Builds a panel that contains a spinner and a button
     *
     * @return panel
     */
    private JPanel getSplitContainer(LinesEditingScreen linesEditingScreen) {
        JPanel splitContainer = new JPanel();
        JButton button = new JButton(SPLIT);
        positionSpinner = new PositionSpinner();
        splitContainer.setLayout(new FlowLayout());
        splitContainer.add(positionSpinner);
        button.addActionListener(new SplitActionListener(linesEditingScreen));
        splitContainer.add(button);

        return splitContainer;
    }

    /**
     * Listener for the split button
     */
    class SplitActionListener implements ActionListener {
        LinesEditingScreen linesEditingScreen;

        SplitActionListener(LinesEditingScreen linesEditingScreen) {
            this.linesEditingScreen = linesEditingScreen;
        }

        /**
         * Returns the component that triggered the event
         *
         * @param event
         * @return
         */
        private Component getInvoker(ActionEvent event) {
            JButton button = (JButton) event.getSource();
            Container jPanel = button.getParent();
            JPopupMenu innerPopupMenu = (JPopupMenu) jPanel.getParent();
            JMenu jMenu = (JMenu) innerPopupMenu.getInvoker();
            JPopupMenu popupMenu = (JPopupMenu) jMenu.getParent();
            return popupMenu.getInvoker();
        }

        /**
         * Splits the text content when split button is pressed
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            SpinnerModel spinnerNumberModel = positionSpinner.getSpinnerNumberModel();
            int wordNumber = Integer.parseInt(spinnerNumberModel.getValue().toString());
            int index = linesEditingScreen.getComponentIndex(getInvoker(e));

            linesEditingScreen.splitAtWord(wordNumber, index);
        }
    }
}
