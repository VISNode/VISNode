/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui.helpers;

import java.util.function.Consumer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * Helper class for text field documents
 */
public class DocumentHelper {

    /**
     * Consumes events for every change in the document
     * 
     * @param document
     * @param consumer 
     */
    public static void onChange(Document document, Consumer<DocumentEvent> consumer) {
        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                consumer.accept(e);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                consumer.accept(e);
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                consumer.accept(e);
            }
        });
    }
    
}
