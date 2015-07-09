package print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;

import model.Skizze;


/**
 * 
 * Eine Skitze ausdrucken
 * 
 * @author Thomas Nill
 *
 */
public class PrintJob implements Printable {
	Skizze model;

	public PrintJob(Skizze model) {
		this.model = model;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(OrientationRequested.PORTRAIT);
		aset.add(new Copies(1));
		aset.add(new JobName("Skitze", null));

		/* Create a print job */
		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj != null) {
			pj.setPrintable(this);

			try {
				pj.pageDialog(aset);
				if (pj.printDialog(aset)) {
					pj.print(aset);
				}
			} catch (PrinterException pe) {
				System.err.println(pe);
			}
		}

	}

	public int print(Graphics g, PageFormat pf, int pageIndex) {

		if (pageIndex == 0) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());
			model.paint(g2d);
			return Printable.PAGE_EXISTS;
		} else {
			return Printable.NO_SUCH_PAGE;
		}
	}

}
