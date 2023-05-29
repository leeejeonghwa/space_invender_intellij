package org.newdawn.spaceinvaders;
import org.newdawn.spaceinvaders.Entity.*;
import org.newdawn.spaceinvaders.Player.AudioPlayer;
import org.newdawn.spaceinvaders.Player.BackgroundPlayer;
import org.newdawn.spaceinvaders.Player.Player;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javax.swing.*;
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
 * detect events (e.g. alien killed, played died) and will take
 * appropriate game actions.
 *
 * @author Kevin Glass
 */

public class Game extends Canvas {
    /**
     * The strategy that allows us to use accelerate page flipping
     */
    private BufferStrategy strategy;
    /**
     * The list of all the entities that exist in our game
     */
    private ArrayList<Entity> entities = new ArrayList<>();
    /**
     * The list of entities that need to be removed from the game this loop
     */
    private ArrayList<Entity> removeList = new ArrayList<>();
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
    // 기록용 시간은 event를 통해 발생한 시작시각과 끝난 시각만 파악하도록 함
    private long finishedTime;
    private long startTimeForRecord;

    /*The number of aliens left on the screen*/
    private int alienCount;
    /* Level is parameter of class instance */
    private int level;
    /* Number of killed Alien */
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
     * The game window that we'll update with the frame count
     */
    private JFrame container;

    private FirebaseTool firebaseTool;

    private GlobalStorage globalStorage;

    private Entity alien;

    private Entity bossAlien;

    private Player backgroundPlayer;
    private Player sucessAudioPlayer;
    private Player failAudioPlayer;

