package de.relluem94.vulcan.entities.components;

import static de.relluem94.vulcan.main.Main.showDebugInfo;
import static de.relluem94.vulcan.main.Things.control;
import static de.relluem94.vulcan.main.Things.entities;
import static de.relluem94.vulcan.main.Things.isChatOpen;
import static de.relluem94.vulcan.main.Things.normalMapEntities;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import de.relluem94.vulcan.entities.BaseEntity;
import de.relluem94.vulcan.entities.TerrainEntity;
import de.relluem94.vulcan.main.Main;
import de.relluem94.vulcan.renderEngine.postProcessing.PostProcessConfig;
import de.relluem94.vulcan.renderEngine.postProcessing.PostProcessing;
import de.relluem94.vulcan.toolbox.GameAction;
import de.relluem94.vulcan.toolbox.MusicPlayer;
import de.relluem94.vulcan.toolbox.Utils;
import de.relluem94.vulcan.toolbox.Variables;
import de.relluem94.vulcan.toolbox.maths.Color3f;

public class ControlComponent {

    public static boolean polymode = true;

    private CollisionComponent cc;
    private MoveComponent mc;

    public static GameAction ga_f1;
    public static GameAction ga_f2;
    public static GameAction ga_f3;
    public static GameAction ga_f4;
    public static GameAction ga_f5;
    public static GameAction ga_f6;
    public static GameAction ga_f7;
    public static GameAction ga_f8;
    public static GameAction ga_f9;
    public static GameAction ga_f10;
    public static GameAction ga_f11;
    public static GameAction ga_f12;

    public static GameAction ga_t;

    public static GameAction ga_w2;
    public static GameAction ga_a2;
    public static GameAction ga_s2;
    public static GameAction ga_d2;

    public static GameAction ga_b;
    public static GameAction ga_c;
    public static GameAction ga_e;
    public static GameAction ga_f;
    public static GameAction ga_g;
    public static GameAction ga_h;
    public static GameAction ga_i;
    public static GameAction ga_j;
    public static GameAction ga_k;
    public static GameAction ga_l;
    public static GameAction ga_m;
    public static GameAction ga_n;
    public static GameAction ga_o;
    public static GameAction ga_p;
    public static GameAction ga_q;
    public static GameAction ga_r;
    public static GameAction ga_u;
    public static GameAction ga_v;
    public static GameAction ga_x;
    public static GameAction ga_y;
    public static GameAction ga_z;

    public static GameAction ga_back;
    public static GameAction ga_score;

    public static GameAction ga_w;
    public static GameAction ga_a;
    public static GameAction ga_s;
    public static GameAction ga_d;
    public static GameAction ga_space;
    public static GameAction ga_space2;
    public static GameAction ga_shift;
    public static GameAction ga_enter;

    public static GameAction ga_0;
    public static GameAction ga_1;
    public static GameAction ga_2;
    public static GameAction ga_3;
    public static GameAction ga_4;
    public static GameAction ga_5;
    public static GameAction ga_6;
    public static GameAction ga_7;
    public static GameAction ga_8;
    public static GameAction ga_9;

    public static GameAction ga_punkt;
    public static GameAction ga_komma;

    public static GameAction ga_esc;

    private boolean isInAir = false;

    public String name;
    private Random random = new Random();
    private MusicPlayer mp;

    private int id;

    public ControlComponent(int id) {
        this.id = id;
        cc = new CollisionComponent(id);
        mc = new MoveComponent(id);
        mp = new MusicPlayer();
    }

    public int getId() {
        return id;
    }

    public void setRunSpeed(float runspeed) {
        mc.setRunSpeed(runspeed);
    }

    public void setBoostSpeed(float boostspeed) {
        mc.setBoostSpeed(boostspeed);
    }

    public void setGravity(float gravity) {
        mc.setGravity(gravity);
    }

    public void setJumpPower(float jumppower) {
        mc.setJumpPower(jumppower);
    }

    public void setTurnSpeed(float turnspeed) {
        mc.setTurnSpeed(turnspeed);
    }

