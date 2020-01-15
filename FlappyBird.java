import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {

    public static FlappyBird flappyBird;

    public final int WIDTH = 1920, HEIGHT = 800;

    public Renderer renderer;

    public ArrayList<Rectangle> columns;

    public Rectangle rat;

    public double ticks, yMotion, xMotion, score, jumps;

    public int speed = 0;

    public Random rng;

    public boolean gameOver, started;

    Color ratColor = new Color(112, 104, 92);
    Color backGroundColor = new Color(94, 92, 89);
    Color epiGroundColor = new Color(44, 39, 33);
    Color groundColor = new Color(60, 53, 44);
    Color hypoGroundColor = new Color(32, 32, 32);

    public FlappyBird() {

        JFrame f = new JFrame();
        Timer timer = new Timer(10, this);

        renderer = new Renderer();
        rng = new Random();

        f.add(renderer);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(WIDTH, HEIGHT);
        f.addMouseListener(this);
        f.addKeyListener(this);
        f.setResizable(false);
        f.setVisible(true);

        rat = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 55, 25);
        columns = new ArrayList<Rectangle>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();

    }

    public void addColumn(boolean start) {

        int space = 450;
        int width = 300;
        int height = 100 + rng.nextInt(100);

        if (start) {
            columns.add(new Rectangle(1100, 400, 250, 250));
            columns.add(new Rectangle(400, 250, 150, 150));

         
        }
    }

    public void paintColumn(Graphics g, Rectangle column) {

        g.setColor(hypoGroundColor);
        g.fillRect(column.x, column.y, column.width, column.height);

    }

    public void jump() {

        jumps++;
        /*
         * if (gameOver) {
         * 
         * rat = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 55, 25);
         * columns.clear(); yMotion = 0; score = 0;
         * 
         * addColumn(true); addColumn(true); addColumn(true); addColumn(true);
         * 
         * gameOver = false; }
         */
        if (!started) {

            started = true;

        }
        /*
         * else if (!gameOver) {
         * 
         * if (yMotion > 0) {
         * 
         * yMotion = 0;
         * 
         * }
         */
        yMotion = -9;

    }

    public void runLeft() {

        while (xMotion > -8){

        xMotion -= 1;
        }
    }

    public void runRight() {

        while (xMotion < 8){

            xMotion += 1;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ticks++;

        if (started) {
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                column.x -= speed;
            }

            if (ticks % 3 == 0 && yMotion < 9 ) {

                yMotion += .8;

                
            }

            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                if (column.x + column.width < 0) {
                    columns.remove(column);

                    if (column.y == 0) {
                        addColumn(false);
                    }
                }
            }

            rat.y += yMotion;
            rat.x += xMotion;

            if (rat.y < 0) {
                rat.y = 0;
                yMotion = 0;
            }

            if (rat.y > 625) {
                
                rat.y = 625;

                jumps = 0;
            }

            if (xMotion > 8) {
                xMotion = 8;
            }
            if (xMotion < -8) {
                xMotion = -8;
            }

            for (Rectangle column : columns) {

                if (!gameOver && column.y == 0 && rat.x + rat.width / 2 > column.x + column.width / 2
                        && rat.x + rat.width / 2 < column.x + column.width / 2 + 10) {

                    score++;
                }
                if (column.intersects(rat)) {

                    if (rat.y - rat.height < column.y && rat.y < column.y) {

                        rat.y = column.y - rat.height;

                        rat.x -= speed;

                        jumps = 0;

                    }

                    else if (rat.y < column.y + column.height && rat.y + rat.height > column.y + column.height) {

                        rat.y = column.y + column.height;

                        yMotion = 0;

                    } else {

                        if (rat.x - rat.width <= column.x) {

                            rat.x = column.x - rat.width;

    
                        } else if (rat.x <= column.x + column.width) {
    
                            rat.x = column.x + column.width;

    
                        }
                    }  
                }
            }

            /*
             * if (rat.y > HEIGHT - 150 - rat.height || rat.y < -50) {
             * 
             * gameOver = true;
             * 
             * }
             */ if (rat.y + yMotion >= HEIGHT - 150) {

                rat.y = HEIGHT - 150 - rat.height;

            }
            if (rat.x <= 0) {
                rat.x = 0;
            }
            if (rat.x >= 1905 - rat.width) {
                rat.x = 1905 - rat.width;
            }
        }

        renderer.repaint();
    }

    public void repaint(Graphics g) {

        g.setColor(backGroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(groundColor);
        g.fillRect(0, HEIGHT - 150, WIDTH, 150);

        g.setColor(epiGroundColor);
        g.fillRect(0, HEIGHT - 150, WIDTH, 30);

        g.setColor(ratColor);
        g.fillRect(rat.x, rat.y, rat.width, rat.height);

        for (Rectangle column : columns) {

            paintColumn(g, column);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 50));

        if (!started) {
            g.drawString("Click To Start", WIDTH / 3, HEIGHT / 2 - 50);
        }
        if (gameOver) {
            g.drawString("Hi Tanay", WIDTH / 3, HEIGHT / 2 - 50);
        }
         if (!gameOver && started) {
        g.drawString( "X: " + String.valueOf(rat.x) + " Y: " + (rat.y) , WIDTH / 2 - 10, 100);
        }
        if (gameOver == true) {
        g.drawString( "X: " + String.valueOf(rat.x) + " Y: " + (rat.y) , WIDTH / 2 - 10, 100);
         }
    }

    public static void main(String[] args) {

        flappyBird = new FlappyBird();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        jump();

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if (jumps <= 1) {

            jump();

            }
            

        }

        if (e.getKeyCode() == KeyEvent.VK_D) {

            runRight();

        }

        if (e.getKeyCode() == KeyEvent.VK_A) {

            runLeft();

        }

        if (e.getKeyCode() == KeyEvent.VK_S) {

            if (speed != 0) {
                speed = 0;
            } else {
                speed = 5;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            runRight();

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            runLeft();

        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {

            if (speed != 0) {
                speed = 0;
            } else {
                speed = 5;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) {

            while (xMotion < 0) {
            xMotion++;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {

            while (xMotion > 0) {
            xMotion--;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            while (xMotion < 0) {
            xMotion++;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            while (xMotion > 0) {
            xMotion--;
            }
        }

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

    @Override
    public void keyTyped(KeyEvent e) {

    }
}