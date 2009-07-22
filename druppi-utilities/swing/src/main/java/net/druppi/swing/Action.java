/*
 * Action.java
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
package net.druppi.swing;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.druppi.util.ResourceManager;
import net.druppi.util.ResourceMap;

/**
 * @author Olivier Sechet
 * @version 1.0 - Jul 17, 2009
 */
public abstract class Action extends AbstractAction {

    /** The serial version id. */
    private static final long serialVersionUID = 8978760043598588185L;

    /**
     * Defines an Action object with a default description string and default
     * icon.
     */
    public Action() {
        this(null, null);
    }

    /**
     * Defines an Action object with the specified description string and a
     * default icon. This constructor calls the Action(String, Icon) constructor.
     *
     * @param actionName the action's name in the bundle.
     * @see Action#Action(String, Icon)
     */
    public Action(String actionName) {
        this(actionName, null);
    }

    /**
     * Defines an Action object with the specified description string and a the
     * specified icon. The given name is the name of the action in the resources
     * bundle. It will be used to find all the associated information (name,
     * mnemonic, description...). The rule is as follow:
     * <p>
     * <ul>
     * <li>&lt;actionName&gt;.action.name
     * <li>&lt;actionName&gt;.action.description
     * <li>&lt;actionName&gt;.action.mnemonic
     * </ul>
     *
     * @param actionName the localized key of the action's name.
     * @param icon the action's icon.
     */
    public Action(String actionName, Icon icon) {
        ResourceMap resourceMap = ResourceManager.getResourceMap(getClass());
        putValue(Action.NAME, resourceMap.getString(actionName + ".action.name")); //$NON-NLS-1$
        putValue(Action.MNEMONIC_KEY, (int) resourceMap.getString(actionName + ".action.mnemonic").charAt(0)); //$NON-NLS-1$
        putValue(Action.SHORT_DESCRIPTION, resourceMap.getString(actionName + ".action.description")); //$NON-NLS-1$
        putValue(Action.SMALL_ICON, icon);
    }
}
