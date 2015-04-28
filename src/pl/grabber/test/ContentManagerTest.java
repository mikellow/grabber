package pl.grabber.test;

import pl.grabber.managers.ContentManager;

public class ContentManagerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ContentManager contentManager = ContentManager.getInstance();
		System.out.println("#contentManager.loadFromDbTagsAll() test : ");
		contentManager.loadFromDbTagsAll();
		
		System.out.println("#contentManager.loadFromDbCategoriesAll() test : ");
		contentManager.loadFromDbCategoriesAll();
	}

}
