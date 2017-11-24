/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.commons.gui;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pichau
 */
public class PanelTest {

    @Test
    public void testFindId() {
        Panel panel = Panels.create();
        Button bt1 = Buttons.create().id("bt1");
        panel.put(bt1);
        Button bt2 = Buttons.create().id("bt2");
        panel.put(bt2);
        //
        assertEquals(bt1, panel.findId("bt1"));
        assertEquals(bt2, panel.findId("bt2"));
        assertNull(panel.findId("bt3"));
    }

    @Test
    public void testFindIdRecursive() {
        Panel panel = Panels.create();
        Panel subPanel = Panels.create();
        Button bt1 = Buttons.create().id("bt1");
        panel.put(bt1);
        Button bt2 = Buttons.create().id("bt2");
        subPanel.put(bt2);
        panel.put(subPanel);
        //
        assertEquals(bt1, panel.findId("bt1"));
        assertEquals(bt2, panel.findId("bt2"));
        assertNull(panel.findId("bt3"));
    }
    
}
