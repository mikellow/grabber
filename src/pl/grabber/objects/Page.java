package pl.grabber.objects;

public class Page {
	Category category;
	int paginationNumber;
	int articlesOnPage;
	
	
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public int getPaginationNumber() {
		return paginationNumber;
	}
	public void setPaginationNumber(int paginationNumber) {
		this.paginationNumber = paginationNumber;
	}
	public int getArticlesOnPage() {
		return articlesOnPage;
	}
	public void setArticlesOnPage(int articlesOnPage) {
		this.articlesOnPage = articlesOnPage;
	}
	
	
	
}