    public Game(int level) {
        // create a frame to contain our game
        container = new JFrame("Space Invaders 102");

        // get hold the content of the frame and set up the resolution of the game //프레임 내용 가져오고 해상도 설정
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800, 600));
        // 화면의 중앙 위치를 계산하여 게임 창을 중앙에 위치시킴
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int) ((screenSize.getWidth() - container.getWidth()) / 2);
        int centerY = (int) ((screenSize.getHeight() - container.getHeight()) / 2);
        container.setLocation(centerX, centerY);


        panel.setLayout(null);

        // set up our canvas size and put it into the content of the frame
        setBounds(0, 0, 800, 600);
        panel.add(this);

        // Tell AWT not to bother repainting our canvas since we're
        // going to do that our self in accelerated mode
        setIgnoreRepaint(true);

        // finally make the window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        firebaseTool = FirebaseTool.getInstance();
        globalStorage = GlobalStorage.getInstance();

        // add a key input system (defined below) to our canvas
        // so, we can respond to key pressed
        addKeyListener(new KeyInputHandler());

        // request the focus so key events come to us
        requestFocus();

        // create the buffering strategy which will allow AWT
        // to manage our accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        // recognize what level is
        this.level = level - 48;

        // initialise the entities in our game so there's something
        // to see at startup
        initEntities();

        container.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                backgroundPlayer.pauseAudio();
                if(sucessAudioPlayer.isPlaying()) sucessAudioPlayer.pauseAudio();
                if(failAudioPlayer.isPlaying()) failAudioPlayer.pauseAudio();
            }
        });
        backgroundPlayer = new BackgroundPlayer("src/sound/backgroundmusic.wav");
        sucessAudioPlayer = new AudioPlayer("src/sound/success.wav");
        failAudioPlayer = new AudioPlayer("src/sound/fail.wav");
    }
    /**
     * Start a fresh game, this should clear out any old data and
     * create a new set. // 이전 기록 보관하기
     */
    private void startGame() {
        if(sucessAudioPlayer.isPlaying()) sucessAudioPlayer.pauseAudio();
        if(failAudioPlayer.isPlaying()) failAudioPlayer.pauseAudio();
        if(!backgroundPlayer.isPlaying()) backgroundPlayer.playAudio();
        // clear out any existing entities and initialize a new set
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
        ship = new ShipEntity(this, "sprites/ship"+ Item.activeSkin.get() +".png",370, 550);
        entities.add(ship);

        if (Item.gainedItems[2]) {
            shield = new ShieldEntity(this, "sprites/shield.gif", (ShipEntity)ship, 362, 538);
            entities.add(shield);
        }

        this.alienCount = 0;
        if(this.level == 5){
            bossAlien = new BossEntity(this, 370,50);
            entities.add(bossAlien);
            this.alienCount++;
        }
        initAlienEntities();
    }

    private void initAlienEntities(){
        Integer[] rowList = new Integer[]{4,6,5,5,5};
        Integer[] colList = new Integer[]{5,7,12,5,5};
        Integer[] xList = new Integer[]{100,100,100,200,280};
        Integer[] yList = new Integer[]{50,50,50,130,130};

        for (int row = 0; row < rowList[this.level-1]; row++) {
            for (int col = 0; col < colList[this.level-1]; col++) {
                alien = new AlienEntity(this, xList[this.level-1] + (col * 50), yList[this.level-1] + row * 40);
                if (this.level == 3 && row % 2 == 0){ alien.setHorizontalMovement(alien.getHorizontalMovement()*(-1)); }
                entities.add(alien);
                this.alienCount++;
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
        firebaseTool.SetUserBestScore(globalStorage.getUserID(), Integer.toString(alienKilled - alienCount));

        backgroundPlayer.pauseAudio();
        new Thread(() -> failAudioPlayer.playAudio()).start();
        waitingForKeyPress = true;
    }

    /**
     * Notification that the player has won since all the aliens
     * are dead.
     */
    public void notifyWin() {
        message = "Well done! You Win!";
        firebaseTool.SetUserBestScore(globalStorage.getUserID(), Integer.toString(alienKilled - alienCount));

        finishedTime = System.currentTimeMillis();
        if (this.level != 5) { Item.gainedItems[this.level-1] = true; }
        Item.money.set(Item.money.get() + alienKilled * 10 * this.level);

        backgroundPlayer.pauseAudio();
        new Thread(() -> sucessAudioPlayer.playAudio()).start();

        waitingForKeyPress = true;
    }

    /**
     * Notification that an alien has been killed
     */
    public void notifyAlienKilled() {
        // reduce the alien count, if there are none left, the player has won!
        alienCount--;
        if (alienCount == 0) {
            notifyWin();
        }

        // if there are still some aliens left then they all need to get faster, so
        // speed up all the existing aliens
        for (Entity entity : entities) {
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
    public void shipTryToFire() {
        // check that we have waiting long enough to fire
        if (System.currentTimeMillis() - lastFire < 200) {
            return;
        }

        // if we waited long enough, create the shot entity, and record the time.
        lastFire = System.currentTimeMillis();
        if (Item.gainedItems[4]){
            easterEggEntity penetration = new easterEggEntity(this, "sprites/longShipshot.png", ship.getX() + 10, ship.getY() - 30);
            entities.add(penetration);
        } else {
            if (Item.gainedItems[3]) {
                ShotEntity leftShot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() - 40, ship.getY() - 30);
                ShotEntity middleShot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() + 10, ship.getY() - 30);
                ShotEntity rightShot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() + 60, ship.getY() - 30);
                entities.add(leftShot);
                entities.add(middleShot);
                entities.add(rightShot);
            } else {
                ShotEntity shot = new ShotEntity(this, "sprites/shipshot.png", ship.getX() + 10, ship.getY() - 30);
                entities.add(shot);
            }
        }
    }

    public void alienTryToFire(){
        // check that we have waiting long enough to fire

        if (System.currentTimeMillis() - lastAlienShot < 200) {
            return;
        }
        // if we waited long enough, create the shot entity, and record the time.
        lastAlienShot = System.currentTimeMillis();
        Entity randomAlien;
        int size = entities.size() - 1;
        int randomIdx = (int) (Math.random() * size) -1;
        for (int i=0; i<=size; i++) {
            if (randomIdx == i) {
                if (entities.get(i) instanceof AlienEntity) {
                    randomAlien = entities.get(i);
                    ShotAlienEntity shot = new ShotAlienEntity(this,"sprites/shot.gif",randomAlien.getX(),randomAlien.getY());
                    entities.add(shot);
                }
            }
        }
    }

    private void entityMovement(Entity entity){
        int moveSpeed;
        if(Item.gainedItems[1]){ moveSpeed = 450;}
        else{ moveSpeed = 300; }

        entity.setHorizontalMovement(0);
        entity.setVerticalMovement(0);

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

    private void drawActivateItems(Graphics2D g){
        try{
            //draw enable items
            String[] itemImages = new String[]{"maxheartpng", "speed", "shield", "shot"};
            ArrayList<BufferedImage> itemImageList = new ArrayList<>();
            for(int i=0;i<4;i+=1){
                itemImageList.add(ImageIO.read(new File("src/sprites/Item "+ itemImages[i] + ".png")));
                if(Item.gainedItems[i]){g.drawImage(itemImageList.get(i),(730+(15*i)) - (38*(4-i)),558,this);}
            }
        } catch (IOException e) { e.printStackTrace(); }
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
        alienKilled = alienCount;

        // keep looping round til the game ends
        while (true) {
            // work out how long it's been since the last update, this
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
                container.setTitle("Space Invaders 102" + " (FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0; //fps = 현재 기록된 프레임 수 -> 화면이 바뀌는 횟수
            }

            // Get hold of a graphics context for the accelerated
            // surface and blank it out  //배경색 설정
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();

            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            g.setColor(Color.WHITE);
            g.drawString("Time: " + (System.currentTimeMillis() - startTimeForDisplay) / 1000, 10, 20);

            g.setColor(Color.WHITE);
            g.drawString("Killed: " + (alienKilled - alienCount), 10, 40);

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
			} catch (IOException e) { e.printStackTrace(); }
            drawActivateItems(g);

            // cycle round drawing all the entities we have in the game
            for (Entity entity : entities) entity.draw(g);

            // brute force collisions, compare every entity against
            // every other entity. If any of them collide notify
            // both entities that the collision has occured  // 충돌 알림
            for (int p = 0; p < entities.size(); p++) {
                for (int s = p + 1; s < entities.size(); s++) {
                    Entity me = entities.get(p);
                    Entity him = entities.get(s);

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
                for (Entity entity : entities) {
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
            } else { // cycle round asking each entity to move itself
                for (Entity entity : entities) { entity.move(delta); }
            }

            // finally, we've completed drawing so clear up the graphics
            // and flip the buffer over
            g.dispose();
            strategy.show();

            // resolve the movement of the ship. First assume the ship
            // isn't moving. If either cursor key is pressed then
            // update the movement appropriately
            this.entityMovement(ship);

            //shield will move with ship
            if (Item.gainedItems[2]) this.entityMovement(shield);

            // if we're pressing fire, attempt to fire
            if (firePressed) {shipTryToFire();}
            if (level >= 4) {alienTryToFire();}

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

    /**
     * A class to handle keyboard input from the user. The class
     * handles both dynamic input during game play, i.e. left/right
     * and shoot, and more static type input (i.e. press any key to
     * continue)
     * <p>
     * This has been implemented as an inner class more through
     * habbit than anything else. Its perfectly normal to implement
     * this as seperated class if slight less convenient.
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
         * released. That's where keyTyped() comes in.
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
            // if we're waiting for an "any key" type then
            // check if we've received any recently. We may
            // have had a keyType() event from the user releasing
            // the shoot or move keys, hence the use of the "pressCount"
            // counter.
            if (waitingForKeyPress) {
                if (pressCount == 1) {
                    // since we've now received our key typed
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