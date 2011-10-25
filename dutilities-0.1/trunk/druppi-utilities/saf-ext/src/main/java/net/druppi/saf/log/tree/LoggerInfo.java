/*
 * LoggerInfo.java
 *
 * Copyright (C) 2010 Olivier Sechet
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
package net.druppi.saf.log.tree;

/**
 * @author Olivier Sechet
 * @version 1.0 - Aug 26, 2010
 */
public class LoggerInfo {

    /** The logger's name. */
    private final String name;

    /** The logger's level. */
    private String level;

    /** The logger's level. */
    private String originalLevel;

    /** <code>true</code> if the instance has been changed. */
    private boolean edited = false;

    /**
     * Creates a new LoggerInfo.
     *
     * @param name the logger's name.
     */
    public LoggerInfo(final String name) {
        this(name, null);
    }

    /**
     * Creates a new LoggerInfo.
     *
     * @param name the logger's name.
     * @param level the logger's level.
     */
    public LoggerInfo(final String name, final String level) {
        this.name = name;
        this.level = level;
    }

    /**
     * Returns the logger's name.
     *
     * @return the logger's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the logger's level or <code>null</code> if the level is not set.
     *
     * @return the logger's level or <code>null</code>.
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the logger's level.
     *
     * @param level the new level.
     */
    public void setLevel(final String level) {
        this.level = level;
        setEdited(true);
    }

    /**
     * Returns <code>true</code> if the instance has been changed.
     *
     * @return <code>true</code> if the instance has been changed.
     */
    public boolean isEdited() {
        return edited;
    }

    /**
     * Sets the edited flag.
     *
     * @param edited the new edited flag.
     */
    private void setEdited(final boolean edited) {
        this.edited = edited;
    }

    /**
     * Apply the changes.
     */
    public void apply() {
        originalLevel = level;
        setEdited(false);
    }

    /**
     * Reset the fields to their value before edition.
     */
    public void reset() {
        this.level = originalLevel;
        originalLevel = null;
        setEdited(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LoggerInfo)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        final LoggerInfo that = (LoggerInfo) obj;
        return this.name.equals(that.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
