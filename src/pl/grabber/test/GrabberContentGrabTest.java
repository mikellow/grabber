package pl.grabber.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import pl.grabber.grabbing.ContentGrabber;
import pl.grabber.grabbing.WykopContentGrabber;

public class GrabberContentGrabTest {

	public static void main(String[] args) throws IOException {
		/*
		System.setProperty("http.proxyHost", "l7fwout.usinet.it");
        System.setProperty("http.proxyPort", "3128");
        */
		long startTime = System.nanoTime();    
		//WykopGrabber wykopArticles = new WykopGrabber();
		
		ContentGrabber contentGrabber = new ContentGrabber();
		contentGrabber.setContentGrabber(new WykopContentGrabber());
		
		int firstPage = 1;
		int lastPage = 2;
		//int lastPage = 2026;
		for(int i=firstPage;i<=lastPage;i++){
			String url = "http://www.wykop.pl/strona/"+i+"/";
			//System.out.println(url);
			Document doc = Jsoup.connect(url).get();
			contentGrabber.grapArticlesFromPageSource(doc);
		}
		
		long estimatedTime = System.nanoTime() - startTime;
		double seconds = (double)estimatedTime / 1000000000.0;
		System.out.println(estimatedTime);
		System.out.println(seconds);
	}

}
