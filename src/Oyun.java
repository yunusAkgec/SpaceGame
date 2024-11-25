import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Oyun extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer(5,this);
    private int gecenSure = 0;
    private int harcananAtes= 0 ;
    private BufferedImage image;

    private ArrayList<Ates> atesler = new ArrayList<Ates>();

    private int atesdirY = 1;

    private int topX = 0;
    private int topdirX = 2;
    private int uzayGemisX = 0;
    private int dirUzayX = 20;

    public boolean kontrolEt(){
        for (Ates ates : atesler){

            if (new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new Rectangle(topX,0,20,20))){
                return true;
            }

        }
        return false;
    }


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public Oyun() {

        try {
            image = ImageIO.read(new FileImageInputStream(new File("uzaygemisi.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.black);

        timer.start();

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ates ates : atesler){
            ates.setY(ates.getY()-atesdirY*2);

        }
        topX += topdirX;
        if (topX>=750){
            topdirX = -topdirX;
        }
        if (topX<=0){
            topdirX = -topdirX;
        }
        repaint();

    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {


    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {

        int c = e.getKeyCode();

        if (c==KeyEvent.VK_LEFT){
            if (uzayGemisX <= 0){
                uzayGemisX=0;
            }
            else {
                uzayGemisX -= dirUzayX;
            }
        }
        else if (c== KeyEvent.VK_RIGHT){
            if (uzayGemisX >= 750){
                uzayGemisX=750;
            }
            else {
                uzayGemisX += dirUzayX;
            }
        }
        else if (c==KeyEvent.VK_CONTROL){
            atesler.add(new Ates(uzayGemisX+15,475));

            harcananAtes++;

        }

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gecenSure += 5;

        g.setColor(Color.red);
        g.fillOval(topX,0,20,20);

        g.drawImage(image,uzayGemisX,490,image.getWidth()/10,image.getHeight()/10,this);
        Iterator<Ates> iterator = atesler.iterator();
        while (iterator.hasNext()) {
            Ates ates = iterator.next();
            if (ates.getY() < 0) {
                iterator.remove(); // Güvenli kaldırma
            }
        }

        g.setColor(Color.BLUE);

        for (Ates ates : atesler){
            g.fillRect(ates.getX(),ates.getY(),10,20);
        }

        if (kontrolEt()){
            timer.stop();
            String message = "Kazandınız... \n"
                    +"Harcanan Ateş : " + harcananAtes
                    +"Geçen Süre: " + gecenSure/1000.0;
             JOptionPane.showMessageDialog(this,message);
             System.exit(0);
        }
    }

    /**
     * Repaints this component.
     * <p>
     * If this component is a lightweight component, this method
     * causes a call to this component's {@code paint}
     * method as soon as possible.  Otherwise, this method causes
     * a call to this component's {@code update} method as soon
     * as possible.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized
     * by AWT and Swing, including information on how to write the most
     * efficient painting code, see
     * <a href="http://www.oracle.com/technetwork/java/painting-140037.html">Painting in AWT and Swing</a>.
     *
     * @see #update(Graphics)
     * @since 1.0
     */
    @Override
    public void repaint() {
        super.repaint();
    }
}
