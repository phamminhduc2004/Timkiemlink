public class Article {
    private String title;
    private String source;
    private String date;
    
    public Article(String title, String source, String date) {
        this.title = title;
        this.source = source;
        this.date = date;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getDate() {
        return date;
    }
}
