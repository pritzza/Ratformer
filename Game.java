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

public class Game implements ActionListener, MouseListener, KeyListener {

    public static Game game;

    public final int WIDTH = 1280, HEIGHT = 720;

    public Renderer renderer;

    public ArrayList<Rectangle> columns, tires, topFloors, bottomFloors, spikes, upgrades, person, checkPoints;

    public Rectangle player;

    public double yMotion, xMotion, playerCenterX, playerCenterY;

    public int ticks, deaths, minutes, seconds, respawnX, respawnY;

    public int areaY = 0;
    public int areaX = 0;

    public int jumps, maxJumps = 1;

    public Random rng;

    public boolean dead, started, ownFourZeroUpgrade, statsOpen = false;

    Color playerColor = new Color(128, 128, 128);
    Color backGroundColor = new Color(88, 88, 88);
    Color topFloorColor = new Color(22, 22, 22);
    Color bottomFloorColor = new Color(32, 32, 32);
    Color columnColor = new Color(16, 16, 16);
    Color tireColor = new Color(0, 255, 255, 127);
    Color spikeColor = new Color(255, 0, 0);
    Color upgradeColor = new Color(0, 255, 0);
    Color personColor = new Color(216, 216, 216, 240);
    Color checkPointColor = new Color(255, 255, 0);

    public Game() {

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

        player = new Rectangle(WIDTH / 2, HEIGHT * 795 / 1080, WIDTH * 30 / 1920, HEIGHT * 55 / 1080);
        columns = new ArrayList<Rectangle>();
        tires = new ArrayList<Rectangle>();
        topFloors = new ArrayList<Rectangle>();
        bottomFloors = new ArrayList<Rectangle>();
        spikes = new ArrayList<Rectangle>();
        upgrades = new ArrayList<Rectangle>();
        person = new ArrayList<Rectangle>();
        checkPoints = new ArrayList<Rectangle>();

        createZeroZero();

        timer.start();

    }

    public void paintColumn(Graphics g, Rectangle column) {

        g.setColor(columnColor);
        g.fillRect(column.x, column.y, column.width, column.height);

    }

    public void paintTire(Graphics g, Rectangle tire) {

        g.setColor(tireColor);
        g.fillRect(tire.x, tire.y, tire.width, tire.height);

    }

    public void paintTopFloor(Graphics g, Rectangle topFloor) {

        g.setColor(topFloorColor);
        g.fillRect(topFloor.x, topFloor.y, topFloor.width, topFloor.height);

    }

    public void paintBottomFloor(Graphics g, Rectangle bottomFloor) {

        g.setColor(bottomFloorColor);
        g.fillRect(bottomFloor.x, bottomFloor.y, bottomFloor.width, bottomFloor.height);

    }

    public void paintSpike(Graphics g, Rectangle spike) {

        g.setColor(spikeColor);
        g.fillRect(spike.x, spike.y, spike.width, spike.height);

    }

    public void paintUpgrade(Graphics g, Rectangle upgrade) {

        g.setColor(upgradeColor);
        g.fillRect(upgrade.x, upgrade.y, upgrade.width, upgrade.height);

    }

    public void paintPerson(Graphics g, Rectangle person) {

        g.setColor(personColor);
        g.fillRect(person.x, person.y, person.width, person.height);

    }

    public void paintCheckPoint(Graphics g, Rectangle checkPoint) {

        g.setColor(checkPointColor);
        g.fillRect(checkPoint.x, checkPoint.y, checkPoint.width, checkPoint.height);

    }

