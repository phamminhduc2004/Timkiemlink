import java.util.*;

public class SearchEngine {
    private List<String> websites;
    private Map<String, List<Article>> articlesCache;
    
    public SearchEngine() {
        this.websites = new ArrayList<>();
        this.articlesCache = new HashMap<>();
        initializeWebsites();
        initializeSampleData();
    }
    
    private void initializeWebsites() {
        websites.add("https://dantri.com.vn");
        websites.add("https://facebook.com");
        websites.add("https://vnexpress.net");
        websites.add("https://tuoitre.vn");
    }
    
    private void initializeSampleData() {
        List<Article> dantriArticles = Arrays.asList(
            new Article("Cơn bão số 3 gây thiệt hại nặng nề", "dantri.com.vn", "22/5/2024"),
            new Article("Bão số 3 từ ngày 22/5 tới, cần chuẩn bị ứng phó", "dantri.com.vn", "20/5/2024"),
            new Article("Tin tức công nghệ mới nhất", "dantri.com.vn", "23/5/2024")
        );
        
        List<Article> facebookArticles = Arrays.asList(
            new Article("Cập nhật tính năng mới trên Facebook", "facebook.com", "21/5/2024"),
            new Article("Bão số 3 - Cảnh báo khẩn cấp", "facebook.com", "22/5/2024")
        );
        
        articlesCache.put("https://dantri.com.vn", dantriArticles);
        articlesCache.put("https://facebook.com", facebookArticles);
    }
    
    public List<Article> search(String keyword1, String keyword2, String keyword3) {
        List<Article> results = new ArrayList<>();
        
        for (String website : websites) {
            List<Article> articles = articlesCache.getOrDefault(website, new ArrayList<>());
            
            for (Article article : articles) {
                if (matchesKeywords(article, keyword1, keyword2, keyword3)) {
                    results.add(article);
                }
            }
        }
        
        return results;
    }
    
    private boolean matchesKeywords(Article article, String kw1, String kw2, String kw3) {
        String content = article.getTitle().toLowerCase();
        
        boolean match = true;
        if (kw1 != null && !kw1.isEmpty()) {
            match = match && content.contains(kw1.toLowerCase());
        }
        if (kw2 != null && !kw2.isEmpty()) {
            match = match && content.contains(kw2.toLowerCase());
        }
        if (kw3 != null && !kw3.isEmpty()) {
            match = match && content.contains(kw3.toLowerCase());
        }
        
        return match;
    }
    
    public List<String> searchProductPrices(String productName) {
        List<String> prices = new ArrayList<>();
        prices.add(productName + " - 5.000.000đ tại Shopee");
        prices.add(productName + " - 5.200.000đ tại Lazada");
        prices.add(productName + " - 4.900.000đ tại Tiki");
        return prices;
    }
    
    public List<String> getWebsites() {
        return new ArrayList<>(websites);
    }
    
    public void addWebsite(String url) {
        if (!websites.contains(url)) {
            websites.add(url);
            articlesCache.put(url, new ArrayList<>());
        }
    }
    
    public void removeWebsite(String url) {
        websites.remove(url);
        articlesCache.remove(url);
    }
}
