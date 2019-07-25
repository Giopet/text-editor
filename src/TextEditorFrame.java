
package texteditor;

import java.awt.Button;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.in;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.apache.commons.io.FilenameUtils;


public class TextEditorFrame extends Frame implements ActionListener,WindowListener {
    private Button openBtn,clearBtn,saveBtn,copyBtn,exitBtn,statisticsBtn;
    private TextField pathTF;
    private TextArea textTA;
    private MenuBar Bar; //dhlwnw bara
    private Menu FileMenu;
    private MenuItem openItem,clearItem,saveItem,copyItem,exitItem,statisticsItem;
    private String copyPath;
    private String path;
    
    public TextEditorFrame() {
        FlowLayout frame=new FlowLayout();
        this.setLayout(frame);
        
        //creating objects
        openBtn=new Button("Open");
        clearBtn=new Button("Clear");
        saveBtn=new Button("Save");
        copyBtn=new Button("Copy");
        exitBtn=new Button("Exit");
        statisticsBtn=new Button("Statistics");
        pathTF=new TextField(80);
        textTA=new TextArea(30,80);
       
        //creating bar objects
        Bar=new MenuBar(); //creating bar
        FileMenu=new Menu("File");
        openItem=new MenuItem("Open.");
        clearItem=new MenuItem("clear.");
        saveItem=new MenuItem("Save.");
        copyItem=new MenuItem("Copy.");
        statisticsItem=new MenuItem("Statistics.");
        exitItem=new MenuItem("Exit.");
        
        //listener's registration and add objects into frame 
        add(openBtn);
        openBtn.addActionListener(this);
        add(clearBtn);
        clearBtn.addActionListener(this);
        add(saveBtn);
        saveBtn.addActionListener(this);
        add(copyBtn);
        copyBtn.addActionListener(this);
        add(statisticsBtn);
        statisticsBtn.addActionListener(this);
        add(exitBtn);
        exitBtn.addActionListener(this);
        this.add(pathTF);
        this.add(textTA);
        
        //listener's registration
        openItem.addActionListener(this);
        clearItem.addActionListener(this);
        saveItem.addActionListener(this);
        copyItem.addActionListener(this);
        statisticsItem.addActionListener(this);
        exitItem.addActionListener(this);
        
        //putting objects in the menu
        FileMenu.add(openItem);
        FileMenu.add(clearItem);
        FileMenu.add(saveItem);
        FileMenu.add(copyItem);
        FileMenu.add(statisticsItem);
        FileMenu.addSeparator();
        FileMenu.add(exitItem);
        
        Bar.add(FileMenu);//putting file inside bar
        this.setMenuBar(Bar);//enable bar
        this.addWindowListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd=e.getActionCommand(); 
        if(cmd.equals("Open")||cmd.equals("Open.")){
            openChooser();
        }
        else if(cmd.equals("Clear")||cmd.equals("Clear.")){
    
            clear();
        }
        else if(cmd.equals("Save")||cmd.equals("Save.")){
    
            saveChooser();
        }
        else if(cmd.equals("Copy")||cmd.equals("Copy.")){
            copy();
            
        }
        else if(cmd.equals("Statistics")||cmd.equals("Statistics.")){
            statistics();
           
        }
        
        else if(cmd.equals("Exit")||cmd.equals("Exit.")){
            System.exit(0);
        }
     
        
    }
    
    private void clear() {
        if(!pathTF.getText().isEmpty())
            pathTF.setText(""); 
        if(!textTA.getText().isEmpty())
            textTA.setText("");
    }
    
    private void openChooser(){
        
        JFileChooser chooser= new JFileChooser();
        int returnVal= chooser.showOpenDialog(this);
        if(returnVal==JFileChooser.APPROVE_OPTION){
            System.out.println("You pressed Open");
            clear();//clear previous path if exists
            
            File selectedFile = chooser.getSelectedFile();
            path=selectedFile.getAbsolutePath();//gets the path
            copyPath=FilenameUtils.getPath(path);//the path without file's name is needed in copy() 
            
            try {
                FileReader file = new FileReader(path);
                BufferedReader buff = new BufferedReader(file);
                boolean eof = false;
                while (!eof) {
                  String line = buff.readLine();
                  if (line == null) 
                       eof = true;
                  else{
                      textTA.append(line+'\n');                     
                  }
                }
                buff.close();
            } 
            catch (IOException e) {
                System.out.println("Error " + e.toString());
            }
            pathTF.setText(path);
        }
        else {
            System.out.println("No Selection ");
        }       
    }
    
