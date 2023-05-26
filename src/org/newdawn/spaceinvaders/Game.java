package org.newdawn.spaceinvaders;



import org.newdawn.spaceinvaders.Entity.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;

import javax.swing.*;



/*test*/

/**
 * The main hook of our game. This class with both act as a manager
 * for the display and central mediator for the game logic.
 * <p>
 * Display management will consist of a loop that cycles round all
 * entities in the game asking them to move and then drawing them
 * in the appropriate place. With the help of an inner class it
 * will also allow the player to control the main ship.
 * <p>
 * As a mediator it will be informed when entities within our game
 * detect events (e.g. alient killed, played died) and will take
 * appropriate game actions.
 *
 * @author Kevin Glass
 */

public class Game extends Canvas {

    private static String bestScore = "";
    /**
     * The stragey that allows us to use accelerate page flipping
     */
    private BufferStrategy strategy;
    /**
     * True if the game is currently "running", i.e. the game loop is looping
     */
    private boolean gameRunning = true;
    /**
     * The list of all the entities that exist in our game
     */
    private ArrayList entities = new ArrayList();
    /**
     * The list of entities that need to be removed from the game this loop
     */
    private ArrayList removeList = new ArrayList();
    /**
     * The entity representing the player
     */
    private Entity ship;
    /* The entity representing the shield */
    private Entity shield;
    /**
     * The time at which last fired a shot
     */
    private long lastFire = 0;
    private long lastAlienShot = 0;
    /**
     * The interval between our players shot (ms)
     */
    private long firingInterval = 200; //총알 사이의 간격
    private long AlienShotInterval = 200;

    // 기록용 시간은 event를 통해 발생한 시작시각과 끝난 시각만 파악하도록 함
    private long finishedTime;
    private long startTimeForRecord;

    /**
     * The number of aliens left on the screen
     */
    private int alienCount;
    /* Available to activate increasing fire number */
    private Boolean fireNum = false;
    /* Available to activate shield */
    private Boolean enableShield = false;
    /* easterEgg */
    private boolean easterEgg = false;
    /* Level is parameter of class instance */
    private int level;
    /* Number of killed Aliean */
    private int alienKilled = 0;
    /**
     * The message to display which waiting for a key press
     */
    private String message = ""; //중앙 메시지
    /**
     * True if we're holding up game play until a key has been pressed
     */
    private boolean waitingForKeyPress = true;
    /**
     * True if the left cursor key is currently pressed
     */
    private boolean leftPressed = false;
    /**
     * True if the right cursor key is currently pressed
     */
    private boolean rightPressed = false;
    /**
     * True if the up cursor key is currently pressed
     */

    private boolean upPressed = false;
    /**
     * True if the down cursor key is currently pressed
     */

    private boolean downPressed = false;
    /**
     * True if we are firing
     */
    private boolean firePressed = false;
    /**
     * True if game logic needs to be applied this loop, normally as a result of a game event
     */
    private boolean logicRequiredThisLoop = false;
    /**
     * The last time at which we recorded the frame rate
     */
    private long lastFpsTime;  // 프레임 속도를 기록한 마지막 시간
    /**
     * The current number of frames recorded
     */
    private int fps; // 현재 기록된 프레임 수
    /**
     * The normal title of the game window
     */
    private String windowTitle = "Space Invaders 102";
    /**
     * The game window that we'll update with the frame count
     */
    private JFrame container;

    private FirebaseTool firebaseTool;

    private GlobalStorage globalStorage;

    private Player player;
    /**
     * Construct our game and set it running.
     * @param
     */
    public Game(String level, Boolean[] enableItems, AtomicInteger money, AtomicInteger activeSkin) {
        // create a frame to contain our game
        container = new JFrame("Space Invaders 102");

        setSize(800,600);
        this.setLocationRelativeTo(null);

        this.setVisible(true);


        panel.setLayout(null);

        // setup our canvas size and put it into the content of the frame
        setBounds(0, 0, 800, 600);

        // Tell AWT not to bother repainting our canvas since we're
        // going to do that our self in accelerated mode
        setIgnoreRepaint(true);

        addWindowListener(windowListener);

        // finally make the window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        firebaseTool = FirebaseTool.getInstance();
        globalStorage = GlobalStorage.getInstance();

        // add a listener to respond to the user closing the window. If they
        // do we'd like to exit the game
//        container.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });

        // add a key input system (defined below) to our canvas
        // so we can respond to key pressed
        addKeyListener(new KeyInputHandler());

        // request the focus so key events come to us
        requestFocus();

        // create the buffering strategy which will allow AWT
        // to manage our accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        // recognize what level is
        this.level = level.charAt(15) - 34;

        initEntities();
        playBgm();
    }

