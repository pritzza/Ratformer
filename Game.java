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

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import javax.swing.Timer;
import javax.swing.JFrame;

import java.util.ArrayList;
import java.util.Random;

public class Game implements ActionListener, MouseListener, KeyListener {

    public static Game game;

    public final int WIDTH = 1920, HEIGHT = 1080;

    public Renderer renderer;

    public ArrayList<Rectangle> columns, doors;

    public Rectangle player;

    public double  yMotion, xMotion;

    public int ticks, jumps, areaX, areaY = 0;

   int playerCenterX ;
   int playerCenterY ;

    public Random rng;

    public boolean gameOver, started;

    GraphicsDevice gDevice;

    int currentScreenWidth, currentScreenHeight;

    /*
     * Color playerColor = new Color(112, 104, 92); Color backGroundColor = new
     * Color(94, 92, 89); Color epiGroundColor = new Color(44, 39, 33); Color
     * groundColor = new Color(60, 53, 44); Color hypoGroundColor = new Color(32,
     * 32, 32);
     */

    Color playerColor = new Color(128, 128, 128);
    Color backGroundColor = new Color(88, 88, 88);
    Color epiGroundColor = new Color(22, 22, 22);
    Color groundColor = new Color(32, 32, 32);
    Color hypoGroundColor = new Color(16, 16, 16);
    Color doorColor = new Color(255, 250, 250);

    public Game() {

        GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gDevice = gEnvironment.getDefaultScreenDevice();

        JFrame f = new JFrame();
        Timer timer = new Timer(10, this);

        renderer = new Renderer();
        rng = new Random();

        f.add(renderer);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(WIDTH, HEIGHT);
        f.addMouseListener(this);
        f.addKeyListener(this);
        f.setResizable(true);
        f.setVisible(true);

        player = new Rectangle(WIDTH / 2, 895, 20, 35);
        columns = new ArrayList<Rectangle>();
        // doors = new ArrayList<Rectangle>();

        createSpawn();

        timer.start();

    }

    public void paintColumn(Graphics g, Rectangle column) {

        g.setColor(hypoGroundColor);
        g.fillRect(column.x, column.y, column.width, column.height);

    }

    /*
     * public void paintDoor(Graphics g, Rectangle door) {
     * 
     * g.setColor(doorColor); g.fillRect(door.x, door.y, door.width, door.height);
     * 
     * }
     */
    public void jump() {

        jumps++;

        if (!started) {

            started = true;

        }

        yMotion = -7;

    }

    public void runLeft() {

        while (xMotion > -5) {

            xMotion -= 1;
        }
    }

    public void runRight() {

        while (xMotion < 5) {

            xMotion += 1;
        }
    }

    /*
     * public void enterDoor() {
     * 
     * columns.clear(); doors.clear();
     * 
     * player.y = 895; player.x = WIDTH / 2 - (player.width / 2);
     * 
     * if (level == 1) {
     * 
     * createLevelOne();
     * 
     * level = 0;
     * 
     * } else {
     * 
     * createSpawn();
     * 
     * }
     * 
     * renderer.repaint();
     * 
     * }
     */
    public void createSpawn() {

        columns.add(new Rectangle(1100, 400, 320, 320));
        columns.add(new Rectangle(400, 250, 150, 150));
        columns.add(new Rectangle(1600, 790, 128, 32));
        columns.add(new Rectangle(1636, 520, 32, 128));

        // doors.add(new Rectangle(450, 175, 50, 75));
        // doors.add(new Rectangle(640, 855, 50, 75));

    }

