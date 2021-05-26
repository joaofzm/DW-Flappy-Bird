/*
 * No rights reserved.
 */

package br.com.joaofzm15highlyunlikely.flappy;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Simple game inspired by the famous "Flappy Bird".
 *
 * @author JoaoFZM 
 * Date: 25/05/2021
 */

public class Gui extends JFrame implements ActionListener, KeyListener {

	ImageIcon defaultBirdIcon = new ImageIcon(Gui.class.getResource("bird2.png"));
	ImageIcon deadBirdIcon = new ImageIcon(Gui.class.getResource("deadBird.png"));
	ImageIcon pipeIcon = new ImageIcon(Gui.class.getResource("pipe.png"));
	ImageIcon windowIcon = new ImageIcon(Gui.class.getResource("birdIcon.jpg"));
	
	/*
	 * 'firstRun' is used to allow the player to start the game pressing 'W' instead
	 * of 'R' on the first attempt after the game is opened. After that the player
	 * has to restart pressing 'R'.
	 */
	boolean firstRun = true;
	boolean gameRunning = false;

	/*
	 * The title, score and warnings are made by turning the following labels
	 * visible, and then invisible when they are no longer necessary.
	 */
	JLabel titleLabel;
	JLabel pressToStartJLabel;
	JLabel gameOverLabel;
	JLabel pressToRestartLabel;
	int score;
	int highScore;
	JLabel scoreLabel;
	JLabel highScoreLabel;

	JLabel birdLabel;
	int birdX = 50;
	int birdY = 320;

	/*
	 * To create the illusion of infinity pipes, there are 8 pipes, divided in four
	 * pairs (1 and 2, 3 and 4, 5 and 6, 7 and 8). When they leave the screen (their
	 * x value reach a negative number) they're reset to 1000, and will show up
	 * again.
	 * 
	 * There's only one pipe image, for the bottom ones the top part of the image is
	 * showing, while for the top ones the bottom part is showing. The even pipes
	 * have negative Y coordinates, they start off-screen and only the bottom show
	 * on screen.
	 */
	JLabel pipe1Label;
	JLabel pipe2Label;
	JLabel pipe3Label;
	JLabel pipe4Label;
	JLabel pipe5Label;
	JLabel pipe6Label;
	JLabel pipe7Label;
	JLabel pipe8Label;

	int pipe1x = 1000;
	int pipe1y = 470;
	int pipe2x = 1000;
	int pipe2y = -600;
	int pipe3x = 1500;
	int pipe3y = 620;
	int pipe4x = 1500;
	int pipe4y = -500;
	int pipe5x = 2000;
	int pipe5y = 470;
	int pipe6x = 2000;
	int pipe6y = -600;
	int pipe7x = 2500;
	int pipe7y = 600;
	int pipe8x = 2500;
	int pipe8y = -500;


	/*
	 * As a way to delay the start of the second, third and fourth set of pipes,
	 * their movement timer will only start after the previous one has reached a set
	 * amount of repetitions, that amount is stored in this variables.
	 */
	int totalBars1and2MovementLoopsAmount;
	int totalBars3and4MovementLoopsAmount;
	int totalBars5and6MovementLoopsAmount;


