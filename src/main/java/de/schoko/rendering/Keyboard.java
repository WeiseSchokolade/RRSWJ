package de.schoko.rendering;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Keyboard {
	private static int MAX_SUPPORTED_KEYS = 65536;
	
	private boolean pressedKeys[] = new boolean[MAX_SUPPORTED_KEYS];
	/**
	 * A key gets set to true when it is pressed.
	 * Every time {@link Keyboard#update()} get's called every value is set to false.
	 */
	private boolean recentlyPressedKeys[] = new boolean[MAX_SUPPORTED_KEYS];
	
	private List<Character> recentlyTypedCharacters;

	public static final int ZERO = KeyEvent.VK_0,
			ONE = KeyEvent.VK_1,
			TWO = KeyEvent.VK_2,
			THREE = KeyEvent.VK_3,
			FOUR = KeyEvent.VK_4,
			FIVE = KeyEvent.VK_5,
			SIX = KeyEvent.VK_6,
			SEVEN = KeyEvent.VK_7,
			EIGHT = KeyEvent.VK_8,
			NINE = KeyEvent.VK_9,
			A = KeyEvent.VK_A,
			B = KeyEvent.VK_B,
			C = KeyEvent.VK_C,
			D = KeyEvent.VK_D,
			E = KeyEvent.VK_E,
			F = KeyEvent.VK_F,
			G = KeyEvent.VK_G,
			H = KeyEvent.VK_H,
			I = KeyEvent.VK_I,
			J = KeyEvent.VK_J,
			K = KeyEvent.VK_K,
			L = KeyEvent.VK_L,
			M = KeyEvent.VK_M,
			N = KeyEvent.VK_N,
			O = KeyEvent.VK_O,
			P = KeyEvent.VK_P,
			Q = KeyEvent.VK_Q,
			R = KeyEvent.VK_R,
			S = KeyEvent.VK_S,
			T = KeyEvent.VK_T,
			U = KeyEvent.VK_U,
			V = KeyEvent.VK_V,
			W = KeyEvent.VK_W,
			X = KeyEvent.VK_X,
			Y = KeyEvent.VK_Y,
			Z = KeyEvent.VK_Z,
			SPACE = KeyEvent.VK_SPACE,
			ENTER = KeyEvent.VK_ENTER,
			WINDOWS = KeyEvent.VK_WINDOWS,
			ESCAPE = KeyEvent.VK_ESCAPE,
			CONTROL = KeyEvent.VK_CONTROL,
			TAB = KeyEvent.VK_TAB,
			ADD = KeyEvent.VK_ADD,
			PLUS = KeyEvent.VK_PLUS,
			MINUS = KeyEvent.VK_MINUS,
			SUBTRACT = KeyEvent.VK_SUBTRACT,
			SLASH = KeyEvent.VK_SLASH,
			ALT = KeyEvent.VK_ALT,
			ALT_GRAPH = KeyEvent.VK_ALT_GRAPH,
			UNDO = KeyEvent.VK_UNDO,
			UP = KeyEvent.VK_UP,
			LEFT = KeyEvent.VK_LEFT,
			DOWN = KeyEvent.VK_DOWN,
			RIGHT = KeyEvent.VK_RIGHT,
			BACK_SPACE = KeyEvent.VK_BACK_SPACE,
			SHIFT = KeyEvent.VK_SHIFT,
			CAPS_LOCK = KeyEvent.VK_CAPS_LOCK,
			UNDERSCORE = KeyEvent.VK_UNDERSCORE,
			ASTERISK = KeyEvent.VK_ASTERISK,
			SEMICOLON = KeyEvent.VK_SEMICOLON,
			DOT = KeyEvent.VK_PERIOD,
			PERIOD = KeyEvent.VK_PERIOD,
			F1 = KeyEvent.VK_F1,
			F2 = KeyEvent.VK_F2,
			F3 = KeyEvent.VK_F3,
			F4 = KeyEvent.VK_F4,
			F5 = KeyEvent.VK_F5,
			F6 = KeyEvent.VK_F6,
			F7 = KeyEvent.VK_F7,
			F8 = KeyEvent.VK_F8,
			F9 = KeyEvent.VK_F9,
			F10 = KeyEvent.VK_F10,
			F11 = KeyEvent.VK_F11,
			F12 = KeyEvent.VK_F12,
			F13 = KeyEvent.VK_F13,
			F14 = KeyEvent.VK_F14,
			F15 = KeyEvent.VK_F15,
			F16 = KeyEvent.VK_F16,
			F17 = KeyEvent.VK_F17,
			F18 = KeyEvent.VK_F18,
			F19 = KeyEvent.VK_F19,
			F20 = KeyEvent.VK_F20,
			F21 = KeyEvent.VK_F21,
			F22 = KeyEvent.VK_F22,
			F23 = KeyEvent.VK_F23,
			F24 = KeyEvent.VK_F24;

	public Keyboard() {
		recentlyTypedCharacters = new ArrayList<>();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
			if (event.getID() == KeyEvent.KEY_PRESSED) {
				pressedKeys[event.getKeyCode()] = true;
				recentlyPressedKeys[event.getKeyCode()] = true;
			} else if (event.getID() == KeyEvent.KEY_RELEASED) {
				pressedKeys[event.getKeyCode()] = false;
			} else if (event.getID() == KeyEvent.KEY_TYPED) {
				if (event.getKeyChar() != BACK_SPACE && event.getKeyChar() != ESCAPE && !event.isActionKey()) {
					recentlyTypedCharacters.add(event.getKeyChar());
				}
			}
			return false;
		});
	}
	
	/**
	 * Marks all recently pressed keys as not pressed
	 */
	public void update() {
		for (int i = 0; i < recentlyPressedKeys.length; i++) {
			recentlyPressedKeys[i] = false;
		}
	}

	/**
	 * Checks whether the given key is being pressed
	 * @param keyCode The Key's Code
	 * @return Whether the key is being pressed
	 */
	public boolean isPressed(int keyCode) {
		return pressedKeys[keyCode];
	}
	
	/**
	 * Checks whether one of the given keys is pressed
	 * @return
	 */
	public boolean isPressed(int keyCode, int... keyCodes) {
		if (pressedKeys[keyCode]) return true;
		for (int i = 0; i < keyCodes.length; i++) {
			if (pressedKeys[keyCodes[i]]) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks whether the given key was pressed since the last frame.
	 * @param keyCode The key to be checked
	 * @return Whether the key was recently pressed
	 */
	public boolean wasRecentlyPressed(int keyCode) {
		return recentlyPressedKeys[keyCode];
	}
	
	/**
	 * Returns a list of recently typed characters; used for text input. Remember to clear the list when the input element loses focus.
	 * @return List<Character>
	 */
	public List<Character> getRecentlyTypedCharacters() {
		return recentlyTypedCharacters;
	}
}
