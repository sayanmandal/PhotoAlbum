package PhotoAlbum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Annotation extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Annotation dialog = new Annotation(null,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Annotation(BufferedImage image,String text) {
		setTitle("Enter the Description");
		setBounds(100, 100, 445, 144);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			textField = new JTextField();
			textField.setToolTipText("Enter Your Annotation here");
			contentPanel.add(textField);
			textField.setColumns(30);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textField.getText().trim().length()!=0){
							try {
								MainWindow.read_from_file();
							} catch (ClassNotFoundException | IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							String annotation = null;
							for(int i = 0 ; i < MainWindow.list2.length ; i++){
								if(MainWindow.imagemap.get(MainWindow.list2[i]).equals(image)){
									annotation = (String) MainWindow.list2[i];
								}
							}
							MainWindow.annotationMap.put(annotation,textField.getText());
							for(int i = 0 ; i < MainWindow.ImageList.size(); i++){
								ImageRe img = MainWindow.ImageList.get(i);
								try {
									if(img.getTitle().equals(text)){
										img.setAnnotation(textField.getText());
										MainWindow.ImageList.set(i, img);
										MainWindow.write_to_image_file();
									}
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							try {
								MainWindow.write_to_file();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							dispose();
							JOptionPane.showMessageDialog(null, "Annotation Added!!Please Open The Album Again To see");
						}else{
							JOptionPane.showMessageDialog(null, "Please give input", "Error", JOptionPane.ERROR);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
