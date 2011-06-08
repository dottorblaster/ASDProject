package src;

import asdlab.libreria.*;
import java.util.*;
import java.lang.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class NewJPanel extends javax.swing.JPanel {
	private JButton Genera;
	private JButton RadixSort;
	private AbstractAction GenerateArray;
	private AbstractAction abstractAction1;
	private AbstractAction DoMerge;
	private AbstractAction IntegerS;
	private JButton IntegerSort;
	private JButton MergeSort;
	int[] array;
	int n;
	int k;

	{
		//Imposta il Look & Feel dell'interfaccia grafica.
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	//Metodo principale.
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new NewJPanel());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public NewJPanel() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setLayout(null);
			this.setPreferredSize(new java.awt.Dimension(241, 216));
			//Aggiungo i vari bottoni ed eventi relativi alla piccola interfaccia grafica
			{
				Genera = new JButton();
				this.add(Genera);
				Genera.setText("Genera Array");
				Genera.setBounds(66, 12, 107, 26);
				Genera.setAction(getGenerateArray());
			}
			{
				RadixSort = new JButton();
				this.add(RadixSort);
				RadixSort.setText("Esegui RadixSort");
				RadixSort.setBounds(66, 91, 107, 26);
				RadixSort.setAction(getAbstractAction1());
			}
			{
				MergeSort = new JButton();
				this.add(MergeSort);
				MergeSort.setText("Esegui MergeSort");
				MergeSort.setBounds(66, 155, 107, 26);
				MergeSort.setAction(getDoMerge());
			}
			{
				IntegerSort = new JButton();
				this.add(IntegerSort);
				IntegerSort.setText("Esegui IntegerSort");
				IntegerSort.setBounds(66, 123, 107, 26);
				IntegerSort.setAction(getIntegerS());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private AbstractAction getGenerateArray() {
		if(GenerateArray == null) {
			GenerateArray = new AbstractAction("Genera Array", null) {
				public void actionPerformed(ActionEvent evt) {
					k = 0; //reinizializza k in modo che venga resettato il confronto
					n = (int)(1000000*Math.random())+1; //imposta la dimensione dell'array
					array=new int[n]; //alloca un nuovo array della dimensione fissata (a caso)
					for(int i=0; i<n; i++){
						array[i] = (int)(100000000*Math.random())+1; //genera nuovi elementi casualmente
						if (array[i]>k){k=array[i];} //confronta il nuovo elemento generato con l'elemento più grande. In caso, lo sostituisce.
					}
					JOptionPane.showMessageDialog(null,"Generato un array di " + n + " elementi. \n k è pari a " + k + ".");
				}
			};
		}
		return GenerateArray; //returns GenerateArray.
	}
	
	/**
	 * Ordina l'array di input utilizzando l'algoritmo di integer sort (<font color=red>Tempo O(n+k)</font>).
	 * Correzione dell'algoritmo utilizzato dalla libreria ASDlab.
	 * Erano stati scelti degli indici sbagliati per un for e per la creazione dell'array.
	 *  
	 * @param A l'array da ordinare
	 * @param k il valore massimo assumibile dagli elementi di <code>A</code>
	 * 
	 * 
	 */
	public static void integerSort(int[] A, int k) {
		int[] Y = new int[k+1]; //kinda magic. Do not touch.
		int j = 0;
		for (int i = 0; i < k+1; i++) {Y[i] = 0;}
		for (int i = 0; i < A.length; i++) {Y[A[i]]++;}
		for (int i = 0; i < k+1; i++) {
			while (Y[i] > 0) {
				A[j] = i;
				j++;
				Y[i]--;
			}
		}
	}
	
	/**
	 * Ordina l'array di input utilizzando l'algoritmo Radix Sort.
	 * Codice scritto a partire dai codici disponibili scritti dal docente, vista la scarsa voglia
	 * di collaborare del metodo implementato nella libreria ASDLab.
	 *  
	 * @param A l'array da ordinare
	 * @param k il valore massimo assumibile dagli elementi di <code>A</code>
	 * @param b base del RadixSort
	 *
	 * 
	 */
	public static void radixSort(int[] A, int k, int b) {
		int cifrek = (int)Math.ceil(Math.log(k+1)/Math.log(b));//il numero di interi fra 0 e k e' k+1
		for(int t=1; t<=cifrek; t++){
			LinkedList[] bucket = new LinkedList[b];
			for(int i=0; i<b; i++) bucket[i] = new LinkedList();//inizializziamo i bucket
			for(int j=0; j<A.length; j++){
				int cifratj = A[j] % ((int)Math.pow(b,t));//leggiamo la t-esima cifra di A[j]
				cifratj = cifratj / (int)Math.pow(b,t-1);
				bucket[cifratj].addLast(A[j]);//aggiungiamo A[j] nel bucket corrispondente
			}
			int j=0;
			for(int i=0; i<b; i++){
				while(!bucket[i].isEmpty()){
					A[j] = (Integer)bucket[i].getFirst();
					bucket[i].removeFirst();
					j++;
				}
			}
		}
	}
	
	private AbstractAction getAbstractAction1() {
		if(abstractAction1 == null) {
			abstractAction1 = new AbstractAction("Esegui RadixSort", null) {
				public void actionPerformed(ActionEvent evt) {
					int[] arrayradix = new int[n];
					for(int i=0;i<n;i++){arrayradix[i]=array[i];}
					String base = JOptionPane.showInputDialog("Inserisci la base del RadixSort."); //l'utente deve inserire la base del RadixSort
					int b = Integer.parseInt(base); //ottengo il numero intero digitato
					double timer1 = System.nanoTime();
					radixSort(arrayradix,k,b);
					double timer2 = System.nanoTime();
					double timer = (timer2-timer1)/1000000;
					JOptionPane.showMessageDialog(null,"Tempo di esecuzione del RadixSort: " + timer + "ms");
				}
			};
		}
		return abstractAction1;
	}
	
	private AbstractAction getIntegerS() {
		if(IntegerS == null) {
			IntegerS = new AbstractAction("Esegui IntegerSort", null) {
				public void actionPerformed(ActionEvent evt) {
					int[] arrayinteger = new int[n];
					for(int i=0;i<n;i++){arrayinteger[i]=array[i];}
					double timer1 = System.nanoTime();
					integerSort(arrayinteger,k);
					double timer2 = System.nanoTime();
					double timer = (timer2-timer1)/1000000;
					JOptionPane.showMessageDialog(null,"Tempo di esecuzione dell'IntegerSort: " + timer + "ms");
				}
			};
		}
		return IntegerS;
	}
	
	private AbstractAction getDoMerge() {
		if(DoMerge == null) {
			DoMerge = new AbstractAction("Esegui MergeSort", null) {
				public void actionPerformed(ActionEvent evt) {
					Comparable[] array2 = new Comparable[n];
					for(int i=0;i<n;i++){array2[i]=array[i];} //clona l'array di interi in un array di Comparable
					double timer1 = System.nanoTime();
					asdlab.libreria.Ordinamento.AlgoritmiOrdinamento.mergeSort(array2); //esegue MergeSort sull'array clonato
					double timer2 = System.nanoTime();
					double timer = (timer2-timer1)/1000000;
					JOptionPane.showMessageDialog(null,"Tempo di esecuzione del MergeSort: " + timer + "ms");
				}
			};
		}
		return DoMerge;
	}

}
