package jogl.helloworld;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

public class JoglProject2 extends GLJPanel implements GLEventListener, KeyListener {
	private int frameNumber;
    public static void main(String[] args) {
        JFrame window = new JFrame("Project2 -- arrows to rotate");
        JoglProject2 panel = new JoglProject2();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        panel.requestFocusInWindow();
        
    }
    
    /**
     * Constructor for class JoglProject2.
     */
    public JoglProject2() {
        super( new GLCapabilities(null) ); // Makes a panel with default OpenGL "capabilities".
        setPreferredSize( new Dimension(700,700) );
        addGLEventListener(this); // A listener is essential! The listener is where the OpenGL programming lives.
        addKeyListener(this);
    }
    
    
    double rotateX = 15;    // rotations of the cube about the axes
    double rotateY = -15;
    double rotateZ = 0;
    
    private void square(GL2 gl2, double r, double g, double b) {
        gl2.glColor3d(r,g,b);
        gl2.glBegin(GL2.GL_TRIANGLE_FAN);
        gl2.glVertex3d(-0.5, -0.5, 0.5);
        gl2.glVertex3d(0.5, -0.5, 0.5);
        gl2.glVertex3d(0.5, 0.5, 0.5);
        gl2.glVertex3d(-0.5, 0.5, 0.5);
        gl2.glEnd();
    }
    private void triangle(GL2 gl2) {
    	gl2.glBegin(GL2.GL_TRIANGLES);
    	gl2.glColor3d( 1, 0, 0 ); // red
    	gl2.glVertex2d( -0.2, -0.2 );
    	gl2.glColor3d( 0, 1, 0 ); // green
    	gl2.glVertex2d( 0.2, -0.2 );
    	gl2.glColor3d( 0, 0, 1 ); // blue
    	gl2.glVertex2d( 0, 0.2 );
    	gl2.glEnd(); 
    }
    private void pria(GL2 gl2) {
    	
    	     
        gl2.glBegin(gl2.GL_TRIANGLES);
        gl2.glColor3f(1f,0.0f,0.0f);          // Red
        gl2.glVertex3f( 0.0f, .1f, 0.0f);          // (Front)
        gl2.glColor3f(1.0f,0f,0.0f);          
        gl2.glVertex3f(-0.1f,-0.1f,0.1f);          // (Front)
        gl2.glColor3f(1.0f,0.0f,0f);          
        gl2.glVertex3f( 0.1f,-0.1f, 0.1f); 
        gl2.glColor3f(0f,1.0f,0.0f);          // green
        gl2.glVertex3f( 0.0f, 0.1f, 0.0f);          // (Right)
        gl2.glColor3f(0.0f,1.0f,0f);          // green
        gl2.glVertex3f( 0.1f,-0.1f, 0.1f);          // (Right)
        gl2.glColor3f(0.0f,1f,0.0f);          
        gl2.glVertex3f( 0.1f,-0.1f, -0.1f); 
        gl2.glColor3f(0f,0.0f,1.0f);          // blue
        gl2.glVertex3f( 0.0f, 0.1f, 0.0f);          // (Back)
        gl2.glColor3f(0.0f,0f,1.0f);          // blue
        gl2.glVertex3f( 0.1f,-0.1f, -0.1f);         //  (Back)
        gl2.glColor3f(0.0f,0.0f,1f);          // Blue
        gl2.glVertex3f(-0.1f,-0.1f, -0.1f);
        gl2.glColor3f(1.0f,0.0f,1.0f);          // purple
        gl2.glVertex3f( 0.0f, 0.1f, 0.0f);          // (Left)
        gl2.glColor3f(1.0f,0.0f,1.0f);          // purple
        gl2.glVertex3f(-0.1f,-0.1f,-0.1f);          // (Left)
        gl2.glColor3f(1.0f,0.0f,1.0f);          
        gl2.glVertex3f(-0.1f,-0.1f, 0.1f);          
        gl2.glEnd();
    }
    
    private void shapes(GL2 gl2) {
    	gl2.glBegin(gl2.GL_TRIANGLES);
    	gl2.glColor3f(1f,0.0f,0.0f);          // Red
        gl2.glVertex3f( 0.0f, .1f, 0.0f);          // (Front)
        gl2.glColor3f(1.0f,0f,0.0f);          
        gl2.glVertex3f(-0.1f,-0.1f,0.1f);          // (Front)
        gl2.glColor3f(1.0f,0.0f,0f);          
        gl2.glVertex3f( 0.1f,-0.1f, 0.1f); 
        gl2.glColor3f(0f,1.0f,0.0f);          // green
        gl2.glVertex3f( 0.0f, 0.1f, 0.0f);          // (Right)
        gl2.glColor3f(0.0f,1.0f,0f);          // green
        gl2.glVertex3f( 0.1f,-0.1f, 0.1f);     
        gl2.glEnd();
    	
    	
    	
    }
    
    
    
