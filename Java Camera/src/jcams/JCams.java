
package jcams;

import javax.swing.*;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JFrame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import static org.opencv.ml.CvSVM.C;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author Saptka
 */
public class JCams {

    static boolean istrue=true;
    public static void main(String[] args) {
      
        System.out.println("My First Java Camera");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CascadeClassifier cc=new CascadeClassifier();
        Mat showCam=new Mat();
        Highgui hq=new Highgui();
        VideoCapture setOn =new VideoCapture(-1);
        CamView cam=new CamView();
        JFrame f=new JFrame("LookAtMe");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(showCam.width(), showCam.height());
        f.setContentPane(cam);
        f.setVisible(true);
        f.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    istrue=false;
                    Highgui.imwrite("MyFoto.jpeg", showCam);
                    System.out.println("captured");
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                }

            @Override
            public void mouseEntered(MouseEvent e) {
                }

            @Override
            public void mouseExited(MouseEvent e) {
              
            }
        });
        
        if( setOn.isOpened())  
        {  
            while( istrue )  
            {  
                setOn.read(showCam);  
                if( !showCam.empty() )  
                {   
                    f.setSize(showCam.width(),showCam.height());
                    cam.ImageToView(showCam); 
                    cam.repaint();                      
                }  
                else  
                {   
                    break;   
                }  
            }  
        }  

            
        return;  
    }

    private static class CamView extends JPanel {

        
        private BufferedImage imageView; 
    
        public CamView() {
            super();
        }
        
        public boolean ImageToView(Mat matBGR){  
        int width = matBGR.width(), height = matBGR.height(), channels = matBGR.channels() ;  
        byte[] sourcePixels = new byte[width * height * channels];  
        matBGR.get(0, 0, sourcePixels);  
        imageView = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);  
        final byte[] targetPixels = ((DataBufferByte) imageView.getRaster().getDataBuffer()).getData();  
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);  
         
        return true;  
    }
        public void paintComponent(Graphics g){  
        super.paintComponent(g);   
        if (this.imageView==null) return;  
        g.drawImage(this.imageView,5,5,this.imageView.getWidth(),this.imageView.getHeight(), null);  
         
    }
        
    }
    
}