    private void playBgm(){
        player = new Player();
        new Thread(() -> {
            player.play("src/sound/backgroundmusic.wav");
        }).start();
    }

    /**
     * Start a fresh game, this should clear out any old data and
     * create a new set. // 이전 기록 보관하기
     */

    WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            // 윈도우 창이 닫힐 때 처리할 내용
            player.pause();
        }
    };
    private void startGame() {
        // clear out any existing entities and intialise a new set
        player.resume();
        entities.clear();
        initEntities();
        startTimeForRecord = System.currentTimeMillis();

        // blank out any keyboard settings we might currently have
        leftPressed = false;
        rightPressed = false;
        upPressed = false;
        downPressed = false;
        firePressed = false;

    }

    /**
     * Initialise the starting state of the entities (ship and aliens). Each
     * entitiy will be added to the overall list of entities in the game.
     */
    private void initEntities() {
        // create the player ship and place it roughly in the center of the screen
        if (Item.activeSkinIndex.get() == -1){ ship = new ShipEntity(this, "sprites/ship.png", 370, 550); }
        else { ship = new ShipEntity(this, "sprites/ship" + Integer.toString(Item.activeSkinIndex.get()+1) +".png", 370, 550); }
        entities.add(ship);

        if(Item.gainedItems[0]){
            ((ShipEntity) this.ship).increaseMaxHealth();
        }
        if(Item.gainedItems[1]){
            this.increaseMoveSpeed();
        }
        if(Item.gainedItems[2]){
            this.enableShield();
        }
        if(Item.gainedItems[3]){
            this.increaseFireNum();
        }
        if(Item.gainedItems[4]){
            this.easterEgg();
        }

        if (this.enableShield == true) {
            shield = new ShieldEntity(this, "sprites/shield.gif", (ShipEntity)ship, 362, 538);
            entities.add(shield);
        }

        alienCount = 0;
        switch(this.level){
            case(0):{
                // create a block of aliens (4 rows, by 5 aliens, spaced evenly)
                for (int row = 0; row < 4; row++) {
                    for (int x = 0; x < 5; x++) {
                        AlienEntity alien = new AlienEntity(this, 100 + (x * 50), (50) + row * 40);
                        entities.add(alien);
                        alienCount++;
                    }
                }
                break;
            }
            case(1):{
                // create a block of aliens (6 rows, by 7 aliens, spaced evenly)
                for (int row = 0; row < 6; row++) {
                    for (int x = 0; x < 7; x++) {
                        AlienEntity alien = new AlienEntity(this, 100 + (x * 50), (50) + row * 40);
                        entities.add(alien);
                        alienCount++;
                    }
                }
                break;
            }
            case(2):{
                // create a block of aliens (7 rows, by 12 aliens, spaced evenly)
                // even row move inversely
                for (int row = 0; row < 5; row++) {
                    for (int x = 0; x < 12; x++) {
                        AlienEntity alien = new AlienEntity(this, 100 + (x * 50), (50) + row * 40);
                        if (row%2 == 0){
                            alien.setHorizontalMovement(alien.getHorizontalMovement()*(-1));
                        }
                        entities.add(alien);
                        alienCount++;
                    }
                }
                break;
            }
            case(3):{
                // create a block of aliens (5 rows, by 5 aliens, spaced evenly)
                for (int row = 0; row < 5; row++) {
                    for (int x = 0; x < 5; x++) {
                        AlienEntity alien = new AlienEntity(this, 200 + (x * 50), (130) + row * 40);
                        entities.add(alien);
                        alienCount++;

                    }
                }
                break;
            }
            case(4):{
                // create a block of aliens (9 rows, by 12 aliens, spaced evenly)
                BossEntity bossAlien = new BossEntity(this, 370,50);
                entities.add(bossAlien);
                alienCount++;

                for (int row = 0; row < 5; row++) {
                    for (int x = 0; x < 5; x++) {
                        AlienEntity alien = new AlienEntity(this, 280 + (x * 50), (130) + row * 40);
                        entities.add(alien);
                        alienCount++;
                    }
                }
                break;
            }
        }
    }

    /**
     * Notification from a game entity that the logic of the game
     * should be run at the next opportunity (normally as a result of some
     * game event)
     */
    public void updateLogic() {
        logicRequiredThisLoop = true;
    }

    /**
     * Remove an entity from the game. The entity removed will
     * no longer move or be drawn.
     *
     * @param entity The entity that should be removed
     */
    public void removeEntity(Entity entity) {
        removeList.add(entity);
    }

    /**
     * Notification that the player has died.
     */
    public void notifyDeath() {
        message = "Oh no! They got you, try again?";

        player.pause();
        new Thread(() -> {
            player.failPlay("src/sound/fail.wav");
        }).start();

        firebaseTool.SetUserBestScore(globalStorage.getUserID(), bestScore);
        waitingForKeyPress = true;
    }

    /**
     * Notification that the player has won since all the aliens
     * are dead.
     */
    public void notifyWin() {
        message = "Well done! You Win!";
        finishedTime = System.currentTimeMillis();
        firebaseTool.SetUserBestScore(globalStorage.getUserID(), bestScore);
        
        Item.gainedItems[this.level] = true;
        Item.money.set(Item.money.get() + this.alienKilled * 10 * this.level);

        player.pause();

        new Thread(() -> {
            player.successPlay("src/sound/success.wav");
        }).start();


        waitingForKeyPress = true;
    }

    /**
     * Notification that an alien has been killed
     */
    public void notifyAlienKilled() {
        // reduce the alient count, if there are none left, the player has won!
        alienCount--;
        if (alienCount == 0) {
            notifyWin();
        }

        // if there are still some aliens left then they all need to get faster, so
        // speed up all the existing aliens
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = (Entity) entities.get(i);

            if (entity instanceof AlienEntity) {
                // speed up by 2%
                entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
            }
        }
    }

    /**
     * Attempt to fire a shot from the player. Its called "try"
     * since we must first check that the player can fire at this
     * point, i.e. has he/she waited long enough between shots
     */
    public void tryToFire() {
        // check that we have waiting long enough to fire
        if (System.currentTimeMillis() - lastFire < firingInterval) {
            return;
        }

        // if we waited long enough, create the shot entity, and record the time.
        lastFire = System.currentTimeMillis();
        if (easterEgg == true){
            easterEggEntity penetration = new easterEggEntity(this, "sprites/longShipshot.png", ship.getX() + 10, ship.getY() - 30);
            entities.add(penetration);
        } else {
            if (fireNum == false) {
                ShotEntity shot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() + 10, ship.getY() - 30);
                entities.add(shot);
            } else if (fireNum == true ) {
                ShotEntity leftShot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() - 40, ship.getY() - 30);
                ShotEntity middleShot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() + 10, ship.getY() - 30);
                ShotEntity rightShot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() + 60, ship.getY() - 30);
                entities.add(leftShot);
                entities.add(middleShot);
                entities.add(rightShot);
            }
        }

        new Thread(() -> {
            player.shootPlay("src/sound/shot.wav");
        }).start();
    }

    public void shotAlien(){
        // check that we have waiting long enough to fire

        if (System.currentTimeMillis() - lastAlienShot < AlienShotInterval) {
            return;
        }
        // if we waited long enough, create the shot entity, and record the time.
        lastAlienShot = System.currentTimeMillis();
        Entity randomAlien = null;
        int size = entities.size() - 1;
        int randomIdx = (int) (Math.random() * size) -1;
        for (int i=0; i<=size; i++) {
            if (randomIdx == i) {
                if (entities.get(i) instanceof AlienEntity) {
                    randomAlien = (Entity) entities.get(i);
                    ShotAlienEntity shot = new ShotAlienEntity(this,"sprites/shot.gif",randomAlien.getX(),randomAlien.getY());
                    entities.add(shot);
                }
            }
        }


    }

    //implement setter of fireNum for Item.
    public void increaseFireNum() {
        this.fireNum = true;
    }

    //implement activate shield.
    public void enableShield() {
        this.enableShield = true;
    }

    //implement easteregg item
    public void easterEgg(){
        this.easterEgg = true;
    }

    /**
     * The main game loop. This loop is running during all game
     * play as is responsible for the following activities:
     * <p>
     * - Working out the speed of the game loop to update moves
     * - Moving the game entities
     * - Drawing the screen contents (entities, text)
     * - Updating game events
     * - Checking Input
     * <p>
     */ // 게임 메인 루프 -> 플레이 중 활동
    public void gameLoop() {

        long lastLoopTime = System.currentTimeMillis();
        long startTimeForDisplay = System.currentTimeMillis();
        this.alienKilled = alienCount;

        // keep looping round til the game ends
        while (gameRunning) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();

            // update the frame counter
            lastFpsTime += delta;
            fps++;

            // update our FPS counter if a second has passed since
            // we last recorded
            if (lastFpsTime >= 1000) {
                setTitle("Space Invaders 102" + " (FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0; //fps = 현재 기록된 프레임 수 -> 화면이 바뀌는 횟수
            }

            // Get hold of a graphics context for the accelerated
            // surface and blank it out  //배경색 설정
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            g.setColor(Color.WHITE);
            g.drawString("Time: " + Long.toString((System.currentTimeMillis() - startTimeForDisplay)/1000), 10, 20);

            g.setColor(Color.WHITE);
            g.drawString("Killed: " + Integer.toString(this.alienKilled - alienCount), 10, 40);

            BufferedImage coin;
            try{
                coin = ImageIO.read(new File("src/sprites/coin.png"));
                g.drawImage(coin, 10, 47, this);
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(Item.money.get()), 20 + coin.getWidth(), 60);
            } catch (IOException e){
                e.printStackTrace();
            }

            BufferedImage heart;
            BufferedImage maxHealth;
            BufferedImage getFaster;
            BufferedImage enableShield;
            BufferedImage moreBullet;
            try {
                //draw health
				int heartNum = ((ShipEntity) ship).returnNowHealth();
                if(heartNum > 0){
                    for(int i=0;i<heartNum;i++){
				    	heart = ImageIO.read(new File("src/sprites/heart.png"));
				    	g.drawImage(heart,32*i+10,558,this);
				    }
                } else {
                    g.setBackground(Color.BLACK);
                    g.clearRect(10, 558, 40, 40);
                }
                //draw enable items
                maxHealth = ImageIO.read(new File("src/sprites/Item maxheartpng.png"));
                getFaster = ImageIO.read(new File("src/sprites/Item speed.png"));
                enableShield = ImageIO.read(new File("src/sprites/Item shield.png"));
                moreBullet = ImageIO.read(new File("src/sprites/Item shot.png"));

                if (Item.gainedItems[0]){
                    g.drawImage(maxHealth,730 - moreBullet.getWidth() - enableShield.getWidth() - getFaster.getWidth() - maxHealth.getWidth()
                    ,558,this);
                }
                if (Item.gainedItems[1]){
                    g.drawImage(getFaster, 745 - moreBullet.getWidth() - enableShield.getWidth() - getFaster.getWidth()
                    ,558,this);
                }
                if (Item.gainedItems[2]){
                    g.drawImage(enableShield,760 - moreBullet.getWidth() - enableShield.getWidth(),558,this);
                }
                if (Item.gainedItems[3]){
                    g.drawImage(moreBullet,775 - moreBullet.getWidth(),558,this);
                }
			} catch (IOException e) {
				e.printStackTrace();
			}

            bestScore = Integer.toString(this.alienKilled - alienCount);

            // cycle round asking each entity to move itself
            if (!waitingForKeyPress) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    
                    entity.move(delta);
                }
            }

            // cycle round drawing all the entities we have in the game
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = (Entity) entities.get(i);

                entity.draw(g);
            }

            // brute force collisions, compare every entity against
            // every other entity. If any of them collide notify
            // both entities that the collision has occured  // 충돌 알림
            for (int p = 0; p < entities.size(); p++) {
                for (int s = p + 1; s < entities.size(); s++) {
                    Entity me = (Entity) entities.get(p);
                    Entity him = (Entity) entities.get(s);

                    if (me.collidesWith(him)) {
                        me.collidedWith(him);
                        him.collidedWith(me);
                    }
                }
            }

            // remove any entity that has been marked for clear up
            entities.removeAll(removeList);
            removeList.clear();

            // if a game event has indicated that game logic should
            // be resolved, cycle round every entity requesting that
            // their personal logic should be considered.   //
            if (logicRequiredThisLoop) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    entity.doLogic();
                }

                logicRequiredThisLoop = false;
            }

            // if we're waiting for an "any key" press then draw the
            // current message
            if (waitingForKeyPress) {
                g.setColor(Color.white);
                g.drawString(message, (800 - g.getFontMetrics().stringWidth(message)) / 2, 250);
                if (message.equals("Well done! You Win!")){
                    g.drawString("Play Time: " + (finishedTime- startTimeForRecord)/1000 + " sec",
                    (800 - g.getFontMetrics().stringWidth("Play Time: " + (finishedTime - startTimeForRecord)/1000 + " sec")) / 2, 275);
                }
                g.drawString("Press any key", (800 - g.getFontMetrics().stringWidth("Press any key")) / 2, 300);
                startTimeForDisplay = System.currentTimeMillis(); // press any key를 기다리는 상태면 startTime을 계속 갱신시켜 흘러가지 않도록 함
            }

            // finally, we've completed drawing so clear up the graphics
            // and flip the buffer over
            g.dispose();
            strategy.show();

            // resolve the movement of the ship. First assume the ship
            // isn't moving. If either cursor key is pressed then
            // update the movement appropraitely
            ship.setHorizontalMovement(0);
            ship.setVerticalMovement(0);

            checkKeyboardPressed(this.ship);
            if (Item.gainedItems[2]){ checkKeyboardPressed(this.shield); }

            // if we're pressing fire, attempt to fire
            if (firePressed) { tryToFire(); }
            // if level 4 or 5, alien can fire
            if (this.level == 4 || this.level == 5) { shotAlien(); }


            // we want each frame to take 10 milliseconds, to do this
            // we've recorded when we started the frame. We add 10 milliseconds
            // to this and then factor in the current time to give
            // us our final value to wait for //?? 화면 바뀌는 거 관련해서 말하는게 맞나요
            try{
                Thread.sleep(System.currentTimeMillis() - lastLoopTime + 10);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private void checkKeyboardPressed(Entity entity) {
        double moveSpeed;

        if (Item.gainedItems[1]){ moveSpeed = 450; }
        else { moveSpeed = 300; }
    
        if ((leftPressed) && (!rightPressed)) {
            entity.setHorizontalMovement(-moveSpeed);
        } else if ((rightPressed) && (!leftPressed)) {
            entity.setHorizontalMovement(moveSpeed);
        }

        if ((upPressed) && (!downPressed)) {
            entity.setVerticalMovement(-moveSpeed);
        } else if ((downPressed) && (!upPressed)) {
            entity.setVerticalMovement(moveSpeed);
        }
    }

    /**
     * A class to handle keyboard input from the user. The class
     * handles both dynamic input during game play, i.e. left/right
     * and shoot, and more static type input (i.e. press any key to
     * continue)
     * <p>
     * This has been implemented as an inner class more through
     * habbit then anything else. Its perfectly normal to implement
     * this as seperate class if slight less convienient.
     * // 키보드 입력 처리 클래스 이동 및 발사
     *
     * @author Kevin Glass
     */
    private class KeyInputHandler extends KeyAdapter {
        /**
         * The number of key presses we've had while waiting for an "any key" press
         */
        private int pressCount = 1;

        /**
         * Notification from AWT that a key has been pressed. Note that
         * a key being pressed is equal to being pushed down but *NOT*
         * released. Thats where keyTyped() comes in.
         *
         * @param e The details of the key that was pressed
         */
        public void keyPressed(KeyEvent e) {
            // if we're waiting for an "any key" typed then we don't
            // want to do anything with just a "press"
            if (waitingForKeyPress) {
                return;
            }


            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = true;
            }
        }

        /**
         * Notification from AWT that a key has been released.
         *
         * @param e The details of the key that was released
         */
        public void keyReleased(KeyEvent e) {
            // if we're waiting for an "any key" typed then we don't
            // want to do anything with just a "released"
            if (waitingForKeyPress) {
                return;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                firePressed = false;
            }
        }

        /**
         * Notification from AWT that a key has been typed. Note that
         * typing a key means to both press and then release it.
         *
         * @param e The details of the key that was typed.
         */
        public void keyTyped(KeyEvent e) {
            // if we're waiting for a "any key" type then
            // check if we've recieved any recently. We may
            // have had a keyType() event from the user releasing
            // the shoot or move keys, hence the use of the "pressCount"
            // counter.
            if (waitingForKeyPress) {
                if (pressCount == 1) {
                    // since we've now recieved our key typed
                    // event we can mark it as such and start
                    // our new game
                    waitingForKeyPress = false;
                    startGame();
                    pressCount = 0;
                } else {
                    pressCount++;
                }
            }

            // if we hit escape, then quit the game
            if (e.getKeyChar() == 27) {
                System.exit(0);
            }
        }
    }
}