    private void cube(GL2 gl2, double size) {
        gl2.glPushMatrix();
        gl2.glScaled(size,size,size); // scale unit cube to desired size
        square(gl2,0, 1, 0); // green front face
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 0, 1, 0);
        square(gl2,0, 1, 0); // green right face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 1, 0, 0);
        square(gl2,0, 0, 1); // blue top face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(180, 0, 1, 0);
        square(gl2,0, 0, 1); // blue back face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(-90, 0, 1, 0);
        square(gl2,1, 0, 0); // red left face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 1, 0, 0);
        square(gl2,1, 0, 0); // red bottom face
        gl2.glPopMatrix();
        
        gl2.glPopMatrix(); // Restore matrix to its state before cube() was called.
    }
    
    
    //-------------------- GLEventListener Methods -------------------------

    /**
     * The display method is called when the panel needs to be redrawn.
     * The is where the code goes for drawing the image, using OpenGL commands.
     */
    public void display(GLAutoDrawable drawable) {    
        
        GL2 gl2 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.
        GL2 gl3 = drawable.getGL().getGL2();
        GL2 gl4 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.
        GL2 gl5 = drawable.getGL().getGL2();
        GL2 gl6 = drawable.getGL().getGL2(); // The object that contains all the OpenGL methods.
        GL2 gl7 = drawable.getGL().getGL2();
        GL2 gl8 = drawable.getGL().getGL2();
        GL2 gl9 = drawable.getGL().getGL2();
        GL2 gl10 = drawable.getGL().getGL2();
        gl3.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        
        gl3.glLoadIdentity();              
        gl3.glRotated(rotateZ,0,0,1);
        gl3.glRotated(rotateY,0,1,0);
        gl3.glRotated(rotateX,1,0,0);
        gl3.glTranslatef(0.0f,0.0f,0.0f);   
        triangle(gl3);
        gl2.glTranslatef(0.0f,0.0f,0.3f);
        cube(gl2,.05);
        gl4.glTranslatef(0.0f,0.0f,-0.6f);
        cube(gl4,.1);
        gl5.glTranslatef(0.3f,0.0f,0.3f);
        pria(gl5);
        gl6.glTranslatef(-0.6f,0.0f,0.f);
        cube(gl6,.2);
        gl7.glTranslatef(0.0f,0.0f,0.3f);
        cube(gl7,.05);
        gl8.glTranslatef(0.6f,0.0f,0.0f);
        cube(gl8,.1);
        gl9.glTranslatef(0.0f,0.0f,-0.6f);
        cube(gl9,.1);
        gl10.glTranslatef(-0.6f,0.0f,0.0f);
        cube(gl10,.1);
        
        
        gl2.glPushMatrix();
        gl2.glTranslated(5.8,3,0);
        gl2.glRotated(-frameNumber*0.7,0,0,1);

        gl2.glPopMatrix();

        
    } // end display()

    private void drawDisk(GL2 gl2, double radius) {
        gl2.glBegin(GL2.GL_POLYGON);
        for (int d = 0; d < 32; d++) {
            double angle = 2*Math.PI/32 * d;
            gl2.glVertex2d( radius*Math.cos(angle), radius*Math.sin(angle));
        }
        gl2.glEnd();
    }
    
    
    public void init(GLAutoDrawable drawable) {
           // called when the panel is created
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glOrtho(-1, 1 ,-1, 1, -1, 1);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glClearColor( 0, 0, 0, 1 );
        gl2.glEnable(GL2.GL_DEPTH_TEST);       
        
    }

    public void dispose(GLAutoDrawable drawable) {
            // called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
            // called when user resizes the window
    }
    
    // ----------------  Methods from the KeyListener interface --------------

    public void keyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();
        if ( key == KeyEvent.VK_LEFT )
            rotateY -= 15;
         else if ( key == KeyEvent.VK_RIGHT )
            rotateY += 15;
         else if ( key == KeyEvent.VK_DOWN)
            rotateX += 15;
         else if ( key == KeyEvent.VK_UP )
            rotateX -= 15;
         else if ( key == KeyEvent.VK_PAGE_UP )
            rotateZ += 15;
         else if ( key == KeyEvent.VK_PAGE_DOWN )
            rotateZ -= 15;
         else if ( key == KeyEvent.VK_HOME )
            rotateX = rotateY = rotateZ = 0;
        repaint();
    }

    public void keyReleased(KeyEvent evt) {
    }
    
    public void keyTyped(KeyEvent evt) {
    }
    
}
