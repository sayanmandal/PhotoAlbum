package PhotoAlbum;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddPhotoRe extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	File file;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AddPhotoRe dialog = new AddPhotoRe();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AddPhotoRe() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\sayan\\workspace\\PhotoAlbum\\Res\\Album.jpg"));
		setTitle("Add Your Photos");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblTitle = new JLabel("  Title :");
			lblTitle.setBounds(27, 18, 46, 14);
			contentPanel.add(lblTitle);
		}
		
		textField = new JTextField();
		textField.setBounds(68, 8, 272, 34);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblImage = new JLabel("Image:");
		lblImage.setBounds(27, 72, 46, 14);
		contentPanel.add(lblImage);
		
		JButton btnChooseFile = new JButton("Browse...");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser filechooser = new JFileChooser();
				javax.swing.filechooser.FileFilter filter = new FileNameExtensionFilter("JPEG Files","jpg","jpeg");
				filechooser.addChoosableFileFilter(filter);
				
				int ret = filechooser.showDialog(null, "Open File");
				
				if(ret == filechooser.APPROVE_OPTION){
					file = filechooser.getSelectedFile();
					try {
						BufferedImage img = ImageIO.read(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					btnChooseFile .setText(file.getName());
					
				}else{
					JOptionPane.showMessageDialog(null, "Please Choose a file!!");
				}
			}
		});
		btnChooseFile.setBounds(69, 68, 113, 23);
		contentPanel.add(btnChooseFile);
		
		JLabel lblAnnotation = new JLabel("Annotation:");
		lblAnnotation.setBounds(10, 130, 85, 23);
		contentPanel.add(lblAnnotation);
		
		textField_1 = new JTextField();
		textField_1.setBounds(76, 118, 348, 67);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
	
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ImageRe image = new ImageRe(file,textField.getText(),textField_1.getText());
						try {
							MainWindow.read_from_image_file();
						} catch (ClassNotFoundException | IOException e) {
							e.printStackTrace();
						}
						MainWindow.ImageList.add(image);
						try {
							MainWindow.write_to_image_file();
						} catch (IOException e) {
							e.printStackTrace();
						}
						dispose();
						JOptionPane.showMessageDialog(null, "Photo Added, please open the album again to see the photos");
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
