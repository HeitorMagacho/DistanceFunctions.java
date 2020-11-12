import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JLabel;

public class RadioButton extends JFrame {

	private static final long serialVersionUID = 1L;

	private JRadioButton l1, l2, brayCurtis, gower, soergel, kulczynski, canberra, lorentzian, interseccao, 
	czekanowski, motyka, ruzicka, tanimoto, produtoInterno, mediaHarmonica, cosseno, kumarHassebrook, jaccard, dice,
	bhattacharyya, hellinger, matusita, squaredChord; //, loo
	private JRadioButton normalize, statistics, kmeans, kmedians;

	private JLabel label1, label2;
	private ButtonGroup group1;
	private RadioButtonHandler handler;

	DistanceFunction df;

	boolean[] extraOptions = new boolean[4];
	boolean end;

	public RadioButton() {

		setLayout( new FlowLayout() );
		handler = new RadioButtonHandler();
		end = false;
		for (int x = 0; x < 4; x++) {
			extraOptions[x] = false;
		}

		label1 = new JLabel("Choose a Distance Function:\n");
		l1 = new JRadioButton("L1", false);
		l2 = new JRadioButton("L2", false);
		//loo = new JRadioButton("Loo", false);
		brayCurtis = new JRadioButton("Bray-Curtis", false);
		gower = new JRadioButton("Gower", false);
		soergel = new JRadioButton("soergel", false);
		kulczynski = new JRadioButton("Kulczynski", false);
		canberra = new JRadioButton("Canberra", false);
		lorentzian = new JRadioButton("Lorentzian", false);
		interseccao = new JRadioButton("Interseccao", false);
		czekanowski = new JRadioButton("Czekanowski", false);
		motyka = new JRadioButton("Motyka", false);
		ruzicka = new JRadioButton("Ruzicka", false);
		tanimoto = new JRadioButton("Tanimoto", false);
		produtoInterno = new JRadioButton("Produto Interno", false);
		mediaHarmonica = new JRadioButton("Media Harmonica", false);
		cosseno = new JRadioButton("Cosseno", false);
		kumarHassebrook = new JRadioButton("Kumar-Hassebrook", false);
		jaccard = new JRadioButton("Jaccard", false);
		dice = new JRadioButton("Dice", false);
		bhattacharyya = new JRadioButton("Bhattacharyya", false);
		hellinger = new JRadioButton("Hellinger", false);
		matusita = new JRadioButton("Matusita", false);
		squaredChord = new JRadioButton("Squared Chord", false);

		label2 = new JLabel("#Extra Options");
		normalize = new JRadioButton("Normalize Dataset", false);
		statistics = new JRadioButton("Print Statistics", false);
		kmeans = new JRadioButton("Kmeans", false);
		kmedians = new JRadioButton("Kmedians", false);

		add(label1);
		add(l1);
		add(l2);
		//add(loo);
		add(brayCurtis);
		add(gower);
		add(soergel);
		add(kulczynski);
		add(canberra);
		add(lorentzian);
		add(interseccao);
		add(czekanowski);
		add(motyka);
		add(ruzicka);
		add(tanimoto);
		add(produtoInterno);
		add(mediaHarmonica);
		add(cosseno);
		add(kumarHassebrook);
		add(jaccard);
		add(dice);
		add(bhattacharyya);
		add(hellinger);
		add(matusita);
		add(squaredChord);

		add(label2);
		add(normalize);
		add(statistics);
		add(kmeans);
		add(kmedians);


		group1 = new ButtonGroup();
		group1.add(l1);
		group1.add(l2);
		//group1.add(loo);
		group1.add(brayCurtis);
		group1.add(gower);
		group1.add(soergel);
		group1.add(kulczynski);
		group1.add(canberra);
		group1.add(lorentzian);
		group1.add(interseccao);
		group1.add(czekanowski);
		group1.add(motyka);
		group1.add(ruzicka);
		group1.add(tanimoto);
		group1.add(produtoInterno);
		group1.add(mediaHarmonica);
		group1.add(cosseno);
		group1.add(kumarHassebrook);
		group1.add(jaccard);
		group1.add(dice);
		group1.add(bhattacharyya);
		group1.add(hellinger);
		group1.add(matusita);
		group1.add(squaredChord);

		group1.add(normalize);
		group1.add(statistics);
		group1.add(kmeans);
		group1.add(kmedians);

		l1.addItemListener(handler);
		l2.addItemListener(handler);
		//loo.addItemListener(handler);
		brayCurtis.addItemListener(handler);
		gower.addItemListener(handler);
		soergel.addItemListener(handler);
		kulczynski.addItemListener(handler);
		canberra.addItemListener(handler);
		lorentzian.addItemListener(handler);
		interseccao.addItemListener(handler);
		czekanowski.addItemListener(handler);
		motyka.addItemListener(handler);
		ruzicka.addItemListener(handler);
		tanimoto.addItemListener(handler);
		produtoInterno.addItemListener(handler);
		mediaHarmonica.addItemListener(handler);
		cosseno.addItemListener(handler);
		kumarHassebrook.addItemListener(handler);
		jaccard.addItemListener(handler);
		dice.addItemListener(handler);
		bhattacharyya.addItemListener(handler);
		hellinger.addItemListener(handler);
		matusita.addItemListener(handler);
		squaredChord.addItemListener(handler);

		normalize.addItemListener(handler);
		statistics.addItemListener(handler);
		kmeans.addItemListener(handler);
		kmedians.addItemListener(handler);
	}

