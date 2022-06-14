package lib.technicalComponents;

import java.util.stream.Collectors;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class MyDocumentNumberFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                        AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, string.chars().map(i -> (char) i).filter(c -> Character.isDigit(c))
                                .mapToObj(c -> Character.valueOf((char) c))
                                .map(c -> c.toString()).collect(Collectors.joining()), attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
                fb.replace(offset, length,
                                text.chars().map(i -> (char) i).filter(c -> Character.isDigit(c))
                                                .mapToObj(c -> Character.valueOf((char) c))
                                                .map(c -> c.toString()).collect(Collectors.joining()),
                                attrs);
        }
}
