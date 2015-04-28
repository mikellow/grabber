package pl.grabber.grabbing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WykopContentGrabber implements IContentGrabber{


	@Override
	public String grabTitle(Element source) {
 		String titleTxt;
		if(source.getElementsByTag("h2").size() < 1){
			return "";
		}else{
			Element title = source.getElementsByTag("h2").get(0);
			titleTxt = title.text();
		}
		
		titleTxt = titleTxt.replaceAll("\"","&quot;").replaceAll("\'","&#39;");
		return titleTxt;
	}

	@Override
	public String grabSource(Element source) {
		String sourceTxt;
		if(source.getElementsByClass("fix-tagline").size() < 1){
			return "";
		}else{
			Element sourceContainer = source.getElementsByClass("fix-tagline").get(0);
			Element sourceTag = sourceContainer.getElementsByTag("span").get(0);
			sourceTxt = sourceTag.getElementsByTag("a").get(0).attr("href");
		}
		  
		return sourceTxt;
	}

	@Override
	public double grabScore(Element source) {
		
		double diggsAmount = 0;
		if(source.getElementsByClass("diggbox").size() < 1){
			return 0;
		}else{
			Element diggs = source.getElementsByClass("diggbox").get(0);
			Element diggsSpan = diggs.getElementsByTag("span").get(0);
			diggsAmount = Double.parseDouble(diggsSpan.text());
		}
		return diggsAmount;
	}

	@Override
	public String grabAuthor(Element source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grabDescription(Element source) {
		// TODO Auto-generated method stub
		String description;
		Elements descriptionBox  = source.getElementsByClass("description");
		if(descriptionBox.size() < 1)
			return "";
		else{
			description  = descriptionBox.get(0).getElementsByTag("a").get(0).html();	
		}
		description = description.replaceAll("\"","&quot;").replaceAll("\'","&#39;");
		return description;
	}

	@Override
	public String grabImage(Element source) {
		// TODO Auto-generated method stub
		String image;
		Elements mediaBox  = source.getElementsByClass("media-content");
		if(mediaBox.size() < 1)
			return "";
		else{
			//src try
			image  = mediaBox.get(0).getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("src");
			if(image.equals("") || image==null){
				image  = mediaBox.get(0).getElementsByTag("a").get(0).getElementsByTag("img").get(0).attr("data-original");	
			}
		}
		return image;
	}

	@Override
	public Date grabDate(Element source) {
		String dateString;
		Elements publishedDateBox  = source.getElementsByTag("time");
		if(publishedDateBox.size() < 1)
			return null;
		else{
			//src try
			dateString  = publishedDateBox.get(0).attr("title");
		}	
		
		//System.out.println("found : " + dateString);
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		//String dateInString = "31-08-1982 10:20:56";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateString);
			//System.out.println(date); //Tue Aug 31 10:20:56 SGT 1982
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	

}