	private class RadioButtonHandler implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent event) {

			if(l1.isSelected()) setDf(new L1());
			if(l2.isSelected()) setDf(new L2());
			//if(loo.isSelected()) setDf(new Loo());
			if(brayCurtis.isSelected()) setDf(new Bray_Curtis());
			if(gower.isSelected()) setDf(new Gower());
			if(soergel.isSelected()) setDf(new Soergel());
			if(kulczynski.isSelected()) setDf(new Kulczynski());
			if(canberra.isSelected()) setDf(new Canberra());
			if(lorentzian.isSelected()) setDf(new Lorentzian());
			if(interseccao.isSelected()) setDf(new Interseccao());
			if(czekanowski.isSelected()) setDf(new Czekanowski());
			if(motyka.isSelected()) setDf(new Motyka());
			if(ruzicka.isSelected()) setDf(new Ruzicka());
			if(tanimoto.isSelected()) setDf(new Tanimoto());
			if(produtoInterno.isSelected()) setDf(new Produto_Interno());
			if(mediaHarmonica.isSelected()) setDf(new Media_Harmonica());
			if(cosseno.isSelected()) setDf(new Cosseno());
			if(kumarHassebrook.isSelected()) setDf(new Kumar_Hassebrook());
			if(jaccard.isSelected()) setDf(new Jaccard());
			if(dice.isSelected()) setDf(new Dice());
			if(bhattacharyya.isSelected()) setDf(new Bhattacharyya());
			if(hellinger.isSelected()) setDf(new Hellinger());
			if(matusita.isSelected()) setDf(new Matusita());
			if(squaredChord.isSelected()) setDf(new Squared_Chord());

			if(normalize.isSelected())  extraOptions[0] = true;
			if(statistics.isSelected()) extraOptions[1] = true;
			if(kmeans.isSelected())     extraOptions[2] = true;
			if(kmedians.isSelected())   extraOptions[3] = true;

			end = true;
			setVisible(false);
		}

	}

	public DistanceFunction getDf() {
		return df;
	}

	public void setDf(DistanceFunction df) {
		this.df = df;
	}

	public boolean[] getExtraOptions(){
		return extraOptions;
	}
	
	public boolean getEnd() {
		return end;
	}

}