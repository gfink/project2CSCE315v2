package eighteen;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class AttackWithdrawGUI extends JFrame{
	public static AttackWithdrawGUI AWGUI;
	AttackWithdrawGUI()
	{
		setTitle("CHOOSE ATTACK OR WITHDRAW");
		setSize(200,200);
		setLocationRelativeTo(null);
		JButton attack = new JButton("Attack");
		JButton withdraw = new JButton("Withdraw");
		attack.setBounds(50,60,80,30);
		withdraw.setBounds(50,60,80,30);
		attack.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent event) {
        	   FanoronaGUI.userPickState = AttackState.ADVANCING;
        	   FanoronaGUI.GUI.DoAttackWithdraw();
        	   AWGUI.setVisible(false);
           }
		});
		withdraw.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent event) {
        	   FanoronaGUI.userPickState = AttackState.WITHDRAWING;
        	   FanoronaGUI.GUI.DoAttackWithdraw();
        	   AWGUI.setVisible(false);
           }
		});
		JPanel buttonPanel = new JPanel(new GridLayout(1,0));
		buttonPanel.add(attack);
		buttonPanel.add(withdraw);
		add(buttonPanel);
		pack();
	}
}
