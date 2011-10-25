/*
 * ExampleDemo.java
 *
 * Copyright (C) 2009 Olivier Sechet
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.druppi.demo.example;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.druppi.demo.AbstractDemo;

/**
 * A simple Demo used for test purpose.
 *
 * @author Olivier Sechet
 * @version 1.0 - Nov 25, 2009
 */
public class ExampleDemo extends AbstractDemo {

    /** The associated panel. */
    private JPanel panel;

    /**
     * Creates a new ExampleDemo.
     */
    public ExampleDemo() {
        super("Example Demo"); //$NON-NLS-1$
        createPanel();
    }

    /**
    *
    */
   private void createPanel() {
       panel = new JPanel();
       panel.add(new JLabel("Hello World!")); //$NON-NLS-1$
   }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel getPanel() {
        return panel;
    }

}
