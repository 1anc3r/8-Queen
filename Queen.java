import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.JLabel;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

public class Queen extends JPanel{
	static final int SIZE = 8;
	static final int GRIDSIZE = 80;
	private JLabel l[][];
	int s = 0;
	int n = 0;
	int[] p = new int[SIZE];
	int[][] b = new int[SIZE][SIZE];
	int[][][] all = new int[1024][SIZE][SIZE];

	public void Path(int now){
		int row = now;
		for (int col = 0; col < SIZE; col++){
			if (b[row][col] == 0){
				for (int tmp = row + 1; tmp < SIZE; tmp++){
					b[tmp][col]++;
					if (col - tmp + row >= 0){
						b[tmp][col - tmp + row]++;
					}
					if (col + tmp - row < SIZE){
						b[tmp][col + tmp - row]++;
					}
				}

				p[row] = col;
				if (row == SIZE - 1){
					Save(s++);
				} else {
					Path(row + 1);
				}

				for (int tmp = row + 1; tmp < SIZE; tmp++){
					b[tmp][col]--;
					if (col - tmp + row >= 0){
						b[tmp][col - tmp + row]--;
					}
					if (col + tmp - row < SIZE){
						b[tmp][col + tmp - row]--;
					}
				}
			}
		}
		if (row == 0){
			return ;
		}
	}

	public void Save(int s){
		for (int row = 0; row < SIZE; row++){
			for (int col = 0; col < SIZE; col++){
				if (p[row] == col) {
					all[s][row][col] = 1;
				} else {
					all[s][row][col] = 0;
				}
			}
		}
	}

	public void Load(int s){
		File pic = new File("queen.png");
		ImageIcon icon = new ImageIcon(pic.toString());
		icon.setImage(icon.getImage().getScaledInstance(80,80,Image.SCALE_DEFAULT));
		for(int row = 0; row < SIZE; row++){
			for (int col = 0; col < SIZE; col++) {
				if (all[s][row][col] == 1) {
					l[row][col].setIcon(icon);
				}else{
					l[row][col].setIcon(null);
				}
			}
		}
	}

	public void initGUI(int grids,int gridsize){
		l = new JLabel[grids][grids];
		for(int i = 0; i < grids; i++){
			for(int j = 0; j < grids; j++){
				l[i][j] = new JLabel();
				l[i][j].setSize(gridsize,gridsize);
				l[i][j].setLocation(i*gridsize,j*gridsize);
				l[i][j].setHorizontalTextPosition(JLabel.CENTER);
				l[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				l[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				if((i+j)%2 == 0){
					l[i][j].setBackground(Color.black);
					l[i][j].setOpaque(true);
				}
				add(l[i][j]);
			}
		}
	}

	public Queen(){
		super(null);
		initGUI(SIZE,GRIDSIZE);
	}

	public static void main(String[] args){
		JFrame f = new JFrame();
		Queen q = new Queen();
		q.Path(0);
		q.Load(q.n);
		// Scanner in = new Scanner(System.in);
		int key;
		f.setSize(658,677);
		f.setLocationRelativeTo(null);
		f.add(q);
		f.setVisible(true);
		f.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				switch(e.getKeyCode()){
					case KeyEvent.VK_DOWN:
					break;
					case KeyEvent.VK_UP:
					break;
					case KeyEvent.VK_LEFT:
						if (--q.n == -1) {
							q.n = q.s-1;
						}
						q.Load((q.n)%q.s);
					break;
					case KeyEvent.VK_RIGHT:
						q.Load((++q.n)%q.s);
					break;
					default:break;
				}
			}
		});
		// System.out.println(SIZE+" Queen have "+q.s+" solutions:");
		// do{
		// 	key = in.nextInt();
		// 	q.Load(key);
		// }while(key > 0 && key <= q.s);
	}
}