    public void set(float runspeed, float boostspeed, float gravity, float jumppower, float turnspeed) {
        mc.set(runspeed, boostspeed, gravity, jumppower, turnspeed);

        ga_w2 = new GameAction("w", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_W);
        ga_a2 = new GameAction("a", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_A);
        ga_s2 = new GameAction("s", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_S);
        ga_d2 = new GameAction("d", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_D);

        ga_b = new GameAction("b", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_B);
        ga_c = new GameAction("c", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_C);
        ga_e = new GameAction("e", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_E);
        ga_f = new GameAction("f", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F);
        ga_g = new GameAction("g", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_G);
        ga_h = new GameAction("h", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_H);
        ga_i = new GameAction("i", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_I);
        ga_j = new GameAction("j", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_J);
        ga_k = new GameAction("k", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_K);
        ga_l = new GameAction("l", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_L);
        ga_m = new GameAction("m", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_M);
        ga_n = new GameAction("n", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_N);
        ga_o = new GameAction("o", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_O);
        ga_p = new GameAction("p", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_P);
        ga_q = new GameAction("q", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_Q);
        ga_r = new GameAction("r", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_R);
        ga_u = new GameAction("u", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_U);
        ga_v = new GameAction("v", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_V);
        ga_x = new GameAction("x", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_X);
        ga_y = new GameAction("y", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_Y);
        ga_z = new GameAction("z", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_Z);
        ga_score = new GameAction("-", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_MINUS);
        ga_back = new GameAction("back", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_BACK);
        ga_enter = new GameAction("enter", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_RETURN);
        ga_space2 = new GameAction("space", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_SPACE);

        ga_w = new GameAction("w", GameAction.NORMAL, Keyboard.KEY_W);
        ga_a = new GameAction("a", GameAction.NORMAL, Keyboard.KEY_A);
        ga_s = new GameAction("s", GameAction.NORMAL, Keyboard.KEY_S);
        ga_d = new GameAction("d", GameAction.NORMAL, Keyboard.KEY_D);

        ga_shift = new GameAction("shift", GameAction.NORMAL, Keyboard.KEY_LSHIFT);

        ga_f1 = new GameAction("f1", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F1);
        ga_f2 = new GameAction("f2", GameAction.NORMAL, Keyboard.KEY_F2);
        ga_f3 = new GameAction("f3", GameAction.NORMAL, Keyboard.KEY_F3);
        ga_f4 = new GameAction("f4", GameAction.NORMAL, Keyboard.KEY_F4);
        ga_f5 = new GameAction("f5", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F5);
        ga_f6 = new GameAction("f6", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F6);
        ga_f7 = new GameAction("f7", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F7);
        ga_f8 = new GameAction("f8", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F8);
        ga_f9 = new GameAction("f9", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F9);
        ga_f10 = new GameAction("f10", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F10);
        ga_f11 = new GameAction("f11", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F11);
        ga_f12 = new GameAction("f12", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_F12);

        ga_space = new GameAction("space", GameAction.NORMAL, Keyboard.KEY_SPACE);

        ga_t = new GameAction("t", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_T);

        ga_0 = new GameAction("0", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_0);
        ga_1 = new GameAction("1", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_1);
        ga_2 = new GameAction("2", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_2);
        ga_3 = new GameAction("3", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_3);
        ga_4 = new GameAction("4", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_4);
        ga_5 = new GameAction("5", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_5);
        ga_6 = new GameAction("6", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_6);
        ga_7 = new GameAction("7", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_7);
        ga_8 = new GameAction("8", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_8);
        ga_9 = new GameAction("9", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_9);
        ga_esc = new GameAction("esc", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_ESCAPE);

        ga_punkt = new GameAction(".", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_PERIOD);
        ga_komma = new GameAction(",", GameAction.DETECT_INITIAL_PRESS_ONLY, Keyboard.KEY_COMMA);
    }