    public void jump() {

        if (!started) {

            started = true;

        }

        if (jumps > 0) {

            jumps--;
            yMotion = -7;

        } else if (true) {

        }
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

    public void createScreens() {

        columns.clear();
        tires.clear();
        topFloors.clear();
        bottomFloors.clear();
        spikes.clear();
        upgrades.clear();
        person.clear();
        checkPoints.clear();
            if (areaX != respawnX || areaY != respawnY) {

                checkPointColor = new Color(255, 255, 0);

            }

        if (areaX == 0 && areaY == 0) {

            createZeroZero();

        } else if (areaX == 1 && areaY == 0) {

            createOneZero();

        } else if (areaX == 2 && areaY == 0) {

            createTwoZero();

        } else if (areaX == 3 && areaY == 0) {

            createThreeZero();

        } else if (areaX == 4 && areaY == 0) {

            createFourZero();

        } else if (areaX == 5 && areaY == 0) {

            createFiveZero();

        } else if (areaX == -1 && areaY == 0) {

            createNegativeOneZero();

        } else if (areaX == -2 && areaY == 0) {

            createNegativeTwoZero();

        } else if (areaX == -3 && areaY == 0) {

            createNegativeThreeZero();

        } else if (areaX == -4 && areaY == 0) {

            createNegativeFourZero();

        } else if (areaX == -4 && areaY == 1) {

            createNegativeFourOne();

        } else if (areaX == 2 && areaY == 1) {

            createTwoOne();

        } else {

            createVoid();

        }
    }

    public void createZeroZero() {

        person.add(new Rectangle(WIDTH * 1536 / 1920, HEIGHT * 795 / 1080, WIDTH * 30 / 1920, HEIGHT * 55 / 1080));

        createFloor1();

    }

    public void createNegativeOneZero() {

        columns.add(new Rectangle(WIDTH * 800 / 1920, HEIGHT * 775 / 1080, WIDTH * 400 / 1920, HEIGHT * 100 / 1080));

        columns.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 350 / 1080, WIDTH * 600 / 1920, HEIGHT * 100 / 1080));
        spikes.add(new Rectangle(WIDTH * 600 / 1920, HEIGHT * 350 / 1080, WIDTH * 50 / 1920, HEIGHT * 100 / 1080));

        columns.add(new Rectangle(WIDTH * 1500 / 1920, HEIGHT * 475 / 1080, WIDTH * 300 / 1920, HEIGHT * 50 / 1080));
        spikes.add(new Rectangle(WIDTH * 1475 / 1920, HEIGHT * 450 / 1080, WIDTH * 350 / 1920, HEIGHT * 100 / 1080));
        tires.add(new Rectangle(WIDTH * 1375 / 1920, HEIGHT * 450 / 1080, WIDTH * 100 / 1920, HEIGHT * 100 / 1080));

