package lib.technicalComponents;

import java.util.stream.Collectors;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class DocumentNumberFilterLimited extends DocumentFilter {

        int extremeValue;
        boolean isMaximum;

        public DocumentNumberFilterLimited(int extremeValue, boolean isMaximum) {
                this.extremeValue = extremeValue;
                this.isMaximum = isMaximum;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string,
                        AttributeSet attr) throws BadLocationException {

                String filtered = filterString(string);
                fb.insertString(offset, filtered, attr);

                //take whole String and check weather or not it is in range
                //get Document
                Document d = fb.getDocument();

                //get String from Dokument
                String num = "0";
                try {
                        num = d.getText(0, d.getLength());
                } catch (BadLocationException e) {
                        e.printStackTrace();
                }
                // get Corrected Version
                String corrected = checkNewRange(num);
                if (!corrected.equals(num)) {
                        setDocumentString(fb, d, corrected);
                }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
                // replace with filtered input
                String filtered = filterString(text);
                fb.replace(offset, length, filtered, attrs);

                //take whole String and check weather or not it is in range
                //get Document
                Document d = fb.getDocument();

                //get String from Dokument
                String num = "0";
                try {
                        num = d.getText(0, d.getLength());
                } catch (BadLocationException e) {
                        e.printStackTrace();
                }
                // get Corrected Version
                String corrected = checkNewRange(num);
                if (!corrected.equals(num)) {
                        setDocumentString(fb, d, corrected);
                }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {

                fb.remove(offset, length);

                //take whole String and check weather or not it is in range
                //get Document
                Document d = fb.getDocument();

                //get String from Dokument
                String num = "0";
                try {
                        num = d.getText(0, d.getLength());
                } catch (BadLocationException e) {
                        e.printStackTrace();
                }
                // get Corrected Version
                String corrected = checkNewRange(num);
                if (!corrected.equals(num)) {
                        setDocumentString(fb, d, corrected);
                }
        }

        private String filterString(String s) {

                String outputString = s.chars().map(i -> (char) i).filter(c -> Character.isDigit(c))
                                .mapToObj(c -> Character.valueOf((char) c))
                                .map(c -> c.toString()).collect(Collectors.joining());

                return outputString;
        }

        private String checkNewRange(String num) {

                int num_int = Integer.parseInt(num);
                if (isMaximum ? num_int > extremeValue : num_int < extremeValue) {
                        return extremeValue + "";
                } else {
                        return num + "";
                }
        }

        private void setDocumentString(FilterBypass fb, Document d, String s) throws BadLocationException {
                fb.remove(0, d.getLength());
                fb.insertString(0, s, null);
        }
}
