/*
 * WrapColumnFactory.java
 *
 * Copyright (C) 2003-2009 Stanislav Lapitsky. All Rights Reserved.
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

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 * A ViewFactory to use in an {@link javax.swing.text.EditorKit} in order to enable the no
 * wrap functionality in a JEditorPane.
 * <p>
 * The original code is based on the example provided by Stanislav Lapitsky on this web
 * page: http://java-sl.com/wrap.html
 *
 * @author Stanislav Lapitsky
 * @author Olivier Sechet
 * @version 1.0 - Nov 25, 2009
 */
public class WrapColumnFactory implements ViewFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public View create(Element elem) {
        String kind = elem.getName();
        if (kind != null) {
            if (kind.equals(AbstractDocument.ParagraphElementName)) {
                return new NoWrapParagraphView(elem);
            } else if (kind.equals(AbstractDocument.SectionElementName)) {
                return new BoxView(elem, View.Y_AXIS);
            } else if (kind.equals(StyleConstants.ComponentElementName)) {
                return new ComponentView(elem);
            } else if (kind.equals(StyleConstants.IconElementName)) {
                return new IconView(elem);
            }
        }

        // default to text display
        return new LabelView(elem);
    }
}
