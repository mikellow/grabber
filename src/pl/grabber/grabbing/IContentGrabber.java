package pl.grabber.grabbing;

import java.util.Date;

import org.jsoup.nodes.Element;

public interface IContentGrabber {
	
	String grabTitle(Element source);
	String grabSource(Element source);
	double grabScore(Element source);
	String grabAuthor(Element source);
	String grabDescription(Element source);
	String grabImage(Element source);
	Date grabDate(Element source);

}