    private void saveChooser(){
        JFileChooser chooser= new JFileChooser();
        int returnVal= chooser.showSaveDialog(this);
        if(returnVal==JFileChooser.APPROVE_OPTION){
            System.out.println("You pressed Save");
            
            File selectedFile = chooser.getSelectedFile();
            path=selectedFile.getAbsolutePath();//gets path
            
            FileWriter out=null;
            try {
                out = new FileWriter(path);
            } 
            catch (IOException ex) {
                Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
                BufferedWriter buff = new BufferedWriter(out);
                String content=textTA.getText();
                content=content.replaceAll("(?!\\r)\\n", "\r\n"); //changes line in saving text
            try {
                buff.write(content);
            } 
            catch (IOException ex) {
                Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            try {
                buff.close();
            } 
            catch (IOException ex) {
                Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            pathTF.setText(path);
        }
        
    }
    
    private void copy() {
        Object frame = "Set file's name";
        String name= JOptionPane.showInputDialog(frame,"");
        FileWriter out = null;
        try {
            out = new FileWriter("C:\\"+copyPath+name);
        } catch (IOException ex) {
            Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
            BufferedWriter buff = new BufferedWriter(out);
            String content=textTA.getText();
            content=content.replaceAll("(?!\\r)\\n", "\r\n");
        try {
            buff.write(content);
        } catch (IOException ex) {
            Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        try {
            buff.close();
        } catch (IOException ex) {
            Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
          }
        
    }
    
    private void statistics() {
        int countWithoutSpaces=0;
        int countWithSpaces=0;
        long size=0;
        int count=0; //for paragraphs
        String directory=pathTF.getText();
        String text=textTA.getText();
        String[] words = Pattern.compile("\\s+").split(text);       
        if(!directory.isEmpty()){ 
           FileInputStream fin=null;
           try {
              fin=new FileInputStream(directory);
              size= fin.getChannel().size(); 
              int ch;
              while((ch=fin.read())!=-1) {
                char c=(char)ch;
                if(c==' ')
                    countWithSpaces++;
                else
                    countWithoutSpaces++;
              }
              countWithSpaces+=countWithoutSpaces;
           } 
           catch (FileNotFoundException ex) {
              Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
           }
           catch (IOException ex) {
              Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
           } 
        
           //number of paragraphs
           FileReader file = null;
           try {
              file = new FileReader(directory);
              BufferedReader buff = new BufferedReader(file);
              String line=buff.readLine();
              boolean eof = false;
            
              StringBuilder paragraph = new StringBuilder();
              while (true) {
                if (line==null || line.trim().length() == 0) {
                    count++;
                    System.out.println("paragraph " + count + ":" + paragraph.toString());
                    paragraph.setLength(0);
                    if(line == null)
                      break;
                } 
                else {
                   paragraph.append(" ");
                   paragraph.append(line);
                }
                line = buff.readLine();
              }
              buff.close(); 
           } 
           catch (FileNotFoundException ex) {
              Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
           } 
           catch (IOException ex) {
              Logger.getLogger(TextEditorFrame.class.getName()).log(Level.SEVERE, null, ex);
           }
           Component frame = null;
           JOptionPane.showMessageDialog( frame,
           "Characters with spaces: "+countWithSpaces+"\nCharacters without spaces: "+countWithoutSpaces+"\nWords: " + words.length +"\nFile size: "+size+" byte\nNumber of paragaphs: "+ count,
           "Statistics",
           JOptionPane.PLAIN_MESSAGE);
        }
        else{
           Component frame = null;
           JOptionPane.showMessageDialog(frame,
           "There is no open file!",
           "Inane error",
           JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
        
    
}
