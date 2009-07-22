/*
 * LocaleRenderer.java
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
package net.druppi.util;

import java.util.Locale;

/**
 * A LocaleRenderer wraps a Locale instance and defines a toString method that returns a
 * human readable description of the locale.<p>
 * The representation is: displayedLanguage (displayedCountry).
 *
 * @author Olivier Sechet
 * @version 1.0 - Apr 13, 2009
 */
public class LocaleRenderer implements Comparable<LocaleRenderer> {

    /** The associated locale. */
    private final Locale locale;

    /** The toString result. */
    private final String toString;

    /**
     * Creates a new LocaleRenderer.
     *
     * @param locale the associated locale.
     */
    public LocaleRenderer(final Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("The locale cannot be null."); //$NON-NLS-1$
        }
        this.locale = locale;
        StringBuilder buffer = new StringBuilder(locale.getDisplayLanguage());
        if (locale.getDisplayCountry().length() > 0) {
            buffer.append(" ("); //$NON-NLS-1$
            buffer.append(locale.getDisplayCountry());
            buffer.append(")"); //$NON-NLS-1$
        }
        toString = buffer.toString();
    }

    /**
     * Returns the associated locale.
     *
     * @return the associated locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return toString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof LocaleRenderer)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        LocaleRenderer that = (LocaleRenderer) obj;
        return this.locale.equals(that.locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return locale.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final LocaleRenderer that) {
        if (locale.getDisplayLanguage().equals(that.locale.getDisplayLanguage())) {
            return locale.getDisplayCountry().compareTo(that.locale.getDisplayCountry());
        }
        return locale.getDisplayLanguage().compareTo(that.locale.getDisplayLanguage());
    }
}
