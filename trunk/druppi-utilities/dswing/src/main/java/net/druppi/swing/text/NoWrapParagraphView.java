/*
 * NoWrapParagraphView.java
 *
 * Copyright © 2003-2009 Stanislav Lapitsky. All Rights Reserved.
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
package net.druppi.swing.text;

import javax.swing.text.Element;
import javax.swing.text.ParagraphView;

/**
 * A ParagraphView used in the {@link WrapColumnFactory}.
 * <p>
 * The original code is based on the example provided by Stanislav Lapitsky on this web
 * page: http://java-sl.com/wrap.html
 *
 * @see WrapColumnFactory
 *
 * @author Stanislav Lapitsky
 * @author Olivier Sechet
 * @version 1.0 - Nov 25, 2009
 */
class NoWrapParagraphView extends ParagraphView {

    /**
     * Constructs a ParagraphView for the given element.
     *
     * @param elem the element that this view is responsible for.
     */
    public NoWrapParagraphView(final Element elem) {
        super(elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void layout(int width, int height) {
        super.layout(Short.MAX_VALUE, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getMinimumSpan(int axis) {
        return super.getPreferredSpan(axis);
    }
}