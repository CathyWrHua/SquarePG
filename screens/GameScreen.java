package screens;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import animation.Animation;
import animation.EffectAnimation;
import characterEntities.*;
import gui.DamageMarker;

    public class GameScreen extends Screen implements KeyListener{
            private GameMap map;
            private int level = 3;
            private Hero player;
            private ArrayList<Entity> targets;
            private ArrayList<DamageMarker> damageMarkers;
            private ArrayList<Animation> animations;
            private Set<Integer> motionKeys;
            
            public GameScreen(Hero.PlayerClass playerClass) {
                    super();
                    setPreferredSize(new Dimension(1000, 1000));
                    addKeyListener(this);
                    setFocusable(true);
                    setFocusTraversalKeysEnabled(false);
                    
                    targets = new ArrayList<>();
                    damageMarkers = new ArrayList<>();
                    animations = new ArrayList<>();
                    motionKeys = new LinkedHashSet<>();
                    map = new GameMap(level);

                    createPlayer(playerClass);
                    createEnemy(300, 0, 0, 500, 500 ,2);
                    createDummy(400, 100, true);
                    createDummy(600, 100, false);
            }

            @Override
            public void init() {
                    requestFocus(true);
            }

            private void createPlayer(Hero.PlayerClass playerClass) {
                    switch (playerClass) {
                    case RED:
                            player = new RedHero(targets);
                            break;
                    case BLUE:
                            player = new BlueHero(targets);
                            break;
                    case YELLOW:
                            player = new YellowHero(targets);
                            break;
                    default:
                    }
            }

            private void createEnemy(int health, int maxDamage, int minDamage, int posX, int posY, int velocity) {
                    Grunt grunt = new Grunt(player, health, maxDamage, minDamage, posX, posY, velocity);
                    targets.add(grunt);

                    grunt.setTargetEntity(player);
            }

            private void createDummy(int posX, int posY, boolean facingEast) {
                    Dummy dummy = new Dummy(posX, posY, facingEast);
                    targets.add(dummy);

            }

            private void createEffectAnimation(EffectAnimation.EffectAnimationType animationType, int posX, int posY) {
                    EffectAnimation animation = new EffectAnimation(animationType, posX, posY);
                    animations.add(animation);
            }

            private void createProjectile() {}

            @Override
            public void keyPressed(KeyEvent e) {

                    //maintains proper order
                    if (motionKeys.contains(e.getKeyCode())) {
                            motionKeys.remove(e.getKeyCode());
                    }
                    motionKeys.add(e.getKeyCode());

                    for (Integer key : motionKeys) {
                            if (key == KeyEvent.VK_UP) {
                                    up();
                            } else if (key == KeyEvent.VK_DOWN) {
                                    down();
                            } else if (key == KeyEvent.VK_LEFT) {
                                    left();
                            } else if (key == KeyEvent.VK_RIGHT) {
                                    right();
                            }
                    }

                    if (e.getKeyCode() == KeyEvent.VK_J) {
                            map.setMap(1);
                    } else if (e.getKeyCode() == KeyEvent.VK_K) {
                            map.setMap(2);
                    } else if (e.getKeyCode() == KeyEvent.VK_A) {
                            player.attack(Hero.Ability.DEFAULT);
                    } else if (e.getKeyCode() == KeyEvent.VK_Z) {
                            //HACK: this is just so that we can damage the player in testing, there will be bugs with this (ignore them)
                            damageMarkers.add(player.inflict(25, new Dummy(-100, -100, true)));
                    } else if (e.getKeyCode() == KeyEvent.VK_X) {
                            player.heal(25);
                    }
            }

            @Override
            public void keyReleased(KeyEvent e) { 
                    Integer code = e.getKeyCode();
                    
                    if (code == KeyEvent.VK_DOWN ) {
                            player.setUDMotionState(motionKeys.contains(KeyEvent.VK_UP)? Entity.MotionStateUpDown.UP : Entity.MotionStateUpDown.IDLE);
                    } else if (code == KeyEvent.VK_UP){
                            player.setUDMotionState(motionKeys.contains(KeyEvent.VK_DOWN)? Entity.MotionStateUpDown.DOWN : Entity.MotionStateUpDown.IDLE);
                    } else if (code == KeyEvent.VK_LEFT) {
                            player.setLRMotionState(motionKeys.contains(KeyEvent.VK_RIGHT)? Entity.MotionStateLeftRight.RIGHT : Entity.MotionStateLeftRight.IDLE);
                    } else if (code == KeyEvent.VK_RIGHT) {
                            player.setLRMotionState(motionKeys.contains(KeyEvent.VK_LEFT)? Entity.MotionStateLeftRight.LEFT : Entity.MotionStateLeftRight.IDLE);
                    }
                    
                    motionKeys.remove(code);
            }

            @Override
            public void keyTyped(KeyEvent arg0) { }

            @Override
            public void update() {
                    //TODO:Remove all magic width and heights with actual ones

                    Rectangle originalPlayer = new Rectangle(player.getPosX(), player.getPosY(),75,  75);
                    player.update();
                    player.setPoint(map.determineMotion(player.getPosX(), player.getPosY(), originalPlayer, targets));
                    damageMarkers.addAll(player.getEnemyMarkers());
                    player.emptyEnemyMarkers();

                    for (Iterator<Entity> iterator = targets.iterator(); iterator.hasNext();) {
                            Entity target = iterator.next();
                            if (target.getEntityType() == Entity.EntityType.ENEMY) {
                                    Enemy enemy = (Enemy)target;
                                    Rectangle originalEnemy = new Rectangle(enemy.getPosX(), enemy.getPosY(), 75, 75);
                                    enemy.update();
                                    enemy.setPoint(map.determineMotion(enemy.getPosX(), enemy.getPosY(), originalEnemy, new ArrayList<Entity>(Arrays.asList(player))));
                                    if (enemy.isDone()) {
                                            createEffectAnimation(EffectAnimation.EffectAnimationType.ENEMY_DEATH, enemy.getPosX(), enemy.getPosY());
                                            iterator.remove();
                                    }
                            } else if (target.getEntityType() == Entity.EntityType.DUMMY) {
                                    target.update();
                            }
                    }

                    for(Iterator<Animation> iterator = animations.iterator(); iterator.hasNext();) {
                            Animation animation = iterator.next();
                            animation.update();
                            if (animation.isDone()) {
                                    iterator.remove();
                            }
                    }
                    for(Iterator<DamageMarker> iterator = damageMarkers.iterator(); iterator.hasNext();) {
                            DamageMarker marker = iterator.next();
                            marker.update();
                            if (marker.isDone()) {
                                    iterator.remove();
                            }
                    }
            }
            
            public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    map.draw(g);
                    for (Entity target : targets) {
                            target.draw(g);
                    }
                    player.draw(g);
                    for (Animation animation : animations) {
                            animation.draw(g);
                    }
                    for (DamageMarker marker : damageMarkers) {
                            marker.draw(g);
                    }
            }
            
            private void up() {
                    player.setUDMotionState(Entity.MotionStateUpDown.UP);
            }
            private void down() {
		player.setUDMotionState(Entity.MotionStateUpDown.DOWN);
	}
	
	private void left() {
		player.setLRMotionState(Entity.MotionStateLeftRight.LEFT);
	}
	
	private void right() {
		player.setLRMotionState(Entity.MotionStateLeftRight.RIGHT);
	}
}