	// Makes the bird automatically.
	Timer gravityTimer = new Timer(10, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (birdY < 655) {
				birdY += 4;
				birdLabel.setBounds(birdX, birdY, 80, 80);
			}
		}
	});


	// Makes the bird upward movement more smooth visually.
	Timer moveUpTimer = new Timer(10, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 10; i++) {
				if (birdY > 0) {
					birdY -= 8;
					birdLabel.setBounds(birdX, birdY, 80, 80);
				}
			}
			moveUpTimer.stop();
		}
	});


	/*
	 * The following 4 timers are responsible for making their corresponding pair of
	 * pipes move, as well as starting the next timer in line after a set of
	 * repetitions. For more details check 'totalBars1and2MovementLoopsAmount'
	 * commentary above.
	 */
	Timer pipes1and2Timer = new Timer(25, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			pipe1x -= 5;
			pipe2x -= 5;
			pipe1Label.setBounds(pipe1x, pipe1y, 80, 800);
			pipe2Label.setBounds(pipe2x, pipe2y, 80, 800);

			totalBars1and2MovementLoopsAmount++;
			if (totalBars1and2MovementLoopsAmount > 200) {
				pipes3and4Timer.start();
			}
		}
	});
	Timer pipes3and4Timer = new Timer(25, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			pipe3x -= 5;
			pipe4x -= 5;
			pipe3Label.setBounds(pipe3x, pipe3y, 80, 800);
			pipe4Label.setBounds(pipe4x, pipe4y, 80, 800);

			totalBars3and4MovementLoopsAmount++;
			if (totalBars3and4MovementLoopsAmount > 200) {
				pipes5and6Timer.start();
			}
		}
	});
	Timer pipes5and6Timer = new Timer(25, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			pipe5x -= 5;
			pipe6x -= 5;
			pipe5Label.setBounds(pipe5x, pipe5y, 80, 800);
			pipe6Label.setBounds(pipe6x, pipe6y, 80, 800);

			totalBars5and6MovementLoopsAmount++;
			if (totalBars5and6MovementLoopsAmount > 200) {
				pipes7and8Timer.start();
			}

		}
	});
	Timer pipes7and8Timer = new Timer(25, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			pipe7x -= 5;
			pipe8x -= 5;
			pipe7Label.setBounds(pipe7x, pipe7y, 80, 800);
			pipe8Label.setBounds(pipe8x, pipe8y, 80, 800);
		}
	});



	/*
	 * The following four are responsible for reseting the pipes. If the pipe is out
	 * of bounds (negative x coordinates), the pipe is moved to coordinate 1000, and
	 * will enter the screen again.
	 */
	Timer resetBars1and2Timer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (pipe1x < -100 && pipe2x < -100) {
				pipe1x = 1000;
				pipe2x = 1000;
				pipe1Label.setBounds(pipe1x, pipe1y, 80, 800);
				pipe2Label.setBounds(pipe2x, pipe2y, 80, 800);
			}
		}
	});
	Timer resetBars3and4Timer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (pipe3x < -200 && pipe4x < -200) {
				pipe3x = 1500;
				pipe4x = 1500;
				pipe3Label.setBounds(pipe3x, pipe3y, 80, 800);
				pipe4Label.setBounds(pipe4x, pipe4y, 80, 800);
			}
		}
	});
	Timer resetBars5and6Timer = new Timer(800, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (pipe5x < -300 && pipe6x < -300) {
				pipe5x = 2000;
				pipe6x = 2000;
				pipe5Label.setBounds(pipe5x, pipe5y, 80, 800);
				pipe6Label.setBounds(pipe6x, pipe6y, 80, 800);
			}
		}
	});
	Timer resetBars7and8Timer = new Timer(850, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (pipe7x < -400 && pipe8x < -400) {
				pipe7x = 2500;
				pipe8x = 2500;
				pipe7Label.setBounds(pipe7x, pipe7y, 80, 800);
				pipe8Label.setBounds(pipe8x, pipe8y, 80, 800);
			}
		}
	});


	/*
	 * While the pipes are in x coordinates between 51 and 131 (the area occupied by
	 * the bird), the timer will check if the bird Y axis equals to the 'pipeY', and
	 * if it does the game will register the collision.
	 * 
	 * Odd pipes Y axis are subtracted 80 to make up for the bird size, since it
	 * occupies 80 pixels counting from it's upper limit, so the collision would
	 * only register if it's upper limit hit the pipe. We subtract 80 to compensate
	 * for that.
	 * 
	 * Even pipes are added 800 because all the even pipes actually have negative Y
	 * coordinates. By adding 800 we get their ending coordinate we get what was
	 * supposed to be their starting position if they were actually draw upside
	 * down, and consider anything above (smaller Y axis) that a collision.
	 */
	Timer checkCollision = new Timer(10, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (pipe1x >= 50 && pipe1x <= 130 && birdY >= (pipe1y - 80)) {
				gameOver();
			}
			if (pipe2x >= 50 && pipe2x <= 130 && birdY <= (pipe2y + 800)) {
				gameOver();
			}
			if (pipe3x >= 50 && pipe3x <= 130 && birdY >= (pipe3y - 80)) {
				gameOver();
			}
			if (pipe4x >= 50 && pipe4x <= 130 && birdY <= pipe4y + 800) {
				gameOver();
			}
			if (pipe5x >= 50 && pipe5x <= 130 && birdY >= (pipe5y - 80)) {
				gameOver();
			}
			if (pipe6x >= 50 && pipe6x <= 130 && birdY <= (pipe6y + 800)) {
				gameOver();
			}
			if (pipe7x >= 50 && pipe7x <= 130 && birdY >= (pipe7y - 80)) {
				gameOver();
			}
			if (pipe8x >= 50 && pipe8x <= 130 && birdY <= pipe8y + 800) {
				gameOver();
			}
			if (birdY >= 655) {
				gameOver();
			}

		}
	});

	/*
	 * Whenever pipes 1,3,5 and 7 (only one pipe out of each pair) hit the X
	 * coordinate of 50, score will be added one, and the labels that show the score
	 * and the high score will be updated.
	 * (the x coordinate off the bird label, at that point they're sharing the same
	 * x axis, which means the bird went through the pipe).
	 */
	Timer scoreTimer = new Timer(25, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (pipe1x == 50) {
				score++;
			}
			if (pipe3x == 50) {
				score++;
			}
			if (pipe5x == 50) {
				score++;
			}
			if (pipe7x == 50) {
				score++;
			}
			if (score > highScore) {
				highScore = score;
				highScoreLabel.setText("High Score: " + highScore);
			}
			scoreLabel.setText("Score: " + score);
		}
	});

	


	


	public Gui() {

		/*
		 * The constructor instantiate the JFrame and all the JLabels, defines all the
		 * properties and add them to the JFrame.
		 */
		this.setContentPane(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("bg.jpg"))));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("DW Flappy Bird");
		this.addKeyListener(this);
		this.setIconImage(windowIcon.getImage());
		this.setResizable(false);
		this.setSize(800, 800);
		this.setMinimumSize(new Dimension(800, 800));

		titleLabel = new JLabel();
		titleLabel.setBounds(130, 70, 800, 100);
		titleLabel.setText("DW Flappy Bird");
		titleLabel.setFont(new Font("Impact", Font.PLAIN, 90));
		titleLabel.setFocusable(false);
		titleLabel.setVisible(true);
		this.add(titleLabel);

		pressToStartJLabel = new JLabel();
		pressToStartJLabel.setBounds(280, 200, 500, 80);
		pressToStartJLabel.setText("Press 'W' to start!");
		pressToStartJLabel.setFont(new Font("Impact", Font.PLAIN, 30));
		pressToStartJLabel.setFocusable(false);
		pressToStartJLabel.setVisible(true);
		this.add(pressToStartJLabel);
		
		scoreLabel = new JLabel();
		scoreLabel.setBounds(350, 100, 500, 80);
		scoreLabel.setText("Score: " + score);
		scoreLabel.setFont(new Font("Impact", Font.PLAIN, 30));
		scoreLabel.setFocusable(false);
		scoreLabel.setVisible(false);
		this.add(scoreLabel);

		highScoreLabel = new JLabel();
		highScoreLabel.setBounds(345, 140, 500, 80);
		highScoreLabel.setText("High Score: " + highScore);
		highScoreLabel.setFont(new Font("Impact", Font.PLAIN, 20));
		highScoreLabel.setFocusable(false);
		highScoreLabel.setVisible(false);
		this.add(highScoreLabel);

		gameOverLabel = new JLabel();
		gameOverLabel.setBounds(200, 70, 800, 100);
		gameOverLabel.setText("GAME OVER");
		gameOverLabel.setFont(new Font("Impact", Font.PLAIN, 90));
		gameOverLabel.setFocusable(false);
		gameOverLabel.setVisible(false);
		this.add(gameOverLabel);

		pressToRestartLabel = new JLabel();
		pressToRestartLabel.setBounds(280, 200, 500, 80);
		pressToRestartLabel.setText("Press 'R' to restart!");
		pressToRestartLabel.setFont(new Font("Impact", Font.PLAIN, 30));
		pressToRestartLabel.setFocusable(false);
		pressToRestartLabel.setVisible(false);
		this.add(pressToRestartLabel);

		birdLabel = new JLabel();
		birdLabel.setIcon(defaultBirdIcon);
		birdLabel.setBounds(birdX, birdY, 80, 80);
		birdLabel.setFocusable(false);
		birdLabel.setVisible(true);
		this.add(birdLabel);

		pipe1Label = new JLabel();
		pipe1Label.setIcon(pipeIcon);
		pipe1Label.setBounds(pipe1x, pipe1y, 80, 800);
		pipe1Label.setFocusable(false);
		pipe1Label.setVisible(true);
		this.add(pipe1Label);

		pipe2Label = new JLabel();
		pipe2Label.setIcon(pipeIcon);
		pipe2Label.setBounds(pipe2x, pipe2y, 80, 800);
		pipe2Label.setFocusable(false);
		pipe2Label.setVisible(true);
		this.add(pipe2Label);

		pipe3Label = new JLabel();
		pipe3Label.setIcon(pipeIcon);
		pipe3Label.setBounds(pipe3x, pipe3y, 80, 800);
		pipe3Label.setFocusable(false);
		pipe3Label.setVisible(true);
		this.add(pipe3Label);

		pipe4Label = new JLabel();
		pipe4Label.setIcon(pipeIcon);
		pipe4Label.setBounds(pipe4x, pipe4y, 80, 800);
		pipe4Label.setFocusable(false);
		pipe4Label.setVisible(true);
		this.add(pipe4Label);

		pipe5Label = new JLabel();
		pipe5Label.setIcon(pipeIcon);
		pipe5Label.setBounds(pipe4x, pipe4y, 80, 800);
		pipe5Label.setFocusable(false);
		pipe5Label.setVisible(true);
		this.add(pipe5Label);

		pipe6Label = new JLabel();
		pipe6Label.setIcon(pipeIcon);
		pipe6Label.setBounds(pipe4x, pipe4y, 80, 800);
		pipe6Label.setFocusable(false);
		pipe6Label.setVisible(true);
		this.add(pipe6Label);

		pipe7Label = new JLabel();
		pipe7Label.setIcon(pipeIcon);
		pipe7Label.setBounds(pipe4x, pipe4y, 80, 800);
		pipe7Label.setFocusable(false);
		pipe7Label.setVisible(true);
		this.add(pipe7Label);

		pipe8Label = new JLabel();
		pipe8Label.setIcon(pipeIcon);
		pipe8Label.setBounds(pipe4x, pipe4y, 80, 800);
		pipe8Label.setFocusable(false);
		pipe8Label.setVisible(true);
		this.add(pipe8Label);

		this.pack();
		this.setVisible(true);

	}

	/*
	 * startGame resets the score, pipe positions and re-arrange the visibility of
	 * the Labels as necessary. Also moves the score label and the high score label
	 * for better visibility.
	 * 
	 * Triggered by a 'w' (if first attempt since opening the game) and 'r' (after
	 * first attempt).
	 */
	public void startGame() {
		score = 0;
		scoreLabel.setText("Score: " + score);
		scoreLabel.setBounds(350, 100, 500, 80);
		highScoreLabel.setBounds(350, 140, 500, 80);
		birdLabel.setIcon(defaultBirdIcon);
		gameOverLabel.setVisible(false);
		pressToRestartLabel.setVisible(false);
		gameRunning = true;
		titleLabel.setVisible(false);
		scoreLabel.setVisible(true);
		highScoreLabel.setVisible(true);
		pressToStartJLabel.setVisible(false);
		gravityTimer.start();
		resetBars1and2Timer.start();
		resetBars3and4Timer.start();
		resetBars5and6Timer.start();
		resetBars7and8Timer.start();
		checkCollision.start();
		scoreTimer.start();
		pipes1and2Timer.start();
		birdY = 320;

		pipe1x = 1000;
		pipe1y = 470;
		pipe2x = 1000;
		pipe2y = -600;
		pipe3x = 1500;
		pipe3y = 620;
		pipe4x = 1500;
		pipe4y = -500;
		pipe5x = 2000;
		pipe5y = 470;
		pipe6x = 2000;
		pipe6y = -600;
		pipe7x = 2500;
		pipe7y = 600;
		pipe8x = 2500;
		pipe8y = -500;
	}

	/*
	 * Triggered by the collision (collisionTimer). Stops all timers and set labels
	 * visibility according. Revert 'startGame' changes to score label and high
	 * score label.
	 */
	public void gameOver() {
		birdLabel.setIcon(deadBirdIcon);
		gameOverLabel.setVisible(true);
		pressToRestartLabel.setVisible(true);
		gameRunning = false;
		pipes1and2Timer.stop();
		pipes3and4Timer.stop();
		pipes5and6Timer.stop();
		pipes7and8Timer.stop();
		resetBars1and2Timer.stop();
		resetBars3and4Timer.stop();
		resetBars5and6Timer.stop();
		resetBars7and8Timer.stop();
		checkCollision.stop();
		scoreLabel.setBounds(350, 300, 500, 80);
		highScoreLabel.setBounds(350, 340, 500, 80);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	/*
	 * W makes both the game start (only for the first attempt since opening the
	 * game), and will act as the main button (jump).
	 * 
	 * R restarts the game at any time(retry button).
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
		case 'w':
			if (gameRunning) {
				birdY -= 20;
				birdLabel.setBounds(birdX, birdY, 80, 80);
				moveUpTimer.start();
				break;
			} else if (firstRun) {
				firstRun = false;
				startGame();
				break;
			} else {
				break;
			}
		case 'r':
			if (firstRun == false) {
				pipe1Label.setBounds(-1000, pipe1y, 80, 800);
				pipe2Label.setBounds(-1000, pipe2y, 80, 800);
				pipe3Label.setBounds(-1000, pipe3y, 80, 800);
				pipe4Label.setBounds(-1000, pipe4y, 80, 800);
				pipe5Label.setBounds(-1000, pipe5y, 80, 800);
				pipe6Label.setBounds(-1000, pipe6y, 80, 800);
				pipe7Label.setBounds(-1000, pipe7y, 80, 800);
				pipe8Label.setBounds(-1000, pipe8y, 80, 800);
				startGame();
				break;
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
