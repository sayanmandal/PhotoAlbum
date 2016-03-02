package PhotoAlbum;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainWindow extends JComponent implements MouseListener{

	private JPanel contentPane;
	private int index = 0;
	private JLabel label;
	static JList thelist;
	static Object[] list2;
	static List<String>list1 = new ArrayList<String>();
	
	static  List<String> DescList = new ArrayList<String>();
	static BufferedImage image,goku,dbz,sayan;
	static Map<String,BufferedImage> imagemap = new LinkedHashMap<String,BufferedImage>();
	static Map<String,String> annotationMap = new LinkedHashMap<String,String>();
	static Map<BufferedImage,String> imagemap1 = new LinkedHashMap<BufferedImage,String>();
	static List<ImageRe> ImageList = new ArrayList<ImageRe>();
	
	BufferedImage album;
	private static JFrame frame;
	private static JPanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BufferedImage icon = ImageIO.read(getClass().getResource("/Icon.jpg"));
					frame = new JFrame();
					read_from_file();
					read_from_image_file();
					MainWindow frame1 = new MainWindow();
					frame.getContentPane().add(frame1);
					frame.pack();
					frame.setVisible(true);
					frame.setIconImage(icon);
					frame.setLocationRelativeTo(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	static void write_to_image_file() throws IOException{
		FileOutputStream fos = new FileOutputStream("Image.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(ImageList);
		oos.close();
		fos.close();
	}

	static void read_from_image_file() throws ClassNotFoundException, IOException{
		RandomAccessFile file = new RandomAccessFile("Image.txt","rw");
		FileInputStream fis = new FileInputStream("Image.txt");
		if(file.length()!=0){
			for(int i = 0 ; i < ImageList.size() ; i++){
				ImageList.remove(i);
			}
			ObjectInputStream ois = new ObjectInputStream(fis);
			ImageList = (ArrayList<ImageRe>)ois.readObject();
			ois.close();
		}
		fis.close();
	}
	
	
	static void write_to_file() throws IOException{
		FileOutputStream fos = new FileOutputStream("Annotation.txt");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(annotationMap);
		oos.close();
		fos.close();
	}
	
	static void read_from_file() throws IOException, ClassNotFoundException{
		RandomAccessFile file = new RandomAccessFile("Annotation.txt","rw");
		FileInputStream fis = new FileInputStream("Annotation.txt");
		if(file.length()!=0){
			ObjectInputStream ois = new ObjectInputStream(fis);
			annotationMap = (LinkedHashMap<String,String>)ois.readObject();
			ois.close();
		}
		fis.close();
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainWindow() throws IOException {
		Iterator<ImageRe> iterator = ImageList.iterator();
		while(iterator.hasNext()){
			 ImageRe img = (ImageRe)iterator.next();
			 list1.add(img.getTitle());
			 imagemap.put(img.getTitle(), ImageIO.read(img.getFile()));
			 imagemap1.put(ImageIO.read(img.getFile()), img.getTitle());
			 annotationMap.put(img.getTitle(), img.getAnnotation());
		}
		frame.setFont(new Font("verdana",Font.BOLD,16));
		frame.setTitle("Digital Photo Album!!");
		frame.setIconImage(ImageIO.read(getClass().getResource("/Icon.jpg")));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 255), 10, true));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("To Add Annotation Please Click On The Picture");
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		addMouseListener(this);
		
		JButton btnNewButton = new JButton("Previous");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				index--;
				image = imagemap.get(list1.get(index));
				thelist.setSelectedIndex(index);
				label.setText(list1.get(index));
				repaint();
				}catch(Exception e1){
					
					index++;
					thelist.setSelectedIndex(index);
					label.setText(list1.get(index));
					JOptionPane.showMessageDialog(null, "No more photos available!!");
				}
			}
		});
		
		addMouseListener(this);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					if(ImageList.size()<=9){
					AddPhotoRe dialog = new AddPhotoRe();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(null, "Sorry!!The Album Cannot Load More Than 10 Photos ..You Have to delete some photos");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int index = thelist.getSelectedIndex();
					if(index < 0){
						throw new Exception();
					}
					annotationMap.remove(ImageList.get(index).getTitle());
					ImageList.remove(index);
					write_to_file();
					write_to_image_file();
					JOptionPane.showMessageDialog(null, "Picture deleted from album,PLease open the album again");
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "Please select an item in the list", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		btnDelete.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(btnDelete);
		
		btnAdd.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(btnAdd);
		
		btnNewButton.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(btnNewButton);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				index++;
				thelist.setSelectedIndex(index);
				image = imagemap.get(list1.get(index));
				label.setText(list1.get(index));
				repaint();
				}catch(Exception e1){
					
					index--;
					thelist.setSelectedIndex(index);
					label.setText(list1.get(index));
					JOptionPane.showMessageDialog(null, "No more Photos available");
				}
			}
		});
		btnNext.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(btnNext);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnClose.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_1.add(btnClose);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 191, 255)));
		contentPane.add(panel_3, BorderLayout.NORTH);
		
		label = new JLabel("");
		label.setFont(new Font("Verdana", Font.BOLD, 16));
		panel_3.add(label);
		
		addMouseListener((new MouseAdapter(){


			@Override
				 public void mouseEntered(MouseEvent e) {
				     Cursor cursor = Cursor.getDefaultCursor();
				     //change cursor appearance to HAND_CURSOR when the mouse pointed on images
				     cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR); 
				     setCursor(cursor);
				  
			}

		}));
		
		list2 = list1.toArray();
		thelist = new JList( list2);
		thelist.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 255, 0)));
		thelist.setVisibleRowCount(10);
		thelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(thelist);
		contentPane.add(scrollPane, BorderLayout.WEST);
		
		thelist.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent e) {
				label.setText((String) list2[thelist.getSelectedIndex()]);
				index = thelist.getSelectedIndex();
				image = imagemap.get(label.getText());
				repaint();
			}
			
		});
		
		
		if(ImageList.size()>0){
		thelist.setSelectedIndex(0);
		label.setText(list1.get(0));
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(800,600);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, panel.getX(),panel.getY(),getWidth(),getHeight(), null);
		
		if(annotationMap.get(label.getText())!=null){
			this.setToolTipText(annotationMap.get(label.getText()));
			}else{
				this.setToolTipText("Nothing to show");
			}
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			Annotation dialog = new Annotation(image,label.getText());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