        createFloor1();

    }

    public void createNegativeTwoZero() {

        columns.add(new Rectangle(WIDTH * 1250 / 1920, HEIGHT * 350 / 1080, WIDTH * 750 / 1920, HEIGHT * 100 / 1080));

        spikes.add(new Rectangle(WIDTH * 500 / 1920, HEIGHT * 0 / 1080, WIDTH * 150 / 1920, HEIGHT * 250 / 1080));
        columns.add(new Rectangle(WIDTH * 525 / 1920, HEIGHT * 0 / 1080, WIDTH * 100 / 1920, HEIGHT * 225 / 1080));
        spikes.add(new Rectangle(WIDTH * 500 / 1920, HEIGHT * 450 / 1080, WIDTH * 150 / 1920, HEIGHT * 450 / 1080));
        columns.add(new Rectangle(WIDTH * 525 / 1920, HEIGHT * 475 / 1080, WIDTH * 100 / 1920, HEIGHT * 400 / 1080));

        createFloor3();

    }

    public void createNegativeThreeZero() {

        columns.add(new Rectangle(WIDTH * 1425 / 1920, HEIGHT * 500 / 1080, WIDTH * 200 / 1920, HEIGHT * 500 / 1080));

        columns.add(new Rectangle(WIDTH * 525 / 1920, HEIGHT * 0 / 1080, WIDTH * 650 / 1920, HEIGHT * 475 / 1080));
        spikes.add(new Rectangle(WIDTH * 525 / 1920, HEIGHT * 0 / 1080, WIDTH * 675 / 1920, HEIGHT * 500 / 1080));
        columns.add(new Rectangle(WIDTH * 525 / 1920, HEIGHT * 0 / 1080, WIDTH * 50 / 1920, HEIGHT * 675 / 1080));
        spikes.add(new Rectangle(WIDTH * 500 / 1920, HEIGHT * 0 / 1080, WIDTH * 100 / 1920, HEIGHT * 700 / 1080));

        spikes.add(new Rectangle(WIDTH * 1100 / 1920, HEIGHT * 700 / 1080, WIDTH * 100 / 1920, HEIGHT * 350 / 1080));
        columns.add(new Rectangle(WIDTH * 1125 / 1920, HEIGHT * 725 / 1080, WIDTH * 50 / 1920, HEIGHT * 350 / 1080));

        columns.add(new Rectangle(WIDTH * 750 / 1920, HEIGHT * 700 / 1080, WIDTH * 200 / 1920, HEIGHT * 50 / 1080));

        tires.add(new Rectangle(WIDTH * 500 / 1920, HEIGHT * 800 / 1080, WIDTH * 100 / 1920, HEIGHT * 100 / 1080));

        columns.add(new Rectangle(WIDTH * 200 / 1920, HEIGHT * 600 / 1080, WIDTH * 100 / 1920, HEIGHT * 500 / 1080));

        createFloor5();

    }

    public void createNegativeFourZero() {

        // first spike
        spikes.add(new Rectangle(WIDTH * 1325 / 1920, HEIGHT * 500 / 1080, WIDTH * 100 / 1920, HEIGHT * 350 / 1080));
        columns.add(new Rectangle(WIDTH * 1350 / 1920, HEIGHT * 525 / 1080, WIDTH * 50 / 1920, HEIGHT * 350 / 1080));
        tires.add(new Rectangle(WIDTH * 1350 / 1920, HEIGHT * 400 / 1080, WIDTH * 50 / 1920, HEIGHT * 50 / 1080));
        
        //secret
        tires.add(new Rectangle(WIDTH * 1400 / 1920, HEIGHT * -50 / 1080, WIDTH * 50 / 1920, HEIGHT * 50 / 1080));

        // downward spike
        spikes.add(new Rectangle(WIDTH * 925 / 1920, HEIGHT * 325 / 1080, WIDTH * 100 / 1920, HEIGHT * 275 / 1080));
        columns.add(new Rectangle(WIDTH * 950 / 1920, HEIGHT * 325 / 1080, WIDTH * 50 / 1920, HEIGHT * 250 / 1080));

        // bottom spikes
        spikes.add(new Rectangle(WIDTH * 725 / 1920, HEIGHT * 825 / 1080, WIDTH * 600 / 1920, HEIGHT * 25 / 1080));

        // top
        spikes.add(new Rectangle(WIDTH * 600 / 1920, HEIGHT * 300 / 1080, WIDTH * 825 / 1920, HEIGHT * 50 / 1080));
        columns.add(new Rectangle(WIDTH * 600 / 1920, HEIGHT * 275 / 1080, WIDTH * 825 / 1920, HEIGHT * 50 / 1080));
        columns.add(new Rectangle(WIDTH * 600 / 1920, HEIGHT * 0 / 1080, WIDTH * 825 / 1920, HEIGHT * 275 / 1080));

        // left wall
        columns.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 625 / 1080, WIDTH * 750 / 1920, HEIGHT * 300 / 1080));
        columns.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 0 / 1080, WIDTH * 250 / 1920, HEIGHT * 650 / 1080));
        columns.add(new Rectangle(WIDTH * 25 / 1920, HEIGHT * 0 / 1080, WIDTH * 250 / 1920, HEIGHT * 125 / 1080));
        columns.add(new Rectangle(WIDTH * 25 / 1920, HEIGHT * 425 / 1080, WIDTH * 250 / 1920, HEIGHT * 350 / 1080));
        tires.add(new Rectangle(WIDTH * 250 / 1920, HEIGHT * 150 / 1080, WIDTH * 25 / 1920, HEIGHT * 250 / 1080));
        spikes.add(new Rectangle(WIDTH * 250 / 1920, HEIGHT * 125 / 1080, WIDTH * 25 / 1920, HEIGHT * 25 / 1080));
        spikes.add(new Rectangle(WIDTH * 250 / 1920, HEIGHT * 400 / 1080, WIDTH * 25 / 1920, HEIGHT * 25 / 1080));

        createFloor1();

    }

    public void createNegativeFourOne() {

        // floor platform
        columns.add(new Rectangle(WIDTH * 600 / 1920, HEIGHT * 800 / 1080, WIDTH * 825 / 1920, HEIGHT * 375 / 1080));

        // left wall
        columns.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 0 / 1080, WIDTH * 250 / 1920, HEIGHT * 1100 / 1080));
        tires.add(new Rectangle(WIDTH * 250 / 1920, HEIGHT * 970 / 1080, WIDTH * 350 / 1920, HEIGHT * 69 / 1080));

        checkPoints.add(new Rectangle(WIDTH * 1000 / 1920, HEIGHT * 701 / 1080, WIDTH * 50 / 1920, HEIGHT * 100 / 1080));
        
        spikes.add(new Rectangle(WIDTH * 1300 / 1920, HEIGHT * 475 / 1080, WIDTH * 50 / 1920, HEIGHT * 75 / 1080));
        columns.add(new Rectangle(WIDTH * 1349 / 1920, HEIGHT * 475 / 1080, WIDTH * 900 / 1920, HEIGHT * 75 / 1080));

    }

    public void createOneZero() {

        columns.add(new Rectangle(WIDTH * 900 / 1920, HEIGHT * 700 / 1080, WIDTH * 100 / 1920, HEIGHT * 350 / 1080));

        createFloor2();

    }

    public void createTwoZero() {

        columns.add(new Rectangle(WIDTH * 1300 / 1920, HEIGHT * 475 / 1080, WIDTH * 150 / 1920, HEIGHT * 550 / 1080));

        tires.add(new Rectangle(WIDTH * 1000 / 1920, HEIGHT * 620 / 1080, WIDTH * 69 / 1920, HEIGHT * 69 / 1080));

        createFloor1();

    }

    public void createThreeZero() {

        columns.add(new Rectangle(WIDTH * 700 / 1920, HEIGHT * 750 / 1080, WIDTH * 100 / 1920, HEIGHT * 50 / 1080));
        columns.add(new Rectangle(WIDTH * 1100 / 1920, HEIGHT * 750 / 1080, WIDTH * 100 / 1920, HEIGHT * 50 / 1080));

        columns.add(new Rectangle(WIDTH * 360 / 1920, HEIGHT * 750 / 1080, WIDTH * 100 / 1920, HEIGHT * 100 / 1080));
        columns.add(new Rectangle(WIDTH * 1459 / 1920, HEIGHT * 750 / 1080, WIDTH * 100 / 1920, HEIGHT * 100 / 1080));

        spikes.add(new Rectangle(WIDTH * 460 / 1920, HEIGHT * 825 / 1080, WIDTH * 1000 / 1920, HEIGHT * 25 / 1080));

        createFloor1();

    }

    public void createFourZero() {

        // right
        columns.add(new Rectangle(WIDTH * 960 / 1920, HEIGHT * 350 / 1080, WIDTH * 200 / 1920, HEIGHT * 500 / 1080));
        tires.add(new Rectangle(WIDTH * 910 / 1920, HEIGHT * 600 / 1080, WIDTH * 50 / 1920, HEIGHT * 100 / 1080));
        columns.add(new Rectangle(WIDTH * 960 / 1920, HEIGHT * 350 / 1080, WIDTH * 300 / 1920, HEIGHT * 100 / 1080));
        tires.add(new Rectangle(WIDTH * 1160 / 1920, HEIGHT * 450 / 1080, WIDTH * 100 / 1920, HEIGHT * 50 / 1080));

        // top
        columns.add(new Rectangle(WIDTH * 300 / 1920, HEIGHT * 0 / 1080, WIDTH * 300 / 1920, HEIGHT * 600 / 1080));
        tires.add(new Rectangle(WIDTH * 600 / 1920, HEIGHT * 400 / 1080, WIDTH * 50 / 1920, HEIGHT * 100 / 1080));

        createFloor4();

    }

    public void createFiveZero() {

        if (ownFourZeroUpgrade == false) {

            upgrades.add(
                    new Rectangle(WIDTH * 1225 / 1920, HEIGHT * 550 / 1080, WIDTH * 50 / 1920, HEIGHT * 50 / 1080));

        }

        columns.add(new Rectangle(WIDTH * 1100 / 1920, HEIGHT * 810 / 1080, WIDTH * 300 / 1920, HEIGHT * 100 / 1080));
        columns.add(new Rectangle(WIDTH * 1150 / 1920, HEIGHT * 780 / 1080, WIDTH * 200 / 1920, HEIGHT * 100 / 1080));

        columns.add(new Rectangle(WIDTH * 300 / 1920, HEIGHT * 700 / 1080, WIDTH * 150 / 1920, HEIGHT * 150 / 1080));
        columns.add(new Rectangle(WIDTH * 600 / 1920, HEIGHT * 400 / 1080, WIDTH * 160 / 1920, HEIGHT * 450 / 1080));
        columns.add(new Rectangle(WIDTH * 450 / 1920, HEIGHT * 550 / 1080, WIDTH * 150 / 1920, HEIGHT * 300 / 1080));

        columns.add(new Rectangle(WIDTH * 1760 / 1920, HEIGHT * 0 / 1080, WIDTH * 150 / 1920, HEIGHT * 900 / 1080));

        createFloor1();

    }

    public void createTwoOne() {

        columns.add(new Rectangle(WIDTH * 150 / 1920, HEIGHT * 800 / 1080, WIDTH * 1100 / 1920, HEIGHT * 75 / 1080));

        tires.add(new Rectangle(WIDTH * 1500 / 1920, HEIGHT * 900 / 1080, WIDTH * 75 / 1920, HEIGHT * 75 / 1080));
    }

    public void createVoid() {

    }

    // Full floor
    public void createFloor1() {

        topFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 1920 / 1920, HEIGHT * 50 / 1080));
        bottomFloors
                .add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 1920 / 1920, HEIGHT * 180 / 1080));

    }

    // middle gap
    public void createFloor2() {

        topFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 640 / 1920, HEIGHT * 50 / 1080));
        bottomFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 640 / 1920, HEIGHT * 180 / 1080));

        topFloors.add(new Rectangle(WIDTH * 1280 / 1920, HEIGHT * 850 / 1080, WIDTH * 640 / 1920, HEIGHT * 50 / 1080));
        bottomFloors
                .add(new Rectangle(WIDTH * 1280 / 1920, HEIGHT * 850 / 1080, WIDTH * 640 / 1920, HEIGHT * 180 / 1080));

    }

    // right gap
    public void createFloor3() {

        topFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 940 / 1920, HEIGHT * 50 / 1080));
        bottomFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 940 / 1920, HEIGHT * 180 / 1080));

        topFloors.add(new Rectangle(WIDTH * 1730 / 1920, HEIGHT * 850 / 1080, WIDTH * 190 / 1920, HEIGHT * 50 / 1080));
        bottomFloors
                .add(new Rectangle(WIDTH * 1730 / 1920, HEIGHT * 850 / 1080, WIDTH * 190 / 1920, HEIGHT * 180 / 1080));

    }

    // left gap
    public void createFloor4() {

        topFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 600 / 1920, HEIGHT * 50 / 1080));
        bottomFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 600 / 1920, HEIGHT * 180 / 1080));

        topFloors.add(new Rectangle(WIDTH * 960 / 1920, HEIGHT * 850 / 1080, WIDTH * 980 / 1920, HEIGHT * 50 / 1080));
        bottomFloors
                .add(new Rectangle(WIDTH * 960 / 1920, HEIGHT * 850 / 1080, WIDTH * 980 / 1920, HEIGHT * 180 / 1080));

    }

    // little on the right
    public void createFloor5() {

        topFloors.add(new Rectangle(WIDTH * 1425 / 1920, HEIGHT * 850 / 1080, WIDTH * 490 / 1920, HEIGHT * 50 / 1080));
        bottomFloors
                .add(new Rectangle(WIDTH * 1425 / 1920, HEIGHT * 850 / 1080, WIDTH * 490 / 1920, HEIGHT * 180 / 1080));

        topFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 200 / 1920, HEIGHT * 50 / 1080));
        bottomFloors.add(new Rectangle(WIDTH * 0 / 1920, HEIGHT * 850 / 1080, WIDTH * 200 / 1920, HEIGHT * 180 / 1080));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (started) {

        ticks++;

        seconds++;

        playerCenterX = player.x + (player.width / 2);
        playerCenterY = player.y + (player.height / 2);

        
        
            if (seconds >= 60 * 89) {

            minutes++;
            seconds = 0;

        }

        if (started) {

            if (ticks % 4 == 0 && yMotion < 7) {

                yMotion += .8;

            }

            if (dead == false) {

                // master physics d
                player.y += yMotion;
                player.x += xMotion;

            }
        }

        collision();

        renderer.repaint();

        }
    }

    public void die() {

        dead = true;
        deaths++;

    }

    public void respawn() {

        dead = false;
        player.x = HEIGHT - (HEIGHT / 4);
        player.y = HEIGHT / 2;

        areaX = respawnX;
        areaY = respawnY;
        createScreens();

    }

    public void collision() {

        for (Rectangle spike : spikes) {

            if (spike.intersects(player)) {

                if (dead == false) {

                die();

                }
            }
        }

        for (Rectangle upgrade : upgrades) {

            if (upgrade.intersects(player)) {

                if (ownFourZeroUpgrade == false) {

                    ownFourZeroUpgrade = true;
                    maxJumps++;
                    upgradeColor = new Color(0, 255, 0, 0);

                }
            }
        }

        for (Rectangle checkPoint : checkPoints) {

            if (checkPoint.intersects(player)) {

                checkPointColor = new Color(169, 169, 0);

                respawnX = areaX;
                respawnY = areaY;

            }
        }

        // lose one jump when walking of cliff
        if (jumps == maxJumps) {

            jumps = maxJumps - 1;

        }

        // prevent infinite acceleplayerion right
        if (xMotion > 5) {

            xMotion = 5;

        }

        // prevent infinite acceleplayerion left
        if (xMotion < -5) {

            xMotion = -5;

        }

        // dont walk off screen left
        if (playerCenterX <= 0) {

            player.x = WIDTH - player.width;
            areaX--;

            createScreens();

        }

        // dont walk off screen right
        if (playerCenterX >= WIDTH) {

            player.x = 0;
            areaX++;

            createScreens();

        }

        if (playerCenterY <= 0) {

            player.y = HEIGHT - player.height;
            areaY++;

            createScreens();

        }

        if (player.y >= HEIGHT) {

            if (areaY >= 1) {

                player.y = 0;
                areaY--;

                createScreens();

            } else {

                if (dead == false) {

                    die();

                }
            }
        }

        for (Rectangle bottomFloor : bottomFloors) {

            if (bottomFloor.intersects(player)) {

                // if you stand on floor
                if (playerCenterY < bottomFloor.y && player.y < bottomFloor.y && yMotion >= 0) {

                    player.y = bottomFloor.y - player.height;

                    jumps = maxJumps;

                    yMotion = 2.5;

                } else {

                    // hit left of bottomFloor
                    if (playerCenterX < bottomFloor.x) {

                        player.x = bottomFloor.x - player.width;

                        // hit right of bottomFloor
                    } else if (playerCenterX > bottomFloor.x + bottomFloor.width) {

                        player.x = bottomFloor.x + bottomFloor.width;

                    }
                }
            }
        }

        // rectangle collision
        for (Rectangle column : columns) {

            if (column.intersects(player)) {

                // if you hit the floor of column
                if (playerCenterY < column.y && player.y < column.y && yMotion >= 0) {

                    player.y = column.y - player.height;

                    jumps = maxJumps;

                    yMotion = 2.5;

                }

                // if you hit ceiling of column
                else if (playerCenterY > column.y + column.height && yMotion < 0) {

                    player.y = column.y + column.height;

                    yMotion = 0;

                } else {

                    // hit left of column
                    if (playerCenterX < column.x) {

                        player.x = column.x - player.width;

                        // hit right of column
                    } else if (playerCenterX > column.x + column.width) {

                        player.x = column.x + column.width;

                    }
                }
            }

            for (Rectangle tire : tires) {

                if (tire.intersects(player)) {

                    jumps = 1;

                }
            }
        }
    }

    public void repaint(Graphics g) {

        g.setColor(backGroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(playerColor);
        g.fillRect(player.x, player.y, player.width, player.height);

        for (Rectangle spike : spikes) {

            paintSpike(g, spike);

        }

        for (Rectangle column : columns) {

            paintColumn(g, column);

        }

        for (Rectangle tire : tires) {

            paintTire(g, tire);

        }

        for (Rectangle bottomFloor : bottomFloors) {

            paintBottomFloor(g, bottomFloor);

        }

        for (Rectangle topFloor : topFloors) {

            paintTopFloor(g, topFloor);

        }

        for (Rectangle upgrade : upgrades) {

            paintUpgrade(g, upgrade);

        }

        for (Rectangle person : person) {

            paintPerson(g, person);

        }

        for (Rectangle checkPoint : checkPoints) {

            paintCheckPoint(g, checkPoint);

        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 32));

        if (!started) {
            g.drawString("WASD & SPACE", (WIDTH * 3) / 7, (HEIGHT / 2) + 16);
        }
        if (dead) {
            g.drawString("You Died", (WIDTH * 2) / 5, HEIGHT / 2);
        }
        if (statsOpen) {
            g.drawString("Deaths: " + String.valueOf(deaths) + " Time: " + (minutes) + ":" + (seconds / 89) + ";" + (ticks)
            , (WIDTH / 2) - 169, 100);
        }
        /*
         * if (true) { g.drawString("Player (" + String.valueOf(playerCenterX) + ", " +
         * (playerCenterY) + ") Area: (" + (areaX) + ", " + (areaY) + ") " + jumps +
         * " / " + maxJumps, (WIDTH / 2) - 169, 100); }
         */
        for (Rectangle person : person) {

            if (person.intersects(player)) {

                if (ownFourZeroUpgrade == false) {

                g.drawString("Hello.", person.x - (WIDTH * 45 / 1920), person.y - (HEIGHT * 75 / 1080));

                } else if (ownFourZeroUpgrade == true) {

                    g.drawString("There\'s nothing in your eyes.", person.x - (WIDTH * 384 / 1920), person.y - (HEIGHT * 75 / 1080));
    
                }
            }
        }
    }

    public static void main(String[] args) {

        game = new Game();

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if (dead == true) {

                respawn();

            } else {

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

        }

        if (e.getKeyCode() == KeyEvent.VK_S) {

            if (statsOpen == false) {

            statsOpen = true;

            } else if (statsOpen == true) {

            statsOpen = false;

            }
        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {

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

        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

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