package pl.grabber.grabbing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.grabber.db.DbManager;
import pl.grabber.objects.Article;

public class ContentGrabber {
	
	private IContentGrabber grabber;
	
	DbManager dbManager = DbManager.getInstance();
	
	public void setContentGrabber(IContentGrabber cg){
		this.grabber = cg;
	}
	
	public void grapArticlesFromPageSource(Document sourceCode) {

			Element content = sourceCode.getElementById("itemsStream");
			Elements itemsStream = content.getElementsByAttribute("data-id");
			
			for (Element sourceElement : itemsStream) {
				  Article article = new Article();
				  
				  article.setAuthor(grabber.grabAuthor(sourceElement));
				  article.setImage(grabber.grabImage(sourceElement));
				  article.setTitle(grabber.grabTitle(sourceElement));
				  
				  
				  article.setDescription(grabber.grabDescription(sourceElement));
				  article.setScore(grabber.grabScore(sourceElement));
				  article.setSource(grabber.grabSource(sourceElement));
				  article.setPublishDate(grabber.grabDate(sourceElement));
				  
				  if(!article.getTitle().equals("") && !dbManager.isArticleAlreadyInDb(article)){
					  saveImageToFile(article);
					  saveArticleToDb(article);
				  }
					  
				}
		
		//return null;
	}
	
	protected void saveArticleToDb(Article article){
		dbManager.insertArticleToDb(article);
	}

	
	public void saveImageToFile(Article article){
		
		String imageUrl = article.getImage();
		String articleTitle = article.getTitle();
		if(imageUrl.equals("") || articleTitle.equals(""))
			return;
		
		String folder = "WebContent/grabbedMedia/";
		String imgName = String.valueOf(articleTitle.hashCode());
		File file = new File(folder + imgName);
		if(file.exists() && !file.isDirectory()) {
			return;
		}else{
			URL url;
			try {
				url = new URL(imageUrl);

			InputStream in = new BufferedInputStream(url.openStream());
			OutputStream out = new BufferedOutputStream(new FileOutputStream(folder + imgName + ".jpg"));
			for ( int i; (i = in.read()) != -1; ) {
			    out.write(i);
			}
			
			in.close();
			out.close();
			System.out.println(imgName + " saved");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