    public void move() {
        if (control) {
            checkInputs();
        } else {
            if (isChatOpen) {
                if (!Keyboard.getEventKeyState()) {
                    if (ga_back.isPressed()) {
                        if (Main.chat.getText().length() >= 1) {
                            Main.chat.setText(Main.chat.getText().substring(0, Main.chat.getText().length() - 1));
                        }
                    }
                    if (ga_esc.isPressed()) {
                        Main.toggleChat();
                    }
                    if (ga_enter.isPressed()) {
                        Main.toggleChat();
                        String command = Main.chat.getText();
                        for (int i = 0; i < command.length(); i++) {
                            if (command.startsWith(" ")) {
                                command = command.replaceFirst(" ", "");
                            }
                        }

                        if (!(command == "")) {
                            try {
                                if (command.equals(" ")) {
                                    Utils.log("String can't be Null", 1);
                                } else if (command.equals("")) {
                                    Utils.log("String can't be Null", 1);
                                } else {
                                    String[] args = command.split(" ");
                                    Main.chat.setText("");
                                    Utils.execute(args);
                                }
                            } catch (NullPointerException e) {
                                Utils.log("String can't be Null", 1);
                            } catch (ArrayIndexOutOfBoundsException e) {
                                Utils.log("Array Index out of Bounds", 1);
                            }

                        }
                    }

                    if (Main.chat.getText().length() <= Variables.chatlength) {
                        if (ga_a2.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "A");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "a");
                            }
                        }
                        if (ga_b.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "B");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "b");
                            }
                        }
                        if (ga_c.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "C");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "c");
                            }
                        }
                        if (ga_d2.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "D");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "d");
                            }
                        }
                        if (ga_e.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "E");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "e");
                            }
                        }
                        if (ga_f.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "F");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "f");
                            }
                        }
                        if (ga_g.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "G");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "g");
                            }
                        }
                        if (ga_h.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "H");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "h");
                            }
                        }

                        if (ga_i.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "I");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "i");
                            }
                        }
                        if (ga_j.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "J");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "j");
                            }
                        }
                        if (ga_k.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "K");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "k");
                            }
                        }
                        if (ga_l.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "L");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "l");
                            }
                        }
                        if (ga_m.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "M");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "m");
                            }
                        }
                        if (ga_n.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "N");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "n");
                            }
                        }
                        if (ga_o.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "O");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "o");
                            }
                        }
                        if (ga_p.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "P");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "p");
                            }
                        }
                        if (ga_q.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "Q");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "q");
                            }
                        }
                        if (ga_r.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "R");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "r");
                            }
                        }
                        if (ga_s2.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "S");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "s");
                            }
                        }
                        if (ga_t.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "T");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "t");
                            }
                        }
                        if (ga_u.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "U");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "u");
                            }
                        }
                        if (ga_v.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "V");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "v");
                            }
                        }
                        if (ga_w2.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "W");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "w");
                            }
                        }
                        if (ga_x.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "X");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "x");
                            }
                        }
                        if (ga_y.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "Y");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "y");
                            }
                        }
                        if (ga_z.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "Z");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "z");
                            }
                        }
                        if (ga_score.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "_");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "-");
                            }
                        }
                        if (ga_komma.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + ";");
                            } else {
                                Main.chat.setText(Main.chat.getText() + ",");
                            }
                        }
                        if (ga_punkt.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + ":");
                            } else {
                                Main.chat.setText(Main.chat.getText() + ".");
                            }
                        }
                        if (ga_0.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "=");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "0");
                            }
                        }
                        if (ga_1.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "!");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "1");
                            }
                        }
                        if (ga_2.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "2");
                            }
                        }
                        if (ga_3.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "3");
                            }
                        }
                        if (ga_4.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "4");
                            }
                        }
                        if (ga_5.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "5");
                            }
                        }
                        if (ga_6.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "6");
                            }
                        }
                        if (ga_7.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "7");
                            }
                        }
                        if (ga_8.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + "(");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "8");
                            }
                        }
                        if (ga_9.isPressed()) {
                            if (ga_shift.isPressed()) {
                                Main.chat.setText(Main.chat.getText() + ")");
                            } else {
                                Main.chat.setText(Main.chat.getText() + "9");
                            }
                        }
                        if (ga_space2.isPressed()) {
                            Main.chat.setText(Main.chat.getText() + " ");
                        }
                    }
                }
            } else {

            }
        }
    }

    private void checkInputs() {
        if (!Keyboard.getEventKeyState()) {
            boolean collide = false;
            for (Integer eid : entities) {
                if (BaseEntity.hasCollisionComponent(eid) && BaseEntity.hasPositionComponent(eid)) {
                    if (cc.checkCollision(eid)) {
                        collide = true;
                    }
                }
            }
            for (Integer eid : normalMapEntities) {
                if (BaseEntity.hasCollisionComponent(eid) && BaseEntity.hasPositionComponent(eid)) {
                    if (cc.checkCollision(eid)) {
                        collide = true;
                    }
                }
            }

            if (ga_w.isPressed()) {
                if (ga_shift.isPressed() && !isInAir) {
                    if (collide) {
                        mc.update(0);
                    } else {
                        mc.update(2);
                    }
                } else {
                    if (collide) {
                        mc.update(0);
                    } else {
                        mc.update(1);
                    }
                }
            } else if (ga_s.isPressed()) {
                mc.update(3);
            } else {
                mc.update(0);
            }
            if (ga_d.isPressed()) {
                mc.update(5);
            } else if (ga_a.isPressed()) {
                mc.update(4);
            } else {
                mc.update(0);
            }
            if (ga_space.isPressed()) {
                mc.update(6);
            }
            if (ga_t.isPressed()) {
                Main.toggleChat();
            }
            if (ga_f1.isPressed()) {
                Main.screenshot.takeScreenshot();
            }
            if (ga_f2.isPressed()) {
                if (Main.debug) {
                    if (ga_1.isPressed()) {
                        Utils.setLightColor(0, new Color3f(random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f));
                    }
                    if (ga_2.isPressed()) {
                        Utils.setLightColor(1, new Color3f(random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f));
                    }
                    if (ga_3.isPressed()) {
                        Utils.setLightColor(2, new Color3f(random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f));
                    }
                    if (ga_4.isPressed()) {
                        Utils.setLightColor(3, new Color3f(random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f));
                    }
                    if (ga_5.isPressed()) {
                        Utils.setVignetteColor(new Color3f(random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f, random.nextFloat() * (1 - 0 * 1) + 0.5f));
                    }
                } else {
                }
            }
            if (ga_f3.isPressed()) {
                if (ga_1.isPressed()) {
                    if (Main.debug) {
                        showDebugInfo();
                    }
                }
                if (ga_2.isPressed()) {
                    if (Main.debug) {
                        try {
                            int id = Utils.getTerrain(PositionComponent.getPosXZ(this.id));
                            Utils.log("Terrain Name: " + TerrainEntity.getName(id) + " Terrain Coords: " + "X " + TerrainComponent.getPosition(id).x + " Z " + TerrainComponent.getPosition(id).y + " Player Coords: " + Main.stores.get(Main.player.getID()).get(0).getValue().toString(), 0);
                        } catch (NullPointerException e) {
                            Utils.log("You standing on no Terrain", 1);
                        }
                    }
                }
                if (ga_3.isPressed()) {

                }
                if (ga_4.isPressed()) {
                    if (Main.debug) {
                        HealthComponent.respawn(getId());
                    }
                }
                if (ga_5.isPressed()) {
                    if (Main.debug) {
                        Utils.log("Particle Mode Changed", 0);
                        Main.particle = !Main.particle;
                    }
                }
                if (ga_6.isPressed()) {

                }
                if (ga_7.isPressed()) {

                }
                if (ga_8.isPressed()) {

                }
                if (ga_9.isPressed()) {
                    if (Main.debug) {
                        Main.world.regenerateWorld();
                    }
                }
                if (ga_0.isPressed()) {
                    if (Main.debug) {
                        Utils.log("Polygon Mode Changed", 0);
                        if (!polymode) {
                            polymode = true;
                        } else {
                            polymode = false;
                        }
                    }

                }
            }

            if (ga_f4.isPressed()) {
                if (Main.debug) {
                    if (ga_1.isPressed()) {
                        PostProcessConfig.nextPostProcess(0);
                    }
                    if (ga_2.isPressed()) {
                        PostProcessConfig.nextPostProcess(1);
                    }
                    if (ga_3.isPressed()) {
                        PostProcessConfig.nextPostProcess(2);
                    }
                    if (ga_4.isPressed()) {
                        PostProcessConfig.nextPostProcess(3);
                    }
                    if (ga_5.isPressed()) {
                        PostProcessConfig.nextPostProcess(4);
                    }
                    if (ga_6.isPressed()) {
                        PostProcessConfig.nextPostProcess(5);
                    }
                }
            }
            if (ga_f5.isPressed()) {
                // FirstPerson ThirdPerson Switch
            }
            if (ga_f6.isPressed()) {
                // SaveGame
                Main.world.saveWorld();
            }
            if (ga_f7.isPressed()) {

            }
            if (ga_f8.isPressed()) {

            }
            if (ga_f9.isPressed()) {

            }
            if (ga_f10.isPressed()) {

            }
            if (ga_f11.isPressed()) {
                Main.setFullScreen();
            }
            if (ga_f12.isPressed()) {

            }
            if (ga_esc.isPressed()) {
                // Menu
                Main.toggleMenu();
                Main.toggleHUD();
                control = false;
                PostProcessing.update(Main.PostProcessing_Menu());
            }
            if (ga_punkt.isPressed()) {
                if (!mp.isLoading) {
                    mp.play();
                }

            }
            if (ga_komma.isPressed()) {
                mp.stop();
            }

        }
    }
}
