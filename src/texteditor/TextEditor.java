
package texteditor;

import java.awt.Dimension;

/**
 *
 * @author georgios petas
 */
public class TextEditor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TextEditorFrame myframe=new TextEditorFrame();//object's creation
        myframe.setTitle("Text Editor"); //putting values
        myframe.setSize(new Dimension(600,600));
        myframe.setLocationRelativeTo(null);
        myframe.setVisible(true); //make it visible
    }
    
}