    public void createLevelOne() {

        columns.add(new Rectangle(900, 350, 150, 150));
        columns.add(new Rectangle(300, 650, 100, 100));

        // doors.add(new Rectangle((WIDTH / 2 - (player.width / 2)), 855, 50, 75));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ticks++;

        playerCenterX = player.x + (player.width / 2);

        if (started) {

            if (ticks % 4 == 0 && yMotion < 7) {

                yMotion += .8;

            }

            for (int i = 0; i < columns.size(); i++) {

                Rectangle column = columns.get(i);

                if (column.x + column.width < 0) {

                    columns.remove(column);

                }
            }

            // master physics
            player.y += yMotion;
            player.x += xMotion;

        }

        collision();

        renderer.repaint();
    }

    public void collision() {

        // hit top of jframe
        if (player.y < 0) {

            player.y = 0;
            yMotion = 0;
        }

        // floor collision
        if (player.y > 895) {

            player.y = 895;
            jumps = 0;

        } else {

            // lose one jump when walking of cliff
            if (jumps == 0) {

                jumps = 1;

            }
        }

        // prevent infinite acceleplayerion right
        if (xMotion > 5) {

            xMotion = 5;

        }

        // prevent infinite acceleplayerion left
        if (xMotion < -5) {

            xMotion = -5;

        }

        // i dont know what this does, something with collision and sinking into the
        // floor
        if (player.y + yMotion >= HEIGHT - 150) {

            player.y = HEIGHT - 150 - player.height;

        }

        // dont walk off screen left
        if (playerCenterX <= 0) {

            player.x = 1905 - player.width;
            areaX--;

        }

        // dont walk off screen right
        if (playerCenterX >= 1905) {

            player.x = 0;
            areaX++;


        }

        // rectangle collision
        for (Rectangle column : columns) {

            if (column.intersects(player)) {

                // if you hit the floor of column
                if (player.y + player.height > column.y && player.y < column.y) {

                    player.y = column.y - player.height;

                    jumps = 0;

                }

                // if you hit ceiling of column
                else if (player.y < column.y + column.height && player.y + player.height > column.y + column.height) {

                    player.y = column.y + column.height;

                    yMotion = 0;

                } else {

                    // hit left of column
                    if (player.x - player.width <= column.x) {

                        player.x = column.x - player.width;

                        // hit right of column
                    } else if (player.x <= column.x + column.width) {

                        player.x = column.x + column.width;

                    }
                }
            }
        }
    }

    public void repaint(Graphics g) {

        g.setColor(backGroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(groundColor);
        g.fillRect(0, HEIGHT - 150, WIDTH, 150);

        g.setColor(epiGroundColor);
        g.fillRect(0, HEIGHT - 150, WIDTH, 30);

        g.setColor(playerColor);
        g.fillRect(player.x, player.y, player.width, player.height);

        for (Rectangle column : columns) {

            paintColumn(g, column);
        }

        /*
         * for (Rectangle door : doors) {
         * 
         * paintDoor(g, door); }
         */
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 32));

        if (!started) {
            g.drawString("WASD & SPACE", (WIDTH / 2) + 172, (HEIGHT / 2) + 16);
        }
        if (gameOver) {
            g.drawString("You Died", WIDTH / 2 - 100, HEIGHT / 2);
        }
        if (!gameOver && started) {
            g.drawString("X: " + String.valueOf(player.x) + " Y: " + (player.y) + " Jumps: " + (jumps) + " centerX: " + (playerCenterX), WIDTH / 2, 100);
        }
    }

    /*
     * for (Rectangle door : doors) {
     * 
     * if (door.intersects(player)) {
     * 
     * g.drawString("W TO ENTER", door.x - 75, door.y - 100);
     * 
     * if (player.y < 400) {
     * 
     * level = 1;
     * 
     * }
     * 
     * } } }
     */
    public static void main(String[] args) {

        game = new Game();

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

        if (e.getKeyCode() == KeyEvent.VK_W) {

            /*
             * for (Rectangle door : doors) {
             * 
             * if (door.intersects(player)) {
             * 
             * enterDoor();
             * 
             * } }
             */ }

        if (e.getKeyCode() == KeyEvent.VK_S) {

        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

            runRight();

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

            runLeft();

        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {

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