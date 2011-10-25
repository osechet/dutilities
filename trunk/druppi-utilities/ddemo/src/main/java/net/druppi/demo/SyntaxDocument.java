/*
 * SyntaxDocument.java
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
package net.druppi.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

/**
 * @author Olivier Sechet
 * @version 1.0 - Sep 3, 2009
 */
@SuppressWarnings("serial")
public class SyntaxDocument extends DefaultStyledDocument {

    private DefaultStyledDocument doc;
    private Element rootElement;

    private boolean multiLineComment;
    private MutableAttributeSet normal;
    private MutableAttributeSet keyword;
    private MutableAttributeSet comment;
    private MutableAttributeSet quote;

    private HashSet<String> keywords;

    public SyntaxDocument() {
        doc = this;
        rootElement = doc.getDefaultRootElement();
        putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n"); //$NON-NLS-1$

        normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Color.black);

        comment = new SimpleAttributeSet();
        StyleConstants.setForeground(comment, Color.gray);
        StyleConstants.setItalic(comment, true);

        keyword = new SimpleAttributeSet();
        StyleConstants.setForeground(keyword, Color.blue);

        quote = new SimpleAttributeSet();
        StyleConstants.setForeground(quote, Color.red);

        keywords = new HashSet<String>();
        keywords.add("abstract"); //$NON-NLS-1$
        keywords.add("boolean"); //$NON-NLS-1$
        keywords.add("break"); //$NON-NLS-1$
        keywords.add("byte"); //$NON-NLS-1$
        keywords.add("byvalue"); //$NON-NLS-1$
        keywords.add("case"); //$NON-NLS-1$
        keywords.add("cast"); //$NON-NLS-1$
        keywords.add("catch"); //$NON-NLS-1$
        keywords.add("char"); //$NON-NLS-1$
        keywords.add("class"); //$NON-NLS-1$
        keywords.add("const"); //$NON-NLS-1$
        keywords.add("continue"); //$NON-NLS-1$
        keywords.add("default"); //$NON-NLS-1$
        keywords.add("do"); //$NON-NLS-1$
        keywords.add("double"); //$NON-NLS-1$
        keywords.add("else"); //$NON-NLS-1$
        keywords.add("extends"); //$NON-NLS-1$
        keywords.add("false"); //$NON-NLS-1$
        keywords.add("final"); //$NON-NLS-1$
        keywords.add("finally"); //$NON-NLS-1$
        keywords.add("float"); //$NON-NLS-1$
        keywords.add("for"); //$NON-NLS-1$
        keywords.add("future"); //$NON-NLS-1$
        keywords.add("generic"); //$NON-NLS-1$
        keywords.add("goto"); //$NON-NLS-1$
        keywords.add("if"); //$NON-NLS-1$
        keywords.add("implements"); //$NON-NLS-1$
        keywords.add("import"); //$NON-NLS-1$
        keywords.add("inner"); //$NON-NLS-1$
        keywords.add("instanceof"); //$NON-NLS-1$
        keywords.add("int"); //$NON-NLS-1$
        keywords.add("interface"); //$NON-NLS-1$
        keywords.add("long"); //$NON-NLS-1$
        keywords.add("native"); //$NON-NLS-1$
        keywords.add("new"); //$NON-NLS-1$
        keywords.add("null"); //$NON-NLS-1$
        keywords.add("operator"); //$NON-NLS-1$
        keywords.add("outer"); //$NON-NLS-1$
        keywords.add("package"); //$NON-NLS-1$
        keywords.add("private"); //$NON-NLS-1$
        keywords.add("protected"); //$NON-NLS-1$
        keywords.add("public"); //$NON-NLS-1$
        keywords.add("rest"); //$NON-NLS-1$
        keywords.add("return"); //$NON-NLS-1$
        keywords.add("short"); //$NON-NLS-1$
        keywords.add("static"); //$NON-NLS-1$
        keywords.add("super"); //$NON-NLS-1$
        keywords.add("switch"); //$NON-NLS-1$
        keywords.add("synchronized"); //$NON-NLS-1$
        keywords.add("this"); //$NON-NLS-1$
        keywords.add("throw"); //$NON-NLS-1$
        keywords.add("throws"); //$NON-NLS-1$
        keywords.add("transient"); //$NON-NLS-1$
        keywords.add("true"); //$NON-NLS-1$
        keywords.add("try"); //$NON-NLS-1$
        keywords.add("var"); //$NON-NLS-1$
        keywords.add("void"); //$NON-NLS-1$
        keywords.add("volatile"); //$NON-NLS-1$
        keywords.add("while"); //$NON-NLS-1$
    }

    /*
     * Override to apply syntax highlighting after the document has been updated
     */
    @Override
    public void insertString(final int offset, String str, final AttributeSet a)
            throws BadLocationException {
        if (str.equals("{")) { //$NON-NLS-1$
            str = addMatchingBrace(offset);
        }

        super.insertString(offset, str, a);
        processChangedLines(offset, str.length());
    }

    /*
     * Override to apply syntax highlighting after the document has been updated
     */
    @Override
    public void remove(final int offset, final int length) throws BadLocationException {
        super.remove(offset, length);
        processChangedLines(offset, 0);
    }

    /*
     * Determine how many lines have been changed, then apply highlighting to each line
     */
    public void processChangedLines(final int offset, final int length) throws BadLocationException {
        String content = doc.getText(0, doc.getLength());

        // The lines affected by the latest document update

        int startLine = rootElement.getElementIndex(offset);
        int endLine = rootElement.getElementIndex(offset + length);

        // Make sure all comment lines prior to the start line are commented
        // and determine if the start line is still in a multi line comment

        setMultiLineComment(commentLinesBefore(content, startLine));

        // Do the actual highlighting

        for (int i = startLine; i <= endLine; i++) {
            applyHighlighting(content, i);
        }

        // Resolve highlighting to the next end multi line delimiter

        if (isMultiLineComment())
            commentLinesAfter(content, endLine);
        else
            highlightLinesAfter(content, endLine);
    }

    /*
     * Highlight lines when a multi line comment is still 'open' (ie. matching end
     * delimiter has not yet been encountered)
     */
    private boolean commentLinesBefore(final String content, final int line) {
        int offset = rootElement.getElement(line).getStartOffset();

        // Start of comment not found, nothing to do

        int startDelimiter = lastIndexOf(content, getStartDelimiter(), offset - 2);

        if (startDelimiter < 0)
            return false;

        // Matching start/end of comment found, nothing to do

        int endDelimiter = indexOf(content, getEndDelimiter(), startDelimiter);

        if (endDelimiter < offset & endDelimiter != -1)
            return false;

        // End of comment not found, highlight the lines

        doc.setCharacterAttributes(startDelimiter, offset - startDelimiter + 1, comment,
            false);
        return true;
    }

    /*
     * Highlight comment lines to matching end delimiter
     */
    private void commentLinesAfter(final String content, final int line) {
        int offset = rootElement.getElement(line).getEndOffset();

        // End of comment not found, nothing to do

        int endDelimiter = indexOf(content, getEndDelimiter(), offset);

        if (endDelimiter < 0)
            return;

        // Matching start/end of comment found, comment the lines

        int startDelimiter = lastIndexOf(content, getStartDelimiter(), endDelimiter);

        if (startDelimiter < 0 || startDelimiter <= offset) {
            doc.setCharacterAttributes(offset, endDelimiter - offset + 1, comment, false);
        }
    }

    /*
     * Highlight lines to start or end delimiter
     */
    private void highlightLinesAfter(final String content, final int line)
            throws BadLocationException {
        int offset = rootElement.getElement(line).getEndOffset();

        // Start/End delimiter not found, nothing to do

        int startDelimiter = indexOf(content, getStartDelimiter(), offset);
        int endDelimiter = indexOf(content, getEndDelimiter(), offset);

        if (startDelimiter < 0)
            startDelimiter = content.length();

        if (endDelimiter < 0)
            endDelimiter = content.length();

        int delimiter = Math.min(startDelimiter, endDelimiter);

        if (delimiter < offset)
            return;

        // Start/End delimiter found, reapply highlighting

        int endLine = rootElement.getElementIndex(delimiter);

        for (int i = line + 1; i < endLine; i++) {
            Element branch = rootElement.getElement(i);
            Element leaf = doc.getCharacterElement(branch.getStartOffset());
            AttributeSet as = leaf.getAttributes();

            if (as.isEqual(comment))
                applyHighlighting(content, i);
        }
    }

    /*
     * Parse the line to determine the appropriate highlighting
     */
    private void applyHighlighting(final String content, final int line) throws BadLocationException {
        int startOffset = rootElement.getElement(line).getStartOffset();
        int endOffset = rootElement.getElement(line).getEndOffset() - 1;

        int lineLength = endOffset - startOffset;
        int contentLength = content.length();

        if (endOffset >= contentLength)
            endOffset = contentLength - 1;

        // check for multi line comments
        // (always set the comment attribute for the entire line)

        if (endingMultiLineComment(content, startOffset, endOffset)
                || isMultiLineComment()
                || startingMultiLineComment(content, startOffset, endOffset)) {
            doc.setCharacterAttributes(startOffset, endOffset - startOffset + 1, comment,
                false);
            return;
        }

        // set normal attributes for the line

        doc.setCharacterAttributes(startOffset, lineLength, normal, true);

        // check for single line comment

        int index = content.indexOf(getSingleLineDelimiter(), startOffset);

        if ((index > -1) && (index < endOffset)) {
            doc.setCharacterAttributes(index, endOffset - index + 1, comment, false);
            endOffset = index - 1;
        }

        // check for tokens

        checkForTokens(content, startOffset, endOffset);
    }

    /*
     * Does this line contain the start delimiter
     */
    private boolean startingMultiLineComment(final String content, final int startOffset,
            int endOffset) throws BadLocationException {
        int index = indexOf(content, getStartDelimiter(), startOffset);

        if ((index < 0) || (index > endOffset))
            return false;
        else {
            setMultiLineComment(true);
            return true;
        }
    }

    /*
     * Does this line contain the end delimiter
     */
    private boolean endingMultiLineComment(final String content, final int startOffset, final int endOffset)
            throws BadLocationException {
        int index = indexOf(content, getEndDelimiter(), startOffset);

        if ((index < 0) || (index > endOffset))
            return false;
        else {
            setMultiLineComment(false);
            return true;
        }
    }

    /*
     * We have found a start delimiter and are still searching for the end delimiter
     */
    private boolean isMultiLineComment() {
        return multiLineComment;
    }

    private void setMultiLineComment(final boolean value) {
        multiLineComment = value;
    }

    /*
     * Parse the line for tokens to highlight
     */
    private void checkForTokens(final String content, int startOffset, final int endOffset) {
        while (startOffset <= endOffset) {
            // skip the delimiters to find the start of a new token

            while (isDelimiter(content.substring(startOffset, startOffset + 1))) {
                if (startOffset < endOffset)
                    startOffset++;
                else
                    return;
            }

            // Extract and process the entire token

            if (isQuoteDelimiter(content.substring(startOffset, startOffset + 1)))
                startOffset = getQuoteToken(content, startOffset, endOffset);
            else
                startOffset = getOtherToken(content, startOffset, endOffset);
        }
    }

    /*
     *
     */
    private int getQuoteToken(final String content, final int startOffset, final int endOffset) {
        String quoteDelimiter = content.substring(startOffset, startOffset + 1);
        String escapeString = getEscapeString(quoteDelimiter);

        int index;
        int endOfQuote = startOffset;

        // skip over the escape quotes in this quote

        index = content.indexOf(escapeString, endOfQuote + 1);

        while ((index > -1) && (index < endOffset)) {
            endOfQuote = index + 1;
            index = content.indexOf(escapeString, endOfQuote);
        }

        // now find the matching delimiter

        index = content.indexOf(quoteDelimiter, endOfQuote + 1);

        if ((index < 0) || (index > endOffset))
            endOfQuote = endOffset;
        else
            endOfQuote = index;

        doc.setCharacterAttributes(startOffset, endOfQuote - startOffset + 1, quote,
            false);

        return endOfQuote + 1;
    }

    /*
     *
     */
    private int getOtherToken(final String content, final int startOffset, final int endOffset) {
        int endOfToken = startOffset + 1;

        while (endOfToken <= endOffset) {
            if (isDelimiter(content.substring(endOfToken, endOfToken + 1)))
                break;

            endOfToken++;
        }

        String token = content.substring(startOffset, endOfToken);

        if (isKeyword(token)) {
            doc.setCharacterAttributes(startOffset, endOfToken - startOffset, keyword,
                false);
        }

        return endOfToken + 1;
    }

    /*
     * Assume the needle will the found at the start/end of the line
     */
    private int indexOf(final String content, final String needle, int offset) {
        int index;

        while ((index = content.indexOf(needle, offset)) != -1) {
            String text = getLine(content, index).trim();

            if (text.startsWith(needle) || text.endsWith(needle))
                break;
            else
                offset = index + 1;
        }

        return index;
    }

    /*
     * Assume the needle will the found at the start/end of the line
     */
    private int lastIndexOf(final String content, final String needle, int offset) {
        int index;

        while ((index = content.lastIndexOf(needle, offset)) != -1) {
            String text = getLine(content, index).trim();

            if (text.startsWith(needle) || text.endsWith(needle))
                break;
            else
                offset = index - 1;
        }

        return index;
    }

    private String getLine(final String content, final int offset) {
        int line = rootElement.getElementIndex(offset);
        Element lineElement = rootElement.getElement(line);
        int start = lineElement.getStartOffset();
        int end = lineElement.getEndOffset();
        return content.substring(start, end - 1);
    }

    /*
     * Override for other languages
     */
    protected boolean isDelimiter(final String character) {
        String operands = ";:{}()[]+-/%<=>!&|^~*"; //$NON-NLS-1$

        if (Character.isWhitespace(character.charAt(0))
                || operands.indexOf(character) != -1) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Override for other languages
     */
    protected boolean isQuoteDelimiter(final String character) {
        String quoteDelimiters = "\"'"; //$NON-NLS-1$

        if (quoteDelimiters.indexOf(character) < 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Override for other languages
     */
    protected boolean isKeyword(final String token) {
        return keywords.contains(token);
    }

    /*
     * Override for other languages
     */
    protected String getStartDelimiter() {
        return "/*"; //$NON-NLS-1$
    }

    /*
     * Override for other languages
     */
    protected String getEndDelimiter() {
        return "*/"; //$NON-NLS-1$
    }

    /*
     * Override for other languages
     */
    protected String getSingleLineDelimiter() {
        return "//"; //$NON-NLS-1$
    }

    /*
     * Override for other languages
     */
    protected String getEscapeString(final String quoteDelimiter) {
        return "\\" + quoteDelimiter; //$NON-NLS-1$
    }

    /*
             *
             */
    protected String addMatchingBrace(final int offset) throws BadLocationException {
        StringBuffer whiteSpace = new StringBuffer();
        int line = rootElement.getElementIndex(offset);
        int i = rootElement.getElement(line).getStartOffset();

        while (true) {
            String temp = doc.getText(i, 1);

            if (temp.equals(" ") || temp.equals("\t")) { //$NON-NLS-1$ //$NON-NLS-2$
                whiteSpace.append(temp);
                i++;
            } else {
                break;
            }
        }

        return "{\n" + whiteSpace.toString() + "\t\n" + whiteSpace.toString() + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    public static void main(final String[] args) {

        EditorKit editorKit = new StyledEditorKit() {
            @Override
            public Document createDefaultDocument() {
                return new SyntaxDocument();
            }
        };

        final JEditorPane edit = new JEditorPane();
        edit.setEditorKitForContentType("text/java", editorKit); //$NON-NLS-1$
        edit.setContentType("text/java"); //$NON-NLS-1$
        // edit.setEditorKit(new StyledEditorKit());
        // edit.setDocument(new SyntaxDocument());

        JButton button = new JButton("Load SyntaxDocument.java"); //$NON-NLS-1$
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fis = new FileInputStream("src/main/java/net/druppi/demo/SyntaxDocument.java"); //$NON-NLS-1$
                    // FileInputStream fis = new FileInputStream(
                    // "C:\\Java\\jdk1.4.1\\src\\javax\\swing\\JComponent.java" );
                    edit.read(fis, null);
                    edit.requestFocus();
                } catch (Exception e2) {
                }
            }
        });

        JFrame frame = new JFrame("Syntax Highlighting"); //$NON-NLS-1$
        frame.getContentPane().add(new JScrollPane(edit));
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300);
        frame.setVisible(true);
    }
}
