/* ========================================================================
 * JCommon : a free general purpose class library for the Java(tm) platform
 * ========================================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * ------------
 * Spinner.java
 * ------------
 * (C) Copyright 2002-2004, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id $
 *
 * Changes
 * -------
 * 14-Oct-2002 : Version 1 (DG);
 *
 */

package org.jfree.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A very basic spinner component, used for demo purposes only.
 *
 * @author David Gilbert
 */
public class Spinner extends JPanel implements MouseListener {

    /** The current value. */
    private int value;

    /** The text field displaying the value. */
    private JTextField valueField;

    /** The arrow button panel. */
    private JPanel arrowPanel;

    /** The up button. */
    private ArrowButton upButton;

    /** The down button. */
    private ArrowButton downButton;

    /**
     * Creates a new spinner.
     *
     * @param value  the initial value.
     */
    public Spinner(final int value) {
        super(new BorderLayout());

        this.value = value;

        createAndRegisterValueField();
        createAndRegisterButtonPanel();
    }

    private void createAndRegisterValueField(){
        this.valueField = new JTextField(Integer.toString(this.value));
        this.valueField.setHorizontalAlignment(SwingConstants.RIGHT);
        add(this.valueField);
    }

    private void createAndRegisterButtonPanel(){
        this.arrowPanel = new JPanel(new GridLayout(2, 1, 0, 1));
        createAndRegisterButtons();
        add(this.arrowPanel, BorderLayout.EAST);
    }

    private void createAndRegisterButtons(){
        this.upButton = createAndRegisterButton(ArrowDirection.UP);
        this.arrowPanel.add(this.upButton);
        this.downButton = createAndRegisterButton(ArrowDirection.DOWN);
        this.arrowPanel.add(this.downButton);
    }

    private ArrowButton createAndRegisterButton(ArrowDirection direction) {
        ArrowButton button = new ArrowButton(direction);
        button.addMouseListener(this);
        return button;
    }

    /**
     * Returns the current value.
     *
     * @return the current value.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Receives notification of mouse clicks.
     *
     * @param e  the mouse event.
     */
    public void mouseClicked(final MouseEvent e) {
        int delta = 0;

        if (isSource(this.upButton, e)) delta = 1;
        else if (isSource(this.downButton, e)) delta = -1;

        if(delta != 0) {
            valueChange(delta);
            firePropertyChange("value", this.value - delta, this.value);
        }
    }

    private Boolean isSource(Object potentialSource, MouseEvent e){
        return e.getSource() == potentialSource;
    }

    private void valueChange(int delta){
        this.value += delta;
        this.valueField.setText(Integer.toString(this.value));
    }

    /**
     * Receives notification of mouse events.
     *
     * @param e  the mouse event.
     */
    public void mouseEntered(final MouseEvent e) {
        // ignored
    }

    /**
     * Receives notification of mouse events.
     *
     * @param e  the mouse event.
     */
    public void mouseExited(final MouseEvent e) {
        // ignored
    }

    /**
     * Receives notification of mouse events.
     *
     * @param e  the mouse event.
     */
    public void mousePressed(final MouseEvent e) {
        // ignored
    }

    /**
     * Receives notification of mouse events.
     *
     * @param e  the mouse event.
     */
    public void mouseReleased(final MouseEvent e) {
        // ignored
    }

}
